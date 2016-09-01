package com.src.kickgifter;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.src.kickgifter.AlertDialogManager;
import com.src.kickgifter.LoginActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class InviteAddActivity extends Activity {
	
	ListView listGiftItem;
	Global globalInfo;
	private String serverURL;
	static InputStream is = null;
	static JSONObject jObj = null;
	String json;
	String data =""; 
	EditText editInviterProject;
	Button addInviterBtn ;
	String strAddInviters;
	String projectId,strInviterProject;
	AlertDialog.Builder builder;
	AlertDialog alert;
	SessionManager session;
	RelativeLayout slidingProjectAdd;
	TextView rlInviterAdd;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		session =  new SessionManager(getApplicationContext());
		session.checkLogin();
		builder = new AlertDialog.Builder(this);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inviter_add);
        serverURL = Constants.Server_Inviter_Add;
         
        addInviterBtn = (Button) findViewById(R.id.btnAddInviters);
        globalInfo = (Global)getApplication();
        editInviterProject = (EditText) findViewById(R.id.editInviterProject);
        editInviterProject.setText(globalInfo.getInvitersProjectAdd().toString());
        slidingProjectAdd = (RelativeLayout) findViewById(R.id.slidingProjectAdd );
        rlInviterAdd = (TextView) findViewById(R.id.inviterAdd);
        
        addInviterBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				/*mGiftIds , projectId submit */
				projectId = globalInfo.getProjectId();
				strInviterProject = editInviterProject.getText().toString();
			    
				if(!(strInviterProject.equals(""))){
					editInviterProject.setEnabled(false);
					addInviterBtn.setEnabled(false);
					rlInviterAdd.setEnabled(false);
			    	projectInvite(projectId , strInviterProject);
			    }else{
			    	AlertDialogManager alert = new AlertDialogManager();
			    	
			    	alert.showAlertDialog(InviteAddActivity.this, Constants.messageInputErrorTitle, Constants.messageInputErrorMessage, false);
				}
			}
			
		});
        slidingProjectAdd.setOnTouchListener(new OnTouchListener() {
		    @Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Utils.hideSoftKeyboard(InviteAddActivity.this);
				return false;
			}
		});
        rlInviterAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InviteAddActivity.this , ContactListInviteActivity.class);
				globalInfo.setInvitersProjectAdd(editInviterProject.getText().toString());
				startActivity(intent);
				
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
        
    }
	public void projectInvite(String projectId , String strInviterProject)
	{
		
		new ServiceTask().execute(projectId,strInviterProject);
	}
	
	    
   class ServiceTask extends AsyncTask<String, String, Object> {

		@Override
		protected Object doInBackground(String... params) {
			
			String projectId = params[0];
			String strInviterProject = params[1];
			
			JSONObject jsonObj = new JSONObject();
			JSONObject result = null;
			try {
				jsonObj.put("project_id", projectId.trim());
				jsonObj.put("invitors", strInviterProject.trim());
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
	    		editInviterProject.setEnabled(true);
	    		return;
    		}
    		JSONObject jsonObj = (JSONObject) result;
    		try {
				if ( jsonObj.getString("result").equals("success")) {
					builder.setMessage(Constants.messageProjectList);
					builder.setCancelable(true);
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                Intent intent = new Intent(InviteAddActivity.this,ProjectDetailActivity.class);
			                editInviterProject.setEnabled(true);
			                globalInfo.setInvitersProjectAdd("");
			                startActivity(intent);
			                finish();
			            }
			        });
					alert = builder.create();
					alert.show();
					
				}else{
					String errorMessage = jsonObj.get("msg").toString();
					AlertDialogManager alert = new AlertDialogManager();
		            alert.showAlertDialog(InviteAddActivity.this, "Failed", errorMessage, false);
		        }
			} catch (JSONException e) {
				AlertDialogManager alert = new AlertDialogManager();
				alert.showAlertDialog(InviteAddActivity.this, "failed", Constants.messageProjectListFailed, false);
				
			}
    		globalInfo.setInvitersProjectAdd("");
    		editInviterProject.setEnabled(false);
    		rlInviterAdd.setEnabled(true);
    		addInviterBtn.setEnabled(true);
    		
    		
		}
   }
   
}
