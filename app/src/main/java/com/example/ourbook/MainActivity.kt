package com.example.ourbook

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ourbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DatabaseHelper
    private lateinit var Adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        db = DatabaseHelper(this)
        Adapter = Adapter(db.getAllBooks(), this)
        setContentView(binding.root)

        binding.booksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.booksRecyclerView.adapter = Adapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        binding.aboutButton.setOnClickListener{
            val intent = Intent(this, Aboutpage::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Adapter.refreshData(db.getAllBooks())
    }
}