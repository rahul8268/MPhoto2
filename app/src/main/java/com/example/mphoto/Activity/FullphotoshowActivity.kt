package com.example.mphoto.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.mphoto.Modal.Photomodal
import com.example.mphoto.R
import com.example.mphoto.databinding.ActivityFullphotoshowBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream
import java.util.Objects


class FullphotoshowActivity : AppCompatActivity() {
    lateinit var binding: ActivityFullphotoshowBinding

    lateinit var dialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullphotoshowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var img = intent.getStringExtra("img")






        Picasso.get().load(img).into(binding.myZoomageView)
        dialog= ProgressDialog(this)
        dialog.setTitle("Please wait")
        dialog.setIcon(R.drawable.baseline_save_alt_24)
        dialog.setMessage("Downloading image")

        var bitmapimg: Bitmap? = null
        lifecycleScope.launch {

            bitmapimg = img?.let { getbitmap(it, this@FullphotoshowActivity) }!!


            binding.btnsave.setOnClickListener {
                dialog.show()

                addphoto(bitmapimg!!)

            }


            binding.sharebtn.setOnClickListener {

                var intent=Intent()
                intent.action=Intent.ACTION_SEND
                intent.type="image/*"
                var screenshotUri =getImageUri(this@FullphotoshowActivity,bitmapimg!!)

                intent.putExtra(Intent.EXTRA_STREAM,screenshotUri)
                startActivity(Intent.createChooser(intent,"Share your image ..."))


            }
        }



        binding.btnback.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))

        }


    }

  private  fun addphoto(bitmap: Bitmap) {

      if (ContextCompat.checkSelfPermission(
              this,
              android.Manifest.permission.WRITE_EXTERNAL_STORAGE
          ) == PackageManager.PERMISSION_GRANTED
      ) {

          if (bitmap != null) {
              var fos: OutputStream? = null

              try {
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                      val resolver = contentResolver
                      val contentvalue = ContentValues()
                      contentvalue.put(
                          MediaStore.MediaColumns.DISPLAY_NAME,
                          "Image_" + ".jpg"
                      )
                      contentvalue.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                      contentvalue.put(
                          MediaStore.MediaColumns.RELATIVE_PATH,
                          Environment.DIRECTORY_PICTURES + File.separator + "Testfolder"
                      )
                      val imaguri = resolver.insert(
                          MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                          contentvalue
                      )
                      fos = resolver.openOutputStream(Objects.requireNonNull(imaguri)!!)!!

                      Objects.requireNonNull<OutputStream>(fos)
                      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                      dialog.dismiss()
                      Toast.makeText(this, "image downloaded succeses", Toast.LENGTH_SHORT)
                          .show()


                  }

              } catch (e: Exception) {
                  e.printStackTrace()
                  Toast.makeText(this, "image not save", Toast.LENGTH_SHORT).show()
                  dialog.dismiss()

              }

          }
      } else {
          Toast.makeText(this, "please Allow Permisstions  ", Toast.LENGTH_SHORT).show()
          dialog.dismiss()
      }

    }


    private suspend fun getbitmap(url: String, context: Context): Bitmap {

        val loading = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url).build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap

    }


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }



    }








