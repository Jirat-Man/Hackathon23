package com.example.pantrymanagement;

import static com.example.pantrymanagement.ConstantsExpiry.EXPIRY_DATE;
import static com.example.pantrymanagement.ConstantsExpiry.ITEM;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.pantrymanagement.room.PantryEntity;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageButton PantryButton;
    ImageButton ScanReceiptButton;
    ViewModel mViewModel;
    String[] itemsName;

    Uri uri;
    Bitmap bitmap;
    byte[] imageByte;

    String ExpiryDate;

    private static final int REQUEST_CAMERA_CODE = 100;


    private void getTextFromImage(Bitmap bitmap){
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if(!recognizer.isOperational()){
            Toast.makeText(MainActivity.this, "Error !", Toast.LENGTH_SHORT).show();
        }
        else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();

            for(int i=0;i<textBlockSparseArray.size();i++){
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }

            itemsName = stringBuilder.toString().split("\n");
            addItemToDatabase(itemsName);
        }
    }

    private void addItemToDatabase(String[] itemsName) {
        LocalDate purchaseDate = getTodayDate();
        String purchaseDateString = String.valueOf(purchaseDate);

        for(int i = 0; i < itemsName.length; i++){
            int daysTillExpiry = predictExpiryDate(itemsName[0]);
            String expiryDate = getExpiryDate(purchaseDate, daysTillExpiry);
            PantryEntity entity = new PantryEntity(itemsName[i], expiryDate,"", null, 23, 34,purchaseDateString, 23, daysTillExpiry);
            mViewModel.Insert(entity);
        }
    }

    private String getExpiryDate(LocalDate purchaseDate, int daysTillExpiry) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return String.valueOf(purchaseDate.plusDays(daysTillExpiry));
        }
        return null;
    }

    private LocalDate getTodayDate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDate.now();
        }
        return null;
    }

    private int predictExpiryDate(String s) {
        int expireDays = 2;
        for(int i = 0; i < ITEM.length; i++){
            if(Objects.equals(ITEM[i], s)){
                expireDays = EXPIRY_DATE[i];
                break;
            }
        }
        return expireDays;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();

        PantryButton.setOnClickListener(v -> {
            Intent journey = new Intent(MainActivity.this, PantryActivity.class);
            startActivity(journey);
        });

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }

        ScanReceiptButton.setOnClickListener(v -> {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(MainActivity.this);
        });

        mViewModel = new ViewModelProvider(this).get(ViewModel.class);

    }

    private void initWidget() {
        PantryButton = findViewById(R.id.pantryButton);
        ScanReceiptButton = findViewById(R.id.ScanReceiptButton);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    getTextFromImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}