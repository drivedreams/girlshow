package com.zhh.girlshow.util.inernet;

import android.util.Pair;

public class PinterestLayoutAlgoritmo {

	private static Pair<Integer, Integer> heights = new Pair<Integer, Integer>(0, 0);
	public static Pair<Integer, Integer> getPinterestPostion(int imageHeight){
		imageHeight += 5;
		return heights.first> heights.second ? new Pair<Integer, Integer>(heights.first, heights.second + imageHeight): new Pair<Integer, Integer>(heights.first + imageHeight, heights.second);
	}
}
