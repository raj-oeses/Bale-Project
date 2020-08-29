package com.bhawani.bhawanitraders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Delete extends AppCompatActivity{
    DatabaseReference mDatabaseRef;
    ImageView imageView;
    StorageReference reference;
    Uri mImageUri;
    Button choose,upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);


    }

    public void uselessnow(){/*reference=FirebaseStorage.getInstance().getReference("Child");
        mDatabaseRef=FirebaseDatabase.getInstance().getReference().child("Temp");
        imageView=findViewById(R.id.deletemadekhaune);
        choose=findViewById(R.id.choosebetween);
        upload =findViewById(R.id.UpoladToFirebase);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectTheImage();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadGardeneAba();
            }
        });


    }



    private void SelectTheImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(requestCode==1 && data!=null &&resultCode==RESULT_OK &&data.getData() !=null){
                mImageUri=data.getData();
                Picasso.get().load(mImageUri).into(imageView);

        }

    }
    private void UploadGardeneAba() {
        StorageReference filestorage=reference.child(System.currentTimeMillis()+"."+getFileExtention(mImageUri));
        filestorage.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot snapshot) {
                String link= snapshot.getMetadata().getReference().getDownloadUrl().toString();

            }
        });

    }

    private String getFileExtention(Uri mImageUri) {
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(mImageUri));*/}

}