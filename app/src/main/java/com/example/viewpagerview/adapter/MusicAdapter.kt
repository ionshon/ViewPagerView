package com.example.viewpagerview.adapter

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viewpagerview.data.MPage
import com.example.viewpagerview.data.Music
import com.example.viewpagerview.databinding.ItemLayoutBinding
import com.example.viewpagerview.databinding.ItemViewpagerBinding
import java.io.IOException
import java.text.SimpleDateFormat

class MusicAdapter: RecyclerView.Adapter<MusicAdapter.Holder>()  {

    val musicList = mutableListOf<Music>()
    var mediaPlayer: MediaPlayer? = null
    val uriLocal = Uri.parse("android.resource://com.inu.contentresolver/drawable/mp3logo")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      //  val binding2 = ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = musicList[position]
        holder.setMusic(music)
    }

    override fun getItemCount() = musicList.size

    inner class Holder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        //    var cardView: CardView? = null
        var musicUri: Uri? = null
        //  var musicBean: Music? = null


        init {
            //    player?.musicPlayer(musicUri, binding.root.context)
            binding.root.setOnClickListener {
                Log.d("클릭:", "adapter Holder")
                //     onSongClicked?.onSongClicked(musicBean!!)

                if (mediaPlayer != null) {
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
                mediaPlayer = MediaPlayer.create(binding.root.context, musicUri)
                mediaPlayer?.start()
            }
        }

        fun setMusic(music: Music) {

//                1. 로드할 대상 Uri    2. 입력될 이미지뷰
            with(binding) {
                //     imageAlbum.setImageBitmap(Utils.songArt(music.path, binding.root.context))
                //      imageAlbum.setImageBitmap(decodeSampledBitmapFromResource(, music.id.toInt(), 250, 250));
                textTitle.text = music.title
                texArtist.text = music.artist
                val sdf = SimpleDateFormat("mm:ss")
                textDuration.text = sdf.format(music.duration)
            }

            Glide.with(binding.root.context)
                .load(music.getAlbumUri())
                //    .placeholder(R.drawable.ic_menu_close_clear_cancel).into(binding.imageAlbum)
                .placeholder(com.example.viewpagerview.R.drawable.outline_music_note_24).into(binding.imageAlbum)
            //      Log.d("duration 길이:", "${music.duration}")
            //  } // 코루틴 마지막 줄
            this.musicUri = music.getMusicUri()
        }
    }
}