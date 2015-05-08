package com.example.db.tline.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db.tline.R;
import com.example.db.tline.SetPasswordActivity;
import com.example.db.tline.activity.CameraActivity;
import com.example.db.tline.activity.HomePagerActicity;
import com.example.db.tline.activity.LoginActivity;
import com.example.db.tline.activity.SettingActivity;
import com.example.db.tline.adapter.HomeViewPagerAdapter;
import com.example.db.tline.adapter.PictureLineAdapter;
import com.example.db.tline.adapter.TextTimeLineAdapter;
import com.example.db.tline.beans.PictureLineInfo;
import com.example.db.tline.beans.TextLineInfo;
import com.example.db.tline.database.PLineSQLiDataBaseHelper;
import com.example.db.tline.database.TLineSQLiDataBaseHelper;
import com.example.db.tline.floatingactionbutton.FloatingActionButton;
import com.example.db.tline.pathanim.AnimatorPath;
import com.example.db.tline.pathanim.PathEvaluator;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.PullToZoomListView;
import com.example.db.tline.view.RevealLayout;
import com.example.db.tline.view.views.indicator.Indicator;
import com.example.db.tline.view.views.indicator.IndicatorViewPager;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public final static float SCALE_FACTOR      = 13f;
    public final static int ANIMATION_DURATION  = 300;
    public final static int MINIMUN_X_DISTANCE  = 200;

    private boolean mRevealFlag;
    private float mFabSize;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentTransaction fragmentTransaction;

    public ListView mListView1, mListView2, mListView3;

    public FloatingActionButton floatingActionButtonText, floatingActionButtonPicture,floatingActionButtonCamera;

    public LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4;


    public ViewPager viewPager;
    public IndicatorViewPager indicatorViewPager;
    public List<View> mList = new ArrayList<View>();
    public Button mLogin,mMore;
    public Bundle bundle;
    public String tText,tContent,pText,pContent,pUri;
    public LayoutInflater inflate;

    public RadioButton mRadioPic,mRadioText,mRatiomy;


    public RevealLayout mRevealLayout;
    public boolean mIsAnimationSlowDown = false;
    public boolean mIsBaseOnTouchLocation = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.v("lililili", AppConstant.getCurrentTime());
        bundle=this.getArguments();

        mRevealLayout = (RevealLayout) rootView.findViewById(R.id.reveal_layout);

        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.activity_up_move_in,R.anim.abc_fade_out);

        mMore=(Button)rootView.findViewById(R.id.btn_more);
        mRadioPic=(RadioButton)rootView.findViewById(R.id.radio_picture);
        mRadioText=(RadioButton)rootView.findViewById(R.id.ratio_text);
        mRatiomy=(RadioButton)rootView.findViewById(R.id.ratio_my);


        mLogin=(Button)rootView.findViewById(R.id.login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        View view1 = inflater.inflate(R.layout.text_line, null);
        mListView1 = (ListView) view1.findViewById(R.id.list_view);
        View mHeadView1 = inflater.inflate(R.layout.common_head, null);
        View mFootView1 = inflater.inflate(R.layout.foot_view,null);
        mListView1.addFooterView(mFootView1);
        mListView1.addHeaderView(mHeadView1);
//        mListView1.getHeaderView().setImageResource(R.drawable.scroll2);
//        mListView1.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);



        if (getPictureLineInfo().size()!=0){
            PictureLineAdapter pictureLineAdapter1 = new PictureLineAdapter(getActivity(),getPictureLineInfo());
            mListView1.setAdapter(pictureLineAdapter1);
        }

        floatingActionButtonPicture = (FloatingActionButton) rootView.findViewById(R.id.edit_picture);
        floatingActionButtonText = (FloatingActionButton) rootView.findViewById(R.id.edit_text);
        floatingActionButtonCamera=(FloatingActionButton)rootView.findViewById(R.id.edit_camera);


        mRevealLayout.setContentShown(false);
        mRevealLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRevealLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mRevealLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRevealLayout.show();
                    }
                }, 50);
            }
        });


        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        floatingActionButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.container, new FabTextFragment()).commit();
            }
        });

        floatingActionButtonPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                getActivity().startActivityForResult(intent, 2);

            }
        });

        floatingActionButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), CameraActivity.class);
                getActivity().startActivityForResult(intent,2);
            }
        });

        View view2 = inflater.inflate(R.layout.text_line, null);
        mListView2 = (ListView) view2.findViewById(R.id.list_view);
        View mHeadView2 = inflater.inflate(R.layout.common_head, null);
        View mFootView2 = inflater.inflate(R.layout.foot_view,null);
        mListView2.addFooterView(mFootView2);
        mListView2.addHeaderView(mHeadView2);
