package com.example.temporizador

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.sql.Time

@SuppressLint("Registered")
class MainActivity : AppCompatActivity() {
    lateinit var mEditTextInput: EditText
    lateinit var countDownText: TextView
    lateinit var setTextButton : Button
    lateinit var beginButton: Button
    lateinit var resetButton: Button
    var StartTimeInMillis : Long = 600000
    lateinit var countDownTimer: CountDownTimer
    var timeLeftMilliseconds: Long = 600000
    private var timerRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mEditTextInput = findViewById(R.id.edit_text_inputs)
        countDownText = findViewById(R.id.countdown_text)

        beginButton = findViewById(R.id.beginButton)
        resetButton = findViewById(R.id.resetButton)
        setTextButton = findViewById(R.id.setTextBtn)

        beginButton.setOnClickListener {

            startStop()
        }
        resetButton.setOnClickListener{

            resetTimer()
        }
        setTextButton.setOnClickListener{
         var input : String = mEditTextInput.text.toString()
            if(input.length == 0){

                Toast.makeText(this,"Please insert time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var millisecondsInput : Long = input.toLong() * 60000
            if(millisecondsInput == 0L){
                Toast.makeText(this,"time can not be '0'", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            setTime(millisecondsInput)
            mEditTextInput.setText("")
            updateTimer()
        }
    }


    fun startStop() {
        if (timerRunning) {
            stoptimer()
        } else if (!timerRunning) {
            startTimer()
        }
    }

    fun setTime(milliseconds : Long){

        timeLeftMilliseconds = milliseconds

    }

    fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftMilliseconds, 1000) {
            override fun onTick(l: Long) {

                timeLeftMilliseconds = l
                updateTimer()
            }
            override fun onFinish() {

            }
        }.start()
        beginButton.text = "Pause"
        timerRunning = true
    }

    fun stoptimer() {
        countDownTimer.cancel()
        beginButton.text = "Begin"
        timerRunning = false

    }

    private fun resetTimer() {
//        timeLeftMilliseconds = StartTimeInMillis
//        updateTimer()
//        resetButton.setVisibility(View.INVISIBLE)
//        beginButton.setVisibility(View.VISIBLE)
    }

    fun updateTimer() {
        var minutes: Int = (timeLeftMilliseconds / 60000).toInt()
        var seconds: Int = (timeLeftMilliseconds % 60000 / 1000).toInt()
        var timeLeftText: String
        timeLeftText = "" + minutes
        timeLeftText += ":"
        if (seconds < 10) timeLeftText += "0"
        timeLeftText += seconds
        countDownText.text = timeLeftText
    }
}
