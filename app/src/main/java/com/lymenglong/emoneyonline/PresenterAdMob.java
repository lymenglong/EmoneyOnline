package com.lymenglong.emoneyonline;

import android.content.Context;
import android.util.Log;
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

public class PresenterAdMob implements PresenterAdMobInterface {
    private static final String TAG = "PresenterAdMob";
    private Context context;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    AdView adView;
    private Boolean isShowingRewardedAd = false;

    PresenterAdMob(Context context) {
        this.context = context;
    }

    @Override
    public void InitialMobileAd(String adMobAppId) {
        MobileAds.initialize(context, context.getString(R.string.admob_app_id));
    }

    @Override
    public void LoadBannerAd(final AdView mAdView, String unitBannerId) {
        //region BannerAd
        adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(unitBannerId);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d(TAG, "onAdLoaded: "+ mAdView.getAdUnitId());
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d(TAG, "onAdFailedToLoad: "+ mAdView.getAdUnitId());
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                Log.d(TAG, "onAdOpened: "+ mAdView.getAdUnitId());
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                Log.d(TAG, "onAdLeftApplication: "+ mAdView.getAdUnitId());
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        //endregion
    }

    @Override
    public void LoadInterstitialAd(String unitInterstitialId){
        //region InterstitialAd
        mPublisherInterstitialAd = new PublisherInterstitialAd(context);
        mPublisherInterstitialAd.setAdUnitId(unitInterstitialId);
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d(TAG, "onAdLoaded: " + mPublisherInterstitialAd.getAdUnitId());
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "onAdFailedToLoad: " + mPublisherInterstitialAd.getAdUnitId());
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.d(TAG, "onAdOpened: " + mPublisherInterstitialAd.getAdUnitId());
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d(TAG, "onAdLeftApplication: " + mPublisherInterstitialAd.getAdUnitId());
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Log.d(TAG, "onAdClosed: " + mPublisherInterstitialAd.getAdUnitId());
                // Load the next interstitial.
                mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
            }
        });
        //endregion
    }

    @Override
    public void ShowInterstitialAd(){
        if (mPublisherInterstitialAd.isLoaded()) {
            mPublisherInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    @Override
    public void LoadRewardedAd(String unitRewardedId) {
        //region RewardedVideo
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        mRewardedVideoAd.setRewardedVideoAdListener(videoAdListener);
        mRewardedVideoAd.loadAd(unitRewardedId, new PublisherAdRequest.Builder().build());
    }

    @Override
    public void ShowRewardAd(){
        if (mRewardedVideoAd.isLoaded()) mRewardedVideoAd.show();
        else Toast.makeText(context, "No video loaded", Toast.LENGTH_SHORT).show();
    }
    private Boolean IsShowingRewardedAd(){
        return isShowingRewardedAd;
    }

    @Override
    public void ResumeRewardAd(){
        if(IsShowingRewardedAd()) mRewardedVideoAd.resume(context);
    }

    @Override
    public void PauseRewardAd(){
        if(IsShowingRewardedAd()) mRewardedVideoAd.pause(context);
    }

    @Override
    public void DestroyRewardAd(){
        if(IsShowingRewardedAd()) mRewardedVideoAd.destroy(context);
    }

    private RewardedVideoAdListener videoAdListener = new RewardedVideoAdListener() {
        @Override
        public void onRewarded(RewardItem reward) {
            Toast.makeText(context, "onRewarded! currency: " + reward.getType() + "  amount: " +
                    reward.getAmount(), Toast.LENGTH_SHORT).show();
            // Reward the user.
        }

        @Override
        public void onRewardedVideoAdLeftApplication() {
            Toast.makeText(context, "onRewardedVideoAdLeftApplication",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdClosed() {
            isShowingRewardedAd = false;
            Toast.makeText(context, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int errorCode) {
            Toast.makeText(context, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdLoaded() {
            Toast.makeText(context, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdOpened() {
            Toast.makeText(context, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoStarted() {
            isShowingRewardedAd = true;
            Toast.makeText(context, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoCompleted() {
            Toast.makeText(context, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
            isShowingRewardedAd = false;
        }
    };

    //endregion
}
