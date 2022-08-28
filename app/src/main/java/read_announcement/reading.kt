package read_announcement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.collegeapp.OpenImage.openimage
import com.example.collegeapp.R

class reading : AppCompatActivity() {

    lateinit var img:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        val announcementimage:ImageView
        val info:TextView
        val heading:TextView
      //  Toast.makeText(applicationContext, "within the redaing", Toast.LENGTH_SHORT).show()
        announcementimage=findViewById(R.id.imageannouncementadapter)
        info=findViewById(R.id.informationadapter)
        heading=findViewById(R.id.Headingadapter)


        val infoannouncement:String
        val headannouncement:String

        img= intent.getStringExtra("Image").toString()
        infoannouncement=intent.getStringExtra("information").toString()
        headannouncement=intent.getStringExtra("heading").toString()

        Glide.with(this).load(img).into(announcementimage);
        info.text=infoannouncement
        heading.text=headannouncement


    }


    fun openImage(view:View)
    {
        val intent=Intent(applicationContext,openimage::class.java)
        intent.putExtra("Image",img)
        startActivity(intent)
    }
}