package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.OrderDetail;
import com.mobileclient.service.OrderDetailService;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class OrderDetailDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_detailId;
	// 声明定单编号控件
	private TextView TV_orderObj;
	// 声明商品名称控件
	private TextView TV_productObj;
	// 声明商品单价控件
	private TextView TV_price;
	// 声明订购数量控件
	private TextView TV_count;
	/* 要保存的订单明细信息信息 */
	OrderDetail orderDetail = new OrderDetail(); 
	/* 订单明细信息管理业务逻辑层 */
	private OrderDetailService orderDetailService = new OrderDetailService();
	private OrderInfoService orderInfoService = new OrderInfoService();
	private ProductInfoService productInfoService = new ProductInfoService();
	private int detailId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderdetail_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看订单明细信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_detailId = (TextView) findViewById(R.id.TV_detailId);
		TV_orderObj = (TextView) findViewById(R.id.TV_orderObj);
		TV_productObj = (TextView) findViewById(R.id.TV_productObj);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_count = (TextView) findViewById(R.id.TV_count);
		Bundle extras = this.getIntent().getExtras();
		detailId = extras.getInt("detailId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderDetailDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    orderDetail = orderDetailService.GetOrderDetail(detailId); 
		this.TV_detailId.setText(orderDetail.getDetailId() + "");
		OrderInfo orderObj = orderInfoService.GetOrderInfo(orderDetail.getOrderObj());
		this.TV_orderObj.setText(orderObj.getOrderNo());
		ProductInfo productObj = productInfoService.GetProductInfo(orderDetail.getProductObj());
		this.TV_productObj.setText(productObj.getProductName());
		this.TV_price.setText(orderDetail.getPrice() + "");
		this.TV_count.setText(orderDetail.getCount() + "");
	} 
}
