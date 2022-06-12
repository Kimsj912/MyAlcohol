package com.example.lastproj_mp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.lastproj_mp.database.AppDatabase
import com.example.lastproj_mp.databinding.ListAlcBinding
import com.example.lastproj_mp.databinding.ShowHistoryBinding

class ShowHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ShowHistoryBinding.inflate(layoutInflater)
        setContentView (binding.root)
        supportActionBar?.setTitle("술 기록")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // get resources
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "myDatabase"
        ).allowMainThreadQueries().build()
        val historyDao = db.historyDao()
        val getList = historyDao.getAll()
        val adapter = MyHistoryAdapter(getList as MutableList<History>)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}
