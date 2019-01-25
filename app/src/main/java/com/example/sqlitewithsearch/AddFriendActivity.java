package com.example.sqlitewithsearch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sqlitewithsearch.Database.Database;
import com.example.sqlitewithsearch.Model.Friend;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;

public class AddFriendActivity extends AppCompatActivity {

    EditText NameText,AddressText,EmailText,PhoneText;
    ImageView personImage;

    Database database;

    Bitmap decodeStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        NameText = findViewById(R.id.NameText);
        AddressText = findViewById(R.id.AddressText);
        EmailText = findViewById(R.id.EmailText);
        PhoneText = findViewById(R.id.PhoneText);
        personImage = findViewById(R.id.personImage);
        personImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        //Init DB
        database = new Database(this);

    }

    public void insertFriend(View view) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        decodeStream.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

       Friend friend = new Friend(0,NameText.getText().toString(),AddressText.getText().toString(),EmailText.getText().toString(),PhoneText.getText().toString(),byteArray);
       database.insertFriend(friend);

       // database.insertFriend(NameText.getText().toString(),AddressText.getText().toString(),EmailText.getText().toString(),PhoneText.getText().toString());

        finish();
        startActivity(new Intent(AddFriendActivity.this,MainActivity.class));

    }


    // Animation between Activities
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void chooseImage(){
        Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
        intentImg.setType("image/*");
        startActivityForResult(intentImg,100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100){ //if process that have requestCode(100) is done(ok)

            Uri uri = data.getData();   //then get data that retrieved from this request

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                 decodeStream = BitmapFactory.decodeStream(inputStream);
                personImage.setImageBitmap(decodeStream); //set Bitmap image in ImageView(personImage)
            } catch (Exception ex) {
                Log.e("ex",ex.getMessage());
            }

        }

    }
}
