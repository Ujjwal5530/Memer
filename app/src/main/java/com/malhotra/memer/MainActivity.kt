package com.malhotra.memer

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.malhotra.memer.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.newMeme.setOnClickListener {
            getData()
        }
    }

    private fun getData() {

        val progressDialog = ProgressDialog(this)

        progressDialog.setMessage("Loading...")
        progressDialog.show()

        RetrofitInstance.apiInterface.getData().enqueue(object : Callback<ResponseClass>{
            override fun onResponse(call: Call<ResponseClass>, response: Response<ResponseClass>) {

                binding.memeTitle.text = response.body()?.title
                binding.memeAuthor.text = response.body()?.author

                Glide.with(this@MainActivity).load(response.body()?.url).into(binding.memeImage)
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseClass>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()

            }

        })
    }
}