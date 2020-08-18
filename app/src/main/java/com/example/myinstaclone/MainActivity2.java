package com.example.myinstaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.IllegalFormatCodePointException;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    EditText mEditText3,mEditText4;
    Button b2;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mEditText3=findViewById(R.id.editTextTextPersonName2);
        mEditText4=findViewById(R.id.editTextTextPassword2);
        b2=findViewById(R.id.button2);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mEditText4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==keyEvent.KEYCODE_ENTER && keyEvent.getAction()== KeyEvent.ACTION_DOWN ){

         onClick(b2);         }
                return false;
            }
        });


    }

    @Override
    public void onClick(View view) {
        mProgress.show();
        ParseUser.logInInBackground(mEditText3.getText().toString(), mEditText4.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
              if(user !=null && e==null){
                  Toast.makeText(getApplicationContext(),"LOgged in",Toast.LENGTH_LONG).show();
mProgress.dismiss();
                  Intent intent=new Intent(MainActivity2.this,MainActivity3.class);
                  startActivity(intent);
              }
              else
              {  mProgress.dismiss();
                  Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

              }
            }
        });
    }
    public void rootLayoutTapped(View view){
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }
}