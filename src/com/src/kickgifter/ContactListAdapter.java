package com.src.kickgifter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	private ArrayList<String> LIST_NAME;
	private ArrayList<String> LIST_PHONE;
	
    public ContactListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    
    public ContactListAdapter(Context context, ArrayList<String> listName, ArrayList<String> listPhone) {
    	mInflater = LayoutInflater.from(context);
    	LIST_NAME = listName;
    	LIST_PHONE = listPhone;
    }
    
	public int getCount() {
		// TODO Auto-generated method stub
		return LIST_NAME.size();
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
            convertView = mInflater.inflate(R.layout.adapter_contact_list, null);
            holder = new ViewHolder();
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_contact_name);
            holder.txt_phone = (TextView) convertView.findViewById(R.id.txt_contact_phone);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.txt_name.setText(LIST_NAME.get(position));
        holder.txt_phone.setText(LIST_PHONE.get(position));
        return convertView;
    }

    static class ViewHolder {
    	TextView txt_name;
        TextView txt_phone;
       
    }	

}
