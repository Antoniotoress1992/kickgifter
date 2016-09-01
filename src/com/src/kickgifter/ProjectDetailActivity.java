package com.src.kickgifter;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.src.kickgifter.Constants;
import com.src.kickgifter.GoShoppingActivity.ServiceTaskBuy;

public class ProjectDetailActivity extends Activity {
	
	TextView txt_project_title , txt_reciever_telnumber , txt_project_country , txt_project_amount,txtInvites ,txtProjectViewMore;
	TextView txt_project_invite , txt_project_expired , txt_project_players , txt_project_inviteAdd,txt_project_message;
	TextView txtProjectPayers,txtIvite , txtCrowdedAmount , txtWastedAmount , txtLeftAmount;
	Button btnGoShoping , btnBook , btnClose , btnFrined;
	
	TextView txtBankView , txtCouponview , txtGiftView;
	TextView txtBankTransfer ,txtCouponCode,txtGiftBuyer;
	
	Button btnCloseBank , btnCloseGift , btnCloseCoupon ;
	ListView listBankTransfer ,  listCouponCode , listGiftBuyer ;
	RelativeLayout rlBankTransfer , rlCouponCode , rlGiftBuyer ;
	
	Bundle extras;
	private String serverURL;
	static InputStream is = null;
	static JSONObject jObj = null;
	String json;
	String data ="";
	String invitors="";
	Global globalInfo;
	String availableAmount;
	
	ArrayList<String> mInvitorsTel = new ArrayList<String>();
	ArrayList<String> mInvitorsAmount = new ArrayList<String>();
	ArrayList<String> mInvitorsPaid = new ArrayList<String>();
	ArrayList<String> mInvitorsName = new ArrayList<String>();
	ArrayList<String> mInvitorsInvited = new ArrayList<String>();
	
	ArrayList<String> mGiftName = new ArrayList<String>();
	ArrayList<String> mGiftAmount = new ArrayList<String>();
	ArrayList<String> mGiftCreated = new ArrayList<String>();
	
	ArrayList<String> mBankInfo = new ArrayList<String>();
	ArrayList<String> mBankCreated = new ArrayList<String>();
	ArrayList<String> mBankAmount = new ArrayList<String>();
	
	ArrayList<String> mCouponCode = new ArrayList<String>();
	ArrayList<String> mCompanyName = new ArrayList<String>();
	ArrayList<String> mCouponAmount = new ArrayList<String>();
	ArrayList<String> mCouponCreated = new ArrayList<String>();
	
	
	private ProjectPayersAdapter mDispAdt;
	private BankTransferAdapter mDispBank;
	private CouponCodeAdapter mDispCoupon;
	private GiftBuyerAdapter mDispGift;
	
	ListView listProjectPayers;
	ScrollView scrProjectDetail;
	RelativeLayout  layoutProjectPayers;
	SessionManager session;
	AlertDialog.Builder builder,builderFriend;
	AlertDialog alert;
	@Override
	 
	protected void onCreate(Bundle savedInstanceState) {
		session =  new SessionManager(getApplicationContext());
		session.checkLogin();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_detail);
		globalInfo = (Global)getApplication();
		final String projectID = globalInfo.getProjectId().toString();
		builder = new AlertDialog.Builder(this);
		builderFriend = new AlertDialog.Builder(this);
		serverURL = Constants.Server_Project_Detail;
		
		txt_project_title = (TextView) findViewById (R.id.txt_project_title);
		txt_reciever_telnumber = (TextView) findViewById (R.id.txt_reciever_telnumber);
		txt_project_country = (TextView) findViewById (R.id.txt_project_country);
		txt_project_amount = (TextView) findViewById (R.id.txt_project_amount);
		txt_project_expired = (TextView) findViewById (R.id.txt_project_expired);
		txt_project_invite = (TextView) findViewById(R.id.txt_project_invite);
		txt_project_inviteAdd = (TextView) findViewById(R.id.txt_project_inviteAdd);
		txt_project_message = (TextView) findViewById(R.id.txt_project_message);
		
