package com.czm.mp3player;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TabHost;

public class TabActivitys extends FragmentActivity{
	private static final int UPDATE = 1;
	private static final int ABOUT = 2;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.fragment);
	        
	        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
	        tabHost.setup();
	        Resources re=getResources();
	        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("tab1",re.getDrawable(android.R.drawable.stat_sys_download)).setContent(R.id.tab1));
	        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2",re.getDrawable(android.R.drawable.stat_sys_download)).setContent(R.id.tab2));
	    }

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.activity_main, menu);
	        menu.add(0, UPDATE, 1, R.string.MP3List_update);
			menu.add(0, ABOUT, 2, R.string.MP3List_about);
			return true;
	    }

}
