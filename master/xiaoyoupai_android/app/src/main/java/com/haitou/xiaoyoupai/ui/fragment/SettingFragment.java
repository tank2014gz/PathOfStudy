package com.haitou.xiaoyoupai.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haitou.xiaoyoupai.DB.sp.ConfigurationSp;
import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.config.SysConstant;
import com.haitou.xiaoyoupai.imservice.manager.IMLoginManager;
import com.haitou.xiaoyoupai.imservice.service.IMService;
import com.haitou.xiaoyoupai.ui.activity.AboutSetting;
import com.haitou.xiaoyoupai.ui.activity.AccountSetting;
import com.haitou.xiaoyoupai.ui.activity.NoticeSetting;
import com.haitou.xiaoyoupai.ui.helper.CheckboxConfigHelper;
import com.haitou.xiaoyoupai.ui.base.TTBaseFragment;
import com.haitou.xiaoyoupai.imservice.support.IMServiceConnector;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 设置页面
 */
public class SettingFragment extends TTBaseFragment{
	private View curView = null;
//	private CheckBox notificationNoDisturbCheckBox;
//	private CheckBox notificationGotSoundCheckBox;
//	private CheckBox notificationGotVibrationCheckBox;

	private TextView mNotice,mAccount,mAbout,mHelp,mCheck,mExit;

	CheckboxConfigHelper checkBoxConfiger = new CheckboxConfigHelper();


    private IMServiceConnector imServiceConnector = new IMServiceConnector(){
        @Override
        public void onIMServiceConnected() {
            logger.d("config#onIMServiceConnected");
            IMService imService = imServiceConnector.getIMService();
            if (imService != null) {
                checkBoxConfiger.init(imService.getConfigSp());
                initOptions();
            }
        }

        @Override
        public void onServiceDisconnected() {
        }
    };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		imServiceConnector.connect(this.getActivity());
		if (null != curView) {
			((ViewGroup) curView.getParent()).removeView(curView);
			return curView;
		}
		curView = inflater.inflate(R.layout.tt_fragment_setting, topContentView);
		initRes();

		mNotice = (TextView)curView.findViewById(R.id.notice);
		mAccount = (TextView)curView.findViewById(R.id.account_psd);
		mAbout = (TextView)curView.findViewById(R.id.about);
		mHelp = (TextView)curView.findViewById(R.id.help_feedback);
		mCheck = (TextView)curView.findViewById(R.id.check_update);
		mExit = (TextView)curView.findViewById(R.id.exit);

		mExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
				LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View dialog_view = inflater.inflate(R.layout.tt_custom_dialog, null);
				final EditText editText = (EditText)dialog_view.findViewById(R.id.dialog_edit_content);
				editText.setVisibility(View.GONE);
				TextView textText = (TextView)dialog_view.findViewById(R.id.dialog_title);
				textText.setText(R.string.exit_teamtalk_tip);
				builder.setView(dialog_view);
				builder.setPositiveButton(getString(R.string.tt_ok), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						IMLoginManager.instance().setKickout(false);
						IMLoginManager.instance().logOut();
						getActivity().finish();
						dialog.dismiss();
					}
				});

				builder.setNegativeButton(getString(R.string.tt_cancel), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});
				builder.show();
			}
		});

		mCheck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(),"已经是最新版本惹~",Toast.LENGTH_SHORT).show();
			}
		});

		mNotice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), NoticeSetting.class));
			}
		});

		mAccount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AccountSetting.class));

			}
		});

		mAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AboutSetting.class));

			}
		});

		mHelp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		return curView;
	}

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        imServiceConnector.disconnect(getActivity());
    }

    private void initOptions() {
//		notificationNoDisturbCheckBox = (CheckBox) curView.findViewById(R.id.NotificationNoDisturbCheckbox);
//		notificationGotSoundCheckBox = (CheckBox) curView.findViewById(R.id.notifyGotSoundCheckBox);
//		notificationGotVibrationCheckBox = (CheckBox) curView.findViewById(R.id.notifyGotVibrationCheckBox);
//		saveTrafficModeCheckBox = (CheckBox) curView.findViewById(R.id.saveTrafficCheckBox);为修改
		
//		checkBoxConfiger.initCheckBox(notificationNoDisturbCheckBox, SysConstant.SETTING_GLOBAL, ConfigurationSp.CfgDimension.NOTIFICATION );
//		checkBoxConfiger.initCheckBox(notificationGotSoundCheckBox, SysConstant.SETTING_GLOBAL , ConfigurationSp.CfgDimension.SOUND);
//		checkBoxConfiger.initCheckBox(notificationGotVibrationCheckBox, SysConstant.SETTING_GLOBAL,ConfigurationSp.CfgDimension.VIBRATION );
//		checkBoxConfiger.initCheckBox(saveTrafficModeCheckBox, ConfigDefs.SETTING_GLOBAL, ConfigDefs.KEY_SAVE_TRAFFIC_MODE, ConfigDefs.DEF_VALUE_SAVE_TRAFFIC_MODE);
	}

	@Override
	public void onResume() {

		super.onResume();
	}

	/**
	 * @Description 初始化资源
	 */
	private void initRes() {
		// 设置标题栏
		setTopTitle(getActivity().getString(R.string.setting_page_name));
		setTopLeftButton(R.drawable.back_pop);
		topLeftContainerLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getActivity().finish();
			}
		});
		setTopLeftText(getResources().getString(R.string.top_left_back));
	}

	@Override
	protected void initHandler() {
	}

}
