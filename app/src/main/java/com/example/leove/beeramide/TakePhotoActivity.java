package com.example.leove.beeramide;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Debug;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by leove on 08/03/2018.
 */

public class TakePhotoActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 5;
    public ArrayList<String> playerList = new ArrayList<String>();
    public int gameMoment;
    String beforePhotoPath;
    String afterPhotoPath;
    public ArrayList<Rule> rules = new ArrayList<Rule>();
    public int nbTurns;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_picture);

        Log.e("feedback", getExternalFilesDir(null).toString());
        Log.e("feedback", String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));

        rules = (ArrayList<Rule>) getIntent().getSerializableExtra("ruleList");
        playerList = getIntent().getStringArrayListExtra("playerList");
        gameMoment = getIntent().getIntExtra("gameMoment", 2);


        ImageButton takePictureButton = findViewById(R.id.takePictureButton);
        Button passerButton = findViewById(R.id.passerButton);
        nbTurns = getIntent().getIntExtra("nbTurns",15);

        //Take a picture
        takePictureButton.setOnClickListener(new View.OnClickListener(){

            public  void onClick(View view){
                dispatchTakePictureIntent();
            }
        });

        //passer l'étape
        passerButton.setOnClickListener(new View.OnClickListener(){

            public  void onClick(View view){
                if(gameMoment == 0){
                    Intent intent = new Intent(TakePhotoActivity.this, GameActivity.class);
                    intent.putStringArrayListExtra("playerList", playerList);
                    intent.putExtra("ruleList",rules);
                    intent.putExtra("nbTurns",nbTurns);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(TakePhotoActivity.this, EndGameActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(TakePhotoActivity.this,
                        "com.example.leove.beeramide.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = gameMoment + timeStamp + "_";
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        if (gameMoment == 0){
            beforePhotoPath = image.getAbsolutePath();
        }
        else if (gameMoment == 1){
            afterPhotoPath = image.getAbsolutePath();
        }

        return image;
    }

    public void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        else{
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(beforePhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                if(gameMoment == 0){
                    addImageToGallery(beforePhotoPath, this);
                }
                else if(gameMoment == 1){
                    addImageToGallery(afterPhotoPath, this);
                }
            }
            return;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // addImageToGallery(mCurrentPhotoPath, this);
                if (gameMoment == 0){

                    Intent intent = new Intent(TakePhotoActivity.this, GameActivity.class);
                    intent.putStringArrayListExtra("playerList", playerList);
                    intent.putExtra("ruleList",rules);
                    intent.putExtra("nbTurns",nbTurns);
                    //galleryAddPic();
                    //addImageToGallery(beforePhotoPath, this);
                    startActivity(intent);
                }
                else if (gameMoment == 1){
                    //faire les layers

                    //startEndActivity
                    Intent intent = new Intent(TakePhotoActivity.this, EndGameActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}
