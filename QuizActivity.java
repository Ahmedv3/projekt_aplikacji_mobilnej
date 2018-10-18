package com.example.jakub.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex;

    private Question[] mQuestionBank = new Question[] {
        new Question(R.string.questionRussia, true),
            new Question(R.string.questionAmerica, false),
            new Question(R.string.questionWroclaw, true),
            new Question(R.string.questionWarsaw, true),
            new Question(R.string.questionBerlin, false),
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.questionTextView);



        mTrueButton = (Button) findViewById(R.id.trueButton);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {checkAnswer(true);}

        });

        mFalseButton = (Button) findViewById(R.id.falseButton);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {checkAnswer(false);}

        });

        mNextButton = (Button) findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();

    }
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTestResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue){
            messageResId = R.string.correctToast;
            mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;
            updateQuestion();
        }
        else {
            messageResId = R.string.incorrectToast;
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

}
