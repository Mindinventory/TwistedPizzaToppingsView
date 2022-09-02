<a href="https://www.mindinventory.com/?utm_source=gthb&utm_medium=repo&utm_campaign=twistedPizzaToppings"><img src="https://github.com/Sammindinventory/MindInventory/blob/main/Banner.png"></a>

# TwistedPizzaToppingsView  [![](https://jitpack.io/v/Mindinventory/TwistedPizzaToppingsView.svg)](https://jitpack.io/#Mindinventory/TwistedPizzaToppingsView) ![](https://img.shields.io/github/languages/top/Mindinventory/TwistedPizzaToppingsView) ![](https://img.shields.io/github/license/mindinventory/TwistedPizzaToppingsView)

## Overview
Simple view which allows options to customize your pizza toppings and size as per your choice.


![ezgif com-gif-maker (10)](/media/feature.gif)

## Features
- Android 12 support 
- Easy setup
- Pizza image customization
- Pizza size customization
- Topping size customization
- Topping quantity customization
- Pizza serving plate customization
- Animation customization
- Orientation support

## Usage
### Dependencies
- **Step 1: Add the JitPack repository in your project build.gradle file**
```bash
allprojects {
	    repositories {
		    ...
		    maven { url 'https://jitpack.io' }
	    }
    }
```
**or**

**If Android studio version is Arctic Fox or higher then add it in your settings.gradle**

```bash
dependencyResolutionManagement {
  		repositories {
       		...
       		maven { url 'https://jitpack.io' }
   		}
   }
``` 

- **Step 2: Add the dependency in your app module build.gradle file**
```bash
dependencies {
		    ...
	        implementation 'com.github.Mindinventory:TwistedPizzaToppingsView:x.x.x'
	}
```
### Implementation
- **Step 1: Add TwistedPizzaToppingsView in your xml and customize attributes**
 ```bash
    <com.mindinventory.twistedpizzatoppings.TwistedPizzaToppingsView
        android:id="@+id/twistedPizzaToppingsView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:animationDuration="800"
        app:pizzaAnimation="true"
        app:pizzaImage="@drawable/pizza5"
        app:pizzaSize="large"
        app:pizzaImageMargin="@dimen/_20sdp"
        app:plateImage="@drawable/plate"
        app:toppingQuantity="12"
        app:toppingViewSize="@dimen/_15sdp" />
```

**Step 2: Add Topping on pizza**
```bash
 fun addTopping(resId: Int)
```
**Customize properties of some attributes programmatically**
```bash
fun setPizzaImage(resId: Int)
```
```bash
fun setPizzaImage(uri: Uri)
```
```bash
fun setPizzaSize(@IntRange(from = 1, to = 100) size: Int)
```
```bash
fun setPlateImage(resId: Int)
```
```bash
fun setPlateImage(uri: Uri)
```
```bash
fun setAnimationDuration(@IntRange(from = 1, to = 1000) value: Long)
```
### Appearance

|            Attribute         |          Type              | Description                                        | Default      |
| -----------------------------| -------------------------- | -------------------------------------------------- | :-----------:|
| **animationDuration**        | long                       | Customize animation duration (From 1 to 1000 ms)   | 800ms        |
| **pizzaAnimation**           | boolean                    | Enable pizza & plate view animation                | true         |
| **pizzaSize**                | enum SMALL, MEDIUM & LARGE | Set pizza view size                                | LARGE        |
| **pizzaImage**               | int or Uri                 | Set pizza image                                    | ic_pizza.png |
| **pizzaImageMargin**         | int                        | Set pizza image margin                             | _20dp        |
| **plateImage**               | int or Uri                 | Set pizza plate image                              | plate.png    |
| **toppingQuantity**          | int                        | Customize adding topping quantity (maximum 12)     | 12           |
| **toppingViewSize**          | int                        | Customize topping view size                        | _15dp        |



## Guideline for contributors
Contribution towards our repository is always welcome, we request contributors to create a pull request to the develop branch only.

## Guideline to report an issue/feature request
It would be great for us if the reporter can share the below things to understand the root cause of the issue.

- Library version
- Code snippet
- Logs if applicable
- Device specification like (Manufacturer, OS version, etc)
- Screenshot/video with steps to reproduce the issue

## Requirements
- minSdkVersion >= 21
- Androidx

## Library used
- [sdp](https://github.com/intuit/sdp)
- [ssp](https://github.com/intuit/ssp)


# LICENSE!

TwistedPizzaToppingsView is [MIT-licensed](/LICENSE).

## Let us know!
If you use our open-source libraries in your project, please make sure to credit us and Give a star to www.mindinventorycom

<p><h4>Please feel free to use this component and Let us know if you are interested to building Apps or Designing Products.</h4>
<a href="https://www.mindinventory.com/contact-us.php?utm_source=gthb&utm_medium=repo&utm_campaign=twistedPizzaToppings">
<img src="https://github.com/Sammindinventory/MindInventory/blob/main/hirebutton.png" width="203" height="43"  alt="app development">
</a>

