package com.example.atul_.eatit;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.atul_.eatit.Database.Database;
import com.example.atul_.eatit.model.Food;
import com.example.atul_.eatit.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    SQLiteDatabase db;
    Order o;
    Database d;



    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;
    Food currentFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);


        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Food");
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);


        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);



        btnCart.setOnClickListener(new View.OnClickListener() {

             @Override


                                       public void onClick(View view) {
                 db=openOrCreateDatabase("EatIt.db",Context.MODE_PRIVATE,null);


                 Toast.makeText(FoodDetail.this, "Added to cart ", Toast.LENGTH_SHORT).show();





                                       }

                                   });


        food_description = (TextView)findViewById(R.id.food_description);
        food_price = (TextView)findViewById(R.id.food_price);
        food_name = (TextView)findViewById(R.id.food_name);
        food_image = (ImageView)findViewById(R.id.img_food);




        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
     // collapsingToolbarLayout = setExpandedTitleAppearance(R.style.ExpandedAppbar);
      //  collapsingToolbarLayout = setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if (getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty()) {
            getDetailFood(foodId);
        }
     }

        private void getDetailFood(String foodId){
           foods.child(foodId).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   currentFood=dataSnapshot.getValue(Food.class);


                   Picasso.with(getBaseContext()).load(currentFood.getImage())
                   .into(food_image);
                   collapsingToolbarLayout.setTitle(currentFood.getName());
                   food_price.setText(currentFood.getPrice());
                   food_name.setText(currentFood.getName());
                   food_description.setText(currentFood.getDescription());

               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
    }



}
