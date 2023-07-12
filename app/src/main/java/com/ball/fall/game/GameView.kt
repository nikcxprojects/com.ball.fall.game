package com.ball.fall.game


import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.media.MediaPlayer
import android.os.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import java.util.*
import kotlin.math.abs


class GameView(ctx: Context, attributeSet: AttributeSet): SurfaceView(ctx,attributeSet) {

    var bg = BitmapFactory.decodeResource(ctx.resources,R.drawable.bg)
    var line = BitmapFactory.decodeResource(ctx.resources,R.drawable.line)
    var ball = BitmapFactory.decodeResource(ctx.resources,R.drawable.red_ball)
    var ball1 = BitmapFactory.decodeResource(ctx.resources,R.drawable.violet_ball)
    var ball2 = BitmapFactory.decodeResource(ctx.resources,R.drawable.blue_ball)
    var ball3 = BitmapFactory.decodeResource(ctx.resources,R.drawable.orange_ball)
    var ball4 = BitmapFactory.decodeResource(ctx.resources,R.drawable.gray_ball)

    private val player = MediaPlayer.create(ctx,R.raw.udar)


    public var score = 0
    var level = ctx.getSharedPreferences("prefs",Context.MODE_PRIVATE).getInt("level",0)
    var sound = ctx.getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("sound",true)
    var speed = arrayOf(4,8,16)
    private val random = Random()
    private var millis = 0
    var bx = 0
    private var listener: EndListener? = null
    private var paintB: Paint = Paint(Paint.DITHER_FLAG)

    init {
        line = Bitmap.createScaledBitmap(line,(line.width/2.5).toInt(),line.height/2,true)
        ball = Bitmap.createScaledBitmap(ball,ball.width/3,ball.height/3,true)
        ball1 = Bitmap.createScaledBitmap(ball1,ball1.width/3,ball1.height/3,true)
        ball2 = Bitmap.createScaledBitmap(ball2,ball2.width/3,ball2.height/3,true)
        ball3 = Bitmap.createScaledBitmap(ball3,ball3.width/3,ball3.height/3,true)
        ball4 = Bitmap.createScaledBitmap(ball4,ball4.width/3,ball4.height/3,true)
        holder.addCallback(object : SurfaceHolder.Callback{
            override fun surfaceCreated(holder: SurfaceHolder) {

            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                val canvas = holder.lockCanvas()
                if(canvas!=null) {
                    bx = (canvas.width/2f-ball.width/2f).toInt()
                    draw(canvas)
                    holder.unlockCanvasAndPost(canvas)
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                paused = true
            }

        })
        val updateThread = Thread {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (!paused) {
                        update.run()
                        millis ++
                    }
                }
            }, 500, 16)
        }

        updateThread.start()
    }
    var code = -1f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action) {
            MotionEvent.ACTION_UP -> {
                code = -1f
            }
            MotionEvent.ACTION_DOWN -> {
                code = event.x
            }
        }
        postInvalidate()
        return true
    }

    var paused = false
    var list = mutableListOf<Model>()
    var delta = 8
    val update = Runnable{
        var isEnd = false
        var sc = false
        if(paused) return@Runnable
        try {
            val canvas = holder.lockCanvas()
            if(code>=0) {
                if(code>bx) bx+=delta
                else bx-=delta
            }
            if(bx<=-getCurrentBall().width) bx = canvas.width
            if(bx>=getCurrentBall().width+canvas.width) bx = 0
            var i = 0
            while(i<list.size) {
                Log.d("TAG","$i")
                list[i].y+=speed[level]
                if(abs(list[i].x-bx)<=getCurrentBall().width && abs(list[i].y-(canvas.height-200f-line.height/2f))<=getCurrentBall().height) {
                     if(list[i].cur) {
                         score++
                         sc = true
                         if(sound) player.start()
                         list.removeAt(i)
                     } else {
                         isEnd = true
                         break
                     }
                } else if(list[i].y>canvas.height+getCurrentBall().height) {
                    list.removeAt(i)
                } else i++
            }
            while(list.size<4) {
                list.add(Model(random.nextInt(canvas.width).toFloat(),-1f*getCurrentBall().height,random.nextBoolean()))
            }
            canvas.drawBitmap(bg,0f,0f,paintB)
            canvas.drawBitmap(line,0f,canvas.height-200f,paintB)
            for(i in list) {
                canvas.drawBitmap(if(i.cur) getCurrentBall() else ball4,i.x,i.y,paintB)
            }
            canvas.drawBitmap(getCurrentBall(),bx.toFloat(),canvas.height-200f-line.height/2f,paintB)
            holder.unlockCanvasAndPost(canvas)
             if(isEnd) {
                Log.d("TAG","END")
                togglePause()
                if(listener!=null) listener!!.end()
            }
            if(sc) {
                listener?.score(score)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setEndListener(list: EndListener) {
        this.listener = list
    }
    fun togglePause() {
        paused = !paused
    }
    companion object {
        interface EndListener {
            fun end();
            fun score(score: Int);
        }
        data class Model(var x: Float, var y: Float, var cur: Boolean)
    }
    val b = ctx.getSharedPreferences("prefs",Context.MODE_PRIVATE).getInt("color",0)

    private fun getCurrentBall(): Bitmap {
       return when(b) {
           0 -> ball
           1 -> ball1
           2 -> ball2
           else -> ball3
       }
    }
}