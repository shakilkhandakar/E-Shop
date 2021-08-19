package com.example.e_shop.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_shop.R;
import com.google.android.material.textfield.TextInputLayout;

public class ForgetPasswordActivity extends AppCompatActivity {
    LinearLayout phoneNumberLayout,setPasswordLayout;
    private TextInputLayout phoneNumberInputLayout, newPasswordInputLayout, confirm_newPasswordInputLayout;
    private String phone, newPass, conFirmNewPass;
    Button nextButton,submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        phoneNumberLayout = findViewById(R.id.nextLayout);
        setPasswordLayout = findViewById(R.id.newPasswordLayout);
        phoneNumberInputLayout = findViewById(R.id.forgetPassword_input_layout_phone);
        newPasswordInputLayout = findViewById(R.id.new_password_input_layout);
        confirm_newPasswordInputLayout = findViewById(R.id.confirm_new_password_input_layout);
        nextButton = findViewById(R.id.next_button_id);
        submitButton = findViewById(R.id.submit_button_id);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = phoneNumberInputLayout.getEditText().getText().toString();
                if (!validatePhone()) {
                    return;
                }
               phoneNumberLayout.setVisibility(View.GONE);
               setPasswordLayout.setVisibility(View.VISIBLE);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPass = newPasswordInputLayout.getEditText().getText().toString();
                conFirmNewPass = confirm_newPasswordInputLayout.getEditText().getText().toString();

                if (!validatePassword() | !validateConfirmPassword()) {
                    return;
                }
                ProgressDialog dialog=new ProgressDialog(v.getContext());
                dialog.setMessage("Loading...");
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });

    }

    private boolean validatePhone() {
        if (phone.isEmpty()) {
            phoneNumberInputLayout.setError("Phone field can't be empty!");
            return false;
        } else if (phone.length() < 11) {
            phoneNumberInputLayout.setError("Phone number should be at least 11 digit !");
            return false;
        } else {
            phoneNumberInputLayout.setError(null);
            phoneNumberInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        if (newPass.isEmpty()) {
            newPasswordInputLayout.setError("Password field can't be empty!");
            return false;
        } else if (newPass.length() < 6) {
            newPasswordInputLayout.setError("Password should be at leas 6 character!");
            return false;
        } else {
            newPasswordInputLayout.setError(null);
            newPasswordInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        if (conFirmNewPass.isEmpty()) {
            confirm_newPasswordInputLayout.setError("Confirm Password field can't be empty!");
            return false;
        } else if (conFirmNewPass.length() < 6) {
            confirm_newPasswordInputLayout.setError("Password should be at leas 6 character!");
            return false;
        } else {
            confirm_newPasswordInputLayout.setError(null);
            confirm_newPasswordInputLayout.setErrorEnabled(false);
            return true;
        }
    }

}