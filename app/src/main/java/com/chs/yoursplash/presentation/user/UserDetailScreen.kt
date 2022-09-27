package com.chs.yoursplash.presentation.user

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun UserDetailScreen(
    userId: String,
    navController: NavHostController,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    Text(text = "UserDetailScreen")
}