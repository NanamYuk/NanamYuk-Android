package com.irfan.nanamyuk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irfan.nanamyuk.adapter.PilihAdapter
import com.irfan.nanamyuk.adapter.TanahAdapter
import com.irfan.nanamyuk.data.Tanah
import com.irfan.nanamyuk.databinding.ActivityFormBinding
import com.irfan.nanamyuk.databinding.ActivityHomeBinding
import com.irfan.nanamyuk.ui.pilih.PilihActivity

class FormActivity : AppCompatActivity() {

    private lateinit var jenisTanah: RecyclerView
    private val list = ArrayList<Tanah>()
    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jenisTanah = findViewById(R.id.jenis_tanah)
        jenisTanah.setHasFixedSize(true)

        list.addAll(listTanah)

        jenisTanah.layoutManager = LinearLayoutManager(this)
        val singleAdapter = TanahAdapter(this, list)
        jenisTanah.adapter = singleAdapter

        binding.nextButton.setOnClickListener {
            val intent = Intent(this, PilihActivity::class.java)
            startActivity(intent)
        }
    }

    private val listTanah: ArrayList<Tanah>
        get() {
            val dataNama = resources.getStringArray(R.array.data_nama_tanah)
            val dataDeskripsi = resources.getStringArray(R.array.data_deskripsi_tanah)
            val dataUrlPhoto = resources.getStringArray(R.array.data_urlPhoto)
            val list = ArrayList<Tanah>()
            for (i in dataNama.indices) {
                val tanah = Tanah(dataNama[i],dataDeskripsi[i], dataUrlPhoto[i])
                list.add(tanah)
            }
            return list
        }
}