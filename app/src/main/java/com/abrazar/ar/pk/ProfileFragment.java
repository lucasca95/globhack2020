package com.abrazar.ar.pk;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.abrazar.ar.pk.R.color.background_floating_material_dark;
import static com.abrazar.ar.pk.R.color.colorAccent;


public class ProfileFragment extends Fragment {
    private TextView proname;
    private TextView proemail;
    private TextView prohomeaddress;
    private TextView prowebsite;
    private TextView protelephone;
    private TextView probio;

    private Button editprofile;
    private Button editRole;

    private RecyclerView projobs_list;
    private DatabaseReference workhub;
    private DatabaseReference workhubusers;
    private FirebaseAuth auth;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(view);
        return view;


    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Job, ProJobViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Job, ProJobViewHolder>(
                Job.class,
                R.layout.profilejob_list,
                ProJobViewHolder.class,
                workhub.orderByChild("jobPostedUserId").equalTo(auth.getCurrentUser().getUid())
        ) {
            @Override
            protected void populateViewHolder(ProJobViewHolder viewHolder, Job model, int position) {
                final String job_key = getRef(position).getKey();
                viewHolder.setJobName(model.getJobName());
                viewHolder.setJobBudget(model.getJobBudget());
                viewHolder.setJobLocation(model.getJobLocationName());
                viewHolder.setJobDate(model.getJobPostedDate());
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SingleJobView.class);
                        intent.putExtra("job_id", job_key);
                        startActivity(intent);
                    }
                });
            }
        };
        projobs_list.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ProJobViewHolder extends RecyclerView.ViewHolder {

        View mview;

        public ProJobViewHolder(View itemView) {
            super(itemView);

            mview = itemView;
        }

        public void setJobName(String jobname) {
            TextView jName = (TextView) mview.findViewById(R.id.projobrowname);
            jName.setText(jobname);
        }

        public void setJobBudget(String jobbudget) {
            TextView jBudget = (TextView) mview.findViewById(R.id.projobrowbudget);
            jBudget.setText("COP." + jobbudget);
        }

        public void setJobLocation(String jobLocation) {
            TextView jLocation = (TextView) mview.findViewById(R.id.projobrowlocation);
            jLocation.setText(jobLocation);
        }

        public void setJobDate(String jobDate) {
            TextView jDate = (TextView) mview.findViewById(R.id.projobrowdate);
            jDate.setText("Agregado en " + jobDate);
        }
    }

    private void initViews(View view) {
        auth = FirebaseAuth.getInstance();
        workhub = FirebaseDatabase.getInstance().getReference().child("jobs");
        workhubusers = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());


        projobs_list = (RecyclerView) view.findViewById(R.id.projobs_list);
        projobs_list.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        proname = (TextView) view.findViewById(R.id.singleProfileUserName);
        proemail = (TextView) view.findViewById(R.id.singleProfileEmail);
        protelephone = (TextView) view.findViewById(R.id.singleProfileTelephone);
        prohomeaddress = (TextView) view.findViewById(R.id.singleProfileAddress);
        prowebsite = (TextView) view.findViewById(R.id.singleProfileWebsite);
        probio = (TextView) view.findViewById(R.id.singleProfileBio);

        editprofile = (Button) view.findViewById(R.id.profileEditBTN);


        workhubusers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                proname.setText((String) dataSnapshot.child("userName").getValue());
                proemail.setText((String) dataSnapshot.child("userEmail").getValue());
                protelephone.setText((String) dataSnapshot.child("userTelephone").getValue());
                prohomeaddress.setText((String) dataSnapshot.child("userAddress").getValue());
                prowebsite.setText((String) dataSnapshot.child("userWebsite").getValue());
                probio.setText((String) dataSnapshot.child("userBio").getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
               
            }
        });

    }
}

