package com.example.sqlitewithsearch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sqlitewithsearch.Database.Database;
import com.example.sqlitewithsearch.Model.Friend;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class UpdateFriendActivity extends AppCompatActivity {

    EditText NameTextUpdate,AddressTextUpdate,EmailTextUpdate,PhoneTextUpdate;
    ImageView personImageUpdate;

    String oldName;
    String oldAddress;
    String oldEmail;
    String oldPhone;
    //Bitmap oldImage;


    Intent intent ;

    private Database database;

    Bitmap decodeStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_friend);

        NameTextUpdate = findViewById(R.id.NameTextUpdate);
        AddressTextUpdate = findViewById(R.id.AddressTextUpdate);
        EmailTextUpdate = findViewById(R.id.EmailTextUpdate);
        PhoneTextUpdate = findViewById(R.id.PhoneTextUpdate);
        personImageUpdate = findViewById(R.id.personImageUpdate);
        personImageUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImageUpdate();
            }
        });

        database = new Database(UpdateFriendActivity.this);

        intent = getIntent();

        try {

            oldName = intent.getStringExtra("oldName_Key");
            oldAddress = intent.getStringExtra("oldAddress_Key");
            oldEmail = intent.getStringExtra("oldEmail_Key");
            oldPhone = intent.getStringExtra("oldPhone_Key");
            byte[] byteArray = getIntent().getByteArrayExtra("oldImage_Key");
            decodeStream = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        } catch (Exception e) {
            e.printStackTrace();
        }


        NameTextUpdate.setText(oldName);
        AddressTextUpdate.setText(oldAddress);
        EmailTextUpdate.setText(oldEmail);
        PhoneTextUpdate.setText(oldPhone);
        personImageUpdate.setImageBitmap(decodeStream);


    }

    public void UpdateFriend(View view) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        decodeStream.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();


        Friend friend = new Friend(0,NameTextUpdate.getText().toString(),AddressTextUpdate.getText().toString(),EmailTextUpdate.getText().toString(),PhoneTextUpdate.getText().toString(),byteArray);
        database.updateFriend(oldName,friend);

        finish();
        startActivity(new Intent(UpdateFriendActivity.this,MainActivity.class));

    }


    // Animation between Activities
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void chooseImageUpdate(){
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
                personImageUpdate.setImageBitmap(decodeStream); //set Bitmap image in ImageView(personImage)
            } catch (Exception ex) {
                Log.e("ex",ex.getMessage());
            }

        }

    }
}
