package com.src.kickgifter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.src.kickgifter.SessionManager;
import com.src.kickgifter.LoginActivity.ServiceTask;

import android.view.View.OnTouchListener;
public class LoginActivity extends Activity {
	private static RelativeLayout relBtn;
	static InputStream is = null;
	static JSONObject jObj = null;
	
	static String json;
	String data =""; 
    int sizeData = 0;
    String serverURL = Constants.Server_Sign_Url;
    
    static EditText userPhoneNumber;
    static EditText userPassword;
    static RelativeLayout rlNewHere ;
    static AlertDialog messageBox;
    static RelativeLayout loginLayout;
    
    String userPhone;
    String userPass;
    static AlertDialog.Builder builder;
    ImageView imgGif;
    SessionManager session;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	      
		/* Define Control variables */
		builder = new AlertDialog.Builder(this);
		userPhoneNumber = (EditText) findViewById(R.id.edit_phone_number);
		userPassword = (EditText) findViewById(R.id.edit_password);
		session = new SessionManager(getApplicationContext()); 
		
		relBtn = (RelativeLayout)findViewById(R.id.relBtn);
		loginLayout = (RelativeLayout)findViewById(R.id.loginLayout);
		
		relBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					
					userPhone = userPhoneNumber.getText().toString();
	                userPass = userPassword.getText().toString();
			        //call login service
	                if(userPass.equals("") || userPhone.equals("")){
		        		 builder.setMessage(Constants.messageWarranty);
		        		 builder.setCancelable(true);
		        		 messageBox = builder.create();
		        		 messageBox.show();
		        	 }else{
		        		 ImageView imgGif = (ImageView) findViewById(R.id.imgGif);
		        		 imgGif.setVisibility(View.VISIBLE);
		        		 relBtn.setFocusableInTouchMode(false);
		        		 relBtn.setEnabled(false); 
		        		 new ServiceTask().execute(userPhone, userPass);
		        	 }
			}
		});
		imgGif = (ImageView) findViewById(R.id.imgGif);
		builder = new AlertDialog.Builder(this);
		
		rlNewHere = (RelativeLayout) findViewById(R.id.rlNewHere);
		loginLayout = (RelativeLayout) findViewById(R.id.loginLayout);
		rlNewHere.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(LoginActivity.this , SignUpActivity.class);
				startActivity(intent);
			}
		});
		loginLayout.setOnTouchListener(new OnTouchListener() {
		    
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Utils.hideSoftKeyboard(LoginActivity.this);
				return false;
			}
		});
		userPassword.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		    	if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
		        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	userPhone = userPhoneNumber.getText().toString();
	                userPass = userPassword.getText().toString();
			        //call login service
	                if(userPass.equals("") || userPhone.equals("")){
		        		 builder.setMessage(Constants.messageWarranty);
		        		 builder.setCancelable(true);
		        		 messageBox =  builder.create();
		        		 messageBox.show();
		        	 }else{
		        		 ImageView imgGif = (ImageView) findViewById(R.id.imgGif);
		        		 imgGif.setVisibility(View.VISIBLE);
		        		 new ServiceTask().execute(userPhone, userPass);
		        		 relBtn.setEnabled(false); 
		        	 }
	              return true;
		        }
		        return false;
		    }
		});
		RelativeLayout rlNewHere = (RelativeLayout) findViewById(R.id.rlSignIn);
		rlNewHere.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(LoginActivity.this , LoginActivity.class);
				intent.putExtra("contactPhone", ""); 
				startActivity(intent);
			}
		});
		
		builder.setMessage(Constants.messageLogin);
		builder.setCancelable(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
		messageBox = builder.create();
        init_globalInfo();
	}
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {
	     //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
	     return true;
	     }
	     return super.onKeyDown(keyCode, event);    
	}
   
   
   
    public void init_globalInfo(){
    	Global globalInfo = (Global)getApplication();
    	globalInfo.setName("");
    	globalInfo.setEmail("");
    	globalInfo.setPhone("");
    	globalInfo.setCountryId("");
    	globalInfo.setUserId("");
    	globalInfo.setExpiredAt("");
    	globalInfo.setProjectId("");
    	globalInfo.setContactPhone("");
    	globalInfo.setContacts("");
    	globalInfo.setAvailableAmount("");
    	globalInfo.setUserNameSignUp("");
    	globalInfo.setUserEmailSignUp("");
    	globalInfo.setUserPhoneNumberSignUp("");
    	globalInfo.setUserCountrySignUp("");
    	globalInfo.setUserPasswordSignUp("");
    	globalInfo.setUserConfirmPasswordSingUp("");
    	globalInfo.setProjectNameAdd("");
    	globalInfo.setProviderProjectAdd("");
    	globalInfo.setCountryProjectAdd("");
    	globalInfo.setAmountProjectAdd("");
    	globalInfo.setMessageProjectAdd("");
    	globalInfo.setExpiredProjectAdd("");
    	globalInfo.setInvitersProjectAdd("");
    }
    public static JSONObject getJSONFromPost(String url, JSONObject param) {
      	 
        // Making HTTP request
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
    	Iterator<String> keys = param.keys();
	    while(keys.hasNext()){
	        String key = keys.next();
	        String val = null;
	        try{
	             urlParameters.add(new BasicNameValuePair(key, param.getString(key)));
	        }catch(Exception e){
	        	val = "Error";
	        }
	    }
	    try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
        	HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent", Constants.USER_AGENT);
            httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();  
        } catch (UnsupportedEncodingException e) {
        	return null;
        } catch (ClientProtocolException e) {
        	return null;
        } catch (IOException e) {
        	return null;
        } catch (Exception e) {
        	return null;
        }
         
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
 
        // return JSON String
        return jObj;
 
    }
    
    public static boolean isNumeric(String str)  
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
    
    class ServiceTask extends AsyncTask<String, String, Object> {

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			String userPhone = params[0];
			String userPass = params[1];
			/* Check the validation */
			if (userPhone == null || userPhone.equals("") || 
					userPass == null || userPass.equals("")) {
			}
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("phone", userPhone.trim());
				jsonObj.put("password", userPass.trim());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject result = getJSONFromPost(serverURL, jsonObj);
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
	    		
	            messageBox = builder.create();
	            messageBox.show();
	            imgGif.setVisibility(View.INVISIBLE);
	            relBtn.setEnabled(true); 
	            return;
			}
			JSONObject jsonObj = (JSONObject) result;
			Global globalInfo = (Global)getApplication();
			String user_id = "";
			String email = "";
			String phone = "";
			String country_id = "";
			
			try {
				if ( jsonObj.getString("result").equals("success")) {
					try {
						user_id =  jsonObj.getString("user_id");
						globalInfo.setUserId(user_id);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						email =  jsonObj.getString("email");
						globalInfo.setEmail(email);
						session.createLoginSession(user_id , email);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						phone =  jsonObj.getString("phone");
						globalInfo.setPhone(phone);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						country_id =  jsonObj.getString("country_id");
						globalInfo.setCountryId(country_id);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent i = new Intent(LoginActivity.this, HomeActivity.class);
					i.putExtra("user_id", user_id);
					globalInfo.setUserId(user_id);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					
					relBtn.setEnabled(true); 
					finish();
				}else
				{
						try {
							String errorMessage = jsonObj.get("msg").toString();
							messageBox.show();
							imgGif.setVisibility(View.INVISIBLE);
							relBtn.setEnabled(true); 
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

}


