package com.example.pchecker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pchecker.model.Cart;

public class CartPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_customer);

        ListView product_list = findViewById(R.id.Product_list);

        product_list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Cart.items_id.toArray()));

        }
}