//        mListView2.getHeaderView().setImageResource(R.drawable.scroll1);
//        mListView2.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);



        if (getTextLineInfo().size()!=0){
            TextTimeLineAdapter textTimeLineAdapter = new TextTimeLineAdapter(getActivity(),getTextLineInfo());
            mListView2.setAdapter(textTimeLineAdapter);
        }







        final View view3 = inflater.inflate(R.layout.person_line, null);

        linearLayout1=(LinearLayout)view3.findViewById(R.id.home_page);
        linearLayout2=(LinearLayout)view3.findViewById(R.id.collection);
        linearLayout3=(LinearLayout)view3.findViewById(R.id.safe);
        linearLayout4=(LinearLayout)view3.findViewById(R.id.copy);

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), HomePagerActicity.class);
                startActivity(intent);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SetPasswordActivity.class);
                startActivity(intent);
            }
        });

        BadgeView badgeView = new BadgeView(getActivity(), linearLayout1);
        badgeView.setBadgeBackgroundColor(getActivity().getResources().getColor(R.color.white));
        badgeView.setBadgePosition(2);
        badgeView.setBadgeMargin(32, 40);
        if (AppConstant.LOGIN_STATUS){
            badgeView.setText("已登陆");
            badgeView.show();
        }else {
            badgeView.setText("未登录");
            badgeView.show();
        }

        BadgeView badge = new BadgeView(getActivity(), linearLayout2);
        badge.setText("0");
        badge.setBadgeBackgroundColor(getActivity().getResources().getColor(R.color.white));
        badge.setBadgePosition(2);
        badge.setBadgeMargin(32,40);
        badge.show();

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);


        viewPager = (ViewPager) rootView.findViewById(R.id.guide_viewPager);
        HomeViewPagerAdapter homeViewPagerAdapter = new HomeViewPagerAdapter(getActivity(), mList);
        viewPager.setAdapter(homeViewPagerAdapter);

        viewPager.setCurrentItem(0);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {

                    mRadioPic.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_left_pressed));
                    mRadioText.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_my));
                    mRatiomy.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_right));
                    mRadioPic.setTextColor(getActivity().getResources().getColor(R.color.actionbar));
                    mRadioText.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));
                    mRatiomy.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));

                } else if (position == 1) {

                    mRadioText.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_my_pressed));
                    mRadioPic.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_left));
                    mRatiomy.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_right));
                    mRadioText.setTextColor(getActivity().getResources().getColor(R.color.actionbar));
                    mRadioPic.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));
                    mRatiomy.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));

                } else if (position == 2) {

                    mRatiomy.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_right_pressed));
                    mRadioPic.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_left));
                    mRadioText.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_my));
                    mRatiomy.setTextColor(getActivity().getResources().getColor(R.color.actionbar));
                    mRadioPic.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));
                    mRadioText.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mRadioPic.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                mRadioPic.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_left_pressed));
                mRadioText.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_my));
                mRatiomy.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_right));
                mRadioPic.setTextColor(getActivity().getResources().getColor(R.color.actionbar));
                mRadioText.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));
                mRatiomy.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));
                viewPager.setCurrentItem(0);
            }
        });
        mRadioText.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                mRadioText.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_my_pressed));
                mRadioPic.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_left));
                mRatiomy.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_right));
                mRadioText.setTextColor(getActivity().getResources().getColor(R.color.actionbar));
                mRadioPic.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));
                mRatiomy.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));
                viewPager.setCurrentItem(1);
            }
        });
        mRatiomy.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                mRatiomy.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_right_pressed));
                mRadioPic.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_bkg_left));
                mRadioText.setBackground(getActivity().getResources().getDrawable(R.drawable.ratio_my));
                mRatiomy.setTextColor(getActivity().getResources().getColor(R.color.actionbar));
                mRadioPic.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));
                mRadioText.setTextColor(getActivity().getResources().getColor(R.color.white_pressed));
                viewPager.setCurrentItem(2);

            }
        });


        if (bundle!=null){
            switch (bundle.getString("command")){
                case "text":
                    tText=bundle.getString("tText");
                    tContent=bundle.getString("tContent");
                    viewPager.setCurrentItem(Integer.valueOf(bundle.getString("tItem")));
                    Log.v("mb",tText+tContent);
                    break;
                case "picture":
                    viewPager.setCurrentItem(Integer.valueOf(bundle.getString("pItem")));
                    break;
            }
        }

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
    public ArrayList<TextLineInfo> getTextLineInfo(){

        ArrayList<TextLineInfo> textLineInfos=new ArrayList<TextLineInfo>();
        TLineSQLiDataBaseHelper tLineSQLiDataBaseHelper=new TLineSQLiDataBaseHelper(getActivity());
        SQLiteDatabase sqLiteDatabase=tLineSQLiDataBaseHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query("textline",new String[]{"title","content","date","location"},null,null,null,null,null);
        if (cursor.getCount()!=0){
            for (int i=0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                TextLineInfo textLineInfo=new TextLineInfo();
                textLineInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                textLineInfo.setContent(cursor.getString(cursor.getColumnIndex("content")));
                textLineInfo.setDate(cursor.getString(cursor.getColumnIndex("date")));
                textLineInfo.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                textLineInfos.add(textLineInfo);
            }
        }

        return textLineInfos;
    }
    public ArrayList<PictureLineInfo> getPictureLineInfo(){

        ArrayList<PictureLineInfo> pictureLineInfos=new ArrayList<PictureLineInfo>();
        PLineSQLiDataBaseHelper pLineSQLiDataBaseHelper=new PLineSQLiDataBaseHelper(getActivity());
        SQLiteDatabase sqLiteDatabase=pLineSQLiDataBaseHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query("pictureline",new String[]{"title","content","uri","date","location"},null,null,null,null,null);
        if (cursor.getCount()!=0){
            for (int i=0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                PictureLineInfo pictureLineInfo=new PictureLineInfo();
                pictureLineInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                pictureLineInfo.setContent(cursor.getString(cursor.getColumnIndex("content")));
                pictureLineInfo.setUri(cursor.getString(cursor.getColumnIndex("uri")));
                pictureLineInfo.setDate(cursor.getString(cursor.getColumnIndex("date")));
                pictureLineInfo.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                pictureLineInfos.add(pictureLineInfo);
            }
        }

        return pictureLineInfos;
    }


}
