package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.MemberInfoService;
import com.mobileclient.service.ProductInfoService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class ProductCartSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public ProductCartSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.productcart_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_cartId = (TextView)convertView.findViewById(R.id.tv_cartId);
	  holder.tv_memberObj = (TextView)convertView.findViewById(R.id.tv_memberObj);
	  holder.tv_productObj = (TextView)convertView.findViewById(R.id.tv_productObj);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.tv_count = (TextView)convertView.findViewById(R.id.tv_count);
	  /*设置各个控件的展示内容*/
	  holder.tv_cartId.setText("记录编号：" + mData.get(position).get("cartId").toString());
	  holder.tv_memberObj.setText("用户名：" + (new MemberInfoService()).GetMemberInfo(mData.get(position).get("memberObj").toString()).getMemberUserName());
	  holder.tv_productObj.setText("商品名称：" + (new ProductInfoService()).GetProductInfo(mData.get(position).get("productObj").toString()).getProductName());
	  holder.tv_price.setText("商品单价：" + mData.get(position).get("price").toString());
	  holder.tv_count.setText("商品数量：" + mData.get(position).get("count").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_cartId;
    	TextView tv_memberObj;
    	TextView tv_productObj;
    	TextView tv_price;
    	TextView tv_count;
    }
} 
