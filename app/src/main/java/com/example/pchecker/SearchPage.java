package com.example.pchecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pchecker.model.Cart;

public class SearchPage extends AppCompatActivity {

    Button button_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_checker);
        button_search = findViewById(R.id.btn_search);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoundProduct();
            }
        });
    }

    public void openFoundProduct(){
        Intent intent = new Intent("android.intent.action.product");
        startActivity(intent);
    }
}
