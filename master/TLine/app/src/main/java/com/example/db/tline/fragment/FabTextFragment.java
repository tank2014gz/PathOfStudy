package com.example.db.tline.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.db.tline.R;
import com.example.db.tline.beans.TextLineInfo;
import com.example.db.tline.database.TLineSQLiDataBaseHelper;
import com.example.db.tline.floatingactionbutton.FloatingActionButton;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.RevealLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FabTextFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FabTextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FabTextFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Button back;
    public FloatingActionButton save;
    public EditText mTextTitle,mTextContent;
    public FragmentTransaction fragmentTransaction;
    private OnFragmentInteractionListener mListener;
    public String mTitle,mCOntent;

    private RevealLayout mRevealLayout;
    private boolean mIsAnimationSlowDown = false;
    private boolean mIsBaseOnTouchLocation = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FabTextFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FabTextFragment newInstance(String param1, String param2) {
        FabTextFragment fragment = new FabTextFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FabTextFragment() {
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
        View rootView=inflater.inflate(R.layout.fragment_fab_text, container, false);

        mRevealLayout = (RevealLayout) rootView.findViewById(R.id.reveal_layout);



        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.activity_up_move_in,R.anim.abc_fade_out);

        back=(Button)rootView.findViewById(R.id.back);
        save=(FloatingActionButton)rootView.findViewById(R.id.save);
        mTextTitle=(EditText)rootView.findViewById(R.id.text_edit_title);
        mTextContent=(EditText)rootView.findViewById(R.id.text_edit_content);

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitle=mTextTitle.getText().toString().trim();
                mCOntent=mTextContent.getText().toString().trim();

                if (mTitle.length()!=0&&mCOntent.length()!=0){

                    TLineSQLiDataBaseHelper tLineSQLiDataBaseHelper=new TLineSQLiDataBaseHelper(getActivity());
                    SQLiteDatabase sqLiteDatabase=tLineSQLiDataBaseHelper.getWritableDatabase();
                    ContentValues contentValues=new ContentValues();

                        contentValues.put("title",mTitle);
                        contentValues.put("content",mCOntent);
                        contentValues.put("date", AppConstant.getCurrentTime());
                        sqLiteDatabase.insert("textline",null,contentValues);
                        sqLiteDatabase.close();

                        HomeFragment homeFragment = new HomeFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("command", "text");
                        bundle.putString("tText", mTitle);
                        bundle.putString("tContent", mCOntent);
                        bundle.putString("tItem", String.valueOf(1));
                        homeFragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.container, homeFragment).commit();


                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentTransaction.replace(R.id.container,new HomeFragment()).commit();

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
    private void initRevealLayout() {
        if (mIsBaseOnTouchLocation) {
            mRevealLayout.setOnClickListener(null);
            mRevealLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        Log.d("SingleChildActivity", "x: " + event.getX() + ", y: " + event.getY());
                        if (mRevealLayout.isContentShown()) {
                            if (mIsAnimationSlowDown) {
                                mRevealLayout.hide((int) event.getX(), (int) event.getY(), 2000);
                            } else {
                                mRevealLayout.hide((int) event.getX(), (int) event.getY());
                            }
                        } else {
                            if (mIsAnimationSlowDown) {
                                mRevealLayout.show((int) event.getX(), (int) event.getY(), 2000);
                            } else {
                                mRevealLayout.show((int) event.getX(), (int) event.getY());
                            }
                        }
                        return true;
                    }
                    return false;
                }
            });
        } else {
            mRevealLayout.setOnTouchListener(null);
            mRevealLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRevealLayout.isContentShown()) {
                        if (mIsAnimationSlowDown) {
                            mRevealLayout.hide(2000);
                        } else {
                            mRevealLayout.hide();
                        }
                    } else {
                        if (mIsAnimationSlowDown) {
                            mRevealLayout.show(2000);
                        } else {
                            mRevealLayout.show();
                        }
                    }
                }
            });
        }
    }
}
