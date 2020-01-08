package com.study.android.mytry.mypage;

public class MyChallengeItem {

    private int num;
    private String title;
    private String category;
    private String type;
    private String frequency;
    private String start;
    private String end;
    private String fee;
     private String challenge_time;
    private String detail;
    private String first_image;
    private String state;
    private String pub;
    private String exp;
    private String alongday;
    private String host;

    public MyChallengeItem(int num, String title, String category, String type, String frequency, String start, String end, String fee, String challenge_time, String detail, String first_image, String state, String pub, String exp, String alongday, String host) {
        this.num = num;
        this.title = title;
        this.category = category;
        this.type = type;
        this.frequency = frequency;
        this.start = start;
        this.end = end;
        this.fee = fee;
        this.challenge_time = challenge_time;
        this.detail = detail;
        this.first_image = first_image;
        this.state = state;
        this.pub = pub;
        this.exp = exp;
        this.alongday=alongday;
        this.host = host;
    }

    public int getNum() {
        return num;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDetail() {
        return detail;
    }

    public String getFirst_image() { return first_image; }

    public String getFee() {
        return fee;
    }

    public String getPub() {
        return pub;
    }

    public String getExp() {
        return exp;
    }

    public String getAlongday() {
        return alongday;
    }

    public String getChallenge_time() {
        return challenge_time;
    }

    public String getHost() {
        return host;
    }


}

