package com.study.android.mytry.challenge_public;

public class CommentItem {

    private int commentNum;
    private int challengeNum;
    private String username;
    private String content;
    private String writeTime;
    private int likeCount;
    private int likeExist;

    public CommentItem(int commentNum, int challengeNum, String username, String content, String writeTime, int likeCount, int likeExist){
        this.commentNum=commentNum;
        this.challengeNum = challengeNum;
        this.username=username;
        this.content=content;
        this.writeTime=writeTime;
        this.likeCount=likeCount;
        this.likeExist=likeExist;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public int getChallengeNum() {
        return challengeNum;
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



}
