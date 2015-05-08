package com.example.db.tline.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db.tline.R;
import com.example.db.tline.view.RevealLayout;
import com.example.db.tline.view.materialedittext.MaterialEditText;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AppLockFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppLockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppLockFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentTransaction fragmentTransaction;

    public RevealLayout mRevealLayout;
    public boolean mIsAnimationSlowDown = false;
    public boolean mIsBaseOnTouchLocation = false;

    public Button btn_zero,btn_one,btn_two,btn_three,btn_four,btn_five,btn_six,btn_seven,btn_eight,btn_nine;
    public MaterialEditText materialEditText;
    public Button mTick,mDelete;

    public String psd="";


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppLockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppLockFragment newInstance(String param1, String param2) {
        AppLockFragment fragment = new AppLockFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AppLockFragment() {
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
        View rootView=inflater.inflate(R.layout.fragment_lock, container, false);

        mRevealLayout = (RevealLayout) rootView.findViewById(R.id.reveal_layout);

        fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.activity_up_move_in,R.anim.abc_fade_out);

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

        initWidget(rootView);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_zero:
                if (materialEditText.getText().length()==0){
                    materialEditText.setText("*");
                    psd=psd+"0";
                }else if (materialEditText.length()!=0&&materialEditText.length()==1){
                    materialEditText.setText("**");
                    psd=psd+"0";
                }else if (materialEditText.length()!=0&&materialEditText.length()==2){
                    materialEditText.setText("***");
                    psd=psd+'0';
                }else if (materialEditText.length()!=0&&materialEditText.length()==3){
                    materialEditText.setText("****");
                    psd=psd+"0";
                }else {
                    Toast.makeText(getActivity(),"已经输入四位密码了!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_one:
                if (materialEditText.getText().length()==0){
                    materialEditText.setText("*");
                    psd=psd+"1";
                }else if (materialEditText.length()!=0&&materialEditText.length()==1){
                    materialEditText.setText("**");
                    psd=psd+"1";
                }else if (materialEditText.length()!=0&&materialEditText.length()==2){
                    materialEditText.setText("***");
                    psd=psd+'1';
                }else if (materialEditText.length()!=0&&materialEditText.length()==3){
                    materialEditText.setText("****");
                    psd=psd+"1";
                }else {
                    Toast.makeText(getActivity(),"已经输入四位密码了!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_two:
                if (materialEditText.getText().length()==0){
                    materialEditText.setText("*");
                    psd=psd+"2";
                }else if (materialEditText.length()!=0&&materialEditText.length()==1){
                    materialEditText.setText("**");
                    psd=psd+"2";
                }else if (materialEditText.length()!=0&&materialEditText.length()==2){
                    materialEditText.setText("***");
                    psd=psd+'2';
                }else if (materialEditText.length()!=0&&materialEditText.length()==3){
                    materialEditText.setText("****");
                    psd=psd+"2";
                }else {
                    Toast.makeText(getActivity(),"已经输入四位密码了!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_three:
                if (materialEditText.getText().length()==0){
                    materialEditText.setText("*");
                    psd=psd+"3";
                }else if (materialEditText.length()!=0&&materialEditText.length()==1){
                    materialEditText.setText("**");
                    psd=psd+"3";
                }else if (materialEditText.length()!=0&&materialEditText.length()==2){
                    materialEditText.setText("***");
                    psd=psd+'3';
                }else if (materialEditText.length()!=0&&materialEditText.length()==3){
                    materialEditText.setText("****");
                    psd=psd+"3";
                }else {
                    Toast.makeText(getActivity(),"已经输入四位密码了!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_four:
                if (materialEditText.getText().length()==0){
                    materialEditText.setText("*");
                    psd=psd+"4";
                }else if (materialEditText.length()!=0&&materialEditText.length()==1){
                    materialEditText.setText("**");
                    psd=psd+"4";
                }else if (materialEditText.length()!=0&&materialEditText.length()==2){
                    materialEditText.setText("***");
                    psd=psd+'4';
                }else if (materialEditText.length()!=0&&materialEditText.length()==3){
                    materialEditText.setText("****");
                    psd=psd+"4";
                }else {
                    Toast.makeText(getActivity(),"已经输入四位密码了!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_five:
                if (materialEditText.getText().length()==0){
                    materialEditText.setText("*");
                    psd=psd+"5";
                }else if (materialEditText.length()!=0&&materialEditText.length()==1){
                    materialEditText.setText("**");
                    psd=psd+"5";
                }else if (materialEditText.length()!=0&&materialEditText.length()==2){
                    materialEditText.setText("***");
                    psd=psd+'5';
                }else if (materialEditText.length()!=0&&materialEditText.length()==3){
                    materialEditText.setText("****");
                    psd=psd+"5";
                }else {
                    Toast.makeText(getActivity(),"已经输入四位密码了!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_six:
                if (materialEditText.getText().length()==0){
                    materialEditText.setText("*");
                    psd=psd+"6";
                }else if (materialEditText.length()!=0&&materialEditText.length()==1){
                    materialEditText.setText("**");
                    psd=psd+"6";
                }else if (materialEditText.length()!=0&&materialEditText.length()==2){
                    materialEditText.setText("***");
                    psd=psd+'6';
                }else if (materialEditText.length()!=0&&materialEditText.length()==3){
                    materialEditText.setText("****");
                    psd=psd+"6";
                }else {
                    Toast.makeText(getActivity(),"已经输入四位密码了!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_seven:
                if (materialEditText.getText().length()==0){
                    materialEditText.setText("*");
                    psd=psd+"7";
                }else if (materialEditText.length()!=0&&materialEditText.length()==1){
                    materialEditText.setText("**");
                    psd=psd+"7";
                }else if (materialEditText.length()!=0&&materialEditText.length()==2){
                    materialEditText.setText("***");
                    psd=psd+'7';
                }else if (materialEditText.length()!=0&&materialEditText.length()==3){
                    materialEditText.setText("****");
                    psd=psd+"7";
                }else {
                    Toast.makeText(getActivity(),"已经输入四位密码了!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_eight:
                if (materialEditText.getText().length()==0){
                    materialEditText.setText("*");
                    psd=psd+"8";
                }else if (materialEditText.length()!=0&&materialEditText.length()==1){
                    materialEditText.setText("**");
                    psd=psd+"8";
                }else if (materialEditText.length()!=0&&materialEditText.length()==2){
                    materialEditText.setText("***");
                    psd=psd+'8';
                }else if (materialEditText.length()!=0&&materialEditText.length()==3){
                    materialEditText.setText("****");
                    psd=psd+"8";
                }else {
                    Toast.makeText(getActivity(),"已经输入四位密码了!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_nine:
                if (materialEditText.getText().length()==0){
                    materialEditText.setText("*");
                    psd=psd+"9";
                }else if (materialEditText.length()!=0&&materialEditText.length()==1){
                    materialEditText.setText("**");
                    psd=psd+"9";
                }else if (materialEditText.length()!=0&&materialEditText.length()==2){
                    materialEditText.setText("***");
                    psd=psd+'9';
                }else if (materialEditText.length()!=0&&materialEditText.length()==3){
                    materialEditText.setText("****");
                    psd=psd+"9";
                }else {
                    Toast.makeText(getActivity(),"已经输入四位密码了!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_ok:
                if (materialEditText.length()!=0&&materialEditText.length()==4){
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.db.tline", Context.MODE_PRIVATE); //私有数据
                    Log.v("jb",psd);
                    Log.v("mb",sharedPreferences.getString("password",""));
                    if (sharedPreferences.getString("password","").equals(psd)){
                        fragmentTransaction.replace(R.id.container, new HomeFragment()).commit();
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putBoolean("FLAG", false);
                        editor.commit();//提交修改
                    }else {
                        Toast.makeText(getActivity(),"输入密码错误!",Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.btn_delete:
                materialEditText.setText("");
                psd="";
                break;
        }
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

    public void initWidget(View view){

        btn_zero=(Button)view.findViewById(R.id.btn_zero);
        btn_one=(Button)view.findViewById(R.id.btn_one);
        btn_two=(Button)view.findViewById(R.id.btn_two);
        btn_three=(Button)view.findViewById(R.id.btn_three);
        btn_four=(Button)view.findViewById(R.id.btn_four);
        btn_five=(Button)view.findViewById(R.id.btn_five);
        btn_six=(Button)view.findViewById(R.id.btn_six);
        btn_seven=(Button)view.findViewById(R.id.btn_seven);
        btn_eight=(Button)view.findViewById(R.id.btn_eight);
        btn_nine=(Button)view.findViewById(R.id.btn_nine);

        mTick=(Button)view.findViewById(R.id.btn_ok);
        mDelete=(Button)view.findViewById(R.id.btn_delete);

        materialEditText=(MaterialEditText)view.findViewById(R.id.psd);

        btn_zero.setOnClickListener(this);
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_seven.setOnClickListener(this);
        btn_eight.setOnClickListener(this);
        btn_nine.setOnClickListener(this);

        mTick.setOnClickListener(this);
        mDelete.setOnClickListener(this);
    }

}