		txtProjectViewMore = (TextView) findViewById(R.id.txt_project_viewMore);
		txtProjectPayers = (TextView) findViewById(R.id.lbl_project_payers);
		txtIvite = (TextView) findViewById(R.id.txt_invite) ;
		
		
		txtCrowdedAmount = (TextView) findViewById(R.id.txt_crowded_amount) ;
		txtWastedAmount = (TextView) findViewById(R.id.txt_wasted_amount) ;
		txtLeftAmount = (TextView) findViewById(R.id.txt_left_amount) ;
				
		listProjectPayers = (ListView) findViewById(R.id.lst_project_payer);
		
		scrProjectDetail = (ScrollView) findViewById(R.id.scrProjectDetail);
		
		
		layoutProjectPayers = (RelativeLayout)findViewById(R.id.layout_project_payers);
		
		btnGoShoping = (Button) findViewById (R.id.btnGoShoping);
		btnBook = (Button) findViewById (R.id.btnBook);
		btnClose = (Button) findViewById(R.id.btnClose);
		btnFrined = (Button) findViewById(R.id.btnFrined);
		
		btnCloseBank = (Button) findViewById(R.id.btnCloseBank);
		btnCloseGift = (Button) findViewById(R.id.btnCloseGift);
		btnCloseCoupon = (Button) findViewById(R.id.btnCloseCoupon);
		
		listBankTransfer = (ListView) findViewById(R.id.lst_bank_transfer);
		listCouponCode = (ListView) findViewById(R.id.lst_coupon_codes);
		listGiftBuyer = (ListView) findViewById(R.id.lst_gift_buy);
		 
		rlBankTransfer = (RelativeLayout) findViewById(R.id.layout_bank_transfers);
		rlCouponCode = (RelativeLayout) findViewById(R.id.layout_coupon_codes);
		rlGiftBuyer = (RelativeLayout) findViewById(R.id.layout_gift_buyers);
		
		txtBankView = (TextView) findViewById(R.id.txt_bank_view);
		txtCouponview = (TextView) findViewById(R.id.txt_coupon_view);
		txtGiftView = (TextView) findViewById(R.id.txt_gift_view);
		
		txtBankTransfer = (TextView) findViewById(R.id.txt_bank_transfer);
		txtCouponCode = (TextView) findViewById(R.id.txt_coupon_code);
		txtGiftBuyer = (TextView) findViewById(R.id.txt_buy_gift);
		 
		btnGoShoping.setEnabled(false);
		btnBook.setEnabled(false);
		txt_project_inviteAdd.setVisibility(View.INVISIBLE);
		final RelativeLayout rlMenuBack = (RelativeLayout) findViewById(R.id.menuPanelBack);
		displayProjectDetail(projectID);
		
