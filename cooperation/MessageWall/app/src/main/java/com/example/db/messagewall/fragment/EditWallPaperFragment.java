package com.example.db.messagewall.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.db.messagewall.activity.MainActivity;
import com.example.db.messagewall.adapter.WallPaperGridAdapter;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.fab.FloatingActionMenu;
import com.support.android.designlibdemo.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditWallPaperFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditWallPaperFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditWallPaperFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    public GridView mGridView;
    public WallPaperGridAdapter wallPaperGridAdapter;
    public com.example.db.messagewall.view.fab.FloatingActionButton floatingActionButton;
    public TextView select;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditWallPaperFragment.
     */

    public static EditWallPaperFragment newInstance(String param1, String param2) {

        EditWallPaperFragment fragment = new EditWallPaperFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public EditWallPaperFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            bundle = this.getArguments();
            CONVERSATION_ID = bundle.getString("_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_wall_paper, container, false);

        floatingActionButton = (com.example.db.messagewall.view.fab.FloatingActionButton)rootView.findViewById(R.id.add);
        floatingActionButton.show(true);

        mGridView = (GridView)rootView.findViewById(R.id.gridview);
        select = (TextView)rootView.findViewById(R.id.select);

        wallPaperGridAdapter = new WallPaperGridAdapter(getActivity());
        mGridView.setAdapter(wallPaperGridAdapter);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(intent,2);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wallPaperGridAdapter.flag){
                    SharedPreferences sharedPreferences = getActivity()
                            .getSharedPreferences("com.example.db.alife_wallpaper"
                                    , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("paper_path", wallPaperGridAdapter.getPath());
                    editor.commit();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("_ID",CONVERSATION_ID);
                    intent.putExtras(bundle);
                    getActivity().finish();
                    getActivity().startActivity(intent);
                }else {
                    ALifeToast.makeText(getActivity()
                            , "请选择图片！"
                            , ALifeToast.ToastType.SUCCESS
                            , ALifeToast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        return rootView;
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

        public void onFragmentInteraction(Uri uri);
    }

}
