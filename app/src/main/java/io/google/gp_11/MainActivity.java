package io.google.gp_11;


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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
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

public class MainActivity extends AppCompatActivity {
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
            Intent i_to_admin = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(i_to_admin);
            finish();
        }
        setContentView(R.layout.activity_main);
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
                        Toast.makeText(MainActivity.this, "Please Fill the username or password", Toast.LENGTH_SHORT).show();
                        sign_in.setText("sign in");
                    } else {
                        login(inputname, inputPass);
                    }
                } else
                    Toast.makeText(MainActivity.this, "Please Check wifi or mobile connection", Toast.LENGTH_SHORT).show();

            }
        });
        TextView sign_up = (TextView) findViewById(R.id.sin_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });


    }

    public boolean isNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);
            sock.connect(sockaddr, timeoutMs);
            sock.close();
            return true;
        } catch (IOException e) {
            return false;
        }
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
                            Toast.makeText(MainActivity.this, "password or username is incorrect", Toast.LENGTH_SHORT).show();
                            sign_in.setText("sign in");
                        } else {
                            if (users.size() == 1) {

                                user1 = users.get(0);
                                if (user1.getTypeId() == 1) {
                                    session.setEditor(user1.getId(), user1.getTypeId(), true);
                                    Intent i_to_admin = new Intent(MainActivity.this, AdminActivity.class);
                                    startActivity(i_to_admin);
                                    finish();

                                } else {
                                    Toast.makeText(MainActivity.this, "some thing error", Toast.LENGTH_SHORT).show();
                                    sign_in.setText("sign in");
                                }

                            }

                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Time out Expired .Please Check connection", Toast.LENGTH_SHORT).show();
                        sign_in.setText("sign in");

                    }


                    ;

                }

                @Override
                public void onFailure(Call<ResultUserSet> call, Throwable t) {

                }
            });
        } catch (Exception ex) {

        }

    }

}
