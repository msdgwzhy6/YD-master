package com.bzh.dytt.main;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzh.common.utils.SharePreferenceUtil;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.widget.XViewPager;
import com.bzh.dytt.setting_about.RxBus;
import com.bzh.dytt.setting_about.StatusBarEvent;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;


public class MainActivity extends BaseActivity
        implements MainIView {

    MainPresenter mainA;

    ActionBarDrawerToggle toggle;

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_coord)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.main_viewPager)
    XViewPager container;

    public Subscription rxSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        /*iv_head = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_head);
        iv_header_view_background = (ImageView) navigationView.getHeaderView(0).findViewById(R.id
                .iv_header_view_background);*/
        //侧边栏头布局信息设置
        View headerLayout = navigationView.getHeaderView(0);
        LinearLayout llImage = (LinearLayout) headerLayout.findViewById(R.id.side_image);
        TextView imageDescription = (TextView) headerLayout.findViewById(R.id.image_description);

        toolbar.setTitle("一点");
        setSupportActionBar(toolbar);

        //设置toolbar颜色
        setToolBar(null, toolbar, true, false, drawer);

        if (new File(getFilesDir().getPath() + "/bg.jpg").exists()) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), getFilesDir().getPath() + "/bg.jpg");
            llImage.setBackground(bitmapDrawable);
            //shared存储的文件名：SharePreferenceUtil.SHARED_PREFERENCE_NAME
            imageDescription.setText(getSharedPreferences(SharePreferenceUtil.SHARED_PREFERENCE_NAME, Context
                    .MODE_PRIVATE).
                    getString(SharePreferenceUtil.IMAGE_DESCRIPTION, "我的愿望，就是希望你的愿望里，也有我"));
        }


        //Navigation的item图标不显示原始颜色，此为解决办法。
        //item图标设置统一颜色: app:itemIconTint="@color/blue" 
        navigationView.setItemIconTintList(null);

        mainA = new MainPresenter(this, this);
        mainA.onCreate(savedInstanceState);

        rxSubscription = RxBus.getDefault().toObservable(StatusBarEvent.class)
                .subscribe(new Action1<StatusBarEvent>() {
                    @Override
                    public void call(StatusBarEvent statusBarEvent) {
                        recreate();
                    }
                });
    }

    @Override
    public void setHeaderViewBackground(String url) {

       /* if (iv_header_view_background != null) {
            //图片加载技术Glide，Glide可以加载GIF动态图，而Picasso不能。
            Glide.with(this)
                    .load(url)
                    .asBitmap()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(new BitmapImageViewTarget(iv_header_view_background) {

                        @Override
                        protected void setResource(Bitmap resource) {

                            int scaleRatio = 10;
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(resource,
                                    resource.getWidth() / scaleRatio,
                                    resource.getHeight() / scaleRatio,
                                    false);
                            Bitmap blurBitmap = FastBlurUtil.doBlur(scaledBitmap, 8, true);
                            iv_header_view_background.setImageBitmap(blurBitmap);
                        }
                    });
        }*/
    }

    @Override
    public void setHeadView(String url) {

       /* if (iv_head != null)
            Glide.with(this)
                    .load(url)
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(new BitmapImageViewTarget(iv_head) {

                        @Override
                        protected void setResource(Bitmap resource) {

                            iv_head.setImageBitmap(resource);
                        }
                    });*/
    }

    @Override
    protected int getContentViewResId() {

        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - mPreTime) > 2000) {
                Snackbar.make(toolbar, "再按一次退出", Snackbar.LENGTH_SHORT).show();
                mPreTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    private long mPreTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//按下菜单键
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initToolbar(String title) {

        
    }

    @Override
    public void setTitle(String title) {

        toolbar.setTitle(title);
    }

    @Override
    public void initDrawerToggle() {

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void initContainer(PagerAdapter pagerAdapter, int limit) {

        container.setOffscreenPageLimit(limit);
        container.setAdapter(pagerAdapter);
    }

    @Override
    public void setCurrentItem(int item) {

        container.setCurrentItem(item, false);
    }

    @Override
    public void closeDrawer() {

        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void setNavigationItemSelectedListener(MainPresenter mainA) {

        navigationView.setNavigationItemSelectedListener(mainA);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mainA.onDestroy();
    }
}
