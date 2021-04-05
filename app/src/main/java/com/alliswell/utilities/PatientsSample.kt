package com.alliswell.utilities

import com.alliswell.data.Patient

class PatientsSample {

    companion object{
        val FIRST_NAME_1 = "Sunanda"
        val LAST_NAME_1 = "Apte"

        val FIRST_NAME_2 = "Manasi"
        val LAST_NAME_2 = "Bhadane"

        val FIRST_NAME_3 = "Upendra"
        val LAST_NAME_3 = "Joshi"
        fun getSamplePatients(): List<Patient> {
            return listOf(
                Patient(FIRST_NAME_1, LAST_NAME_1),
                Patient(FIRST_NAME_2, LAST_NAME_2),
                Patient(FIRST_NAME_3, LAST_NAME_3)
            );
        }
    }

}