package com.samgithiaka.apps.foodforthehungry.data.room

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class AppExecutors (
    private var diskIO: Executor? = null,
    private var networkIO: Executor? = null,
    private var mainThread: Executor? = null){

    fun diskIO(): Executor? {
        return diskIO
    }

    fun mainThread(): Executor? {
        return mainThread
    }

    fun networkIO(): Executor? {
        return networkIO
    }

    companion object {
        private var sInstance: AppExecutors? = null
        private var LOCK = Any()

        fun getInstance(): AppExecutors? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = AppExecutors(
                        Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        MainThreadExecutor()
                    )
                }
            }
            return sInstance
        }

        private class MainThreadExecutor : Executor {
            private var mainThreadHandler = Handler(Looper.getMainLooper())
            override fun execute(command: Runnable) {
                mainThreadHandler.post(command)
            }
        }
    }
}