package com.mindinventory.twistedpizzatoppingssample.ui

import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mindinventory.twistedpizzatoppingssample.R
import com.mindinventory.twistedpizzatoppingssample.databinding.ActivitySampleBinding
import com.mindinventory.twistedpizzatoppingssample.model.PizzaData
import com.mindinventory.twistedpizzatoppingssample.model.Topping
import kotlinx.android.synthetic.main.activity_sample.*

/**
 * This class provides pizza customization feature
 */
class SampleActivity : AppCompatActivity() {

    private var _mBinding: ActivitySampleBinding? = null
    private val mBinding get() = _mBinding!!

    private lateinit var adapter: ToppingAdapter

    private val sampleViewModel: SampleViewModel by lazy {
        ViewModelProvider(this)[SampleViewModel::class.java]
    }

    companion object {
        const val BUNDLE_KEY_SELECTED_PIZZA_SIZE = "SelectedPizzaSize"
        const val LOAD_DELAY_DURATION = 1000L
        const val PIZZA_SIZE_SMALL = 65
        const val PIZZA_SIZE_MEDIUM = 80
        const val PIZZA_SIZE_LARGE = 100
        const val TEXT_ANIMATION_DURATION = 500L
        const val MAX_SCALE = 1.5f
        const val MIN_SCALE = 1.0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _mBinding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        var selectedPizzaSize: PizzaSize? = null
        savedInstanceState?.let {
            selectedPizzaSize = it.getSerializable(BUNDLE_KEY_SELECTED_PIZZA_SIZE) as PizzaSize
        }

        registerRadioButtonCheckChangeListener()
        initClickListener()
        initObservers()

        if (selectedPizzaSize == null) {
            mBinding.rbMedium.isChecked = true
            sampleViewModel.initialize()
        } else {
            loadPresetToppings()
        }
    }

    /**
     * Uses for load preset toppings on pizza
     */
    private fun loadPresetToppings() {
        Handler(Looper.getMainLooper()).postDelayed({
            sampleViewModel.toppingList.value?.let { list ->
                manageToppingList(list)
                list.map { topping ->
                    if (topping.numberOfTimes > 0) {
                        for (i in 0 until topping.numberOfTimes) {
                            addToppingOnPizza(topping)
                        }
                    }
                }
            }
        }, LOAD_DELAY_DURATION)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(
            BUNDLE_KEY_SELECTED_PIZZA_SIZE,
            sampleViewModel.selectedPizzaSize.value
        )
        super.onSaveInstanceState(outState)
    }

    /**
     * Uses for register click listener
     */
    private fun initClickListener() {
        with(mBinding)
        {
            ibPrevious.setOnClickListener {
                sampleViewModel.onPrevious()
            }

            ibNext.setOnClickListener {
                sampleViewModel.onNext()
            }
        }
    }

    /**
     * Uses for register pizza size radio button listener
     */
    private fun registerRadioButtonCheckChangeListener() {
        with(mBinding)
        {
            rgSize.setOnCheckedChangeListener { group, checkedId ->
                val radioButton: View = group.findViewById(checkedId)
                when (group.indexOfChild(radioButton)) {
                    0 -> {
                        sampleViewModel.setSelectedPizzaSize(PizzaSize.SMALL)
                    }
                    1 -> {
                        sampleViewModel.setSelectedPizzaSize(PizzaSize.MEDIUM)
                    }
                    2 -> {
                        sampleViewModel.setSelectedPizzaSize(PizzaSize.LARGE)
                    }
                }
            }
        }
    }

    /**
     * Uses for register liveData observers
     */
    private fun initObservers() {
        sampleViewModel.pizzaTotalPrice.observe(this, ::managePizzaPrice)
        sampleViewModel.selectedPizza.observe(this, ::managePizza)
        sampleViewModel.selectedPizzaSize.observe(this, ::managePizzaSize)
        sampleViewModel.toppingList.observe(this, ::manageToppingList)
    }

