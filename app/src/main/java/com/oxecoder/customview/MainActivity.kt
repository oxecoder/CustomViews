package com.oxecoder.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.oxecoder.customview.databinding.ActivityMainBinding
import com.oxecoder.customview.dragadditem.DragAddActivity
import com.oxecoder.customview.progressbar.HorizontalDotActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonHorizontalDot.setOnClickListener { startActivity(HorizontalDotActivity.newIntent(this)) }
        binding.buttonDragAddActivity.setOnClickListener { startActivity(DragAddActivity.newIntent(this)) }
    }
}
