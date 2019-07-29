package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;

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
public class OrderInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明订单编号输入框
	private EditText ET_orderNo;
	// 声明下单会员下拉框
	private Spinner spinner_memberObj;
	private ArrayAdapter<String> memberObj_adapter;
	private static  String[] memberObj_ShowText  = null;
	private List<MemberInfo> memberInfoList = null; 
	/*会员信息管理业务逻辑层*/
	private MemberInfoService memberInfoService = new MemberInfoService();
	// 声明下单时间输入框
	private EditText ET_orderTime;
	// 声明订单状态下拉框
	private Spinner spinner_orderStateObj;
	private ArrayAdapter<String> orderStateObj_adapter;
	private static  String[] orderStateObj_ShowText  = null;
	private List<OrderState> orderStateList = null; 
	/*订单状态信息管理业务逻辑层*/
	private OrderStateService orderStateService = new OrderStateService();
	// 声明收货人姓名输入框
	private EditText ET_realName;
	// 声明收货人电话输入框
	private EditText ET_telphone;
	/*查询过滤条件保存到这个对象中*/
	private OrderInfo queryConditionOrderInfo = new OrderInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置订单信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_orderNo = (EditText) findViewById(R.id.ET_orderNo);
		spinner_memberObj = (Spinner) findViewById(R.id.Spinner_memberObj);
		// 获取所有的会员信息
		try {
			memberInfoList = memberInfoService.QueryMemberInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int memberInfoCount = memberInfoList.size();
		memberObj_ShowText = new String[memberInfoCount+1];
		memberObj_ShowText[0] = "不限制";
		for(int i=1;i<=memberInfoCount;i++) { 
			memberObj_ShowText[i] = memberInfoList.get(i-1).getMemberUserName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		memberObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, memberObj_ShowText);
		// 设置下单会员下拉列表的风格
		memberObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_memberObj.setAdapter(memberObj_adapter);
		// 添加事件Spinner事件监听
		spinner_memberObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setMemberObj(memberInfoList.get(arg2-1).getMemberUserName()); 
				else
					queryConditionOrderInfo.setMemberObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_memberObj.setVisibility(View.VISIBLE);
		ET_orderTime = (EditText) findViewById(R.id.ET_orderTime);
		spinner_orderStateObj = (Spinner) findViewById(R.id.Spinner_orderStateObj);
		// 获取所有的订单状态信息
		try {
			orderStateList = orderStateService.QueryOrderState(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int orderStateCount = orderStateList.size();
		orderStateObj_ShowText = new String[orderStateCount+1];
		orderStateObj_ShowText[0] = "不限制";
		for(int i=1;i<=orderStateCount;i++) { 
			orderStateObj_ShowText[i] = orderStateList.get(i-1).getStateName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		orderStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderStateObj_ShowText);
		// 设置订单状态下拉列表的风格
		orderStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_orderStateObj.setAdapter(orderStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_orderStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setOrderStateObj(orderStateList.get(arg2-1).getStateId()); 
				else
					queryConditionOrderInfo.setOrderStateObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_orderStateObj.setVisibility(View.VISIBLE);
		ET_realName = (EditText) findViewById(R.id.ET_realName);
		ET_telphone = (EditText) findViewById(R.id.ET_telphone);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionOrderInfo.setOrderNo(ET_orderNo.getText().toString());
					queryConditionOrderInfo.setOrderTime(ET_orderTime.getText().toString());
					queryConditionOrderInfo.setRealName(ET_realName.getText().toString());
					queryConditionOrderInfo.setTelphone(ET_telphone.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionOrderInfo", queryConditionOrderInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
