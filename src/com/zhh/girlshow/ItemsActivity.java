package com.zhh.girlshow;

import java.util.Arrays;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.ListView;

import com.zhh.girlshow.R;
import com.zhh.girlshow.util.inernet.RequestWrapper;
import com.zhh.girshow.adapters.UrlItemsAdapter;

public class ItemsActivity extends Activity {

	private ListView listViewLeft;
	private ListView listViewRight;
	private UrlItemsAdapter leftUrlItemsAdapter;
	private UrlItemsAdapter rightUrlItemsAdapter;
	int[] leftViewsHeights;
	int[] rightViewsHeights;

	private RequestWrapper requestWrapper = RequestWrapper.getIstance();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.items_list);

		listViewLeft = (ListView) findViewById(R.id.list_view_left);
		listViewRight = (ListView) findViewById(R.id.list_view_right);

		/*loadItems();*/
		new UrlPicsDownloader().execute();
		
		//listViewLeft.setOnTouchListener(touchListener);
		//listViewRight.setOnTouchListener(touchListener);
		/*listViewLeft.setOnScrollListener(scrollListener);
		listViewRight.setOnScrollListener(scrollListener);*/
	}

	// Passing the touch event to the opposite list
	OnTouchListener touchListener = new OnTouchListener() {
		boolean dispatched = false;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (v.equals(listViewLeft) && !dispatched) {
				dispatched = true;
				listViewRight.dispatchTouchEvent(event);
			} else if (v.equals(listViewRight) && !dispatched) {
				dispatched = true;
				listViewLeft.dispatchTouchEvent(event);
			}

			dispatched = false;
			return false;
		}
	};

	/**
	 * Synchronizing scrolling Distance from the top of the first visible
	 * element opposite list: sum_heights(opposite invisible screens) -
	 * sum_heights(invisible screens) + distance from top of the first visible
	 * child
	 */
	OnScrollListener scrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView v, int scrollState) {
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

			if (view.getChildAt(0) != null) {
				if (view.equals(listViewLeft)) {
					leftViewsHeights[view.getFirstVisiblePosition()] = view
							.getChildAt(0).getHeight();

					int h = 0;
					for (int i = 0; i < listViewRight.getFirstVisiblePosition(); i++) {
						h += rightViewsHeights[i];
					}

					int hi = 0;
					for (int i = 0; i < listViewLeft.getFirstVisiblePosition(); i++) {
						hi += leftViewsHeights[i];
					}

					int top = h - hi + view.getChildAt(0).getTop();
					listViewRight.setSelectionFromTop(
							listViewRight.getFirstVisiblePosition(), top);
				} else if (view.equals(listViewRight)) {
					rightViewsHeights[view.getFirstVisiblePosition()] = view
							.getChildAt(0).getHeight();

					int h = 0;
					for (int i = 0; i < listViewLeft.getFirstVisiblePosition(); i++) {
						h += leftViewsHeights[i];
					}

					int hi = 0;
					for (int i = 0; i < listViewRight.getFirstVisiblePosition(); i++) {
						hi += rightViewsHeights[i];
					}

					int top = h - hi + view.getChildAt(0).getTop();
					listViewLeft.setSelectionFromTop(
							listViewLeft.getFirstVisiblePosition(), top);
				}

			}

		}
	};

	 
	private class UrlPicsDownloader extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... arg0) {
			String result = requestWrapper.getRequestResult("http://bcs.duapp.com/photo-repository/test.txt");
			Log.w("test", result);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {

			
			String [] urls = {"http://cyj.qiniudn.com/99506/1393643781502p18htq8vao1obfs4v1ci2l5pcn2.jpg-display",  
					"http://cyj.qiniudn.com/99506/1393643818744p18htq8vao6ase7s1d51g71g7ad-1393650875.jpg-display", 
					"http://cyj.qiniudn.com/99506/1393643816076p18htq8vao1rf01ik6q8s1a0p6gnc.jpg-display_g", 
					"http://cyj.qiniudn.com/99506/1393643823454p18htq8vaofn9g02qm4r4l1qh9e-1393650865.jpg-display", 
					"http://cyj.qiniudn.com/99506/1393643825862p18htq8vao7cn1idu1867urf3ukf.jpg-display_g", 
					"http://cyj.qiniudn.com/99506/1393643833489p18htq8vao109s186r7jtv9u1allh-1393651582.jpg-display", 
					"http://cyj.qiniudn.com/99506/1393643830975p18htq8vao5su17s1nvb1ic511qcg.jpg-display", 
					"http://cyj.qiniudn.com/99506/1393643852282p18htq8vao84l1oo1j0mnn013mun.jpg-display_g", 
					"http://cyj.qiniudn.com/99506/1393643865782p18htq8vaolj91jhicqrir114l1r.jpg-display", 
					"http://cyj.qiniudn.com/99506/1393643858523p18htq8vao1it1qje11cuj23pegp.jpg-display", 
					"http://cyj.qiniudn.com/99506/1393643835768p18htq8vao1g1n1kntiom181eb0fi.jpg-display_g",
					"http://cyj.qiniudn.com/99506/1393643855798p18htq8vao1pigmu16291p5j1dq9o.jpg-display ",
					"http://cyj.qiniudn.com/99506/1393692550495p18hv8p7qu169b5bu1lj1pu01v663.jpg-display_g",
					"http://cyj.qiniudn.com/99506/1393692548994p18hv8p7quf3u18u1159v1nb31p6i2.jpg-display_g",
					"http://cyj.qiniudn.com/99506/1393643840993p18htq8vao1a43l061f6f12mr83qk.jpg-display_g", 
					"http://cyj.qiniudn.com/99506/1393643844385p18htq8vao1aiohmu1nlji5v1cakl.jpg-display_g",
					"http://cyj.qiniudn.com/99506/1393643848062p18htq8vao1vq31fmn1fei6rt1s8fm.jpg-display_g", 
					"http://cyj.qiniudn.com/1393140001/8E1587A9-0797-4388-A482-DB48B584C1B8.jpg-display",
					"http://cyj.qiniudn.com/99506/1393655106599p18hu521cfbksdp81lct1n5oo7u7.jpg-display_g"};
			int halfLength = urls.length /2;
		    String [] leftUrls = Arrays.copyOf(urls, urls.length - halfLength);
			
		    String [] rightUrls = Arrays.copyOfRange(urls, urls.length - halfLength -1 , urls.length - halfLength + halfLength);		
		  //  Log.w("test", "urls : " + urls[0]);
			leftUrlItemsAdapter = new UrlItemsAdapter(ItemsActivity.this,  R.layout.item, urls);
			//rightUrlItemsAdapter = new UrlItemsAdapter(ItemsActivity.this,  R.layout.item, rightUrls);
			//SectionedAdapter adapter = new 
			listViewLeft.setAdapter(leftUrlItemsAdapter);
			
			listViewRight.setAdapter(rightUrlItemsAdapter);
			
			leftViewsHeights = new int[urls.length];
			rightViewsHeights = new int[urls.length];
			
			super.onPostExecute(result);
		}

	}

}