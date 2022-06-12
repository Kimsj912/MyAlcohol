package com.example.lastproj_mp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lastproj_mp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.moveToEditAlcBtn.setOnClickListener{
            moveToEditAlcPage()
        }
        binding.moveToShowHistoryBtn.setOnClickListener{
            moveToShowHistoryPage()
        }
        binding.moveToAddHistoryBtn.setOnClickListener{
            moveToAddHistoryPage()
        }
    }

    fun moveToEditAlcPage() {
        val intent = Intent(this, EditAlcActivity::class.java)
        startActivity(intent)
    }
    fun moveToShowHistoryPage() {
        val intent = Intent(this, ShowHistoryActivity::class.java)
        startActivity(intent)
    }
    fun moveToAddHistoryPage() {
        val intent = Intent(this, AddHistoryActivity::class.java)
        startActivity(intent)
    }
}
