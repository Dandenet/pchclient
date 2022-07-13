package com.example.pchecker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pchecker.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    private final String url = "http:/192.168.1.64:8080/api/product/";
    private String code;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Bundle arguments = getIntent().getExtras();
        code = arguments.getString("code");

        getProductByCode(code);
    }

    private Product getProductByCode(String code) {

        Product product = new Product();

        StringRequest request = new StringRequest(Request.Method.GET, url + code, new Response.Listener< String>(){
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("Response", response);

                            JSONObject jsonObject = new JSONObject(response);

                            product.setId(jsonObject.getLong("id"));
                            product.setName(jsonObject.getString("name"));
                            product.setDescription( jsonObject.getString("description") );
                            product.setPrice( jsonObject.getString("price") );

                        } catch (JSONException e) {
                            Log.e("JSON", e.getMessage());
                        }

                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        return product;
    }
}
