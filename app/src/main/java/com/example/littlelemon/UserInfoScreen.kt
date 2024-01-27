package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.LittleLemonColor

/*TODO: use shared preference*/

@Composable
fun UserInfoScreen(navController: NavHostController){
    val context = LocalContext.current


    val isRegisteredLiveData = MutableLiveData<Boolean>()
    val firstnameLiveData = MutableLiveData<String>()
    val lastnameLiveData = MutableLiveData<String>()
    val emailLiveData = MutableLiveData<String>()


    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)




    firstnameLiveData.value = sharedPreferences.getString("firstname", "")
    var firstname by remember {
        mutableStateOf(TextFieldValue(firstnameLiveData.value.toString()))
    }

    lastnameLiveData.value = sharedPreferences.getString("lastname", "")
    var lastname by remember {
        mutableStateOf(TextFieldValue(lastnameLiveData.value.toString()))
    }
    emailLiveData.value = sharedPreferences.getString("email", "")
    var email by remember {
        mutableStateOf(TextFieldValue(emailLiveData.value.toString()))
    }

    var isRegistered by remember {
        mutableStateOf(if(sharedPreferences.getBoolean("isRegistered", false))true else false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        //TopAppBar(navController,if(isRegistered) true else false)
        TopAppBar(navController,false)
        if(! isRegistered){

            Column(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
                    .background(color = LittleLemonColor.green)
            ){
                Text(
                    text = "Let's get to know you",
                    style = MaterialTheme.typography.body1,
                    color = LittleLemonColor.cloud,
                    modifier = Modifier
                        .padding(bottom = 28.dp, end = 30.dp)
                        .fillMaxWidth(0.6f)
                )
            }

        }

        Text(
            text = "Personal Information"
        )
        TextField(
            value = firstname,
            onValueChange = {
                firstname = it

                sharedPreferences.edit(commit = true) {
                    putString("firstname", firstname.text)
                }
                firstnameLiveData.value = firstname.text
            },
            readOnly = isRegistered,
            label = { Text(text = "First name") },
            modifier = Modifier.padding(10.dp)
        )

        TextField(
            value = lastname,
            onValueChange = {
                lastname = it

                sharedPreferences.edit(commit = true) {
                    putString("lastname", lastname.text)
                }
                lastnameLiveData.value = lastname.text

            },
            readOnly = isRegistered,
            label = { Text(text = "Last name ") },
            modifier = Modifier.padding(10.dp)
        )

        TextField(
            value = email,
            onValueChange = {
                email = it

                sharedPreferences.edit(commit = true) {
                    putString("email", email.text)
                }
                emailLiveData.value = email.text
            },
            readOnly = isRegistered,
            label = { Text(text = "Email") },
            modifier = Modifier.padding(10.dp)
        )

        Button(
            onClick = {
                if (! isRegistered){
                    isRegistered = true;

                    sharedPreferences.edit(commit = true) {
                        putBoolean("isRegistered", true)
                    }
                    isRegisteredLiveData.value = true
                }
                else{
                    firstname=TextFieldValue("")
                    sharedPreferences.edit(commit = true) {
                        putString("firstname", firstname.text)
                    }
                    firstnameLiveData.value = firstname.text

                    lastname=TextFieldValue("")
                    sharedPreferences.edit(commit = true) {
                        putString("lastname", firstname.text)
                    }
                    lastnameLiveData.value = lastname.text

                    email=TextFieldValue("")
                    sharedPreferences.edit(commit = true) {
                        putString("email", firstname.text)
                    }
                    emailLiveData.value = email.text

                    isRegistered = false;

                    sharedPreferences.edit(commit = true) {
                        putBoolean("isRegistered", false)
                    }
                    isRegisteredLiveData.value = false
                }

            },
            colors = ButtonDefaults.buttonColors(
                Color.Yellow
            ),
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = if (isRegistered) "Logout" else "Register"
            )
        }


        if (isRegistered){
            Button(
                onClick = {
                    navController?.navigate(Home.route)
                },
                colors = ButtonDefaults.buttonColors(
                    Color.Yellow
                ),
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text =  "To Home"
                )
            }


        }



    }}

    @Preview(showBackground = true)
    @Composable
    fun UserInfoScreenPreview(){
        val navController = rememberNavController()
        UserInfoScreen(navController)
    }