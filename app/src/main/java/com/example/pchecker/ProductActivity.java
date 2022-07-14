package com.example.pchecker;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.pchecker.model.Product;
import com.example.pchecker.model.User;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


/**
 * Founds a product by code and shows it
 **/
public class ProductActivity extends AppCompatActivity {
    private final String url = "http:/192.168.1.64:8080/api/product/";
    private final String cartUrl = "http:/192.168.1.64:8080/api/cart/user/";


    private TextView productResultTxtView;
    private TextView nameTxtView;
    private TextView descriptionTxtView;
    private TextView priceTxtView;
    private Button   addProductButton;

    private String  code;
    private Product product = new Product();
    private User    user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productResultTxtView = findViewById(R.id.productResult_text);
        nameTxtView          = findViewById(R.id.productName_text);
        descriptionTxtView   = findViewById(R.id.productDescription_text);
        priceTxtView         = findViewById(R.id.productPrice_text);
        addProductButton     = findViewById(R.id.addProduct_button);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null) {
            code = arguments.getString("code");
            user = (User) arguments.getSerializable(User.class.getSimpleName());
        }

        getProductByCode(code);

        addProductButton.setOnClickListener(view -> {
            AppendProductToCart();
        });
    }



    /**
     * Lodes product to the activity by a product code
     **/
    private void getProductByCode(String code) {

        StringRequest request = new StringRequest(Request.Method.GET, url + code, (response) -> {

            try {
                JSONObject jsonObject = new JSONObject(EncodingToUTF8(response));


                product.setId(jsonObject.getLong("id"));
                product.setName(jsonObject.getString("name"));
                product.setDescription( jsonObject.getString("description") );
                product.setPrice( jsonObject.getString("price") );

                productResultTxtView.setText("Найден товар");
                nameTxtView.setText(product.getName());
                descriptionTxtView.setText(product.getDescription());
                priceTxtView.setText(product.getPrice() + " руб");
                addProductButton.setVisibility(Button.VISIBLE);

            } catch (JSONException e) {
                Log.e("JSON", e.getMessage());
            }

        }, error -> {
            Log.e("VOLLEY", error.toString());

            productResultTxtView.setText("Товар не найден");
            nameTxtView.setText(new String());
            descriptionTxtView.setText(new String());
            priceTxtView.setText(new String());
            addProductButton.setVisibility(Button.INVISIBLE);


        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    private void AppendProductToCart() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,
                cartUrl + user.getUsername(),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Добавлено",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                Toast.makeText(getApplicationContext(), "Ошибка",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                JSONObject object = new JSONObject();

                try {
                    object.put("id", product.getId());
                    object.put("name", product.getName());
                    object.put("quantity", 1);
                } catch (JSONException e) {
                    Log.e("JSON:", e.getMessage());
                }

                return object.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError {
                final HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + user.getToken());

                return headers;
            }
        };

        queue.add(request);
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
