package com.src.kickgifter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class ContactsAddActivity extends Activity {

	private static ListView lst_disp_contact;
	ArrayList<String> mContactName = new ArrayList<String>();
	ArrayList<String> mContactPhone = new ArrayList<String>();
	
	RelativeLayout menuPanelDone,menuPanelBack;
 	Global globalInfo;
	private ContactListAdapter mDispAdt;
	ListView listProjectAdd;
	ImageView imageItem;
	String phoneNumber;
	String phoneContactNumber;
	String contacts = "";
	
	HashMap phones = new HashMap();
	HashMap contactName = new HashMap();
	
	SessionManager session;
	RelativeLayout rlContact,rlNewContact;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		session =  new SessionManager(getApplicationContext());
		session.checkLogin();
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        //lst_disp_contact = (ListView)findViewById(R.id.list_all_contact);
        globalInfo = (Global)getApplication(); 
        lst_disp_contact = (ListView)findViewById(R.id.list_contact);
        getContactList();
        rlContact = (RelativeLayout) findViewById(R.id.rlcontact);
        menuPanelBack = (RelativeLayout) findViewById(R.id.menuPanelBack);
        menuPanelDone = (RelativeLayout) findViewById(R.id.menuPanelDone);
        
        menuPanelDone.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(ContactsAddActivity.this , ProjectAddActivity.class);
				if(globalInfo.getInvitersProjectAdd().toString().equals("")){
					globalInfo.setInvitersProjectAdd(contacts);
				}else{
					globalInfo.setInvitersProjectAdd(globalInfo.getInvitersProjectAdd().toString()+","+contacts);
				}
				startActivity(intent);
				finish();
			}
		});
        
        menuPanelBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				finish();
			}
		});
        
        lst_disp_contact.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
					Intent intent = new Intent(ContactsAddActivity.this, ProjectAddActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP);
					//isert new element
					phoneNumber = ((TextView) view.findViewById(R.id.txt_contact_phone)).getText().toString();
					phoneContactNumber = ((TextView) view.findViewById(R.id.txt_contact_name)).getText().toString();
					rlNewContact = new RelativeLayout(getBaseContext());
					imageItem = (ImageView) view.findViewById(R.id.img_phone_number);
					
					imageItem.setBackgroundResource(R.drawable.icon_checkbox_selected_green);
					
					if (phones.containsKey(position))
					{
						phones.remove(position);
						contactName.remove(position);
						imageItem.setBackgroundResource(R.drawable.icon_checkbox_unselected_25x25);
						resetLayout();
					}else{
						phones.put( position , phoneNumber);
						contactName.put(position, phoneContactNumber);
						imageItem.setBackgroundResource(R.drawable.icon_checkbox_selected_green);
						resetLayout();
					}
			}
		});
    }
	public void resetLayout(){
			  // Get a set of the entries
		      Set phoneSet = phones.entrySet();
		      // Get an iterator
		      Iterator i = phoneSet.iterator();
		      // Display elements
		      contacts = "";
		      rlContact.removeAllViews();
		      while(i.hasNext()) {
		    	 Map.Entry me = (Map.Entry)i.next();
		         if(contacts.equals("")){
		        	 contacts = contacts + me.getValue().toString().replaceAll("\\D+","");
		         }else{
		        	 contacts = contacts +"," + me.getValue().toString().replaceAll("\\D+","");
		         }
		       }
	}
	public void getContactList()
	{
		mContactName = globalInfo.getContactListName();
		mContactPhone = globalInfo.getContactListPhone();
		if (mDispAdt == null) {
			mDispAdt = new ContactListAdapter(ContactsAddActivity.this, mContactName , mContactPhone);
			lst_disp_contact.setAdapter(mDispAdt);
		} else {
			mDispAdt.notifyDataSetChanged();
		}
	}
	
}
