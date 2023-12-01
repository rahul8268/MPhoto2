package com.example.mphoto.Fragments

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isGone
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mphoto.Modal.Photomodal
import com.example.mphoto.PhotoshowAdapter
import com.example.mphoto.R
import com.example.mphoto.databinding.FragmentHomefragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class Homefragment : Fragment() {



     var list=ArrayList<Photomodal>()



    var layoutmanager=GridLayoutManager(context,4)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

      var view=  inflater.inflate(R.layout.fragment_homefragment, container, false)
        var recview=view.findViewById<RecyclerView>(R.id.recview)
        var nodata=view.findViewById<TextView>(R.id.nodata)
        recview.layoutManager=layoutmanager


 var dialog=ProgressDialog(context)
        dialog.setTitle("please wait")
        dialog.setMessage("your Images getting for database")



        var adapter= context?.let { PhotoshowAdapter(list, it) }

        recview.adapter=adapter



dialog.show()

        FirebaseDatabase.getInstance().reference.child("Photo").child(FirebaseAuth.getInstance().uid.toString()).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    list.clear()
                    for (datasnapshot:DataSnapshot in snapshot.children){
                        var photomodal=datasnapshot.getValue<Photomodal>()




                        if (photomodal != null && snapshot.key==FirebaseAuth.getInstance().uid.toString()) {
                            list.add(photomodal)
                            dialog.dismiss()
                        }



                    }

                    adapter?.notifyDataSetChanged()

                    if(list.isEmpty()){

                        nodata.visibility=View.VISIBLE
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    error.toException()
                }


            })




        return view
    }




    }
