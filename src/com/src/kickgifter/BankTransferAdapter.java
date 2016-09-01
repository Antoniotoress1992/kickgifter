package com.src.kickgifter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BankTransferAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	private ArrayList<String> LIST_BANKINFO;
	private ArrayList<String> LIST_BANKAMOUNT;
	private ArrayList<String> LIST_BANKCREATED;
		
    public BankTransferAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    
    public BankTransferAdapter(Context context, ArrayList<String> mBankInfo, ArrayList<String> mBankAmount , ArrayList<String> mBankCreated ) {
    	mInflater = LayoutInflater.from(context);
    	LIST_BANKINFO = mBankInfo;
    	LIST_BANKAMOUNT = mBankAmount;
    	LIST_BANKCREATED = mBankCreated;
    	
    }
    
	public int getCount() {
		// TODO Auto-generated method stub
		return LIST_BANKINFO.size();
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
            holder.txtBankInfo = (TextView) convertView.findViewById(R.id.txt_bank_info);
            holder.txtBankAmount = (TextView) convertView.findViewById(R.id.txt_bank_amount);
            holder.txtBankCreated = (TextView) convertView.findViewById(R.id.txt_bank_created);
          
          
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.txtBankInfo.setText(LIST_BANKINFO.get(position));
        holder.txtBankAmount.setText(LIST_BANKAMOUNT.get(position));
        holder.txtBankCreated.setText(LIST_BANKCREATED.get(position));
        
        return convertView;
    }

    static class ViewHolder {
    	TextView txtBankInfo;
        TextView txtBankAmount;
        TextView txtBankCreated;
      
    }	

}
