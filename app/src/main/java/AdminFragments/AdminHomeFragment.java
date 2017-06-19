package AdminFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.google.gp_11.Admin;
import io.google.gp_11.R;
import models.Singleton;
import models.ToEgyptAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminHomeFragment extends Fragment {


    private TextView numOfUsers;
    private TextView numOfGuides;
    private TextView numeOfPlaces;
    private TextView numOfPackages;
    Retrofit retrofit;
    public AdminHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_admin, container, false);
        numOfUsers = (TextView) view.findViewById(R.id.number_users);
        numeOfPlaces = (TextView) view.findViewById(R.id.number_places);
        numOfGuides = (TextView) view.findViewById(R.id.number_guides);
        numOfPackages = (TextView) view.findViewById(R.id.number_packages);
        retrofit = Singleton.getRetrofit();
        ToEgyptAPI api = retrofit.create(ToEgyptAPI.class);
        api.getUsersCount().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int n = response.body();
                numOfUsers.setText(String.valueOf(n));
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        api.getGuideCount().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                numOfGuides.setText(String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        ((Admin) getActivity()).setActionBarTitle("Home");
        return view;
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