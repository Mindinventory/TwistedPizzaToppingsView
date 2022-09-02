package com.mindinventory.twistedpizzatoppings

import android.content.Context
import android.content.res.TypedArray
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.annotation.IntRange
import androidx.constraintlayout.widget.ConstraintLayout
import com.mindinventory.twistedpizzatoppings.databinding.TwistedPizzaToppingsViewBinding
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


/**
 * This class uses for pizza customization (like, set pizza image, change pizza size, add topping, animate topping etc.)
 */
class TwistedPizzaToppingsView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var inflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private var _mBinding: TwistedPizzaToppingsViewBinding? = null
    private val mBinding get() = _mBinding!!

    private var pizzaActualSize: Pair<Int, Int> = Pair(0, 0)
    private var pizzaRadius: Int = 0
    private var toppingViewSize: Int = 0
    private var pizzaCenterPoint: Pair<Float, Float> = Pair(0F, 0F)
    private var centerWoodenPlate: Pair<Float, Float> = Pair(0F, 0F)
    private var animationDuration: Long = 0
    private var toppingQuantity: Int = 0
    private var pizzaAnimation: Boolean = true
    private var pizzaCustomizedSize: Int = 0
    private var pizzaImage: Int = 0
    private var pizzaImageUri: String = ""
    private var plateImage: Int = 0
    private var pizzaImageMargin: Int = 0

    init {
        _mBinding = TwistedPizzaToppingsViewBinding.inflate(inflater, this, true)

        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.TwistedPizzaToppingsView)

        toppingViewSize = typedArray.getDimension(
            R.styleable.TwistedPizzaToppingsView_toppingViewSize,
            resources.getDimension(com.intuit.sdp.R.dimen._15sdp)
        ).toInt()

        animationDuration =
            typedArray.getInt(R.styleable.TwistedPizzaToppingsView_animationDuration, 800).toLong()

        toppingQuantity =
            typedArray.getInt(R.styleable.TwistedPizzaToppingsView_toppingQuantity, 12)

        pizzaAnimation =
            typedArray.getBoolean(R.styleable.TwistedPizzaToppingsView_pizzaAnimation, true)

        pizzaCustomizedSize =
            typedArray.getInt(R.styleable.TwistedPizzaToppingsView_pizzaSize, 80)

        pizzaImage =
            typedArray.getResourceId(
                R.styleable.TwistedPizzaToppingsView_pizzaImage,
                R.drawable.ic_pizza
            )

        plateImage =
            typedArray.getResourceId(
                R.styleable.TwistedPizzaToppingsView_plateImage,
                R.drawable.plate
            )

        pizzaImageMargin = typedArray.getDimension(
            R.styleable.TwistedPizzaToppingsView_pizzaImageMargin,
            resources.getDimension(com.intuit.sdp.R.dimen._20sdp)
        ).toInt()


        initPizzaLayoutListener()
        setPizzaImage(pizzaImage)
        setPizzaSize(pizzaCustomizedSize)
        setPlateImage(plateImage)
        typedArray.recycle()
    }

    /**
     * Uses for set observer to a callback to be invoked when the global layout state or the
     * visibility of views within the view tree changes.
     */
    private fun initPizzaLayoutListener() {
        with(mBinding)
        {
            (ivPizza.layoutParams as LayoutParams).apply {
                this.setMargins(
                    pizzaImageMargin,
                    pizzaImageMargin,
                    pizzaImageMargin,
                    pizzaImageMargin
                )
                ivPizza.layoutParams = this
            }

            ivPizza.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    ivPizza.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val width: Int = ivPizza.width
                    val height: Int = ivPizza.height

                    pizzaActualSize = Pair(width, height)

                    val x = ivPizza.x
                    val y = ivPizza.y

                    val centerX = x + (width / 2)
                    val centerY = y + (height / 2)

                    pizzaRadius = (width / 2) - (toppingViewSize / 2)
                    pizzaCenterPoint = Pair(centerX, centerY)
                }
            })

            ivWoodenPlate.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    ivWoodenPlate.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val width: Int = ivWoodenPlate.width
                    val height: Int = ivWoodenPlate.height

                    val x = ivWoodenPlate.x
                    val y = ivWoodenPlate.y

                    val centerX = x + (width / 2)
                    val centerY = y + (height / 2)

                    centerWoodenPlate = Pair(centerX, centerY)
                }
            })
        }
    }

    /**
     * Uses for find random position on defined circle view
     * @param r: Radius of the circle
     * @param n number of random points
     * @param centerPoint centerPoint of the circle
     */
    private fun getRandomPosition(
        r: Int,
        n: Int,
        centerPoint: Pair<Float, Float>
    ): ArrayList<Pair<Double, Double>> {
        val positionList = arrayListOf<Pair<Double, Double>>()

        for (i in 0 until n) {

            // Get Angle in radians
            val theta: Double = 2 * Math.PI * uniform()

            // Get length from center
            val len: Double = r * sqrt(uniform())

            // Add point to results.
            positionList.add(
                Pair(
                    len * cos(theta) + centerPoint.first,
                    len * sin(theta) + centerPoint.second
                )
            )
        }
        return positionList
    }

    /**
     * Uses for get random number
     */
    private fun uniform(): Double {
        return Math.random()
    }

    /**
     * Uses for get X position of topping
     */
    private fun TwistedPizzaToppingsViewBinding.getToppingsYPosition(): ArrayList<Float> {
        return arrayListOf(
            (cnsPizza.translationY / 2),
            (cnsPizza.translationY / 2),
            (cnsPizza.translationY),
            (cnsPizza.translationY / 2),
            (cnsPizza.height).toFloat(),
            (cnsPizza.height / 2).toFloat(),
            (cnsPizza.height / 2) + (cnsPizza.height / 4).toFloat(),
            (cnsPizza.height / 2).toFloat(),
            (cnsPizza.height).toFloat(),
            (cnsPizza.height).toFloat(),
            (cnsPizza.height).toFloat(),
            (cnsPizza.height / 2) - (cnsPizza.height / 4).toFloat()
        )
    }

    /**
     * Uses for get Y position of topping
     */
    private fun TwistedPizzaToppingsViewBinding.getToppingsXPosition(): ArrayList<Float> {
        return arrayListOf(
            (cnsPizza.width / 2) - (cnsPizza.width / 4).toFloat(),
            cnsPizza.translationX / 2,
            cnsPizza.translationX,
            (cnsPizza.width / 2).toFloat(),
            cnsPizza.translationX,
            cnsPizza.translationX / 2,
            cnsPizza.translationX,
            (cnsPizza.width).toFloat(),
            (cnsPizza.width / 2).toFloat(),
            (cnsPizza.width / 2) + (cnsPizza.width / 4).toFloat(),
            (cnsPizza.width).toFloat(),
            (cnsPizza.width).toFloat()
        )
    }

    /**
     * Uses for set pizza image
     * @param resId the resource identifier of the drawable
     */
    fun setPizzaImage(resId: Int) {
        pizzaImage = resId
        with(mBinding) {
            cnsPizza.removeAllViews()
            ivPizza.setImageResource(resId)
        }
    }

    /**
     * Uses for set pizza image
     * @param uri fileUri
     */
    fun setPizzaImage(uri: Uri) {
        pizzaImageUri = uri.toString()
        with(mBinding) {
            cnsPizza.removeAllViews()
            ivPizza.setImageURI(uri)
        }
    }

    /**
     * Uses for set pizza size
     * @param size pizzaSize
     */
    fun setPizzaSize(@IntRange(from = 1, to = 100) size: Int = 100) {
        pizzaCustomizedSize = size
        var scalePercentage: Float = (size / 100F)
        if (scalePercentage > 1.0) {
            scalePercentage = 1.0f
        }
        val rotationAngle = (0..360).random().toFloat()
        mBinding.cnsPizza.animate().apply {
            this.scaleX(scalePercentage)
            this.scaleY(scalePercentage)
            if (pizzaAnimation) {
                this.rotation(rotationAngle)
            }
            this.duration = animationDuration
        }

        mBinding.ivPizza.animate().apply {
            this.scaleX(scalePercentage)
            this.scaleY(scalePercentage)
            if (pizzaAnimation) {
                this.rotation(rotationAngle)
            }
            this.duration = animationDuration
        }

        mBinding.ivWoodenPlate.animate().apply {
            this.scaleX(scalePercentage)
            this.scaleY(scalePercentage)
            if (pizzaAnimation) {
                this.rotation(rotationAngle)
            }
            this.duration = animationDuration
        }
    }

    /**
     * Uses for add topping
     * @param resId the resource identifier of the drawable
     */
    fun addTopping(resId: Int) {
        with(mBinding) {
            val arrayOfPositionXSource = getToppingsXPosition()
            val arrayOfPositionYSource = getToppingsYPosition()

            val pizzaPosition =
                getRandomPosition(
                    pizzaRadius,
                    toppingQuantity,
                    pizzaCenterPoint
                )

            for (i in 0 until toppingQuantity) {
                val iv = ImageView(context)//Animated view for topping
                iv.layoutParams = android.view.ViewGroup.LayoutParams(
                    toppingViewSize,
                    toppingViewSize
                )

                iv.x = arrayOfPositionXSource[i]
                iv.y = arrayOfPositionYSource[i]

                iv.rotation = (0..180).random().toFloat()

                iv.setImageResource(resId)

                iv.scaleX = 1F
                iv.scaleY = 1F

                val newData = pizzaPosition[i]
                val toppingPizzaPositionX = newData.first - (toppingViewSize / 2)
                val toppingPizzaPositionY = newData.second - (toppingViewSize / 2)
                iv.animate()
                    .translationX(toppingPizzaPositionX.toFloat())
                    .translationY(toppingPizzaPositionY.toFloat()).scaleX(1F)
                    .scaleY(1F)
                    .duration = animationDuration

                val rotationAngle = (0..360).random().toFloat()
                cnsPizza.animate().rotation(rotationAngle).duration = animationDuration
                mBinding.ivPizza.animate().rotation(rotationAngle).duration = animationDuration
                cnsPizza.addView(iv)
            }
        }
    }

    /**
     * Uses for set plate image
     * @param resId the resource identifier of the drawable
     */
    fun setPlateImage(resId: Int) {
        plateImage = resId
        mBinding.ivWoodenPlate.setImageResource(resId)
    }

    /**
     * Uses for set plate image
     * @param uri fileUri
     */
    fun setPlateImage(uri: Uri) {
        mBinding.ivWoodenPlate.setImageURI(uri)
    }

    /**
     * Uses for set animation duration
     * @param value animationDuration
     */
    fun setAnimationDuration(@IntRange(from = 1, to = 1000) value: Long) {
        this.animationDuration = value
    }
}