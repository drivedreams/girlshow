package com.zhh.girshow.adapters;

import android.widget.Adapter;

public class Section {
	String caption;
	Adapter adapter;

	Section(String caption, Adapter adapter) {
	this.caption=caption;
	this.adapter=adapter;
	}
}
