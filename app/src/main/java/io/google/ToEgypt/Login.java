package io.google.ToEgypt;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import BL.Session;
import models.ResultUserSet;
import models.Singleton;
import models.ToEgyptAPI;
import models.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
    private EditText username;
    private EditText userpass;
    private Retrofit retrofit;
    private ProgressDialog progressDialog;
    private user user1;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(this);
        if (session.loggedIn()) {
            if (session.getUserType() == 1) {
                Intent i_to_admin = new Intent(Login.this, Admin.class);
                startActivity(i_to_admin);
                finish();
            } else if (session.getUserType() == 2) {
                Intent i_to_user = new Intent(Login.this, User.class);
                startActivity(i_to_user);
                finish();
            } else if (session.getUserType() == 3) {
                Intent i_to_guide = new Intent(Login.this, Guide.class);
                startActivity(i_to_guide);
                finish();
            }
        }
        setContentView(R.layout.login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("logging in ");
        progressDialog.setCancelable(false);
        retrofit = Singleton.getRetrofit();
        username = (EditText) findViewById(R.id.usr_name);
        userpass = (EditText) findViewById(R.id.user_password);
        //give you input name and input pass and you give me the type id
        //assume you retrieve me 1 it means this is admin


        final Button sign_in = (Button) findViewById(R.id.sing_in);
        //final int finalType = type;
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputname = username.getText().toString();
                String inputPass = userpass.getText().toString();
                if (isNetwork()) {

                    sign_in.setText("Please wait ...");
                    if (inputname.isEmpty() || inputPass.isEmpty()) {
                        Toast.makeText(Login.this, "Please Fill the username or password", Toast.LENGTH_SHORT).show();
                        sign_in.setText("sign in");
                    } else {
                        login(inputname, inputPass);
                    }
                } else
                    Toast.makeText(Login.this, "Please Check wifi or mobile connection", Toast.LENGTH_SHORT).show();

            }
        });
        TextView sign_up = (TextView) findViewById(R.id.sin_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Login.this, Registration.class);
                startActivity(register);
            }
        });


    }

    public boolean isNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public void login(String username, String Password) {

        //user user1 = new user();
        try {
            String url = "/odata/users?$filter=username%20eq%20%27" + username + "%27%20and%20password%20eq%20%27" + Password + "%27";
            ToEgyptAPI toEgyptAPI = retrofit.create(ToEgyptAPI.class);
            toEgyptAPI.login(url).enqueue(new Callback<ResultUserSet>() {
                @Override
                public void onResponse(Call<ResultUserSet> call, Response<ResultUserSet> response) {
                    Button sign_in = (Button) findViewById(R.id.sing_in);
                    if (response.isSuccess()) {

                        ArrayList<user> users;
                        users = (ArrayList<user>) response.body().getUsers();
                        if (users.size() == 0) {
                            Toast.makeText(Login.this, "password or username is incorrect", Toast.LENGTH_SHORT).show();
                            sign_in.setText("sign in");
                        } else {
                            if (users.size() == 1) {

                                user1 = users.get(0);
                                if (user1.getTypeId() == 1) {
                                    session.setEditor(user1.getId(), user1.getTypeId(), true);
                                    Intent i_to_admin = new Intent(Login.this, Admin.class);
                                    startActivity(i_to_admin);
                                    finish();

                                } else if (user1.getTypeId() == 2) {
                                    session.setEditor(user1.getId(), user1.getTypeId(), true);
                                    Intent i_to_user = new Intent(Login.this, User.class);
                                    startActivity(i_to_user);
                                    finish();

                                } else if (user1.getTypeId() == 3) {
                                    session.setEditor(user1.getId(), user1.getTypeId(), true);
                                    Intent i_to_guide = new Intent(Login.this, Guide.class);
                                    startActivity(i_to_guide);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "some thing error", Toast.LENGTH_SHORT).show();
                                    sign_in.setText("sign in");
                                }

                            }

                        }

                    } else {
                        Toast.makeText(Login.this, "Time out Expired .Please Check connection", Toast.LENGTH_SHORT).show();
                        sign_in.setText("sign in");

                    }


                    ;

                }

                @Override
                public void onFailure(Call<ResultUserSet> call, Throwable t) {
                    Button sign_in = (Button) findViewById(R.id.sing_in);
                    Toast.makeText(Login.this, "Error ,Please Check your internet connection", Toast.LENGTH_SHORT).show();
                    sign_in.setText("sign in");
                    //progressDialog.dismiss();
                }
            });
        } catch (Exception ex) {

        }

    }

}
