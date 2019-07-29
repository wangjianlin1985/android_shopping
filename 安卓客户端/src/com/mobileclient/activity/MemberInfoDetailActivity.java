package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.MemberInfo;
import com.mobileclient.service.MemberInfoService;
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
public class MemberInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明会员用户名控件
	private TextView TV_memberUserName;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明真实姓名控件
	private TextView TV_realName;
	// 声明性别控件
	private TextView TV_sex;
	// 声明出生日期控件
	private TextView TV_birthday;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明联系邮箱控件
	private TextView TV_email;
	// 声明联系qq控件
	private TextView TV_qq;
	// 声明家庭地址控件
	private TextView TV_address;
	// 声明会员照片图片框
	private ImageView iv_photo;
	/* 要保存的会员信息信息 */
	MemberInfo memberInfo = new MemberInfo(); 
	/* 会员信息管理业务逻辑层 */
	private MemberInfoService memberInfoService = new MemberInfoService();
	private String memberUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.memberinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看会员信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_memberUserName = (TextView) findViewById(R.id.TV_memberUserName);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_realName = (TextView) findViewById(R.id.TV_realName);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_birthday = (TextView) findViewById(R.id.TV_birthday);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_email = (TextView) findViewById(R.id.TV_email);
		TV_qq = (TextView) findViewById(R.id.TV_qq);
		TV_address = (TextView) findViewById(R.id.TV_address);
		iv_photo = (ImageView) findViewById(R.id.iv_photo); 
		Bundle extras = this.getIntent().getExtras();
		memberUserName = extras.getString("memberUserName");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MemberInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    memberInfo = memberInfoService.GetMemberInfo(memberUserName); 
		this.TV_memberUserName.setText(memberInfo.getMemberUserName());
		this.TV_password.setText(memberInfo.getPassword());
		this.TV_realName.setText(memberInfo.getRealName());
		this.TV_sex.setText(memberInfo.getSex());
		Date birthday = new Date(memberInfo.getBirthday().getTime());
		String birthdayStr = (birthday.getYear() + 1900) + "-" + (birthday.getMonth()+1) + "-" + birthday.getDate();
		this.TV_birthday.setText(birthdayStr);
		this.TV_telephone.setText(memberInfo.getTelephone());
		this.TV_email.setText(memberInfo.getEmail());
		this.TV_qq.setText(memberInfo.getQq());
		this.TV_address.setText(memberInfo.getAddress());
		byte[] photo_data = null;
		try {
			// 获取图片数据
			photo_data = ImageService.getImage(HttpUtil.BASE_URL + memberInfo.getPhoto());
			Bitmap photo = BitmapFactory.decodeByteArray(photo_data, 0,photo_data.length);
			this.iv_photo.setImageBitmap(photo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
