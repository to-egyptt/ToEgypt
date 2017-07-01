package Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.google.ToEgypt.Admin;
import io.google.ToEgypt.Guide;
import io.google.ToEgypt.R;
import io.google.ToEgypt.UpdatePlace;
import io.google.ToEgypt.User;
import models.ResultPlaceSet;
import models.Singleton;
import models.ToEgyptAPI;
import models.place;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class Places extends Fragment {

//    private Integer[] IMAGE = {R.drawable.pyramids, R.drawable.tower, R.drawable.egyptianmuseum, R.drawable.sinai};
//    private String[] placeName = {"Pyramids", "Cairo Tower", "Egyptian Museum", "Mount Sinai"};
//    private String[] govenate = {"Giza", "Cairo", "Cairo", "Sinai"};
//    private String[] category = {"historical", "historical", "historical", "historical"};
//    private String[] placeDescription = {"The Egyptian pyramids are ancient pyramid-shaped masonry structures located in Egypt. The Great Pyramid was listed as one of the Seven Wonders of the World.",
//            "The Cairo Tower is a free-standing concrete tower located in Cairo, Egypt. At 187 m (614 ft), it has been the tallest structure in Egypt and North Africa for about 50 years",
//            "The Museum of Egyptian Antiquities is home to an extensive collection of ancient Egyptian antiquities. It has 120,000 items, with a representative amount on display, the remainder in storerooms",
//            "Mount Sinai is a mountain in the Sinai Peninsula of Egypt that is a possible location of the biblical Mount Sinai, According to Jewish, Christian, and Islamic tradition, the biblical Mount Sinai was the place where Moses received the Ten Commandments."
//    };

    //private ArrayList<model_place_old_abuelhassan> placeModels;
    private ArrayList<place> places;
    private RecyclerView recyclerView;
    private fragment_place_adapter placeAdapter;
    private ProgressDialog progressDialog;
    private Retrofit retrofit;
    private String activityName;
    /*
      modes to updatePlace
        1 update place  admin
        2 create place  admin
   */
    public Places() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Retrieving data. please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        retrofit = Singleton.getRetrofit();
        ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
        api.getPlaces().enqueue(new Callback<ResultPlaceSet>() {
            @Override
            public void onResponse(Call<ResultPlaceSet> call, Response<ResultPlaceSet> response) {
                if (response.isSuccess()) {
                    places = response.body().getValue();
                    updateUI();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResultPlaceSet> call, Throwable t) {
                Toast.makeText(getActivity(), "Error ,Please Check your internet connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
//        placeModels = new ArrayList<>();
//        for (int i = 0; i < placeName.length; i++) {
//            model_place_old_abuelhassan PlaceModelForRecyclerView = new model_place_old_abuelhassan(IMAGE[i], placeName[i], placeDescription[i], govenate[i], category[i]);
//            placeModels.add(PlaceModelForRecyclerView);
//        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.places, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        TextView hint = (TextView) view.findViewById(R.id.placeHint);
        activityName = getActivity().getClass().getSimpleName();
        if (activityName.equals("Admin")) {
            hint.setText("select place to edit");
            ((Admin) getActivity()).setActionBarTitle("Places");
            ((Admin) getActivity()).setMenuItem(4);
            fab.setImageResource(R.drawable.addplace);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Click action
                    Intent intent = new Intent(getActivity(), UpdatePlace.class);
                    intent.putExtra("Mode", 2);
                    startActivity(intent);
                }
            });

        } else if (activityName.equals("User")) {
            hint.setText("select place to show all packages that belongs to this place");
            ((User) getActivity()).setActionBarTitle("Places");
            ((User) getActivity()).setMenuItem(0);
            fab.setVisibility(View.GONE);
        } else if (activityName.equals("Guide")) {
            hint.setText("select place to show all packages that belongs to this place");
            ((Guide) getActivity()).setActionBarTitle("Places");
            ((Guide) getActivity()).setMenuItem(0);
            fab.setVisibility(View.GONE);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewPlaces);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //updateUI();
        return view;
    }

    private void updateUI() {
        placeAdapter = new fragment_place_adapter(places);
        recyclerView.setAdapter(placeAdapter);
    }

    private class PlacesHolder extends RecyclerView.ViewHolder {
        ImageView placeImage;
        TextView placeName;
        TextView placeDescription;
        TextView placeGovernorate;
        TextView placeCategory;
        LinearLayout placeCard;

        public PlacesHolder(View view) {
            super(view);
            placeImage = (ImageView) view.findViewById(R.id.placeImage);
            placeName = (TextView) view.findViewById(R.id.placeName);
            placeDescription = (TextView) view.findViewById(R.id.placeDescription);
            placeGovernorate = (TextView) view.findViewById(R.id.placeGovernate);
            placeCategory = (TextView) view.findViewById(R.id.placeCategory);
            placeCard = (LinearLayout) view.findViewById(R.id.linearLayoutPlace);
        }
    }

    private class fragment_place_adapter extends RecyclerView.Adapter<PlacesHolder> {
        private ArrayList<place> models;

        public fragment_place_adapter(ArrayList<place> Models) {
            models = Models;
        }

        @Override
        public PlacesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.recycler_view_places, parent, false);
            return new PlacesHolder(view);
        }

        @Override
        public void onBindViewHolder(PlacesHolder holder, int position) {
            final place modela = models.get(position);
            holder.placeImage.setImageResource(R.drawable.sinai);
            holder.placeName.setText(modela.getName());
            holder.placeDescription.setText(modela.getDescription());
            holder.placeGovernorate.setText(modela.getGovernate().getName());
            holder.placeCategory.setText(modela.getCategory().getName());
            holder.placeCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activityName.equals("Admin")) {
                        Intent intent = new Intent(getActivity(), UpdatePlace.class);
                        intent.putExtra("Mode", 1);
                        intent.putExtra("place_id", modela.getId());
                        startActivity(intent);
                    } else if (activityName.equals("User")) {
                        Fragment fragment = new Packages();
                            Bundle bundle = new Bundle();
                        bundle.putInt("placeId", modela.getId());
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.userContent, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                    } else if (activityName.equals("Guide")) {
                        Fragment fragment = new Packages();
                        Bundle bundle = new Bundle();
                        bundle.putInt("placeId", 1);
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.guideContent, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
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
