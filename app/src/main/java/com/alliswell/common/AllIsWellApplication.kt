package com.alliswell.common

import android.app.Application
import com.alliswell.data.AppDatabase
import com.alliswell.data.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// Basic Tutorial for Architecture of the APP = https://developer.android.com/codelabs/android-room-with-a-view-kotlin
class AllIsWellApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getInstance(this, applicationScope)}
    val repository by lazy { AppRepository(database.patientDao()) }
}