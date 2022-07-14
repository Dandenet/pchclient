package com.example.pchecker;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pchecker.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {

    private Button btnSignIn;
    private EditText usernameEdit, passwordEdit;

    private final String authUrl = "http://192.168.1.64:8080/api/auth";
    private final String signInUrl = authUrl + "/signin";

    private User user = new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnSignIn = (Button) findViewById(R.id.button_sign);
        usernameEdit = findViewById(R.id.edit_username);
        passwordEdit = findViewById(R.id.edit_password);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(usernameEdit.getText().toString(), passwordEdit.getText().toString());

            }
        });


    }


    private void signIn(String username, String password) {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar_loading);
        progressBar.setVisibility(ListView.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, signInUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONObject jsObj = new JSONObject(response);
                    user.setId(jsObj.getLong("id"));
                    user.setUsername(jsObj.getString("username"));
                    user.setEmail(jsObj.getString("email"));
                    user.setToken(jsObj.getString("token"));


                    Intent intent = new Intent("android.intent.action.found");
                    startActivity(intent);

                } catch (JSONException exception) {
                    Log.i("JSON", exception.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                progressBar.setVisibility(ListView.GONE);
                Toast.makeText(getApplicationContext(), "Ошибка авторизации",Toast.LENGTH_SHORT).show();
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
                    Log.i("Username", username);
                    Log.i("Password", password);

                    object.put("username", username);
                    object.put("password", password);

                } catch (JSONException e) {
                    Log.e("JSON:", e.getMessage());
                }


                return object.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(request);
    }
}