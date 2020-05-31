package com.abrazar.ar.pk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private TextView lEmail;
    private TextView lPass;
    private Button lLoginBTN;
    private Button lForgotPassBTN;
    private Button lLinktoSignupBTN;


    private ProgressDialog progress;

    private FirebaseAuth auth;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        pref = this.getSharedPreferences("Users", 0);

        progress = new ProgressDialog(this);

        lEmail = (TextView) findViewById(R.id.emailLoginET);
        lPass = (TextView) findViewById(R.id.passwordLoginET);
        lLoginBTN = (Button) findViewById(R.id.loginBTN);
        lLinktoSignupBTN = (Button) findViewById(R.id.linktoregisterBTN);
        lForgotPassBTN = (Button) findViewById(R.id.forgotpasswordBTN);


        lLinktoSignupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        lForgotPassBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });

        lLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = lEmail.getText().toString().trim();
                String userPass = lPass.getText().toString().trim();

                if (!TextUtils.isEmpty(userEmail) || !TextUtils.isEmpty(userPass)) {
                    if (validateEmail(userEmail) == true) {
                        progress.setMessage("Registro completo!");
                        progress.show();

                        auth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
                                    editor.putString(Constants.EMAIL, auth.getCurrentUser().getEmail());
                                    editor.putString(Constants.UNIQUE_ID, auth.getCurrentUser().getUid());
                                    editor.apply();

                                    progress.dismiss();

                                    Intent intent = new Intent(LoginActivity.this, SelectionActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    progress.dismiss();
                                    Toast.makeText(LoginActivity.this, "Algun elemento es invalido!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Ese email no es valido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Completa los campos!", Toast.LENGTH_SHORT).show();
                }


            }


        });

    }

    public boolean validateEmail(String email) {

        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
