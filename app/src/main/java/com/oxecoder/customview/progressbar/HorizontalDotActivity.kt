package com.oxecoder.customview.progressbar

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oxecoder.customview.R

class HorizontalDotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_dot)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, HorizontalDotActivity::class.java)
    }
}
