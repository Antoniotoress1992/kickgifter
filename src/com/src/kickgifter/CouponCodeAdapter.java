package com.src.kickgifter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CouponCodeAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	private ArrayList<String> LIST_COUPONCODE;
	private ArrayList<String> LIST_COMPANYNAME;
	private ArrayList<String> LIST_COUPONAMOUNT;
	private ArrayList<String> LIST_COUPONCREATED;	
    public CouponCodeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    
    public CouponCodeAdapter(Context context, ArrayList<String> mCouponCode, ArrayList<String> mCompanyName , ArrayList<String> mCouponAmount , ArrayList<String> mCouponCreated ) {
    	mInflater = LayoutInflater.from(context);
    	LIST_COUPONCODE = mCouponCode;
    	LIST_COMPANYNAME = mCompanyName;
    	LIST_COUPONAMOUNT = mCouponAmount;
    	LIST_COUPONCREATED = mCouponCreated;
    	
    }
    
	public int getCount() {
		// TODO Auto-generated method stub
		return LIST_COUPONCODE.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_gift_buys, null);
            holder = new ViewHolder();
            holder.txtCouponCode = (TextView) convertView.findViewById(R.id.txt_coupon_code);
            holder.txtCompanyName = (TextView) convertView.findViewById(R.id.txt_company_name);
            holder.txtCouponAmount = (TextView) convertView.findViewById(R.id.txt_coupon_amount);
            holder.txtCouponCreated = (TextView) convertView.findViewById(R.id.txt_coupon_created);
          
          
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.txtCouponCode.setText(LIST_COUPONCODE.get(position));
        holder.txtCompanyName.setText(LIST_COMPANYNAME.get(position));
        holder.txtCouponAmount.setText(LIST_COUPONAMOUNT.get(position));
        holder.txtCouponCreated.setText(LIST_COUPONCREATED.get(position));
        return convertView;
    }

    static class ViewHolder {
    	TextView txtCouponCode;
        TextView txtCompanyName;
        TextView txtCouponAmount;
        TextView txtCouponCreated;
      
    }	

}
