package com.example.viewpagerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpagerview.activities.BaseActivity
import com.example.viewpagerview.data.MPage
import com.example.viewpagerview.data.Music
import com.example.viewpagerview.databinding.ActivityMainBinding
import com.example.viewpagerview.databinding.ItemViewpagerBinding
import com.google.android.material.internal.ContextUtils.getActivity
import com.example.viewpagerview.activities.MainActivity




class CustomPagerAdapter(val mPageList: List<MPage>, context: Context): RecyclerView.Adapter<CustomPagerAdapter.Holder>() {

    val mContext = context
    var i =0
    inner class Holder(val binding: ItemViewpagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setText(mPage:MPage) {
            with(binding) {
                textDay.text = "${mPage.day} 일"
                textWeather.text = mPage.weather
            }
        }

        //  val musicList = mutableListOf<Music>()
        var mediaPlayer: MediaPlayer? = null
        val uriLocal = Uri.parse("android.resource://com.inu.contentresolver/drawable/mp3logo")
        fun startProcess() {
            val adapter = MusicAdapter()

            adapter.musicList.addAll(getMusicList())
            binding.recyclerView2.adapter = adapter
            binding.recyclerView2.layoutManager = LinearLayoutManager(mContext)
        }

        fun getMusicList(): List<Music> {
            // 컨텐트 리졸버로 음원 목록 가져오기
            // 1. 데이터 테이블 주소
            val musicListUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            // 2. 가져올 데이터 컬컴 정의
            val proj2 = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION
            )
            val proj = arrayOf(
                MediaStore.Audio.AudioColumns._ID, // 6
                MediaStore.Audio.AudioColumns.TITLE, // 0
                MediaStore.Audio.AudioColumns.ARTIST,// 7
                MediaStore.Audio.AudioColumns.ALBUM_ID, // 5
                MediaStore.Audio.AudioColumns.DURATION, // 3
                MediaStore.Audio.AudioColumns.DATA, // 4
                MediaStore.Audio.AudioColumns.TRACK, // 1
                MediaStore.Audio.AudioColumns.YEAR, // 2
            )
            //3.  컨텐트 리졸버에 해당 데이터 요청
            val sortOrderDesc = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
            val sortOrderAsc = "${MediaStore.Images.Media.DATE_TAKEN} ASC"
            val artistOrder = "MediaStore.Audio.Artists.DEFAULT_SORT_ORDER"
            val cursor = mContext.contentResolver.query(musicListUri, proj, null, null, null)
            // 4. 커서로 전달받은 데이터를 꺼내서 저장
            val musicList = mutableListOf<Music>()

            Log.d("클릭", "cursor: ${cursor}")
            val defaultUri = Uri.parse("android.resource://com.inu.contentresolver/drawable/resource01")
            while (cursor?.moveToNext() == true) {
                val id = cursor.getString(0)
                val title = cursor.getString(1)
                val artist = cursor.getString(2)
                val albumId = cursor.getString(3) //Long = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)) //cursor!!.getString(3)
                //   var albumUri = Uri.parse("content://media/external/audio/albumart/$albumId")
                val duration = cursor.getLong(4)
                //       val path = cursor.getString(5)
                //       val path = cursor.getString(cursor.getColumnIndex("_data"))
                //    Log.d("패스 로그:", "$path")
                if (duration > 100000) {  // 약 1분 이하 곡 제외
                    i+=1
                    val music = Music(id, title, artist, albumId, duration) //,, path, albumArtBit)
                    musicList.add(music)
                }
            }
            cursor?.close()
            Log.d("클릭", "$i")
            return  musicList
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val text = mPageList.get(position)
        holder.startProcess()
        //setTitle("곡수 : $i")
        holder.setText(text)

    }

    override fun getItemCount()= mPageList.size
}