package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime
import org.intellij.lang.annotations.PrintFormat

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var button30:Button
    lateinit var button60:Button
    lateinit var button90:Button
    lateinit var button120:Button

    var timeToCountDownInMs = 5000L
    val timeTicks = 1000L
    var Countdownrunning=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



       button120 = findViewById<Button>(R.id.button120)
       button120.setOnClickListener(){
           timeToCountDownInMs = 7200000
           updateCountDownDisplay(timeToCountDownInMs)
       }
       button90 = findViewById<Button>(R.id.button90)
       button90.setOnClickListener(){
           timeToCountDownInMs = 5400000
           updateCountDownDisplay(timeToCountDownInMs)
       }
       button60 = findViewById<Button>(R.id.button60)
       button60.setOnClickListener(){
           timeToCountDownInMs = 3600000
           updateCountDownDisplay(timeToCountDownInMs)
       }
       button30 = findViewById<Button>(R.id.button30)
       button30.setOnClickListener(){
           timeToCountDownInMs = 1800000
           updateCountDownDisplay(timeToCountDownInMs)
       }
       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
        if (!Countdownrunning){
           startCountDown(it)
           Countdownrunning=true
           return@setOnClickListener}
       Toast.makeText(this@MainActivity,"Countdown already running!", Toast.LENGTH_SHORT).show()


       }
       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                Countdownrunning=false
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}