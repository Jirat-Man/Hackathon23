package com.example.pantrymanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.pantrymanagement.room.PantryEntity;

public class MainActivity extends AppCompatActivity {

    ImageButton PantryButton;
    ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        PantryEntity item = new PantryEntity("Broccoli", "20/12/2023", null,
                null, 4, 300, "15/12/2023", 34, 4);
        mViewModel = new ViewModelProvider(this).get(ViewModel.class);
        mViewModel.Insert(item);

        PantryButton.setOnClickListener(v -> {
            Intent journey = new Intent(MainActivity.this, PantryActivity.class);
            startActivity(journey);
        });

        mViewModel = new ViewModelProvider(this).get(ViewModel.class);


        Log.d("LOGDD", item.getName());
    }

    private void initWidget() {
        PantryButton = findViewById(R.id.pantryButton);
    }
}