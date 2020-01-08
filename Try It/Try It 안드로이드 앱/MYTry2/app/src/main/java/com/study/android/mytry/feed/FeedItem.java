package com.study.android.mytry.feed;

public class FeedItem {

    private int feed_num;
    private String member_id;
    private int challenge_num;
    private String challenge_title;
    private String feed_update_time;
    private String feed_info;
    private String feed_comment;
    private String location;
    private int likecount;
    private int likeExist;
    private int comment_count;
    private String comment_userid;
    private String comment_content;

    private String member_profile;
    private String member_nicname;


    public FeedItem(int feed_num , String member_id , int challenge_num , String challenge_title , String feed_update_time , String feed_info , String feed_comment , int likecount , int likeExist , int comment_count) {
        this.feed_num = feed_num;
        this.member_id = member_id;
        this.challenge_num = challenge_num;
        this.challenge_title = challenge_title;
        this.feed_update_time = feed_update_time;
        this.feed_info = feed_info;
        this.feed_comment = feed_comment;
        this.likecount = likecount;
        this.likeExist = likeExist;
        this.comment_count = comment_count;
    }

    public FeedItem(int feed_num , String member_id , int challenge_num , String challenge_title , String feed_update_time , String feed_info , String feed_comment , int likecount , int likeExist , int comment_count , String member_profile , String member_nicname) {
        this.feed_num = feed_num;
        this.member_id = member_id;
        this.challenge_num = challenge_num;
        this.challenge_title = challenge_title;
        this.feed_update_time = feed_update_time;
        this.feed_info = feed_info;
        this.feed_comment = feed_comment;
        this.likecount = likecount;
        this.likeExist = likeExist;
        this.comment_count = comment_count;
        this.member_profile = member_profile;
        this.member_nicname = member_nicname;
    }

    public String getMember_profile() {
        return member_profile;
    }

    public String getMember_nicname() {
        return member_nicname;
    }

    public int getFeed_num() {
        return feed_num;
    }

    public void setFeed_num(int feed_num) {
        this.feed_num = feed_num;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public int getChallenge_num() {
        return challenge_num;
    }

    public void setChallenge_num(int challenge_num) {
        this.challenge_num = challenge_num;
    }

    public String getChallenge_title() {
        return challenge_title;
    }

    public void setChallenge_title(String challenge_title) {
        this.challenge_title = challenge_title;
    }

    public String getFeed_update_time() {
        return feed_update_time;
    }

    public void setFeed_update_time(String feed_update_time) {
        this.feed_update_time = feed_update_time;
    }

    public String getFeed_info() {
        return feed_info;
    }

    public void setFeed_info(String feed_info) {
        this.feed_info = feed_info;
    }

    public String getFeed_comment() {
        return feed_comment;
    }

    public void setFeed_comment(String feed_comment) {
        this.feed_comment = feed_comment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLikecount() {
        return likecount;
    }

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }

    public int getLikeExist() {
        return likeExist;
    }

    public void setLikeExist(int likeExist) {
        this.likeExist = likeExist;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getComment_userid() {
        return comment_userid;
    }

    public void setComment_userid(String comment_userid) {
        this.comment_userid = comment_userid;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }
}