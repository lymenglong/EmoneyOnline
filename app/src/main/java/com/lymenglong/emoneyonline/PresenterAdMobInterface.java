package com.lymenglong.emoneyonline;

import com.google.android.gms.ads.AdView;

public interface PresenterAdMobInterface {

    void InitialMobileAd(String adMobAppId);

    void LoadBannerAd(AdView mAdView, String unitBannerId);

    void LoadInterstitialAd(String unitInterstitialId);

    void ShowInterstitialAd();

    void LoadRewardedAd(String unitRewardedId);

    void ShowRewardAd();

    void ResumeRewardAd();

    void PauseRewardAd();

    void DestroyRewardAd();
}
