package com.fatchickensoftware.books

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import com.fatchickensoftware.books.databinding.ActivityBookListBinding
import kotlinx.android.synthetic.main.activity_book_detail.*
import org.koin.android.ext.android.inject

class BookListActivity : AppCompatActivity() {

    private val booksAPI: BooksAPI by inject()

    private var twoPane: Boolean = false
    private lateinit var binding: ActivityBookListBinding
    private lateinit var adapter: BookAdapter
    private var searchQuery: String = ""
    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = BookAdapter(layoutInflater)
        adapter.setHasStableIds(true)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (dy > 0 && linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1 && loading.not()) {
                    loadMore()
                }
            }
        })

        binding.searchView.apply {
            setIconifiedByDefault(false)
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(query: String?): Boolean = false

                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchBooks(query)
                    binding.searchView.clearFocus() // Prevent double call
                    return true
                }
            })
            requestFocus()
        }
    }

    private fun searchBooks(query: String?) {
        if (loading.not()) {
            adapter.content = emptyList()
            adapter.notifyDataSetChanged()
            loading = false
            query?.let {
                searchQuery = it
                loadMore()
            }
        }
    }

    private fun loadMore() {
        loading = true

        booksAPI.search(searchQuery, adapter.itemCount, MAX_RESULTS) { response ->
            runOnUiThread {
                if (isFinishing.not() && isDestroyed.not()) {
                    response?.items?.takeIf { it.isNotEmpty() }?.let { additions ->
                        val itemCount = adapter.itemCount
                        adapter.content += additions
                        adapter.notifyItemRangeInserted(itemCount, additions.size)
                    }
                    loading = false
                }
            }
        }
    }
}

private const val MAX_RESULTS = 10
