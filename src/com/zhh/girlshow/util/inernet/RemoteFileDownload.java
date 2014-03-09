package com.zhh.girlshow.util.inernet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.zhh.girlshow.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class RemoteFileDownload {

	public static RemoteFileDownload getInstance(){
		return new RemoteFileDownload();
	}
	/**
	 * 
	 * @param c
	 * @param url
	 * @return
	 */
	public synchronized Bitmap getBitMap(Context c, String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		HttpURLConnection conn = null;
		Log.w("Pic", "set url ..." + url);
		try {
			myFileUrl = new URL(url);
			Log.w("Pic", "set url ...");
		} catch (MalformedURLException e) {

			bitmap = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.ic_launcher);
			Log.w("Pic", "failed at begin ...");
			return bitmap;
		}

		Log.w("Pic", "Begin to opne conn url " + url);

		try {
			conn = (HttpURLConnection) myFileUrl.openConnection();

			Log.w("Pic", "set conn ...");
			conn.setDoInput(true);
			Log.w("Pic", "seted conn ...");
			conn.connect();

			Log.w("Pic", "Conn success ...");

			InputStream is = conn.getInputStream();

			Log.w("Pic", "Beging download url ...");

			int length = (int) conn.getContentLength();
			if (length != -1) {
				byte[] imgData = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, imgData, destPos, readLen);
					destPos += readLen;
				}

				bitmap = BitmapFactory.decodeByteArray(imgData, 0,
						imgData.length);
				imgData = null;
			}
			
			Log.w("Pic", "Download successfully  ");
		} catch (IOException e) {
			bitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.sexymm04);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				
			}
		}

		return bitmap;
	}

	/**
	 * 
	 * @param c
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public synchronized Bitmap getBitMap(Context c, String url, float imageWidth) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		HttpURLConnection conn = null;
		//Log.w("Pic", "set url ..." + url);
		try {
			myFileUrl = new URL(url);
			//Log.w("Pic", "set url ...");
		} catch (MalformedURLException e) {

			bitmap = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.ic_launcher);
			//Log.w("Pic", "failed at begin ...");
			return bitmap;
		}

		//Log.w("Pic", "Begin to opne conn url " + url);

		try {
			conn = (HttpURLConnection) myFileUrl.openConnection();

			//Log.w("Pic", "set conn ...");
			conn.setDoInput(true);
			//Log.w("Pic", "seted conn ...");
			conn.connect();

			//Log.w("Pic", "Conn success ...");

			InputStream is = conn.getInputStream();

			//Log.w("Pic", "Beging download url ...");

			int length = (int) conn.getContentLength();
			if (length != -1) {
				byte[] imgData = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, imgData, destPos, readLen);
					destPos += readLen;
				}

				bitmap =  getCompressedPic(imgData, imageWidth);
				
			}

			//Log.w("Pic", "Download successfully  ");
		} catch (IOException e) {
			bitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.sexymm04);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return bitmap;
	}
	private Bitmap getCompressedPic(byte[] imgData, float imageWidth) {
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(imgData, 0,
				imgData.length, options);
		
		options.inJustDecodeBounds = false;
		int rate = (int)imageWidth / options.outWidth;
		//int height = options.outHeight * rate;
		options.inSampleSize = rate;
		options.inJustDecodeBounds = false;
		
		return  BitmapFactory.decodeByteArray(imgData, 0, imgData.length, options);
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public synchronized String getTxtString(String url) {
		System.out.println("url : " + url);
		URL myFileUrl = null;
		HttpURLConnection conn = null;
		String txtString = "";
		try {
			myFileUrl = new URL(url);
			//Log.w("test", "url : " + url);
			//Log.w("Txt", "set url ...");
		} catch (MalformedURLException e) {

			//Log.w("Txt", "failed at begin ...");
			return txtString;
		}
		try {
			//Log.w("Txt", "Begin to conn url ...");
			conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			//Log.w("Txt", "Conn success ...");
			InputStream is = conn.getInputStream();
			//Log.w("Txt", "Beging download url ...");
			int length = (int) conn.getContentLength();
			if (length != -1) {
				byte[] stringData = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, stringData, destPos, readLen);
					destPos += readLen;
				}
				is = new ByteArrayInputStream(stringData);
				InputStreamReader read = new InputStreamReader(is, "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(read);
				String tempString = null;
				while ((tempString = bufferedReader.readLine()) != null) {
					txtString += tempString;
				}
			//	Log.w("txt", txtString);
			}

		//	Log.w("Txt", "Download successfully  ");

		} catch (IOException e) {
			//Log.w("Pic", "Download failed  ");

			conn.disconnect();

			return txtString;
		}

		return txtString;
	}
}
