package com.example.myinstaclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class UsersTab extends Fragment {
private ListView mListView;
private ArrayList mArrayList;
private ArrayAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_users_tab, container, false);


        mListView=view.findViewById(R.id.Listview);
        mArrayList=new ArrayList();
        mAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,mArrayList);

        ParseQuery<ParseUser>parseQuery=ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser());
       parseQuery.findInBackground(new FindCallback<ParseUser>() {
           @Override
           public void done(List<ParseUser> objects, ParseException e) {
              if(objects.size()>0 && e==null){
                  for(ParseUser parseUser:objects){
                      mArrayList.add(parseUser.getUsername());
                  }

mListView.setAdapter(mAdapter);
              }



           }
       });

    return view;
    }

}