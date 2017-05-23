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

import AdminModels.model_guide;
import io.google.gp_11.AdminUpdateUser;
import io.google.gp_11.R;

public class AdminGuidesFragmemnt extends Fragment {

    private Integer[] IMAGE = {R.drawable.person1, R.drawable.person4, R.drawable.person5};
    private String[] Name = {"salah", "Saad Khalifa", "Mohamed Atef"};
    private String[] email = {"ab.com", "sa.com", "tefa.com"};

    private ArrayList<model_guide> guideModels;
    private RecyclerView recyclerView;
    private fragment_guide_adapter GuideAdapter;


    public AdminGuidesFragmemnt() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        guideModels = new ArrayList<>();
        for (int i = 0; i < Name.length; i++) {
            model_guide GuideModelForRecyclerView = new model_guide(IMAGE[i], Name[i], email[i]);
            guideModels.add(GuideModelForRecyclerView);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guides_admin, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setImageResource(R.drawable.adduser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getActivity(), AdminUpdateUser.class);
                intent.putExtra("Mode", 3);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewGuides);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;

    }

    private void updateUI() {
        GuideAdapter = new fragment_guide_adapter(guideModels);
        recyclerView.setAdapter(GuideAdapter);
    }


    private class GuideHolder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView guideimage;
        TextView guidename;
        TextView guideemail;
        LinearLayout ln;

        public GuideHolder(View view) {
            super(view);
            guideimage = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.guideImage);
            guidename = (TextView) view.findViewById(R.id.guideName);
            guideemail = (TextView) view.findViewById(R.id.guideName);
            ln = (LinearLayout) view.findViewById(R.id.linearLayoutGuide);

        }
    }

    private class fragment_guide_adapter extends RecyclerView.Adapter<GuideHolder> {
        private ArrayList<model_guide> models;

        public fragment_guide_adapter(ArrayList<model_guide> Models) {
            models = Models;
        }

        @Override
        public GuideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.recycler_view_guides, parent, false);
            return new GuideHolder(view);
        }

        @Override
        public void onBindViewHolder(GuideHolder holder, int position) {
            final model_guide modela = models.get(position);
            holder.guideimage.setImageResource(modela.getImage());
            holder.guidename.setText(modela.getName());
            holder.guideemail.setText(modela.getEmail());
            holder.ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AdminUpdateUser.class);
//<<<<<<< Updated upstream
                    intent.putExtra("Mode", 1);
//=======
//                    intent.putExtra("Mode",1);
//>>>>>>> Stashed changes
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
