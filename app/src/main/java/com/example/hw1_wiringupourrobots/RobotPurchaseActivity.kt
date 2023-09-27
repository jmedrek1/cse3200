package com.example.hw1_wiringupourrobots

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
private const val EXTRA_ROBOT_ENERGY = "com.bignerdranch.android.robot.current_robot_energy"

class RobotPurchaseActivity : AppCompatActivity() {

    private lateinit var reward_button_a : Button
    private lateinit var reward_button_b : Button
    private lateinit var reward_button_c : Button
    private lateinit var robot_energy_available : TextView
    private var robot_energy = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robot_purchase)

        reward_button_a = findViewById(R.id.buy_reward_a)
        reward_button_b = findViewById(R.id.buy_reward_b)
        reward_button_c = findViewById(R.id.buy_reward_c)
        robot_energy_available = findViewById(R.id.robot_energy_to_spend)

        //robot_energy = 2 // temporary hardcoded for testing
        robot_energy = intent.getIntExtra(EXTRA_ROBOT_ENERGY, 0)
        robot_energy_available.setText(robot_energy.toString())

        reward_button_a.setOnClickListener{view : View ->
            makePurchase(1)
        }
        reward_button_b.setOnClickListener{view : View ->
            makePurchase(2)
        }
        reward_button_c.setOnClickListener{view : View ->
            makePurchase(3)
        }
    }
    companion object {
        fun newIntent(packageContext : Context, robot_energy : Int) : Intent { // package context is the activity starting this one
            return Intent(packageContext, RobotPurchaseActivity::class.java).apply {
                putExtra(EXTRA_ROBOT_ENERGY, robot_energy)
            }
        }
    }
    private fun makePurchase(costOfPurchase : Int){
        if (robot_energy >= costOfPurchase){
            val s1 = when {
                costOfPurchase == 1 -> getString(R.string.reward_a)
                costOfPurchase == 2 -> getString(R.string.reward_a)
                costOfPurchase == 3 -> getString(R.string.reward_a)
                else -> getString(R.string.error_reward)
            }
            val s2 = getString(R.string.purchased)
            val s3 = s1 + " " + s2
            robot_energy -= costOfPurchase
            robot_energy_available.setText(robot_energy.toString())
            Toast.makeText(this, s3, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, R.string.insufficient, Toast.LENGTH_SHORT).show()
        }
    }
}

