package com.lymenglong.emoneyonline;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private Context context = MainActivity.this;
    private PresenterAdMob presenterAdMob = new PresenterAdMob(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initial Mobile Ad
        presenterAdMob.InitialMobileAd(getString(R.string.admob_app_id));

        //Load Banner Ad
        AdView adView = findViewById(R.id.adView);
        presenterAdMob.LoadBannerAd(adView, getString(R.string.admob_banner_id));

        //Load Interstitial Ad
        presenterAdMob.LoadInterstitialAd(getString(R.string.admob_interstitial_id));
        findViewById(R.id.button_int_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show Interstitial Ad
                presenterAdMob.ShowInterstitialAd();
            }
        });

        //Load Rewarded Ad
        presenterAdMob.LoadRewardedAd(getString(R.string.admob_rewarded_id));
        findViewById(R.id.button_video_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show Rewarded Ad
                presenterAdMob.ShowRewardAd();
            }
        });

    }
}
