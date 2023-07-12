package com.ball.fall.game

import android.content.Context
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.ball.fall.game.databinding.ActivityGameBinding
import java.util.*

class ActivityGame : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    var count = 60
    var score = 0
    var isEnd = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var vibr = getSharedPreferences("prefs", Context.MODE_PRIVATE).getBoolean("vibr",true)
        binding.game.setEndListener(object : GameView.Companion.EndListener {
            override fun end() {
                endGame()
            }

            override fun score(score: Int) {

              runOnUiThread {  this@ActivityGame.score = score
                  binding.textView4.text = "${this@ActivityGame.score}"
                  if(vibr) {
                      if(Build.VERSION.SDK_INT>=31) {
                          (applicationContext.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager).vibrate(
                              CombinedVibration.createParallel(
                                  VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)))
                      } else {
                          (applicationContext.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                      }
                  }
              }
            }

        })
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if(!binding.game.paused && count>=0) {
                   runOnUiThread {
                        if(count<=10) {
                            binding.textView3.setTextColor(resources.getColor(R.color.red,theme))
                        }
                       binding.textView3.text = String.format("%02d:%02d",count/60,count%60)
                       count--
                   }
                }
                if(count<=0) {
                    endGame()
                    stopLockTask()
                }
            }
        }, 500, 1000)
        binding.back2.setOnClickListener {
            finish()
        }
    }
    private fun endGame() {
        if(isEnd) return
        isEnd = true
        var dialog = EndDialog(score)
        dialog.show(supportFragmentManager,"TAG")
    }
}