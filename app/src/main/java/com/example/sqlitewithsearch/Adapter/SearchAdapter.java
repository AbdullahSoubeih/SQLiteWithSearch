package com.example.sqlitewithsearch.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitewithsearch.Database.Database;
import com.example.sqlitewithsearch.Model.Friend;
import com.example.sqlitewithsearch.R;
import com.example.sqlitewithsearch.UpdateFriendActivity;

import java.io.ByteArrayOutputStream;
import java.util.List;


class SearchViewHolder extends RecyclerView.ViewHolder{



    public TextView name,address,phone,email;
    public ImageView deleteBtn,editBtn,personImagee;


    public SearchViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.name);
        address = (TextView)itemView.findViewById(R.id.address);
        phone = (TextView)itemView.findViewById(R.id.phone);
        email = (TextView)itemView.findViewById(R.id.email);

        deleteBtn = (ImageView)itemView.findViewById(R.id.deleteBtn);
        editBtn = (ImageView)itemView.findViewById(R.id.editBtn);
        personImagee = (ImageView)itemView.findViewById(R.id.personImagee);



    }
}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{

     private Activity activity;
     private List<Friend> friends;

    private Database database;

    private Bitmap imageBitmap ;

    public SearchAdapter(Activity activity, List<Friend> friends) {
        this.activity = activity;
        this.friends = friends;

       database = new Database(activity);

    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item,parent,false);

        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, final int position) {
      holder.name.setText(friends.get(position).getName());
      holder.address.setText(friends.get(position).getAddress());
      holder.email.setText(friends.get(position).getEmail());
      holder.phone.setText(friends.get(position).getPhone());
      imageBitmap = BitmapFactory.decodeByteArray(friends.get(position).getPersonImage(), 0, friends.get(position).getPersonImage().length);
      holder.personImagee.setImageBitmap(imageBitmap);
      //imageBitmap = ((BitmapDrawable)holder.personImagee.getDrawable()).getBitmap();


      holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              database.deleteFriend(friends.get(position).getName());
              friends.remove(position);
              notifyItemRemoved(position);
          }
      });

      holder.editBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //Convert Bitmap to byte array
              // imageBitmap = friends.get(position).getPersonImage();
             // imageBitmap = ((BitmapDrawable)holder.personImagee.getDrawable()).getBitmap();
             // ByteArrayOutputStream stream = new ByteArrayOutputStream();
             // imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
              //byte[] byteArray = friends.get(position).getPersonImage();

             // Intent in1 = new Intent(activity, Activity2.class);
              //in1.putExtra("image",byteArray);

              activity.startActivity(new Intent(activity, UpdateFriendActivity.class)
                     .putExtra("oldName_Key",holder.name.getText().toString())
                     .putExtra("oldAddress_Key",holder.address.getText().toString())
                     .putExtra("oldEmail_Key",holder.email.getText().toString())
                     .putExtra("oldPhone_Key",holder.phone.getText().toString())
                     .putExtra("oldImage_Key",friends.get(position).getPersonImage())


             );

              // Animation between Activities
             activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

          }
      });

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(activity,"Friend Name is: "+friends.get(position).getName(),Toast.LENGTH_SHORT).show();
          }
      });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
