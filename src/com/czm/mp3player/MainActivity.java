package com.czm.mp3player;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ListActivity {
	private static final int UPDATE=1;
	private static final int ABOUT=2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.add(0,UPDATE,1,R.string.MP3List_update);
		menu.add(0,ABOUT,2,R.string.MP3List_about);
		return true;
	}
	class UpdateThread extends Thread{

		@Override
		public void run() {
			String XML=download("http://10.0.2.2:8082/Test/MP3/resources.xml");
			System.out.println("asa");
			System.out.println(XML);
			
		}
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==UPDATE){
			new UpdateThread().start();
		}
		else if(item.getItemId()==ABOUT){
			
		}
		return super.onOptionsItemSelected(item);
	}

	private String download(String url) {
		HttpDownloader downloader=new HttpDownloader();
		String result=downloader.download(url);
		
		return result;
	}
	
}
