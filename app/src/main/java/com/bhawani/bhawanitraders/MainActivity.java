package com.bhawani.bhawanitraders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    ViewFlipper flipper;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int image[] = {R.drawable.owner,R.drawable.ic_with_basket_full_name,R.drawable.display2};
        mAuth=FirebaseAuth.getInstance();

        CardView search = findViewById(R.id.search);
        CardView add = findViewById(R.id.add);
        CardView edit = findViewById(R.id.edit);
        CardView del = findViewById(R.id.delete);
        flipper = findViewById(R.id.flipper);

        search.setOnClickListener(this);
        add.setOnClickListener(this);
        edit.setOnClickListener(this);
        del.setOnClickListener(this);
        for (int Image : image) {
            flipperimage(Image);
        }
    }
/*==========================================On click Ko lagi==============================*/
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                Intent searching = new Intent(this, Search.class);
                startActivity(searching);
                break;
            case R.id.add:
                Intent adding = new Intent(this, Add.class);
                startActivity(adding);
                break;
            case R.id.edit:
                Intent editing = new Intent(this, Edit.class);
                startActivity(editing);
                break;
            case R.id.delete:
                Intent del = new Intent(this, Delete.class);
                startActivity(del);
                break;

        }
    }

    /*==========================================For fliping image==============================*/

    public void flipperimage(int image) {
        ImageView imageView = new ImageView(this);

        imageView.setBackgroundResource(image);

        flipper.addView(imageView);
        flipper.setFlipInterval(4000);   //10 sec ko lagi
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }
    /*==========================================Option Menu ko lagi==============================*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.contact_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.contact:
                Intent abtme=new Intent(this,AboutMe.class);
                startActivity(abtme);
                return  true;
            case R.id.backtologin:
                mAuth.signOut();
                startActivity(new Intent(this,Login.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mUser=mAuth.getCurrentUser();
        if(mUser!=null){
            //There is some user
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        }
        else{
            //There is no user we need to go to the login part
            startActivity(new Intent(this,Login.class));
            finish();

        }

    }
}