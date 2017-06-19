package AdminFragments;


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

import java.util.ArrayList;

import AdminModels.model_place;
import io.google.gp_11.Admin;
import io.google.gp_11.AdminUpdatePlace;
import io.google.gp_11.R;
import io.google.gp_11.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminPlacesFragment extends Fragment {

    private Integer[] IMAGE = {R.drawable.pyramids, R.drawable.tower, R.drawable.egyptianmuseum, R.drawable.sinai};
    private String[] placeName = {"Pyramids", "Cairo Tower", "Egyptian Museum", "Mount Sinai"};
    private String[] govenate = {"Giza", "Cairo", "Cairo", "Sinai"};
    private String[] category = {"historical", "historical", "historical", "historical"};
    private String[] placeDescription = {"The Egyptian pyramids are ancient pyramid-shaped masonry structures located in Egypt. The Great Pyramid was listed as one of the Seven Wonders of the World.",
            "The Cairo Tower is a free-standing concrete tower located in Cairo, Egypt. At 187 m (614 ft), it has been the tallest structure in Egypt and North Africa for about 50 years",
            "The Museum of Egyptian Antiquities is home to an extensive collection of ancient Egyptian antiquities. It has 120,000 items, with a representative amount on display, the remainder in storerooms",
            "Mount Sinai is a mountain in the Sinai Peninsula of Egypt that is a possible location of the biblical Mount Sinai, According to Jewish, Christian, and Islamic tradition, the biblical Mount Sinai was the place where Moses received the Ten Commandments."
    };

    private ArrayList<model_place> placeModels;
    private RecyclerView recyclerView;
    private fragment_place_adapter placeAdapter;

    private String activityName;

    /*
                modes
                1 update place      admin
                2 create place      admin
                */


    public AdminPlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        placeModels = new ArrayList<>();
        for (int i = 0; i < placeName.length; i++) {
            model_place PlaceModelForRecyclerView = new model_place(IMAGE[i], placeName[i], placeDescription[i], govenate[i], category[i]);
            placeModels.add(PlaceModelForRecyclerView);
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_places_admin, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        TextView hint = (TextView) view.findViewById(R.id.placeHint);
        activityName = getActivity().getClass().getSimpleName();
        if (activityName.equals("Admin")) {
            hint.setText("select place to edit");
            ((Admin) getActivity()).setActionBarTitle("Places");
            ((Admin) getActivity()).setMenuItem(4);

        } else {
            if (activityName.equals("User")) {
                hint.setText("select place to show all packages that belongs to this place");
                ((User) getActivity()).setActionBarTitle("Places");
                ((User) getActivity()).setMenuItem(0);
                fab.setVisibility(View.GONE);
            }
        }

        fab.setImageResource(R.drawable.addplace);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getActivity(), AdminUpdatePlace.class);
                intent.putExtra("Mode", 2);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewPlaces);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        placeAdapter = new fragment_place_adapter(placeModels);
        recyclerView.setAdapter(placeAdapter);
    }

    private class PlacesHolder extends RecyclerView.ViewHolder {
        ImageView placeImage;
        TextView placeName;
        TextView placeDescritpion;
        TextView Governate;
        TextView Category;
        LinearLayout ln;

        public PlacesHolder(View view) {
            super(view);
            placeImage = (ImageView) view.findViewById(R.id.placeImage);
            placeName = (TextView) view.findViewById(R.id.placeName);
            placeDescritpion = (TextView) view.findViewById(R.id.placeDescription);
            Governate = (TextView) view.findViewById(R.id.placeGovernate);
            Category = (TextView) view.findViewById(R.id.placeCategory);
            ln = (LinearLayout) view.findViewById(R.id.linearLayoutPlace);
        }
    }

    private class fragment_place_adapter extends RecyclerView.Adapter<PlacesHolder> {
        private ArrayList<model_place> models;

        public fragment_place_adapter(ArrayList<model_place> Models) {
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
            final model_place modela = models.get(position);
            holder.placeImage.setImageResource(modela.getImage());
            holder.placeName.setText(modela.getPlaceName());
            holder.placeDescritpion.setText(modela.getPlaceDescription());
            holder.Governate.setText(modela.getPlaceGovernate());
            holder.Category.setText(modela.getPlaceCategory());
            holder.ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activityName.equals("Admin")) {

                        Intent intent = new Intent(getActivity(), AdminUpdatePlace.class);
                        intent.putExtra("Mode", 1);
                        startActivity(intent);
                    } else {
                        if (activityName.equals("User")) {


//                            Bundle bundle = new Bundle();
//                            bundle.putInt("placeid", 1);
//                            fragment.setArguments(bundle);

                            Fragment fragment = new AdminPackagesFragment();

                            FragmentManager fragmentManager = getFragmentManager();

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            fragmentTransaction.replace(R.id.userContent, fragment);

                            fragmentTransaction.addToBackStack("packages");

                            fragmentTransaction.commit();
                        }
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
