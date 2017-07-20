package com.example.shushuweather.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.shushuweather.R;
import com.example.shushuweather.adapter.SetWeatherListView;
import com.example.shushuweather.models.SetWeatherItem;
import com.example.shushuweather.service.AutoUpdateWeather;
import com.example.shushuweather.utils.Utility;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Toast;
/**
 * �������û��
 * @author WangBin
 */
public class SetWeatherActivity extends Activity implements OnClickListener{
	private Button backbtn;//�������ذ�ť
	private ListView set_weather_list;//���������б�
	private SetWeatherListView adapter;//���������б�������
	private List<SetWeatherItem> dataList = new ArrayList<SetWeatherItem>();
	private CheckBox auto_freshweather_ck;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��Ĭ�ϵı�����
		
		setContentView(R.layout.set_weather);
		
		//��ʼ��layout�ؼ�
		backbtn = (Button)findViewById(R.id.backbtn);
		//set_weather_list = (ListView)findViewById(R.id.set_weather_list);
		auto_freshweather_ck = (CheckBox)findViewById(R.id.auto_freshweather_ck);
		
		//��ť�󶨵���¼�
		backbtn.setOnClickListener(this);
		auto_freshweather_ck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(SetWeatherActivity.this).edit();
				if(isChecked)
				{
					if(!Utility.ServiceIsRun(SetWeatherActivity.this, "com.example.shushuweather.service.AutoUpdateWeather"))
					{
						//�Զ����·���δ����-��������
						Intent i= new Intent(SetWeatherActivity.this,AutoUpdateWeather.class);
						
						startService(i);
						
						//д���ʶ
						editor.putBoolean("autorefresh", true);
					}
					
					Toast.makeText(SetWeatherActivity.this, "�Զ��������������ѿ�����", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if(Utility.ServiceIsRun(SetWeatherActivity.this, "com.example.shushuweather.service.AutoUpdateWeather"))
					{
						//�Զ����·���������-�رշ���
						Intent i= new Intent(SetWeatherActivity.this,AutoUpdateWeather.class);
						
						stopService(i);
						
						//д���ʶ
						editor.putBoolean("autorefresh", false);
					}
					
					Toast.makeText(SetWeatherActivity.this, "�Զ��������������ѿ�����", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		//dataList = init_set_weather_list();
		//�б�����
		//adapter = new SetWeatherListView(this, dataList);
		
		//set_weather_list.setAdapter(adapter);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.backbtn:
				//���ص���������
				backToWeather();
				break;

			default:
				break;
		}
	}
	
	//��ʼ�����������б�
	private List init_set_weather_list()
	{	
		SetWeatherItem sItem = new SetWeatherItem();
		sItem.setItemText("�Զ�����");
		sItem.setItemImage(R.drawable.auto_refresh);
		dataList.add(sItem);
		Log.d("dataList", "1");
		return dataList;
	}
	
	//���ص���������
	private void backToWeather()
	{
		Intent i = new Intent(this,WeatherActivity.class);
		startActivity(i);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		backToWeather();//���ص���������
	}
}