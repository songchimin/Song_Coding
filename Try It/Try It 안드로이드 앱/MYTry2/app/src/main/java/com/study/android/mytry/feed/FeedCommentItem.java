package com.study.android.mytry.feed;

public class FeedCommentItem {
    private int commentNum;
    private String username;
    private String content;
    private String writeTime;
    private int likeCount;
    private int likeExist;
    private String userProfile;
    private String userNicname;


    public FeedCommentItem(int commentNum,  String username, String content, String writeTime, int likeCount, int likeExist){
        this.commentNum=commentNum;
        this.username=username;
        this.content=content;
        this.writeTime=writeTime;
        this.likeCount=likeCount;
        this.likeExist=likeExist;
    }

    public FeedCommentItem(int commentNum , String username , String content , String writeTime , int likeCount , int likeExist , String userProfile , String userNicname) {
        this.commentNum = commentNum;
        this.username = username;
        this.content = content;
        this.writeTime = writeTime;
        this.likeCount = likeCount;
        this.likeExist = likeExist;
        this.userProfile = userProfile;
        this.userNicname = userNicname;
    }

    public int getCommentNum() {
        return commentNum;
    }

       public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getWriteTime() {
        return writeTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getLikeExist() {
        return likeExist;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public String getUserNicname() {
        return userNicname;
    }
}