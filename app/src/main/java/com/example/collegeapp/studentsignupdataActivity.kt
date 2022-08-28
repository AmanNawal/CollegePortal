package com.example.collegeapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.collegeapp.studentdataAttributes.studentdatasignup
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class studentsignupdataActivity : AppCompatActivity() {


    lateinit var email:String
    lateinit var password:String
    lateinit var username:String
    lateinit var database: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var reference: DatabaseReference
    lateinit var universityRollnumber:EditText
    lateinit var dateofjoining:EditText
    lateinit var semester:EditText
    lateinit var stream:TextInputEditText
    lateinit var submit:Button
    lateinit var storage:FirebaseStorage
    lateinit var Profileimageuri:Uri
    lateinit var signupdatascreen:ConstraintLayout
    val context:Context=this
    lateinit var progressbar: ProgressBar

    lateinit var studentdata:studentdatasignup




    private val watchText= object: TextWatcher {                   //is used for checking the conditions required to match the edt are fulfilled or not
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            var Rollnum:String?
            var dateofjoin:String?
            var sem:String?
            var btechstream:String?

            Rollnum=universityRollnumber.text.toString().trim()
            dateofjoin=dateofjoining.text.toString().trim()
            sem=semester.text.toString().trim()
            btechstream=stream.text.toString().trim()
         //   Toast.makeText(applicationContext, Rollnum+dateofjoin+sem+btechstream, Toast.LENGTH_SHORT).show()
            //Toast.makeText(applicationContext, "${userstring}  $emailstring  $passwordstring", Toast.LENGTH_SHORT).show()
            if(!Rollnum.isEmpty() && !dateofjoin.isEmpty() && !sem.isEmpty() && !btechstream.isEmpty())
            {
                submit?.isEnabled=true
            }
            else
            {
                submit?.isEnabled=false
            }

        }

        override fun afterTextChanged(s: Editable?) {

        }

    }




    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentsignupdata)
        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()
        reference=database.getReference()
        storage= FirebaseStorage.getInstance()
        signupdatascreen=findViewById(R.id.signupdatascreen)


        //progress bar

        progressbar = findViewById(R.id.spin_kit)
        val doublebounce: Sprite = Wave()
        progressbar.setIndeterminateDrawable(doublebounce)


        email= intent.getStringExtra("email").toString()
        username= intent.getStringExtra("studentname").toString()
        password= intent.getStringExtra("password").toString()
        val bundle: Bundle? =intent.extras

           Profileimageuri =Uri.parse(bundle!!.getString("imageuri"))



    //    Toast.makeText(applicationContext, Profileimageuri.toString(), Toast.LENGTH_SHORT).show()

        universityRollnumber=findViewById(R.id.universityrollnumber)
        dateofjoining=findViewById(R.id.editTextDate)
        semester=findViewById(R.id.editTextNumber)
        stream=findViewById(R.id.stream)
        submit=findViewById(R.id.submitbutton)


        universityRollnumber.addTextChangedListener(watchText)
        dateofjoining.addTextChangedListener(watchText)
        semester.addTextChangedListener(watchText)
        stream.addTextChangedListener(watchText)

        val dateNow = Calendar.getInstance().time
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val formatedDate = sdf.format(dateNow)
        dateofjoining.setText(formatedDate.toString())
      //  Toast.makeText(applicationContext, formatedDate.toString(), Toast.LENGTH_SHORT).show()


        //Toast.makeText(applicationContext, email+username+password, Toast.LENGTH_SHORT).show()


    }

    fun hideAllComponents(reqcode:Int)
    {
        val gehuimage=findViewById<ImageView>(R.id.signinImageView2)
        val unirolltext=findViewById<TextInputLayout>(R.id.textInputLayout)
        val DOJtext=findViewById<TextInputLayout>(R.id.textInputLayout4)
        val streamtext=findViewById<TextInputLayout>(R.id.textInputLayout6)
        val semestertext=findViewById<TextInputLayout>(R.id.textInputLayout5)
        val loading=findViewById<TextView>(R.id.loadingtext)

        if(reqcode==0)
        {
        gehuimage.visibility = View.INVISIBLE
        unirolltext.visibility = View.INVISIBLE
        DOJtext.visibility = View.INVISIBLE
        streamtext.visibility = View.INVISIBLE
        semestertext.visibility = View.INVISIBLE
            submit.visibility=View.INVISIBLE
            loading.visibility=View.VISIBLE
        }
        else if(reqcode==1)
        {

            gehuimage.visibility = View.VISIBLE
            unirolltext.visibility = View.VISIBLE
            DOJtext.visibility = View.VISIBLE
            streamtext.visibility = View.VISIBLE
            semestertext.visibility = View.VISIBLE
            submit.visibility=View.VISIBLE
            loading.visibility=View.INVISIBLE
        }

    }



    fun CreateUserForStudent(view: View)
    {
       // Toast.makeText(applicationContext, email+password, Toast.LENGTH_SHORT).show()
        submit.isEnabled=false
        val uniroll:String=universityRollnumber.text.toString()
        val doj:String=dateofjoining.text.toString()
        val sem:String=semester.text.toString()
        val currentstream=stream.text.toString()

        val sreference=storage.reference.child(uniroll)

        progressbar.setVisibility(View.VISIBLE)  //progressbar
        hideAllComponents(0)
        reference.child("checkroll").child(uniroll).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists())
                {
                    submit.isEnabled=true
                    hideAllComponents(1)
                    progressbar.setVisibility(View.INVISIBLE)
                    Toast.makeText(applicationContext, "A user already exists with this university roll number", Toast.LENGTH_SHORT).show()

                }

                else
                {
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this@studentsignupdataActivity){
                            task->
                        hideAllComponents(0)
                        //signupdatascreen.visibility=View.INVISIBLE
                        if(task.isSuccessful) {
                             // in order to make sure that the user does not taps the button more than once
                            val user: FirebaseUser? = auth.currentUser
                            user!!.sendEmailVerification().addOnSuccessListener {
                                    verify->
                                Toast.makeText(applicationContext, "Verification Email sent", Toast.LENGTH_SHORT).show()
                                if (Profileimageuri.toString() != "null") {       //in case user enters profile pic

                                    // Toast.makeText(applicationContext, "inside if block", Toast.LENGTH_SHORT).show()
                                    sreference.putFile(Profileimageuri).addOnSuccessListener {

                                        sreference.downloadUrl.addOnSuccessListener { uri ->

                                            studentdata = studentdatasignup(uniroll, username, email, password, doj, sem, currentstream, uri.toString())
                                            //Toast.makeText(applicationContext, "$email && $uniroll", Toast.LENGTH_SHORT).show()
                                            var purified_email=email.replace(".","")
                                            database.reference.child("Users").child(uniroll+purified_email)     //here make add uniroll+email
                                                .setValue(studentdata)
                                                .addOnCompleteListener() {
                                                    Toast.makeText(applicationContext, "User created succesfully", Toast.LENGTH_SHORT)
                                                        .show()
                                                }
                                            database.reference.child("checkroll").child(uniroll).setValue(email).addOnFailureListener {
                                                hideAllComponents(1)
                                                progressbar.setVisibility(View.INVISIBLE)
                                                submit.isEnabled=true
                                                Toast.makeText(applicationContext, "Failed to create user", Toast.LENGTH_SHORT).show()
                                            }
                                            StudentSigninScreen()
                                            finish()
                                            //    Toast.makeText(applicationContext, profileimagelink, Toast.LENGTH_LONG).show()

                                        }.addOnFailureListener { exception ->
                                            hideAllComponents(1)
                                            progressbar.setVisibility(View.INVISIBLE)
                                            submit.isEnabled=true   //when the user fails then the button should be enabled
                                            Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }

                                }/* else    //in case the user does not enters a profilepic
                                {
                                    //    Toast.makeText(applicationContext, "inside else block", Toast.LENGTH_SHORT).show()
                                    studentdata = studentdatasignup(uniroll, username, email, password, doj, sem,
                                        currentstream,
                                        null
                                    )
                                    database.reference.child("Users").child(uniroll).setValue(studentdata)
                                        .addOnCompleteListener() {
                                            //  Toast.makeText(applicationContext, "data entered successfully!", Toast.LENGTH_SHORT).show()

                                        }

                                    StudentSigninScreen()
                                    finish()
                                }*/

                            }
                        }
                        else{
                            hideAllComponents(1)
                            progressbar.setVisibility(View.INVISIBLE)
                            submit.isEnabled=true;   // in case if any error occurs button should be anabled
                            Toast.makeText(applicationContext, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }


                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }



    fun StudentSigninScreen()
    {
        val intent=Intent(this,signinActivityStudent::class.java)
        startActivity(intent)
    }





}