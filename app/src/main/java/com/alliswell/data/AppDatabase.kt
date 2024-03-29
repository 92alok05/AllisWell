package com.alliswell.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Database(
    entities = arrayOf(
        Patient::class,
        Situation::class,
        PatientSituation::class,
        Parameter::class,
        SituationParameter::class,
        Detail::class),
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun patientDao(): PatientDao
    abstract fun situationDao(): SituationDao
    abstract fun patientSituationDao(): PatientSituationDao
    abstract fun patientWithSituationsDao(): PatientWithSituationsDao
    abstract fun parameterDao(): ParameterDao
    abstract fun situationParameterDao(): SituationParameterDao
    abstract fun detailDao(): DetailDao
    abstract fun situationWithParameterDao(): SituationWithParameterDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(
                        database.patientDao(),
                        database.situationDao(),
                        database.patientSituationDao(),
                        database.parameterDao(),
                        database.situationParameterDao(),
                        database.detailDao())
                }
            }
        }

        suspend fun populateDatabase(
            patientDao: PatientDao,
            situationDao: SituationDao,
            patientSituationDao: PatientSituationDao,
            parameterDao: ParameterDao,
            situationParameterDao: SituationParameterDao,
            detailDao: DetailDao) {
            // Delete all content here.
            patientDao.deleteAll()

            // Add sample patients
            var patient1 = Patient("Sunanda", "Apte")
            patientDao.insert(patient1)
            var patient2 = Patient("Manasi", "Bhadane")
            patientDao.insert(patient2)

            // Add sample situation
            val situation1 = Situation("Body Movement Recovery", "Trying to do daily activities")
            situationDao.insert(situation1)

            val situation2 = Situation("Covid", "Monitoring breathing metrics")
            situationDao.insert(situation2)

            // Add sample association
            val patientSituation1 = PatientSituation(1,1)
            patientSituationDao.insert(patientSituation1)

            val patientSituation2 = PatientSituation(2, 2)
            patientSituationDao.insert(patientSituation2)

            // Add sample parameter
            val parameter = Parameter("Walking")
            parameterDao.insert(parameter)

            // Add sample association
            val situationParameter = SituationParameter(1, 1)
            situationParameterDao.insert(situationParameter)

            // Add sample detail
            val parser =  SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = parser.parse("2021-04-07")
            val detail = Detail(date!!, 1, 2.2f, 1, 1)
            detailDao.insert(detail)

            val date2 = parser.parse("2021-04-08")
            val detail2 = Detail(date2!!, 1, 2.3f, 1, 1)
            detailDao.insert(detail2)


        }
    }

    companion object {

        val DATABASE_NAME : String = "AllisWell.db"

        // For Singleton instantiation
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, scope).also { INSTANCE = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(AppDatabaseCallback(scope))
                .build()
        }
    }
}