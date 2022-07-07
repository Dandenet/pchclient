package com.example.pchecker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn;
    RelativeLayout root;
    Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);

        root = findViewById(R.id.root_element);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignIn();
            }
        });

//        button_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showCart();
//            }
//        });
    }

    public void openCart(View view){
        Intent intent = new Intent(this, CartPage.class);
        startActivity(intent);
    }


    private void showSignIn() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Вход");
        LayoutInflater inflater = LayoutInflater.from(this);
        View sign_window = inflater.inflate(R.layout.sign_in_window, null);
        dialog.setView(sign_window);
        dialog.show();

    }
}