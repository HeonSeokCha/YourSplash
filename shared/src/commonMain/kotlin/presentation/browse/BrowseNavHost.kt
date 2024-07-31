package presentation.browse

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chs.yoursplash.presentation.Screens
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailScreen
import com.chs.yoursplash.presentation.browse.collection_detail.CollectionDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.ImageDetailScreen
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoDetailViewModel
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoTagListScreen
import com.chs.yoursplash.presentation.browse.photo_detail.PhotoTagListViewModel
import com.chs.yoursplash.presentation.browse.user.UserDetailScreen
import com.chs.yoursplash.presentation.browse.user.UserDetailViewModel
import com.chs.yoursplash.util.Constants

@Composable
fun BrowseNavHost(
    modifier: Modifier,
    navController: NavHostController,
    intent: Intent?
) {
    val context = LocalContext.current
    val startDestination: Screens =
        when (intent?.getStringExtra(Constants.TARGET_TYPE)) {
            Constants.TARGET_PHOTO -> {
                Screens.ImageDetailScreen(intent.getStringExtra(Constants.TARGET_ID)!!)
            }

            Constants.TARGET_COLLECTION -> {
                Screens.CollectionDetailScreen(intent.getStringExtra(Constants.TARGET_ID)!!)
            }

            Constants.TARGET_USER -> {
                Screens.UserDetailScreen(intent.getStringExtra(Constants.TARGET_ID)!!)
            }

            else -> {
                Screens.ImageDetailScreen(intent?.getStringExtra(Constants.TARGET_ID)!!)
            }
        }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screens.ImageDetailScreen> {
            val arg = it.toRoute<Screens.ImageDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: PhotoDetailViewModel = hiltViewModel(parentEntry)
            LaunchedEffect(context, viewModel) {
                viewModel.getImageDetailInfo()
                viewModel.getImageRelatedList()
            }

            ImageDetailScreen(viewModel.state) {
                navController.navigate(it)
            }
        }

        composable<Screens.CollectionDetailScreen> {
            val arg = it.toRoute<Screens.CollectionDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: CollectionDetailViewModel = hiltViewModel(parentEntry)
            LaunchedEffect(context, viewModel) {
                viewModel.getCollectionDetailInfo()
            }

            CollectionDetailScreen(viewModel.state) {
                if (it.first == Constants.TARGET_USER) {
                    navController.navigate(Screens.UserDetailScreen(it.second))
                } else {
                    navController.navigate(Screens.CollectionDetailScreen(it.second))
                }
            }
        }

        composable<Screens.UserDetailScreen> {
            val arg = it.toRoute<Screens.UserDetailScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: UserDetailViewModel = hiltViewModel(parentEntry)
            LaunchedEffect(context, viewModel) {
                viewModel.getUserDetailInfo()
            }

            UserDetailScreen(viewModel.state) {
                navController.navigate(it)
            }
        }

        composable<Screens.PhotoTagResultScreen> {
            val arg = it.toRoute<Screens.PhotoTagResultScreen>()
            val parentEntry = remember(it) {
                navController.getBackStackEntry(arg)
            }
            val viewModel: PhotoTagListViewModel = hiltViewModel(parentEntry)
            PhotoTagListScreen(state = viewModel.state) {
                if (it.first == Constants.TARGET_USER) {
                    navController.navigate(Screens.UserDetailScreen(it.second))
                } else {
                    navController.navigate(Screens.ImageDetailScreen(it.second))
                }
            }
        }
    }
}