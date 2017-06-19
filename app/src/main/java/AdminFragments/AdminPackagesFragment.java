package AdminFragments;


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

import io.google.gp_11.Admin;
import io.google.gp_11.AdminUpdatePackage;
import io.google.gp_11.R;
import io.google.gp_11.User;
import models.ResultpakageSet;
import models.Singleton;
import models.ToEgyptAPI;
import models.packag;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminPackagesFragment extends Fragment {

    private ArrayList<packag> packages;
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private fragment_package_adapter PackageAdapter;
    private String activityName;
    private int placeId;
    private ProgressDialog progressDialog;
    public AdminPackagesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Retrieving data. please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            placeId = bundle.getInt("placeId");

        }

        retrofit = Singleton.getRetrofit();
        ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
        api.getPackages().enqueue(new Callback<ResultpakageSet>() {
            @Override
            public void onResponse(Call<ResultpakageSet> call, Response<ResultpakageSet> response) {
                packages = response.body().getValue();
                updateUI();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResultpakageSet> call, Throwable t) {
                Toast.makeText(getActivity(), "Error ,Please Check your internet connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_packages_admin, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        TextView hint = (TextView) view.findViewById(R.id.packageHint);
        activityName = getActivity().getClass().getSimpleName();
        if (activityName.equals("Admin")) {
            hint.setText("select package to edit");
            ((Admin) getActivity()).setActionBarTitle("Packages");
            ((Admin) getActivity()).setMenuItem(3);
            fab.setImageResource(R.drawable.addpackage);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Click action
                    Intent intent = new Intent(getActivity(), AdminUpdatePackage.class);
                    intent.putExtra("Mode", 2);
                    startActivity(intent);

                }
            });

        } else {
            if (activityName.equals("User")) {
                hint.setText("select package to see details and join");
                ((User) getActivity()).setActionBarTitle("Packages");
                ((User) getActivity()).setMenuItem(1);
                if (placeId == 1) {
                    Toast.makeText(getActivity(), "place id done", Toast.LENGTH_LONG).show();
                }
                fab.setVisibility(View.GONE);
            }
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewPackages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//         updateUI();
        return view;
    }

    private void updateUI() {
        PackageAdapter = new fragment_package_adapter(packages);
        recyclerView.setAdapter(PackageAdapter);
    }

    private class PackagesHolder extends RecyclerView.ViewHolder {
        TextView end;
        TextView start;
        TextView pckname;
        TextView placesingpckg;
        TextView time;
        TextView price;
        LinearLayout ln;

        public PackagesHolder(View view) {
            super(view);
            start = (TextView) view.findViewById(R.id.start);
            end = (TextView) view.findViewById(R.id.end);
            pckname = (TextView) view.findViewById(R.id.pckgname);
            placesingpckg = (TextView) view.findViewById(R.id.placesinpckg);
            time = (TextView) view.findViewById(R.id.timetostart);
            price = (TextView) view.findViewById(R.id.price);
            ln = (LinearLayout) view.findViewById(R.id.linearLayoutPackage);

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
            holder.pckname.setText(modela.getName());
            for (int i = 0; i < modela.getPackageDetailes().size(); i++) {
                places = places + modela.getPackageDetailes().get(i).getPlace().getName() + ",";
            }
            holder.placesingpckg.setText(places);
            holder.start.setText(String.valueOf(modela.getStart_date()).replace(String.valueOf(modela.getStart_date()).substring(10, 30), " "));
            holder.end.setText(String.valueOf(modela.getEnd_date()).replace(String.valueOf(modela.getEnd_date()).substring(10, 30), " "));
            holder.time.setText(modela.getUser().getUsername());
            for (int i = 0; i < modela.getPackageDetailes().size(); i++) {
                price += modela.getPackageDetailes().get(i).getPlace().getPriceTovisit();
            }
            holder.price.setText(String.valueOf(price) + " USD");
            holder.ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityName = getActivity().getClass().getSimpleName();
                    Intent intent = new Intent(getActivity(), AdminUpdatePackage.class);

                    if (activityName.equals("Admin")) {
                        intent.putExtra("Mode", 1);
                    } else if (activityName.equals("User")) {
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