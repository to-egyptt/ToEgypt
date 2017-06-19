package io.google.gp_11;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import models.ResultPlaceSet;
import models.ResultUserSet;
import models.Singleton;
import models.ToEgyptAPI;
import models.packag;
import models.place;
import models.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AdminUpdatePackage extends AppCompatActivity
        //implements View.OnClickListener
{
    private ArrayList<place> places;
    private boolean[] checkedItem;
    private ArrayList<Integer> muserItems;
    private ArrayList<user> guides;
    private Retrofit retrofit;
    private EditText package_name;
    private EditText Places;
    private Spinner guide;
    private EditText description;
    private EditText Price;
    private packag packag;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private int mode;
    private int package_id;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_package);
        retrofit = Singleton.getRetrofit();
        package_name = (EditText) findViewById(R.id.packageName);
        Places = (EditText) findViewById(R.id.packagePlaces);
        guide = (Spinner) findViewById(R.id.tour_Guide);
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        description = (EditText) findViewById(R.id.packageDescription);
        Button Update = (Button) findViewById(R.id.updatePackage);
        Button delete = (Button) findViewById(R.id.deletePackage);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.nav_actionbar);

        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
        setGuideSpinner();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        setDateTimeField();
        Intent i = getIntent();
        mode = i.getExtras().getInt("Mode");

        if (mode == 1) {

            Places.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
                    api.getPlaces().enqueue(new Callback<ResultPlaceSet>() {
                        @Override
                        public void onResponse(Call<ResultPlaceSet> call, Response<ResultPlaceSet> response) {
                            places = response.body().getValue();
                            checkedItem = new boolean[places.size()];
                        }

                        @Override
                        public void onFailure(Call<ResultPlaceSet> call, Throwable t) {
                            Toast.makeText(AdminUpdatePackage.this, "Error ,Please Check your internet connection", Toast.LENGTH_SHORT).show();
                            //progressDialog.dismiss();
                        }
                    });
                }
            });
            package_id = i.getExtras().getInt("package_id");
            setForms(package_id);
            mToolbar.setTitle("Update Package");
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(AdminUpdatePackage.this, "Package updated", Toast.LENGTH_SHORT).show();
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminUpdatePackage.this);
                    alertDialog.setTitle("Confirm Delete ..");
                    alertDialog.setMessage("Are you sure you want delete this Package?");
                    alertDialog.setIcon(R.drawable.delete);
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
                                    api.deletePackage(package_id).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if (response.isSuccess()) {
                                                Toast.makeText(AdminUpdatePackage.this, "Deleted sucssfully", Toast.LENGTH_SHORT).show();
                                                Intent i_to_admin = new Intent(AdminUpdatePackage.this, Admin.class);
                                                startActivity(i_to_admin);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {

                                        }
                                    });
                                    // Write your code here to execute after dialog
                                }
                            });
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog

                                    dialog.cancel();
                                }
                            });

                    // Showing Alert Message
                    alertDialog.show();
                }
            });


        } else if (mode == 2) {
            mToolbar.setTitle("Create Package");
            Update.setVisibility(View.GONE);
            delete.setText("Create");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(AdminUpdatePackage.this, "Package created", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (mode == 3) { // pckg details
            mToolbar.setTitle("Package Details");
            package_name.setCursorVisible(false);
            package_name.setFocusable(false);
            description.setCursorVisible(false);
            description.setFocusable(false);
            Update.setVisibility(View.GONE);
            delete.setText("Join");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminUpdatePackage.this);
                    alertDialog.setTitle("Confirm Join ..");
                    alertDialog.setMessage("Are you sure you want Join to this Package?");
                    alertDialog.setIcon(R.drawable.success);
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog

                                    Intent i_to_admin = new Intent(AdminUpdatePackage.this, Admin.class); // navigate to my package
                                    startActivity(i_to_admin);
                                    finish();

                                }
                            });
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog

                                    dialog.cancel();
                                }
                            });

                    // Showing Alert Message
                    alertDialog.show();
                }
            });
        }
    }


    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });
        toDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
            }
        });


    }


    public void setGuideSpinner() {
        ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
        api.getGuide().enqueue(new Callback<ResultUserSet>() {
            @Override
            public void onResponse(Call<ResultUserSet> call, Response<ResultUserSet> response) {
                if (response.isSuccess()) {
                    guides = (ArrayList<user>) response.body().getUsers();
                    ArrayAdapter<user> userArrayAdapter = new ArrayAdapter<user>(AdminUpdatePackage.this, R.layout.support_simple_spinner_dropdown_item, guides);
                    guide.setAdapter(userArrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResultUserSet> call, Throwable t) {
                Toast.makeText(AdminUpdatePackage.this, "Error ,Please Check your internet connection", Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
            }
        });
    }

    public void setForms(int id) {
        ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
        api.getPackage(id).enqueue(new Callback<packag>() {
            @Override
            public void onResponse(Call<packag> call, Response<packag> response) {
                String plc = "";
                double prc = 0;
                packag = response.body();
                package_name.setText(packag.getName());
                fromDateEtxt.setText(String.valueOf(packag.getStart_date()).replace(String.valueOf(packag.getStart_date()).substring(10, 30), " "));
                toDateEtxt.setText(String.valueOf(packag.getEnd_date()).replace(String.valueOf(packag.getEnd_date()).substring(10, 30), " "));
                for (int i = 0; i < packag.getPackageDetailes().size(); i++) {
                    plc = plc + packag.getPackageDetailes().get(i).getPlace().getName() + ",";
                }
                Places.setText(plc);
                description.setText(packag.getDescription());

            }

            @Override
            public void onFailure(Call<packag> call, Throwable t) {
                Toast.makeText(AdminUpdatePackage.this, "Error ,Please Check your internet connection", Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
            }
        });
    }
}

