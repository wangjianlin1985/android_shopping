package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.ProductCart;
import com.mobileclient.service.ProductCartService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
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

public class ProductCartEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_cartId;
	// 声明用户名下拉框
	private Spinner spinner_memberObj;
	private ArrayAdapter<String> memberObj_adapter;
	private static  String[] memberObj_ShowText  = null;
	private List<MemberInfo> memberInfoList = null;
	/*用户名管理业务逻辑层*/
	private MemberInfoService memberInfoService = new MemberInfoService();
	// 声明商品名称下拉框
	private Spinner spinner_productObj;
	private ArrayAdapter<String> productObj_adapter;
	private static  String[] productObj_ShowText  = null;
	private List<ProductInfo> productInfoList = null;
	/*商品名称管理业务逻辑层*/
	private ProductInfoService productInfoService = new ProductInfoService();
	// 声明商品单价输入框
	private EditText ET_price;
	// 声明商品数量输入框
	private EditText ET_count;
	protected String carmera_path;
	/*要保存的商品购物车信息*/
	ProductCart productCart = new ProductCart();
	/*商品购物车管理业务逻辑层*/
	private ProductCartService productCartService = new ProductCartService();

	private int cartId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.productcart_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑商品购物车信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_cartId = (TextView) findViewById(R.id.TV_cartId);
		spinner_memberObj = (Spinner) findViewById(R.id.Spinner_memberObj);
		// 获取所有的用户名
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
		// 设置图书类别下拉列表的风格
		memberObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_memberObj.setAdapter(memberObj_adapter);
		// 添加事件Spinner事件监听
		spinner_memberObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				productCart.setMemberObj(memberInfoList.get(arg2).getMemberUserName()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_memberObj.setVisibility(View.VISIBLE);
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
				productCart.setProductObj(productInfoList.get(arg2).getProductNo()); 
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
		cartId = extras.getInt("cartId");
		/*单击修改商品购物车按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取商品单价*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(ProductCartEditActivity.this, "商品单价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					productCart.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*验证获取商品数量*/ 
					if(ET_count.getText().toString().equals("")) {
						Toast.makeText(ProductCartEditActivity.this, "商品数量输入不能为空!", Toast.LENGTH_LONG).show();
						ET_count.setFocusable(true);
						ET_count.requestFocus();
						return;	
					}
					productCart.setCount(Integer.parseInt(ET_count.getText().toString()));
					/*调用业务逻辑层上传商品购物车信息*/
					ProductCartEditActivity.this.setTitle("正在更新商品购物车信息，稍等...");
					String result = productCartService.UpdateProductCart(productCart);
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
	    productCart = productCartService.GetProductCart(cartId);
		this.TV_cartId.setText(cartId+"");
		for (int i = 0; i < memberInfoList.size(); i++) {
			if (productCart.getMemberObj().equals(memberInfoList.get(i).getMemberUserName())) {
				this.spinner_memberObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < productInfoList.size(); i++) {
			if (productCart.getProductObj().equals(productInfoList.get(i).getProductNo())) {
				this.spinner_productObj.setSelection(i);
				break;
			}
		}
		this.ET_price.setText(productCart.getPrice() + "");
		this.ET_count.setText(productCart.getCount() + "");
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
