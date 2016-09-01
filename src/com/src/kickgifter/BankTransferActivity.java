package com.src.kickgifter;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.src.kickgifter.AlertDialogManager;
import com.src.kickgifter.LoginActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BankTransferActivity extends Activity {
	
	private ProjectShoppingAdapter mDispAdt;
	ListView listGiftItem;
	Global globalInfo;
	private String serverURL;
	static InputStream is = null;
	static JSONObject jObj = null;
	String json;
	String data =""; 
	EditText editBankTransfer;
	EditText editGiftAmount;
	CheckBox chkGift;
	Button btnSendBank ;
	String strBankInfo;
	String strGiftAmount;
	String projectId;
	AlertDialog.Builder builder;
	AlertDialog alert;
	SessionManager session;
	TextView txtAvailable;
	RelativeLayout rlBankTransfer;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		session =  new SessionManager(getApplicationContext());
		session.checkLogin();
		builder = new AlertDialog.Builder(this);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);
        serverURL = Constants.Server_Bank_Send;
        
        btnSendBank = (Button) findViewById(R.id.btnBankSend);
        globalInfo = (Global)getApplication();
        
        editGiftAmount = (EditText) findViewById(R.id.editGiftAmount);
        editBankTransfer = (EditText) findViewById(R.id.editBankTransfer);
        
        txtAvailable = (TextView) findViewById(R.id.txtBankAvailableAmount);
        
        txtAvailable.setText(globalInfo.getAvailableAmount());
        rlBankTransfer =  (RelativeLayout) findViewById(R.id.rlBankTransfer);
        rlBankTransfer.setOnTouchListener(new OnTouchListener() {
				    @Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						Utils.hideSoftKeyboard(BankTransferActivity.this);
						return false;
					}
		});

        btnSendBank.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				/*mGiftIds , projectId submit */
				projectId = globalInfo.getProjectId();
				strGiftAmount = editGiftAmount.getText().toString();
			    strBankInfo = editBankTransfer.getText().toString();
			    if(LoginActivity.isNumeric(strGiftAmount) && !(strBankInfo.equals(""))){
			    	btnSendBank.setEnabled(false);
			    	sendService(projectId , strGiftAmount , strBankInfo);
			    }else{
			    	AlertDialogManager alert = new AlertDialogManager();
			    	
			    	alert.showAlertDialog(BankTransferActivity.this, Constants.messageInputErrorTitle, Constants.messageInputErrorMessage, false);
				}
			}
			
		});
        editGiftAmount.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		    	if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
		        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	editBankTransfer.requestFocus();
	              return true;
		        }
		        return false;
		    }
		});
        editBankTransfer.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		    	if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
		        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	projectId = globalInfo.getProjectId();
					strGiftAmount = editGiftAmount.getText().toString();
				    strBankInfo = editBankTransfer.getText().toString();
				    if(LoginActivity.isNumeric(strGiftAmount) && !(strBankInfo.equals(""))){
				    	sendService(projectId , strGiftAmount , strBankInfo);
				    }else{
				    	AlertDialogManager alert = new AlertDialogManager();
				    	alert.showAlertDialog(BankTransferActivity.this, Constants.messageInputErrorTitle, Constants.messageInputErrorMessage, false);
					}
	              return true;
		        }
		        return false;
		    }
		});
        RelativeLayout rlMenuBack = (RelativeLayout) findViewById(R.id.menuPanelBack);
		rlMenuBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				Utils.hideSoftKeyboard(BankTransferActivity.this);
				finish();
			}
		});
        
    }
	public void sendService(String projectId , String strGiftAmount , String strBankInfo)
	{
		
		new ServiceTask().execute(projectId,strGiftAmount,strBankInfo);
	}
	
	    
   class ServiceTask extends AsyncTask<String, String, Object> {

		@Override
		protected Object doInBackground(String... params) {
			
			String projectId = params[0];
			String giftAmount = params[1];
			String bankInfo = params[2];
			
			JSONObject jsonObj = new JSONObject();
			JSONObject result = null;
			try {
				jsonObj.put("project_id", projectId.trim());
				jsonObj.put("amount", giftAmount.trim());
				jsonObj.put("bank_info", bankInfo.trim());
				
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
	                }
	            });
	    		btnSendBank.setEnabled(true);
	    		return;
    		}
    		JSONObject jsonObj = (JSONObject) result;
    		try {
				if ( jsonObj.getString("result").equals("success")) {
					builder.setMessage(Constants.messageSubmitGift);
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
		            alert.showAlertDialog(BankTransferActivity.this, "Failed", errorMessage, false);
		           
		         }
			} catch (JSONException e) {
				AlertDialogManager alert = new AlertDialogManager();
				alert.showAlertDialog(BankTransferActivity.this, "failed", Constants.messageBankTrnasferFailed, false);
				
			}
    		btnSendBank.setEnabled(true);
		}
   }
   
}
