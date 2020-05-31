package com.abrazar.ar.pk;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class FeedActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private FirebaseAuth auth;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);


        pref = this.getSharedPreferences("Users", 0);
        builder = new AlertDialog.Builder(this);
        auth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = JobFragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = SearchFragment.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });


        //Manually displaying the first fragment - one time only


        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, JobFragment.newInstance());
        transaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.SignOut) {
            builder.setTitle("Confirmar");
            builder.setMessage("Quieres salir?");

            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    auth.signOut();

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN, false);
                    editor.putString(Constants.EMAIL, null);
                    editor.putString(Constants.UNIQUE_ID, null);
                    editor.apply();
                    dialog.dismiss();

                    startActivity(new Intent(FeedActivity.this, MainActivity.class));
                    finish();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
        return super.onOptionsItemSelected(item);

    }

}
