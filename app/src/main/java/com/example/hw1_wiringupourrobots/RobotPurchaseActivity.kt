package com.example.hw1_wiringupourrobots

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.selects.select
import org.w3c.dom.Text
import java.util.Random

private const val EXTRA_ROBOT_ENERGY = "com.bignerdranch.android.robot.current_robot_energy"

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

    private lateinit var robot_energy_available : TextView
    private var robot_energy = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        reward_button_left = findViewById(R.id.buy_reward_left)
        reward_button_middle = findViewById(R.id.buy_reward_middle)
        reward_button_right = findViewById(R.id.buy_reward_right)
        reward_cost_left = findViewById(R.id.reward_left_cost)
        reward_cost_middle = findViewById(R.id.reward_middle_cost)
        reward_cost_right = findViewById(R.id.reward_right_cost)
        robot_energy_available = findViewById(R.id.robot_energy_to_spend)

        // selecting 3 random rewards out of 7 possible
        val random = Random()
        val selectedRewards = mutableMapOf<String, Int>()

        while (selectedRewards.size < 3) {
            val randomReward = allRewards.keys.random()

            if (!selectedRewards.containsKey(getString(randomReward))) {
                selectedRewards[getString(randomReward)] = allRewards[randomReward] ?: 0
            }
        }
        val sortedRewards = selectedRewards.entries.sortedBy { it.key } // ascending left to right

        reward_button_left.text = sortedRewards[0].key
        reward_button_middle.text = sortedRewards[1].key
        reward_button_right.text = sortedRewards[2].key

        reward_cost_left.text = sortedRewards[0].value.toString()
        reward_cost_middle.text = sortedRewards[1].value.toString()
        reward_cost_right.text = sortedRewards[2].value.toString()

        robot_energy = intent.getIntExtra(EXTRA_ROBOT_ENERGY, 0)
        robot_energy_available.setText(robot_energy.toString())

        reward_button_left.setOnClickListener{view : View ->
            makePurchase(sortedRewards[0].value)
        }
        reward_button_middle.setOnClickListener{view : View ->
            makePurchase(sortedRewards[1].value)
        }
        reward_button_right.setOnClickListener{view : View ->
            makePurchase(sortedRewards[2].value)
        }


    }
    companion object {
        fun newIntent(packageContext : Context, robot_energy : Int) : Intent { // package context is the activity starting this one
            return Intent(packageContext, RobotPurchaseActivity::class.java).apply {
                putExtra(EXTRA_ROBOT_ENERGY, robot_energy)
            }
        }

        const val EXTRA_LAST_PURCHASE = "com.example.hw1_wiringupourrobots.LAST_PURCHASE"
        const val EXTRA_UPDATED_ENERGY = "com.example.hw1_wiringupourrobots.UPDATED_ENERGY"
    }
    //TODO: fix makePurchase to show toast properly for new rewards
    private fun makePurchase(costOfPurchase : Int){
        if (robot_energy >= costOfPurchase){
            val s1 = when {
                costOfPurchase == 1 -> getString(R.string.reward_a)
                costOfPurchase == 2 -> getString(R.string.reward_b)
                costOfPurchase == 3 -> getString(R.string.reward_c)
                else -> getString(R.string.error_reward)
            }
            val s2 = getString(R.string.purchased)
            val s3 = s1 + " " + s2
            robot_energy -= costOfPurchase
            robot_energy_available.setText(robot_energy.toString())
            Toast.makeText(this, s3, Toast.LENGTH_SHORT).show()

            // pass lastPurchase and new energy back to MainActivity
            val intent = Intent()
            intent.putExtra(EXTRA_LAST_PURCHASE, costOfPurchase)
            intent.putExtra(EXTRA_UPDATED_ENERGY, robot_energy)
            setResult(Activity.RESULT_OK, intent)
        }else{
            Toast.makeText(this, R.string.insufficient, Toast.LENGTH_SHORT).show()
        }
    }
}

