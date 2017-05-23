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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AdminUpdatePackage extends AppCompatActivity
        //implements View.OnClickListener
{

    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private int mode;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_package);

        EditText package_name = (EditText) findViewById(R.id.packageName);
        EditText Places = (EditText) findViewById(R.id.packagePlaces);
        Spinner guide = (Spinner) findViewById(R.id.tour_Guide);
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        EditText Price = (EditText) findViewById(R.id.packagePrice);

        Button Update = (Button) findViewById(R.id.updatePackage);
        Button delete = (Button) findViewById(R.id.deletePackage);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.nav_actionbar);

        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);


        List<String> guides = new ArrayList<String>();
        guides.add("abuelhassen");
        guides.add("tifa");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, guides);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        guide.setAdapter(dataAdapter);
        guide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String assigned_guide = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        setDateTimeField();


        Intent i = getIntent();
        mode = i.getExtras().getInt("Mode");

        if (mode == 1) {
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
                                    // Write your code here to execute after dialog
                                    Toast.makeText(AdminUpdatePackage.this, "You clicked on YES", Toast.LENGTH_SHORT).show();
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

        } else {
            if (mode == 2) {
                mToolbar.setTitle("Create Package");
                Update.setVisibility(View.GONE);
                delete.setText("Create");
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AdminUpdatePackage.this, "Package created", Toast.LENGTH_SHORT).show();
                    }
                });
            }
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

}

