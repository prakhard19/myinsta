package com.example.myinstaclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity3 extends AppCompatActivity {
private TabAdapter mAdapter;
private ViewPager mViewPager;
private Toolbar mToolbar;
private TabLayout mTabLayout;
private Bitmap mBitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mToolbar=findViewById(R.id.tool);
        setSupportActionBar(mToolbar);
        mViewPager=findViewById(R.id.page);
        mAdapter=new TabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout=findViewById(R.id.tab);
        mTabLayout.setupWithViewPager(mViewPager,false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()==R.id.logout){

            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null){

                        Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if(item.getItemId()==R.id.camera){

            if(Build.VERSION.SDK_INT >=23 && checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},3000);
            }
            else {
              CaptureImage();
            }


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode ==3000){


            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                CaptureImage();
            }
        }

    }
    private void CaptureImage() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,7000);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==7000 &&resultCode== RESULT_OK & data !=null){


                try{


                    Uri capturedimg=data.getData();


                        mBitmap = MediaStore.Images.Media.getBitmap(MainActivity3.this.getContentResolver(),capturedimg);

                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] bytes=byteArrayOutputStream.toByteArray();
                    ParseFile parseFile=new ParseFile("img.JPEG",bytes);
                    ParseObject parseObject=new ParseObject("Photo");
                    parseObject.put("picture",parseFile);
                    parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                    final ProgressDialog progressDialog=new ProgressDialog(getApplicationContext());
                    progressDialog.setMessage("Loading.....");
                    progressDialog.show();
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){



                                Toast.makeText(MainActivity3.this,"Image saved",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            }


                            else{

                                Toast.makeText(MainActivity3.this,"Something Wrong",Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();

                        }
                    });









                }

                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();


                }



            }




    }
}
