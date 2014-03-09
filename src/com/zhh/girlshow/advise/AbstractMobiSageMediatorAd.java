package com.zhh.girlshow.advise;

import android.app.Activity;
import android.view.KeyEvent;
import com.mobisage.android.agg.view.AdSageLayout;
import com.mobisage.android.agg.view.AdSageListener;

public abstract class AbstractMobiSageMediatorAd extends Activity implements AdSageListener{


    //此处配置 您的艾德聚合平台publishId；
	

	
	//此处为sample程序中艾德聚合广告view
	private AdSageLayout adSageLayout = null; ;
	public void setAdSageLayout(AdSageLayout adSageLayout) {
		this.adSageLayout = adSageLayout;
	}

	public AbstractMobiSageMediatorAd() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static String yourPublishId = "3688daf8cf2d4cddaa8f5465ff208bc8"; 
	
	 /* 7.释放资源（请务必执行此操作） */
	@Override
    public void onDestroy(){
	    	super.onDestroy();
	    	/* 请您于此处释放资源*/
	    	if (adSageLayout != null){
	    		adSageLayout.destroy();
	    	}
    }
	
	/**
	 * This method must be implemented. And you should add this method to onCreate method.
	 * The following is an example to add ADs view:
	 * <pre>
	 * 	LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fullscreen_content_controls);
		final AdSageLayout adSageLayout;
        adSageLayout = new AdSageLayout(this, yourPublishId,AdSageSize.AdSageSize_Auto);	
        adSageLayout.setAdListener(this);
        linearLayout.addView(adSageLayout);
	 * </pre>
	 */
	public abstract void addADs();
	
	@Override
	public void onClickAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloseBannerAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloseFullAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDismissScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailedReceiveAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailedReceiveFullScreenAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFullScreenDismissScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFullScreenPresentScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveFullScreenAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSplashLoadFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSplshScreenDismissScreen() {
		adSageLayout.closeDomobSplashAd();
		
	}

	@Override
	public void onSplshScreenNoReady() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSplshScreenPresentScreen() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//		 Back key disabled
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			android.os.Process.killProcess(android.os.Process.myPid());
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	 
}
