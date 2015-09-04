package com.haitou.xiaoyoupai.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.config.IntentConstant;
import com.haitou.xiaoyoupai.ui.activity.WebViewFragmentActivity;
import com.haitou.xiaoyoupai.ui.adapter.InternalAdapter;
import com.haitou.xiaoyoupai.ui.base.TTBaseFragment;

public class InternalFragment extends TTBaseFragment {
    private View curView = null;
    private ListView internalListView;
    private InternalAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null != curView) {
            ((ViewGroup) curView.getParent()).removeView(curView);
            return curView;
        }
        curView = inflater.inflate(R.layout.tt_fragment_internal,
                topContentView);

        initRes();
        mAdapter = new InternalAdapter(this.getActivity());
        internalListView.setAdapter(mAdapter);
        mAdapter.update();
        return curView;
    }

    private void initRes() {
        // 设置顶部标题栏
        setTopTitle(getActivity().getString(R.string.main_innernet));
        internalListView = (ListView)curView.findViewById(R.id.internalListView);
        internalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = mAdapter.getItem(i).getItemUrl();
                Intent intent=new Intent(InternalFragment.this.getActivity(),WebViewFragmentActivity.class);
                intent.putExtra(IntentConstant.WEBVIEW_URL, url);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initHandler() {
    }

}
