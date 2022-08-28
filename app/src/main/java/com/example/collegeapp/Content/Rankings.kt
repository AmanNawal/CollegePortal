package com.example.collegeapp.Content

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeapp.R
import com.example.collegeapp.modelclass_for_Announcement
import com.example.collegeapp.student_announcement_adpater
import com.example.collegeapp.teacherfragments.addteacherskeletionclass
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import com.google.firebase.database.*
import java.lang.NullPointerException

class Rankings : AppCompatActivity() {

    lateinit var recyclerranking:RecyclerView
    lateinit var database:FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var teacherlist:ArrayList<addteacherskeletionclass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rankings)

        var progressbar: ProgressBar?

        recyclerranking=findViewById(R.id.recycler_for_rankings)
        database= FirebaseDatabase.getInstance()
        reference=database.reference
        progressbar = findViewById(R.id.spin_kit)
        teacherlist= arrayListOf()
        val doublebounce: Sprite = Wave()
        progressbar.setIndeterminateDrawable(doublebounce)

        var purestudentemail=intent.getStringExtra("purifiedemail").toString()
        var studentrollnumber=intent.getStringExtra("universityrollnumber").toString()


        reference.child("teachers").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                progressbar.setVisibility(View.VISIBLE)

                for (snap in snapshot.children) {
                    val teacherdata = snap?.getValue(addteacherskeletionclass::class.java)
                    if (teacherdata != null) {
                        teacherlist.add(teacherdata)

                    }

                    if(teacherlist.size>0)
                    {

                        try {
                             progressbar.setVisibility(View.INVISIBLE)
                            val adapter = Teacherrankingadapter(teacherlist,applicationContext,purestudentemail,studentrollnumber)
                            recyclerranking.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                            recyclerranking.adapter = adapter

                        }
                        catch (e:NullPointerException)
                        {

                            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "No data to fetch!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Cancelled", Toast.LENGTH_SHORT).show()
            }

        })

    }
}