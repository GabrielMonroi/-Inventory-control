package com.example.biel.yyy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    Button button;
    EditText pass, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.web);
        login = findViewById(R.id.login);
        pass = findViewById(R.id.pass);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }


    public void login() {
        StringRequest request = new StringRequest(Request.Method.POST, "http://negteam.16mb.com/public_html/1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      if (response.contains("1")){
                          startActivity(new Intent(getApplicationContext(), Menu.class));
                      }
                      else {
                          Toast.makeText(getApplicationContext(),"Seu usuario ou senha estao incorretos ",Toast.LENGTH_SHORT).show();
                      }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",login.getText().toString());
                params.put("password",pass.getText().toString());
                return params;



            }
        };
        Volley.newRequestQueue(this).add(request);

    }
}

