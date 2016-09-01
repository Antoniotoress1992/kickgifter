
package com.src.kickgifter;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.src.kickgifter.SignUpActivity.PhoneNumberValidator;
import com.src.kickgifter.SignUpActivity.ServiceTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ProjectAddActivity extends FragmentActivity {
	// Declare
	private LinearLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;
	private RelativeLayout headerPanel;
	private int panelWidth;
	private int panelWidth1;

	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters;
	LinearLayout.LayoutParams listViewParameters;
	private CaldroidFragment dialogCaldroidFragment;
	Intent intent;
	RelativeLayout addButton;
	EditText editProviderTelephone;
	EditText editInviter;
	EditText editProjectName;
	EditText editCountry;
	EditText editAmount;
	EditText editMessage;
	EditText editExpired;
	TextView addInviterTxt;
	Button btnAddTelephone;
	String json;
	String data =""; 
	String serverURL = "" ;
	RelativeLayout rlProviderTelephone , rlProjectCountry , rlProjectExpired , rlProjectInviter , slidingProjectAdd;
	Global globalInfo;
	AlertDialog.Builder mSelectLanguageDialog , mSigleDialog;
	String[] country;
	String[] countryId;
	String countrySelectedId;
	private ImageView menuRightButton;
	String strProjectName , strCountry , strAmount , strMessage , strEditMessage;
	String strExpired , strProviderTelephone , strInviter , user_id ;
	SessionManager session;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		session =  new SessionManager(getApplicationContext());
		session.checkLogin();
		final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		globalInfo = (Global) getApplication();
		if(globalInfo.getUserId().toString().equals("")){
			Intent intent = new Intent(this,LoginActivity.class);
			startActivity(intent);
			finish();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_add);
		//init activity
		
		addButton = (RelativeLayout) findViewById(R.id.relAddButton);
		rlProviderTelephone = (RelativeLayout)  findViewById(R.id.rlProviderTelephoneProject);
		rlProjectCountry = (RelativeLayout) findViewById(R.id.btnProjectCountry);
		rlProjectExpired = (RelativeLayout) findViewById(R.id.btnProjectExpired);
		rlProjectInviter = (RelativeLayout) findViewById(R.id.btnProjectInviter);
		// Initialize
		editProviderTelephone = (EditText) findViewById(R.id.editProviderTelephoneProjectAdd);
		
		editInviter = (EditText) findViewById(R.id.editInviterProject);
		editProjectName = (EditText) findViewById(R.id.editProjectName);
		editCountry = (EditText) findViewById(R.id.editCountry);
		editAmount = (EditText) findViewById(R.id.editAmount);
		editMessage = (EditText) findViewById(R.id.editMessage);
		editExpired = (EditText) findViewById(R.id.editExpired);
		
		addInviterTxt = (TextView) findViewById(R.id.addInviterTxt);
		mSelectLanguageDialog = new AlertDialog.Builder(this);
		mSigleDialog = new AlertDialog.Builder(this);
		
		btnAddTelephone = (Button) findViewById(R.id.addTelephoneBtn);
				
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
		slidingProjectAdd = (RelativeLayout) findViewById(R.id.slidingProjectAdd);
		
		slidingProjectAdd.setOnTouchListener(new OnTouchListener() {
			    @Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					Utils.hideSoftKeyboard(ProjectAddActivity.this);
					return false;
				}
		});
		editProviderTelephone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				set_globalInfoProjectAdd();
				Intent intent = new Intent(ProjectAddActivity.this , ContactListActivity.class);
				startActivity(intent);
			}
		});
		
		addInviterTxt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				set_globalInfoProjectAdd();
				Intent intent = new Intent(ProjectAddActivity.this , ContactsAddActivity.class);
				globalInfo.setInvitersProjectAdd(editInviter.getText().toString());
				startActivity(intent);
				
			}
		});
		editProjectName.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		    	if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
		        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	editAmount.requestFocus();
	              return true;
		        }
		        return false;
		    }
		});
		editProviderTelephone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				set_globalInfoProjectAdd();
				Intent intent = new Intent(ProjectAddActivity.this , ContactListActivity.class);
				startActivity(intent);
			}
		});
		editAmount.setOnKeyListener(new OnKeyListener() {
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		    	if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
		        if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        	editMessage.requestFocus();
	              return true;
		        }
		        return false;
		    }
		});
		
		editCountry.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				AlertDialog alert = mSelectLanguageDialog.create();
				alert.show();
			}
		});
		
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setData();
				user_id = globalInfo.getUserId();
				if(!validationCheck()){
	        		AlertDialogManager alertError = new AlertDialogManager();
	        		alertError.showAlertDialog(ProjectAddActivity.this, "Input Error", strMessage , false);
	        	}else{
	        		addButton.setFocusable(false);
	        		new ServiceTask().execute(user_id, strProjectName , strProviderTelephone , countrySelectedId , strAmount , strEditMessage , strExpired , strInviter);
	    		}
			}
		});
		
		mSelectLanguageDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

			@Override
				   public void onClick(DialogInterface dialog, int which) {
				   // TODO Auto-generated method stub
				   }
		});
		final EditText showCalendarDate = (EditText) findViewById(R.id.editExpired);		
		final CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				Toast.makeText(getApplicationContext(), formatter.format(date),
						Toast.LENGTH_SHORT).show();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String DateToStr = format.format(date);
				showCalendarDate.setText(DateToStr);
				dialogCaldroidFragment.dismiss();
			}
			@Override
			public void onCaldroidViewCreated() {
				
					Toast.makeText(getApplicationContext(),
							"Caldroid view is created", Toast.LENGTH_SHORT)
							.show();
			}

		};
		final Bundle state = savedInstanceState;
		editExpired.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Setup caldroid to use as dialog
				dialogCaldroidFragment = new CaldroidFragment();
				dialogCaldroidFragment.setCaldroidListener(listener);

				// If activity is recovered from rotation
				final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
				if (state != null) {
					dialogCaldroidFragment.restoreDialogStatesFromKey(
							getSupportFragmentManager(), state,
							"DIALOG_CALDROID_SAVED_STATE", dialogTag);
					Bundle args = dialogCaldroidFragment.getArguments();
					if (args == null) {
						args = new Bundle();
						dialogCaldroidFragment.setArguments(args);
					}
					args.putString(CaldroidFragment.DIALOG_TITLE,
							"Select a date");
				} else {
					// Setup arguments
					Bundle bundle = new Bundle();
					// Setup dialogTitle
					bundle.putString(CaldroidFragment.DIALOG_TITLE,
							"Select a date");
					dialogCaldroidFragment.setArguments(bundle);
				}

				dialogCaldroidFragment.show(getSupportFragmentManager(),
						dialogTag);
				
			}
		});
		menuRightButton = (ImageView) findViewById(R.id.menuViewButton);
		menuRightButton.setOnClickListener(new OnClickListener() {
			
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
		
		//init and set globalInfo
		init_ProjectAdd();	
		///init call service
		new ServiceLanguage().execute();
	}
	protected void onResume(Bundle savedInstanceState){
		super.onResume();
		init_ProjectAdd();
	}
	public void set_globalInfoProjectAdd() {
		// TODO Auto-generated method stub
		globalInfo.setProjectNameAdd(editProjectName.getText().toString()) ;
		globalInfo.setProviderProjectAdd(editProviderTelephone.getText().toString() );
		globalInfo.setCountryProjectAdd(editCountry .getText().toString());
		globalInfo.setAmountProjectAdd(editAmount .getText().toString());
		globalInfo.setMessageProjectAdd(editMessage .getText().toString());
		globalInfo.setExpiredProjectAdd(editExpired .getText().toString());
		globalInfo.setInvitersProjectAdd(editInviter.getText().toString());
	}
	public void init_ProjectAdd(){
		editProjectName.setText(globalInfo.getProjectNameAdd());
		editProviderTelephone.setText(globalInfo.getProviderProjectAdd());
		editCountry.setText(globalInfo.getCountryProjectAdd());
		editAmount.setText(globalInfo.getAmountProjectAdd());
		editMessage.setText(globalInfo.getMessageProjectAdd());
		editExpired.setText(globalInfo.getExpiredProjectAdd());
		editInviter.setText(globalInfo.getInvitersProjectAdd());
		countrySelectedId = globalInfo.getCoutryIdProjectAdd();
	}
	
	public void format_projectAdd(){
		globalInfo.setProjectNameAdd("") ;
		globalInfo.setProviderProjectAdd("");
		globalInfo.setCountryProjectAdd("");
		globalInfo.setAmountProjectAdd("");
		globalInfo.setMessageProjectAdd("");
		globalInfo.setExpiredProjectAdd("");
		globalInfo.setInvitersProjectAdd("");
		
	}
	public void setData(){
		strProjectName = editProjectName.getText().toString();
		strCountry = editCountry.getText().toString();
		strAmount = editAmount.getText().toString();
		strEditMessage = editMessage.getText().toString();
		strExpired = editExpired.getText().toString();
		strProviderTelephone = editProviderTelephone.getText().toString();
		strInviter = editInviter.getText().toString();
		countrySelectedId = globalInfo.getCoutryIdProjectAdd();
	}
	
	public Boolean  validationCheck(){
			if(strProjectName.equals("")){strMessage = Constants.messageProjectNameAddFailed;return false;}
			if(strCountry.equals("")){strMessage = Constants.messageProjectCountryAddFailed;return false;}
			
			PhoneNumberValidator phoneCheck = new PhoneNumberValidator();
			if(!phoneCheck.isValidPhoneNumber(strProviderTelephone)){
				strMessage = "Please input phonenumber correctly";
				return false;}
			strProviderTelephone  = strProviderTelephone.replaceAll("\\D+","");
			if(strCountry.equals("")){strMessage = Constants.messageProjectCountryAddFailed;return false;}
			if(strInviter.equals("") ){strMessage = Constants.messageProjectInvitersAddFailed;return false;}
			if(strEditMessage.equals("") ){strMessage = Constants.messageProjectMessageAddFailed;return false;}
			if(strEditMessage.equals("") ){strMessage = Constants.messageProjectMessageAddFailed;return false;}
			if(!LoginActivity.isNumeric(strAmount)){
				strMessage = Constants.messageProjectAmountAddFailed;return false;
			}
		return true;
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
    			AlertDialogManager alert = new AlertDialogManager();
	            alert.showAlertDialog(ProjectAddActivity.this, Constants.messageConnectionTitle, Constants.messageConnectionFailed, false);
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
						}
						mSelectLanguageDialog.setTitle("Language Selection");
						mSelectLanguageDialog.setCancelable(true);
						mSelectLanguageDialog.setSingleChoiceItems(country, -1, new DialogInterface.OnClickListener() {
						    
							@Override
							public void onClick(DialogInterface dialog, int which) {
							     // TODO Auto-generated method stub
							     int nIndex = which;
							     countrySelectedId = countryId[nIndex];
							     globalInfo.setCoutryIdProjectAdd(countrySelectedId);
							     editCountry.setText(country[nIndex]);
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
    class ServiceTask extends AsyncTask<String, String, Object> {

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			String user_id = params[0];
			String strProjectName = params[1];
			String strProviderTelephone = params[2];
			String strCountry = params[3];
			String strAmount = params[4];
			String strMessage = params[5];
			String strExpired = params[6];
			String strInviter = params[7];
			
			JSONObject jsonObj = new JSONObject();
			try {
				
				jsonObj.put("user_id", user_id.trim());
				jsonObj.put("name", strProjectName.trim());
				jsonObj.put("receiver_tel", strProviderTelephone.trim());
				jsonObj.put("country_id", strCountry.trim());
				jsonObj.put("amount", strAmount.trim());
				jsonObj.put("message", strMessage.trim());
				jsonObj.put("expired_at", strExpired.trim());
				jsonObj.put("invitors", strInviter.trim());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			serverURL = Constants.Server_Project_Add;
			JSONObject result = LoginActivity.getJSONFromPost(serverURL, jsonObj);
			return result;
		}
    	@Override
		protected void onPostExecute(Object result) {
    		if(result == null){
    			AlertDialogManager alert = new AlertDialogManager();
	            alert.showAlertDialog(ProjectAddActivity.this, Constants.messageConnectionTitle, Constants.messageConnectionFailed, false);
	            return;
   			}
			JSONObject jsonObj = (JSONObject) result;
			try {
				if ( jsonObj.getString("result").equals("success")) {
					mSigleDialog.setMessage(Constants.messageProjectAdd);
					mSigleDialog.setCancelable(true);
					mSigleDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                Intent intent = new Intent(ProjectAddActivity.this , HomeActivity.class);
							startActivity(intent);
							globalInfo.setInvitersProjectAdd("");
							finish();
			            }
			        });
					AlertDialog alert = mSigleDialog.create();
					alert.show();
					format_projectAdd();
					addButton.setFocusable(true);
					
				}else
				{
						try {
							String errorMessage = jsonObj.get("msg").toString();
							mSigleDialog.setMessage(errorMessage);
							mSigleDialog.setCancelable(true);
							mSigleDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog, int id) {
					                dialog.cancel();
					                globalInfo.setInvitersProjectAdd("");
					            }
					        });
							mSigleDialog.show();
							addButton.setFocusable(true);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				format_projectAdd();
				addButton.setFocusable(true);
				globalInfo.setInvitersProjectAdd("");  
			}
	}
	
    
}
