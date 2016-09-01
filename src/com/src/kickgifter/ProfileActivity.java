package com.src.kickgifter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.src.kickgifter.Constants;

public class ProfileActivity extends FragmentActivity{
	
	static RelativeLayout relSignUp;
	
	EditText editUserName , editUserEmail , editUserPhone , editUserCountry , editUserPassword , editUserConfirm;
	
	
	private LinearLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;
	private RelativeLayout headerPanel;
	private int panelWidth;
	private int panelWidth1;
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters;
	LinearLayout.LayoutParams listViewParameters;
	ArrayList<String> contactListPhone = new ArrayList<String>();
	ArrayList<String> contactListName = new ArrayList<String>();
	
	JSONObject jsonContacts = new JSONObject();
	
	static InputStream is = null;
	static JSONObject jObj = null;
	
	String json;
	String data =""; 
	String serverURL = "" ;
	String countrySelectedId;
	
	int sizeData = 0;
    
	String[] country;
    String[] countryId;
    
    AlertDialog.Builder builder;
    
    RelativeLayout rlContactList , rlUpdate  , rlProfile;
    AlertDialog.Builder  mSingleDialog , mLanguageSelectDialog;
    ImageView menuLeftButton;
    
    String strUserName , strUserEmail , strUserPhone , strUserCountry , strUserPassword , strUserConfirm , strMessage ;
    Global globalInfo;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		globalInfo = (Global) getApplication();
		builder = new AlertDialog.Builder(this);
		serverURL = Constants.Server_User_Detail;
		
		editUserName = (EditText) findViewById(R.id.edit_user_name);
		editUserEmail = (EditText) findViewById(R.id.edit_user_email);
		editUserPhone = (EditText) findViewById(R.id.edit_user_phone);
		editUserCountry = (EditText) findViewById(R.id.edit_user_country);
		editUserPassword = (EditText) findViewById(R.id.edit_user_password);
		editUserConfirm = (EditText) findViewById(R.id.edit_user_confirm);
		
		rlContactList = (RelativeLayout) findViewById(R.id.relContactList); 
		rlUpdate = (RelativeLayout) findViewById(R.id.relUpdate);
		rlProfile = (RelativeLayout) findViewById(R.id.rlProfile);
		
