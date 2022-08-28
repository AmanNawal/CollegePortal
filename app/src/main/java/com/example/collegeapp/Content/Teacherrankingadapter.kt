package com.example.collegeapp.Content

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeapp.R
import com.example.collegeapp.modelclass_for_Announcement
import com.example.collegeapp.student_announcement_adpater
import com.example.collegeapp.teacherfragments.addteacherskeletionclass

class Teacherrankingadapter(val list1:ArrayList<addteacherskeletionclass>,val context:Context?,val purifiedemail:String,val studentroll:String): RecyclerView.Adapter<Teacherrankingadapter.UserDefinedViewHolder>() {
    lateinit var list: MutableList<addteacherskeletionclass>

    init {

        list=list1.asReversed()


    }




    class UserDefinedViewHolder(itemview:View):RecyclerView.ViewHolder(itemview)
    {

       var teachercardview:CardView=itemview.findViewById(R.id.Teachercardviewclick)
        var ranking:TextView=itemview.findViewById(R.id.teacherrank)
        var teachername:TextView=itemview.findViewById(R.id.teachernameadapter)
        var subject:TextView=itemview.findViewById(R.id.teachersubject)
        var votes:TextView=itemview.findViewById(R.id.votesinadapter)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDefinedViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.adapterforteacherrankings,parent,false)
        return Teacherrankingadapter.UserDefinedViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserDefinedViewHolder, position: Int) {
       try {

           val nameofteacher="Name: "+list[position]?.name
           val doj="DOJ: "+list[position]?.doj
           val subjectofteacher="Subject: "+list[position]?.subject
           val idofteacher=list[position]?.teacherid
           val aboutteacher="About: "+list[position]?.about
           val votesforteacher="Votes: "+list[position]?.votes

           holder.teachername.text=nameofteacher
           holder.subject.text=subjectofteacher
           holder.votes.text=votesforteacher
           holder.teachercardview.setOnClickListener {

               val intent = Intent(it.context, VoteforTeacher::class.java)
               intent.putExtra("nameteacher", nameofteacher)
               intent.putExtra("doj", doj)
               intent.putExtra("subject", subjectofteacher)
               intent.putExtra("teacherid", idofteacher)
               intent.putExtra("about", aboutteacher)
               intent.putExtra("studentroll", studentroll)
               intent.putExtra("purifiedemail", purifiedemail)

               it.context!!.startActivity(intent)


           }
       }
       catch (ex:Exception)
       {
           Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
       }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}




