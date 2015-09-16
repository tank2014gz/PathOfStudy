package com.haitou.xiaoyoupai.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.haitou.xiaoyoupai.DB.entity.UserEntity;
import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.imservice.event.UserInfoEvent;
import com.haitou.xiaoyoupai.imservice.service.IMService;
import com.haitou.xiaoyoupai.imservice.support.IMServiceConnector;
import com.haitou.xiaoyoupai.ui.activity.NNextActivity;
import com.haitou.xiaoyoupai.ui.widget.RichEditor;
import com.haitou.xiaoyoupai.ui.widget.materialedittext.MaterialEditText;

import de.greenrobot.event.EventBus;


public class NextFragment extends MainFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View curView = null;

    /*
    富文本编辑器
     */
    public RichEditor richEditor;

    private ImageButton undo,redo,camera,picture,link,format;

    public Button btn_next;

    public MaterialEditText editText_type;
    public ImageView imageView;

    public String type;

    public Bundle bundle;

    public UserEntity currentUser;
    public int currentUserId;

    private OnFragmentInteractionListener mListener;


    public static NextFragment newInstance(String param1, String param2) {
        NextFragment fragment = new NextFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NextFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private IMServiceConnector imServiceConnector = new IMServiceConnector(){
        @Override
        public void onServiceDisconnected() {}

        @Override
        public void onIMServiceConnected() {
            if (curView == null) {
                return;
            }
            IMService imService = imServiceConnector.getIMService();
            if (imService == null) {
                return;
            }
            if (!imService.getContactManager().isUserDataReady()) {
                logger.i("detail#contact data are not ready");
            } else {
                init(imService);
            }
        }
    };

    private void init(IMService imService) {

        if (imService == null) {
            return;
        }

        final UserEntity loginContact = imService.getLoginManager().getLoginInfo();
        if (loginContact == null) {
            return;
        }

        currentUserId = loginContact.getPeerId();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("peer_id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("peer_id",String.valueOf(currentUserId));
        editor.commit();


    }

    public void onEventMainThread(UserInfoEvent event){
        switch (event){
            case USER_INFO_OK:
                init(imServiceConnector.getIMService());
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        imServiceConnector.connect(getActivity());
        EventBus.getDefault().register(this);

        if (null != curView) {
            logger.d("curView is not null, remove it");
            ((ViewGroup) curView.getParent()).removeView(curView);
        }

        bundle = this.getArguments();

        curView = inflater.inflate(R.layout.fragment_next, topContentView);
        super.init(curView);
        initTitleView();

        btn_next = (Button)curView.findViewById(R.id.next);

        editText_type = (MaterialEditText)curView.findViewById(R.id.type);
        imageView = (ImageView)curView.findViewById(R.id.cover_img);

        richEditor = (RichEditor)curView.findViewById(R.id.editor);

        undo = (ImageButton)curView.findViewById(R.id.undo);
        redo = (ImageButton)curView.findViewById(R.id.redo);
        camera = (ImageButton)curView.findViewById(R.id.camera);
        picture = (ImageButton)curView.findViewById(R.id.picture);
        link = (ImageButton)curView.findViewById(R.id.link);
        format = (ImageButton)curView.findViewById(R.id.format);

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richEditor.undo();
            }
        });
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richEditor.redo();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                先将图片上传到服务器上去，然后获取服务器返回的url，在将url插入到html里面
                服务器地址 http://123.56.42.245:8700
                 */


                richEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
            }
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        format.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        richEditor.setEditorFontSize(16);
        richEditor.setTextBackgroundColor(getResources().getColor(R.color.default_light_grey_color));
        richEditor.setPlaceholder("最少100个汉字");

        topLeftContainerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        topRightTitleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NNextActivity.class));
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText_type!=null&&richEditor.getHtml()!=null
                        &&editText_type.length()!=0&&richEditor.getHtml().length()!=0){

                    bundle.putString("type",type);
                    bundle.putString("content",richEditor.getHtml());
                    bundle.putString("cover_img","http://img4.imgtn.bdimg.com/it/u=3046486516,3889326814&fm=21&gp=0.jpg");

                    Intent intent = new Intent(getActivity(), NNextActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }else {
                    Toast.makeText(getActivity(), "输入不能为空!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return curView;
    }

    private void initTitleView() {
        // 设置标题
        setTopRightText("下一步");
        setTopLeftText("基本信息");
        setTopLeftButton(R.drawable.back_pop);
    }

    @Override
    protected void initHandler() {

    }

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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
