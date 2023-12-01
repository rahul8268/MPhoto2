package com.example.mphoto

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mphoto.Activity.FullphotoshowActivity
import com.example.mphoto.Modal.Photomodal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class PhotoshowAdapter(var list:ArrayList<Photomodal>,var context:Context) : RecyclerView.Adapter<PhotoshowAdapter.ViewHolder>() {


    class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        var imgphoto: ImageView =view.findViewById<ImageView>(R.id.imgphoto)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.photoshowlayout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var photomodal=list[position]





        Picasso.get().load(photomodal.img).placeholder(R.drawable.blankphoto).into(holder.imgphoto)

        holder.itemView.setOnClickListener {

            if(photomodal.img!=null) {
                var intent = Intent(context, FullphotoshowActivity::class.java)
                intent.putExtra("img", photomodal.img)
                context.startActivity(intent)

            }
        }


        holder.itemView.setOnLongClickListener {

            var alertdialog=AlertDialog.Builder(context)
            alertdialog.setTitle("Are you sure")
            alertdialog.setMessage("want to delete image")
            alertdialog.setIcon(R.drawable.baseline_delete_24)
            alertdialog.setPositiveButton("Yes"
            ) { p0, position ->
                Toast.makeText(context, "Your are not delete any photo", Toast.LENGTH_LONG).show()


            }.setNegativeButton("No"
            ) { p0, p1 -> Toast.makeText(context, "click No", Toast.LENGTH_LONG).show() }.show()




            return@setOnLongClickListener true
        }
    }
}


