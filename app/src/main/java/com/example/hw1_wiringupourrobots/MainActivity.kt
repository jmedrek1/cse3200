package com.example.hw1_wiringupourrobots

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.util.Log

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var redBotImg: ImageView
    private lateinit var whiteBotImg: ImageView
    private lateinit var yellowBotImg: ImageView
    private lateinit var messageBox: TextView
    var turnCount = 0
    private lateinit var robotImages : MutableList<ImageView>

    private val robots = listOf(
        Robot(R.string.red_turn, false, R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small),
        Robot(R.string.white_turn, false, R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small),
        Robot(R.string.yellow_turn, false, R.drawable.king_of_detroit_robot_yellow_large, R.drawable.king_of_detroit_robot_yellow_small)
    )

    private val robotViewModel : RobotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redBotImg = findViewById(R.id.red_robot)
        whiteBotImg = findViewById(R.id.white_robot)
        yellowBotImg = findViewById(R.id.yellow_robot)
        messageBox = findViewById(R.id.message_box)

        robotImages = mutableListOf(redBotImg, whiteBotImg, yellowBotImg)

        redBotImg.setOnClickListener { toggleImage() }
        whiteBotImg.setOnClickListener { toggleImage() }
        yellowBotImg.setOnClickListener { toggleImage() }

        Log.d(TAG, "Got a RobotViewModel : $robotViewModel")
    }

    private fun toggleImage() {
        turnCount++
        if (turnCount > 3) {
            turnCount = 1
        }
        setRobotsTurn()
        setRobotsImages()
        updateMessageBox()
    }
    private fun setRobotsTurn() {
        if (robotViewModel.currentTurn == 0)
            return
        for (robot in robots) { robot.myTurn = false }
        robots[robotViewModel.currentTurn - 1].myTurn = true
    }
    private fun setRobotsImages() {
        for (i in robots.indices) {
            if (robots[i].myTurn) {
                robotImages[i].setImageResource(robots[i].largeImgRes)
            } else {
                robotImages[i].setImageResource(robots[i].smallImgRes)
            }
        }
    }
    private fun updateMessageBox() {
        if (robotViewModel.currentTurn == 0)
            return
        messageBox.setText(robots[robotViewModel.currentTurn - 1].turnResourceId)
    }
}