package com.origogi.myapplication.log

import android.util.Log


object Logger {
    private val LOG_TAG = "ORIGOGI"
    private val FORMAT = "[%s]: %s"

    fun v(msg: String?) {
        Log.v(LOG_TAG, String.format(FORMAT, callerInfo, msg))
    }

    fun d(msg: String?) {
        Log.d(LOG_TAG, String.format(FORMAT, callerInfo, msg))
    }

    fun l(msg: String?) {
        Log.i(LOG_TAG, String.format(FORMAT, callerInfo, msg))
    }

    fun w(msg: String?) {
        Log.d(LOG_TAG, String.format(FORMAT, callerInfo, msg))
    }

    fun e(msg: String?) {
        Log.e(LOG_TAG, String.format(FORMAT, callerInfo, msg))
    }

    private val callerInfo: String?
        private get() {
            val elements =
                Exception().stackTrace
            val className = elements[2].className
            return className.substring(
                className.lastIndexOf(".") + 1,
                className.length
            ) + "_" + elements[2].lineNumber
        }
}
