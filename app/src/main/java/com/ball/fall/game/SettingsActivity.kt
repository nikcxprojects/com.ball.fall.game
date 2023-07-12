package com.ball.fall.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.ball.fall.game.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private var sound = true
    private var vibr = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sound = getSharedPreferences("prefs", MODE_PRIVATE).getBoolean("sound",true)
        vibr = getSharedPreferences("prefs", MODE_PRIVATE).getBoolean("vibr",true)
        change()
        binding.button.setOnClickListener {
            sound = !sound
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putBoolean("sound",sound).apply()
            change()
        }
        binding.button2.setOnClickListener {
            vibr = !vibr
            getSharedPreferences("prefs", MODE_PRIVATE).edit().putBoolean("vibr",vibr).apply()
            change()
        }
        binding.back2.setOnClickListener {
            finish()
        }
    }

    private fun change() {
        var drawable  = ResourcesCompat.getDrawable(resources,if(sound) R.drawable.on else R.drawable.off,theme)
        drawable!!.setBounds(0,0,70,70)
        binding.button.text = if(sound) "Sounds On" else "Sounds off"
        binding.button.setCompoundDrawables(null,null,drawable,null)
        var drawable1  = ResourcesCompat.getDrawable(resources,if(vibr) R.drawable.on else R.drawable.off,theme)
        drawable1!!.setBounds(0,0,70,70)
        binding.button2.text = if(vibr) "Vibration On" else "Vibration off"
        binding.button2.setCompoundDrawables(null,null,drawable1,null)
    }
}