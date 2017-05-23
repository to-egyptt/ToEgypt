package AdminFragments;

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

import AdminModels.model_user;
import io.google.gp_11.AdminUpdateUser;
import io.google.gp_11.R;
import models.ResultUserSet;
import models.ToEgyptAPI;
import models.country;
import models.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminUsersFragment extends Fragment {

    private Integer[] IMAGE = {R.drawable.person1, R.drawable.person4, R.drawable.person5};
    private String[] Name = {"Ahmed Abuelhassan", "Saad Khalifa", "Medo Atef"};
    //private String[] country = {"USA", "SPAIN", "ITALY"};
    private ArrayList<user> users;
    private String country;
    private ArrayList<model_user> userModels;
    private RecyclerView recyclerView;
    private fragment_user_adapter UserAdapter;
    Retrofit retrofit;

    public AdminUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //     userModels = new ArrayList<>();
        users = new ArrayList<>();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://2egyptwebservice.somee.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        for (int i = 0; i < Name.length; i++) {
//            model_user UserModelForRecyclerView = new model_user(IMAGE[i], Name[i], country[i]);
//            userModels.add(UserModelForRecyclerView);
//        }

        super.onCreate(savedInstanceState);
        getUsers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_admin, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
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
                }

                @Override
                public void onFailure(Call<ResultUserSet> call, Throwable t) {

                }
            });
        } catch (Exception ex) {


        }

    }

    private String getCountry(int id) {
        final String[] country1 = {new String()};
        try {

            ToEgyptAPI toEgyptAPI = retrofit.create(ToEgyptAPI.class);
            Call<country> connection = toEgyptAPI.getCountry(id);
            connection.enqueue(new Callback<country>() {
                @Override
                public void onResponse(Call<country> call, Response<country> response) {
                    country1[0] = response.body().getName();
                    updateUI();
                }

                @Override
                public void onFailure(Call<country> call, Throwable t) {

                }
            });
        } catch (Exception ex) {


        }
        return country1[0];
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
            country = getCountry(modela.getId());
            holder.userimage.setImageResource(R.drawable.person1);
            holder.username.setText(modela.getUsername());
            holder.usercounrty.setText(country);
            holder.ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AdminUpdateUser.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", models.get(position).getId());
                    bundle.putString("fullname", models.get(position).getFullname());
                    bundle.putString("username", models.get(position).getUsername());
                    bundle.putInt("age", models.get(position).getAge());
                    bundle.putInt("phone", models.get(position).getPhonenumber());
                    bundle.putInt("country", models.get(position).getCountryId());
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
