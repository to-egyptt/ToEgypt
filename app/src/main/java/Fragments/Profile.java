package Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import BL.Session;
import io.google.ToEgypt.Admin;
import io.google.ToEgypt.Guide;
import io.google.ToEgypt.R;
import io.google.ToEgypt.User;
import models.ResultCountrySet;
import models.Singleton;
import models.ToEgyptAPI;
import models.country;
import models.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Profile extends Fragment {

    Retrofit retrofit;
    private String activityName;
    private int mode;
    private EditText fullName;
    private EditText userName;
    private EditText Email;
    private EditText Age;
    private EditText Phone;
    private Spinner Country;
    private ArrayList<country> countries;
    Session session;
    user user;
    int urser_id;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        retrofit = Singleton.getRetrofit();
        session = new Session(getContext());
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        urser_id = session.getUserId();
        if (bundle != null) {
            mode = bundle.getInt("mode");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile, container, false);

        fullName = (EditText) view.findViewById(R.id.userFullName);
        userName = (EditText) view.findViewById(R.id.username);
        Email = (EditText) view.findViewById(R.id.userEmail);
        Age = (EditText) view.findViewById(R.id.userAge);
        Phone = (EditText) view.findViewById(R.id.userNumberPhone);
        Country = (Spinner) view.findViewById(R.id.userCountry);
        setSpinnerCountries();
        activityName = getActivity().getClass().getSimpleName();
        Button update = (Button) view.findViewById(R.id.create);
        if (mode == 1) {

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fullname = Profile.this.fullName.getText().toString();
                    String username = userName.getText().toString();
                    String email = Email.getText().toString();
                    int age = 0;
                    if (!(Age.getText().toString().isEmpty())) {
                        age = Integer.valueOf(Age.getText().toString());
                    }
                    int phone = 0;
                    if (!(Phone.getText().toString().isEmpty())) {
                        phone = Integer.valueOf(Phone.getText().toString());
                    }
                    country country = (country) Country.getSelectedItem();
                    if (fullname.isEmpty() || username.isEmpty() || email.isEmpty() || age <= 0 || phone < 0) {
                        Toast.makeText(getActivity(), "Invalid data please fill fields or enter correct data", Toast.LENGTH_SHORT).show();
                    } else {
                        user user = new user();
                        user.setFullname(fullname);
                        user.setPassword("p@$$word");
                        user.setTypeId(3);
                        user.setUsername(username);
                        user.setEmail(email);
                        user.setAge(age);
                        user.setPhonenumber(phone);
                        user.setCountryId(country.getId());
                        user.setGender(false);
                        addGuide(user);
                    }
                }
            });
            ((Admin) getActivity()).setActionBarTitle("Add Guide");
            ((Admin) getActivity()).setMenuItem(2);

        } else if (activityName.equals("Admin")) {


            ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
            api.getuser(urser_id).enqueue(new Callback<user>() {
                @Override
                public void onResponse(Call<user> call, Response<user> response) {
                    user = response.body();
                    fullName.setText(user.getFullname());
                    userName.setText(user.getUsername());
                    Email.setText(user.getEmail());
                    Age.setText(String.valueOf(user.getAge()));
                    Phone.setText(String.valueOf(user.getPhonenumber()));
                    Country.setSelection(user.getCountryId());
                }

                @Override
                public void onFailure(Call<user> call, Throwable t) {

                }
            });
            ((Admin) getActivity()).setActionBarTitle("Profile");
            ((Admin) getActivity()).setMenuItem(5);
            update.setText("Update");
        } else if (activityName.equals("User")) {
            ((User) getActivity()).setActionBarTitle("Profile");
            ((User) getActivity()).setMenuItem(4);
            update.setText("Update");
        } else if (activityName.equals("User")) {
            ((Guide) getActivity()).setActionBarTitle("Profile");
            ((Guide) getActivity()).setMenuItem(4);
            update.setText("Update");
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    void setSpinnerCountries() {
        ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
        api.getCountries().enqueue(new Callback<ResultCountrySet>() {
            @Override
            public void onResponse(Call<ResultCountrySet> call, Response<ResultCountrySet> response) {
                countries = (ArrayList<country>) response.body().getCountries();
                ArrayAdapter<country> countryArrayAdapter = new ArrayAdapter<country>(getActivity(), R.layout.support_simple_spinner_dropdown_item, countries);
                Country.setAdapter(countryArrayAdapter);
            }

            @Override
            public void onFailure(Call<ResultCountrySet> call, Throwable t) {
                Toast.makeText(getActivity(), "Error ,Please Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void addGuide(user user) {
        ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
        api.addUser(user).enqueue(new Callback<models.user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                if (response.isSuccess()) {
                    Toast.makeText(getActivity(), "Guide Add successfully", Toast.LENGTH_SHORT).show();
                    Intent i_to_admin = new Intent(getActivity(), Admin.class);
                    startActivity(i_to_admin);
                }
            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {
                Toast.makeText(getActivity(), "Error ,Please Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
