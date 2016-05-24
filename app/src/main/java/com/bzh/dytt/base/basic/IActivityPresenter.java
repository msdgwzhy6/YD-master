package com.bzh.dytt.base.basic;

import android.os.Bundle;

public interface IActivityPresenter {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);
}
