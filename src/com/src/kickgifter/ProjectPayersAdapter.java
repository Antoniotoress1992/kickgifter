package com.src.kickgifter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProjectPayersAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	private ArrayList<String> LIST_TEL;
	private ArrayList<String> LIST_AMOUNT;
	private ArrayList<String> LIST_INVITED;
	private ArrayList<String> LIST_PAID;
	private ArrayList<String> LIST_NAME;
	

	
    public ProjectPayersAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    
    public ProjectPayersAdapter(Context context, ArrayList<String> mInvitorsTel, ArrayList<String> mInvitorsName , ArrayList<String> mInvitorsAmount , ArrayList<String> mInvitorsInvited , ArrayList<String> mInvitorsPaid ) {
    	mInflater = LayoutInflater.from(context);
    	LIST_TEL = mInvitorsTel;
    	LIST_AMOUNT = mInvitorsAmount;
    	LIST_INVITED = mInvitorsInvited;
    	LIST_PAID = mInvitorsPaid;
    	LIST_NAME = mInvitorsName;
    }
    
	public int getCount() {
		// TODO Auto-generated method stub
		return LIST_TEL.size();
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
            convertView = mInflater.inflate(R.layout.adapter_project_inviters, null);
            holder = new ViewHolder();
            holder.txtInvitorTel = (TextView) convertView.findViewById(R.id.txt_invitor_tel);
            holder.txtInvitorAmount = (TextView) convertView.findViewById(R.id.txt_invitor_amount);
            holder.txtInvitorName = (TextView) convertView.findViewById(R.id.txt_invitor_name);
            holder.txtInvitorPaid = (TextView) convertView.findViewById(R.id.txt_invitor_paid);
            holder.txtInvitorInvited = (TextView) convertView.findViewById(R.id.txt_invitor_invited);
          
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.txtInvitorTel.setText(LIST_TEL.get(position));
        holder.txtInvitorAmount.setText(LIST_AMOUNT.get(position));
        holder.txtInvitorName.setText(LIST_NAME.get(position));
        holder.txtInvitorPaid.setText(LIST_PAID.get(position));
        holder.txtInvitorInvited.setText(LIST_INVITED.get(position));
        
       
        return convertView;
    }

    static class ViewHolder {
    	TextView txtInvitorTel;
        TextView txtInvitorAmount;
        TextView txtInvitorName;
        TextView txtInvitorPaid;
        TextView txtInvitorInvited;
        
      
    }	

}
