package com.example.pchecker;

import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pchecker.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    private static final String JSON_URL = "http://192.168.1.64:8080/api/cart/user/";
    private ListView listView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listView = (ListView) findViewById(R.id.product_list);

        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            user = (User) arguments.getSerializable(User.class.getSimpleName());
        }

        loadJSONFromURL(JSON_URL);

    }

    private void loadJSONFromURL(String url) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.cartLoading_progressBar);
        progressBar.setVisibility(ListView.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + user.getUsername(),
                response -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    try {
                        JSONArray jsonArray = new JSONArray(EncodingToUTF8(response));
                        ArrayList< JSONObject> listItems = getArrayListFromJSONArray(jsonArray);
                        ListAdapter adapter = new CartViewAdapter(getApplicationContext(),
                                R.layout.row,R.id.textViewName,listItems);
                        listView.setAdapter(adapter);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(getApplicationContext(),
                error.toString(),
                Toast.LENGTH_SHORT).show()) {

            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError {
                final HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + user.getToken());

                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private ArrayList< JSONObject> getArrayListFromJSONArray(JSONArray jsonArray){
        ArrayList< JSONObject> aList = new ArrayList< JSONObject>();
        try {
            if(jsonArray!= null){
                for(int i = 0; i< jsonArray.length();i++){
                    aList.add(jsonArray.getJSONObject(i));
                }
            }
        }catch (JSONException js){
            js.printStackTrace();
        }
        return aList;
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
