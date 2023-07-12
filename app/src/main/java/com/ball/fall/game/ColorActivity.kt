package com.ball.fall.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.ball.fall.game.databinding.ActivityColorBinding

class ColorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityColorBinding
    private var ind = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ind = getSharedPreferences("prefs", MODE_PRIVATE).getInt("color",0)
        choose()
        binding.red.setOnClickListener {
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putInt("color",0).apply()
            ind = 0
            choose()
        }
        binding.violet.setOnClickListener {
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putInt("color",1).apply()
            ind = 1
            choose()
        }
        binding.blue.setOnClickListener {
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putInt("color",2).apply()
            ind = 2
            choose()
        }
        binding.orange.setOnClickListener {
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putInt("color",3).apply()
            ind = 3
            choose()
        }
        binding.back.setOnClickListener {
            finish()
        }
        binding.imageView.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }

    private fun choose() {
        when(ind) {
            0 -> {
                binding.blue.setColorFilter(R.color.black)
                binding.violet.setColorFilter(R.color.black)
                binding.orange.setColorFilter(R.color.black)
                binding.ballChoose.setImageResource(R.drawable.red_ball)
                binding.red.setColorFilter(android.R.color.transparent)
            }
            1 -> {
                binding.blue.setColorFilter(R.color.black)
                binding.red.setColorFilter(R.color.black)
                binding.orange.setColorFilter(R.color.black)
                binding.ballChoose.setImageResource(R.drawable.violet_ball)
                binding.violet.setColorFilter(android.R.color.transparent)
            }
            2 -> {
                binding.red.setColorFilter(R.color.black)
                binding.violet.setColorFilter(R.color.black)
                binding.orange.setColorFilter(R.color.black)
                binding.ballChoose.setImageResource(R.drawable.blue_ball)
                binding.blue.setColorFilter(android.R.color.transparent)
            }
            3 -> {
                binding.blue.setColorFilter(R.color.black)
                binding.violet.setColorFilter(R.color.black)
                binding.red.setColorFilter(R.color.black)
                binding.ballChoose.setImageResource(R.drawable.orange_ball)
                binding.orange.setColorFilter(android.R.color.transparent)
            }
        }
    }
}