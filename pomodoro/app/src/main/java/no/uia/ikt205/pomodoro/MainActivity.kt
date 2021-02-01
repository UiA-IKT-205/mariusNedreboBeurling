package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime
import org.intellij.lang.annotations.PrintFormat

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var seekBar: SeekBar
    lateinit var timer2:CountDownTimer
    lateinit var edit: EditText
    var timeToCountDownInMs2 = 5000L
    var timeToCountDownInMs = 5000L
    val timeTicks = 1000L
    val timeTicks2 = 1000L
    var Countdownrunning=false
    var pausecountdownrun=false
    var rep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edit = findViewById<EditText>(R.id.EDITTEXT)
        seekBar = findViewById(R.id.SEEKBAR)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                timeToCountDownInMs = progress * 600L
                updateCountDownDisplay(timeToCountDownInMs)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                updateCountDownDisplay(timeToCountDownInMs)

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
               updateCountDownDisplay(timeToCountDownInMs)

            }

        })
        seekBar = findViewById(R.id.SEEKBAR2)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                timeToCountDownInMs2 = progress * 600L
                updateCountDownDisplay(timeToCountDownInMs2)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                updateCountDownDisplay(timeToCountDownInMs2)

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                updateCountDownDisplay(timeToCountDownInMs2)

            }

        })



       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
        if (!Countdownrunning && !pausecountdownrun){
           rep = edit.text.toString().toInt()
            startCountDown(it)
           return@setOnClickListener}
           Toast.makeText(this@MainActivity,"Countdown already running!", Toast.LENGTH_SHORT).show()


       }
       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                if (!pausecountdownrun && rep >0){
                    startCountDown2(v)
                }
               rep --
               Countdownrunning = false

            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
                Countdownrunning =true
                pausecountdownrun =false
            }
        }

        timer.start()
    }

    fun startCountDown2(v: View){

        timer2 = object : CountDownTimer(timeToCountDownInMs2,timeTicks2) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Pause ferdig er ferdig", Toast.LENGTH_SHORT).show()
                if (!Countdownrunning && rep > 0)
                    startCountDown(v)
                    pausecountdownrun = false

            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
                pausecountdownrun = true
                Countdownrunning = false
            }
        }

        timer2.start()
    }
    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}