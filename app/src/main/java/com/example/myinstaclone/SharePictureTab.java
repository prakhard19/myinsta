package com.example.myinstaclone;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


public class SharePictureTab extends Fragment  implements View.OnClickListener{
ImageView mImageView5;
EditText mEditText9;
Button mButton9;
    Bitmap recievedimage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share_picture_tab, container, false);


    mButton9=view.findViewById(R.id.share);
        mEditText9=view.findViewById(R.id.desc);
        mImageView5=view.findViewById(R.id.ishare);

mImageView5.setOnClickListener(SharePictureTab.this);
        mButton9.setOnClickListener(SharePictureTab.this);



    return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.ishare:
                if(Build.VERSION.SDK_INT >=23 && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }
                else {
getChosenImage();
                }
                break;

            case R.id.share:
                if(recievedimage !=null){
                    if(mEditText9.getText().toString().equals("")){
                        Toast.makeText(getContext(),"please select an image",Toast.LENGTH_SHORT).show();


                    }
                    else{
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        recievedimage.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                        byte[] bytes=byteArrayOutputStream.toByteArray();
                        ParseFile parseFile=new ParseFile("img.JPEG",bytes);
                        ParseObject parseObject=new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("img_desc",mEditText9.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                        final ProgressDialog progressDialog=new ProgressDialog(getContext());
                        progressDialog.setMessage("Loading.....");
                        progressDialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){

                                    Toast.makeText(getContext(),"Image saved",Toast.LENGTH_SHORT).show();
                                }


                                else{

                                    Toast.makeText(getContext(),"Something Wrong",Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        });






                        
                    }


                }
                else{

                    Toast.makeText(getContext(),"please select an image",Toast.LENGTH_SHORT).show();
                }


        }
    }

    private void getChosenImage() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if(requestCode==1000){


        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

            getChosenImage();
        }
    }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2000){
            if(resultCode== Activity.RESULT_OK){

                try{
                    Uri selectedimage=data.getData();
                    String[] filepathcolumn={MediaStore.Images.Media.DATA};
                    Cursor cursor=getActivity().getContentResolver().query(selectedimage,filepathcolumn,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex=cursor.getColumnIndex(filepathcolumn[0]);
                    String picpath=cursor.getString(columnIndex);
                    cursor.close();
                     recievedimage= BitmapFactory.decodeFile(picpath);
                    mImageView5.setImageBitmap(recievedimage);







                }

               catch (Exception e){
                    e.printStackTrace();


               }



            }



        }
    }
}