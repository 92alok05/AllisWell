package com.alliswell.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val patientDao: PatientDao,
    private val situationDao: SituationDao,
    private val patientSituationDao: PatientSituationDao,
    private val patientWithSituationsDao: PatientWithSituationsDao,
    private val parameterDao: ParameterDao,
    private val situationParameterDao: SituationParameterDao,
    private val detailDao: DetailDao,
    private val situationWithParameterDao: SituationWithParameterDao
    ) {

    val patients = patientDao.getAll()

    @WorkerThread
    suspend fun insert(patient: Patient) {
        patientDao.insert(patient)
    }

    @WorkerThread
    suspend fun insert(situation: Situation): Long {
        return situationDao.insert(situation)
    }

    @WorkerThread
    suspend fun insert(patientSituation: PatientSituation) {
        patientSituationDao.insert(patientSituation)
    }

    @WorkerThread
    suspend fun insert(parameter: Parameter) : Long{
        return parameterDao.insert(parameter)
    }

    @WorkerThread
    suspend fun insert(situationParameter: SituationParameter) {
        return situationParameterDao.insert(situationParameter)
    }

    @WorkerThread
    suspend fun insert(detail: Detail) {
        return detailDao.insert(detail)
    }

    @WorkerThread
    fun getPatientWithSituations(patientId: Int): Flow<List<PatientWithSituations>> {
        return patientWithSituationsDao.getPatientWithSituations(patientId);
    }

    @WorkerThread
    fun getDetailsForPatientAndSituation(patientId: Int, situationId: Int): Flow<List<Detail>> {
        return detailDao.getAllValuesForPatientAndSituation(patientId, situationId)
    }

    @WorkerThread
    fun getParametersForSituation(situationId: Int): Flow<List<SituationWithParameters>> {
        return situationWithParameterDao.getSituationWithParameters(situationId)
    }
}