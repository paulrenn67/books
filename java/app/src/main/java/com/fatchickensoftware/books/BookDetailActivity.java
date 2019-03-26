package com.fatchickensoftware.books;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.fatchickensoftware.books.databinding.ActivityBookDetailBinding;

import static com.fatchickensoftware.books.BookDetailFragment.INTENT_VOLUME;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link BookListActivity}.
 */
public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityBookDetailBinding binding = ActivityBookDetailBinding.inflate(getLayoutInflater(), null);
        binding.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        setContentView(binding.getRoot());

        setSupportActionBar(binding.detailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        Volume volume = intent != null ? (Volume) intent.getSerializableExtra(INTENT_VOLUME) : null;
        if (volume != null) {
            binding.setVolumeInfo(volume.volumeInfo);
            binding.setTextColor(ContextCompat.getColor(this, R.color.white));

            Fragment fragment = BookDetailFragment.createInstance(volume);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, BookListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent createInstance(Context context, Volume volume) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(BookDetailFragment.INTENT_VOLUME, volume);
        return intent;
    }
}
