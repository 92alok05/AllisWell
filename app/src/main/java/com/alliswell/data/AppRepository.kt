package com.alliswell.data

import androidx.annotation.WorkerThread

class AppRepository(private val patientDao: PatientDao) {
    val patients = patientDao.getAll()

    @WorkerThread
    suspend fun insert(patient: Patient) {
        patientDao.insert(patient)
    }
}