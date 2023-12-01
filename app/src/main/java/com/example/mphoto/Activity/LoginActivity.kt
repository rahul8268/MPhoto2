package com.example.mphoto.Activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.mphoto.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.R

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth:FirebaseAuth
    lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth= FirebaseAuth.getInstance()
        dialog=ProgressDialog(this)


        if(auth.currentUser!=null){

            startActivity(Intent(this,MainActivity::class.java))
            finishAffinity()
        }


        binding.signintoplogin.setOnClickListener {

            startActivity(Intent(this, SignupActivity::class.java))

        }

        binding.btnloginbt.setOnClickListener {

            dialog.setTitle("please wait")
            dialog.setMessage("Login your Account")
            dialog.show()

            var email=binding.edtemaillogin.text.toString()
            var password=binding.edtpaswordlogin.text.toString()

            if(email!=""&&password!=""){


                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {

                    if(it.isSuccessful)
                    {
                        dialog.dismiss()
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()


                    }else{

                        dialog.dismiss()
                        Toast.makeText(this,it.exception.toString(),Toast.LENGTH_LONG).show()
                    }


                }



            }else{

                dialog.dismiss()

                Toast.makeText(this,"Please fill the  blanks", Toast.LENGTH_LONG).show()
            }


        }



    }
}