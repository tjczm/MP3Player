package com.czm.mp3player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class SDFile {
	private String SDPATH;
	public String getSDPATH(){
		return SDPATH;
	}
	public SDFile(){
		SDPATH=Environment.getExternalStorageDirectory()+"/";
		
	}
	public File creatSDFile(String fileName){
		File file=new File(SDPATH+fileName);
		try {
			file.createNewFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
		
	}
	public File creatSDDir(String dirName){
		File dir=new File(SDPATH+dirName);
		dir.mkdir();
		return dir;
		
	}
	public boolean isFileExist(String fileName){
		File file=new File(SDPATH+fileName);
		
		return file.exists();
		
	}
	public File writeSDFromInput(String path,String fileName,InputStream input){
		File file=null;
		OutputStream output=null;
		try{
			creatSDDir(path);
			file=creatSDDir(path+fileName);
			output=new FileOutputStream(file);
			byte buffer[]=new byte[4*1024];
			int temp;
			while((temp=input.read(buffer))!=-1){
				output.write(buffer,0,temp);
				
			}
			output.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				output.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return file;
	}


}
