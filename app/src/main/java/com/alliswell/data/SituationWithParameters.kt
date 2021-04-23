package com.alliswell.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class SituationWithParameters (
    @Embedded val situation: Situation,
    @Relation(
        parentColumn = "situationId",
        entityColumn = "parameterId",
        associateBy = Junction(SituationParameter::class)
    )
    val parameters: List<Parameter>
)