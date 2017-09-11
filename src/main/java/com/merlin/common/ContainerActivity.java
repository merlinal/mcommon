package com.merlin.common;

import android.content.Intent;
import android.os.Bundle;

import com.merlin.core.at.MustFragment;
import com.merlin.core.util.LogUtil;
import com.merlin.core.util.Util;
import com.merlin.view.bar.MBarView;
import com.merlin.view.bar.model.Bar;

/**
 * Created by ncm on 2017/5/27.
 */

public class ContainerActivity extends AbstractActivity {

    public static void start(AbstractFragment srcFragment, @MustFragment Class<?> cls, Bundle bundle, int requestCode) {
        Intent it = new Intent(srcFragment.getContext(), ContainerActivity.class);
        it.putExtra("fragmentName", cls.getName());
        if (bundle != null) {
            it.putExtras(bundle);
        }
        srcFragment.startActivityForResult(it, requestCode);
    }

    public static void start(AbstractActivity activity, @MustFragment Class<?> cls, Bundle bundle, int requestCode) {
        Intent it = new Intent(activity, ContainerActivity.class);
        it.putExtra("fragmentName", cls.getName());
        if (bundle != null) {
            it.putExtras(bundle);
        }
        activity.startActivityForResult(it, requestCode);
    }

    public static void startNoAnim(AbstractFragment srcFragment, @MustFragment Class<?> cls, Bundle bundle, int requestCode) {
        Intent it = new Intent(srcFragment.getContext(), ContainerActivity.class);
        it.putExtra("fragmentName", cls.getName());
        if (bundle != null) {
            it.putExtras(bundle);
        }
        it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        srcFragment.startActivityForResult(it, requestCode);
        srcFragment.getActivity().overridePendingTransition(0, 0);
    }

    public static void startNoAnim(AbstractActivity activity, @MustFragment Class<?> cls, Bundle bundle, int requestCode) {
        Intent it = new Intent(activity, ContainerActivity.class);
        it.putExtra("fragmentName", cls.getName());
        if (bundle != null) {
            it.putExtras(bundle);
        }
        it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivityForResult(it, requestCode);
        activity.overridePendingTransition(0, 0);
    }

    private AbstractFragment fragment;
    private Bar bar;
    private MBarView mBarView;

    @Override
    public void initData() {
        super.initData();
        bar = new Bar.Builder().setActivity(this).build();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.container;
    }

    @Override
    public void initView() {
        super.initView();

        Intent it = getIntent();
        fragment = Util.loadClass(it.getStringExtra("fragmentName"));
        if (fragment == null) {
            LogUtil.e("not found this fragment -- " + it.getStringExtra("fragmentName"));
            finish();
        } else {
            if (it.getExtras() != null) {
                fragment.setArguments(it.getExtras());
            }

            mBarView = Util.view(this, R.id.mBarView);
            mBarView.apply(bar = new Bar.Builder().setActivity(this).build());

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_page, fragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {
        fragment.onBackPressed();
    }

    protected MBarView bar() {
        return mBarView;
    }

}