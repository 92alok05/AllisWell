package com.alliswell.details.ui.journal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.alliswell.patients.AddPatientsActivity
import com.alliswell.patients.R
import kotlinx.android.synthetic.main.activity_add_journal_detail.*

class AddJournalDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_journal_detail)

        btnDetailsSave.setOnClickListener{
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTextDate.text) ||
                TextUtils.isEmpty(editTextParamId.text) ||
                TextUtils.isEmpty(editTextTextParamValue.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(REPLY_PARAM_ID, editTextParamId.text.toString());
                replyIntent.putExtra(REPLY_PARAM_VALUE, editTextTextParamValue.text.toString());
                replyIntent.putExtra(REPLY_DATE, editTextDate.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val REPLY_PARAM_ID = "com.alliswell.details.paramname"
        const val REPLY_PARAM_VALUE = "com.alliswell.details.paramvalue"
        const val REPLY_DATE = "com.alliswell.details.detaildate"
    }
}