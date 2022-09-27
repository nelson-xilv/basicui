package com.xilv.basicui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xilv.basicui.databinding.ActivityMainBinding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setContentView(binding.root)

        binding.buttonMain.setOnClickListener {
            onGetRandomImagePressed()
        }

        binding.keywordRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            onGetRandomImagePressed()
        }
    }

    private fun onGetRandomImagePressed(): Boolean {
        val checkedId = binding.keywordRadioGroup.checkedRadioButtonId
        val keyword = binding.keywordRadioGroup.findViewById<RadioButton>(checkedId).text.toString()
        val encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
        binding.progressBar.visibility = View.VISIBLE
        Glide.with(this)
            .load("https://source.unsplash.com/random/800x600?$$encodedKeyword")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?,
                                          target: Target<Drawable>?,
                                          isFirstResource: Boolean): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?,
                                             target: Target<Drawable>?, dataSource: DataSource?,
                                             sFirstResource: Boolean): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(binding.imageView)

        return false
    }

}