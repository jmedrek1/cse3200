package com.example.hw1_wiringupourrobots

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.util.Log
import androidx.lifecycle.SavedStateViewModelFactory

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var redBotImg: ImageView
    private lateinit var whiteBotImg: ImageView
    private lateinit var yellowBotImg: ImageView
    private lateinit var messageBox: TextView
    private lateinit var robotImages : MutableList<ImageView>

    private val robots = listOf(
        Robot(R.string.red_turn, true, R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small),
        Robot(R.string.white_turn, true, R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small),
        Robot(R.string.yellow_turn, true, R.drawable.king_of_detroit_robot_yellow_large, R.drawable.king_of_detroit_robot_yellow_small)
    )

    private val robotViewModel : RobotViewModel by viewModels {
        SavedStateViewModelFactory(application, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redBotImg = findViewById(R.id.red_robot)
        whiteBotImg = findViewById(R.id.white_robot)
        yellowBotImg = findViewById(R.id.yellow_robot)
        messageBox = findViewById(R.id.message_box)

        robotImages = mutableListOf(redBotImg, whiteBotImg, yellowBotImg)

        setRobotsTurn()
        setRobotsImages()
        updateMessageBox()

        redBotImg.setOnClickListener {toggleImage()}
        whiteBotImg.setOnClickListener {toggleImage()}
        yellowBotImg.setOnClickListener {toggleImage()}

        Log.d(TAG, "Got a RobotViewModel : $robotViewModel")
    }

    private fun toggleImage() {
        robotViewModel.advanceTurn()
        setRobotsTurn()
        setRobotsImages()
        updateMessageBox()
    }
    private fun setRobotsTurn() {
        if (robotViewModel.getTurnCount() == 0)
            return
        for (robot in robots) { robot.myTurn = false }
        robots[robotViewModel.getTurnCount() - 1].myTurn = true
    }
    private fun setRobotsImages() {
        for (i in robots.indices) {
            if (robots[i].myTurn) {
                robotImages[i].setImageResource(robots[i].largeResImgId)
            } else {
                robotImages[i].setImageResource(robots[i].smallResImgId)
            }
        }
    }
    private fun updateMessageBox() {
        if (robotViewModel.getTurnCount() == 0)
            return
        messageBox.setText(robots[robotViewModel.getTurnCount() - 1].turnResourceId)
    }
}