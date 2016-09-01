package com.src.kickgifter;
import java.io.InputStream;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Button;

public class HomeActivity extends FragmentActivity {

	// Declare
	private LinearLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;
	private RelativeLayout headerPanel;
	private int panelWidth;
	private int panelWidth1;
	private String serverURL;
	private String contactList = "" ;
	
	private ProjectListAdapter mDispAdt,mdispAdtExpired;
	private static ListView lst_disp_project_now;
	private static ListView lst_disp_project_expired;
	static InputStream is = null;
	static JSONObject jObj = null;
	String json;
	String data =""; 
    int sizeData = 0;
	private ImageView menuRightButton;
	Button btnNowButton , btnExpiredButton;
	ImageView imgGoToAdd;
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters;
	LinearLayout.LayoutParams listViewParameters;
	Bundle extras;
	
	ArrayList<String> mNowProjectName = new ArrayList<String>();
	ArrayList<String> mNowProjectAmount = new ArrayList<String>();
	ArrayList<String> mNowProjectExpired = new ArrayList<String>();
	ArrayList<String> mNowProjectMessage = new ArrayList<String>();
	ArrayList<String> mExpiredProjectName = new ArrayList<String>();
	ArrayList<String> mExpiredProjectAmount = new ArrayList<String>();
	ArrayList<String> mExpiredProjectDate = new ArrayList<String>();
	ArrayList<String> mExpiredProjectMessage = new ArrayList<String>();
	ArrayList<String> mNowProjectCrowded =  new ArrayList<String>();
	ArrayList<String> mExpiredProjectCrowded = new ArrayList<String>();
	ArrayList<String> contactListPhoneNumber = new ArrayList<String>();
	ArrayList<String> contactListName = new ArrayList<String>();
	
	
	ListView listViewNow , listViewExpired;
	ArrayList<String> projectIdNow = new ArrayList<String>();
	ArrayList<String> projectIdExpired = new ArrayList<String>();
	Global globalInfo;
	SessionManager session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		session =  new SessionManager(getApplicationContext());
		session.checkLogin();
		globalInfo = (Global) getApplication();
		if(globalInfo.getUserId().toString().equals("")){
			Intent intent = new Intent(this,LoginActivity.class);
			startActivity(intent);
			finish();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		
		String user_id = globalInfo.getUserId().toString();
		serverURL = Constants.Server_Project_List;
		
		lst_disp_project_now = (ListView)findViewById(R.id.list_project_now);
		lst_disp_project_expired = (ListView)findViewById(R.id.list_project_expired);
		
		imgGoToAdd = (ImageView) findViewById(R.id.menuGoToAdd);
		// Initialize
		
		projectIdNow.clear();
		projectIdExpired.clear();
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

		// Slide the Panel
		btnNowButton = (Button) findViewById(R.id.btnNow);
		btnExpiredButton = (Button) findViewById(R.id.btnExpired);
		lst_disp_project_now.setVisibility(View.VISIBLE);
		lst_disp_project_expired.setVisibility(View.INVISIBLE);
		btnExpiredButton.setTextColor(getResources().getColor(R.color.blue));
		btnNowButton.setTextColor(getResources().getColor(R.color.white));
		
		btnNowButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				lst_disp_project_now.setVisibility(View.VISIBLE);
				lst_disp_project_expired.setVisibility(View.INVISIBLE);
				btnNowButton.setBackgroundResource(R.drawable.button_click);
				btnExpiredButton.setBackgroundResource(R.drawable.button);
				btnExpiredButton.setTextColor(getResources().getColor(R.color.blue));
				btnNowButton.setTextColor(getResources().getColor(R.color.white));
			}
			
		});
		imgGoToAdd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(HomeActivity.this,ProjectAddActivity.class);
				startActivity(intent);
				finish();
			}
			
		});
		btnExpiredButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				lst_disp_project_now.setVisibility(View.GONE);
				lst_disp_project_expired.setVisibility(View.VISIBLE);
				btnExpiredButton.setTextColor(getResources().getColor(R.color.white));
				btnNowButton.setTextColor(getResources().getColor(R.color.blue));
				btnNowButton.setBackgroundResource(R.drawable.button);
				btnExpiredButton.setBackgroundResource(R.drawable.button_click);
			}
			
		});
		
		lst_disp_project_now.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					Intent i = new Intent(HomeActivity.this, ProjectDetailActivity.class);
			    	i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    	globalInfo.setProjectId(projectIdNow.get(position));
			    	startActivity(i);
			}
		});
		lst_disp_project_expired.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					Intent i = new Intent(HomeActivity.this, ProjectDetailActivity.class);
			    	i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    	globalInfo.setProjectId(projectIdExpired.get(position));
			    	startActivity(i);
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

		
		getProjectList(user_id);
		getContactList();
	}
	public void getProjectList(String user_id){
		new ServiceTask().execute(user_id);
	}
	public void getContactList(){
		String phoneNumber = null;
		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
		
		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
		ContentResolver contentResolver = getContentResolver();
		
		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null); 
		
		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {
		
			while (cursor.moveToNext()) {
			
				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
			
				if (hasPhoneNumber > 0) {
				
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
					
					while (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						String contactName = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.DISPLAY_NAME));
						contactListPhoneNumber.add(phoneNumber);
						contactListName.add(contactName);
						if(contactList.equals("")){
							contactList = contactList + phoneNumber;
						}else{
							contactList = contactList + "," + phoneNumber;
						}
					}
					
					
					phoneCursor.close();
				}
			}
		}
		globalInfo.setContactListPhone(contactListPhoneNumber);
		globalInfo.setContactListName(contactListName);
		globalInfo.setContactList(contactList);
		
	}    
   class ServiceTask extends AsyncTask<String, String, Object> {

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			String userId = params[0];
			/* Check the validation */
			
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("user_id", userId.trim());
				jsonObj.put("type", "0");
				
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
	            alert.showAlertDialog(HomeActivity.this, Constants.messageConnectionTitle, Constants.messageConnectionFailed, false);
	            return;
	        }
			JSONObject jsonObj = (JSONObject) result;
			String is_expired = "0";
			int countJsonData = 0 ;
			String userName = "";
			String userAmount = "" ;
			String expiredDate = "";
			String projectId = "" ;
			String crowdedAmount = "";
			String message = "";
			 
			try {
				 countJsonData = jsonObj.getJSONArray("projects").length();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0 ; i < countJsonData;i++){
				JSONObject objectProject = null;
				try {
					objectProject = jsonObj.getJSONArray("projects").getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					userName = objectProject.getString("name");
					userAmount = objectProject.getString("amount");
					is_expired = objectProject.getString("is_expired");
					crowdedAmount = objectProject.getString("crowded_amount");
					expiredDate = objectProject.getString("expired_at");
					message = objectProject.getString("message");
					projectId = objectProject.getString("id");
					if(is_expired.equals("0")){
						mNowProjectName.add(userName);
						mNowProjectAmount.add(userAmount);
						mNowProjectExpired.add(expiredDate);
						projectIdNow.add(projectId);
						mNowProjectCrowded.add(crowdedAmount);
						mNowProjectMessage.add(message);
					}
					if(is_expired.equals("1")){
						mExpiredProjectName.add(userName);
						mExpiredProjectAmount.add(userAmount);
						mExpiredProjectDate.add(expiredDate);
						projectIdExpired.add(projectId);
						mExpiredProjectCrowded.add(crowdedAmount);
						mExpiredProjectMessage.add(message);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (mDispAdt == null) {
				mDispAdt = new ProjectListAdapter(HomeActivity.this, mNowProjectName , mNowProjectAmount ,mNowProjectCrowded , mNowProjectExpired , mNowProjectMessage);
				lst_disp_project_now.setAdapter(mDispAdt);
				mdispAdtExpired =  new ProjectListAdapter(HomeActivity.this, mExpiredProjectName , mExpiredProjectAmount,mExpiredProjectCrowded , mExpiredProjectDate , mExpiredProjectMessage);
				lst_disp_project_expired.setAdapter(mdispAdtExpired);
			} else {
				mDispAdt.notifyDataSetChanged();
			}
		}
   }
}
