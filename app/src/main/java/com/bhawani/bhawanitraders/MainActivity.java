package com.bhawani.bhawanitraders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.android.material.slider.Slider;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int image[] = {R.drawable.owner,R.drawable.ic_with_basket_full_name,R.drawable.display2};

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
        //SliderClass();
    }

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

    public void flipperimage(int image) {
        ImageView imageView = new ImageView(this);

        imageView.setBackgroundResource(image);

        flipper.addView(imageView);
        flipper.setFlipInterval(4000);   //10 sec ko lagi
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

}