package com.fatchickensoftware.books

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fatchickensoftware.books.databinding.FragmentBookDetailBinding

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [BookListActivity]
 * in two-pane mode (on tablets) or a [BookDetailActivity]
 * on handsets.
 */
class BookDetailFragment : Fragment() {

    private var volume: Volume? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        volume = (arguments?.getSerializable(BookDetailFragment.INTENT_VOLUME) as? Volume)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBookDetailBinding.inflate(layoutInflater, container, false)
        binding.volumeInfo = volume?.volumeInfo
        return binding.root
    }

    companion object {
        fun createInstance(volume: Volume): BookDetailFragment =
            BookDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BookDetailFragment.INTENT_VOLUME, volume)
                }
            }

        const val INTENT_VOLUME = "volume"
    }
}
