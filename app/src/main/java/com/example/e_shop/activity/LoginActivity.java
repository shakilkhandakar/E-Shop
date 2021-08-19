package com.example.e_shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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
import com.example.e_shop.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout emailOrPhoneInputLayout, passwordInputLayout;
    TextView forgetTextButton;
    ProgressBar loginProgressBar;
    private String email_phone, password;
    private String loginUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailOrPhoneInputLayout = findViewById(R.id.input_layout_email_or_password);
        passwordInputLayout = findViewById(R.id.input_layout_pass);
        forgetTextButton = findViewById(R.id.forgetPasswordId);
        loginProgressBar = (ProgressBar) findViewById(R.id.loginProgressBar);
    }

    public void processToLogin(View view) {
           /* email_phone=emailOrPhoneInputLayout.getEditText().getText().toString().trim();
            password=passwordInputLayout.getEditText().getText().toString();*/

            /*if (!validateEmailOrPhone()|!validatePassword()){
                return;
            }*/
        startActivity(new Intent(this, MainActivity.class));
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    private void userInformationForLogin() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again !", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("",email_phone);
                params.put("",password);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean validateEmailOrPhone() {
        if (email_phone.isEmpty()) {
            emailOrPhoneInputLayout.setError("Field can't be empty!");
            return false;
        } else {
            emailOrPhoneInputLayout.setError(null);
            emailOrPhoneInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        if (password.isEmpty()) {
            passwordInputLayout.setError("Password field can't be empty!");
            return false;
        } else if (password.length() < 6) {
            passwordInputLayout.setError("Password should be at leas 6 character!");
            return false;
        } else {
            passwordInputLayout.setError(null);
            passwordInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public void processToPasswordChange(View view) {
        startActivity(new Intent(this,ForgetPasswordActivity.class));
    }

    public void goToSignUpPage(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }
}