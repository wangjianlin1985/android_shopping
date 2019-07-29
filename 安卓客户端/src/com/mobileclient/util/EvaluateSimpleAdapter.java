package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.ProductInfoService;
import com.mobileclient.service.MemberInfoService;
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

public class EvaluateSimpleAdapter extends SimpleAdapter { 
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

    public EvaluateSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.evaluate_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_evaluateId = (TextView)convertView.findViewById(R.id.tv_evaluateId);
	  holder.tv_productObj = (TextView)convertView.findViewById(R.id.tv_productObj);
	  holder.tv_memberObj = (TextView)convertView.findViewById(R.id.tv_memberObj);
	  holder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
	  holder.tv_evaluateTime = (TextView)convertView.findViewById(R.id.tv_evaluateTime);
	  /*设置各个控件的展示内容*/
	  holder.tv_evaluateId.setText("评价编号：" + mData.get(position).get("evaluateId").toString());
	  holder.tv_productObj.setText("商品名称：" + (new ProductInfoService()).GetProductInfo(mData.get(position).get("productObj").toString()).getProductName());
	  holder.tv_memberObj.setText("用户名：" + (new MemberInfoService()).GetMemberInfo(mData.get(position).get("memberObj").toString()).getMemberUserName());
	  holder.tv_content.setText("评价内容：" + mData.get(position).get("content").toString());
	  holder.tv_evaluateTime.setText("评价时间：" + mData.get(position).get("evaluateTime").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_evaluateId;
    	TextView tv_productObj;
    	TextView tv_memberObj;
    	TextView tv_content;
    	TextView tv_evaluateTime;
    }
} 
