package com.src.kickgifter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProjectShoppingAdapter extends BaseAdapter {

	private GoShoppingActivity mContext = null;
	
	private LayoutInflater mInflater;
	
	private ArrayList<String> LIST_NAME;
	private ArrayList<String> LIST_PRICE;
	private ArrayList<String> LIST_THUMB;
	private ArrayList<Bitmap> LIST_BITMAP;
	
    public ProjectShoppingAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    
    public ProjectShoppingAdapter(Context context, ArrayList<String> listName, ArrayList<String> listPhone , ArrayList<Bitmap> listBitmap ) {
    	
    	mContext = (GoShoppingActivity) context;
    	
    	mInflater = LayoutInflater.from(context);
    	LIST_NAME = listName;
    	LIST_PRICE = listPhone;
    	LIST_BITMAP = listBitmap;
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
            convertView = mInflater.inflate(R.layout.adapter_go_shopping, null);
            holder = new ViewHolder();
            holder.txt_proejct_name = (TextView) convertView.findViewById(R.id.txt_proejct_name);
            holder.txt_project_price = (TextView) convertView.findViewById(R.id.txt_gift_price);
            holder.img_phone_thumbnail = (ImageView) convertView.findViewById(R.id.img_phone_thumbnail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.txt_proejct_name.setText(LIST_NAME.get(position));
        holder.txt_project_price.setText(LIST_PRICE.get(position));
        /*holder.img_phone_thumbnail.setImageBitmap(LIST_BITMAP.get(position));*/
        return convertView;
    }
  
    static class ViewHolder {
    	TextView txt_proejct_name;
        TextView txt_project_price;
        ImageView img_phone_thumbnail;
       
    }	

    private void runThread(final URL url, final ViewHolder holder) {

    	new Thread() {
    		public void run() {
                
                          Bitmap bitmap = null;
								try {
									bitmap = BitmapFactory.decodeStream(url.openStream());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
            	                if (bitmap != null)
            	                	try{
            	                		holder.img_phone_thumbnail.setImageBitmap(bitmap);
            	                	}catch (Exception e) {
            	                		e.printStackTrace();
            	                }
            	
                
            }
        }.start();
    }
}
