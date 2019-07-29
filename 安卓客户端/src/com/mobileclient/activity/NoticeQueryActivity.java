package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class NoticeQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明公告标题输入框
	private EditText ET_title;
	// 发布日期控件
	private DatePicker dp_publishDate;
	private CheckBox cb_publishDate;
	/*查询过滤条件保存到这个对象中*/
	private Notice queryConditionNotice = new Notice();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.notice_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置系统公告查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_title = (EditText) findViewById(R.id.ET_title);
		dp_publishDate = (DatePicker) findViewById(R.id.dp_publishDate);
		cb_publishDate = (CheckBox) findViewById(R.id.cb_publishDate);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionNotice.setTitle(ET_title.getText().toString());
					if(cb_publishDate.isChecked()) {
						/*获取发布日期*/
						Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
						queryConditionNotice.setPublishDate(new Timestamp(publishDate.getTime()));
					} else {
						queryConditionNotice.setPublishDate(null);
					} 
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionNotice", queryConditionNotice);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
