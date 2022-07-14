package com.example.pchecker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pchecker.model.Product;
import com.example.pchecker.model.User;

import java.util.Optional;

public class ScannerActivity extends AppCompatActivity {

    private Button button_scan, btn_cart;
    private EditText code_edit;
    private User mUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        button_scan = findViewById(R.id.btn_scan);
        btn_cart = findViewById(R.id.btn_cart);

        code_edit = findViewById(R.id.edit_code);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            mUser = (User) arguments.getSerializable(User.class.getSimpleName());
        }
        tmp();
        button_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanProduct();
            }
        });

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCart();
            }
        });
    }

    public void tmp()
    {
        Log.i("User: ",mUser.getUsername());
    }

    public void scanProduct(){
        Intent intent = new Intent("android.intent.action.product");
        intent.putExtra("code", code_edit.getText().toString());
        startActivity(intent);
    }


    public void openCart(){
        Intent intent = new Intent("android.intent.action.list");
        startActivity(intent);
    }
}
