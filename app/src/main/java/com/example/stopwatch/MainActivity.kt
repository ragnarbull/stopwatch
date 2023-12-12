package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isRunning = false
    private var timer = 0
    private val handler: Handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {
            timer++
            val hours = timer / 360000
            val minutes = (timer % 360000) / 6000
            val seconds = (timer % 6000) / 100
            val milliseconds = timer % 100

            if (hours >= 100) {
                // Reset timer when 100 hrs have elapsed
                timer = 0
            }

            val time = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds)
            binding.timerText.text = time
            handler.postDelayed(this, 10)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.startBtn.setOnClickListener {
            startTimer()
        }
        binding.stopBtn.setOnClickListener {
            stopTimer()
        }
        binding.resetBtn.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (!isRunning) {
            handler.postDelayed(runnable, 10)
            isRunning = true

            binding.startBtn.isEnabled = false
            binding.stopBtn.isEnabled = true
            binding.resetBtn.isEnabled = true
        }
    }

    private fun stopTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable)
            isRunning = false

            binding.startBtn.isEnabled = true
            binding.startBtn.text = "Resume"
            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = true
        }
    }

    private fun resetTimer() {
        stopTimer()

        timer = 0
        binding.timerText.text = "00:00:00:00"

        binding.startBtn.isEnabled = true
        binding.stopBtn.isEnabled = false
        binding.resetBtn.isEnabled = false
    }
}