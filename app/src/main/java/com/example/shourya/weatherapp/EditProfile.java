package com.example.shourya.weatherapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity implements View.OnClickListener  {


    //for changing pic
    private static final int SELECT_PICTURE = 100;


    Button edit_btn;
    Button save;

    CircleImageView image;
    Button change_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        //change dp
        findViewById(R.id.change_dp).setOnClickListener(this);


        edit_btn=(Button)findViewById(R.id.edit_btn);
        save=(Button)findViewById(R.id.save);

        final TextView Text_name=(TextView)findViewById(R.id.Text_name);
        final TextView Text_home=(TextView)findViewById(R.id.Text_home);
        final TextView Text_work=(TextView)findViewById(R.id.Text_work);
        final TextView Text_pass=(TextView)findViewById(R.id.Text_pass);
        final TextView Text_DOB=(TextView)findViewById(R.id.Text_DOB);


        final EditText Edit_name=(EditText)findViewById(R.id.Edit_name);
        Edit_name.setVisibility(View.GONE);
        final EditText Edit_home=(EditText)findViewById(R.id.Edit_home);
        Edit_home.setVisibility(View.GONE);
        final EditText Edit_work=(EditText)findViewById(R.id.Edit_work);
        Edit_work.setVisibility(View.GONE);
        final EditText Edit_pass=(EditText)findViewById(R.id.Edit_pass);
        Edit_pass.setVisibility(View.GONE);

        final EditText Edit_DOB=(EditText)findViewById(R.id.Edit_DOB);
        Edit_DOB.setVisibility(View.GONE);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_name.setVisibility(View.GONE);
                Edit_home.setVisibility(View.GONE);
                Edit_work.setVisibility(View.GONE);
                Edit_pass.setVisibility(View.GONE);
                Edit_DOB.setVisibility(View.GONE);

                Text_name.setVisibility(View.VISIBLE);
                Text_home.setVisibility(View.VISIBLE);
                Text_work.setVisibility(View.VISIBLE);
                Text_pass.setVisibility(View.VISIBLE);
                Text_DOB.setVisibility(View.VISIBLE);


            }
        });


        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text_name.setVisibility(View.GONE);
                Text_home.setVisibility(View.GONE);
                Text_work.setVisibility(View.GONE);
                Text_pass.setVisibility(View.GONE);
                Text_DOB.setVisibility(View.GONE);

                Edit_name.setVisibility(View.VISIBLE);
                Edit_home.setVisibility(View.VISIBLE);
                Edit_work.setVisibility(View.VISIBLE);
                Edit_pass.setVisibility(View.VISIBLE);
                Edit_DOB.setVisibility(View.VISIBLE);


            }
        });

    }






    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    //Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    ((ImageView) findViewById(R.id.image)).setImageURI(selectedImageUri);
                }
            }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public void onClick(View v) {
        openImageChooser();

    }




}