    /**
     * Uses for display Topping List
     */
    private fun manageToppingList(toppingList: ArrayList<Topping>) {
        adapter = ToppingAdapter(::onSelectTopping, toppingList)
        rvToppings.adapter = adapter
    }

    /**
     * Uses for add selected topping on pizza
     */
    private fun addToppingOnPizza(topping: Topping) {
        mBinding.twistedPizzaToppingsView.addTopping(topping.image)
    }

    /**
     * Uses for display pizza total price
     */
    private fun managePizzaPrice(pizzaTotalPrice: Int?) {
        pizzaTotalPrice?.let {
            val pizzaPrice = getString(R.string.Rs) + it.toString()
            mBinding.tvPizzaPrice.text = pizzaPrice
        }
    }

    /**
     * Uses for display selected pizza name and image
     */
    private fun managePizza(pizzaData: PizzaData?) {
        with(mBinding)
        {
            pizzaData?.let {
                tvPizza.text = it.name
                tvPizza.paintFlags = tvPizza.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                twistedPizzaToppingsView.setPizzaImage(it.image)
            }
        }
    }

    /**
     * Uses for manage pizza and radioButton text size
     */
    private fun managePizzaSize(pizzaSize: PizzaSize) {
        resizePizza(pizzaSize)
        resizeRadioButtonText(pizzaSize)
    }

    /**
     * Uses for resize pizza
     */
    private fun resizePizza(pizzaSize: PizzaSize) {
        when (pizzaSize) {
            PizzaSize.SMALL -> {
                mBinding.twistedPizzaToppingsView.setPizzaSize(PIZZA_SIZE_SMALL)
            }
            PizzaSize.MEDIUM -> {
                mBinding.twistedPizzaToppingsView.setPizzaSize(PIZZA_SIZE_MEDIUM)
            }
            PizzaSize.LARGE -> {
                mBinding.twistedPizzaToppingsView.setPizzaSize(PIZZA_SIZE_LARGE)
            }
        }
    }

    /**
     * Uses for resize size text with animation
     */
    private fun resizeRadioButtonText(pizzaSize: PizzaSize) {
        with(mBinding) {
            when (pizzaSize) {
                PizzaSize.SMALL -> {
                    rbSmall.animate().scaleX(MAX_SCALE).scaleY(MAX_SCALE).duration =
                        TEXT_ANIMATION_DURATION
                    rbMedium.animate().scaleX(MIN_SCALE).scaleY(MIN_SCALE).duration =
                        TEXT_ANIMATION_DURATION
                    rbLarge.animate().scaleX(MIN_SCALE).scaleY(MIN_SCALE).duration =
                        TEXT_ANIMATION_DURATION
                }
                PizzaSize.MEDIUM -> {
                    rbSmall.animate().scaleX(MIN_SCALE).scaleY(MIN_SCALE).duration =
                        TEXT_ANIMATION_DURATION
                    rbMedium.animate().scaleX(MAX_SCALE).scaleY(MAX_SCALE).duration =
                        TEXT_ANIMATION_DURATION
                    rbLarge.animate().scaleX(MIN_SCALE).scaleY(MIN_SCALE).duration =
                        TEXT_ANIMATION_DURATION
                }
                PizzaSize.LARGE -> {
                    rbSmall.animate().scaleX(MIN_SCALE).scaleY(MIN_SCALE).duration =
                        TEXT_ANIMATION_DURATION
                    rbMedium.animate().scaleX(MIN_SCALE).scaleY(MIN_SCALE).duration =
                        TEXT_ANIMATION_DURATION
                    rbLarge.animate().scaleX(MAX_SCALE).scaleY(MAX_SCALE).duration =
                        TEXT_ANIMATION_DURATION
                }
            }
        }
    }

    /**
     * Uses for add selected topping on pizza
     */
    private fun onSelectTopping(topping: Topping, position: Int) {
        with(mBinding)
        {
            rvToppings.layoutManager?.scrollToPosition(position)
            sampleViewModel.calculatePizzaToppingPrice(topping)
            addToppingOnPizza(topping)
        }
    }
}