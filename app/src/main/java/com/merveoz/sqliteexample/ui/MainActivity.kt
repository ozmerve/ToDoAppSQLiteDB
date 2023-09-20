package com.merveoz.sqliteexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.merveoz.sqliteexample.common.viewBinding
import com.merveoz.sqliteexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding (ActivityMainBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}