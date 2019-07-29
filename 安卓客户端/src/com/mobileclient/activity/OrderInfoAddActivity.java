package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class OrderInfoAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明订单编号输入框
	private EditText ET_orderNo;
	// 声明下单会员下拉框
	private Spinner spinner_memberObj;
	private ArrayAdapter<String> memberObj_adapter;
	private static  String[] memberObj_ShowText  = null;
	private List<MemberInfo> memberInfoList = null;
	/*下单会员管理业务逻辑层*/
	private MemberInfoService memberInfoService = new MemberInfoService();
	// 声明下单时间输入框
	private EditText ET_orderTime;
	// 声明订单总金额输入框
	private EditText ET_totalMoney;
	// 声明订单状态下拉框
	private Spinner spinner_orderStateObj;
	private ArrayAdapter<String> orderStateObj_adapter;
	private static  String[] orderStateObj_ShowText  = null;
	private List<OrderState> orderStateList = null;
	/*订单状态管理业务逻辑层*/
	private OrderStateService orderStateService = new OrderStateService();
	// 声明付款方式输入框
	private EditText ET_buyWay;
	// 声明收货人姓名输入框
	private EditText ET_realName;
	// 声明收货人电话输入框
	private EditText ET_telphone;
	// 声明邮政编码输入框
	private EditText ET_postcode;
	// 声明收货地址输入框
	private EditText ET_address;
	// 声明附加信息输入框
	private EditText ET_memo;
	protected String carmera_path;
	/*要保存的订单信息信息*/
	OrderInfo orderInfo = new OrderInfo();
	/*订单信息管理业务逻辑层*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加订单信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_orderNo = (EditText) findViewById(R.id.ET_orderNo);
		spinner_memberObj = (Spinner) findViewById(R.id.Spinner_memberObj);
		// 获取所有的下单会员
		try {
			memberInfoList = memberInfoService.QueryMemberInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int memberInfoCount = memberInfoList.size();
		memberObj_ShowText = new String[memberInfoCount];
		for(int i=0;i<memberInfoCount;i++) { 
			memberObj_ShowText[i] = memberInfoList.get(i).getMemberUserName();
		}
		// 将可选内容与ArrayAdapter连接起来
		memberObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, memberObj_ShowText);
		// 设置下拉列表的风格
		memberObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_memberObj.setAdapter(memberObj_adapter);
		// 添加事件Spinner事件监听
		spinner_memberObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setMemberObj(memberInfoList.get(arg2).getMemberUserName()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_memberObj.setVisibility(View.VISIBLE);
		ET_orderTime = (EditText) findViewById(R.id.ET_orderTime);
		ET_totalMoney = (EditText) findViewById(R.id.ET_totalMoney);
		spinner_orderStateObj = (Spinner) findViewById(R.id.Spinner_orderStateObj);
		// 获取所有的订单状态
		try {
			orderStateList = orderStateService.QueryOrderState(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int orderStateCount = orderStateList.size();
		orderStateObj_ShowText = new String[orderStateCount];
		for(int i=0;i<orderStateCount;i++) { 
			orderStateObj_ShowText[i] = orderStateList.get(i).getStateName();
		}
		// 将可选内容与ArrayAdapter连接起来
		orderStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderStateObj_ShowText);
		// 设置下拉列表的风格
		orderStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_orderStateObj.setAdapter(orderStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_orderStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setOrderStateObj(orderStateList.get(arg2).getStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_orderStateObj.setVisibility(View.VISIBLE);
		ET_buyWay = (EditText) findViewById(R.id.ET_buyWay);
		ET_realName = (EditText) findViewById(R.id.ET_realName);
		ET_telphone = (EditText) findViewById(R.id.ET_telphone);
		ET_postcode = (EditText) findViewById(R.id.ET_postcode);
		ET_address = (EditText) findViewById(R.id.ET_address);
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加订单信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取订单编号*/
					if(ET_orderNo.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "订单编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderNo.setFocusable(true);
						ET_orderNo.requestFocus();
						return;
					}
					orderInfo.setOrderNo(ET_orderNo.getText().toString());
					/*验证获取下单时间*/ 
					if(ET_orderTime.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "下单时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderTime.setFocusable(true);
						ET_orderTime.requestFocus();
						return;	
					}
					orderInfo.setOrderTime(ET_orderTime.getText().toString());
					/*验证获取订单总金额*/ 
					if(ET_totalMoney.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "订单总金额输入不能为空!", Toast.LENGTH_LONG).show();
						ET_totalMoney.setFocusable(true);
						ET_totalMoney.requestFocus();
						return;	
					}
					orderInfo.setTotalMoney(Float.parseFloat(ET_totalMoney.getText().toString()));
					/*验证获取付款方式*/ 
					if(ET_buyWay.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "付款方式输入不能为空!", Toast.LENGTH_LONG).show();
						ET_buyWay.setFocusable(true);
						ET_buyWay.requestFocus();
						return;	
					}
					orderInfo.setBuyWay(ET_buyWay.getText().toString());
					/*验证获取收货人姓名*/ 
					if(ET_realName.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "收货人姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_realName.setFocusable(true);
						ET_realName.requestFocus();
						return;	
					}
					orderInfo.setRealName(ET_realName.getText().toString());
					/*验证获取收货人电话*/ 
					if(ET_telphone.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "收货人电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telphone.setFocusable(true);
						ET_telphone.requestFocus();
						return;	
					}
					orderInfo.setTelphone(ET_telphone.getText().toString());
					/*验证获取邮政编码*/ 
					if(ET_postcode.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "邮政编码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_postcode.setFocusable(true);
						ET_postcode.requestFocus();
						return;	
					}
					orderInfo.setPostcode(ET_postcode.getText().toString());
					/*验证获取收货地址*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "收货地址输入不能为空!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					orderInfo.setAddress(ET_address.getText().toString());
					/*验证获取附加信息*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(OrderInfoAddActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					orderInfo.setMemo(ET_memo.getText().toString());
					/*调用业务逻辑层上传订单信息信息*/
					OrderInfoAddActivity.this.setTitle("正在上传订单信息信息，稍等...");
					String result = orderInfoService.AddOrderInfo(orderInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
