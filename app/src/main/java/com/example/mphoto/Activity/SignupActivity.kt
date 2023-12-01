package com.example.mphoto.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.mphoto.Modal.Users
import com.example.mphoto.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    lateinit var auth:FirebaseAuth
    lateinit var dialog:ProgressDialog
    lateinit var database: FirebaseDatabase

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth= FirebaseAuth.getInstance()
        dialog= ProgressDialog(this)
        database=FirebaseDatabase.getInstance()




        binding.btnsinguptop.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))
        }


        binding.btnsingupbt.setOnClickListener {

            dialog.setTitle("Please wait")
            dialog.setMessage("Creating your account")

            dialog.show()

            var name=binding.edtname.text.toString()
            var email=binding.edtemailsingup.text.toString()
            var password=binding.edtpaswordsingup.text.toString()

            var user=Users(name, email, password)

            if(name != "" && email != "" && password != ""){


                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    dialog.dismiss()
                    Toast.makeText(this,"Account created succesfully",Toast.LENGTH_LONG).show()

                    database.reference.child("Users").child(auth.uid.toString()).setValue(user)
                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                    finish()






                }





            }else{

                dialog.dismiss()

                Toast.makeText(this,"Please fill the  blanks",Toast.LENGTH_LONG).show()
            }









        }







    }
}