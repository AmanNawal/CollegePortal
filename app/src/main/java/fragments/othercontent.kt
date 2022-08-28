package fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.collegeapp.Content.Rankings
import com.example.collegeapp.Content.tutorials
import com.example.collegeapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [othercontent.newInstance] factory method to
 * create an instance of this fragment.
 */
class othercontent : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_othercontent, container, false)
        var tutorialcard:CardView=view.findViewById(R.id.tutorialcard)
        var rankcard:CardView=view.findViewById(R.id.rankcard)
        var dsaSheet:CardView=view.findViewById(R.id.codingquestions)


        val bundle=arguments
        val profile_email= bundle?.getString("Email")
        val universityrollnumber= bundle?.getString("Universityrollnumber")
        val purified_email= profile_email?.replace(".","")




        tutorialcard.setOnClickListener{
            val intent=Intent(context,tutorials::class.java)
            startActivity(intent)
        }

        rankcard.setOnClickListener{
            val intent=Intent(context,Rankings::class.java)
            intent.putExtra("purifiedemail",purified_email)
            intent.putExtra("universityrollnumber",universityrollnumber)
            startActivity(intent)
        }

        dsaSheet.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1FMdN_OCfOI0iAeDlqswCiC2DZzD4nPsb/view")))
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
         * @return A new instance of fragment othercontent.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            othercontent().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}