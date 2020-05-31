package com.abrazar.ar.pk;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectionActivity extends AppCompatActivity {

    private Button askHelp;
    private Button giveHelp;

    Fragment  fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        askHelp = (Button)findViewById(R.id.askHelpBtn);
        giveHelp = (Button)findViewById(R.id.giveHelpBtn);
         fragment = (fragment);
                getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        //above part is to determine which fragment is in your frame_container



        giveHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                fragment = fm.findFragmentByTag("myFragmentTag");
                if (fragment == null) {
                    FragmentTransaction ft = fm.beginTransaction();
                    fragment =new RequestFavor();
                    ft.add(android.R.id.content,fragment,"myFragmentTag");
                    ft.commit();

                    Toast.makeText(getApplication(), "Ayudar",Toast.LENGTH_LONG).show();
                }
            }
        });

        askHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplication(), "Pedir ayuda",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), AddJobActivity.class);
                startActivity(intent);
            }
        });
    }
}