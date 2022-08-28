package com.example.collegeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.example.collegeapp.studentmainscreen.studentMainScreen
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TeacherLoginSighupMode : AppCompatActivity() {



    lateinit var password: EditText
    lateinit var Email:EditText
    lateinit var signupadminmode: Button
    lateinit var auth:FirebaseAuth
    lateinit var database:FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var progressbar: ProgressBar
    lateinit var emailtextinput:TextInputLayout
    lateinit var passwordtextinput:TextInputLayout
    lateinit var gehuimageview:ImageView
    lateinit var submitbutton:Button



    private val watchText= object: TextWatcher {                   //is used for checking the conditions required to match the edt are fulfilled or not
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            var userstring:String?
            var emailstring:String?
            var passwordstring:String?


            passwordstring=password.text.toString().trim()
            emailstring=Email.text.toString()
            //Toast.makeText(applicationContext, "${userstring}  $emailstring  $passwordstring", Toast.LENGTH_SHORT).show()
            if(!passwordstring.isEmpty() && !emailstring.isEmpty())
            {
                signupadminmode?.isEnabled=true
            }
            else
            {
                signupadminmode?.isEnabled=false
            }

        }

        override fun afterTextChanged(s: Editable?) {

        }

    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_login_sighup_mode)


        password=findViewById(R.id.password_teacher_signin)
        signupadminmode=findViewById(R.id.sign_in_teachers)
        Email=findViewById(R.id.editTextTextEmailAddress3)
        emailtextinput=findViewById(R.id.textInputLayout8)
        passwordtextinput=findViewById(R.id.textInputLayout3)
        gehuimageview=findViewById(R.id.signinImageView)
        submitbutton=findViewById(R.id.sign_in_teachers)


        database= FirebaseDatabase.getInstance()
        reference=database.reference
        auth= FirebaseAuth.getInstance()

        //***********************************************
        progressbar = findViewById(R.id.spin_kit)
        val doublebounce: Sprite = Wave()
        progressbar.setIndeterminateDrawable(doublebounce)
        //***********************************************


        password.addTextChangedListener(watchText)
        Email.addTextChangedListener(watchText)


    }


    fun signInAdmin(view: View) {

        var loadingtext=findViewById<TextView>(R.id.loadingtext)

        val adminemail=Email.text.toString()
        val adminpassword=password.text.toString()
        val purified_email=adminemail.replace(".","")

        emailtextinput.visibility=View.INVISIBLE
        passwordtextinput.visibility=View.INVISIBLE
       gehuimageview.visibility=View.INVISIBLE
        submitbutton.visibility=View.INVISIBLE

        progressbar.visibility=View.VISIBLE
        loadingtext.visibility=View.VISIBLE

        reference.child("Admin").child(purified_email).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists())
                {
                    emailtextinput.visibility=View.VISIBLE
                    passwordtextinput.visibility=View.VISIBLE
                    gehuimageview.visibility=View.VISIBLE
                    submitbutton.visibility=View.VISIBLE
                    progressbar.visibility=View.INVISIBLE
                    loadingtext.visibility=View.INVISIBLE
                    Toast.makeText(applicationContext, "Invalid Email", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    auth.signInWithEmailAndPassword(adminemail,adminpassword).addOnSuccessListener {
                        val user=auth.currentUser
                        if(user!!.isEmailVerified)
                        {
                            val intent=Intent(applicationContext,teachernavigationmainscreen.techermainscreen::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }.addOnFailureListener {
                        emailtextinput.visibility=View.VISIBLE
                        passwordtextinput.visibility=View.VISIBLE
                        gehuimageview.visibility=View.VISIBLE
                        submitbutton.visibility=View.VISIBLE
                        loadingtext.visibility=View.INVISIBLE
                        progressbar.visibility=View.INVISIBLE
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                emailtextinput.visibility=View.VISIBLE
                passwordtextinput.visibility=View.VISIBLE
                gehuimageview.visibility=View.VISIBLE
                submitbutton.visibility=View.VISIBLE
                loadingtext.visibility=View.INVISIBLE
                progressbar.visibility=View.INVISIBLE
                Toast.makeText(applicationContext, "Cancelled", Toast.LENGTH_SHORT).show()
            }

        })


    }


}