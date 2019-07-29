package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.ProductInfo;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.domain.ProductClass;
import com.mobileclient.service.ProductClassService;
import com.mobileclient.domain.YesOrNo;
import com.mobileclient.service.YesOrNoService;
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
public class ProductInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明商品编号控件
	private TextView TV_productNo;
	// 声明商品类别控件
	private TextView TV_productClassObj;
	// 声明商品名称控件
	private TextView TV_productName;
	// 声明商品图片图片框
	private ImageView iv_productPhoto;
	// 声明商品单价控件
	private TextView TV_productPrice;
	// 声明商品库存控件
	private TextView TV_productCount;
	// 声明是否推荐控件
	private TextView TV_recommendFlag;
	// 声明人气值控件
	private TextView TV_hotNum;
	// 声明上架日期控件
	private TextView TV_onlineDate;
	/* 要保存的商品信息信息 */
	ProductInfo productInfo = new ProductInfo(); 
	/* 商品信息管理业务逻辑层 */
	private ProductInfoService productInfoService = new ProductInfoService();
	private ProductClassService productClassService = new ProductClassService();
	private YesOrNoService yesOrNoService = new YesOrNoService();
	private String productNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.productinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看商品信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_productNo = (TextView) findViewById(R.id.TV_productNo);
		TV_productClassObj = (TextView) findViewById(R.id.TV_productClassObj);
		TV_productName = (TextView) findViewById(R.id.TV_productName);
		iv_productPhoto = (ImageView) findViewById(R.id.iv_productPhoto); 
		TV_productPrice = (TextView) findViewById(R.id.TV_productPrice);
		TV_productCount = (TextView) findViewById(R.id.TV_productCount);
		TV_recommendFlag = (TextView) findViewById(R.id.TV_recommendFlag);
		TV_hotNum = (TextView) findViewById(R.id.TV_hotNum);
		TV_onlineDate = (TextView) findViewById(R.id.TV_onlineDate);
		Bundle extras = this.getIntent().getExtras();
		productNo = extras.getString("productNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ProductInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    productInfo = productInfoService.GetProductInfo(productNo); 
		this.TV_productNo.setText(productInfo.getProductNo());
		ProductClass productClassObj = productClassService.GetProductClass(productInfo.getProductClassObj());
		this.TV_productClassObj.setText(productClassObj.getClassName());
		this.TV_productName.setText(productInfo.getProductName());
		byte[] productPhoto_data = null;
		try {
			// 获取图片数据
			productPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + productInfo.getProductPhoto());
			Bitmap productPhoto = BitmapFactory.decodeByteArray(productPhoto_data, 0,productPhoto_data.length);
			this.iv_productPhoto.setImageBitmap(productPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_productPrice.setText(productInfo.getProductPrice() + "");
		this.TV_productCount.setText(productInfo.getProductCount() + "");
		YesOrNo recommendFlag = yesOrNoService.GetYesOrNo(productInfo.getRecommendFlag());
		this.TV_recommendFlag.setText(recommendFlag.getName());
		this.TV_hotNum.setText(productInfo.getHotNum() + "");
		Date onlineDate = new Date(productInfo.getOnlineDate().getTime());
		String onlineDateStr = (onlineDate.getYear() + 1900) + "-" + (onlineDate.getMonth()+1) + "-" + onlineDate.getDate();
		this.TV_onlineDate.setText(onlineDateStr);
	} 
}
