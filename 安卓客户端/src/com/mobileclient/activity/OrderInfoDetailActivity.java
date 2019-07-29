package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
import com.mobileclient.domain.OrderState;
import com.mobileclient.service.OrderStateService;
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
public class OrderInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明订单编号控件
	private TextView TV_orderNo;
	// 声明下单会员控件
	private TextView TV_memberObj;
	// 声明下单时间控件
	private TextView TV_orderTime;
	// 声明订单总金额控件
	private TextView TV_totalMoney;
	// 声明订单状态控件
	private TextView TV_orderStateObj;
	// 声明付款方式控件
	private TextView TV_buyWay;
	// 声明收货人姓名控件
	private TextView TV_realName;
	// 声明收货人电话控件
	private TextView TV_telphone;
	// 声明邮政编码控件
	private TextView TV_postcode;
	// 声明收货地址控件
	private TextView TV_address;
	// 声明附加信息控件
	private TextView TV_memo;
	/* 要保存的订单信息信息 */
	OrderInfo orderInfo = new OrderInfo(); 
	/* 订单信息管理业务逻辑层 */
	private OrderInfoService orderInfoService = new OrderInfoService();
	private MemberInfoService memberInfoService = new MemberInfoService();
	private OrderStateService orderStateService = new OrderStateService();
	private String orderNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看订单信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_orderNo = (TextView) findViewById(R.id.TV_orderNo);
		TV_memberObj = (TextView) findViewById(R.id.TV_memberObj);
		TV_orderTime = (TextView) findViewById(R.id.TV_orderTime);
		TV_totalMoney = (TextView) findViewById(R.id.TV_totalMoney);
		TV_orderStateObj = (TextView) findViewById(R.id.TV_orderStateObj);
		TV_buyWay = (TextView) findViewById(R.id.TV_buyWay);
		TV_realName = (TextView) findViewById(R.id.TV_realName);
		TV_telphone = (TextView) findViewById(R.id.TV_telphone);
		TV_postcode = (TextView) findViewById(R.id.TV_postcode);
		TV_address = (TextView) findViewById(R.id.TV_address);
		TV_memo = (TextView) findViewById(R.id.TV_memo);
		Bundle extras = this.getIntent().getExtras();
		orderNo = extras.getString("orderNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    orderInfo = orderInfoService.GetOrderInfo(orderNo); 
		this.TV_orderNo.setText(orderInfo.getOrderNo());
		MemberInfo memberObj = memberInfoService.GetMemberInfo(orderInfo.getMemberObj());
		this.TV_memberObj.setText(memberObj.getMemberUserName());
		this.TV_orderTime.setText(orderInfo.getOrderTime());
		this.TV_totalMoney.setText(orderInfo.getTotalMoney() + "");
		OrderState orderStateObj = orderStateService.GetOrderState(orderInfo.getOrderStateObj());
		this.TV_orderStateObj.setText(orderStateObj.getStateName());
		this.TV_buyWay.setText(orderInfo.getBuyWay());
		this.TV_realName.setText(orderInfo.getRealName());
		this.TV_telphone.setText(orderInfo.getTelphone());
		this.TV_postcode.setText(orderInfo.getPostcode());
		this.TV_address.setText(orderInfo.getAddress());
		this.TV_memo.setText(orderInfo.getMemo());
	} 
}
