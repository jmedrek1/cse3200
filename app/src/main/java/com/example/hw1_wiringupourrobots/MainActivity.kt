package com.example.hw1_wiringupourrobots

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.util.Log
import android.view.View
import android.widget.Button

//private const val EXTRA_ROBOT_ENERGY = "com.bignerdranch.android.robot.current_robot_energy"

class MainActivity : AppCompatActivity() {

    private lateinit var redBotImage: ImageView
    private lateinit var whiteBotImage: ImageView
    private lateinit var yellowBotImage: ImageView
    private lateinit var messageBox: TextView
    private lateinit var reward_button : Button

    private lateinit var robotImages : MutableList<ImageView>

    private var turnCount = 0
    private val robots = listOf(
        Robot(R.string.red_robot_mssg, false,
            R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small, 0),
        Robot(R.string.white_robot_mssg, false,
            R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small, 0),
        Robot(R.string.yellow_robot_mssg, false,
            R.drawable.king_of_detroit_robot_yellow_large, R.drawable.king_of_detroit_robot_yellow_small, 0)
    )

    private val robotViewModel : RobotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redBotImage = findViewById(R.id.red_robot)
        whiteBotImage = findViewById(R.id.white_robot)
        yellowBotImage = findViewById(R.id.yellow_robot)
        messageBox = findViewById(R.id.message_box)
        reward_button = findViewById(R.id.purchase_rewards_button)

        robotImages = mutableListOf(redBotImage, whiteBotImage, yellowBotImage)

        redBotImage.setOnClickListener { toggleImage() }
        whiteBotImage.setOnClickListener { toggleImage() }
        yellowBotImage.setOnClickListener { toggleImage() }
        reward_button.setOnClickListener {view : View ->
//            Toast.makeText(this, "Going to make a purchase!", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, RobotPurchaseActivity::class.java)
//            intent.putExtra(EXTRA_ROBOT_ENERGY, robots[turnCount - 1].myEnergy)
            val intent = RobotPurchaseActivity.newIntent(this, robots[turnCount - 1].myEnergy)
            startActivity(intent)
        }
    }


    private fun toggleImage() {
        turnCount++
        if(turnCount > 3)
            turnCount = 1
        updateMessageBox()
        setRobotsTurn()
        setRobotImages()
    }

    private fun updateMessageBox(){
        messageBox.setText(robots[turnCount - 1].messageResource)
    }

    private fun setRobotsTurn(){
        for(robot in robots){robot.myTurn = false}
        robots[turnCount - 1].myTurn = true
        robots[turnCount - 1].myEnergy++
    }

    private fun setRobotImages(){
        for(indy in robots.indices){
            if(robots[indy].myTurn){
                robotImages[indy].setImageResource(robots[indy].largeImgRes)
            }else{
                robotImages[indy].setImageResource(robots[indy].smallImgRes)
            }
        }
    }
}