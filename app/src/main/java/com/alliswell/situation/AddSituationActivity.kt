package com.alliswell.situation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.alliswell.patients.AddPatientsActivity
import com.alliswell.patients.R

class AddSituationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_situation)

        val patientIdStr = intent.getStringExtra("patientId")
        val patientIdEntryView =  findViewById<TextView>(R.id.patientIdEntryValue)
        patientIdEntryView.setText(patientIdStr)

        val titleValue = findViewById<EditText>(R.id.situationTitleValue)
        val descriptionValue = findViewById<EditText>(R.id.situationDescriptionValue)
        val saveButton = findViewById<Button>(R.id.situationSaveBtn)
        saveButton.setOnClickListener{
            val replyIntent = Intent()
            if (TextUtils.isEmpty(titleValue.text) && TextUtils.isEmpty(descriptionValue.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val title = titleValue.text.toString()
                val description = descriptionValue.text.toString()
                replyIntent.putExtra(REPLY_TITLE, title)
                replyIntent.putExtra(REPLY_DESCRIPTION, description)
                replyIntent.putExtra(REPLY_PATIENTID, patientIdStr)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val REPLY_TITLE = "com.alliswell.situation.title"
        const val REPLY_DESCRIPTION = "com.alliswell.situation.description"
        const val REPLY_PATIENTID = "com.alliswell.situation.patientId"

    }
}