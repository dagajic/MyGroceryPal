package hr.foi.air.mygrocerypal.myapplication.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Pattern;

import hr.foi.air.mygrocerypal.myapplication.Controller.RegistrationController;
import hr.foi.air.mygrocerypal.myapplication.Controller.RegistrationListener;
import hr.foi.air.mygrocerypal.myapplication.Core.BaseActivity;
import hr.foi.air.mygrocerypal.myapplication.Model.UserModel;
import hr.foi.air.mygrocerypal.myapplication.R;

public class RegistrationActivity extends BaseActivity implements RegistrationListener {

    private ProgressBar progressBar;
    private TextView dateOfBirthTxt;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText emailTxt, passwordTxt, userNameTxt, firstNameTxt, lastNameTxt, adressTxt, townTxt, contactTxt, retypedPasswordTxt;
    private Button registerBtn, backToLoginBtn;

    private RegistrationController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        controller = new RegistrationController(this);
        // TODO Ukloniti kompoziciju prema controlleru!

        progressBar = findViewById(R.id.progressBar);
        firstNameTxt = (EditText) findViewById(R.id.firstnameRegistration);
        lastNameTxt = (EditText) findViewById(R.id.lastnameRegistration);
        userNameTxt = (EditText) findViewById(R.id.usernameRegistration);
        passwordTxt = (EditText) findViewById(R.id.passwordRegistration);
        retypedPasswordTxt = (EditText) findViewById(R.id.repeatPasswordRegistration);
        emailTxt = (EditText) findViewById(R.id.emailRegistration);
        adressTxt = (EditText) findViewById(R.id.addressRegistration);
        townTxt = (EditText) findViewById(R.id.townRegistration);
        dateOfBirthTxt = (TextView) findViewById(R.id.dateOfBirthRegistration);
        contactTxt = (EditText) findViewById(R.id.contactRegistration);

        registerBtn = (Button) findViewById(R.id.buttonRegister);
        backToLoginBtn = (Button) findViewById(R.id.buttonBackToLogin);

        dateOfBirthTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegistrationActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = (month+1) + "/" + dayOfMonth + "/" + year;
                dateOfBirthTxt.setText(date);
                contactTxt.requestFocus();
            }
        };

        emailTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(emailTxt.getText().toString().trim().length() > 0){
                        boolean validationSuccess = controller.validateEmail(emailTxt.getText().toString().trim());
                        if(!validationSuccess) emailTxt.setError("Molimo unesite ispravnu email adresu!");
                    }else {
                        emailTxt.setError("Obavezno polje!");
                    }
                }
            }
        });

        passwordTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if (passwordTxt.getText().toString().trim().length() > 0) {
                        boolean validationSuccess = controller.validatePassword(passwordTxt.getText().toString().trim());
                        if (!validationSuccess)
                            passwordTxt.setError("Minimalno 6 slova i jedan specijalni znak!");
                    } else {
                        passwordTxt.setError("Obavezno polje!");
                    }
                }
            }
        });

        retypedPasswordTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(retypedPasswordTxt.getText().toString().trim().length() > 0){
                        boolean validationSuccess = controller.validateRetypedPassword(passwordTxt.getText().toString().trim(), retypedPasswordTxt.getText().toString().trim());
                        if(!validationSuccess) retypedPasswordTxt.setError("Lozinke ne odgovaraju!");
                    }else{
                        retypedPasswordTxt.setError("Obavezno polje!");
                    }
                }
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                controller.validateInputAndRegisterUserIfInputCorrect(
                        firstNameTxt.getText().toString().trim(), lastNameTxt.getText().toString().trim(),
                        userNameTxt.getText().toString().trim(), passwordTxt.getText().toString().trim(),
                        retypedPasswordTxt.getText().toString().trim(), emailTxt.getText().toString().trim(),
                        adressTxt.getText().toString().trim(), townTxt.getText().toString().trim(),
                        contactTxt.getText().toString().trim(), dateOfBirthTxt.getText().toString().trim());
                }
        });

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowActivity(LoginActivity.class);
            }
        });
    }

    @Override
    public void onRegistrationSuccess(String message) {
        progressBar.setVisibility(View.GONE);
        ShowActivity(LoginActivity.class);
        showToastRegistration(message);
    }

    @Override
    public void onRegistrationFail(String message) {
        progressBar.setVisibility(View.GONE);
        showToastRegistration(message);

    }

    public void showToastRegistration(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}