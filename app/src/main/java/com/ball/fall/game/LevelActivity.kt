package com.ball.fall.game

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ball.fall.game.databinding.ActivityLevelBinding

class LevelActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLevelBinding
    private var ind = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ind = getSharedPreferences("prefs", MODE_PRIVATE).getInt("level",0)
        choose()
        binding.button.setOnClickListener {
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putInt("level",0).apply()
            ind = 0
            choose()
        }
        binding.button2.setOnClickListener {
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putInt("level",1).apply()
            ind = 1
            choose()
        }
        binding.button3.setOnClickListener {
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putInt("level",2).apply()
            ind = 2
            choose()
        }
        binding.back2.setOnClickListener {
            finish()
        }
        binding.imageView.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }

    private fun choose() {
        when(ind) {
            0 -> {
                binding.button2.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
                binding.button3.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
                binding.button.backgroundTintList = ColorStateList.valueOf(resources.getColor(android.R.color.transparent))
            }
            1 -> {
                binding.button.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
                binding.button3.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
                binding.button2.backgroundTintList = ColorStateList.valueOf(resources.getColor(android.R.color.transparent))
            }
            2 -> {
                binding.button2.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
                binding.button.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
                binding.button3.backgroundTintList = ColorStateList.valueOf(resources.getColor(android.R.color.transparent))
            }
        }
    }


    override fun onResume() {
        super.onResume()
        when(getSharedPreferences("prefs", MODE_PRIVATE).getInt("color",0)) {
            0 -> {
                binding.ball.setImageResource(R.drawable.red_ball)
            }
            1 -> {
                binding.ball.setImageResource(R.drawable.violet_ball)
            }
            2 -> {
                binding.ball.setImageResource(R.drawable.blue_ball)
            }
            3 -> {
                binding.ball.setImageResource(R.drawable.orange_ball)
            }
        }
    }
}