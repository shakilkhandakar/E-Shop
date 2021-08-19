package com.example.e_shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
   private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPhone, inputLayoutPassword, inputLayoutConfirmPassword;
    private String name, email, phone, password, confirmPassword;
    ProgressBar progressBar;
    private String registrationUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//      textInputLayout find
        inputLayoutName = findViewById(R.id.input_layout_full_name);
        inputLayoutEmail = findViewById(R.id.input_layout_email);
        inputLayoutPhone = findViewById(R.id.input_layout_phone);
        inputLayoutPassword = findViewById(R.id.input_layout_password);
        inputLayoutConfirmPassword = findViewById(R.id.input_layout_confirm_password);
        progressBar=findViewById(R.id.signUpProgressBar);


    }

    public void processToRegistration(View view) {
        name = inputLayoutName.getEditText().getText().toString();
        email = inputLayoutEmail.getEditText().getText().toString().trim();
        phone = inputLayoutPhone.getEditText().getText().toString().trim();
        password = inputLayoutPassword.getEditText().getText().toString().trim();
        confirmPassword = inputLayoutConfirmPassword.getEditText().getText().toString().trim();

        if (!validateName() | !validateEmail() | !validatePhone() | !validatePassword()
                | !validateConfirmPassword()) {
            return;
        }
        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        uploadUserInformation();
    }

    private void uploadUserInformation() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, registrationUrl, new Response.Listener<String>() {
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
                    params.put("",password);
                    params.put("",confirmPassword);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
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

    private boolean validatePassword() {
        if (password.isEmpty()) {
            inputLayoutPassword.setError("Password field can't be empty!");
            return false;
        }else if(password.length()<6){
            inputLayoutPassword.setError("Password should be at leas 6 character!");
            return false;
        }
            else {
            inputLayoutPassword.setError(null);
            inputLayoutPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        if (confirmPassword.isEmpty()) {
            inputLayoutConfirmPassword.setError("Confirm Password field can't be empty!");
            return false;
        }else if(confirmPassword.length()<6){
            inputLayoutConfirmPassword.setError("Password should be at leas 6 character!");
            return false;
        } else {
            inputLayoutConfirmPassword.setError(null);
            inputLayoutConfirmPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void goToSignInPage(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}