package AdminFragments;


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

import java.util.ArrayList;

import AdminModels.model_package;
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
    private String[] Start = {"Start 1/6/2017 12:00", "Start 5/6/2017 12:00", "Start 7/6/2017 12:00"};
    private String[] End = {"End 8/6/2017 12:00", "End 8/6/2017 12:00", "End 8/6/2017 12:00"};
    private String[] pckgname = {"Package1", "Package2", "Package3"};
    private String[] placesinpackage = {"pyramids ,sinai", "Helwan ,sinai", "Cairo Tower"};
    private String[] TIME = {"6h 21m", "8h 11m", "4h 40m"};
    private String[] PRICE = {"21,685 USD", "45,421 USD", "22,500 USD"};
    private Retrofit retrofit;
    private ArrayList<model_package> packageModels;
    private RecyclerView recyclerView;
    private fragment_package_adapter PackageAdapter;
    private String activityName;

    public AdminPackagesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        packageModels = new ArrayList<>();
        for (int i = 0; i < Start.length; i++) {
            model_package PackageModelForRecyclerView = new model_package(pckgname[i], placesinpackage[i], Start[i], End[i], TIME[i], PRICE[i]);
            packageModels.add(PackageModelForRecyclerView);
        }
        retrofit = Singleton.getRetrofit();
        ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
        api.getPackages().enqueue(new Callback<ResultpakageSet>() {
            @Override
            public void onResponse(Call<ResultpakageSet> call, Response<ResultpakageSet> response) {
                packages = response.body().getValue();
                updateUI();
            }

            @Override
            public void onFailure(Call<ResultpakageSet> call, Throwable t) {

            }
        });
//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            int myInt = bundle.getInt("placeid", defaultValue);
//        }
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

        } else {
            if (activityName.equals("User")) {
                hint.setText("select package to see details and join");
                ((User) getActivity()).setActionBarTitle("Packages");
                ((User) getActivity()).setMenuItem(1);
                fab.setVisibility(View.GONE);
            }
        }
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
                    if (activityName.equals("Admin")) {
                        Intent intent = new Intent(getActivity(), AdminUpdatePackage.class);
                        intent.putExtra("Mode", 1);
                        intent.putExtra("package_id", modela.getId());
                        startActivity(intent);
                    } else if (activityName.equals("User")) {

                    }
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