package com.example.db.civil.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.db.civil.R;
import com.example.db.civil.activity.LoginActivity;
import com.example.db.civil.activity.SubscribeActivity;
import com.example.db.civil.adapter.ArticleAdapter;
import com.example.db.civil.beans.Article;
import com.example.db.civil.beans.ArticleInfo;
import com.example.db.civil.database.ArticleUtlDataBase;
import com.example.db.civil.utlis.AppConstant;
import com.example.db.civil.view.TitanicTextView;
import com.example.db.civil.view.views.indicator.Indicator;
import com.example.db.civil.view.views.indicator.IndicatorViewPager;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.JazzyListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BeautifulArticleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BeautifulArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeautifulArticleFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public FloatingActionButton mFloatingActionButton;

    public JazzyListView jazzyListView;

    public SwipeRefreshLayout mSwipeRefreshLayout;

    public ProgressDialog progressDialog;

    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    public List<Article> list;

    public ArrayList<String> temp;

    public ImageView mSubscribute;


    public ArticleAdapter articleAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeautifulArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeautifulArticleFragment newInstance(String param1, String param2) {
        BeautifulArticleFragment fragment = new BeautifulArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BeautifulArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        temp=new ArrayList<String>();
//        list=new ArrayList<Article>();
//
//        Bundle bundle=getActivity().getIntent().getExtras();
//
//        temp=bundle.getStringArrayList("article");
//
//        for (int i=0;i<temp.size();i++){
//            Article article=new Article();
//            List<String> url=new ArrayList<String>();
//            url.add(temp.get(i));
//            article.setUrl(url);
//            list.add(article);
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView=inflater.inflate(R.layout.fragment_beautiful_article,null);

        View mHeadView=inflater.inflate(R.layout.listview_head,null);
        mSubscribute=(ImageView)mHeadView.findViewById(R.id.subscribute);
        mSubscribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            }
        });

        ViewPager viewPager = (ViewPager) mHeadView.findViewById(R.id.guide_viewPager);
        Indicator indicator = (Indicator) mHeadView.findViewById(R.id.guide_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getActivity());
        indicatorViewPager.setAdapter(adapter);



        jazzyListView=(JazzyListView)RootView.findViewById(R.id.article_listview);
        jazzyListView.setTransitionEffect(AppConstant.LISTVIEW_EFFECT);
        jazzyListView.addHeaderView(mHeadView);
        jazzyListView.setHeaderDividersEnabled(false);

        ArticleAdapter articleAdapter=new ArticleAdapter(getActivity(), AppConstant.list,getArticleInfo());
        jazzyListView.setAdapter(articleAdapter);


//        new RemoteDataTask().execute();

        mSwipeRefreshLayout=(SwipeRefreshLayout)RootView.findViewById(R.id.article_refreshlayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RemoteDataTask().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mFloatingActionButton=(FloatingActionButton)RootView.findViewById(R.id.fab);

        mFloatingActionButton.attachToListView(jazzyListView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                Log.d("ListViewFragment", "onScrollDown()");
            }

            @Override
            public void onScrollUp() {
                Log.d("ListViewFragment", "onScrollUp()");
            }
        }, new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d("ListViewFragment", "onScrollStateChanged()");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("ListViewFragment", "onScroll()");
            }
        });


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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

    public List<ArticleInfo> getArticleInfo(){

        List<ArticleInfo> list=new ArrayList<ArticleInfo>();

        ArticleUtlDataBase articleUtlDataBase=new ArticleUtlDataBase(getActivity());
        SQLiteDatabase sqLiteDatabase=articleUtlDataBase.getWritableDatabase();

        Cursor cursor=sqLiteDatabase.query("articleurl",new String[]{"url","name"},null,null,null,null,null);

        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);

            ArticleInfo articleInfo=new ArticleInfo();
            articleInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            articleInfo.setDescription(cursor.getString(cursor.getColumnIndex("name")));

            list.add(articleInfo);
        }


        return list;
    }


    private class RemoteDataTask extends AsyncTask<Void, Void, List<Article>> {
        // Override this method to do custom remote calls

        List<ArticleInfo> articleInfos=getArticleInfo();

        List<Article> articles=new ArrayList<Article>();

        @Override
        protected List<Article> doInBackground(Void... params) {


            try {

                for (int i=0;i<articleInfos.size();i++){

                    List<String> list=new ArrayList<String>();

                    ArrayList<String> content=new ArrayList<String>();

                    Article article=new Article();
                    Document document=Jsoup.connect(articleInfos.get(i).getUrl()).get();

                    Elements image=document.getElementsByTag("p");

                    for (int j=0;j<image.size();j++){
//                        content.add(image.get(j).html());
                        String link=image.get(i).select("IMG").attr("src").toString();
                        if (link!=null){
                            list.add(link);
                        }
                    }

                    article.setUrl(list);
//                    article.setContent(content);

                    articles.add(article);

                }
                 /**
                 * 测试
                 *Document document= Jsoup.connect("http://www.edu24ol.com/web_news/html/2013-1/201301221029509529.html").get();
                 *Elements image=document.getElementsByTag("p");
                 *for (int i=0;i<image.size();i++){
                    Log.v("image",image.get(i).text());
                    String link=image.get(i).select("IMG").attr("src").toString();
                    Log.v("url",link);
                 *}
                 **/

            }catch (Exception e) {
                e.printStackTrace();
            }

            for (int t=0;t<articles.size();t++){
                Log.v("uuurl",articles.get(t).getUrl().get(0));
            }


            return articles;
        }
        @Override
        protected void onPreExecute() {
            progressDialog =
                    ProgressDialog.show(getActivity(), "", "Loading...", true);
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(List<Article> result) {
            articleAdapter=new ArticleAdapter(getActivity(),result,getArticleInfo());
            jazzyListView.setAdapter(articleAdapter);
            progressDialog.dismiss();

        }
    }
    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
        private int[] images = { R.drawable.empty, R.drawable.error, R.drawable.head, R.drawable.test };

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_guide, container, false);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new View(getActivity());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            convertView.setBackgroundResource(images[position]);
            return convertView;
        }

        @Override
        public int getCount() {
            return images.length;
        }
    };


}
