package com.example.db.qrtools.Qr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.db.qrtools.R;
import com.example.db.qrtools.Utils.Utils;

import at.markushi.ui.CircleButton;
import me.drakeet.materialdialog.MaterialDialog;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelfDefineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelfDefineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public  class SelfDefineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //定义界面上的一些控件
    private FancyButton btn_reset,btn_product;
    private EditText selfdefine;
    private ImageView preview;
    public LinearLayout commit;
    public CircleButton edit,save,share;
    public String content;
    public Bitmap bitmap;
    public View toast;
    public Toast mToast;
    public TextView textView;
    public EditText editText;
    public String fileName=null;
    public View tempView;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelfDefine.
     */
    // TODO: Rename and change types and number of parameters
    public static SelfDefineFragment newInstance(String param1, String param2) {
        SelfDefineFragment fragment = new SelfDefineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SelfDefineFragment() {
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView=inflater.inflate(R.layout.fragment_self_define,null);

        toast=Utils.selfDefineToast(getActivity());
        mToast=new Toast(getActivity());
        mToast.setView(toast);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER_HORIZONTAL,0,100);
        textView=(TextView)toast.findViewById(R.id.toast);
        textView.setTextColor(Color.WHITE);


        btn_reset=(FancyButton)RootView.findViewById(R.id.btn_reset);
        btn_product=(FancyButton)RootView.findViewById(R.id.btn_product);
        selfdefine=(EditText)RootView.findViewById(R.id.selfdefine);
        preview=(ImageView)RootView.findViewById(R.id.preview);
        commit=(LinearLayout)RootView.findViewById(R.id.commit);
        edit=(CircleButton)RootView.findViewById(R.id.edit);
        save=(CircleButton)RootView.findViewById(R.id.save);
        share=(CircleButton)RootView.findViewById(R.id.share);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content=selfdefine.getText().toString();
                if (content!=null&&Utils.createQRImage(content)!=null){
                    Utils.deleteQRImage(fileName,Utils.SELF_DEFINE);
                    textView.setText("删除成功!");
                    mToast.show();

                    Intent intent = new Intent();
                    intent.setAction("db.notify.box");
                    intent.putExtra("command", "refresh");
                    getActivity().sendBroadcast(intent);

                }else {
                    textView.setText("输入的内容不能为空!");
                    mToast.show();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final MaterialDialog materialDialog=new MaterialDialog(getActivity());
                tempView=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_item,null);
                editText=(EditText)tempView.findViewById(R.id.filename);

                materialDialog.setContentView(tempView);
                materialDialog.setTitle("Save");
                materialDialog.setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        content = selfdefine.getText().toString();
                        fileName=editText.getText().toString();
                        if (content != null && Utils.createQRImage(content) != null) {
                            bitmap = Utils.createQRImage(content);
                            Utils.saveQRImage(fileName, bitmap, Utils.SELF_DEFINE);
                            textView.setText("保存成功!");
                            mToast.show();

                            Intent intent = new Intent();
                            intent.setAction("db.notify.box");
                            intent.putExtra("command", "refresh");
                            getActivity().sendBroadcast(intent);

                        } else {
                            textView.setText("输入的内容不能为空!");
                            mToast.show();
                        }
                        materialDialog.dismiss();
                    }
                });
                materialDialog.setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        materialDialog.dismiss();
                    }
                });
                materialDialog.show();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content=selfdefine.getText().toString();
                if (content!=null&&Utils.createQRImage(content)!=null) {
                    if (Utils.isSaved(fileName,Utils.SELF_DEFINE)) {
                        Utils.shareQRImage(getActivity(), fileName, Utils.SELF_DEFINE);
                    }else {
                        textView.setText("请先保存!");
                        mToast.show();
                    }
                }else {
                    textView.setText("输入的内容不能为空!");
                    mToast.show();
                }
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selfdefine.setText("");
                preview.setImageBitmap(null);
            }
        });
        btn_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content=selfdefine.getText().toString();
                if (content!=null&&Utils.createQRImage(content)!=null) {
                    bitmap = Utils.createQRImage(content);
                    preview.setImageBitmap(bitmap);
                    commit.setVisibility(View.VISIBLE);
                }else {
                    textView.setText("输入的内容不能为空!");
                    mToast.show();
                }
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
