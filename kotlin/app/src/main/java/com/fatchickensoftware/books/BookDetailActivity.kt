package com.fatchickensoftware.books

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.fatchickensoftware.books.databinding.ActivityBookDetailBinding

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [BookListActivity].
 */
class BookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityBookDetailBinding.inflate(layoutInflater, null)
        binding.backgroundColor = ContextCompat.getColor(this, R.color.transparent)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        (intent?.getSerializableExtra(BookDetailFragment.INTENT_VOLUME) as? Volume)?.let { volume ->
            binding.volumeInfo = volume.volumeInfo
            binding.textColor = ContextCompat.getColor(this, R.color.white)

            val fragment = BookDetailFragment.createInstance(volume)
            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, BookListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    companion object {
        fun createInstance(context: Context, volume: Volume): Intent =
            Intent(context, BookDetailActivity::class.java).apply {
                putExtra(BookDetailFragment.INTENT_VOLUME, volume)
            }
    }
}
