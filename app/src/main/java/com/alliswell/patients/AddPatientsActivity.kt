package com.alliswell.patients

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AddPatientsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patients)

        val firstNameView = findViewById<TextView>(R.id.editTextTextPersonName)
        val lastNameView = findViewById<TextView>(R.id.editTextTextPersonName2)
        val saveButton = findViewById<Button>(R.id.button)

        saveButton.setOnClickListener{
            val replyIntent = Intent()
            if (TextUtils.isEmpty(firstNameView.text) && TextUtils.isEmpty(lastNameView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val firstName = firstNameView.text.toString()
                val lastName = lastNameView.text.toString()
                replyIntent.putExtra(REPLY_FIRST_NAME, firstName);
                replyIntent.putExtra(REPLY_LAST_NAME, lastName);
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }


    companion object {
        const val REPLY_FIRST_NAME = "com.alliswell.patients.firstname"
        const val REPLY_LAST_NAME = "com.alliswell.patients.lastname"
    }

}