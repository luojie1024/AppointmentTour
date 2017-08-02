package hnulab.appointmenttour.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import hnulab.appointmenttour.MyApplication;
import hnulab.appointmenttour.R;
import hnulab.appointmenttour.base.BaseActivity;
import hnulab.appointmenttour.ui.home.MainActivity;
import hnulab.appointmenttour.widgets.TimerTextView;

/**
 * Created by dingmouren on 2017/3/14.
 */

public class SplashActivtiy extends BaseActivity {
          @BindView(R.id.tv_count_down)
          TimerTextView mTvCountDown;
          @BindView(R.id.lottie_view)
          LottieAnimationView mLottieView;
          @BindView(R.id.iv_splash_bg)
          ImageView iv_bg;
          @BindView(R.id.fonts)
          LinearLayout mFontsLayout;

          @Override
          public int setLayoutId() {
                    return R.layout.activity_splash;
          }

          @Override
          public void initView(Bundle savedInstanceState) {
                    MyApplication.applicationContext = getApplicationContext();
                    mTvCountDown.setTimes(2);
                    mTvCountDown.beginRun();
          }

          @Override
          public void initListener() {
                    iv_bg.setOnTouchListener(new View.OnTouchListener() {
                              @Override
                              public boolean onTouch(View v, MotionEvent event) {
                                        if (!mTvCountDown.isRun()) {
                                                  startActivity(new Intent(MyApplication.applicationContext, MainActivity.class));
                                                  finish();
                                        }
                                        return false;
                              }
                    });

                    mTvCountDown.setOnClickListener(v -> {
                              if (!mTvCountDown.isRun()) {
                                        startActivity(new Intent(this, MainActivity.class));
                                        finish();
                              }
                    });
          }

          @Override
          public void initData() {

          }

          @Override
          protected void onDestroy() {
                    super.onDestroy();
          }
}
