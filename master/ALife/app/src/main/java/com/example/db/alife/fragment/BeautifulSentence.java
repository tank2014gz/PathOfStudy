package com.example.db.alife.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.db.alife.R;
import com.example.db.alife.adapter.BeautifulSentenceAdapter;
import com.example.db.alife.adapter.EnglishMotoAdapter;
import com.example.db.alife.beans.BeautifulSentenceInfo;
import com.example.db.alife.beans.EnglishMotoInfo;
import com.example.db.alife.view.ALifeToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BeautifulSentence.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BeautifulSentence#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeautifulSentence extends Fragment {

    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public ListView mListView;
    public SwipeRefreshLayout mSwipeRefreshLayout;

    public ProgressDialog progressDialog;

    public BeautifulSentence newInstance(String text){
        BeautifulSentence mFragment = new BeautifulSentence();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.fragment_beautiful_sentence, container, false);

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText) searchView.findViewById(R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.nliveo_white));
        searchView.setOnQueryTextListener(onQuerySearchView);

        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case R.id.menu_search:
                mSearchCheck = true;

                break;
        }
        return true;
    }

    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            if (mSearchCheck){
                // implement your search here
            }
            return false;
        }
    };
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    private class RemoteDataTask0 extends AsyncTask<Void, Integer, ArrayList<BeautifulSentenceInfo>> {

        public ArrayList<BeautifulSentenceInfo> beautifulSentenceInfos = new ArrayList<BeautifulSentenceInfo>();

        @Override
        protected ArrayList<BeautifulSentenceInfo> doInBackground(Void... params) {

            try {
                Document document = Jsoup.connect("http://www.egouz.com/yuedu/weimei/").get();
                Elements elements = document.getElementsByClass("block");
                Elements root = document.getElementsByClass("ulcl");
                Elements li = root.get(0).getElementsByTag("li");
                for (int i=0;i<elements.size()-1;i++){
                    BeautifulSentenceInfo beautifulSentenceInfo = new BeautifulSentenceInfo();
                    Elements a = elements.get(i).getElementsByTag("a");
                    String link = a.get(0).attr("href");
                    String title = a.get(0).text();
                    Elements description0 = elements.get(i).getElementsByClass("memo");
                    String description = description0.get(0).text();
                    Elements tag0 = elements.get(i).getElementsByClass("tags");
                    Elements tags = tag0.get(0).getElementsByTag("a");
                    String tag = "";
                    for (int j=0;j<tags.size()-1;j++){
                        tag = tag + tags.get(j).text()+" ";
                    }
                    Elements a0 = li.get(i).getElementsByTag("a");
                    Elements imgs = a0.get(0).getElementsByTag("img");
                    beautifulSentenceInfo.setUrl(link);
                    beautifulSentenceInfo.setTitle(title);
                    beautifulSentenceInfo.setPicture(imgs.get(0).attr("src"));
                    beautifulSentenceInfo.setDescription(description);
                    beautifulSentenceInfo.setTag(tag);
                    beautifulSentenceInfos.add(beautifulSentenceInfo);
                }

            }catch (Exception e){
                Log.v("error0", e.getMessage());
            }

            return beautifulSentenceInfos;
        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog =
                    ProgressDialog.show(getActivity(), "", "Loading...", true);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }
        @Override
        protected void onPostExecute(ArrayList<BeautifulSentenceInfo> result) {
            if (result!=null){
                BeautifulSentenceAdapter beautifulSentenceAdapter = new BeautifulSentenceAdapter(getActivity(),result);
                mListView.setAdapter(beautifulSentenceAdapter);
                progressDialog.dismiss();
                ALifeToast.makeText(getActivity(), "加载了" + String.valueOf(result.size()) + "消息！", ALifeToast.ToastType.SUCCESS, ALifeToast.LENGTH_SHORT).show();
            }

        }
    }
}
