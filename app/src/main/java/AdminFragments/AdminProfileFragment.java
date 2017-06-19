package AdminFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.google.gp_11.Admin;
import io.google.gp_11.R;
import io.google.gp_11.User;


public class AdminProfileFragment extends Fragment {

    private String activityName;
    private int mode;


    public AdminProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mode = bundle.getInt("mode");

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_admin, container, false);
        activityName = getActivity().getClass().getSimpleName();

        if (mode == 1) {
            ((Admin) getActivity()).setActionBarTitle("Add Guide");
            ((Admin) getActivity()).setMenuItem(2);
            Toast.makeText(getActivity(), "hi", Toast.LENGTH_LONG).show();
        } else if (mode == 2) {
            if (activityName.equals("Admin")) {
                ((Admin) getActivity()).setActionBarTitle("Profile");
                ((Admin) getActivity()).setMenuItem(5);
            } else if (activityName.equals("User")) {
                ((User) getActivity()).setActionBarTitle("Profile");
                ((User) getActivity()).setMenuItem(4);
            }
        }
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
