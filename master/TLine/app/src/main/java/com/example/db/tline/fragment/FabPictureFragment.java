package com.example.db.tline.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.db.tline.R;
import com.example.db.tline.database.PLineSQLiDataBaseHelper;
import com.example.db.tline.floatingactionbutton.FloatingActionButton;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.RevealLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FabPictureFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FabPictureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FabPictureFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    public Button back;
    public FloatingActionButton save;
    public EditText mPictureTitle,mPictureContent;
    public ImageView mImageView;
    public String uri=null;
    public String pTitle,pDescription;
    public DisplayImageOptions options;
    public ImageLoader imageLoader;
    public FragmentTransaction fragmentTransaction;

    private RevealLayout mRevealLayout;
    private boolean mIsAnimationSlowDown = false;
    private boolean mIsBaseOnTouchLocation = false;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FabPictureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FabPictureFragment newInstance(String param1, String param2) {
        FabPictureFragment fragment = new FabPictureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FabPictureFragment() {
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
        View rootView=inflater.inflate(R.layout.fragment_fab_picture,container,false);

        mRevealLayout = (RevealLayout) rootView.findViewById(R.id.reveal_layout);

        imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.login)
                .showImageForEmptyUri(R.drawable.login)
                .showImageOnFail(R.drawable.login)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        final Bundle bundle=this.getArguments();
        uri=bundle.getString("uri");


        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.activity_up_move_in,R.anim.abc_fade_out);

        back=(Button)rootView.findViewById(R.id.back);
        save=(FloatingActionButton)rootView.findViewById(R.id.save);

        mPictureContent=(EditText)rootView.findViewById(R.id.picture_edit_content);
        mPictureTitle=(EditText)rootView.findViewById(R.id.picture_edit_title);
        mImageView=(ImageView)rootView.findViewById(R.id.preview);

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.container,new HomeFragment()).commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pTitle=mPictureTitle.getText().toString().trim();
                pDescription=mPictureContent.getText().toString().trim();
                if (pTitle.length()!=0&&pDescription.length()!=0&&uri.length()!=0){
                    PLineSQLiDataBaseHelper pLineSQLiDataBaseHelper=new PLineSQLiDataBaseHelper(getActivity());
                    SQLiteDatabase sqLiteDatabase=pLineSQLiDataBaseHelper.getWritableDatabase();
                    ContentValues contentValues=new ContentValues();

                    contentValues.put("title",pTitle);
                    contentValues.put("content",pDescription);
                    contentValues.put("uri",uri);
                    contentValues.put("date",AppConstant.getCurrentTime());
                    sqLiteDatabase.insert("pictureline",null,contentValues);
                    sqLiteDatabase.close();

                    HomeFragment homeFragment=new HomeFragment();
                    Bundle bundle0=new Bundle();
                    bundle.putString("command","picture");
                    bundle.putString("pText",pTitle);
                    bundle.putString("pContent",pDescription);
                    bundle.putString("pItem",String.valueOf(0));
                    homeFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.container,homeFragment).commit();

                }
            }
        });
        if (uri.length()!=0){
            imageLoader.displayImage(uri, mImageView, options);

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

}
