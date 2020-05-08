package com.doublef.abode.features.details

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.doublef.abode.model.Photo
import com.doublef.abode.R
import com.doublef.abode.features.main.MainActivity.Constants.Companion.ITEM
import kotlinx.android.synthetic.main.activity_details.*
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val photo: Photo = intent.getSerializableExtra(ITEM) as Photo
        imageView.setImageURI(Uri.parse(photo.url))
        val simpleDateFormat = SimpleDateFormat("yyyy MM dd HH:mm:ss", Locale.getDefault())
        date.text = simpleDateFormat.format(photo.date)
    }
}
