package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {

    private val isRegisteredLiveData = MutableLiveData<Boolean>()
    private val sharedPreferences by lazy {
        getSharedPreferences("LittleLemon", MODE_PRIVATE)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isRegisteredLiveData.value = sharedPreferences.getBoolean("isRegistered", false)

        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()

                var startDestinationRoute = UserDetail.route;
                val isRegistered = isRegisteredLiveData.observeAsState(false)
                if(isRegistered.value){
                    startDestinationRoute=Home.route
                }

                NavHost(navController = navController, startDestination = startDestinationRoute) {
                    composable(UserDetail.route) {
                        UserInfoScreen(navController)
                    }
                    composable(Home.route) {
                        HomeScreen(navController)
                    }
                    composable(
                        DishDetails.route + "/{${DishDetails.argDishId}}",
                        arguments = listOf(navArgument(DishDetails.argDishId) { type = NavType.IntType })
                    ) {
                        val id = requireNotNull(it.arguments?.getInt(DishDetails.argDishId)) { "Dish id is null" }
                        DishDetails(id, navController)
                    }
                }
            }
        }
    }
}
