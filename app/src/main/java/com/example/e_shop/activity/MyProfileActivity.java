package com.example.e_shop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

public class MyProfileActivity extends AppCompatActivity {
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPhone;
    private String name, email, phone;

    private String userUpdateUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        inputLayoutName = findViewById(R.id.profile_layout_full_name);
        inputLayoutEmail = findViewById(R.id.profile_layout_email);
        inputLayoutPhone = findViewById(R.id.profile_layout_phone);
    }

    public void processToSaveUserInformation(View view) {
        name = inputLayoutName.getEditText().getText().toString();
        email = inputLayoutEmail.getEditText().getText().toString().trim();
        phone = inputLayoutPhone.getEditText().getText().toString().trim();

        if (!validateName() | !validateEmail() | !validatePhone()) {
            return;
        }
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (name.isEmpty()) {
            inputLayoutName.setError("Name field can't be empty!");
            return false;
        } else {
            inputLayoutName.setError(null);
            inputLayoutName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        if (email.isEmpty()) {
            inputLayoutEmail.setError("Email field can't be empty!");
            return false;
        } else {
            inputLayoutEmail.setError(null);
            inputLayoutEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone() {
        if (phone.isEmpty()) {
            inputLayoutPhone.setError("Phone field can't be empty!");
            return false;
        } else {
            inputLayoutPhone.setError(null);
            inputLayoutPhone.setErrorEnabled(false);
            return true;
        }
    }

    private void updateUserInformation() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, userUpdateUrl, new Response.Listener<String>() {
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
                params.put("",name);
                params.put("",email);
                params.put("",phone);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void backArrow(View view) {
        onBackPressed();
    }
}