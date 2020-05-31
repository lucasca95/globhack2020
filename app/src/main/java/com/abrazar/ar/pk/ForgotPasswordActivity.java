package com.abrazar.ar.pk;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private Button resetBTN;
    private EditText resetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetBTN = findViewById(R.id.resetPasswordBTN);
        resetEmail = findViewById(R.id.resetPasswordEmail);

        firebaseAuth = FirebaseAuth.getInstance();

        resetBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!resetEmail.getText().toString().isEmpty()) {

                    firebaseAuth.sendPasswordResetEmail(resetEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(ForgotPasswordActivity.this,
                                                "Se envio un correo de solicitud, espera unos minutos", Toast.LENGTH_LONG).show();
                                        finish();
                                    }

                                }
                            });
                }
            }
        });


    }
}


