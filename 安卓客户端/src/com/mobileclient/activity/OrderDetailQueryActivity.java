package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.OrderDetail;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;

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
public class OrderDetailQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明定单编号下拉框
	private Spinner spinner_orderObj;
	private ArrayAdapter<String> orderObj_adapter;
	private static  String[] orderObj_ShowText  = null;
	private List<OrderInfo> orderInfoList = null; 
	/*订单信息管理业务逻辑层*/
	private OrderInfoService orderInfoService = new OrderInfoService();
	// 声明商品名称下拉框
	private Spinner spinner_productObj;
	private ArrayAdapter<String> productObj_adapter;
	private static  String[] productObj_ShowText  = null;
	private List<ProductInfo> productInfoList = null; 
	/*商品信息管理业务逻辑层*/
	private ProductInfoService productInfoService = new ProductInfoService();
	/*查询过滤条件保存到这个对象中*/
	private OrderDetail queryConditionOrderDetail = new OrderDetail();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.orderdetail_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置订单明细信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_orderObj = (Spinner) findViewById(R.id.Spinner_orderObj);
		// 获取所有的订单信息
		try {
			orderInfoList = orderInfoService.QueryOrderInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int orderInfoCount = orderInfoList.size();
		orderObj_ShowText = new String[orderInfoCount+1];
		orderObj_ShowText[0] = "不限制";
		for(int i=1;i<=orderInfoCount;i++) { 
			orderObj_ShowText[i] = orderInfoList.get(i-1).getOrderNo();
		} 
		// 将可选内容与ArrayAdapter连接起来
		orderObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, orderObj_ShowText);
		// 设置定单编号下拉列表的风格
		orderObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_orderObj.setAdapter(orderObj_adapter);
		// 添加事件Spinner事件监听
		spinner_orderObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderDetail.setOrderObj(orderInfoList.get(arg2-1).getOrderNo()); 
				else
					queryConditionOrderDetail.setOrderObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_orderObj.setVisibility(View.VISIBLE);
		spinner_productObj = (Spinner) findViewById(R.id.Spinner_productObj);
		// 获取所有的商品信息
		try {
			productInfoList = productInfoService.QueryProductInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int productInfoCount = productInfoList.size();
		productObj_ShowText = new String[productInfoCount+1];
		productObj_ShowText[0] = "不限制";
		for(int i=1;i<=productInfoCount;i++) { 
			productObj_ShowText[i] = productInfoList.get(i-1).getProductName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		productObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, productObj_ShowText);
		// 设置商品名称下拉列表的风格
		productObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_productObj.setAdapter(productObj_adapter);
		// 添加事件Spinner事件监听
		spinner_productObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderDetail.setProductObj(productInfoList.get(arg2-1).getProductNo()); 
				else
					queryConditionOrderDetail.setProductObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_productObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionOrderDetail", queryConditionOrderDetail);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
