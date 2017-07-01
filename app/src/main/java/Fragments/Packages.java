package Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import BL.Session;
import io.google.ToEgypt.Admin;
import io.google.ToEgypt.Guide;
import io.google.ToEgypt.R;
import io.google.ToEgypt.UpdatePackage;
import io.google.ToEgypt.User;
import models.ResultReservedPackageSet;
import models.ResultpakageSet;
import models.Singleton;
import models.ToEgyptAPI;
import models.packag;
import models.reserved_package;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class Packages extends Fragment {

    private ArrayList<packag> packages;
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private fragment_package_adapter PackageAdapter;
    private String activityName;
    private int placeId;
    private ProgressDialog progressDialog;
    private Boolean privatePackage;
    private Session session;

    public Packages() {
        // Required empty public constructor
    }


    /*
    modes to updatePackage
    1 update package admin
    2 create package admin
    3 package details user , guide
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Retrieving data. please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        session = new Session(this.getContext());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            placeId = bundle.getInt("placeId");
        } else {
            placeId = 0;
        }
        retrofit = Singleton.getRetrofit();
        ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
        activityName = getActivity().getClass().getSimpleName();
        if (activityName.equals("Admin")) {
            api.getPackages().enqueue(new Callback<ResultpakageSet>() {
                @Override
                public void onResponse(Call<ResultpakageSet> call, Response<ResultpakageSet> response) {
                    packages = response.body().getValue();
                    if (placeId != 0) {
                        ArrayList<packag> list = new ArrayList<packag>();
                        for (int i = 0; i < packages.size(); i++) {
                            for (int j = 0; j < packages.get(i).getPackageDetailes().size(); j++) {
                                if (packages.get(i).getPackageDetailes().get(j).getPlace_id() == placeId)
                                    list.add(packages.get(i));
                            }
                        }
                        packages = list;
                    }
                    updateUI();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResultpakageSet> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error ,Please Check your internet connection", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else if (activityName.equals("User")) {

            privatePackage = ((User) getActivity()).myPackage;
            if (privatePackage) {
                //id
                //"/odata/reserved_package?$expand=package&$filter=tourist_id%20eq%20" + String.valueOf(session.getUserId())
                api.getReserrvedPackage().enqueue(new Callback<ResultReservedPackageSet>() {
                    @Override
                    public void onResponse(Call<ResultReservedPackageSet> call, Response<ResultReservedPackageSet> response) {
                        ArrayList<reserved_package> reservedPackages = new ArrayList<reserved_package>();
                        reservedPackages = response.body().getValue();
                        ArrayList<packag> listt = new ArrayList<packag>();
                        for (int i = 0; i < reservedPackages.size(); i++) {
                            listt.add(reservedPackages.get(i).getPackag());
                        }
                        packages = listt;
                        updateUI();

                    }

                    @Override
                    public void onFailure(Call<ResultReservedPackageSet> call, Throwable t) {

                    }
                });
                Toast.makeText(getActivity(), ":P", Toast.LENGTH_LONG).show();
            } else {
                api.getPackages().enqueue(new Callback<ResultpakageSet>() {
                    @Override
                    public void onResponse(Call<ResultpakageSet> call, Response<ResultpakageSet> response) {
                        packages = response.body().getValue();
                        if (placeId != 0) {
                            ArrayList<packag> list = new ArrayList<packag>();
                            for (int i = 0; i < packages.size(); i++) {
                                for (int j = 0; j < packages.get(i).getPackageDetailes().size(); j++) {
                                    if (packages.get(i).getPackageDetailes().get(j).getPlace_id() == placeId)
                                        list.add(packages.get(i));
                                }
                            }
                            packages = list;
                        }
                        updateUI();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResultpakageSet> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error ,Please Check your internet connection", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.packages, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        TextView hint = (TextView) view.findViewById(R.id.packageHint);

        if (activityName.equals("Admin")) {
            hint.setText("select package to edit");
            ((Admin) getActivity()).setActionBarTitle("Packages");
            ((Admin) getActivity()).setMenuItem(3);
            fab.setImageResource(R.drawable.addpackage);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), UpdatePackage.class);
                    intent.putExtra("Mode", 2);
                    startActivity(intent);

                }
            });
        } else if (activityName.equals("User")) {
            if (privatePackage) {
                ((User) getActivity()).setActionBarTitle("My Packages");
                ((User) getActivity()).setMenuItem(2);
                ((User) getActivity()).myPackage = false;

            } else {
                hint.setText("select package to see details and join");
                ((User) getActivity()).setActionBarTitle("Packages");
                ((User) getActivity()).setMenuItem(1);

                if (placeId == 1) {
                    Toast.makeText(getActivity(), "place id done", Toast.LENGTH_LONG).show();
                }
                fab.setVisibility(View.GONE);
            }
        } else if (activityName.equals("Guide")) {
            hint.setText("select package to see details");
            ((Guide) getActivity()).setActionBarTitle("Packages");
            ((Guide) getActivity()).setMenuItem(1);
            if (placeId == 1) {
                Toast.makeText(getActivity(), "place id done", Toast.LENGTH_LONG).show();
            }
            fab.setVisibility(View.GONE);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewPackages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void updateUI() {
        PackageAdapter = new fragment_package_adapter(packages);
        recyclerView.setAdapter(PackageAdapter);
    }

    private class PackagesHolder extends RecyclerView.ViewHolder {
        TextView packageEnd;
        TextView packageStart;
        TextView packageName;
        TextView placesInPackages;
        TextView packageTime;
        TextView packagePrice;
        LinearLayout packageCard;

        public PackagesHolder(View view) {
            super(view);
            packageStart = (TextView) view.findViewById(R.id.start);
            packageEnd = (TextView) view.findViewById(R.id.end);
            packageName = (TextView) view.findViewById(R.id.pckgname);
            placesInPackages = (TextView) view.findViewById(R.id.placesinpckg);
            packageTime = (TextView) view.findViewById(R.id.timetostart);
            packagePrice = (TextView) view.findViewById(R.id.price);
            packageCard = (LinearLayout) view.findViewById(R.id.linearLayoutPackage);

        }
    }

    private class fragment_package_adapter extends RecyclerView.Adapter<PackagesHolder> {
        private ArrayList<packag> models;

        public fragment_package_adapter(ArrayList<packag> Models) {
            models = Models;
        }

        @Override
        public PackagesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.recycler_view_packages, parent, false);
            return new PackagesHolder(view);
        }

        @Override
        public void onBindViewHolder(PackagesHolder holder, int position) {
            String places = "";
            double price = 0;
            final packag modela = models.get(position);
            holder.packageName.setText(modela.getName());
            for (int i = 0; i < modela.getPackageDetailes().size(); i++) {
                places = places + modela.getPackageDetailes().get(i).getPlace().getName() + ",";
            }
            holder.placesInPackages.setText(places);
            holder.packageStart.setText(String.valueOf(modela.getStart_date()).replace(String.valueOf(modela.getStart_date()).substring(10, 30), " "));
            holder.packageEnd.setText(String.valueOf(modela.getEnd_date()).replace(String.valueOf(modela.getEnd_date()).substring(10, 30), " "));
            holder.packageTime.setText(modela.getUser().getUsername());
            for (int i = 0; i < modela.getPackageDetailes().size(); i++) {
                price += modela.getPackageDetailes().get(i).getPlace().getPriceTovisit();
            }
            holder.packagePrice.setText(String.valueOf(price) + " USD");
            holder.packageCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityName = getActivity().getClass().getSimpleName();
                    Intent intent = new Intent(getActivity(), UpdatePackage.class);
                    if (activityName.equals("Admin")) {
                        intent.putExtra("Mode", 1);
                    } else if (activityName.equals("User")) {
                        intent.putExtra("Mode", 3);
                    } else if (activityName.equals("Guide")) {
                        intent.putExtra("Mode", 3);
                    }
                    intent.putExtra("package_id", modela.getId());
                    startActivity(intent);
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