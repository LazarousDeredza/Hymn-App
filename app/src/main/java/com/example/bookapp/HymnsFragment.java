package com.example.bookapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HymnsFragment extends Fragment {

    private RecyclerView myrecyclerView;
     ArrayList<Hymns> lstHymn ;
    RecyclerViewAdapter recyclerAdapter;

    public HymnsFragment() {
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.hymns_fragment, container, false);
        myrecyclerView=(RecyclerView) rootView.findViewById(R.id.hymns_fragment);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);



        lstHymn=new ArrayList<>();


        String[] tittles = getResources().getStringArray(R.array.hymns_titles);
        String[] content =getResources().getStringArray(R.array.hymn_content);
        for (int i = 0; i < tittles.length; i++) {
            lstHymn.add(new Hymns(tittles[i],i+1,content[i]));

        }

        recyclerAdapter=new RecyclerViewAdapter(lstHymn);

        myrecyclerView.setAdapter(recyclerAdapter);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
       inflater.inflate(R.menu.actionbar_menu,menu);
       MenuItem menuItem=menu.findItem(R.id.search);
       SearchView searchView=(SearchView) menuItem.getActionView();
       searchView.setMaxWidth(Integer.MAX_VALUE);
       searchView.setQueryHint("Search hymn...");

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               Log.d("typed",newText);

               recyclerAdapter.getFilter().filter(newText);


               return true;
           }
       });

    }
}