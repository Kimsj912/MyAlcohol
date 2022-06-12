package com.example.lastproj_mp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.lastproj_mp.database.AppDatabase
import com.example.lastproj_mp.databinding.InputHistoryBinding
import java.util.*


class AddHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = InputHistoryBinding.inflate(layoutInflater)
        setContentView (binding.root)
        supportActionBar?.setTitle("술 기록")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // get resources
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "myDatabase"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        val historyDao = db.historyDao()
        val alcoholDao = db.alcoholDao()


        // date
        var gc = GregorianCalendar(TimeZone.getTimeZone("Asia/Seoul"))
        var selectedDate ="${gc.get(GregorianCalendar.YEAR)}.${gc.get(GregorianCalendar.MONTH)+1}.${gc.get(GregorianCalendar.DAY_OF_MONTH)}"
        val datePicker = binding.date
        datePicker.init(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH), gc.get(GregorianCalendar.DAY_OF_MONTH)) {
            view, year, monthOfYear, dayOfMonth -> selectedDate= "${year}.${monthOfYear+1}.${dayOfMonth}"
        }

        // alc_name
        val arr: List<String> = alcoholDao.getNameAll()
        var selectedAlcName = arr[0]

        val spinner: Spinner = binding.alcName
        var adapter:ArrayAdapter<String> = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : Activity(), AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                Log.d("TAG", "${pos}이 선택됨: ${arr[pos]}")
                selectedAlcName = arr[pos]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }
        }


        binding.submit.setOnClickListener{
            val drunk = binding.drunk.text.toString().toInt()
            Log.d("TAG: ", "${selectedAlcName}, ${selectedDate}, ${drunk}, ${alcoholDao.getPercentFromAlcType(selectedAlcName)}")
            historyDao.insert(History(0,selectedDate,selectedAlcName,drunk, String.format("%.2f",drunk*alcoholDao.getPercentFromAlcType(selectedAlcName)*0.01*0.7947)))
            val getList = historyDao.getAll()
            Log.d("TAG", getList.toString())
            Toast.makeText(this, "${selectedDate}에 ${selectedAlcName}을 ${drunk}ml을 마셨습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
