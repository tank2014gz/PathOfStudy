package fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.db.xiaoshiji.MainActivity;
import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.ActivityBringMeal;
import com.example.db.xiaoshiji.activity.ActivityFound;
import com.example.db.xiaoshiji.activity.ActivityLost;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.ArrayList;
import java.util.List;

import adapter.FindListAdapter;
import beans.BringMealInfo;
import utils.LeanCloudService;
import view.OverScrollView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentFind.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFind#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFind extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toolbar toolBar;
    public static final String TITLE="发现";

    public TextView mLost,mFound,mHelp,mDate;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFind.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFind newInstance(String param1, String param2) {
        FragmentFind fragment = new FragmentFind();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentFind() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_fragment_find, container, false);

//        (((MainActivity)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
//        toolBar=(((MainActivity)getActivity()).getToolbar());
//        toolBar.setTitle(TITLE);
//        toolBar.setSubtitle(null);

        mLost = (TextView)RootView.findViewById(R.id.tv_lost);
        mFound = (TextView)RootView.findViewById(R.id.tv_found);
        mHelp = (TextView)RootView.findViewById(R.id.tv_help);
        mDate = (TextView)RootView.findViewById(R.id.tv_date);

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().getSupportFragmentManager()
//                             .beginTransaction()
//                             .replace(R.id.container,new FragmentBringMeal())
//                             .commit();
                startActivity(new Intent(getActivity(), ActivityBringMeal.class));
            }
        });
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.container,new FragmentLost())
//                        .commit();
                startActivity(new Intent(getActivity(), ActivityLost.class));
            }
        });
        mFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityFound.class));
            }
        });

        return RootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
