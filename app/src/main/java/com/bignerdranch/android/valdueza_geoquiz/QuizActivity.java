package com.bignerdranch.android.valdueza_geoquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index"; // Lesson3

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private boolean[] qAnswered = { false, false, false, false, false, false};
    private int questionsGotRight = 0;

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){ // Lesson3
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
                qAnswered[mCurrentIndex] = true;
                updateButtons();
                if(checkIfAllAnswered()){
                    showResults();
                }
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                qAnswered[mCurrentIndex] = true;
                updateButtons();
                if(checkIfAllAnswered()){
                    showResults();
                }
            }
        });

        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex!=0){
                    mCurrentIndex = (mCurrentIndex-1) % mQuestionBank.length;
                    updateQuestion();
                }
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();
    }

    private void updateQuestion(){
        updateButtons(); //Lesson3.1

        int question = mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionTextView.setText(question);
    }

    private void showResults(){
        String result = "You scored: " + questionsGotRight + "/" + mQuestionBank.length;
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    private void updateButtons(){ //Lesson3.1
        if(qAnswered[mCurrentIndex]){
            setButtonActiveness(false);
        } else {
            setButtonActiveness(true);
        }
    }

    private boolean checkIfAllAnswered(){
        int unanswered = 0;
        for(int x = 0; qAnswered.length > x; x++ ){
            if(qAnswered[x] == false){
                unanswered++;
            }
        }
        if(unanswered>0){
            return false;
        } else {
            return true;
        }
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();

        int messageResId = 0;

        if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
            questionsGotRight++;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        qAnswered[mCurrentIndex] = true;

        //Log.d(TAG, "currentIndex: "+mCurrentIndex);
        Log.d(TAG, "Got Right: "+ questionsGotRight);

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "yo....", Toast.LENGTH_SHORT).show();
    }

    private void setButtonActiveness(boolean x){ //Lesson3.1
        mTrueButton.setClickable(x);
        mTrueButton.setEnabled(x);
        mFalseButton.setClickable(x);
        mFalseButton.setEnabled(x);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override // Lesson3
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

}