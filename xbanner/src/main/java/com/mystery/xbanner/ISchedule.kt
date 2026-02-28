package com.mystery.xbanner

interface ISchedule<T> {

    fun schedulerRefresh(engine: ScheduleRefresh)

    fun schedulerSuccess(t: T)
}