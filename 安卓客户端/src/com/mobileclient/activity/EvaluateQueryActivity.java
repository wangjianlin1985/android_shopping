package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Evaluate;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;

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
public class EvaluateQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明商品名称下拉框
	private Spinner spinner_productObj;
	private ArrayAdapter<String> productObj_adapter;
	private static  String[] productObj_ShowText  = null;
	private List<ProductInfo> productInfoList = null; 
	/*商品信息管理业务逻辑层*/
	private ProductInfoService productInfoService = new ProductInfoService();
	// 声明用户名下拉框
	private Spinner spinner_memberObj;
	private ArrayAdapter<String> memberObj_adapter;
	private static  String[] memberObj_ShowText  = null;
	private List<MemberInfo> memberInfoList = null; 
	/*会员信息管理业务逻辑层*/
	private MemberInfoService memberInfoService = new MemberInfoService();
	/*查询过滤条件保存到这个对象中*/
	private Evaluate queryConditionEvaluate = new Evaluate();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.evaluate_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置商品评价查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
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
					queryConditionEvaluate.setProductObj(productInfoList.get(arg2-1).getProductNo()); 
				else
					queryConditionEvaluate.setProductObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_productObj.setVisibility(View.VISIBLE);
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
		// 设置用户名下拉列表的风格
		memberObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_memberObj.setAdapter(memberObj_adapter);
		// 添加事件Spinner事件监听
		spinner_memberObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionEvaluate.setMemberObj(memberInfoList.get(arg2-1).getMemberUserName()); 
				else
					queryConditionEvaluate.setMemberObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_memberObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionEvaluate", queryConditionEvaluate);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
