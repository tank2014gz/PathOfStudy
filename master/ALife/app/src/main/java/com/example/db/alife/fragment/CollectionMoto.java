package com.example.db.alife.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.db.alife.R;
import com.example.db.alife.adapter.EnglishMotoAdapter;
import com.example.db.alife.beans.EnglishMotoInfo;
import com.example.db.alife.database.MotoDatabaseHelper;
import com.example.db.alife.view.ALifeToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CollectionMoto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CollectionMoto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionMoto extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListView mListView;
    public SwipeRefreshLayout mSwipeRefreshLayout;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectionMoto.
     */
    // TODO: Rename and change types and number of parameters
    public static CollectionMoto newInstance(String param1, String param2) {
        CollectionMoto fragment = new CollectionMoto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CollectionMoto() {
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_collection_moto, container, false);

        mListView = (ListView)rootView.findViewById(R.id.listview);
        View mHeadView = LayoutInflater.from(getActivity()).inflate(R.layout.head_view,null);
        View mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.footer_view,null);
        mListView.addHeaderView(mHeadView);
        mListView.addFooterView(mFooterView);

        mSwipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refreshlayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RemoteDataTask0().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        new RemoteDataTask0().execute();

        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    private class RemoteDataTask0 extends AsyncTask<Void, Integer, ArrayList<EnglishMotoInfo>> {

        public ArrayList<EnglishMotoInfo> englishMotoInfos = new ArrayList<EnglishMotoInfo>();

        @Override
        protected ArrayList<EnglishMotoInfo> doInBackground(Void... params) {

            MotoDatabaseHelper motoDatabaseHelper = new MotoDatabaseHelper(getActivity());
            SQLiteDatabase sqLiteDatabase = motoDatabaseHelper.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.query("alife_moto",new String[]{"title","tag","description","picture","url"},null,null,null,null,null);

            for (int i=0;i<cursor.getCount()-1;i++){

                cursor.moveToPosition(i);
                EnglishMotoInfo englishMotoInfo = new EnglishMotoInfo();
                englishMotoInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                englishMotoInfo.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                englishMotoInfo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                englishMotoInfo.setPicture(cursor.getString(cursor.getColumnIndex("picture")));
                englishMotoInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                englishMotoInfos.add(englishMotoInfo);

            }

            return englishMotoInfos;
        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }
        @Override
        protected void onPostExecute(final ArrayList<EnglishMotoInfo> result) {
            if (result!=null){
                EnglishMotoAdapter englishMotoAdapter = new EnglishMotoAdapter(getActivity(),result);
                mListView.setAdapter(englishMotoAdapter);
                ALifeToast.makeText(getActivity(), "收藏了" + String.valueOf(result.size()) + "消息！", ALifeToast.ToastType.SUCCESS, ALifeToast.LENGTH_SHORT).show();
            }

        }
    }
}
