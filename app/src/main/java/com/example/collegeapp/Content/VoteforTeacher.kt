package com.example.collegeapp.Content

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.collegeapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class VoteforTeacher : AppCompatActivity() {

    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var teacherID:String
    lateinit var studentid:String
    lateinit var studentpuremail:String
    lateinit var votebutton:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_votefor_teacher)


        var purestudentemail=intent.getStringExtra("purifiedemail").toString()
        var studentrollnumber=intent.getStringExtra("studentroll").toString()
        var name=intent.getStringExtra("nameteacher").toString()
        var subject=intent.getStringExtra("subject").toString()
        var doj=intent.getStringExtra("doj").toString()
        var teacherid=intent.getStringExtra("teacherid").toString()
        var about=intent.getStringExtra("about").toString()
        teacherID=teacherid
        studentid=studentrollnumber
        studentpuremail=purestudentemail


        val teachername=findViewById<TextView>(R.id.Teachernamevote)
        val teacheridtext=findViewById<TextView>(R.id.Teacheridvote)
        val teachersubject=findViewById<TextView>(R.id.subjectVote)
        val teacherdoj=findViewById<TextView>(R.id.Dojvote)
        val teacherabout=findViewById<TextView>(R.id.About)
        votebutton=findViewById(R.id.Voteforteacher)
        votebutton.setVisibility(View.INVISIBLE)

        teachername.text=name
        teacheridtext.text="Teacher ID: "+teacherid
        teachersubject.text=subject
        teacherabout.text=about
        teacherdoj.text=doj


        database = FirebaseDatabase.getInstance()
        reference = database.reference


        reference.child("Users").child(studentrollnumber+purestudentemail).child("voted").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(!snapshot.exists())
                {
                    reference.child("Users").child(studentrollnumber+purestudentemail).child("voted").setValue("0").addOnSuccessListener {
                        Toast.makeText(applicationContext, "User is eligible for voting", Toast.LENGTH_SHORT).show()
                        votebutton.setVisibility(View.VISIBLE)
                    }
                }
                else
                {
                  var checkvote=snapshot.getValue().toString()
                    var checkint=checkvote.toInt()
                    if(checkint>0)
                    {
                        Toast.makeText(applicationContext, "Already voted!", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        votebutton.setVisibility(View.VISIBLE)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun voteForTeacher(view:View)
    {
        votebutton.setVisibility(View.INVISIBLE)
        reference.child("teachers").child(teacherID).child("votes").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists())
               {
                   var votes=snapshot.getValue().toString().toInt()

                   votes=votes+1
                 votefunction(votes)
               }
                else
               {
                   reference.child("teachers").child(teacherID).child("votes").setValue("1")
                   Toast.makeText(applicationContext, "Successfully Voted!", Toast.LENGTH_SHORT).show()

               }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }

    fun votefunction(votes:Int)
    {
        //Toast.makeText(applicationContext, "${votes.toString()}<---------", Toast.LENGTH_LONG).show()
        reference.child("teachers").child(teacherID).child("votes").setValue(votes.toString()).addOnSuccessListener {

            reference.child("Users").child(studentid+studentpuremail).child("voted").setValue(votes.toString()).addOnSuccessListener {
                Toast.makeText(applicationContext, "Successfully Voted!", Toast.LENGTH_SHORT).show()
                votebutton.setVisibility(View.INVISIBLE)
            }.addOnFailureListener {
                Toast.makeText(applicationContext, "Failed to vote", Toast.LENGTH_SHORT).show()
                votebutton.setVisibility(View.VISIBLE)
            }
        }
    }


}