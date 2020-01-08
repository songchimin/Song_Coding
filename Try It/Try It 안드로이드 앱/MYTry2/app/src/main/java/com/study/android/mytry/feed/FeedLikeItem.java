package com.study.android.mytry.feed;

public class FeedLikeItem {

    private String feedLikeUserid;
    private int followExist;

    public FeedLikeItem(String feedLikeUserid , int followExist) {
        this.feedLikeUserid = feedLikeUserid;
        this.followExist = followExist;
    }

    public String getFeedLikeUserid() {
        return feedLikeUserid;
    }

    public void setFeedLikeUserid(String feedLikeUserid) {
        this.feedLikeUserid = feedLikeUserid;
    }

    public int getFollowExist() {
        return followExist;
    }

    public void setFollowExist(int followExist) {
        this.followExist = followExist;
    }
}
