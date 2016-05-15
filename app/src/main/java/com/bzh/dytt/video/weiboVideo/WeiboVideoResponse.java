package com.bzh.dytt.video.weiboVideo;

import com.google.gson.annotations.SerializedName;

/**
 */
public class WeiboVideoResponse {
    @SerializedName("cards")
    private WeiboVideoCardsItem[] cardsItems;

    public WeiboVideoCardsItem[] getCardsItems() {
        return cardsItems;
    }

    public void setCardsItems(WeiboVideoCardsItem[] cardsItems) {
        this.cardsItems = cardsItems;
    }
}
