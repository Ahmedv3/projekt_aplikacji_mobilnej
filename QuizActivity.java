package com.example.jakub.quizapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex;
    private TextView mApiLevelTextView;

    private boolean mIsCheater;

    int currentPoints = 0;
    int questionAnswerStatus = 0;

    public int cheatToken = 1;

    private static final int REQUEST_CODE_CHEAT = 0;


    void messageAnswer() {
        Toast.makeText(this, "Odpowiedziałeś juz na to pytanie.", Toast.LENGTH_SHORT).show();
    }
    void tokenStatusMessage(){

        Toast.makeText(this, "Wykorzystałeś: "+cheatToken+"/3"+" tokeny.", Toast.LENGTH_SHORT).show();
        if(cheatToken >= 3){
            mCheatButton.setVisibility(View.INVISIBLE);
            nullTokensMessage();
        }
    }

    void nullTokensMessage() {
        Toast.makeText(this, "Koniec tokenów z podpowiedziami.", Toast.LENGTH_SHORT).show();
    }

    void showMessageEndQuiz() {
        Toast.makeText(this, "Koniec Quizu. Uzyskałeś: " + currentPoints + "/" + mQuestionBank.length + " pkt.", Toast.LENGTH_SHORT).show();
    }

    void EndQuiz(int answerStatus) {
        if (answerStatus == 5) {
            showMessageEndQuiz();
        }
    }

    void questionAnswerIsTrue() {
        mQuestionBank[mCurrentIndex].setQuestionAnswered(true);
        questionAnswerStatus += 1;
    }

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.questionRussia, true),
            new Question(R.string.questionAmerica, false),
            new Question(R.string.questionWroclaw, true),
            new Question(R.string.questionWarsaw, true),
            new Question(R.string.questionBerlin, false)
    };

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTestResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (mQuestionBank[mCurrentIndex].getQuestionIsCheated() == true) {

            messageResId = R.string.judgmentText;
        }
        if (mQuestionBank[mCurrentIndex].getQuestionAnswered() == false) {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correctToast;
                currentPoints += 1;
                questionAnswerIsTrue();
            } else {
                messageResId = R.string.incorrectToast;
                questionAnswerIsTrue();
            }
        }

        Toast mess = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        mess.setGravity(Gravity.TOP, 0, 200);
        mess.show();
        EndQuiz(questionAnswerStatus);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        mApiLevelTextView = findViewById(R.id.apiLevelTextView);
        mApiLevelTextView.setText("Api Level: "+String.valueOf(Build.VERSION.SDK_INT));

        mCheatButton = findViewById(R.id.cheatButton);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cheatToken <= 3 && mIsCheater == false){
                    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                    Intent intent = cheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                    startActivityForResult(intent, REQUEST_CODE_CHEAT);
                }
            }
        });

        mQuestionTextView = findViewById(R.id.questionTextView);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex != 4) {
                    mCurrentIndex += 1;
                    mIsCheater = false;
                    updateQuestion();
                }
            }
        });


        mTrueButton = findViewById(R.id.trueButton);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuestionBank[mCurrentIndex].getQuestionAnswered() == true && mQuestionBank[mCurrentIndex].getQuestionIsCheated() == false) {
                    messageAnswer();
                } else {
                    checkAnswer(true);
                }
            }

        });

        mFalseButton = findViewById(R.id.falseButton);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuestionBank[mCurrentIndex].getQuestionAnswered() == true && mQuestionBank[mCurrentIndex].getQuestionIsCheated() == false) {
                    messageAnswer();
                } else {
                    checkAnswer(false);
                }
            }

        });

        mNextButton = findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex != 4) {
                    mCurrentIndex += 1;
                    mIsCheater = false;
                    updateQuestion();
                }
            }
        });

        mPreviousButton = findViewById(R.id.previousButton);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex != 0) {
                    mCurrentIndex -= 1;
                    updateQuestion();
                }
            }
        });
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }

            mIsCheater = cheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setQuestionIsCheated(mIsCheater);
            if (!mQuestionBank[mCurrentIndex].isQuestionAnswered()) {
                tokenStatusMessage();
                questionAnswerStatus += 1;
                cheatToken +=1;
                mQuestionBank[mCurrentIndex].setQuestionAnswered(true);
            }


        }
    }

}
