package com.example.hw1_wiringupourrobots

import android.app.Activity
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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.SavedStateViewModelFactory

//private const val EXTRA_ROBOT_ENERGY = "com.bignerdranch.android.robot.current_robot_energy"

class MainActivity : AppCompatActivity() {

    private lateinit var redBotImage: ImageView
    private lateinit var whiteBotImage: ImageView
    private lateinit var yellowBotImage: ImageView
    private lateinit var messageBox: TextView
    private lateinit var reward_button : Button

    private lateinit var robotImages : MutableList<ImageView>

//    private var turnCount = 0
    private val robots = listOf(
        Robot(R.string.red_robot_mssg, true,
            R.drawable.king_of_detroit_robot_red_large, R.drawable.king_of_detroit_robot_red_small, 0),
        Robot(R.string.white_robot_mssg, true,
            R.drawable.king_of_detroit_robot_white_large, R.drawable.king_of_detroit_robot_white_small, 0),
        Robot(R.string.yellow_robot_mssg, true,
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

        // restore the state of the robots on rotation
        updateMessageBox()
        setRobotsTurn()
        setRobotImages()

        redBotImage.setOnClickListener { toggleImage() }
        whiteBotImage.setOnClickListener { toggleImage() }
        yellowBotImage.setOnClickListener { toggleImage() }
        reward_button.setOnClickListener {view : View ->
            if (robotViewModel.getTurnCount() != -1) {
                val intent = RobotPurchaseActivity.newIntent(
                    this,
                    robots[robotViewModel.getTurnCount()].myEnergy,
                    robots[robotViewModel.getTurnCount()].largeImgRes,
                    robots[robotViewModel.getTurnCount()].purchases
                )
                purchaseLauncher.launch(intent)
            }
        }
    }

    private val purchaseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val purchases = result.data?.getStringArrayListExtra(RobotPurchaseActivity.EXTRA_PURCHASES)
            if (purchases != null) {
                robots[robotViewModel.getTurnCount()].purchases.clear() // removes duplicates
                robots[robotViewModel.getTurnCount()].purchases.addAll(purchases) // add back purchase history
            }

            val updatedEnergy = result.data?.getIntExtra(RobotPurchaseActivity.EXTRA_UPDATED_ENERGY, 0)
            if (updatedEnergy != null) {
                robots[robotViewModel.getTurnCount()].myEnergy = updatedEnergy
            }
        }
    }

    private fun toggleImage() {
        robotViewModel.advanceTurn()
        updateMessageBox()
        setRobotsTurn()
        setRobotImages()
    }

    private fun updateMessageBox(){
        if (robotViewModel.getTurnCount() == -1)
            return
        messageBox.setText(robots[robotViewModel.getTurnCount()].messageResource)
    }

    private fun setRobotsTurn(){
        if (robotViewModel.getTurnCount() == -1)
            return
        for(robot in robots){robot.myTurn = false}
        robots[robotViewModel.getTurnCount()].myTurn = true
        robots[robotViewModel.getTurnCount()].myEnergy += 1

        showToastForPurchases(robots[robotViewModel.getTurnCount()])
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

    private fun showToastForPurchases(robot: Robot) {
        if (robot.myEnergy > 0) {
            var purchasesResource = robot.purchases
            // only show the toast if a last purchase exists
            if (purchasesResource.isNotEmpty()) {
                val purchasesMessage = buildPurchasesToastMessage(purchasesResource)
                Toast.makeText(this, purchasesMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buildPurchasesToastMessage(purchases: List<String>): String {
        val purchasesNoPrefix = purchases.map { it.removePrefix("Reward ") }
        val purchasedMessage = purchasesNoPrefix.joinToString(", ")
        return "Purchased: " + purchasedMessage
    }
}