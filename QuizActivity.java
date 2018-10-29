package com.example.jakub.quizapp;

import android.annotation.SuppressLint;
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
    private TextView mQuestionTextView;
    private int mCurrentIndex;

    int currentPoints = 0;
    int questionAnswerStatus = 0;


    public void questionAnswerIsTrue(){
        mQuestionBank[mCurrentIndex].setQuestionAnswered(true);
        questionAnswerStatus +=1;
    }

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.questionRussia, true,false),
            new Question(R.string.questionAmerica, false,false),
            new Question(R.string.questionWroclaw, true,false),
            new Question(R.string.questionWarsaw, true,false),
            new Question(R.string.questionBerlin, false,false)
    };

    public void messageAnswer(){ Toast.makeText(this,"Odpowiedziałeś juz na to pytanie.",Toast.LENGTH_SHORT).show(); }

    public void showMessageEndQuiz(){
        String points = Integer.toString(currentPoints);
        Toast.makeText(this,"Koniec Quizu. Uzyskałeś: "+points+"/5 pkt",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = findViewById(R.id.questionTextView);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex != 4){
                    mCurrentIndex +=1;
                    updateQuestion();
                }
                else if(questionAnswerStatus == 5){
                    showMessageEndQuiz();
                }
                else{  }
            }
        });


        mTrueButton = findViewById(R.id.trueButton);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mQuestionBank[mCurrentIndex].getQuestionAnswered()==true){ messageAnswer();}
                else  {checkAnswer(true);}
            }

        });

        mFalseButton = findViewById(R.id.falseButton);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mQuestionBank[mCurrentIndex].getQuestionAnswered()==true){ messageAnswer();}
                else  {checkAnswer(false);}
            }

        });

        mNextButton = findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex != 4){
                    mCurrentIndex +=1;
                    updateQuestion();
                }
                else if(questionAnswerStatus == 5){
                    showMessageEndQuiz();
                }
                else{  }
            }
        });

        mPreviousButton = findViewById(R.id.previousButton);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex != 0){
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                }
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
        if(mQuestionBank[mCurrentIndex].getQuestionAnswered()==false){
            if (userPressedTrue == answerIsTrue){
                messageResId = R.string.correctToast;
                currentPoints +=1;
                questionAnswerIsTrue();
            }
            else {
                messageResId = R.string.incorrectToast;
                questionAnswerIsTrue();
            }
        }

            Toast mess = Toast.makeText(this,messageResId,Toast.LENGTH_SHORT);
         //  Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
            mess.setGravity(Gravity.TOP,0, 200);
            mess.show();

    }

}
