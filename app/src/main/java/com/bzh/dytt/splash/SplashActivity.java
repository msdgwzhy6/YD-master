package com.bzh.dytt.splash;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.main.MainActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements SplashView {

    @BindView(R.id.splash_image)
    ImageView mSplashImage;

    @BindView(R.id.splash_version_name)
    TextView mVersionName;

    @BindView(R.id.splash_copyright)
    TextView mCopyright;

    private SplashPresenterImpl mSplashPresenter = null;

    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    private ObjectAnimator mAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getContentViewResId() {

        return R.layout.activity_splash;
    }

    protected void init() {

        mSplashPresenter = new SplashPresenterImpl(this, this);
        mSplashPresenter.initialized();

        //Activity动画效果
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public void animateBackgroundImage() {

        /*final Animation anima1=animation;
        
        new Thread(new Runnable() {

            @Override
            public void run() {

                mSplashImage.startAnimation(anima1);

            }
        }).start();*/

        mAnim = ObjectAnimator
                .ofFloat(mSplashImage, "zhy", 1.0F,  1.2F)
                .setDuration(2800);
        
        
        mAnim.start();
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float cVal = (Float) animation.getAnimatedValue();
                mSplashImage.setAlpha(1.8F-cVal);
                mSplashImage.setScaleX(cVal);
                mSplashImage.setScaleY(cVal);
            }
        });
    }
    
    @Override
    public void addAnimListener(SplashPresenterImpl presenter){
        mAnim.addListener(presenter);
    }

    @Override
    public void initializeViews(String versionName, String copyright, int backgroundResId) {

        mCopyright.setText(copyright);
        mVersionName.setText(versionName);
        mSplashImage.setImageResource(backgroundResId);
    }


    @Override
    public void navigateToHomePage() {

        readyGoThenKill(MainActivity.class);
    }

    protected void readyGoThenKill(Class<?> clazz) {

        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
