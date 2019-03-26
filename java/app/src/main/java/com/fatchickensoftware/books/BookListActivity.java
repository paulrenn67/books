package com.fatchickensoftware.books;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.fatchickensoftware.books.databinding.ActivityBookListBinding;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BookDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BookListActivity extends AppCompatActivity {

    private BooksAPI booksAPI = new BooksAPI(new OkHttpClient.Builder().build());
    private boolean mTwoPane = false;
    private ActivityBookListBinding binding = null;
    private BookAdapter adapter = null;
    private String searchQuery = "";
    private Boolean loading = false;
    private String apiKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(getLayoutInflater());
        adapter.setHasStableIds(true);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (dy > 0 && linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1 && !loading) {
                    loadMore();
                }
            }
        });

        SearchView searchView = binding.searchView;
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBooks(query);
                binding.searchView.clearFocus(); // Prevent double call
                return true;
            }
        });
        searchView.requestFocus();

        apiKey = getResources().getString(R.string.books_api_key);
    }

    private void searchBooks(String query) {
        if (!loading) {
            adapter.content = new ArrayList<>();
            adapter.notifyDataSetChanged();
            loading = false;
            if (query != null) {
                searchQuery = query;
                loadMore();
            }
        }
    }

    private void loadMore() {
        loading = true;
        booksAPI.search(apiKey, searchQuery, adapter.getItemCount(), MAX_RESULTS, searchCallback);
    }

    private BooksAPI.SearchCallback searchCallback = volumes ->
            runOnUiThread(() -> {
                if (!isFinishing() && !isDestroyed()) {
                    if (volumes != null) {
                        int itemCount = adapter.getItemCount();
                        adapter.content.addAll(volumes.items);
                        adapter.notifyItemRangeInserted(itemCount, volumes.items.size());
                    }
                    loading = false;
                }
            });

    private static int MAX_RESULTS = 10;
}
