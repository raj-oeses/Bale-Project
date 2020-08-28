package com.bhawani.bhawanitraders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.UUID;

public class Delete extends AppCompatActivity implements View.OnClickListener {
    StorageReference mStorage;
    DatabaseReference mRefrence;
    Button select,upload;
    ImageView showimagetemp;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        select=findViewById(R.id.selecttheimageindelete );
        upload=findViewById(R.id.uploading);
        showimagetemp=findViewById(R.id.showimagetemp);
        mStorage= FirebaseStorage.getInstance().getReference("uploads");
        mRefrence=FirebaseDatabase.getInstance().getReference("uploads");


        select.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selecttheimageindelete:
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
                break;
            case R.id.uploading:
                UploadingTheImage();
                break;
        }
    }

    private String getFileExtention( Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&& data.getData()!=null &&data!=null && resultCode==RESULT_OK){
            uri=data.getData();
            showimagetemp.setImageResource(0);
            showimagetemp.setImageURI(uri);
        }
        else {
            Toast.makeText(this, "Error File", Toast.LENGTH_SHORT).show();
        }
    }
    private void UploadingTheImage() {
        /*//StorageReference filerefrence=mStorage.child(System.currentTimeMillis()+"."+getFileExtention(uri));
        StorageReference filerefrence = mStorage.child("images/rivers.jpg");
        filerefrence.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Delete.this, "File is uploaded", Toast.LENGTH_SHORT).show();
                showimagetemp.setImageResource(0);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Delete.this, "On Progress...", Toast.LENGTH_SHORT).show();
                double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressBar.setProgress((int)progress);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Delete.this, "Failed to upload", Toast.LENGTH_SHORT).show();
            }
        });*/
        if(uri!=null){
            final ProgressDialog progressDialog= new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            //StorageReference ref = mStorage.child("images/"+ UUID.randomUUID().toString());
            StorageReference ref = mStorage.child(System.currentTimeMillis()+"."+getFileExtention(uri));
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    showimagetemp.setImageResource(0);
                    //showimagetemp.setImageResource(0);
                    Toast.makeText(Delete.this,"Image Uploaded!!",Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Delete.this, "Failed..", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress= (100.0* taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+ (int)progress + "%");
                }
            });
        }

    }
}