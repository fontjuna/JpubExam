package com.example.fontjuna.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = QuizActivity.class.getSimpleName();
    private static final String KEY_INDEX = "index";

    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_africa, false),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false)
    };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) 호출");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        findViewById(R.id.true_button).setOnClickListener(this);
        findViewById(R.id.false_button).setOnClickListener(this);
        findViewById(R.id.next_button).setOnClickListener(this);
        findViewById(R.id.prev_button).setOnClickListener(this);
        findViewById(R.id.cheat_button).setOnClickListener(this);
        findViewById(R.id.question_text_view).setOnClickListener(this);
        updateQuestion();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSavedInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.question_text_view:
                nextQuestion();
                break;
            case R.id.next_button:
                nextQuestion();
                break;
            case R.id.prev_button:
                prevQuestion();
                break;
            case R.id.false_button:
                checkAnswer(false);
                break;
            case R.id.true_button:
                checkAnswer(true);
                break;
            case R.id.cheat_button:
//                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivity(intent);
                break;
        }
    }

    private void nextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
    }

    private void prevQuestion() {
        mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
        if (mCurrentIndex < 0) {
            mCurrentIndex += mQuestionBank.length;
        }
        updateQuestion();
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() 호출");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() 호출");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() 호출");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() 호출");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() 호출");
    }

}
