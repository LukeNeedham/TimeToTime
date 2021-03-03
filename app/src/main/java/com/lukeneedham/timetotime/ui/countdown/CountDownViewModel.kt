/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    private val _showPinchHint = MutableLiveData<Boolean>()
    val showPinchHint: LiveData<Boolean> = _showPinchHint

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

    fun onClockPinch() {
        _showPinchHint.value = false
    }

    fun restart() {
        startCountdown(DEFAULT_TIMER)
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
        private const val DEFAULT_TIMER = 30 * MILLIS_IN_SECOND
    }
}
