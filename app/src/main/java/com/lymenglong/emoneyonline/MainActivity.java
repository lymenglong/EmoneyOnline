package com.lymenglong.emoneyonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,
                getString(R.string.admob_app_id));


        //region BannerAd
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.admob_banner_id));

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        //endregion

        //region InterstitialAd
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("/6499/example/interstitial");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

        findViewById(R.id.button_int_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPublisherInterstitialAd.isLoaded()) {
                    mPublisherInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                // Load the next interstitial.
                mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
            }
        });
        //endregion

        //region RewardedVideo
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(videoAdListener);
        loadRewardedVideoAd();
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    RewardedVideoAdListener videoAdListener = new RewardedVideoAdListener() {
        @Override
        public void onRewarded(RewardItem reward) {
            Toast.makeText(MainActivity.this, "onRewarded! currency: " + reward.getType() + "  amount: " +
                    reward.getAmount(), Toast.LENGTH_SHORT).show();
            // Reward the user.
        }

        @Override
        public void onRewardedVideoAdLeftApplication() {
            Toast.makeText(MainActivity.this, "onRewardedVideoAdLeftApplication",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdClosed() {
            Toast.makeText(MainActivity.this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int errorCode) {
            Toast.makeText(MainActivity.this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdLoaded() {
            Toast.makeText(MainActivity.this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdOpened() {
            Toast.makeText(MainActivity.this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoStarted() {
            Toast.makeText(MainActivity.this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoCompleted() {
            Toast.makeText(MainActivity.this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
        }
    };

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("/6499/example/rewarded-video",
                new PublisherAdRequest.Builder().build());
    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
    //endregion
}
