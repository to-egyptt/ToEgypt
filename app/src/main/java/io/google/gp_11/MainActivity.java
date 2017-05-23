package io.google.gp_11;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import models.ResultUserSet;
import models.ToEgyptAPI;
import models.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    int type;
    private user user1;
    private ArrayList<user> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText username = (EditText) findViewById(R.id.usr_name);
        String inputname = username.getText().toString();
        EditText userpass = (EditText) findViewById(R.id.user_password);
        String inputPass = userpass.getText().toString();
        //give you input name and input pass and you give me the type id
        //assume you retrieve me 1 it means this is admin
        type = 0;

        Button sign_in = (Button) findViewById(R.id.sing_in);
        //final int finalType = type;
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user1 = new user();
                login("", "");

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


    public void login(String username, String Password) {
        //user user1 = new user();
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://2egyptwebservice.somee.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ToEgyptAPI toEgyptAPI = retrofit.create(ToEgyptAPI.class);
            toEgyptAPI.login().enqueue(new Callback<ResultUserSet>() {
                @Override
                public void onResponse(Call<ResultUserSet> call, Response<ResultUserSet> response) {
                    users = new ArrayList<user>();
                    users = (ArrayList<user>) response.body().getUsers();
                    if (users == null) {
                        type = 0;

                    } else {
                       // if (users.size() == 1) {

                            user1 = users.get(0);
                        if(user1.getTypeId()==1)
                        {
                            Intent i_to_admin = new Intent(MainActivity.this, AdminActivity.class);
                            startActivity(i_to_admin);
                        }

                        //}
                     //   int s = user1.getTypeId();
                        //Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();


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
