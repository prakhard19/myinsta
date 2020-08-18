package com.example.myinstaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    EditText mEditText,mEditText1;
    Button b1;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText=findViewById(R.id.editTextTextPersonName2);
        mEditText1=findViewById(R.id.editTextTextPassword2);
        b1=findViewById(R.id.button2);
        mTextView=findViewById(R.id.textView2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser appUser = new ParseUser();
                appUser.setUsername(mEditText.getText().toString());
                appUser.setPassword(mEditText1.getText().toString());
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(getApplicationContext(),"signed up",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });


            }
        });

mTextView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,MainActivity2.class);
        startActivity(intent);
    }
});

    }
    public void rootLayoutTapped(View view){
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }
}