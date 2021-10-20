package com.example.viewpagerview.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewpagerview.adapter.CustomPagerAdapter
import com.example.viewpagerview.adapter.MusicAdapter
import com.example.viewpagerview.data.MPage
import com.example.viewpagerview.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : BaseActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val permission = Manifest.permission.READ_EXTERNAL_STORAGE
    val FLAG_REQ_STORAGE = 99
    val FLAG_PERM_STORAGE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(isPermitted()) {
            startProcess()
         //   setTitle("곡수 : $i")
        }else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), FLAG_REQ_STORAGE)
        }
    }

    fun startProcess() {// 1. 데이터 로드
        val list = loadData() // listOf("노래", "음악가", "앨범", "재생목록")
      //  val list =  listOf("노래")//, "음악가", "앨범", "재생목록")
        // 2. 아답터 생성
        val pagerAdapter = CustomPagerAdapter(list, applicationContext)
      //  val pagerAdapter2 = MusicAdapter()
        // 3. 아답터와 뷰페이저 연결
        binding.viewPager.adapter = pagerAdapter
     //   binding.viewPager.adapter = pagerAdapter2

        //4. 탭 타이틀 목록 생성
        val titles = listOf("노래", "음악가", "앨범", "재생목록")
        // 5. 탭 에이우웃과 뷰페이저 연결
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles.get(position)
        }.attach()
        /*
        val adapter = MusicAdapter()
        adapter.musicList.addAll(getMusicList())
        //   adapter.albumList.addAll(getAlbumLIst())

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this) */
    }

    fun loadData() : List<MPage> {
        val mPageList = mutableListOf<MPage>()
        mPageList.add(MPage(1, "흐림"))
        mPageList.add(MPage(2, "맑음"))
        mPageList.add(MPage(3, "구름"))
        mPageList.add(MPage(4, "비"))
        return mPageList
    }

    fun isPermitted() : Boolean { // 책에는 checkPermission, 조건이 하나일 때 한줄로
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == FLAG_REQ_STORAGE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // startProcess()
            } else {
                Toast.makeText(this, "권한 요청 실행해야지 앱 실행", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    override fun permissionGranted(requestCode: Int) {
        when(requestCode) {
        //    FLAG_PERM_STORAGE -> // startProcess()
        }
    }

    override fun permissionDenied(requestCode: Int) {
        when(requestCode) {
            FLAG_PERM_STORAGE -> {
                Toast.makeText(this, "공용 저장소 권한을 승인해야 앱을 사용 가능합니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}


