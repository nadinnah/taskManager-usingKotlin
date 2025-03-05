package com.example.taskmanager.home.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(loginViewModel: LoginViewModel?=null, onNavToHomePage:()->Unit, onNavToLoginPage:()->Unit, onNavToSignupPage:()->Unit){
    val loginUiState= loginViewModel?.loginUiState
    val isError= loginUiState?.loginError!=null
    val context= LocalContext.current

    Column(
        modifier= Modifier.fillMaxSize(),
        horizontalAlignment= Alignment.CenterHorizontally
    ){
        Text(
            text= "Login",
            style= MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black,
            color= MaterialTheme.colorScheme.primary
        )
        if(isError){
            Text(
                text= loginUiState?.loginError?: "unknown error",
                color= Color.Red
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLoginScreen(){
    MaterialTheme {
        LoginScreen(
            onNavToHomePage = { /*TODO*/ },
            loginViewModel = null,
            onNavToLoginPage = { /*TODO*/ }
        ) {
            
        }
    }
}

