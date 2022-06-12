package com.example.lastproj_mp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.lastproj_mp.database.AppDatabase
import com.example.lastproj_mp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "myDatabase"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        val alcoholDao = db.alcoholDao()
        alcoholDao.insert(Alcohol("참이슬 오리지널", "소주",360, 48, 20.1))
        alcoholDao.insert(Alcohol("처음처럼 순한", "소주", 360, 48, 16.0))
        alcoholDao.insert(Alcohol("서울 장수 막걸리","막걸리", 750, 140, 6.0))
        alcoholDao.insert(Alcohol("지평 생 막걸리", "막걸리", 700, 140, 5.0))
        alcoholDao.insert(Alcohol("카스 프레시", "맥주", 500, 500, 4.5))
        alcoholDao.insert(Alcohol("카스 라이트", "맥주", 500, 500, 4.0))
        alcoholDao.insert(Alcohol("테라", "맥주", 500, 500, 4.5))
        alcoholDao.insert(Alcohol("하이트 엑스트라 콜드", "맥주", 500, 500, 4.5))
        alcoholDao.insert(Alcohol("잭다니엘", "양주", 375, 30, 40.0))
        alcoholDao.insert(Alcohol("핸드릭스 진", "양주", 700, 30, 44.0))
        alcoholDao.insert(Alcohol("핸드릭스 진 토닉", "칵테일", 700, 30, 44.0))


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
