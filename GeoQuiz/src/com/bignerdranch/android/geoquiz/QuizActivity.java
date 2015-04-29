package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private Button mPreviousButton;
	private TextView mQuestionTextView;
	private Button mCheatButton;
	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX= "index";
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
		new TrueFalse(R.string.question_oceans, true),
		new TrueFalse(R.string.question_mideast, false),
		new TrueFalse(R.string.question_africa, false),
		new TrueFalse(R.string.question_americas, true),
		new TrueFalse(R.string.question_asia, true),
	};
	private int mCurrentIndex = 0;
	private boolean mIsCheater;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (data == null){
			return;
		}
		mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
	}
	
	private void updateQuestion(){
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue){
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		
		int messageResId = 0;
		
		if(mIsCheater){
			messageResId = R.string.judgment_toast;
		} else {
		
			if (userPressedTrue == answerIsTrue){
			messageResId = R.string.correct_toast;
			} else {
			messageResId = R.string.incorrect_toast;
			  }
		  }
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle) called");
		setContentView(R.layout.activity_quiz);
		
		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
		
		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				checkAnswer(true);
			}
		});
		
		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				checkAnswer(false);
			}
		});
		
		mNextButton = (Button) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				mIsCheater = false;
				updateQuestion();
				
			}
		});
		
		mPreviousButton = (Button) findViewById(R.id.previous_button);
		mPreviousButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int length = mQuestionBank.length;
				mCurrentIndex = (mCurrentIndex + (length-1)) % length;;
				updateQuestion();
			}
		});
		
		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				startActivityForResult(i, 0);
			}
		});
		
		if(savedInstanceState != null){
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
		}
		
		updateQuestion();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		Log.d(TAG, "onStart() called");
	}
	
	@Override
	public void onPause(){
		super.onPause();
		Log.d(TAG, "onPause() is called");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.d(TAG, "onResume() is called");
	}
	
	@Override
	public void onStop(){
		super.onStop();
		Log.d(TAG, "onStop() is called");
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.d(TAG, "onDestroy() is called");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
