package com.example.myinstaclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class ProfileTab extends Fragment {

    EditText mname,mbio,mprof;
    Button mupdate;

    public ProfileTab() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_tab, container, false);
        mbio=view.findViewById(R.id.editTextTextPersonName4);
        mname=view.findViewById(R.id.editTextTextPersonName3);
        mprof=view.findViewById(R.id.editTextTextPersonName5);
        mupdate=view.findViewById(R.id.button5);
        final ParseUser parseUser=ParseUser.getCurrentUser();
        if(parseUser.get("ProfileName")==null){
            mname.setText("");
        }
        else {
            mname.setText(parseUser.get("ProfileName").toString());
        }
        if(parseUser.get("Bio")==null){
            mbio.setText("");
        }
        else{
            mbio.setText(parseUser.get("Bio").toString());

        }
      if(parseUser.get("Profession")==null){
          mprof.setText("");

      }
      else{
          mprof.setText(parseUser.get("Profession").toString());

      }

        mupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseUser.put("ProfileName",mname.getText().toString());
                parseUser.put("Bio",mbio.getText().toString());
                parseUser.put("Profession",mprof.getText().toString());
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(getContext(),"UPDATED",Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });

        return view;
    }
}