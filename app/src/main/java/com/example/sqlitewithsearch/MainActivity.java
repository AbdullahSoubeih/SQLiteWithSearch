package com.example.sqlitewithsearch;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.ListViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.sqlitewithsearch.Adapter.SearchAdapter;
import com.example.sqlitewithsearch.Database.Database;
import com.example.sqlitewithsearch.Model.Friend;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler_search;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;



    MaterialSearchBar search_barr;
    List<String> suggestList = new ArrayList<>();

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //init view
        recycler_search = (RecyclerView)findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recycler_search.setLayoutManager(layoutManager);
        recycler_search.setHasFixedSize(true);

        search_barr = (MaterialSearchBar)findViewById(R.id.search_barr);


        //Init DB
        database = new Database(this);

        //setup search bar
        search_barr.setHint("Search");
        search_barr.setCardViewElevation(10);
        loadSuggestList();
        search_barr.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<>();
                for (String search:suggestList){
                    if (search.toLowerCase().contains(search_barr.getText().toLowerCase()))      {suggest.add(search);}
                }

                search_barr.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        search_barr.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled){
                    //if close search , just restore default
                    adapter = new SearchAdapter((Activity) getBaseContext(),database.getFriends());
                    recycler_search.setAdapter(adapter);
                }

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                    startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


        // Init Adapter default set all result
        adapter = new SearchAdapter(this,database.getFriends());
        recycler_search.setAdapter(adapter);

    }

    private void startSearch(String text) {
        adapter = new SearchAdapter(this,database.getFriendByName(text));
        recycler_search.setAdapter(adapter);
    }

    private void loadSuggestList() {
        suggestList = database.getNames();
        search_barr.setLastSuggestions(suggestList);
    }



    public void addFriend(View view) {
        startActivity(new Intent(MainActivity.this,AddFriendActivity.class));

        // Animation between Activities
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}
