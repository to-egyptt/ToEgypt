package AdminFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.google.gp_11.AdminUpdateUser;
import io.google.gp_11.R;
import models.ResultUserSet;
import models.Singleton;
import models.ToEgyptAPI;
import models.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminGuidesFragmemnt extends Fragment {
    private Retrofit retrofit;
    private static ArrayList<user> users;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private fragment_guide_adapter GuideAdapter;


    public AdminGuidesFragmemnt() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Retrieving data. please wait...");
        progressDialog.setCancelable(false);
        retrofit = Singleton.getRetrofit();
        users = new ArrayList<>();
        progressDialog.show();
        ToEgyptAPI toEgyptAPI = retrofit.create(ToEgyptAPI.class);
        toEgyptAPI.getGuide().enqueue(new Callback<ResultUserSet>() {
            @Override
            public void onResponse(Call<ResultUserSet> call, Response<ResultUserSet> response) {
                if (response.isSuccess()) {
                    users = (ArrayList<user>) response.body().getUsers();
                    updateUI();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResultUserSet> call, Throwable t) {

            }
        });


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guides_admin, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setImageResource(R.drawable.adduser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getActivity(), AdminUpdateUser.class);
                intent.putExtra("Mode", 3);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewGuides);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //updateUI();
        return view;

    }

    private void updateUI() {
        GuideAdapter = new fragment_guide_adapter(users);
        recyclerView.setAdapter(GuideAdapter);
    }


    private class GuideHolder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView guideimage;
        TextView guidename;
        TextView guideemail;
        LinearLayout ln;

        public GuideHolder(View view) {
            super(view);
            guideimage = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.guideImage);
            guidename = (TextView) view.findViewById(R.id.guideName);
            guideemail = (TextView) view.findViewById(R.id.guideEmail);
            ln = (LinearLayout) view.findViewById(R.id.linearLayoutGuide);

        }
    }

    private class fragment_guide_adapter extends RecyclerView.Adapter<GuideHolder> {
        private ArrayList<user> models;

        public fragment_guide_adapter(ArrayList<user> Models) {
            models = Models;
        }

        @Override
        public GuideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.recycler_view_guides, parent, false);
            return new GuideHolder(view);
        }

        @Override
        public void onBindViewHolder(GuideHolder holder, int position) {
            final user modela = models.get(position);
            holder.guideimage.setImageResource(R.drawable.person4);
            holder.guidename.setText(modela.getUsername());
            holder.guideemail.setText(modela.getEmail());
            holder.ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AdminUpdateUser.class);
//<<<<<<< Updated upstream
                    intent.putExtra("Mode", 1);
//=======
//                    intent.putExtra("Mode",1);
//>>>>>>> Stashed changes
                    startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return models.size();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
