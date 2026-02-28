package com.mystery.xbanner

interface IScheduleModule {

    fun startTimer(start: Boolean, autoTime: Long = 3)
    fun startTask()
    fun stopTask()
}