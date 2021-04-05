package com.alliswell.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Patient(
        @PrimaryKey(autoGenerate = true) val patientId: Int,
        @ColumnInfo(name = "first_name") val firstName: String?,
        @ColumnInfo(name = "last_name") val lastName: String?
) {
        constructor(firstName: String?, lastName: String?) : this(0, firstName, lastName)
}
