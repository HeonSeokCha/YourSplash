package com.chs.yoursplash.presentation.browse.photo_detail

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import com.chs.yoursplash.util.DownLoadState

@Composable
fun DownloadBroadCastReceiver(
    downLoadQueueId: Long,
    onResult: (DownLoadState) -> Unit
) {
    val context = LocalContext.current
    val currentOnSystemEvent by rememberUpdatedState(onResult)

    DisposableEffect(downLoadQueueId, context) {
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        val broadCast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val reference = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (downLoadQueueId == reference) {
                    val query: DownloadManager.Query = DownloadManager.Query().apply {
                        this.setFilterById(reference)
                    }
                    val downloadManager = context?.getSystemService(ComponentActivity.DOWNLOAD_SERVICE) as DownloadManager

                    val cursor: Cursor? = downloadManager.query(query)
                    if (cursor != null && cursor.moveToNext()) {
                        val status: Int = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                        cursor.close()

                        when (status) {
                            DownloadManager.STATUS_FAILED -> {
                                currentOnSystemEvent(DownLoadState.DOWNLOAD_FAILED)
                            }
                            DownloadManager.STATUS_SUCCESSFUL -> {
                                currentOnSystemEvent(DownLoadState.DOWNLOAD_SUCCESS)
                            }
                        }
                    }
                }
            }
        }

        context.registerReceiver(broadCast, intentFilter)
        onDispose {
            context.unregisterReceiver(broadCast)
        }
    }
}