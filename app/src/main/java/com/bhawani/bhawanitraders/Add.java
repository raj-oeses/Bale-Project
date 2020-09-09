package com.bhawani.bhawanitraders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
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
import android.os.Environment;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Add extends AppCompatActivity implements View.OnClickListener {
    EditText item,cpperpiece,spperpiece,sppercarton,memo,barcode;
    Button scan,save,select;
    Uri mImageUri=null;
    ImageView imagepreview;
    String currentPhotoPath;
    File photoFile;

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
        databaseReference.keepSynced(true);

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
/*=========================================selecting the image==============*/
    private void SelectTheImage() {
        AlertDialog.Builder choose = new AlertDialog.Builder(this);
         choose.setTitle("Chose the Media");
         choose.setMessage("What do You Want To Choose...");
         choose.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                 if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                     dispatchTakePictureIntent();
                 }
                 else{
                     Toast.makeText(Add.this, "its ok to get wrong (smily) (smily)", Toast.LENGTH_SHORT).show();
                 }

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
/*======================================On Activity Result====================================*/
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
            else {
                mImageUri=null;
            }
        }
        else if (requestCode==CAMERA_KO){
            imagepreview.setImageResource(0);
            Picasso.get().load(mImageUri).into(imagepreview);
            }
    }
    /*====================================   File Extention   ===============================================*/
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
        try {

            if (item.getText().toString().equals("")) {
                Toast.makeText(this, "Enter Mandatory", Toast.LENGTH_SHORT).show();
            } else if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED) {
                Toast.makeText(this, "Check Internet Connection!!!", Toast.LENGTH_SHORT).show();
            } else {

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
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
    /*==================================this is for image storage===========================================*/
    public void SavingTheDetails(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (item.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Mandatory...", Toast.LENGTH_SHORT).show();
            mImageUri=null;
        }
        else if (mImageUri!=null){
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED){
                Toast.makeText(this, "Check Your Internet Connection...", Toast.LENGTH_SHORT).show();
            }
            else{
                UploadingImage();
            }
        }
        else if (mImageUri==null){
            String demoimageuri="https://firebasestorage.googleapis.com/v0/b/demoforall-17e03.appspot.com/o/Details%2Fcamera.png?alt=media&token=903f560f-6b73-4a92-83ed-d5d36e79f50a";
            StoringInFirebase(demoimageuri);
        }
    }

    /*====================================Uploading with Image ===============================================*/

    public void UploadingImage(){
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

    /*============================Image From Camera========================================================*/
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                try {
                    mImageUri = FileProvider.getUriForFile(this,"com.bhawani.bhawanitraders.fileprovider",photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(takePictureIntent,CAMERA_KO);
                }catch (Exception e){
                    Toast.makeText(this,"Camera Permission Denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}