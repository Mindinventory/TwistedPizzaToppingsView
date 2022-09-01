package com.mindinventory.twistedpizzatoppingssample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindinventory.twistedpizzatoppingssample.R
import com.mindinventory.twistedpizzatoppingssample.model.PizzaData
import com.mindinventory.twistedpizzatoppingssample.model.Topping
import kotlinx.coroutines.launch

enum class PizzaSize {
    SMALL,
    MEDIUM,
    LARGE
}

class SampleViewModel : ViewModel() {

    private var selectedPizzaPrice = 0
    private var selectedPizzaPosition = 0

    private val _pizzaTotalPrice = MutableLiveData<Int?>()
    val pizzaTotalPrice: LiveData<Int?> get() = _pizzaTotalPrice

    private val _selectedPizza = MutableLiveData<PizzaData>()
    val selectedPizza: LiveData<PizzaData> get() = _selectedPizza

    private val _selectedPizzaSize = MutableLiveData<PizzaSize>()
    val selectedPizzaSize: LiveData<PizzaSize> get() = _selectedPizzaSize

    private val _toppingList = MutableLiveData<ArrayList<Topping>>()
    val toppingList: LiveData<ArrayList<Topping>> get() = _toppingList

    /**
     * Uses for set default pizza
     */
    fun initialize() {
        setPizza()
        getToppingList()
        calculatePizzaInitialPrice(PizzaSize.MEDIUM)
    }

    /**
     * Uses for notify selected pizza
     */
    private fun setPizza() {
        viewModelScope.launch {
            _selectedPizza.postValue(getPizzaList()[selectedPizzaPosition])
        }
    }

    /**
     * Uses for calculate selected pizza price
     */
    private fun calculatePizzaInitialPrice(selectedVariant: PizzaSize) {
        viewModelScope.launch {
            var pizzaPrice = 0
            val selectedPizzaSize =
                getPizzaList()[selectedPizzaPosition].price.filter { it.first == selectedVariant }
            if (selectedPizzaSize.isNotEmpty()) {
                selectedPizzaPrice = selectedPizzaSize[0].second
                pizzaPrice += selectedPizzaPrice
                _pizzaTotalPrice.postValue(pizzaPrice)
            }
        }
    }

    /**
     * Uses for calculate pizza price according to selected size
     */
    private fun calculatePizzaVariantPrice(selectedPizzaSize: PizzaSize) {
        viewModelScope.launch {
            var pizzaPrice = 0
            _pizzaTotalPrice.value?.let {
                pizzaPrice = it
            }
            pizzaPrice -= selectedPizzaPrice
            val pizzaSize =
                getPizzaList()[selectedPizzaPosition].price.filter { it.first == selectedPizzaSize }
            if (pizzaSize.isNotEmpty()) {
                selectedPizzaPrice = pizzaSize[0].second
                pizzaPrice += selectedPizzaPrice
                _pizzaTotalPrice.postValue(pizzaPrice)
            }
        }
    }

    /**
     * Uses for calculate pizza topping price
     */
    fun calculatePizzaToppingPrice(topping: Topping) {
        viewModelScope.launch {
            var pizzaPrice = 0
            _pizzaTotalPrice.value?.let {
                pizzaPrice = it
            }
            pizzaPrice += topping.price
            _pizzaTotalPrice.postValue(pizzaPrice)
        }
    }

    /**
     * Manage view onPrevious button click
     */
    fun onPrevious() {
        if (selectedPizzaPosition == 0) {
            selectedPizzaPosition = getPizzaList().size - 1
        } else {
            selectedPizzaPosition--
        }
        setPizza()
        getToppingList()
        selectedPizzaSize.value?.let {
            calculatePizzaInitialPrice(it)
        }
    }

    /**
     * Manage view onNext button click
     */
    fun onNext() {
        if (selectedPizzaPosition == getPizzaList().size - 1) {
            selectedPizzaPosition = 0
        } else {
            selectedPizzaPosition++
        }
        setPizza()
        getToppingList()
        selectedPizzaSize.value?.let {
            calculatePizzaInitialPrice(it)
        }
    }

    /**
     * Uses for set pizza size
     */
    fun setSelectedPizzaSize(pizzaSize: PizzaSize) {
        calculatePizzaVariantPrice(pizzaSize)
        viewModelScope.launch {
            _selectedPizzaSize.postValue(pizzaSize)
        }
    }

    /**
     * Uses for get pizza list
     */
    private fun getPizzaList(): ArrayList<PizzaData> {
        return arrayListOf(
            PizzaData(
                R.drawable.pizza0,
                "Double Cheese",
                arrayListOf(
                    Pair(PizzaSize.SMALL, 360),
                    Pair(PizzaSize.MEDIUM, 510),
                    Pair(PizzaSize.LARGE, 720)
                )
            ),
            PizzaData(
                R.drawable.pizza1,
                "Ala Mexican",
                arrayListOf(
                    Pair(PizzaSize.SMALL, 350),
                    Pair(PizzaSize.MEDIUM, 500),
                    Pair(PizzaSize.LARGE, 700)
                )
            ),
            PizzaData(
                R.drawable.pizza5, "Tomato Olive", arrayListOf(
                    Pair(PizzaSize.SMALL, 360),
                    Pair(PizzaSize.MEDIUM, 510),
                    Pair(PizzaSize.LARGE, 710)
                )
            ),
            PizzaData(
                R.drawable.pizza4, "Paneer Tikka", arrayListOf(
                    Pair(PizzaSize.SMALL, 360),
                    Pair(PizzaSize.MEDIUM, 510),
                    Pair(PizzaSize.LARGE, 710)
                )
            )
        )
    }

    /**
     * Uses for get topping list
     */
    private fun getToppingList() {
        viewModelScope.launch {
            _toppingList.postValue(
                arrayListOf(
                    Topping(
                        R.drawable.ic_pepperoni,
                        "Pepperoni",
                        2,
                        70
                    ),
                    Topping(
                        R.drawable.ic_onions, "Onions", 3, 30
                    ),
                    Topping(
                        R.drawable.ic_sausage,
                        "Sausage",
                        4,
                        60
                    ),
                    Topping(
                        R.drawable.ic_black_olive,
                        "Black olives",
                        5,
                        80
                    ),
                    Topping(
                        R.drawable.ic_green_chilli,
                        "Green Chillis",
                        6,
                        40
                    ),
                    Topping(
                        R.drawable.ic_mushroom,
                        "Mushrooms",
                        7,
                        50
                    )
                )
            )
        }
    }
}