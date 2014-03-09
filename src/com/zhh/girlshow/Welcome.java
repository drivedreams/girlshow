package com.zhh.girlshow;

import com.zhh.girlshow.advise.AbstractMobiSageMediatorAd;
import com.zhh.girlshow.util.inernet.Exit;
import com.zhh.girlshow.widget.SwitchClickListener;
import com.mobisage.android.agg.view.AdSageLayout;
import com.mobisage.android.agg.view.AdSageSize;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class Welcome extends AbstractMobiSageMediatorAd {
	View internalCircle = null;
	LinearLayout switchBtnBorder = null;
	
	/**
	 * Listen to switch button.
	 */
	SwitchClickListener switchClickListener = new SwitchClickListener() {
		@Override
		public void switchBefore() {
			
		}

		@Override
		public void switchAfter() {
			startDisplay();
		}
	};

	//»Ö¸´°´Å¥³õÊ¼×´Ì¬
	@Override
	protected void onStart() {
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				Welcome.this, R.anim.switch_btn_click_resume_anim);
		internalCircle.startAnimation(hyperspaceJumpAnimation);
		super.onStart();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.welcom_layout);
		LinearLayout switch_btn_wrapper = (LinearLayout) findViewById(R.id.switch_btn_wrapper);
		switchBtnBorder = (LinearLayout) View.inflate(this,
				R.layout.switch_button, null);
		switch_btn_wrapper.addView(switchBtnBorder);
		internalCircle = (View) switchBtnBorder
				.findViewById(R.id.switch_btn_circle);
		internalCircle.setOnTouchListener(new SwitchButtonOnTouchListener());
		addADs();
		super.onCreate(savedInstanceState);
	}


	public void setOnSwitchClickListener(SwitchClickListener switchClickListener) {
		this.switchClickListener = switchClickListener;
	}

	@Override
	public void addADs() {
		Log.w("test", "add ADs");
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.advertise);
		final AdSageLayout adSageLayout;
		adSageLayout = new AdSageLayout(this, yourPublishId,
				AdSageSize.AdSageSize_Auto);
		adSageLayout.setAdListener(this);
		this.setAdSageLayout(adSageLayout);
		linearLayout.addView(adSageLayout);
	}

	private void startDisplay() {
		Intent intent = new Intent(this, ItemsActivity.class);
		startActivity(intent);
	}

	private class SwitchButtonOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			GestureDetector gestureDetector = new GestureDetector(Welcome.this,
					new GestureListener());

			gestureDetector.onTouchEvent(event);

			playAnim();
			return false;
		}

	}

	private void playAnim() {
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				Welcome.this, R.anim.switch_btn_click_anim);
		hyperspaceJumpAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				switchClickListener.switchBefore();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				switchClickListener.switchAfter();
			}
		});
		internalCircle.startAnimation(hyperspaceJumpAnimation);
	}

	private class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

	    	new Exit().appExit();
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}

	   
}
