package com.src.kickgifter;

import java.util.ArrayList;

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

public class ContactListActivity extends Activity {

	private static ListView lst_disp_contact;
	ArrayList<String> mContactName = new ArrayList<String>();
	ArrayList<String> mContactPhone = new ArrayList<String>();
	private ContactListAdapter mDispAdt;
	ListView listProjectAdd;
	ImageView imageItem;
	String phoneNumber;
	Global globalInfo;
	SessionManager session;
	RelativeLayout menuPanelBackContact;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		session =  new SessionManager(getApplicationContext());
		session.checkLogin();
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);
        //lst_disp_contact = (ListView)findViewById(R.id.list_all_contact);
        lst_disp_contact = (ListView)findViewById(R.id.list);
        globalInfo = (Global)getApplication();
        getContactList();
        menuPanelBackContact = (RelativeLayout) findViewById(R.id.menuPanelBackContact);
        lst_disp_contact.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
					Intent intent = new Intent(ContactListActivity.this, ProjectAddActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP);
					phoneNumber = ((TextView) view.findViewById(R.id.txt_contact_phone)).getText().toString();
					imageItem = (ImageView) view.findViewById(R.id.img_phone_number);
					imageItem.setBackgroundResource(R.drawable.icon_checkbox_selected_green);
			    	intent.putExtra("contactPhone", phoneNumber);
			    	globalInfo.setProviderProjectAdd(phoneNumber.replaceAll("\\D+",""));
			    	startActivity(intent);
			    	finish();
			}
		});
        menuPanelBackContact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				finish();
			}
		});
    }
	
	public void getContactList()
	{
		mContactName = globalInfo.getContactListName();
		mContactPhone = globalInfo.getContactListPhone();
		
		if (mDispAdt == null) {
			mDispAdt = new ContactListAdapter(ContactListActivity.this, mContactName , mContactPhone);
			lst_disp_contact.setAdapter(mDispAdt);
		} else {
			mDispAdt.notifyDataSetChanged();
		}
	}
	
}
