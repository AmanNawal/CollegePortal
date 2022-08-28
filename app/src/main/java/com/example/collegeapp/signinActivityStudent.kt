package com.example.collegeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.example.collegeapp.studentmainscreen.studentMainScreen
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class signinActivityStudent : AppCompatActivity() {

    lateinit var auth:FirebaseAuth
    lateinit var email:TextInputEditText
    lateinit var password:EditText
    lateinit var universityroll:EditText
    lateinit var signin:Button
    lateinit var database:FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var progressbar: ProgressBar



    private val watchText= object: TextWatcher {                   //is used for checking the conditions required to match the edt are fulfilled or not
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            var Emailstudent:String?
            var passwordstudent:String?
            var universitynumber:String?


            Emailstudent=email.text.toString().trim()
            passwordstudent=password.text.toString().trim()
            universitynumber=universityroll.text.toString()

            //   Toast.makeText(applicationContext, Rollnum+dateofjoin+sem+btechstream, Toast.LENGTH_SHORT).show()
            //Toast.makeText(applicationContext, "${userstring}  $emailstring  $passwordstring", Toast.LENGTH_SHORT).show()
            if(!Emailstudent.isEmpty() && !passwordstudent.isEmpty() && !universitynumber.isEmpty())
            {
                signin?.isEnabled=true
            }
            else
            {
                signin?.isEnabled=false
            }

        }

        override fun afterTextChanged(s: Editable?) {

        }

    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_student)
        email=findViewById(R.id.email_teacher_signin)
        password=findViewById(R.id.password_teacher_signin)
        signin=findViewById(R.id.buttonsignin_student)
        universityroll=findViewById(R.id.editTextNumber2)
        database= FirebaseDatabase.getInstance()
        reference=database.reference

        auth= FirebaseAuth.getInstance()

        email.addTextChangedListener(watchText)
        password.addTextChangedListener(watchText)
        universityroll.addTextChangedListener(watchText)





    }


    fun studentsignUp(view: View)
    {
        val intent= Intent(this,studentLoginSignupmode::class.java)
        startActivity(intent)
        finish()
    }


    fun checkValidUniNumber(view:View)
    {
        signin.isEnabled=false
        allComponents(0)
        val studentemail=email.text.toString()
        val studentpassword=password.text.toString()
        val universitynum=universityroll.text.toString()


        //here did some changes added universitynum+studentemail
        var purified_email=studentemail.replace(".","")
        reference.child("Users").child(universitynum+purified_email).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists())
                {
                    allComponents(1)
                    signin.isEnabled=true
                    Toast.makeText(applicationContext, "Invalid university roll number", Toast.LENGTH_SHORT).show()
                }
                else
                {

                    Signinstudent(view)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    fun allComponents(reqcode:Int)
    {
        val gehulogo:ImageView=findViewById(R.id.signinImageView3)
        val universitytext:TextInputLayout=findViewById(R.id.textInputLayout7)
        val emailtext:TextInputLayout=findViewById(R.id.textInputLayout2)
        val passwordtext:TextInputLayout=findViewById(R.id.textInputLayout3)
        val createnewuser:TextView=findViewById(R.id.create_a_new_account2)
        val loadingtext:TextView=findViewById(R.id.loadingtext)
        progressbar = findViewById(R.id.spin_kit)
        val doublebounce: Sprite = Wave()
        progressbar.setIndeterminateDrawable(doublebounce)
        if(reqcode==0)
        {

            gehulogo.visibility=View.INVISIBLE
            universitytext.visibility=View.INVISIBLE
            emailtext.visibility=View.INVISIBLE
            passwordtext.visibility=View.INVISIBLE
            createnewuser.visibility=View.INVISIBLE
            signin.visibility=View.INVISIBLE
            loadingtext.visibility=View.VISIBLE
            progressbar.visibility=View.VISIBLE

        }
        else if(reqcode==1)
        {
            gehulogo.visibility=View.VISIBLE
            universitytext.visibility=View.VISIBLE
            emailtext.visibility=View.VISIBLE
            passwordtext.visibility=View.VISIBLE
            createnewuser.visibility=View.VISIBLE
            signin.visibility=View.VISIBLE
            loadingtext.visibility=View.INVISIBLE
            progressbar.visibility=View.INVISIBLE
        }


    }






    fun Signinstudent(view:View)
    {
        val studentemail=email.text.toString()
        val studentpassword=password.text.toString()
        val universitynum=universityroll.text.toString()
        signin.isEnabled=false
        auth.signInWithEmailAndPassword(studentemail,studentpassword).addOnSuccessListener {


            val user=auth.currentUser

            if(user!!.isEmailVerified)
            {
                val intent=Intent(this,studentMainScreen::class.java)
                intent.putExtra("universityrollnum",universitynum)
                intent.putExtra("Email",studentemail)

                startActivity(intent)
                finish()
            }

            else
            {
                allComponents(1)
                signin.isEnabled=true
                Toast.makeText(applicationContext, "Email not verified", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener(OnFailureListener {
            signin.isEnabled=true
            allComponents(1)
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()

        })

    }

}