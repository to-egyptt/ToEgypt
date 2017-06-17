package AdminFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class AdminUsersFragment extends Fragment {

    private ArrayList<user> users;
    private RecyclerView recyclerView;
    private fragment_user_adapter UserAdapter;
    private Retrofit retrofit;
    private ProgressDialog progressDialog;
    public AdminUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Retrieving data. please wait...");
        progressDialog.setCancelable(false);
        users = new ArrayList<>();
        retrofit = Singleton.getRetrofit();
        super.onCreate(savedInstanceState);
        progressDialog.show();
        getUsers();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_admin, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //updateUI();
        return view;

    }

    private void updateUI() {
        UserAdapter = new fragment_user_adapter(users);
        recyclerView.setAdapter(UserAdapter);
    }

    private void getUsers() {
        try {

            ToEgyptAPI toEgyptAPI = retrofit.create(ToEgyptAPI.class);
            Call<ResultUserSet> connection = toEgyptAPI.getUsers();
            connection.enqueue(new Callback<ResultUserSet>() {
                @Override
                public void onResponse(Call<ResultUserSet> call, Response<ResultUserSet> response) {
                    users = (ArrayList<user>) response.body().getUsers();
                    updateUI();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResultUserSet> call, Throwable t) {

                }
            });
        } catch (Exception ex) {


        }

    }


    private class UsersHolder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView userimage;
        TextView username;
        TextView usercounrty;
        LinearLayout ln;

        public UsersHolder(View view) {
            super(view);
            userimage = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.personimage);
            username = (TextView) view.findViewById(R.id.username);
            usercounrty = (TextView) view.findViewById(R.id.usercountry);
            ln = (LinearLayout) view.findViewById(R.id.linearLayoutUser);

        }
    }

    private class fragment_user_adapter extends RecyclerView.Adapter<UsersHolder> {
        private ArrayList<user> models;

        public fragment_user_adapter(ArrayList<user> Models) {
            models = Models;
        }

        @Override
        public UsersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.recycler_view_users, parent, false);
            return new UsersHolder(view);
        }

        @Override
        public void onBindViewHolder(UsersHolder holder, final int position) {

            final user modela = models.get(position);
            holder.userimage.setImageResource(R.drawable.person1);
            holder.username.setText(modela.getUsername());
            holder.usercounrty.setText(modela.getCountry().getIso());
            holder.ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AdminUpdateUser.class);
                    Bundle bundle = new Bundle();
                    //bundle.putSerializable("user",(Serializable) models.get(position));
                    bundle.putInt("id", models.get(position).getId());
                    bundle.putString("fullname", models.get(position).getFullname());
                    bundle.putString("username", models.get(position).getUsername());
                    bundle.putInt("age", models.get(position).getAge());
                    bundle.putInt("phone", models.get(position).getPhonenumber());
                    bundle.putString("country", models.get(position).getCountry().getNicename());
                    bundle.putInt("age", models.get(position).getAge());
                    bundle.putString("Email", models.get(position).getEmail());

                    intent.putExtras(bundle);
                    intent.putExtra("Mode", 1);

                    // intent.putExtra();
                    startActivity(intent);
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//                    alertDialog.setTitle("Confirm Delete...");
//                    alertDialog.setMessage("Are you sure you want delete this?");
//                    alertDialog.setIcon(R.drawable.delete);
//                    alertDialog.setPositiveButton("YES",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Write your code here to execute after dialog
//                                    Toast.makeText(getActivity(), "You clicked on YES", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                    alertDialog.setNegativeButton("NO",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Write your code here to execute after dialog
//
//                                    dialog.cancel();
//                                }
//                            });
//
//                    // Showing Alert Message
//                    alertDialog.show();
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
