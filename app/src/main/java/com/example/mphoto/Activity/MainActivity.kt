package com.example.mphoto.Activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.mphoto.Fragments.Homefragment
import com.example.mphoto.R
import com.example.mphoto.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage
    lateinit var database: FirebaseDatabase
    lateinit var dialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var toolbar=findViewById<Toolbar>(R.id.toolbar)
        var addphoto=findViewById<ImageView>(R.id.imgtoolbar)


        setSupportActionBar(toolbar)
        auth= FirebaseAuth.getInstance()
        storage= FirebaseStorage.getInstance()
        database= FirebaseDatabase.getInstance()
        dialog=ProgressDialog(this)

        if(checkinternet()){
            //Toast.makeText(this,"",Toast.LENGTH_LONG).show()

        }else{
           // Toast.makeText(this,"Network not availble",Toast.LENGTH_LONG).show()
            startActivity(Intent(this,NoInternetActivity::class.java))
            finish()

        }

        



        loadfragemnt(Homefragment())



        var toggel=ActionBarDrawerToggle(this,binding.drawerlayout,toolbar,R.string.opendrawer,R.string.closedrawer)

        binding.drawerlayout.addDrawerListener(toggel)
        toggel.syncState()




        addphoto.setOnClickListener {

            var intent=Intent(Intent.ACTION_GET_CONTENT)
            intent.type="image/*"

            startActivityForResult(intent,100)

            dialog.setTitle("Please wait")
            dialog.setMessage("Image uploaded")
            dialog.show()


        }






        binding.nvview.setNavigationItemSelectedListener(object :NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                var id=item.itemId

                if(id==R.id.home){


                    loadfragemnt(Homefragment())
                    binding.drawerlayout.closeDrawer(GravityCompat.START)


                }else{

                    auth.signOut()
                    startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                    finishAffinity()
                }



                return true
            }


        })
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


    private fun loadfragemnt( fragment:Fragment) {


        var fm=this.supportFragmentManager

        var ft=fm.beginTransaction()

if(ft==null) {

    ft.add(R.id.container, fragment)



}else{

    ft.replace(R.id.container,fragment)

}
        ft.commit()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data!=null){

            var img=data.data
            var refernce=storage.reference.child("photos").child(auth.uid.toString()).child(data.data!!.path.toString())

            if (img != null) {
                refernce.putFile(img).addOnSuccessListener {


                    if(it.task.isComplete){
                        dialog.dismiss()

                        Toast.makeText(this,"images uploaded",Toast.LENGTH_LONG).show()

                        refernce.downloadUrl.addOnCompleteListener {
                            data ->

                            var url=data.result.toString()




                            database.reference.child("Photo").child(auth.uid.toString()).push().child("img").setValue(url)
                        }

                        }else{
                            dialog.dismiss()
                    }






                    }


                }
            }else{

                dialog.dismiss()
        }


        }

    }





