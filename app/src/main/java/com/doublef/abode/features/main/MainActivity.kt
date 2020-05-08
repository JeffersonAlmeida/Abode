package com.doublef.abode.features.main


import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.doublef.abode.*
import com.doublef.abode.features.details.DetailsActivity
import com.doublef.abode.features.main.MainActivity.Constants.Companion.COLUMNS
import com.doublef.abode.features.main.MainActivity.Constants.Companion.ITEM
import com.doublef.abode.features.main.MainActivity.Constants.Companion.REQUEST_IMAGE
import com.doublef.abode.features.main.MainActivity.Constants.Companion.WRITE_EXTERNAL
import com.doublef.abode.model.Photo
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), Adapter.Listener {

    private var imageFilePath = ""
    private val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupBindings()
        requestPermissions()
    }

    private fun setupBindings() {
        fab.setOnClickListener {
            startCamera()
        }
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, COLUMNS)

        mainViewModel.photos().observe(this, Observer {
            recyclerView.adapter =
                Adapter(it, this)
        })
    }

    private fun requestPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
        if (permission != PERMISSION_GRANTED) {
            requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL)
        }
    }

    private fun savePhoto(photo: String){
        mainViewModel.savePhoto(photo)
    }

    private fun startCamera() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (pictureIntent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return
            }
            val photoUri = FileProvider.getUriForFile(this, "$packageName.provider", photoFile!!)
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(pictureIntent, REQUEST_IMAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == WRITE_EXTERNAL && grantResults.isNotEmpty()) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                savePhoto(imageFilePath)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        imageFilePath = image.absolutePath
        return image
    }

    class Constants {
        companion object {
            const val WRITE_EXTERNAL = 99
            const val REQUEST_IMAGE = 101
            const val COLUMNS = 2
            const val ITEM = "ITEM"
        }
    }

    override fun onClickItem(photo: Photo) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(ITEM, photo)
        startActivity(intent)
    }
}

