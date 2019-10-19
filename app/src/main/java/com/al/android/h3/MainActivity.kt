package com.al.android.h3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uber.h3core.H3Core
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var h3: H3Core

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        h3 = H3Core.newSystemInstance()
        initViews()
    }

    private fun initViews() {
        val testLat = 37.775938728915946
        val testLng = -122.41795063018799
        val testResolution = 9

        edittext_latitude.setText(testLat.toString())
        edittext_longitude.setText(testLng.toString())
        edittext_resolution.setText(testResolution.toString())

        button_test.setOnClickListener {
            val lat = edittext_latitude.text.toString().toDouble()
            val lng = edittext_longitude.text.toString().toDouble()
            val resolution = edittext_resolution.text.toString().toInt()

            val hexAddr = h3.geoToH3Address(lat, lng, resolution)
            val hex = h3.stringToH3(hexAddr)
            val kRingsResult = h3.kRings(hexAddr, 1)

            textview_geo_to_h3.text = "geoToH3Address(): $hexAddr"
            textview_string_to_h3.text = "stringToH3(): $hex"
            textview_krings.text = "kRings(): $kRingsResult"
        }
    }

}
