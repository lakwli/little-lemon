package com.example.littlelemon

interface Destinations {
    val route: String
}
object UserDetail : Destinations {
    override val route = "UserDetail"
}

object Home : Destinations {
    override val route = "Home"
}

object DishDetails : Destinations {
    override val route = "Menu"
    const val argDishId = "dishId"
}
