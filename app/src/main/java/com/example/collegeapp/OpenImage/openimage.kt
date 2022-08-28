package com.example.collegeapp.OpenImage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.collegeapp.R

//This activity is for opening images for all other activities

class openimage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_openimage)
        val imageforevery=findViewById<ImageView>(R.id.imageforevery)
       val img= intent.getStringExtra("Image").toString()
        Glide.with(this).load(img).into(imageforevery)




    }
}