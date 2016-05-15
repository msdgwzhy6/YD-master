/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bzh.dytt.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.animation.Animation;

import com.bzh.common.utils.SharePreferenceUtil;
import com.bzh.data.image.ImageResponse;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 */
public class SplashPresenterImpl implements Presenter {

    private SharedPreferences sharedPreferences;

    private Context mContext = null;

    private SplashView mSplashView = null;

    private SplashInteractor mSplashInteractor = null;

    public SplashPresenterImpl(Context context, SplashView splashView) {

        if (null == splashView) {
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }

        mContext = context;
        mSplashView = splashView;
        mSplashInteractor = new SplashInteractorImpl();

        sharedPreferences = context.getSharedPreferences(SharePreferenceUtil.SHARED_PREFERENCE_NAME, Context
                .MODE_PRIVATE);
    }

    @Override
    public void initialized() {
//        mSplashView.initializeUmengConfig();
        mSplashView.initializeViews(mSplashInteractor.getVersionName(mContext),
                mSplashInteractor.getCopyright(mContext),
                mSplashInteractor.getBackgroundImageResID());

        DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.DATE_FIELD);
        String date = dateFormat.format(new Date());
        
        final String today=date;
        
        Animation animation = mSplashInteractor.getBackgroundImageAnimation(mContext);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                if (!sharedPreferences.getString(SharePreferenceUtil.IMAGE_GET_TIME, "").equals(today) && 
                        SharePreferenceUtil
                        .isChangeThemeAuto(mContext)) {

                   /* //判断权限
                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != 
                            PackageManager
                            .PERMISSION_GRANTED) {
                        new AlertDialog.Builder(mContext).setMessage(getString(R.string.request_storage_permission))
                                .setPositiveButton(R.string.common_i_know, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ActivityCompat.requestPermissions(SplashActivity.this, new String[] {Manifest.permission
                                                .READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
                                    }
                                }).setCancelable(false).show();
                    } */
                    
                    getBackground();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                mSplashView.navigateToHomePage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplashView.animateBackgroundImage(animation);
    }

    public void getBackground() {

        Repository.getInstance().getImage().subscribeOn(Schedulers.io()).
                map(new Func1<ImageResponse, Boolean>() {

                    @Override
                    public Boolean call(ImageResponse imageResponse) {

                        if (imageResponse.getData() != null && imageResponse.getData().getImages() != null) {
                            try {
                                Bitmap bitmap = BitmapFactory.decodeStream(new URL("http://wpstatic.zuimeia.com/" +
                                        imageResponse.getData().getImages().get(0).getImageUrl() +
                                        "?imageMogr/v2/auto-orient/thumbnail/480x320/quality/100").openConnection()
                                        .getInputStream());
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File
                                        (mContext.getFilesDir().getPath() + "/bg.jpg")));
                                Palette palette = Palette.from(bitmap).generate();
                                int color = 0x000000;
                                int vibrant = palette.getVibrantColor(color);
                                int vibrantDark = palette.getDarkVibrantColor(color);
                                if (vibrant == 0)
                                    vibrant = vibrantDark;
                                if (vibrant == 0)
                                    vibrant = getRandomPrimaryColor();
                                int muted = palette.getMutedColor(color);
                                int mutedDark = palette.getDarkMutedColor(color);
                                if (muted == 0)
                                    muted = mutedDark;
                                if (muted == 0)
                                    muted = ContextCompat.getColor(mContext, R.color.colorAccent);
                                DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.DATE_FIELD);
                                sharedPreferences.edit()
                                        //侧边栏图片描述
                                        .putString(SharePreferenceUtil.IMAGE_DESCRIPTION, imageResponse.getData()
                                                .getImages().get(0).getDescription())
                                        .putInt(SharePreferenceUtil.VIBRANT, vibrant)
                                        .putInt(SharePreferenceUtil.MUTED, muted)
                                        //将推按的日期啊什么的存储起来
                                        .putString(SharePreferenceUtil.IMAGE_GET_TIME, dateFormat.format(new Date()))
                                        .apply();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

//                        mSplashView.navigateToHomePage();
                    }

                    @Override
                    public void onNext(Boolean imageReponse) {

//                        mSplashView.navigateToHomePage();

                    }
                });
    }

    private int getRandomPrimaryColor() {

        int[] primaryInt = new int[] {
                R.color.colorBlueGreyPrimary,
                R.color.colorBluePrimary,
                R.color.colorBrownPrimary,
                R.color.colorCyanPrimary,
                R.color.colorDeepOrangePrimary,
                R.color.colorDeepPurplePrimary,
                R.color.colorGreenPrimary,
                R.color.colorIndigoPrimary,
                R.color.colorLightGreenPrimary,
                R.color.colorLimePrimary,
                R.color.colorRedPrimary,
                R.color.colorPinkPrimary,
                R.color.colorPrimary
        };
        return ContextCompat.getColor(mContext, primaryInt[new Random().nextInt(14)]);
    }
}
