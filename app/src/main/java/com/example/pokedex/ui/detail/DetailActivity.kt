package com.example.pokedex.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityDetailBinding
import com.example.pokedex.ui.main.MainActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_detail)

        binding.arrowBack.setOnClickListener{
            intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
