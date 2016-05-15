package com.bzh.dytt.base.basic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import com.bzh.dytt.R;

import java.lang.reflect.Method;

/**
 * ========================================================== <br>
 * <b>版权</b>：　　　音悦台 版权所有(c) 2015 <br>
 * <b>作者</b>：　　　别志华 zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　15-12-15 <br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0 <br>
 * <b>修订历史</b>：　<br>
 * ========================================================== <br>
 */
public class FragmentContainerActivity extends BaseActivity {

    public static final String FRAGMENT_TAG = "fragment_container";
    public static final String KEY_CLASS_NAME = "className";
    public static final String KEY_ARGS = "args";
    private Fragment fragment;

    public static void launch(Activity activity,
                              Class<? extends Fragment> clazz,
                              FragmentArgs args) {
        Intent intent = new Intent(activity, FragmentContainerActivity.class);
        intent.putExtra(KEY_CLASS_NAME, clazz.getName());
        if (args != null) {
            intent.putExtra(KEY_ARGS, args);
        }
        activity.startActivity(intent);
    }

    /**
     * 从Fragment调用
     */
    public static void launchForResult(BaseFragment fragment,
                                       Class<? extends Fragment> clazz,
                                       FragmentArgs args,
                                       int requestCode) {
        if (fragment.getActivity() == null) {
            return;
        }
        Activity activity = fragment.getActivity();
        Intent intent = new Intent(activity, FragmentContainerActivity.class);
        intent.putExtra(KEY_CLASS_NAME, clazz.getName());
        if (args != null) {
            intent.putExtra(KEY_ARGS, args);
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 从Activity调用
     */
    public static void launchForResult(Activity from,
                                       Class<? extends Fragment> clazz,
                                       FragmentArgs args,
                                       int requestCode) {
        Intent intent = new Intent(from, FragmentContainerActivity.class);
        intent.putExtra(KEY_CLASS_NAME, clazz.getName());
        if (args != null) {
            intent.putExtra(KEY_ARGS, args);
        }
        from.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = getIntent().getStringExtra(KEY_CLASS_NAME);
        if (TextUtils.isEmpty(className)) {
            finish();
            return;
        }
        FragmentArgs values = (FragmentArgs) getIntent().getSerializableExtra(KEY_ARGS);
        if (savedInstanceState == null) {
            try {
                Class clazz = Class.forName(className);
                fragment = (Fragment) clazz.newInstance();

                // 设置参数给Fragment
                if (values != null) {
                    try {
                        Method method = clazz.getMethod("setArguments", new Class[]{Bundle.class});
                        method.invoke(fragment, FragmentArgs.transToBundle(values));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                finish();
                return;
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainer, fragment, FRAGMENT_TAG)
                        .commit();
            }
        }
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.comm_frag_container;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (null != fragment) {
            // 解决第三方登陆不能回调Fragment的onActiviytResult()方法的BUG
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (fragment != null) {
            fragment.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
