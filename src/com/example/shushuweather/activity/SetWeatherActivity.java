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
 * 天气设置活动类
 * @author WangBin
 */
public class SetWeatherActivity extends Activity implements OnClickListener{
	private Button backbtn;//顶部返回按钮
	private ListView set_weather_list;//天气配置列表
	private SetWeatherListView adapter;//天气配置列表适配器
	private List<SetWeatherItem> dataList = new ArrayList<SetWeatherItem>();
	private CheckBox auto_freshweather_ck;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉默认的标题栏
		
		setContentView(R.layout.set_weather);
		
		//初始化layout控件
		backbtn = (Button)findViewById(R.id.backbtn);
		//set_weather_list = (ListView)findViewById(R.id.set_weather_list);
		auto_freshweather_ck = (CheckBox)findViewById(R.id.auto_freshweather_ck);
		
		//按钮绑定点击事件
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
						//自动更新服务未启动-开启服务
						Intent i= new Intent(SetWeatherActivity.this,AutoUpdateWeather.class);
						
						startService(i);
						
						//写入标识
						editor.putBoolean("autorefresh", true);
					}
					
					Toast.makeText(SetWeatherActivity.this, "自动更新天气服务已开启！", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if(Utility.ServiceIsRun(SetWeatherActivity.this, "com.example.shushuweather.service.AutoUpdateWeather"))
					{
						//自动更新服务已启动-关闭服务
						Intent i= new Intent(SetWeatherActivity.this,AutoUpdateWeather.class);
						
						stopService(i);
						
						//写入标识
						editor.putBoolean("autorefresh", false);
					}
					
					Toast.makeText(SetWeatherActivity.this, "自动更新天气服务已开启！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		//dataList = init_set_weather_list();
		//列表配置
		//adapter = new SetWeatherListView(this, dataList);
		
		//set_weather_list.setAdapter(adapter);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.backbtn:
				//返回到天气界面
				backToWeather();
				break;

			default:
				break;
		}
	}
	
	//初始化天气配置列表
	private List init_set_weather_list()
	{	
		SetWeatherItem sItem = new SetWeatherItem();
		sItem.setItemText("自动更新");
		sItem.setItemImage(R.drawable.auto_refresh);
		dataList.add(sItem);
		Log.d("dataList", "1");
		return dataList;
	}
	
	//返回到天气界面
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
		backToWeather();//返回到天气界面
	}
}
