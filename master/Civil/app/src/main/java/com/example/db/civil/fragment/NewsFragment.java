package com.example.db.civil.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.example.db.civil.R;
import com.example.db.civil.adapter.NewsAdapter;
import com.example.db.civil.adapter.NewsViewPagerAdapter;
import com.example.db.civil.beans.NewsInfo;
import com.example.db.civil.beans.SoftWareInfo;
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
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TextView tv1,tv2,tv3;
    public ViewPager mViewPager;

    public Handler handler;

    public JazzyListView jazzyListView0,jazzyListView1,jazzyListView2;
    public FloatingActionButton floatingActionButton0,floatingActionButton1,floatingActionButton2;
    public SwipeRefreshLayout mSwipeRefreshLayout0,mSwipeRefreshLayout1,mSwipeRefreshLayout2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsFragment() {
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
        View rootView=inflater.inflate(R.layout.fragment_news, container, false);

        new RemoteDataTask0().execute();
        View mHeadView=LayoutInflater.from(getActivity()).inflate(R.layout.temp,null);

        List<View> list=new ArrayList<View>();

        tv1=(TextView)rootView.findViewById(R.id.tv1);
        tv1.setTextColor(Color.parseColor("#ffffff"));
        tv2=(TextView)rootView.findViewById(R.id.tv2);
        tv3=(TextView)rootView.findViewById(R.id.tv3);

        mViewPager=(ViewPager)rootView.findViewById(R.id.news_viewPager);


        View view1=LayoutInflater.from(getActivity()).inflate(R.layout.news_jianzhu, null);
        jazzyListView0=(JazzyListView)view1.findViewById(R.id.article_listview);
        jazzyListView0.setTransitionEffect(JazzyHelper.TILT);
        jazzyListView0.setHeaderDividersEnabled(false);
        jazzyListView0.addHeaderView(mHeadView);
        jazzyListView0.addFooterView(mHeadView);
        floatingActionButton0=(FloatingActionButton)view1.findViewById(R.id.fab);
        mSwipeRefreshLayout0=(SwipeRefreshLayout)view1.findViewById(R.id.article_refreshlayout);
        mSwipeRefreshLayout0.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RemoteDataTask0().execute();
                mSwipeRefreshLayout0.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout0.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        View view2=LayoutInflater.from(getActivity()).inflate(R.layout.news_jiegou, null);
        jazzyListView1=(JazzyListView)view2.findViewById(R.id.article_listview);
        jazzyListView1.setTransitionEffect(JazzyHelper.TILT);
        jazzyListView1.setHeaderDividersEnabled(false);
        jazzyListView1.addHeaderView(mHeadView);
        jazzyListView1.addFooterView(mHeadView);
        floatingActionButton1=(FloatingActionButton)view2.findViewById(R.id.fab);
        mSwipeRefreshLayout1=(SwipeRefreshLayout)view2.findViewById(R.id.article_refreshlayout);
        mSwipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RemoteDataTask1().execute();
                mSwipeRefreshLayout1.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout1.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        View view3=LayoutInflater.from(getActivity()).inflate(R.layout.news_yuanlin, null);
        jazzyListView2=(JazzyListView)view3.findViewById(R.id.article_listview);
        jazzyListView2.setTransitionEffect(JazzyHelper.TILT);
        jazzyListView2.setHeaderDividersEnabled(false);
        jazzyListView2.addHeaderView(mHeadView);
        jazzyListView2.addFooterView(mHeadView);
        floatingActionButton2=(FloatingActionButton)view3.findViewById(R.id.fab);
        mSwipeRefreshLayout2=(SwipeRefreshLayout)view3.findViewById(R.id.article_refreshlayout);
        mSwipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RemoteDataTask2().execute();
                mSwipeRefreshLayout2.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout2.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        list.add(view1);
        list.add(view2);
        list.add(view3);

        NewsViewPagerAdapter newsViewPagerAdapter=new NewsViewPagerAdapter(getActivity(),list);
        mViewPager.setAdapter(newsViewPagerAdapter);
        mViewPager.setCurrentItem(0);


        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x123){
                    NewsAdapter newsAdapter=new NewsAdapter(getActivity(),(ArrayList<NewsInfo>)msg.obj);
                    jazzyListView0.setAdapter(newsAdapter);

                }else if (msg.what==0x122){
                    NewsAdapter newsAdapter=new NewsAdapter(getActivity(),(ArrayList<NewsInfo>)msg.obj);
                    jazzyListView1.setAdapter(newsAdapter);
                }else if (msg.what==0x121){
                    NewsAdapter newsAdapter=new NewsAdapter(getActivity(),(ArrayList<NewsInfo>)msg.obj);
                    jazzyListView2.setAdapter(newsAdapter);
                }
            }
        };

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    tv1.setTextColor(Color.parseColor("#ffffff"));
                    tv2.setTextColor(Color.parseColor("#78dcca"));
                    tv3.setTextColor(Color.parseColor("#78dcca"));
                    new RemoteDataTask0().execute();
                }else if (position==1){
                    tv2.setTextColor(Color.parseColor("#ffffff"));
                    tv1.setTextColor(Color.parseColor("#78dcca"));
                    tv3.setTextColor(Color.parseColor("#78dcca"));
                    new RemoteDataTask1().execute();
                }else if (position==2){
                    tv3.setTextColor(Color.parseColor("#ffffff"));
                    tv2.setTextColor(Color.parseColor("#78dcca"));
                    tv1.setTextColor(Color.parseColor("#78dcca"));
                    new RemoteDataTask2().execute();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        floatingActionButton0.attachToListView(jazzyListView0, new ScrollDirectionListener() {
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

        floatingActionButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RemoteDataTask0().execute();
            }
        });

        floatingActionButton1.attachToListView(jazzyListView1, new ScrollDirectionListener() {
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

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RemoteDataTask1().execute();
            }
        });

        floatingActionButton2.attachToListView(jazzyListView2, new ScrollDirectionListener() {
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

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RemoteDataTask2().execute();
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1);
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(2);
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class RemoteDataTask0 extends AsyncTask<Void, Integer, ArrayList<NewsInfo>> {
        // Override this method to do custom remote calls

        public ArrayList<NewsInfo> newsInfos=new ArrayList<NewsInfo>();


        @Override
        protected ArrayList<NewsInfo> doInBackground(Void... params) {

            try {
                Document document= Jsoup.connect("http://m.co188.com/").get();

                Elements elements=document.getElementsByClass("box1_body");

                Elements list=elements.get(0).getElementsByTag("li");
                for (int i=0;i<list.size();i++){
                    NewsInfo newsInfo=new NewsInfo();
                    String href=list.get(i).getElementsByTag("a").attr("href");
                    String title=list.get(i).getElementsByTag("a").text();
                    newsInfo.setTitle(title);
                    newsInfo.setUrl(href);
                    newsInfos.add(newsInfo);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return newsInfos;
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
        protected void onPostExecute(ArrayList<NewsInfo> result) {
            Message message=Message.obtain();
            message.obj=result;
            message.what=0x123;
            handler.sendMessage(message);
        }
    }
    private class RemoteDataTask1 extends AsyncTask<Void, Integer, ArrayList<NewsInfo>> {
        // Override this method to do custom remote calls

        public ArrayList<NewsInfo> newsInfos=new ArrayList<NewsInfo>();


        @Override
        protected ArrayList<NewsInfo> doInBackground(Void... params) {

            try {
                Document document= Jsoup.connect("http://m.co188.com/").get();

                Elements elements=document.getElementsByClass("box1_body");

                Elements list=elements.get(1).getElementsByTag("li");
                for (int i=0;i<list.size();i++){
                    NewsInfo newsInfo=new NewsInfo();
                    String href=list.get(i).getElementsByTag("a").attr("href");
                    String title=list.get(i).getElementsByTag("a").text();
                    newsInfo.setTitle(title);
                    newsInfo.setUrl(href);
                    newsInfos.add(newsInfo);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return newsInfos;
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
        protected void onPostExecute(ArrayList<NewsInfo> result) {
            Message message=Message.obtain();
            message.obj=result;
            message.what=0x122;
            handler.sendMessage(message);
        }
    }

    private class RemoteDataTask2 extends AsyncTask<Void, Integer, ArrayList<NewsInfo>> {
        // Override this method to do custom remote calls

        public ArrayList<NewsInfo> newsInfos=new ArrayList<NewsInfo>();


        @Override
        protected ArrayList<NewsInfo> doInBackground(Void... params) {

            try {
                Document document= Jsoup.connect("http://m.co188.com/").get();

                Elements elements=document.getElementsByClass("box1_body");

                Elements list=elements.get(2).getElementsByTag("li");
                for (int i=0;i<list.size();i++){
                    NewsInfo newsInfo=new NewsInfo();
                    String href=list.get(i).getElementsByTag("a").attr("href");
                    String title=list.get(i).getElementsByTag("a").text();
                    newsInfo.setTitle(title);
                    newsInfo.setUrl(href);
                    newsInfos.add(newsInfo);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return newsInfos;
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
        protected void onPostExecute(ArrayList<NewsInfo> result) {
            Message message=Message.obtain();
            message.obj=result;
            message.what=0x121;
            handler.sendMessage(message);
        }
    }

}
