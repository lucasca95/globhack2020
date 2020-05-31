package com.abrazar.ar.pk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditJobActivity extends AppCompatActivity {

    private String job_key = null;

    private EditText editJobName;
    private EditText editJobDesc;
    private EditText editJobBudget;
    private TextView editLocat;

    private Button editJobBTN;

    private String editJobLocation;

    private FirebaseAuth auth;
    private DatabaseReference workhub;

    private Double jobLong;
    private Double jobLat;

    private AlertDialog.Builder builder;

    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);
        job_key = getIntent().getExtras().getString("job_id");

        builder = new AlertDialog.Builder(this);
        auth = FirebaseAuth.getInstance();
        workhub = FirebaseDatabase.getInstance().getReference().child("jobs");

        editJobName = (EditText) findViewById(R.id.editjobJobNameET);
        editJobDesc = (EditText) findViewById(R.id.editjobDescET);
        editJobBudget = (EditText) findViewById(R.id.editjobBudgetET);
        editLocat = (TextView) findViewById(R.id.locat);

        editJobBTN = (Button) findViewById(R.id.editJobBTN);


        workhub.child(job_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editJobName.setText((String) dataSnapshot.child("jobName").getValue());
                editJobDesc.setText((String) dataSnapshot.child("jobDesc").getValue());
                editJobBudget.setText((String) dataSnapshot.child("jobBudget").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editJobBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String jname = editJobName.getText().toString();
                final String jdesc = editJobDesc.getText().toString();
                final String jbudget = editJobBudget.getText().toString();

                if (!TextUtils.isEmpty(jname) && !TextUtils.isEmpty(jdesc) && !TextUtils.isEmpty(jbudget)) {
                    builder.setTitle("Confirmar");
                    builder.setMessage("Guardar cambios?");

                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {


                            workhub.child(job_key).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    dataSnapshot.getRef().child("jobName").setValue(jname);
                                    dataSnapshot.getRef().child("jobDesc").setValue(jdesc);
                                    dataSnapshot.getRef().child("jobBudget").setValue(jbudget);
                                    dataSnapshot.getRef().child("jobLocationName").setValue(editJobLocation);
                                    dataSnapshot.getRef().child("jobLocationLong").setValue(jobLong);
                                    dataSnapshot.getRef().child("jobLocationLat").setValue(jobLat);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            Toast.makeText(EditJobActivity.this, "Se guardaron los cambios!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(EditJobActivity.this, SingleJobView.class);
                            intent.putExtra("job_id", job_key);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();


                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    Toast.makeText(EditJobActivity.this, "Debes completar todos los campos!", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
