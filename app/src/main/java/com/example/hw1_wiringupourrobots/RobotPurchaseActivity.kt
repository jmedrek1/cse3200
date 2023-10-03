package com.example.hw1_wiringupourrobots

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels

private const val EXTRA_CURRENT_ENERGY = "com.example.hw1_wiringupourrobots.CURRENT_ENERGY"

private val allRewards = mapOf(
    R.string.reward_a to 1,
    R.string.reward_b to 2,
    R.string.reward_c to 3,
    R.string.reward_d to 3,
    R.string.reward_e to 4,
    R.string.reward_f to 4,
    R.string.reward_g to 7
)

class RobotPurchaseActivity : AppCompatActivity() {

    private lateinit var reward_button_left : Button
    private lateinit var reward_button_middle : Button
    private lateinit var reward_button_right : Button

    private lateinit var reward_cost_left : TextView
    private lateinit var reward_cost_middle : TextView
    private lateinit var reward_cost_right : TextView

    private lateinit var robot_image: ImageView
//    private var robot_image_res = R.drawable.king_of_detroit_robot_white_large

    private lateinit var robot_energy_available : TextView
//    private var robot_energy = 0

    private val robotViewModel : RobotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        reward_button_left = findViewById(R.id.buy_reward_left)
        reward_button_middle = findViewById(R.id.buy_reward_middle)
        reward_button_right = findViewById(R.id.buy_reward_right)

        reward_cost_left = findViewById(R.id.reward_left_cost)
        reward_cost_middle = findViewById(R.id.reward_middle_cost)
        reward_cost_right = findViewById(R.id.reward_right_cost)

        robot_image = findViewById(R.id.robot_purchase_image)

        robot_energy_available = findViewById(R.id.robot_energy_to_spend)

        // selecting 3 random rewards out of 7 possible, ascending left to right
        val sortedRewards = selectRandomRewards(mutableMapOf())

        reward_button_left.text = sortedRewards[0].key
        reward_button_middle.text = sortedRewards[1].key
        reward_button_right.text = sortedRewards[2].key

        reward_cost_left.text = sortedRewards[0].value.toString()
        reward_cost_middle.text = sortedRewards[1].value.toString()
        reward_cost_right.text = sortedRewards[2].value.toString()

        robotViewModel.setRobotImageRes(intent.getIntExtra(EXTRA_ROBOT_IMAGE, R.drawable.king_of_detroit_robot_white_large))
        robot_image.setImageResource(robotViewModel.getRobotImageRes())

        robotViewModel.setRobotEnergy(intent.getIntExtra(EXTRA_CURRENT_ENERGY, 0))
        robot_energy_available.text = robotViewModel.getRobotEnergy().toString()

        reward_button_left.setOnClickListener{view : View ->
            makePurchase(sortedRewards[0].key, sortedRewards[0].value)
        }
        reward_button_middle.setOnClickListener{view : View ->
            makePurchase(sortedRewards[1].key, sortedRewards[1].value)
        }
        reward_button_right.setOnClickListener{view : View ->
            makePurchase(sortedRewards[2].key, sortedRewards[2].value)
        }

    }
    companion object {
        fun newIntent(packageContext : Context, robot_energy : Int, robot_image_res : Int, robot_purchases : List<String>) : Intent { // package context is the activity starting this one
            return Intent(packageContext, RobotPurchaseActivity::class.java).apply {
                putExtra(EXTRA_CURRENT_ENERGY, robot_energy)
                putExtra(EXTRA_ROBOT_IMAGE, robot_image_res)
                putStringArrayListExtra(EXTRA_PURCHASES, ArrayList(robot_purchases))
            }
        }

        const val EXTRA_PURCHASES = "com.example.hw1_wiringupourrobots.PURCHASES"
        const val EXTRA_UPDATED_ENERGY = "com.example.hw1_wiringupourrobots.UPDATED_ENERGY"
        const val EXTRA_ROBOT_IMAGE = "com.example.hw1_wiringupourrobots.ROBOT_IMAGE"
    }

    private fun makePurchase(rewardPurchased : String, costOfPurchase : Int){
        if (robotViewModel.getRobotEnergy() >= costOfPurchase){
            val purchasesList = intent.getStringArrayListExtra(EXTRA_PURCHASES) ?: ArrayList()
            val s1 = when {
                rewardPurchased != "" -> rewardPurchased
                else -> getString(R.string.error_reward)
            }
            if (!purchasesList.contains(s1)) {
                val s2 = getString(R.string.purchased)
                val s3 = s1 + " " + s2

                robotViewModel.setRobotEnergy(robotViewModel.getRobotEnergy() - costOfPurchase)
                robot_energy_available.text = robotViewModel.getRobotEnergy().toString()
                Toast.makeText(this, s3, Toast.LENGTH_SHORT).show()

                // pass lastPurchase and new energy back to MainActivity
                purchasesList.add(s1)

                val intent = Intent()
                intent.putStringArrayListExtra(EXTRA_PURCHASES, purchasesList)
                intent.putExtra(EXTRA_UPDATED_ENERGY, robotViewModel.getRobotEnergy())
                setResult(Activity.RESULT_OK, intent)
            } else {
                Toast.makeText(this, R.string.already_purchased, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, R.string.insufficient, Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectRandomRewards(selectedRewards : MutableMap<String, Int>): List<MutableMap.MutableEntry<String, Int>> {
        while (selectedRewards.size < 3) {
            val randomReward = allRewards.keys.random()

            if (!selectedRewards.containsKey(getString(randomReward))) {
                selectedRewards[getString(randomReward)] = allRewards[randomReward] ?: 0
            }
        }
        return selectedRewards.entries.sortedBy { it.key } // ascending left to right
    }
}

