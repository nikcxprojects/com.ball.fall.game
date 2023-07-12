package com.ball.fall.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ball.fall.game.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            startActivity(Intent(this,ActivityGame::class.java))
        }
        binding.button2.setOnClickListener {
            startActivity(Intent(this,ColorActivity::class.java))
        }
        binding.button3.setOnClickListener {
            startActivity(Intent(this,LevelActivity::class.java))
        }
        binding.imageView.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
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