package com.src.kickgifter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GiftBuyerAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	private ArrayList<String> LIST_GIFTNAME;
	private ArrayList<String> LIST_GIFTAMOUNT;
	private ArrayList<String> LIST_GIFTCRATED;
		
    public GiftBuyerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    
    public GiftBuyerAdapter(Context context, ArrayList<String> mGiftName, ArrayList<String> mGiftAmount , ArrayList<String> mGiftCreated ) {
    	mInflater = LayoutInflater.from(context);
    	LIST_GIFTNAME = mGiftName;
    	LIST_GIFTAMOUNT = mGiftAmount;
    	LIST_GIFTCRATED = mGiftCreated;
    	
    }
    
	public int getCount() {
		// TODO Auto-generated method stub
		return LIST_GIFTNAME.size();
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
            holder.txtGiftName = (TextView) convertView.findViewById(R.id.txt_gift_name);
            holder.txtGiftAmount = (TextView) convertView.findViewById(R.id.txt_gift_amount);
            holder.txtGiftCreated = (TextView) convertView.findViewById(R.id.txt_gift_created);
          
          
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.txtGiftName.setText(LIST_GIFTNAME.get(position));
        holder.txtGiftAmount.setText(LIST_GIFTAMOUNT.get(position));
        holder.txtGiftCreated.setText(LIST_GIFTCRATED.get(position));
        
        return convertView;
    }

    static class ViewHolder {
    	TextView txtGiftName;
        TextView txtGiftAmount;
        TextView txtGiftCreated;
      
    }	

}
