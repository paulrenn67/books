package com.fatchickensoftware.books;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatchickensoftware.books.databinding.FragmentBookDetailBinding;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link BookListActivity}
 * in two-pane mode (on tablets) or a {@link BookDetailActivity}
 * on handsets.
 */
public class BookDetailFragment extends Fragment {

    private Volume volume = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            volume = (Volume) arguments.getSerializable(BookDetailFragment.INTENT_VOLUME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentBookDetailBinding binding = FragmentBookDetailBinding.inflate(getLayoutInflater(), container, false);
        if (volume != null) {
            binding.setVolumeInfo(volume.volumeInfo);
        }
        return binding.getRoot();
    }

    public static BookDetailFragment createInstance(Volume volume) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BookDetailFragment.INTENT_VOLUME, volume);
        fragment.setArguments(bundle);
        return fragment;
    }

    static final String INTENT_VOLUME = "volume";
}
