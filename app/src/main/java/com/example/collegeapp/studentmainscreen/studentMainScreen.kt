package com.example.collegeapp.studentmainscreen

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.collegeapp.R
import com.example.collegeapp.unirollClass
import com.example.collegeapp.usermode
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import fragments.Announcement
import fragments.Profile
import fragments.Result
import fragments.othercontent


class studentMainScreen : AppCompatActivity() {

    lateinit var studentBottomNavigation: BottomNavigationView

    val announcementfrag = Announcement()
    val resultfrag = Result()
    val profilefrag = Profile()
    lateinit var universityroll: String
    lateinit var Email:String
    lateinit var mFragment: Fragment
    lateinit var mFragmentTransaction: FragmentTransaction
    lateinit var mBundle: Bundle
    //lateinit var toolbar: androidx.appcompat.widget.Toolbar


    override fun onBackPressed() {    //when user presses back in the main screen the application moves to mode selection
        // so we want to make sure if user wants to exit
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                 { dialog, id -> this.finish() })
            .setNegativeButton("No", null)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_main_screen)

        universityroll = intent.getStringExtra("universityrollnum").toString()
        Email=intent.getStringExtra("Email").toString()

        unirollClass.getinstance().setItem(universityroll)    //for setting universityrollnumber to the class

        //  Toast.makeText(applicationContext, "University Roll Number->  $universityroll", Toast.LENGTH_SHORT).show()
        val mFragmentManager = supportFragmentManager
        mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragment = Profile()

        mFragmentTransaction.add(R.id.fragmentcontainer, mFragment).commit()

        studentBottomNavigation = findViewById(R.id.studentBottomnavigation)
        //toolbar=findViewById(R.id.toolbar3)
        //replaceFragment(profilefrag)

//**************************************************************************
        val profileFrag=Profile()
        val fragmenttransaction=supportFragmentManager.beginTransaction()
        val bundle=Bundle()
        bundle.putString("Universityrollnumber",universityroll)
        bundle.putString("Email",Email)
        profileFrag.arguments=bundle
        fragmenttransaction.replace(R.id.fragmentcontainer,profileFrag).commit()

      //  ********************************************************************

        studentBottomNavigation.setOnItemSelectedListener {


            when (it.itemId) {
                R.id.announcement -> {
                    replaceFragment(announcementfrag)


                }
                R.id.Result -> {
                    val resultFrag=Result()
                    val fragmenttransaction=supportFragmentManager.beginTransaction()
                    val bundle=Bundle()
                    bundle.putString("Universityrollnumber",universityroll)
                    bundle.putString("Email",Email)
                    resultFrag.arguments=bundle
                    fragmenttransaction.replace(R.id.fragmentcontainer,resultFrag).commit()

                }
                R.id.Profile -> {
                    val profileFrag=Profile()
                    val fragmenttransaction=supportFragmentManager.beginTransaction()
                    val bundle=Bundle()
                    bundle.putString("Universityrollnumber",universityroll)
                    bundle.putString("Email",Email)
                    profileFrag.arguments=bundle
                    fragmenttransaction.replace(R.id.fragmentcontainer,profileFrag).commit()
                        //Toast.makeText(applicationContext, "passing bundle-> ${mBundle.getString("Uniroll")}", Toast.LENGTH_SHORT).show()
                }//
                R.id.studycontent->{

                    val ContentFrag=othercontent()
                    val fragmenttransaction=supportFragmentManager.beginTransaction()
                    val bundle=Bundle()
                    bundle.putString("Universityrollnumber",universityroll)
                    bundle.putString("Email",Email)
                    ContentFrag.arguments=bundle
                    fragmenttransaction.replace(R.id.fragmentcontainer,ContentFrag).commit()

                }//
            }
            true
        }
            studentBottomNavigation.setOnItemReselectedListener { object : NavigationBarView.OnItemReselectedListener{
                override fun onNavigationItemReselected(item: MenuItem) {
                    Toast.makeText(applicationContext, item.itemId.toString()+"reselected", Toast.LENGTH_SHORT).show()

                }


            } }

    }


    public fun userProfileData():String
    {
        var purified_string=Email.replace(".","")
        return universityroll+purified_string
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuforstudentfragments,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sign_out->{
                FirebaseAuth.getInstance().signOut()
                val intent=Intent(this,usermode::class.java)
                Toast.makeText(applicationContext, "Successfully signed out", Toast.LENGTH_SHORT).show()
                finish()
                return true
            }
        }

        return true
    }

    fun replaceFragment(fragment: Fragment)     //will change out the fragments on the basis of the button which is clicked
    {
        // Toast.makeText(applicationContext, "${mBundle.getString("Uniroll")}", Toast.LENGTH_SHORT).show()
        if (fragment != null) {

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentcontainer, fragment)
            transaction.commit()
        }
    }
}

