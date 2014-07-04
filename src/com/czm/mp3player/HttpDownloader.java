package com.czm.mp3player;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class HttpDownloader {
	private URL url=null;
	public String download(String urlStr){
		StringBuffer sb=new StringBuffer();
		String line=null;
		BufferedReader buffer=null;
		try {
			url=new URL(urlStr);
			
			HttpURLConnection urlConn=(HttpURLConnection)url.openConnection();
			buffer=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			while((line=buffer.readLine())!=null){
				sb.append(line);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{if(buffer!=null)
				buffer.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	public int downFile(String urlStr,String path,String fileName){
		InputStream inputStream=null;
		try{
		SDFile sdFile=new SDFile();
		if(sdFile.isFileExist(path+fileName)){
			return 1;
		}else{
			inputStream= getInputStreamFromUrl(urlStr);
			File resultFile =sdFile.writeSDFromInput(path, fileName, inputStream);
			if(resultFile==null){
				return -1;
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return 0;
	}
	private InputStream getInputStreamFromUrl(String urlStr) {
		InputStream in=null;
		try {
			URL url=new URL(urlStr);
			try {
				HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
				in=httpConn.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}
	
	
	
	
}
