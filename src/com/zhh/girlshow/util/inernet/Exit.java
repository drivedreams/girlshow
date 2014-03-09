package com.zhh.girlshow.util.inernet;

import android.os.Process;

public class Exit {

	public  void appExit(){
		Process.killProcess(Process.myPid());
	}
}
