package com.mindinventory.twistedpizzatoppingssample.model

data class Topping(
    val image: Int = 0,
    val name: String = "",
    val viewType: Int = 0,
    val price: Int = 0,
    var numberOfTimes: Int = 0
)