package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.OrderDetail;
import com.mobileclient.service.OrderDetailService;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class OrderDetailEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_detailId;
	// 声明定单编号下拉框
	private Spinner spinner_orderObj;
	private ArrayAdapter<String> orderObj_adapter;
	private static  String[] orderObj_ShowText  = null;
	private List<OrderInfo> orderInfoList = null;
	/*定单编号管理业务逻辑层*/
	private OrderInfoService orderInfoService = new OrderInfoService();
	// 声明商品名称下拉框
	private Spinner spinner_productObj;
	private ArrayAdapter<String> productObj_adapter;
	private static  String[] productObj_ShowText  = null;
	private List<ProductInfo> productInfoList = null;
	/*商品名称管理业务逻辑层*/
	private ProductInfoService productInfoService = new ProductInfoService();
	// 声明商品单价输入框
	private EditText ET_price;
	// 声明订购数量输入框
	private EditText ET_count;
	protected String carmera_path;
	/*要保存的订单明细信息信息*/
	OrderDetail orderDetail = new OrderDetail();
	/*订单明细信息管理业务逻辑层*/
	private OrderDetailService orderDetailService = new OrderDetailService();

	private int detailId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.orderdetail_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑订单明细信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_detailId = (TextView) findViewById(R.id.TV_detailId);
		spinner_orderObj = (Spinner) findViewById(R.id.Spinner_orderObj);
		// 获取所有的定单编号
		try {
			orderInfoList = orderInfoService.QueryOrderInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int orderInfoCount = orderInfoList.size();
		orderObj_ShowText = new String[orderInfoCount];
		for(int i=0;i<orderInfoCount;i++) { 
			orderObj_ShowText[i] = orderInfoList.get(i).getOrderNo();
		}
		// 将可选内容与ArrayAdapter连接起来
		orderObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderObj_ShowText);
		// 设置图书类别下拉列表的风格
		orderObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_orderObj.setAdapter(orderObj_adapter);
		// 添加事件Spinner事件监听
		spinner_orderObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderDetail.setOrderObj(orderInfoList.get(arg2).getOrderNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_orderObj.setVisibility(View.VISIBLE);
		spinner_productObj = (Spinner) findViewById(R.id.Spinner_productObj);
		// 获取所有的商品名称
		try {
			productInfoList = productInfoService.QueryProductInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int productInfoCount = productInfoList.size();
		productObj_ShowText = new String[productInfoCount];
		for(int i=0;i<productInfoCount;i++) { 
			productObj_ShowText[i] = productInfoList.get(i).getProductName();
		}
		// 将可选内容与ArrayAdapter连接起来
		productObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, productObj_ShowText);
		// 设置图书类别下拉列表的风格
		productObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_productObj.setAdapter(productObj_adapter);
		// 添加事件Spinner事件监听
		spinner_productObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderDetail.setProductObj(productInfoList.get(arg2).getProductNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_productObj.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_count = (EditText) findViewById(R.id.ET_count);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		detailId = extras.getInt("detailId");
		/*单击修改订单明细信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取商品单价*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(OrderDetailEditActivity.this, "商品单价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					orderDetail.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*验证获取订购数量*/ 
					if(ET_count.getText().toString().equals("")) {
						Toast.makeText(OrderDetailEditActivity.this, "订购数量输入不能为空!", Toast.LENGTH_LONG).show();
						ET_count.setFocusable(true);
						ET_count.requestFocus();
						return;	
					}
					orderDetail.setCount(Integer.parseInt(ET_count.getText().toString()));
					/*调用业务逻辑层上传订单明细信息信息*/
					OrderDetailEditActivity.this.setTitle("正在更新订单明细信息信息，稍等...");
					String result = orderDetailService.UpdateOrderDetail(orderDetail);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    orderDetail = orderDetailService.GetOrderDetail(detailId);
		this.TV_detailId.setText(detailId+"");
		for (int i = 0; i < orderInfoList.size(); i++) {
			if (orderDetail.getOrderObj().equals(orderInfoList.get(i).getOrderNo())) {
				this.spinner_orderObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < productInfoList.size(); i++) {
			if (orderDetail.getProductObj().equals(productInfoList.get(i).getProductNo())) {
				this.spinner_productObj.setSelection(i);
				break;
			}
		}
		this.ET_price.setText(orderDetail.getPrice() + "");
		this.ET_count.setText(orderDetail.getCount() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
