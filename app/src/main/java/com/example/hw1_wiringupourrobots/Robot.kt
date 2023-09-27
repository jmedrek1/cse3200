package com.example.hw1_wiringupourrobots

data class Robot(
    val messageResource : Int,
    var myTurn : Boolean,
    val largeImgRes : Int,
    val smallImgRes : Int,
    var myEnergy : Int
)
