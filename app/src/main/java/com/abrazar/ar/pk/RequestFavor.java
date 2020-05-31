package com.abrazar.ar.pk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RequestFavor extends Fragment {
    FloatingActionButton actionButton;
    RecyclerView job_list;
    DatabaseReference workhub;



    public static JobFragment newInstance() {
        JobFragment fragment = new JobFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Job, JobViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Job, JobViewHolder>(
                Job.class,
                R.layout.job_row,
                JobViewHolder.class,
                workhub

        ) {
            @Override
            protected void populateViewHolder(JobViewHolder viewHolder, Job model, int position) {
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
        job_list.setAdapter(firebaseRecyclerAdapter);
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {

        View mview;

        public JobViewHolder(View itemView) {
            super(itemView);

            mview = itemView;
        }

        public void setJobName(String jobname) {
            TextView jName = (TextView) mview.findViewById(R.id.jobrowname);
            jName.setText(jobname);
        }

        public void setJobBudget(String jobbudget) {
            TextView jBudget = (TextView) mview.findViewById(R.id.jobrowbudget);
            jBudget.setText("$." + jobbudget);
        }

        public void setJobLocation(String jobLocation) {
            TextView jLocation = (TextView) mview.findViewById(R.id.jobrowlocation);
            jLocation.setText(jobLocation);
        }

        public void setJobDate(String jobDate) {
            TextView jDate = (TextView) mview.findViewById(R.id.jobrowdate);
            jDate.setText("Agregado en  " + jobDate);
        }
    }

    private void initViews(View view) {
        workhub = FirebaseDatabase.getInstance().getReference().child("jobs");
        workhub.keepSynced(true);

        actionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton2);
        job_list = (RecyclerView) view.findViewById(R.id.job_list);
        job_list.setHasFixedSize(true);
        job_list.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Estas en modo 'AYUDA'\n Cierra la aplicacion para salir"
                , Toast.LENGTH_SHORT).show();
            }
        });

    }




}
