package io.google.ToEgypt;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.ResultcategorySet;
import models.Singleton;
import models.ToEgyptAPI;
import models.category;
import models.governate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UpdatePlace extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int mode;
    private int place_id;
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    ImageView img;
    private EditText place_name;
    private Spinner category;
    private Spinner governorate;
    private EditText Description;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_place);
        retrofit = Singleton.getRetrofit();
        place_name = (EditText) findViewById(R.id.place_Name);
        category = (Spinner) findViewById(R.id.place_category);
        governorate = (Spinner) findViewById(R.id.place_Governate);
        Description = (EditText) findViewById(R.id.place_description);
        img = (ImageView) findViewById(R.id.placeimagetoview);
        Button Update = (Button) findViewById(R.id.updatePlace);
        Button delete = (Button) findViewById(R.id.deletePlace);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.nav_actionbar);
        fillGoverSpinner();
        CategorySpinner();

        Intent i = getIntent();
        mode = i.getExtras().getInt("Mode");
        place_id = i.getExtras().getInt("place_id");

        if (mode == 1) {
//            String myString = "ancient"; //the value you want the position for
//            ArrayAdapter myAdap = (ArrayAdapter) category.getAdapter(); //cast to an ArrayAdapter
//            int spinnerPosition = myAdap.getPosition(myString);
//            //set the default according to value
//            category.setSelection(spinnerPosition);
            img = (ImageView) findViewById(R.id.placeimagetoview);
            changePic();
            mToolbar.setTitle("Update Place");
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(UpdatePlace.this, "Place updated", Toast.LENGTH_SHORT).show();
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdatePlace.this);
                    alertDialog.setTitle("Confirm Delete ..");
                    alertDialog.setMessage("Are you sure you want delete this Place?");
                    alertDialog.setIcon(R.drawable.delete);
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    Toast.makeText(UpdatePlace.this, "You clicked on YES", Toast.LENGTH_SHORT).show();
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
                mToolbar.setTitle("Create Place");
                changePic();
                Update.setVisibility(View.GONE);
                delete.setText("Create");
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(UpdatePlace.this, "Place created", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spin = (Spinner) parent;
        if (spin.getId() == R.id.place_category) {
            String item = parent.getItemAtPosition(position).toString();
        } else {
            if (spin.getId() == R.id.place_Governate) {
                String item = parent.getItemAtPosition(position).toString();
            }
        }

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                doCrop();
                break;
            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();
                doCrop();
                break;
            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    img.setImageBitmap(photo);
                }
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) f.delete();
                break;
        }
    }

    private void changePic() {
        final String[] items = new String[]{"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { //pick from camera
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                            "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    try {
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else { //pick from file
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                }
            }
        });
        final AlertDialog dialog = builder.create();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);

        int size = list.size();

        if (size == 0) {
            Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();

            return;
        } else {
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 400);
            intent.putExtra("outputY", 400);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);
                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();
                    co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);
                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    cropOptions.add(co);
                }
                CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (mImageCaptureUri != null) {
                            getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    void fillGoverSpinner() {

        ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
        api.getGovernate().enqueue(new Callback<ArrayList<governate>>() {
            @Override
            public void onResponse(Call<ArrayList<governate>> call, Response<ArrayList<governate>> response) {
                if (response.isSuccess()) {
                    ArrayAdapter<governate> governateArrayAdapter = new ArrayAdapter<governate>(UpdatePlace.this, R.layout.support_simple_spinner_dropdown_item, response.body());
                    governorate.setAdapter(governateArrayAdapter);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<governate>> call, Throwable t) {

            }
        });
    }

    public void CategorySpinner() {
        ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
        api.getCategory().enqueue(new Callback<ResultcategorySet>() {
            @Override
            public void onResponse(Call<ResultcategorySet> call, Response<ResultcategorySet> response) {
                if (response.isSuccess()) {
                    ArrayAdapter<category> categoryArrayAdapter = new ArrayAdapter<category>(UpdatePlace.this, R.layout.support_simple_spinner_dropdown_item, response.body().getValue());
                    governorate.setAdapter(categoryArrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResultcategorySet> call, Throwable t) {

            }
        });

    }
}
