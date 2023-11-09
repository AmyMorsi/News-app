package com.example.signuploginfirebase;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {



    private Context context;

    private List<DataClass> dataList;
    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImg()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getDatatitle());
        holder.recDesc.setText(dataList.get(position).getDatadesc());
        holder.recTemp.setText(dataList.get(position).getTemp());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImg());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDatadesc());
                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getDatatitle());
                intent.putExtra("Temperature", dataList.get(holder.getAdapterPosition()).getTemp());
                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.like.getText().equals("Like"))    holder.like.setText("liked");
                else holder.like.setText("Like");
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, CommentActivity.class);
                context.startActivity(intent);


            }
        });
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the post id
                String post_id=dataList.get(holder.getAdapterPosition()).getKey();

                // get the signed in user id
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = auth.getCurrentUser();


                //call the function
                savePost(post_id,currentUser.getUid());




            }
        });





    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }


    public static void savePost(String postId, String userId) {
        DatabaseReference savedPostsRef = FirebaseDatabase.getInstance().getReference("savedPosts");
        DatabaseReference userSavedPostsRef = savedPostsRef.child(userId).push();

        Map<String, Object> savedPost = new HashMap<>();
        savedPost.put("postId", postId);

        userSavedPostsRef.setValue(savedPost);

    }
    public void displaySavedPosts(String userId) {
        DatabaseReference savedPostsRef = FirebaseDatabase.getInstance().getReference("savedPosts");
        DatabaseReference userSavedPostsRef = savedPostsRef.child(userId);

        userSavedPostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String postId = postSnapshot.child("postId").getValue(String.class);

                    DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference("posts").child(postId);
                    postsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot postSnapshot) {
                            if (postSnapshot.exists()) {
                                Post post = postSnapshot.getValue(Post.class);
                                // Display the post on the saved posts page
                                // ...
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d(TAG, "Error getting post document: " + databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Error getting saved posts: " + databaseError.getMessage());
            }
        });
    }


}



class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recTitle, recDesc, recTemp;
    CardView recCard;
    Button like;
    Button comment;
    Button save;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);
        recTitle = itemView.findViewById(R.id.recTitle);
        recTemp = itemView.findViewById(R.id.recTemp);
        like=itemView.findViewById(R.id.likeButton);
        comment=itemView.findViewById(R.id.commentButton);
        save=itemView.findViewById(R.id.saveButton);
    }



}

