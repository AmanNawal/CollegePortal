package com.example.collegeapp.Content

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.collegeapp.R


class tutorials : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorials)
        var clangcard:CardView=findViewById(R.id.cprog)
        var cppprogcard:CardView=findViewById(R.id.cppprog)
        var datastructurescard:CardView=findViewById(R.id.datastructurescard)
        var databasecard:CardView=findViewById(R.id.datastructurescard)
        var operatingsyscard:CardView=findViewById(R.id.operatingsyscard)
        var networkingcard:CardView=findViewById(R.id.networkingcard)


    }

   fun clangonclick(view:View)
    {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=ZSPZob_1TOk"))
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
        }
    }
    fun cpplangonclick(view: View)
    {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=vLnPwxZdW4Y"))
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
        }
    }
    fun Daaonclick(view: View)
    {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=8hly31xKli0"))
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
        }

    }
    fun databaseonclick(view: View)
    {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=HXV3zeQKqGY"))
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
        }
    }
    fun operatingsysonclick(view: View)
    {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=mXw9ruZaxzQ"))
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
        }
    }
    fun networkingonclick(view: View)
    {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=qiQR5rTSshw&t=20682s"))
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
        }
    }
}