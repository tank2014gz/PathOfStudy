package com.haitou.xiaoyoupai.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.config.IntentConstant;
import com.haitou.xiaoyoupai.imservice.event.LoginEvent;
import com.haitou.xiaoyoupai.imservice.event.UnreadEvent;
import com.haitou.xiaoyoupai.imservice.service.IMService;
import com.haitou.xiaoyoupai.imservice.support.IMServiceConnector;
import com.haitou.xiaoyoupai.ui.fragment.ChatFragment;
import com.haitou.xiaoyoupai.ui.fragment.ContactFragment;
import com.haitou.xiaoyoupai.ui.fragment.HomeFragment;
import com.haitou.xiaoyoupai.ui.widget.NaviTabButton;
import com.haitou.xiaoyoupai.utils.Logger;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class MainActivity extends FragmentActivity implements HomeFragment.OnFragmentInteractionListener{

	private Fragment[] mFragments;
	private NaviTabButton[] mTabButtons;
	private Logger logger = Logger.getLogger(MainActivity.class);
    private IMService imService;

	private IMServiceConnector imServiceConnector = new IMServiceConnector(){
        @Override
        public void onIMServiceConnected() {
            imService = imServiceConnector.getIMService();

			ArrayList<Integer> ids = new ArrayList<Integer>();
			ids.add(1);
			ids.add(2);
			ids.add(3);

			imService.getContactManager().reqGetDetaillUsers(ids);
        }

        @Override
        public void onServiceDisconnected() {
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		logger.d("MainActivity#savedInstanceState:%s", savedInstanceState);
		//todo eric when crash, this will be called, why?
		if (savedInstanceState != null) {
			logger.w("MainActivity#crashed and restarted, just exit");
			jumpToLoginPage();
			finish();
		}

        // 在这个地方加可能会有问题吧
        EventBus.getDefault().register(this);
		imServiceConnector.connect(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tt_activity_main);

		initTab();
		initFragment();
		setFragmentIndicator(0);


	}

	@Override
	public void onBackPressed() {
		//don't let it exit
		//super.onBackPressed();

		//nonRoot	If false then this only works if the activity is the root of a task; if true it will work for any activity in a task.
		//document http://developer.android.com/reference/android/app/Activity.html

		//moveTaskToBack(true);

		Intent i = new Intent(Intent.ACTION_MAIN);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addCategory(Intent.CATEGORY_HOME);
		startActivity(i);

	}


	private void initFragment() {
		mFragments = new Fragment[4];
		mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_internal);
		mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_chat);
		mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_contact);
		mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_my);
	}

	private void initTab() {
		mTabButtons = new NaviTabButton[4];

		mTabButtons[0] = (NaviTabButton) findViewById(R.id.tabbutton_internal);
		mTabButtons[1] = (NaviTabButton) findViewById(R.id.tabbutton_chat);
		mTabButtons[2] = (NaviTabButton) findViewById(R.id.tabbutton_contact);
		mTabButtons[3] = (NaviTabButton) findViewById(R.id.tabbutton_my);

		mTabButtons[0].setTitle(getString(R.string.main_chat));
		mTabButtons[0].setIndex(0);
		mTabButtons[0].setSelectedImage(getResources().getDrawable(R.drawable.activity_selected));
		mTabButtons[0].setUnselectedImage(getResources().getDrawable(R.drawable.activity));

		mTabButtons[1].setTitle(getString(R.string.main_contact));
		mTabButtons[1].setIndex(1);
		mTabButtons[1].setSelectedImage(getResources().getDrawable(R.drawable.conversation_selected));
		mTabButtons[1].setUnselectedImage(getResources().getDrawable(R.drawable.conversation));

		mTabButtons[2].setTitle(getString(R.string.main_innernet));
		mTabButtons[2].setIndex(2);
		mTabButtons[2].setSelectedImage(getResources().getDrawable(R.drawable.contact_selected));
		mTabButtons[2].setUnselectedImage(getResources().getDrawable(R.drawable.contact));

		mTabButtons[3].setTitle(getString(R.string.main_me_tab));
		mTabButtons[3].setIndex(3);
		mTabButtons[3].setSelectedImage(getResources().getDrawable(R.drawable.myprofile_selected));
		mTabButtons[3].setUnselectedImage(getResources().getDrawable(R.drawable.myprofile));
	}

	public void setFragmentIndicator(int which) {
		getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();

		mTabButtons[0].setSelectedButton(false);
		mTabButtons[1].setSelectedButton(false);
		mTabButtons[2].setSelectedButton(false);
		mTabButtons[3].setSelectedButton(false);

		mTabButtons[which].setSelectedButton(true);
	}

	public void setUnreadMessageCnt(int unreadCnt) {
		mTabButtons[0].setUnreadNotify(unreadCnt);
	}



    /**双击事件*/
	public void chatDoubleListener() {
        setFragmentIndicator(0);
        ((ChatFragment) mFragments[0]).scrollToUnreadPosition();
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		handleLocateDepratment(intent);
	}


	@Override
	protected void onResume() {
        super.onResume();
	}

	private void handleLocateDepratment(Intent intent) {
		int departmentIdToLocate= intent.getIntExtra(IntentConstant.KEY_LOCATE_DEPARTMENT,-1);
		if (departmentIdToLocate == -1) {
			return;
		}

		logger.d("department#got department to locate id:%d", departmentIdToLocate);
		setFragmentIndicator(1);
		ContactFragment fragment = (ContactFragment) mFragments[1];
		if (fragment == null) {
			logger.e("department#fragment is null");
			return;
		}
		fragment.locateDepartment(departmentIdToLocate);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		logger.d("mainactivity#onDestroy");
		EventBus.getDefault().unregister(this);
		imServiceConnector.disconnect(this);
        super.onDestroy();
	}



    public void onEventMainThread(UnreadEvent event){
        switch (event.event){
            case SESSION_READED_UNREAD_MSG:
            case UNREAD_MSG_LIST_OK:
            case UNREAD_MSG_RECEIVED:
                showUnreadMessageCount();
                break;
        }
    }

    private void showUnreadMessageCount() {
        //todo eric when to
        if(imService!=null)
        {
            int unreadNum = imService.getUnReadMsgManager().getTotalUnreadCount();
            mTabButtons[0].setUnreadNotify(unreadNum);
        }

    }

	public void onEventMainThread(LoginEvent event){
        switch (event){
            case LOGIN_OUT:
                handleOnLogout();
                break;
        }
    }

	private void handleOnLogout() {
		logger.d("mainactivity#login#handleOnLogout");
		finish();
		logger.d("mainactivity#login#kill self, and start login activity");
		jumpToLoginPage();

	}

	private void jumpToLoginPage() {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra(IntentConstant.KEY_LOGIN_NOT_AUTO, true);
		startActivity(intent);
	}

	@Override
	public void onFragmentInteraction(Uri uri) {

	}
}
