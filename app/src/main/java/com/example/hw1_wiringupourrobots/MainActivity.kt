package com.example.hw1_wiringupourrobots

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var redImg : ImageView
    private lateinit var yellowImg : ImageView
    private lateinit var whiteImg : ImageView
    private lateinit var messageBox : TextView
    private lateinit var clockwiseBtn : ImageButton
    private lateinit var counterBtn : ImageButton

    private lateinit var robotImages : MutableList<ImageView>

    var turnCount = 0
    private val robots = listOf(
        Robot(R.string.red_robot_msg, false, R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small),
        Robot(R.string.yellow_robot_msg, false, R.drawable.king_of_detroit_robot_yellow_large, R.drawable.king_of_detroit_robot_yellow_small),
        Robot(R.string.white_robot_msg, false, R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redImg = findViewById(R.id.red_robot)
        yellowImg = findViewById(R.id.yellow_robot)
        whiteImg = findViewById(R.id.white_robot)

        messageBox = findViewById(R.id.message_box)

        clockwiseBtn = findViewById(R.id.button_clockwise)
        counterBtn = findViewById(R.id.button_counter)

        robotImages = mutableListOf(redImg, whiteImg, yellowImg)

        clockwiseBtn.setOnClickListener { toggleImage(counterClockwise = false) }
        counterBtn.setOnClickListener { toggleImage(counterClockwise = true) }
    }

    private fun toggleImage(counterClockwise: Boolean) {
        if (counterClockwise) {
            turnCount++
            if (turnCount > 3) {
                turnCount = 1
            }
        } else {
            turnCount--
            if (turnCount < 1) {
                turnCount = 3
            }
        }

        updateMessageBox()
        setRobotsTurn()
        setRobotImages()
    }

    private fun updateMessageBox() {
        messageBox.setText(robots[turnCount - 1].messageResource)
    }

    private fun setRobotsTurn() {
        for (robot in robots) { robot.myTurn = false }
        robots[turnCount - 1].myTurn = true
    }

    private fun setRobotImages() {
        for (i in robots.indices) {
            if (robots[i].myTurn) {
                robotImages[i].setImageResource(robots[i].largeImgRes)
            }
            else {
                robotImages[i].setImageResource(robots[i].smallImgRes)
            }
        }
    }
}