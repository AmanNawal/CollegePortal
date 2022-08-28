package fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import com.example.collegeapp.R
import com.example.collegeapp.studentdataAttributes.studentdatasignup
import com.example.collegeapp.unirollClass
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.lang.Exception
import android.widget.RelativeLayout

import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.example.collegeapp.OpenImage.openimage


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */


class GetUniversityRollNumber{
    var flag:Boolean=false
    var rollnum:String=""
}

class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var username: TextView
    lateinit var email:TextView
    lateinit var semester:TextView
    lateinit var universitynum:TextView
    lateinit var stream:TextView
    lateinit var DOJ:TextView
    lateinit var Profilepic:ImageView
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var getuni:GetUniversityRollNumber
    lateinit var progessdialog:ProgressDialog



    /*init {
        Toast.makeText(context, "working init", Toast.LENGTH_SHORT).show()
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {

        if (arguments != null) {
           // val value = requireArguments().getString("key")
            //Toast.makeText(context, "arguments block", Toast.LENGTH_SHORT).show()
            //uniroll=value.toString()
        }
            super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profile, container, false)

        try {
            progessdialog = ProgressDialog(activity)
            progessdialog.setTitle("Profile")
            progessdialog.setMessage("Loading Data")

            progessdialog.show()
            if (activity != null && isAdded && !isRemoving) {
                //var uniroll: String?
                //val toolbar: Toolbar


               // uniroll = unirollClass.getinstance().getItem()
                //Toast.makeText(context, uniroll, Toast.LENGTH_SHORT).show()
                username = view.findViewById(R.id.student_name)
                email = view.findViewById(R.id.student_email_profile)
                semester = view.findViewById(R.id.semesterprofile)
                universitynum = view.findViewById(R.id.universityrollnum_profile)
                stream = view.findViewById(R.id.stream_profile)
                DOJ = view.findViewById(R.id.date_join_profile)
                Profilepic = view.findViewById(R.id.studentprofileimage)
                database = FirebaseDatabase.getInstance()
                reference = database.reference

                //****************************************************************
                val bundle=arguments
                val profile_email= bundle?.getString("Email")
                val universityrollnumber= bundle?.getString("Universityrollnumber")
                val purified_email= profile_email?.replace(".","")
                //**************************************************************


                Log.d("PROFILEUNIVERSITYROLL","$profile_email   $universityrollnumber")
                var data: studentdatasignup?
                reference.child("Users").child(universityrollnumber+purified_email)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            data = snapshot.getValue<studentdatasignup>()

                            username.setText("Username -   " + data?.username)
                            email.setText("E-Mail -   " + data?.email)
                            universitynum.setText("University Roll Number -  " + data?.universityRollNumber)
                            semester.setText("Semester -  " + data?.semester)
                            stream.setText("Stream -  " + data?.stream)
                            DOJ.setText("DOJ -  " + data?.doj)
                            Glide.with(view).load(data?.profileimagelink).into(Profilepic);

                            Profilepic.setOnClickListener(object: View.OnClickListener{   //in case user open the profile image
                                override fun onClick(v: View?) {

                                    val intent=Intent(context,openimage::class.java)
                                    intent.putExtra("Image",data?.profileimagelink)
                                    startActivity(intent)

                                }

                            })

                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                progessdialog.dismiss()
            }
            return view
        }
        catch (ex: Exception)
        {
            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
        }
        return view
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}