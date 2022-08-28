package com.example.collegeapp.teacherfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.collegeapp.R
import com.example.collegeapp.WritingResultTeacher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTeachers.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTeachers : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var teacherroll:EditText
    lateinit var addteachername:EditText
    lateinit var addsubject:EditText
    lateinit var dateofjoin:EditText
    lateinit var aboutteacher:EditText
    lateinit var addteachersubmit:Button
    lateinit var database: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       val view=inflater.inflate(R.layout.fragment_add_teachers, container, false)

        teacherroll=view.findViewById(R.id.teacherroll)
        addteachername=view.findViewById(R.id.addteachername)
        addsubject=view.findViewById(R.id.addteacherSubject)
        dateofjoin=view.findViewById(R.id.addteacherdoj)
        aboutteacher=view.findViewById(R.id.addaboutteacher)
        addteachersubmit=view.findViewById(R.id.addteacherbutton)

        addteachersubmit.setOnClickListener {

            var rollnumber=teacherroll.text.toString()
            var teachername=addteachername.text.toString()
            var subject=addsubject.text.toString()
            var doj=dateofjoin.text.toString()
            var about=aboutteacher.text.toString()
            if(rollnumber.isEmpty() || teachername.isEmpty() || subject.isEmpty() || doj.isEmpty() || about.isEmpty())
            {
                Toast.makeText(context, "Please fill all the fields!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                database= FirebaseDatabase.getInstance()
                auth= FirebaseAuth.getInstance()
                reference=database.getReference()
                val teacherdata=addteacherskeletionclass(rollnumber,teachername,subject,doj,about)
                database.reference.child("teachers").child(rollnumber).addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists())
                        {
                            Toast.makeText(context, "A teacher with same roll number already exists!", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            database.reference.child("teachers").child(rollnumber).setValue(teacherdata).addOnCompleteListener {
                                Toast.makeText(context, "Teacher added to Rankboard!", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener {
                                Toast.makeText(context, "Failure in teacher addition", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }


        }

        return view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddTeachers.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddTeachers().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}