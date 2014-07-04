package com.czm.mp3player;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		Mp3Info mp3=(Mp3Info)intent.getSerializableExtra("mp3Info");
		new DownloadThread(mp3).start();
		return super.onStartCommand(intent, flags, startId);
	}
	class DownloadThread extends Thread{
		private Mp3Info mp3;
		public DownloadThread(Mp3Info mp3){
			this.mp3=mp3;
		}
		@Override
		public void run() {
			String url="http://10.0.2.2:8082/MP3/"+mp3.getMp3Name();
			HttpDownloader download=new HttpDownloader();
			int result=download.downFile(url, "mp3/", mp3.getMp3Name());
			String resultMessage=null;
			if(result==-1){
				resultMessage="�ļ�����ʧ��";
			}else if(result==0){
				resultMessage="�ļ��Ѵ��ڣ�����Ҫ�ظ�����";
			}else if(result==1){
				resultMessage="�ļ����سɹ�";
			}
			
		}
		
	}

}
