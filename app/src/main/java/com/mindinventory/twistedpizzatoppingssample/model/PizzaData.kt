package com.mindinventory.twistedpizzatoppingssample.model

import com.mindinventory.twistedpizzatoppingssample.ui.PizzaSize

data class PizzaData(
    val image: Int = 0,
    val name: String = "",
    val price: ArrayList<Pair<PizzaSize, Int>> = arrayListOf()
)