package com.example.lastproj_mp

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lastproj_mp.databinding.AlcEditViewBinding

class EditAlcActivity : AppCompatActivity() {
    val dbHelper = MyDbHelper.MyDbHelper(this)
    lateinit var binding: AlcEditViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AlcEditViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("술 종류 추가")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // get resources
        val res = getResources()
        val alc_type_arr = res.getStringArray(R.array.alc_type)
        var selectedAlcType = alc_type_arr[0]

        val spinner: Spinner = binding.inputItemView.alcType
        ArrayAdapter.createFromResource(
            this,
            R.array.alc_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : Activity(), AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                Log.d("TAG", "${pos}이 선택됨: ${alc_type_arr[pos]}")
                selectedAlcType = alc_type_arr[pos]
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


        var db = dbHelper.writableDatabase
        val entryArr = mutableListOf(
            MyElement("소주", "참이슬 오리지널", 360, 48, 20.1),
            MyElement("소주", "처음처럼 순한", 360, 48, 16.0),
            MyElement("막걸리", "서울 장수 막걸리", 750, 140, 6.0),
            MyElement("막걸리", "지평 생 막걸리", 700, 140, 5.0),
            MyElement("맥주", "카스 프레시", 500, 500, 4.5),
            MyElement("맥주", "카스 라이트", 500, 500, 4.0),
            MyElement("맥주", "테라", 500, 500, 4.5),
            MyElement("맥주", "하이트 엑스트라 콜드", 500, 500, 4.5),
            MyElement("아메리칸 위스키", "잭다니엘", 375, 30, 40.0),
            MyElement("진", "핸드릭스 진", 700, 30, 44.0),
            MyElement("칵테일", "핸드릭스 진 토닉", 700, 30, 44.0),
        )
        for (entry in entryArr) {
            val values = ContentValues().apply {
                put(MyDbHelper.MyDbHelper.MyEntry.alcType, entry.alcType)
                put(MyDbHelper.MyDbHelper.MyEntry.alcName, entry.alcName)
                put(MyDbHelper.MyDbHelper.MyEntry.bottle, entry.bottle)
                put(MyDbHelper.MyDbHelper.MyEntry.cup, entry.cup)
                put(MyDbHelper.MyDbHelper.MyEntry.percent, entry.percent)
            }
            Log.d("TAG", values.toString())
            val newRowId = db?.insert(MyDbHelper.MyDbHelper.MyEntry.TABLE_NAME, null, values)
            Log.d("TAG", newRowId.toString())
        }
        val getList = dbHelper.selectAll()
        val adapter = MyAdapter(getList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.inputItemView.submit.setOnClickListener {
            if (!checkAllFilled()) return@setOnClickListener;
            db = dbHelper.writableDatabase
            val elem = MyElement(
                selectedAlcType,
                binding.inputItemView.alcName.text.toString(),
                binding.inputItemView.bottle.text.toString().toInt(),
                binding.inputItemView.cup.text.toString().toInt(),
                binding.inputItemView.percent.text.toString().toDouble(),
            )
            val values = ContentValues().apply {
                put(MyDbHelper.MyDbHelper.MyEntry.alcType, elem.alcType)
                put(MyDbHelper.MyDbHelper.MyEntry.alcName, elem.alcName)
                put(MyDbHelper.MyDbHelper.MyEntry.bottle, elem.bottle)
                put(MyDbHelper.MyDbHelper.MyEntry.cup, elem.cup)
                put(MyDbHelper.MyDbHelper.MyEntry.percent, elem.percent)
            }
            Log.d("TAG", values.toString())
            try {
                val newRowId =
                    db?.insertOrThrow(MyDbHelper.MyDbHelper.MyEntry.TABLE_NAME, null, values)
                Log.d("TAG", newRowId.toString())
            } catch (e: SQLiteConstraintException) {
                db?.update(
                    MyDbHelper.MyDbHelper.MyEntry.TABLE_NAME,
                    values,
                    "${MyDbHelper.MyDbHelper.MyEntry.alcType} LIKE ?",
                    arrayOf(elem.alcType)
                )
            }
            val newList = dbHelper.selectAll()
            adapter.setList(newList)
            adapter.notifyDataSetChanged()
            db.close()
        }
        adapter.setItemClickListener(object : MyAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                db = dbHelper.writableDatabase
                db?.delete(
                    MyDbHelper.MyDbHelper.MyEntry.TABLE_NAME,
                    "${MyDbHelper.MyDbHelper.MyEntry.alcName} = ?",
                    arrayOf(adapter.getElement(position).alcName)
                )
                val newList = dbHelper.selectAll()
                Toast.makeText(
                    applicationContext,
                    "${adapter.getElement(position).alcName}이 삭제됨",
                    Toast.LENGTH_SHORT
                ).show()
                adapter.setList(newList)
                adapter.notifyDataSetChanged()
            }
        })
    }

    fun checkAllFilled(): Boolean {
        if (binding.inputItemView.alcName.text.isBlank()) {
            Toast.makeText(applicationContext, "술 이름이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.inputItemView.bottle.text.isBlank()) {
            Toast.makeText(applicationContext, "1병의 용량이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.inputItemView.cup.text.isBlank()) {
            Toast.makeText(applicationContext, "1잔의 용량이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.inputItemView.percent.text.isBlank()) {
            Toast.makeText(applicationContext, "도수가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


}
