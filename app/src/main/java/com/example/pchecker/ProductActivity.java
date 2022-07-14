package com.example.pchecker;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.pchecker.model.Product;

import java.io.UnsupportedEncodingException;


public class ProductActivity extends AppCompatActivity {
    private final String url = "http:/192.168.1.64:8080/api/product/";
    private String code;
    private TextView nameTxtView;
    private TextView descriptionTxtView;
    private TextView priceTxtView;
    private Product product = new Product();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        nameTxtView         = findViewById(R.id.productName_text);
        descriptionTxtView  = findViewById(R.id.productDescription_text);
        priceTxtView        = findViewById(R.id.productPrice_text);

        Bundle arguments = getIntent().getExtras();
        code = arguments.getString("code");

        getProductByCode(code);
    }

    private void getProductByCode(String code) {



        StringRequest request = new StringRequest(Request.Method.GET, url + code, (response) -> {

            try {
                Log.i("Response", response);

                JSONObject jsonObject = new JSONObject(EncodingToUTF8(response));

                product.setId(jsonObject.getLong("id"));
                product.setName(jsonObject.getString("name"));
                product.setDescription( jsonObject.getString("description") );
                product.setPrice( jsonObject.getString("price") );


                nameTxtView.setText(product.getName());
                descriptionTxtView.setText(product.getDescription());
                priceTxtView.setText(product.getPrice() + " руб");

            } catch (JSONException e) {
                Log.e("JSON", e.getMessage());
            }

        }, error -> Log.e("VOLLEY", error.getMessage()));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public  static  String EncodingToUTF8(String response){
        try {
            byte[] code = response.toString().getBytes("ISO-8859-1");
            response = new String(code, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }
        return response;
    }
}
