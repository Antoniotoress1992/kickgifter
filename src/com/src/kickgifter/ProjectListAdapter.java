package com.src.kickgifter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProjectListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	private ArrayList<String> LIST_PROJECT_NAME;
	private ArrayList<String> LIST_PROJECT_AMOUNT;
	private ArrayList<String> LIST_PROJECT_EXPIRED;
	private ArrayList<String> LIST_PROJECT_CROWEDED;
	private ArrayList<String> LIST_PROJECT_MESSAGE;
    public ProjectListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    
    public ProjectListAdapter(Context context, ArrayList<String> listProjectName, ArrayList<String> listProjectAmount ,ArrayList<String> listProjectCrowded ,ArrayList<String> listProjectExpired , ArrayList<String> listProjectMessage) {
    	mInflater = LayoutInflater.from(context);
    	LIST_PROJECT_NAME = listProjectName;
    	LIST_PROJECT_AMOUNT = listProjectAmount;
    	LIST_PROJECT_EXPIRED = listProjectExpired;
    	LIST_PROJECT_CROWEDED = listProjectCrowded;
    	LIST_PROJECT_MESSAGE = listProjectMessage ;
    }
    
	public int getCount() {
		// TODO Auto-generated method stub
		return LIST_PROJECT_NAME.size();
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
            convertView = mInflater.inflate(R.layout.adapter_project_list, null);
            holder = new ViewHolder();
            holder.txt_project_name = (TextView) convertView.findViewById(R.id.txt_project_name);
            holder.txt_project_amount = (TextView) convertView.findViewById(R.id.txt_project_amount);
            holder.txt_project_expired = (TextView) convertView.findViewById(R.id.txt_project_expired);
            holder.txt_project_crowed = (TextView) convertView.findViewById(R.id.txt_project_crowded);
            holder.txt_project_message = (TextView) convertView.findViewById(R.id.txt_project_message);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.txt_project_name.setText(LIST_PROJECT_NAME.get(position));
        holder.txt_project_amount.setText(LIST_PROJECT_AMOUNT.get(position));
        holder.txt_project_expired.setText(LIST_PROJECT_EXPIRED.get(position));
        holder.txt_project_crowed.setText(LIST_PROJECT_CROWEDED.get(position));
        holder.txt_project_message.setText(LIST_PROJECT_MESSAGE.get(position));
        return convertView;
    }

    static class ViewHolder {
    	TextView txt_project_name;
        TextView txt_project_amount;
        TextView txt_project_expired;
        TextView txt_project_crowed;
        TextView txt_project_message;
    }	

}
