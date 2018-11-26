package com.example.jakub.quizapp;

public class Question {
    private int mTestResId;
    private boolean mAnswerTrue;
    private boolean mQuestionAnswered;
    private boolean mQuestionIsCheated;

    public Question(int textResId, boolean answerTrue) {
        mTestResId = textResId;
        mAnswerTrue = answerTrue;
        mQuestionAnswered = false;
        mQuestionIsCheated = false;
    }

    public int getTestResId() {
        return mTestResId;
    }

    public void setTestResId(int testResId) {
        mTestResId = testResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isQuestionAnswered() {
        return mQuestionAnswered;
    }

    public void setQuestionAnswered(boolean questionAnswered) {
        mQuestionAnswered = questionAnswered;
    }

    public boolean getQuestionAnswered() {
        return mQuestionAnswered;
    }

    public void setQuestionIsCheated(boolean QuestionIsCheated) {
        mQuestionIsCheated = QuestionIsCheated;
    }

    public boolean getQuestionIsCheated() {
        return mQuestionIsCheated;
    }

}
