package com.zhh.girlshow.util.inernet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

public class FileStorage {
	private Context context;
	
	public FileStorage( Context context) { 
		
		this.context = context;
	}
 

	/**
	 * You should give a image type such like
	 * {@link android.graphics.Bitmap.CompressFormat.JPEG}
	 * 
	 * @param bitmap
	 * @param imageFormat
	 * @param path
	 * @param context
	 */
	public void storageBitmapToInterlStorage(Bitmap bitmap,
		  CompressFormat imageFormat, String folder, String name) {
		if(bitmap !=null){
		
				File file =  context.getDir(folder, Context.MODE_PRIVATE)  ;
				if(!file.exists()){
					file.mkdir();
				}
				File newFile = new File(file, name);
		      try {
		    	
				newFile.createNewFile();
				
				FileOutputStream fo = new FileOutputStream(
						newFile.getAbsolutePath());
				 
				bitmap.compress(imageFormat, 100, fo);
				fo.flush();
				fo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		   
	}

	 
	public void storageFileToInternalStorage(String folder,String name, String content) {
		BufferedWriter writer = null;
		File file =  context.getDir(folder, Context.MODE_PRIVATE)  ;
		if(!file.exists()){
			file.mkdir();
		}
		File newFile = new File(file, name);
		 
		FileOutputStream fileOutputStream = null;
		try { 
			newFile.createNewFile();
			fileOutputStream = new FileOutputStream(newFile);
			writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			fileOutputStream.write(content.getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String readFileFromInternalStorage(String folder, String fileName) {
		String eol = System.getProperty("line.separator");
		File file =  context.getDir(folder, Context.MODE_PRIVATE)  ;
		File newFile = new File(file, fileName);
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = input.readLine()) != null) {
				buffer.append(line + eol);
			}
			
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return "";
	}

	/*
	 * private void readFileFromSDCard() { File directory =
	 * Environment.getExternalStorageDirectory(); // assumes that a file
	 * article.rss is available on the SD card File file = new File(directory +
	 * "/article.rss"); if (!file.exists()) { throw new
	 * RuntimeException("File not found"); } Log.e("Testing",
	 * "Starting to read"); BufferedReader reader = null; try { reader = new
	 * BufferedReader(new FileReader(file)); StringBuilder builder = new
	 * StringBuilder(); String line; while ((line = reader.readLine()) != null)
	 * { builder.append(line); } } catch (Exception e) { e.printStackTrace(); }
	 * finally { if (reader != null) { try { reader.close(); } catch
	 * (IOException e) { e.printStackTrace(); } } } }
	 */
}
