package com.abrazar.ar.pk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddJobActivity extends AppCompatActivity {

    private EditText addJobName;
    private EditText addJobDesc;
    private EditText addJobBudget;
    private Button submitjob;
    private AutoCompleteTextView addjobSkills;

    private String addJobLocation;

    private FirebaseAuth auth;
    private DatabaseReference workhub;
    private DatabaseReference workhubUsers;


    private AlertDialog.Builder builder;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        builder = new AlertDialog.Builder(this);
        auth = FirebaseAuth.getInstance();
        workhub = FirebaseDatabase.getInstance().getReference().child("jobs");
        workhubUsers = FirebaseDatabase.getInstance().getReference().child("users");

        String[] skills = {"Frutas", "Productos de limpieza", "Productos para el hogar" , "Enlatados",
        "Pago de factura", "Envio de mensajes"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, skills);

        addJobName = (EditText) findViewById(R.id.addjobJobNameET);
        addJobDesc = (EditText) findViewById(R.id.addjobDescET);
        addJobBudget = (EditText) findViewById(R.id.addjobBudgetET);
        submitjob = (Button) findViewById(R.id.submitjobBTN);
        addjobSkills = (AutoCompleteTextView) findViewById(R.id.addjobSkilltET);
        addjobSkills.setThreshold(1);
        addjobSkills.setAdapter(adapter);

        workhubUsers.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userName = (String) dataSnapshot.child("userName").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submitjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String jname = addJobName.getText().toString();
                final String jdesc = addJobDesc.getText().toString();
                final String jbudget = addJobBudget.getText().toString();
                final String skill = addjobSkills.getText().toString();
                if (!TextUtils.isEmpty(jname) && !TextUtils.isEmpty(jdesc) && !TextUtils.isEmpty(jbudget) && !TextUtils.isEmpty(skill)) {
                    builder.setTitle("Confirmación");
                    builder.setMessage("Quieres agregar este Favor?");

                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {


                            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
                            final String jdate = sdf.format(new Date());

                            String juid = auth.getCurrentUser().getUid();

                            String jobId = workhub.push().getKey();

                            Job newJob = new Job();
                            newJob.setJobName(jname);
                            newJob.setJobDesc(jdesc);
                            newJob.setJobBudget(jbudget);
                            newJob.setJobPostedDate(jdate);
                            newJob.setJobPostedUserId(juid);
                            newJob.setJobKeyWord(skill);
                            newJob.setJobPostedUserName(userName);
                            newJob.setJobStatus("Available");

                            workhub.child(jobId).setValue(newJob);

                            Toast.makeText(AddJobActivity.this, "Se agrego el Favor" +
                                    "", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                            startActivity(new Intent(AddJobActivity.this, FeedActivity.class));
                            finish();
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
                    Toast.makeText(AddJobActivity.this, "Debes completar todos los campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
