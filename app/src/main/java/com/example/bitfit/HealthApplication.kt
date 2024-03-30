package com.example.bitfit

import android.app.Application


class HealthApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}