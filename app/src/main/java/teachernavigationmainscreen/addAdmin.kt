package teachernavigationmainscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.collegeapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class addAdmin : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var  email:EditText
    lateinit var adminnumber:EditText
    lateinit var universityroll:EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_admin)
        email=findViewById(R.id.editTextTextEmailAddress3)
        adminnumber=findViewById(R.id.editTextNumber3)
        universityroll=findViewById(R.id.teacherrollnumber)
    }

    fun addAdmin(view: View)
    {


        if(email.text.toString().isEmpty() || adminnumber.text.toString().isEmpty() || universityroll.text.toString().isEmpty())
        {
            Toast.makeText(applicationContext, "Please enter the required information!", Toast.LENGTH_SHORT).show()
        }
        else
        {
            database= FirebaseDatabase.getInstance()
            reference=database.reference
            auth= FirebaseAuth.getInstance()
            val purified_email=email.text.toString().replace(".","")
            val adminnum=adminnumber.text.toString()
            val teacherroll=universityroll.text.toString()
            reference.child("Users").child(teacherroll+purified_email).addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!snapshot.exists())
                    {
                        Toast.makeText(applicationContext, "Please create a student account first", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        database.reference.child("Admin").child(purified_email).setValue(adminnum).addOnCompleteListener {
                            Toast.makeText(applicationContext, "Admin Added", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(applicationContext, "Failed to add admin!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT).show()
                }


            })

        }
    }

}