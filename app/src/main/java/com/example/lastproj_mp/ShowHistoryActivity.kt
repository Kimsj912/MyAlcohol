package com.example.lastproj_mp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.lastproj_mp.database.AppDatabase
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
        try{
            val getList = historyDao.getAll()
            val adapter = MyHistoryAdapter(getList as MutableList<History>)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            adapter.setItemClickListener(object: MyHistoryAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    historyDao.deleteById(adapter.getElement(position).hid)
                    val newList = historyDao.getAll()
                    Toast.makeText(applicationContext,"선택한 기록이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    adapter.setList(newList as MutableList<History>)
                    adapter.notifyDataSetChanged()
                }
            })
            binding.recyclerView.adapter = adapter
        } catch (e:IllegalStateException){
            Toast.makeText(this,"아직 아무런 기록이 없습니다!", Toast.LENGTH_LONG).show()
        }

    }

}
