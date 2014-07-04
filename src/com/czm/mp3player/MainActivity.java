package com.czm.mp3player;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ListFragment {
	private static final int UPDATE = 1;
	private static final int ABOUT = 2;
	private Handler handler;
	private List<Mp3Info> infos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new UpdateHandler();
		
		new UpdateThread().start();
	}
	
@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	handler = new UpdateHandler();
	
	new UpdateThread().start();
		super.onActivityCreated(savedInstanceState);
	}

	/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.add(0, UPDATE, 1, R.string.MP3List_update);
		menu.add(0, ABOUT, 2, R.string.MP3List_about);
		return true;
	}
*/
	class UpdateThread extends Thread {

		@Override
		public void run() {
			updateListView();

		}

	}

	class UpdateHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			String result = (String) msg.obj;
			if (result.equals("receive")) {
				setListAdapter(simpleAdapter);
			}
			super.handleMessage(msg);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == UPDATE) {
			new UpdateThread().start();
		} else if (item.getItemId() == ABOUT) {

		}
		return super.onOptionsItemSelected(item);
	}

	private SimpleAdapter buildSimpleAdapter(List<Mp3Info> mp3Infos) {
		// 生成一个List对象，并按照SimpleAdapter的标准，将mp3Infos当中的数据添加到List当中去
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext();) {
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mp3_name", mp3Info.getMp3Name());
			map.put("mp3_size", mp3Info.getMp3Size());
			list.add(map);
		}
		// 创建一个SimpleAdapter对象
		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list,
				R.layout.mp3info_item, new String[] { "mp3_name", "mp3_size" },
				new int[] { R.id.mp3_name, R.id.mp3_size });
		// 将这个SimpleAdapter对象设置到ListActivity当中
		return simpleAdapter;
	}

	private void updateListView() {
		// 用户点击了更新列表按钮
		// 下载包含所有Mp3基本信息的xml文件
		String xml = downloadXML("http://10.0.2.2:8082/MP3/resources.xml");
		// 对xml文件进行解析，并将解析的结果放置到Mp3Info对象当中，最后将这些Mp3Info对象放置到List当中
		infos = parse(xml);
		simpleAdapter = buildSimpleAdapter(infos);
		Message msg = handler.obtainMessage();
		msg.obj = "receive";
		handler.sendMessage(msg);

	}

	private SimpleAdapter simpleAdapter;

	private String downloadXML(String urlStr) {
		HttpDownloader httpDownloader = new HttpDownloader();
		String result = httpDownloader.download(urlStr);
		return result;
	}

	private List<Mp3Info> parse(String xmlStr) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		List<Mp3Info> infos = new ArrayList<Mp3Info>();
		try {
			XMLReader xmlReader = saxParserFactory.newSAXParser()
					.getXMLReader();
			Mp3ListContentHandler mp3ListContentHandler = new Mp3ListContentHandler(
					infos);
			xmlReader.setContentHandler(mp3ListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));

			for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
				Mp3Info mp3Info = (Mp3Info) iterator.next();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Mp3Info mp3 = infos.get(position);

		Intent intent = new Intent();
		intent.putExtra("mp3Info", mp3);
		intent.setClass(getActivity(), DownloadService.class);
		getActivity().startService(intent);
		super.onListItemClick(l, v, position, id);
	}

}
