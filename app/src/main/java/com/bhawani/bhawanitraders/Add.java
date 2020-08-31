package com.bhawani.bhawanitraders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Add extends AppCompatActivity implements View.OnClickListener {
    EditText item,cpperpiece,spperpiece,sppercarton,memo,barcode;
    Button scan,save,select;
    Uri mImageUri;
    ImageView imagepreview;
    private static final int PICK_IMAGE_REQUEST=2;
    private static final int CAMERA_KO=1;

    StorageReference mStorageRef;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        item=findViewById(R.id.enteritem);
        cpperpiece=findViewById(R.id.entercpperpiece);
        spperpiece=findViewById(R.id.enterspperpc);
        sppercarton=findViewById(R.id.entersppercarton);
        memo=findViewById(R.id.entermemo);
        barcode=findViewById(R.id.enterbarcode);
        scan=findViewById(R.id.barcodescanbutton);
        save=findViewById(R.id.save);
        select=findViewById(R.id.selecttheimage);
        imagepreview=findViewById(R.id.addimagepreview);

        mStorageRef=FirebaseStorage.getInstance().getReference().child("Details");
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Details");

        scan.setOnClickListener(this);
        save.setOnClickListener(this);
        select.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.barcodescanbutton:
                IntentIntegrator intentIntegrator=new IntentIntegrator(this);
                intentIntegrator.setCaptureActivity(Barcode.class);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setPrompt("Scanning...");
                intentIntegrator.initiateScan();
                break;
            case R.id.selecttheimage:


                SelectTheImage();
                break;
            case R.id.save:
                SavingTheDetails();
                break;
            default:
                Toast.makeText(this, "Choose Something..", Toast.LENGTH_SHORT).show();
        }
    }

    private void SelectTheImage() {
        AlertDialog.Builder choose=new AlertDialog.Builder(this);
         choose.setTitle("Chose the Media");
         choose.setMessage("What do You Want To Choose...");
         choose.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 Toast.makeText(Add.this, "This is camera", Toast.LENGTH_SHORT).show();
                 if(ContextCompat.checkSelfPermission(Add.this,
                         Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                     ActivityCompat.requestPermissions(Add.this,new String[]{
                             Manifest.permission.CAMERA
                     },CAMERA_KO);
                 }

                 Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 startActivityForResult(camera,CAMERA_KO);
             }
         }).setNegativeButton("Storage", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 Intent intent=new Intent();
                 intent.setType("image/*");
                 intent.setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(intent,PICK_IMAGE_REQUEST);

             }
         });
         choose.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null){
            if(result.getContents()!=null){
                barcode=findViewById(R.id.enterbarcode);
                barcode.setText(result.getContents());
            }
            else{

                Toast.makeText(this, "Error!!!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode==PICK_IMAGE_REQUEST && data!=null &&resultCode==RESULT_OK &&data.getData() !=null){
            if(requestCode==PICK_IMAGE_REQUEST){
                mImageUri=data.getData();
                imagepreview.setImageResource(0);
                Picasso.get().load(mImageUri).into(imagepreview);
            }
        }
        else if (requestCode==CAMERA_KO&&data.getExtras()!=null){
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            imagepreview.setImageBitmap(bitmap);
        }
    }
    private String getFileExtention(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    /*==========================================Storing In Firebase  String Details===========================================*/
    void StoringInFirebase(String ImageUrl) {

        HashMap<String, Object> data = new HashMap<>();
        data.put("Item", item.getText().toString().trim());
        data.put("CPPerPiece", cpperpiece.getText().toString().trim());
        data.put("SPPerPiece", spperpiece.getText().toString().trim());
        data.put("SPPerCarton", sppercarton.getText().toString().trim());
        data.put("BarCode", barcode.getText().toString().trim());
        data.put("Description", memo.getText().toString().trim());
        data.put("ImageUrl",ImageUrl);


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if (item.getText().toString().equals("")
                ||cpperpiece.getText().toString().equals("")
                ||spperpiece.getText().toString().equals("")
                ||sppercarton.getText().toString().equals("")
                ||mImageUri==null) {
            Toast.makeText(this, "Enter Mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED){
            Toast.makeText(this, "Check Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }
        else {

            databaseReference.push()
                    .setValue(data)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            item.setText("");
                            cpperpiece.setText("");
                            spperpiece.setText("");
                            sppercarton.setText("");
                            barcode.setText("");
                            memo.setText("");
                            imagepreview.setImageResource(R.drawable.image);
                            Toast.makeText(Add.this, "Done Uploading", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Add.this, "Error!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    /*==================================this is for image storage===========================================*/
    public void SavingTheDetails(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (item.getText().toString().equals("")
                ||cpperpiece.getText().toString().equals("")
                ||spperpiece.getText().toString().equals("")
                ||sppercarton.getText().toString().equals("")
                ||mImageUri==null) {
            Toast.makeText(this, "Enter Mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (mImageUri ==null){
            Toast.makeText(this, "Upoload The Image...", Toast.LENGTH_SHORT).show();

        }
        else if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED){
            Toast.makeText(this, "Check Internet Connection...", Toast.LENGTH_SHORT).show();
        }

        else {
            final ProgressDialog progressDialog= new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference filerefrence = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtention(mImageUri));

            filerefrence.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    //String link=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uri.isComplete());
                    Uri link = uri.getResult();
                    StoringInFirebase(link.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed..", Toast.LENGTH_SHORT).show();
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