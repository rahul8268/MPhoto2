package com.example.mphoto.Activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mphoto.R
import com.example.mphoto.databinding.ActivityNoInternetBinding

class NoInternetActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoInternetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNoInternetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {

            if(checkinternet()){

                Toast.makeText(this,"welcome back", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()



            }else{
                Toast.makeText(this,"network not availble", Toast.LENGTH_LONG).show()


            }



        }

        if(checkinternet()){

           // Toast.makeText(this,"Welcome back", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()



        }else{
            Toast.makeText(this,"network not availble", Toast.LENGTH_LONG).show()


        }

    }


    fun checkinternet():Boolean {

        var conctivitymg=getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkinfo=conctivitymg.activeNetworkInfo

        if(networkinfo!=null){
            return networkinfo.isConnectedOrConnecting


        }else{

            return false
        }

    }


}