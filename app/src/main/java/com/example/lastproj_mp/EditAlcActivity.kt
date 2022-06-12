package com.example.lastproj_mp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.lastproj_mp.database.AppDatabase
import com.example.lastproj_mp.databinding.AlcEditViewBinding

class EditAlcActivity : AppCompatActivity() {
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

        // get resources
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "myDatabase"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        val alcoholDao = db.alcoholDao()
        val getList = alcoholDao.getAll()
        val adapter = MyAlcoholAdapter(getList as MutableList<Alcohol>)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.inputItemView.submit.setOnClickListener {
            if (!checkAllFilled()) return@setOnClickListener;
            val alcName = binding.inputItemView.alcName.text.toString()
            val alcType = selectedAlcType
            val bottle = binding.inputItemView.bottle.text.toString().toInt()
            val cup = binding.inputItemView.cup.text.toString().toInt()
            val percent = binding.inputItemView.percent.text.toString().toDouble()

            alcoholDao.insert(Alcohol(alcName,alcType,bottle,cup, percent))
            Toast.makeText(applicationContext, "${alcName}이 추가되었습니다.", Toast.LENGTH_LONG).show()
            val newList = alcoholDao.getAll()
            adapter.setList(newList as MutableList<Alcohol>)
            adapter.notifyDataSetChanged()
            binding.recyclerView.adapter = adapter

            binding.inputItemView.alcType.setSelection(0)
            binding.inputItemView.alcName.text.clear()
            binding.inputItemView.bottle.text.clear()
            binding.inputItemView.cup.text.clear()
            binding.inputItemView.percent.text.clear()

        }
        try{
            val getList = alcoholDao.getAll()
            val adapter = MyAlcoholAdapter(getList as MutableList<Alcohol>)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            adapter.setItemClickListener(object: MyAlcoholAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    alcoholDao.deleteByName(adapter.getElement(position).alcName)
                    Toast.makeText(applicationContext,"선택한 ${adapter.getElement(position).alcName}이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    val newList = alcoholDao.getAll()
                    adapter.setList(newList as MutableList<Alcohol>)
                    adapter.notifyItemRemoved(position)
                }
            })
            binding.recyclerView.adapter = adapter
        } catch (e:IllegalStateException){
            Toast.makeText(this,"아직 아무런 기록이 없습니다!", Toast.LENGTH_LONG).show()
        }
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

        try{
            val bottle = binding.inputItemView.bottle.text.toString().toInt()
            val cup = binding.inputItemView.cup.text.toString().toInt()
            val percent = binding.inputItemView.percent.text.toString().toDouble()
        } catch (e: NumberFormatException){
            Toast.makeText(applicationContext, "병, 잔, 도수는 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


}