		mSingleDialog = new AlertDialog.Builder(this);
		mLanguageSelectDialog = new AlertDialog.Builder(this);
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels) * -0.75);
		panelWidth1 = (int) ((metrics.widthPixels) * 0.75);
		headerPanel = (RelativeLayout) findViewById(R.id.header);
		headerPanelParameters = (LinearLayout.LayoutParams) headerPanel.getLayoutParams();
		headerPanelParameters.width = metrics.widthPixels;
		headerPanel.setLayoutParams(headerPanelParameters);
		
		slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);
		
		rlProfile.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Utils.hideSoftKeyboard(ProfileActivity.this);
				return false;
			}
		});
		rlUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setData();
				if(!validationCheck()){
	        		AlertDialogManager alertError = new AlertDialogManager();
	        		alertError.showAlertDialog(ProfileActivity.this, "Input Error", strMessage , false);
	        		
				}else{
					rlUpdate.setEnabled(false);
	        		new ServiceTask().execute(strUserName, strUserEmail ,strUserPhone ,countrySelectedId,strUserPassword);
				}
			}
		});
		
		rlContactList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				rlContactList.setEnabled(false);
				new UploadContactList().execute(globalInfo.getContactList().toString());
			}
		});
		
		menuLeftButton = (ImageView) findViewById(R.id.menuViewButton);
		menuLeftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isExpanded) {
					isExpanded = true;
					// Expand

					//FragmentManager fragmentManager = getFragmentManager();
					FragmentManager fragmentManager = getSupportFragmentManager();

					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					fragmentTransaction.replace(R.id.menuPanel,	new LeftMenuFragment());
					fragmentTransaction.commit();
					
					new ExpandAnimation(slidingPanel, panelWidth1,
							Animation.RELATIVE_TO_SELF, 0.0f,
							Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);

				} else {
					isExpanded = false;
					// Collapse
					
					new CollapseAnimation(slidingPanel, panelWidth1,
							TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
							TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f,
							0, 0.0f);

				}
			}
		});
		initProfileActivity();
		
		mSingleDialog.setTitle(Constants.messageUpdateProfile);
		mLanguageSelectDialog.setTitle(Constants.messageLanguageSelection);
		mLanguageSelectDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		@Override
			   public void onClick(DialogInterface dialog, int which) {
			   // TODO Auto-generated method stub
			   }
		});
		
		mSingleDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
				   public void onClick(DialogInterface dialog, int which) {
				   // TODO Auto-generated method stub
				   }
		});
		
		editUserCountry.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					AlertDialog alert = mLanguageSelectDialog.create();
					alert.show();
				}
		});
		editUserPhone.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		    	if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
		        // If the event is a key-down event on the "enter" button
		        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	editUserPassword.requestFocus();
	              return true;
		        }
		        return false;
		    }
		});
		editUserEmail.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		    	if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
		        // If the event is a key-down event on the "enter" button
		        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	editUserPhone.requestFocus();
	              return true;
		        }
		        return false;
		    }
		});
		
		editUserPassword.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		    	if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
		        // If the event is a key-down event on the "enter" button
		        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	editUserConfirm.requestFocus();
	              return true;
		        }
		        return false;
		    }
		});
		
		editUserConfirm.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		    	if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
		        // If the event is a key-down event on the "enter" button
		        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	setData();
		        	if(!validationCheck()){
		        		AlertDialogManager alert = new AlertDialogManager();
		        		alert.showAlertDialog(ProfileActivity.this, "Input Error", strMessage , false);
		        	}else{
		        		new ServiceTask().execute(strUserName, strUserEmail ,strUserPhone ,countrySelectedId,strUserPassword);
					}
	              return true;
		        }
		        return false;
		    }
		});
		
	}
	public void initProfileActivity(){
		new ServiceLanguage().execute();
		getUserDetail();
	}	
	public void getUserDetail(){
		
		editUserName.setText(globalInfo.getUserId().toString());
		editUserEmail.setText(globalInfo.getEmail().toString());
		editUserPhone.setText(globalInfo.getPhone().toString());
		
		contactListPhone = globalInfo.getContactListPhone();
		contactListName = globalInfo.getContactListName();
		
		int countContactInfo = globalInfo.getContactListName().size();
		for(int i = 0 ; i < countContactInfo ; i++){
			
			try {
				jsonContacts.put("name", contactListName.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				jsonContacts.put("phone", contactListPhone.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	public void setData(){
		strUserName = editUserName.getText().toString();
		strUserEmail = editUserEmail.getText().toString();
		strUserPhone = editUserPhone.getText().toString();
		strUserCountry = editUserCountry.getText().toString();
		strUserPassword = editUserPassword.getText().toString();
		strUserConfirm = editUserConfirm.getText().toString();
	}
	
	public Boolean  validationCheck(){
			if(strUserName.equals("")){strMessage = "Please input username correctly";return false;}
			if(!isValidEmailAddress(strUserEmail)){strMessage = "Please input email correctly";return false;}
			PhoneNumberValidator phoneCheck = new PhoneNumberValidator();
			if(!phoneCheck.isValidPhoneNumber(strUserPhone)){
				strMessage = "Please input phonenumber correctly";
				return false;}
			strUserPhone  = strUserPhone.replaceAll("\\D+","");
			if(strUserCountry.equals("")){strMessage = "Please input country correctly";return false;}
			if (strUserPassword == strUserConfirm){
				strMessage = "Please Input Password Correctly";
			}
		return true;
	}
	
	public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
	
	public static class PhoneNumberValidator {

	    public boolean isValidPhoneNumber(String s) {
	    	try  
	    	  {  
	    	    double d = Double.parseDouble(s);  
	    	  }  
	    	  catch(NumberFormatException nfe)  
	    	  {  
	    	    return false;  
	    	  }  
	    	  	return true;  
	    }
	}
	
	class ServiceLanguage extends AsyncTask<String, String, Object> {

		@Override
		protected Object doInBackground(String... params) {
			JSONObject jsonObj = new JSONObject();
			serverURL = Constants.Server_Country_List;
			JSONObject result = LoginActivity.getJSONFromPost(serverURL, jsonObj);
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
	    		return;
    		}
			JSONObject jsonObj = (JSONObject) result;
			String strID = "";
			String strName = "";
			String strMsg = "";
			int countJsonData = 0;
			
			try {
				if ( jsonObj.getString("result").equals("success")) {
					try {
						countJsonData = jsonObj.getJSONArray("countreis").length();
						country = new String[countJsonData];
						countryId = new String[countJsonData];
						JSONObject objectProject = null;
						for(int i = 0 ; i < countJsonData;i++){
							
							objectProject = jsonObj.getJSONArray("countreis").getJSONObject(i);
							strName = objectProject.getString("name");
							strID = objectProject.getString("id");
							country[i] = strName ;
							countryId[i] = strID;	
							
							if( strID.equals((globalInfo.getCountryId().toString()))){
								editUserCountry.setText(strName);
							}
						}
						mLanguageSelectDialog.setSingleChoiceItems(country, -1, new DialogInterface.OnClickListener() {
						    @Override
							public void onClick(DialogInterface dialog, int which) {
							     // TODO Auto-generated method stub
							     int nIndex = which;
							     countrySelectedId = countryId[nIndex];
							     editUserCountry.setText(country[nIndex]);
							    }
						});
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						strMsg =  jsonObj.getString("msg");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
					
				}else
				{
						try {
							String errorMessage = jsonObj.get("msg").toString();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				  
			}
	}
	
	
	class UploadContactList extends AsyncTask<String, String, Object> {

			@Override
			protected Object doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				JSONObject jsonObj = new JSONObject();
				
				try {
					jsonObj.put("user_id", globalInfo.getUserId().toString());
					jsonObj.put("contacts", jsonContacts);
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				serverURL = Constants.Server_Phone_Upload;
				JSONObject result = LoginActivity.getJSONFromPost(serverURL, jsonObj);
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
		    		rlContactList.setEnabled(true);
		    		return;
				}	
				JSONObject jsonObj = (JSONObject) result;
				try {
					if ( jsonObj.getString("result").equals("success")) {
						mSingleDialog.setMessage(Constants.messageUploadSuccess);
						mSingleDialog.setCancelable(true);
						mSingleDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				            }
				        });
						AlertDialog alert = mSingleDialog.create();
						alert.show();
					}else
					{
							try {
								String errorMessage = jsonObj.get("msg").toString();
								mSingleDialog.setMessage(errorMessage);
								mSingleDialog.setCancelable(true);
								mSingleDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						            public void onClick(DialogInterface dialog, int id) {
						                dialog.cancel();
						            }
						        });
								mSingleDialog.show();
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rlContactList.setEnabled(true);	  
				return;
			}
	}
    class ServiceTask extends AsyncTask<String, String, Object> {

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			String userName = params[0];
			String userEmail = params[1];
			String userPhone = params[2];
			String userCountry = params[3];
			String userPass = params[4];
			
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("user_id", globalInfo.getUserId().toString());
				jsonObj.put("name", userName.trim());
				jsonObj.put("password", userPass.trim());
				jsonObj.put("email", userEmail.trim());
				jsonObj.put("phone", userPhone.trim());
				jsonObj.put("country_id", userCountry.trim());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			serverURL = Constants.Server_User_Update;
			JSONObject result = LoginActivity.getJSONFromPost(serverURL, jsonObj);
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
	    		rlUpdate.setEnabled(true);
	    		return;
			}	
			JSONObject jsonObj = (JSONObject) result;
			try {
				if ( jsonObj.getString("result").equals("success")) {
					mSingleDialog.setMessage(Constants.messageProfileUpdateSuccess);
					mSingleDialog.setCancelable(true);
					mSingleDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			            }
			        });
					AlertDialog alert = mSingleDialog.create();
					alert.show();
				}else
				{
						try {
							String errorMessage = jsonObj.get("msg").toString();
							mSingleDialog.setMessage(Constants.messageProfileUpdateFailed);
							mSingleDialog.setCancelable(true);
							mSingleDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog, int id) {
					                dialog.cancel();
					            }
					        });
							mSingleDialog.show();
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rlUpdate.setEnabled(true);
			return;
		}
	}
    
    public void userSignUp(String user_id){
		new ServiceTask().execute(user_id);
	}
}
