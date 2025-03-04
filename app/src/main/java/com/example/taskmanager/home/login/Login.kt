package com.example.taskmanager.home.login

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(loginViewModel: LoginViewModel?=null, onNavToHomePage:()->Unit, onNavToLoginPage:()->Unit, onNavToSignupPage:()->Unit){
    val loginUiState= loginViewModel?.loginUiState
    val isError= loginUiState?.loginError!=null
    val context= LocalContext.current

    Column(){

    }
}