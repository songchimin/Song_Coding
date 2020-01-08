package com.study.android.mytry.mypage.setup;

public class QnAItem {

    private int question_num;
    private String member_id;
    private String manager_id;
    private String question_title;
    private String question_Content;
    private String question_Picture;
    private String question_answer;
    private String question_divide;
    private String question_state;
    private String question_date;
    private String answer_date;

    public QnAItem( int question_num,
             String member_id,
             String manager_id,
             String question_title,
             String question_Content,
             String question_Picture,
             String question_answer,
             String question_divide,
             String question_state,
             String question_date,
             String answer_date) {
        this.question_num=question_num;
        this.member_id=member_id;
        this.manager_id=manager_id;
        this.question_title=question_title;
        this.question_Content=question_Content;
        this.question_Picture=question_Picture;
        this.question_answer=question_answer;
        this.question_answer=question_answer;
        this.question_divide=question_divide;
        this.question_state=question_state;
        this.question_date=question_date;
        this.answer_date=answer_date;
    }

    public int getQuestion_num() {
        return question_num;
    }

    public void setQuestion_num(int question_num) {
        this.question_num = question_num;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getManager_id() {
        return manager_id;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_Content() {
        return question_Content;
    }

    public void setQuestion_Content(String question_Content) {
        this.question_Content = question_Content;
    }

    public String getQuestion_Picture() {
        return question_Picture;
    }

    public void setQuestion_Picture(String question_Picture) {
        this.question_Picture = question_Picture;
    }

    public String getQuestion_answer() {
        return question_answer;
    }

    public void setQuestion_answer(String question_answer) {
        this.question_answer = question_answer;
    }

    public String getQuestion_divide() {
        return question_divide;
    }

    public void setQuestion_divide(String question_divide) {
        this.question_divide = question_divide;
    }

    public String getQuestion_state() {
        return question_state;
    }

    public void setQuestion_state(String question_state) {
        this.question_state = question_state;
    }

    public String getQuestion_date() {
        return question_date;
    }

    public void setQuestion_date(String question_date) {
        this.question_date = question_date;
    }

    public String getAnswer_date() {
        return answer_date;
    }

    public void setAnswer_date(String answer_date) {
        this.answer_date = answer_date;
    }
}