		btnGoShoping.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProjectDetailActivity.this, GoShoppingActivity.class);
				startActivity(intent);
				
			}
		});
		
		btnFrined.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				builderFriend.setMessage(Constants.messageFriend);
				builderFriend.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		                displayChooseGift(projectID);
		            }
		        });
				builderFriend.setNegativeButton("No", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		                
		            }
		        });
				alert = builderFriend.create();
				alert.show();
				
			}
		});
		
		txtProjectViewMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scrProjectDetail.setVisibility(View.GONE);
				layoutProjectPayers.setVisibility(View.VISIBLE);
				rlMenuBack.setVisibility(View.INVISIBLE);
				
			}
		});
		txtBankView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scrProjectDetail.setVisibility(View.GONE);
				rlBankTransfer.setVisibility(View.VISIBLE);
				rlMenuBack.setVisibility(View.INVISIBLE);
				
			}
		});
		txtCouponview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scrProjectDetail.setVisibility(View.GONE);
				rlCouponCode.setVisibility(View.VISIBLE);
				rlMenuBack.setVisibility(View.INVISIBLE);
				
			}
		});
		txtGiftView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scrProjectDetail.setVisibility(View.GONE);
				rlGiftBuyer.setVisibility(View.VISIBLE);
				rlMenuBack.setVisibility(View.INVISIBLE);
				
			}
		});
		
		
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scrProjectDetail.setVisibility(View.VISIBLE);
				layoutProjectPayers.setVisibility(View.GONE);
				rlMenuBack.setVisibility(View.VISIBLE);
				
			}
		});
		btnCloseBank.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scrProjectDetail.setVisibility(View.VISIBLE);
				rlBankTransfer.setVisibility(View.GONE);
				rlMenuBack.setVisibility(View.VISIBLE);
				
			}
		});
		
		btnCloseGift.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scrProjectDetail.setVisibility(View.VISIBLE);
				rlGiftBuyer.setVisibility(View.GONE);
				rlMenuBack.setVisibility(View.VISIBLE);
				
			}
		});
		btnCloseCoupon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				scrProjectDetail.setVisibility(View.VISIBLE);
				rlCouponCode.setVisibility(View.GONE);
				rlMenuBack.setVisibility(View.VISIBLE);
				
			}
		});
		txt_project_inviteAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProjectDetailActivity.this, InviteAddActivity.class);
				startActivity(intent);
				
			}
		});
		
		btnBook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProjectDetailActivity.this, BankTransferActivity.class);
				startActivity(intent);
				
			}
		});
		
		
		
		rlMenuBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(ProjectDetailActivity.this,HomeActivity.class);
				onBackPressed();
			}
		});
	}
	
	public void displayProjectDetail(String projectID){
		new ServiceTask().execute(projectID);
	}
	public void displayChooseGift(String projectID){
		
		new ServiceChooseGift().execute(projectID);
		setDisableAllview();
	}
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}	
	public void setEnableAllView(){
		LinearLayout layout = (LinearLayout) findViewById(R.id.frameLayout);
		for (int i = 0; i < layout.getChildCount(); i++) {
		    View child = layout.getChildAt(i);
		    child.setEnabled(true);
		}
	}
	public void setDisableAllview(){
		LinearLayout layout = (LinearLayout) findViewById(R.id.frameLayout);
		for (int i = 0; i < layout.getChildCount(); i++) {
		    View child = layout.getChildAt(i);
		    child.setEnabled(false);
		}
	}
	class ServiceTask extends AsyncTask<String, String, Object> {
		
		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			String projectId = params[0];
			
			/* Check the validation */
			
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("project_id", projectId.trim());
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
	            alert.showAlertDialog(ProjectDetailActivity.this, Constants.messageConnectionTitle, Constants.messageConnectionFailed, false);
	            btnGoShoping.setEnabled(true);
				btnBook.setEnabled(true);
	            return;
   			}
    		
    		JSONObject jsonObj = (JSONObject) result;
			String txtProjectTitle = "";
			String txtRecieverTelNumber = "" ;
			String txtProjectCountry = "" ;
			String txtProjectAmount = "" ;
			String txtProjectExpired = "" ;
			String txtProjectMessage = "" ;
			String txtCrowdedAmounts = "" ;
			String txtWastedAmounts = "";
			String txtLeftAmounts = "";
			
			int countJsonData , countJsonTemp = 0;
		
			try {
				txtProjectTitle = jsonObj.getString("name");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				txtRecieverTelNumber = jsonObj.getString("receiver_tel");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				txtProjectCountry = jsonObj.getString("country_name");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				txtProjectAmount = jsonObj.getString("amount");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				txtProjectExpired = jsonObj.getString("expired_at");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				txtProjectMessage = jsonObj.getString("message");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				txtCrowdedAmounts = jsonObj.getString("crowded");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				txtWastedAmounts = jsonObj.getString("wasted");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				txtLeftAmounts = jsonObj.getString("avaiable");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			txt_project_title.setText(txtProjectTitle);
			txt_reciever_telnumber.setText(txtRecieverTelNumber);
			txt_project_country.setText(txtProjectCountry);
			txt_project_amount.setText(txtProjectAmount);
			txt_project_expired.setText(txtProjectExpired);
			txt_project_message.setText(txtProjectMessage);
			txtCrowdedAmount.setText(txtCrowdedAmounts);
			txtWastedAmount.setText(txtWastedAmounts);
			txtLeftAmount.setText(txtLeftAmounts);
	
			try {
				
				availableAmount = jsonObj.getJSONObject("amount_status").getString("avaiable");
				globalInfo = (Global)getApplication();
				globalInfo.setAvailableAmount(availableAmount);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				
				if(jsonObj.getString("result").equals("success")){
					
					countJsonData = jsonObj.getJSONArray("invitors").length();
					countJsonTemp = countJsonData;
					
					JSONObject objectProject = null;
					JSONObject objectTemp = null;
					txtIvite.setText("Inviters" );
					txt_project_invite.setText(Integer.toString(countJsonData) + " People ," + Integer.toString(countJsonTemp) + " People Paid "); 
					
					countJsonData = jsonObj.getJSONArray("invitors").length();
					
					for(int i = 0 ; i < countJsonData;i++){
						
						objectProject = jsonObj.getJSONArray("invitors").getJSONObject(i);
						
						if(objectProject.getString("amount").equals("null")){
							mInvitorsAmount.add("");
						}else{
							
							mInvitorsAmount.add(objectProject.getString("amount")) ;
						}
						
						if(objectProject.getString("paid_at").equals("null")){
							mInvitorsPaid.add("");
						}else{
							
							mInvitorsPaid.add(objectProject.getString("paid_at").substring(0,10)) ;
						}
						if(objectProject.getString("invited_at").equals("null")){
							mInvitorsPaid.add("");
						}else{
							
							mInvitorsInvited.add(objectProject.getString("invited_at").substring(0,10)) ;
						}
						if(objectProject.getString("name").equals("null")){
							mInvitorsName.add("");
						}else{
							
							mInvitorsName.add(objectProject.getString("name")) ;
						}
						if(objectProject.getString("invitor_tel").equals("null")){
							mInvitorsTel.add("");
						}else{
							
							mInvitorsTel.add(objectProject.getString("invitor_tel")) ;
						}
					
					}

					if (mDispAdt == null) {
						mDispAdt = new ProjectPayersAdapter(ProjectDetailActivity.this, mInvitorsTel , mInvitorsName , mInvitorsAmount ,mInvitorsInvited , mInvitorsPaid  );
						listProjectPayers.setAdapter(mDispAdt);
					} else {
						mDispAdt.notifyDataSetChanged();
					}
					countJsonData = jsonObj.getJSONObject("amount_status").getJSONArray("gift_buys").length();
					objectTemp = jsonObj.getJSONObject("amount_status");
					if(countJsonData == 0){
						txtGiftBuyer.setText(Constants.noGift);
					}else{
						txtGiftView.setVisibility(View.VISIBLE);
					}
					for(int i = 0 ; i < countJsonData;i++){
						
						objectProject = objectTemp.getJSONArray("gift_buys").getJSONObject(i);
						
						if(objectProject.getString("gift_name").equals("null")){
							mGiftName.add("");
						}else{
							
							mGiftName.add(objectProject.getString("gift_name")) ;
						}
						
						if(objectProject.getString("amount").equals("null")){
							mGiftAmount.add("");
						}else{
							
							mGiftAmount.add(objectProject.getString("amount")) ;
						}
						if(objectProject.getString("created_at").equals("null")){
							mGiftCreated.add("");
						}else{
							
							mGiftCreated.add(objectProject.getString("created_at").substring(0,10)) ;
						}
						
					
					}
					
					countJsonData = jsonObj.getJSONObject("amount_status").getJSONArray("bank_transfers").length();
					
					if(countJsonData == 0){
						txtBankTransfer.setText(Constants.noBank);
					}else{
						txtBankView.setVisibility(View.VISIBLE);
					}
					
					for(int i = 0 ; i < countJsonData;i++){
						
						objectProject = objectTemp.getJSONArray("bank_transfers").getJSONObject(i);
						
						if(objectProject.getString("bank_info").equals("null")){
							mBankInfo.add("");
						}else{
							
							mBankInfo.add(objectProject.getString("bank_info")) ;
						}
						
						if(objectProject.getString("amount").equals("null")){
							mBankAmount.add("");
						}else{
							
							mBankAmount.add(objectProject.getString("amount")) ;
						}
						if(objectProject.getString("created_at").equals("null")){
							mBankCreated.add("");
						}else{
							
							mBankCreated.add(objectProject.getString("created_at").substring(0,10)) ;
						}
						
					
					}
					
					countJsonData = jsonObj.getJSONObject("amount_status").getJSONArray("coupon_codes").length();
					if(countJsonData == 0){
						txtCouponCode.setText(Constants.noCoupon);
					}else{
						txtCouponview.setVisibility(View.VISIBLE);
					}
					for(int i = 0 ; i < countJsonData;i++){
						
						objectProject = objectTemp.getJSONArray("coupon_codes").getJSONObject(i);
						
						if(objectProject.getString("coupon_code").equals("null")){
							mCouponCode.add("");
						}else{
							
							mCouponCode.add(objectProject.getString("coupon_code")) ;
						}
						
						if(objectProject.getString("amount").equals("null")){
							mCouponAmount.add("");
						}else{
							
							mCouponAmount.add(objectProject.getString("amount")) ;
						}
						if(objectProject.getString("created_at").equals("null")){
							mCouponCreated.add("");
						}else{
							
							mCouponCreated.add(objectProject.getString("created_at").substring(0,10)) ;
						}
						if(objectProject.getString("company_name").equals("null")){
							mCompanyName.add("");
						}else{
							
							mCompanyName.add(objectProject.getString("company_name")) ;
						}
						
					
					}
					
					if (mDispBank == null) {
						mDispBank = new BankTransferAdapter(ProjectDetailActivity.this, mBankInfo , mBankAmount , mBankCreated);
						listBankTransfer.setAdapter(mDispBank);
					} else {
						mDispAdt.notifyDataSetChanged();
					}
					if (mDispGift == null) {
						mDispGift = new GiftBuyerAdapter(ProjectDetailActivity.this, mGiftName , mGiftAmount , mGiftCreated);
						listGiftBuyer.setAdapter(mDispGift);
					} else {
						mDispGift.notifyDataSetChanged();
					}
					if (mDispCoupon == null) {
						mDispCoupon = new CouponCodeAdapter(ProjectDetailActivity.this, mCouponCode , mCompanyName , mCouponAmount,mCouponCreated);
						listCouponCode.setAdapter(mDispCoupon);
					} else {
						mDispCoupon.notifyDataSetChanged();
					}
					
					
					
				
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			btnGoShoping.setEnabled(true);
			btnBook.setEnabled(true);
			txt_project_inviteAdd.setVisibility(View.VISIBLE);
		}
    	
    	
   }
   class ServiceChooseGift extends AsyncTask<String, String, Object> {

		@Override
		protected Object doInBackground(String... params) {
			serverURL = Constants.Server_Project_Choose;
			String projectId = params[0];
			
			JSONObject jsonObj = new JSONObject();
			JSONObject result = null;
			try {
				jsonObj.put("project_id", projectId.trim());
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try{
				 result = LoginActivity.getJSONFromPost(serverURL, jsonObj);
			}catch(Exception e){
				builder.setMessage(Constants.messageConnectionFailed);
				builder.setCancelable(true);
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		            }
		        });
				alert = builder.create();
				alert.show();
			}
			return result;
		}
    	@Override
		protected void onPostExecute(Object result) {
    		if(result == null){
				builder.setMessage(Constants.messageConnectionFailed);
	    		builder.setCancelable(true);
	    		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                    setEnableAllView();
	                }
	            });
	    		
	    		return;
    		}
    		JSONObject jsonObj = (JSONObject) result;
    		try {
				if ( jsonObj.getString("result").equals("success")) {
					builder.setMessage(Constants.messageProjectChooseGift);
					builder.setCancelable(true);
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                onBackPressed();
			            }
			        });
					alert = builder.create();
					alert.show();
					
				}else{
					String errorMessage = jsonObj.get("msg").toString();
					AlertDialogManager alert = new AlertDialogManager();
		            alert.showAlertDialog(ProjectDetailActivity.this, "Failed", errorMessage, false);
		           
		         }
			} catch (JSONException e) {
				AlertDialogManager alert = new AlertDialogManager();
				alert.showAlertDialog(ProjectDetailActivity.this, "failed", Constants.messageProjectChooseGiftFailed, false);
				
			}
    		setEnableAllView();
		}
   }
	
}
