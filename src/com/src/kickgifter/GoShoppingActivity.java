package com.src.kickgifter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.src.kickgifter.ProjectShoppingAdapter.ViewHolder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GoShoppingActivity extends Activity {
	
	ArrayList<String> mGiftName = new ArrayList<String>();
	ArrayList<String> mGiftPrice = new ArrayList<String>();
	ArrayList<String> mImageThumb = new ArrayList<String>();
	ArrayList<String> mGiftIds = new ArrayList<String>();
	ArrayList<String> mSelectedGiftIds = new ArrayList<String>();
	ArrayList<Bitmap> mBitmap = new ArrayList<Bitmap>();
	ArrayList<URL> mUrl = new ArrayList<URL>();
	Bitmap thumbNail ;
	
	HashMap giftIds = new HashMap();
	String[] giftIdsRef ;
	String strGiftIds;
	private ProjectShoppingAdapter mDispAdt;
	ListView listGiftItem;
	Global globalInfo;
	private String serverURL;
	static InputStream is = null;
	static JSONObject jObj = null;
	String json;
	String data =""; 
	ListView listGift;
	double availableAmount,availableAmountTemp;
	TextView txtAvailable;
	TextView txtGiftPrice;
	Button btnSubmitGift , btnSubmitChoose ;
	CheckBox chkGift;
	Button btnBuy ;
	String strGifts,is_creator;
	String projectId;
	AlertDialog.Builder builder;
	AlertDialog messageBox;
	SessionManager session;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		session =  new SessionManager(getApplicationContext());
		session.checkLogin();
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_shopping);
        serverURL = Constants.Server_Gift_List;
        builder = new AlertDialog.Builder(this);
        //lst_disp_contact = (ListView)findViewById(R.id.list_all_contact);
        listGift = (ListView)findViewById(R.id.listGift);
        btnSubmitGift = (Button) findViewById(R.id.btnSubmitGift);
        btnSubmitChoose = (Button) findViewById(R.id.btnSubmitChoose);
        
        globalInfo = (Global)getApplication();
        init();
        getGiftList();
        
        listGift.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				chkGift = (CheckBox) view.findViewById(R.id.chkGift);
				txtGiftPrice = (TextView) view.findViewById(R.id.txt_gift_price);
				if(chkGift.isChecked()){
					chkGift.setChecked(false);
					availableAmount = availableAmount +  Double.parseDouble(txtGiftPrice.getText().toString());
					giftIds.remove(position);
				}else{
					
					availableAmountTemp = availableAmount;
					availableAmount = availableAmount -  Double.parseDouble(txtGiftPrice.getText().toString());
					
					if(availableAmount < 0){
						availableAmount = availableAmountTemp;
						builder.setMessage(Constants.messageAvailableWarranty);
		        		builder.setCancelable(true);
		        		messageBox = builder.create();
		        		messageBox.show();
						
					}else{
						chkGift.setChecked(true);
						txtAvailable.setText( String.format("%.2f", availableAmount));	
						giftIds.put(position, giftIdsRef[position]);
					}
					
				}
				Set gift = giftIds.entrySet();
			      // Get an iterator
			      Iterator i = gift.iterator();
			      // Display elements
			      strGiftIds = "";
			      while(i.hasNext()) {
			         Map.Entry me = (Map.Entry)i.next();
			         if(strGiftIds.equals("")){
			        	 strGiftIds = strGiftIds + me.getValue();
			         }else{
			        	 strGiftIds = strGiftIds +"," + me.getValue();
			         }
			       }
			}
		});
        btnSubmitGift.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				/*mGiftIds , projectId submit */
				projectId = globalInfo.getProjectId();
				buyService(strGiftIds,projectId,"1");
			}
			
		});
        btnSubmitChoose.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				/*mGiftIds , projectId submit */
				projectId = globalInfo.getProjectId();
				
				buyService(strGiftIds,projectId,"0");
			}
			
		});
        
        RelativeLayout rlMenuBack = (RelativeLayout) findViewById(R.id.menuPanelBack);
		rlMenuBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				finish();
			}
		});
		builder = new AlertDialog.Builder(this);
		builder.setMessage(Constants.messageLogin);
		builder.setCancelable(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
    }
	
    
	public void  init(){
		txtAvailable = (TextView) findViewById(R.id.edt_gift_availble);
		availableAmount = Double.parseDouble( globalInfo.getAvailableAmount());
		txtAvailable.setText(globalInfo.getAvailableAmount());
		strGiftIds="";
		
		
	}
	public void getGiftList()
	{
		
		new ServiceTask().execute();
	}
	public void buyService(String mGiftIds , String projectId , String is_creator){
		new ServiceTaskBuy().execute(mGiftIds,projectId,is_creator);
		setDisableAllview();
	}
	private void runThread(final ArrayList<URL> url, final ArrayList<Bitmap> mBitmap) {
			new Thread() {
	    		public void run() {
	                       for(int i = 0 ; i <url.size();i++){
	                    	   Bitmap bitmap = null;
								try {
									bitmap = BitmapFactory.decodeStream(url.get(i).openStream());
									mBitmap.add(bitmap);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                       }
	            }
	        }.start();
	}
	public void setEnableAllView(){
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.rlGoShopping);
		for (int i = 0; i < layout.getChildCount(); i++) {
		    View child = layout.getChildAt(i);
		    child.setEnabled(true);
		}
	}
	public void setDisableAllview(){
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.rlGoShopping);
		for (int i = 0; i < layout.getChildCount(); i++) {
		    View child = layout.getChildAt(i);
		    child.setEnabled(false);
		}
	}
	
    
   class ServiceTask extends AsyncTask<String, String, Object> {

		@Override
		protected Object doInBackground(String... params) {
			
			
			JSONObject jsonObj = new JSONObject();
			JSONObject result = LoginActivity.getJSONFromPost(serverURL, jsonObj);
			return result;
			
		}
    	@Override
		protected void onPostExecute(Object result) {
    		if(result == null){
    			AlertDialogManager alert = new AlertDialogManager();
	            alert.showAlertDialog(GoShoppingActivity.this, Constants.messageConnectionTitle, Constants.messageConnectionFailed, false);
	            return;
   			}
			JSONObject jsonObj = (JSONObject) result;
			String giftName = "";
			String imageThumb = "";
			String giftPrice = "0" ;
			String userName = "";
			String msg = "" ;
			String giftId = "";
			int countJsonData = 0 ; 
			try {
				 countJsonData = jsonObj.getJSONArray("gifts").length();
				 giftIdsRef = new String[countJsonData];
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i = 0 ; i < countJsonData;i++){
				JSONObject objectProject = null;
				try {
					objectProject = jsonObj.getJSONArray("gifts").getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					giftName = objectProject.getString("name");
					imageThumb = objectProject.getString("thumb");
					giftPrice = objectProject.getString("price");
					giftId = objectProject.getString("id");
					
					mGiftName.add(giftName);
					mGiftPrice.add(giftPrice);
					
					URL newurl = null;
					try {
						newurl = new URL(imageThumb.toString());
						mUrl.add(newurl);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mImageThumb.add(imageThumb);
					giftIdsRef[i]= giftId;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			runThread(mUrl , mBitmap);
			if (mDispAdt == null) {
				mDispAdt = new ProjectShoppingAdapter(GoShoppingActivity.this, mGiftName , mGiftPrice,mBitmap);
				listGift.setAdapter(mDispAdt);
				
			} else {
				mDispAdt.notifyDataSetChanged();
			}
			setEnableAllView();
		}
   }
   class ServiceTaskBuy extends AsyncTask<String, String, Object> {

		@Override
		protected Object doInBackground(String... params) {
			serverURL = Constants.Server_Gift_Submit;
			
			String mGifts = params[0];
			String projectId = params[1];
			String is_creator = params[2];
			/* Check the validation */
			
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("project_id", projectId.trim());
				jsonObj.put("gift_ids", mGifts.trim());
				jsonObj.put("is_creator", is_creator.trim());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject result = LoginActivity.getJSONFromPost(serverURL, jsonObj);
			return result;
			
			
		}
   	@Override
		protected void onPostExecute(Object result) {
   			if(result == null){
			
	            AlertDialogManager alert = new AlertDialogManager();
	            alert.showAlertDialog(GoShoppingActivity.this, Constants.messageConnectionTitle, Constants.messageConnectionFailed, false);
	            setEnableAllView();
	            return;
   			}
			JSONObject jsonObj = (JSONObject) result;
			String strResult = "";
			try {
				strResult = jsonObj.getString("result");
				if(strResult.equals("success")){
					builder.setMessage(Constants.messageSubmitSuccess);
					builder.setCancelable(true);
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                setEnableAllView();
			            }
			        });
					AlertDialog alert = builder.create();
					alert.show();
				}else{
					builder.setMessage(Constants.messageSubmitFailed);
					builder.setCancelable(true);
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                setEnableAllView();
			            }
			        });
					AlertDialog alert = builder.create();
					alert.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			setEnableAllView();
		}
  }
}
