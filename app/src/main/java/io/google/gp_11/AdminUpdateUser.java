package io.google.gp_11;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.Singleton;
import models.ToEgyptAPI;
import models.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminUpdateUser extends AppCompatActivity {

    private Uri mImageCaptureUri;
    ImageView banar1;
    private int mode;
    private TextView username;
    private TextView email;
    private EditText fullname;
    private EditText age;
    private EditText phone;
    private EditText country;
    private int user_id;
    private String Fullname;
    private String Username;
    private String Email;
    private int Age;
    private int country_id;
    private int Phone;
    private Retrofit retrofit;
    private user user;
    //user user;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_user);
        email = (TextView) findViewById(R.id.Email_profile);
        username = (TextView) findViewById(R.id.username_profile);
        fullname = (EditText) findViewById(R.id.userFullName);
        age = (EditText) findViewById(R.id.userAge);
        phone = (EditText) findViewById(R.id.userNumberPhone);
        country = (EditText) findViewById(R.id.userCountry);
        Button reset = (Button) findViewById(R.id.reset);
        Button delete = (Button) findViewById(R.id.delete);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.nav_actionbar);
        //user=new user();
        //mode=1;
        Intent i = getIntent();
        mode = i.getExtras().getInt("Mode");
        Bundle bundle = i.getExtras();
        fullname.setText(bundle.getString("fullname"));
        email.setText(bundle.getString("Email"));
        username.setText(bundle.getString("username"));
        //err hena
        age.setText(Integer.toString(bundle.getInt("age")));
        country.setText(Integer.toString(bundle.getInt("country")));
        phone.setText(Integer.toString(bundle.getInt("phone")));
        user_id = bundle.getInt("id");
        Fullname = bundle.getString("fullname");
        Email = bundle.getString("Email");
        Username = bundle.getString("username");
        country_id = bundle.getInt("country");
        Phone = bundle.getInt("phone");
        retrofit = Singleton.getRetrofit();
        user = new user();
        //user= (models.user) i.getExtras().getSerializable("user");
        //mode = 1; // just show true false
        //mode = 2;  //want update true true
        if (mode == 1) {
            mToolbar.setTitle("Update User");
            fullname.setCursorVisible(false);
            fullname.setFocusable(false);
            age.setCursorVisible(false);
            age.setFocusable(false);
            phone.setCursorVisible(false);
            phone.setFocusable(false);
            country.setCursorVisible(false);
            country.setFocusable(false);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminUpdateUser.this);
                    alertDialog.setTitle("Confirm Delete ..");
                    alertDialog.setMessage("Are you sure you want delete this user?");
                    alertDialog.setIcon(R.drawable.delete);
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    try {

                                        ToEgyptAPI toEgyptAPI = retrofit.create(ToEgyptAPI.class);
                                        toEgyptAPI.deleteUser(user_id).enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                Toast.makeText(AdminUpdateUser.this, "Deleted", Toast.LENGTH_SHORT).show();
                                                Intent i_to_admin = new Intent(AdminUpdateUser.this, Admin.class);
                                                startActivity(i_to_admin);
                                                finish();
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Toast.makeText(AdminUpdateUser.this, "Not Deleted some thing error", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } catch (Exception ex) {


                                    }


                                    Toast.makeText(AdminUpdateUser.this, "You clicked on YES", Toast.LENGTH_SHORT).show();
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
            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminUpdateUser.this);
                    alertDialog.setTitle("Confirm Reset ..");
                    alertDialog.setMessage("Are you sure you want reset password for this user?");
                    alertDialog.setIcon(R.drawable.reset);
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    getuser(user_id);
                                    // Write your code here to execute after dialog
                                    Toast.makeText(AdminUpdateUser.this, "You clicked on YES", Toast.LENGTH_SHORT).show();
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
                mToolbar.setTitle("My profile");
                fullname.setCursorVisible(true);
                fullname.setFocusable(true);
                age.setCursorVisible(true);
                age.setFocusable(true);
                phone.setCursorVisible(true);
                phone.setFocusable(true);
                country.setCursorVisible(true);
                country.setFocusable(true);
                reset.setVisibility(View.GONE);
                delete.setText("Update");
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
                banar1 = (ImageView) findViewById(R.id.banar1);
                banar1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.show();
                    }
                });
            } else {
                if (mode == 3) {
                    mToolbar.setTitle("Add new User");
                    reset.setVisibility(View.GONE);
                    delete.setText("Create");
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
                    banar1 = (ImageView) findViewById(R.id.banar1);
                    banar1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.show();
                        }
                    });
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(AdminUpdateUser.this, "Place created", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        }

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
                    banar1.setImageBitmap(photo);
                }
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) f.delete();
                break;
        }
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
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
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

    public void getuser(final int userId) {
        ToEgyptAPI toEgyptAPI = retrofit.create(ToEgyptAPI.class);
        toEgyptAPI.getuser(userId).enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                user = response.body();
                resetpassword(userId, user);
                Toast.makeText(AdminUpdateUser.this, "All is Good", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {

            }
        });
    }

    void resetpassword(int userId, user user1) {
        user1.setPassword("p@ssw0rd");
        ToEgyptAPI toEgyptAPI = retrofit.create(ToEgyptAPI.class);
        toEgyptAPI.updateUser(userId, user1).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccess()) {
                    Toast.makeText(AdminUpdateUser.this, "Password Updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

}
