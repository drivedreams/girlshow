package com.zhh.girshow.adapters;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhh.girlshow.R;
import com.zhh.girlshow.util.inernet.RemoteFileDownload;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class UrlItemsAdapter extends ArrayAdapter<String> {

	private final int CACHE_LIMIT = 5;
	Context context;
	LayoutInflater inflater;
	float imageWidth;
	int resourceId;
	private RemoteFileDownload remoteFileDownload = RemoteFileDownload
			.getInstance();
	// To cache the already loaded bitmap.
	private Map<String, SoftReference<Bitmap>> loadedBitmaps = new HashMap<String, SoftReference<Bitmap>>();

	private List<String> urls = new ArrayList<String>();
	public UrlItemsAdapter(Context context, int resourceId, String[] items) {
		super(context, resourceId, items);
		this.context = context;
		this.resourceId = resourceId;

		Log.w("Pic", "items url: " + items[0]);
		float width = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getWidth();
		float margin = (int) convertDpToPixel(10f, (Activity) context);
		// two images, three margins of 10dips
		imageWidth = ((width - (3 * margin)) / 2);
		
		
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FrameLayout row = (FrameLayout) convertView;
		ItemHolder holder;

		// Get item in specified position.
		String item = getItem(position);

		// Reuse the view had created.
		if (row == null) {
			holder = new ItemHolder();
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = (FrameLayout) inflater.inflate(resourceId, parent, false);
			ImageView itemImage = (ImageView) row.findViewById(R.id.item_image);
			holder.itemImage = itemImage;
			//LayoutParams laout = holder.itemImage.getLayoutParams();
			//laout.width = (int) imageWidth;
			//laout.height = 300;
			itemImage.setMinimumHeight(50);
			//itemImage.setLayoutParams(laout);
		} else {
			holder = (ItemHolder) row.getTag();
		}
		row.setTag(holder);
		try {
			setImageBitmap(item, holder.itemImage);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return row;
	}

	private void setImageBitmap(String item, ImageView itemImage)
			throws IOException {

		new PicLoaderTask(item, itemImage).execute();
	}

	// Conveniently gets view.
	private static class ItemHolder {
		ImageView itemImage;
	}

	// Gets the width of the device.
	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	// Task for downloading image.
	private class PicLoaderTask extends
			AsyncTask<Void, Void, SoftReference<Bitmap>> {

		private String item;
		private ImageView itemImage;

		public PicLoaderTask(String item, ImageView itemImage) {
			super();
			this.item = item;
			this.itemImage = itemImage;
		}

		@Override
		protected synchronized SoftReference<Bitmap> doInBackground(Void... params) {
			SoftReference<Bitmap> softLoadedBitmap = null;
			// If picture has been loaded, just get  from cache.
			if (loadedBitmaps.containsKey(item) && loadedBitmaps.get(item) !=null) {
				return loadedBitmaps.get(item);
			} else {
				softLoadedBitmap = new SoftReference<Bitmap>(
						remoteFileDownload.getBitMap(context, item, imageWidth));
				loadedBitmaps.put(item, softLoadedBitmap);
				//Limit the cache pool size below CACHE_LIMIT
				if(urls.size() <= CACHE_LIMIT){
					urls.add(item);
				}else{
					loadedBitmaps.remove(urls.get(0));
					urls.remove(0);
					urls.add(item);
				}
			}

			return softLoadedBitmap;
		}

		@Override
		protected void onPostExecute(SoftReference<Bitmap> result) {
			itemImage.setImageBitmap(result.get());
			super.onPostExecute(result);
		}

	}

}
