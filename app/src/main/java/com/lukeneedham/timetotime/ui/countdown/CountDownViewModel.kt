package com.lukeneedham.timetotime.ui.countdown

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountDownViewModel : ViewModel() {
    private var timer: CountDownTimer? = null

    private var millisRemainingValue: Long? = null
        set(value) {
            field = value
            val seconds = if (value == null) null else millisToSeconds(value)
            _secondsRemaining.value = seconds
            val minusEnabled = if (value == null) false else value > ADJUST_BY_MILLIS
            _minusEnabled.value = minusEnabled
        }

    private val _secondsRemaining = MutableLiveData<Long?>()
    val secondsRemaining: LiveData<Long?> = _secondsRemaining

    private val _minusEnabled = MutableLiveData<Boolean>()
    val minusEnabled: LiveData<Boolean> = _minusEnabled

    val adjustBySeconds = ADJUST_BY_SECONDS

    init {
        startCountdown(DEFAULT_TIMER)
    }

    fun onPlusClick() {
        val oldMillis = millisRemainingValue ?: 0
        val newMillis = oldMillis + ADJUST_BY_MILLIS
        startCountdown(newMillis)
    }

    fun onMinusClick() {
        val oldMillis = millisRemainingValue ?: 0
        val newMillis = oldMillis - ADJUST_BY_MILLIS
        startCountdown(newMillis)
    }

    private fun startCountdown(millis: Long) {
        timer?.cancel()

        val timer = object : CountDownTimer(millis, 1) {
            override fun onTick(millisUntilFinished: Long) {
                millisRemainingValue = millisUntilFinished
            }

            override fun onFinish() {}
        }
        this.timer = timer
        timer.start()
    }

    private fun millisToSeconds(millis: Long) = millis / 1000

    companion object {
        private const val MILLIS_IN_SECOND = 1000L
        private const val ADJUST_BY_SECONDS = 10
        private const val ADJUST_BY_MILLIS = ADJUST_BY_SECONDS * MILLIS_IN_SECOND
        private const val DEFAULT_TIMER = 100 * MILLIS_IN_SECOND
    }
}
