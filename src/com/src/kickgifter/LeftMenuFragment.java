
package com.src.kickgifter;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.src.kickgifter.Global;

//Left Menu 
public class LeftMenuFragment extends Fragment implements OnClickListener{
	static RelativeLayout rlLeftSignOut , rlLeftAdd , rlLeftHome , rlLeftProfile;
	Global globalInfo;
	Intent intent;
	SessionManager session;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.leftmenu, container, false);
		rlLeftAdd  = (RelativeLayout) rootView.findViewById(R.id.rl_left_add);
		rlLeftHome  = (RelativeLayout) rootView.findViewById(R.id.rl_left_home);
		rlLeftSignOut  = (RelativeLayout) rootView.findViewById(R.id.rl_left_signout);
		rlLeftProfile = (RelativeLayout) rootView.findViewById(R.id.rl_left_profile);
		
		rlLeftAdd.setOnClickListener(this);	
		rlLeftHome.setOnClickListener(this);
		rlLeftSignOut.setOnClickListener(this);
		rlLeftProfile.setOnClickListener(this);
		//return inflater.inflate(R.layout.leftmenu, container, false);
		session = new SessionManager(getActivity().getApplicationContext());
		return rootView;
	}
	public void onClick(View v) {
		globalInfo = (Global)getActivity().getApplication();
		
        switch (v.getId()) {
        case R.id.rl_left_add:
        	rlLeftAdd.setBackgroundColor(getResources().getColor(R.color.red));
        	rlLeftHome.setBackgroundColor(getResources().getColor(R.color.black));
        	rlLeftSignOut.setBackgroundColor(getResources().getColor(R.color.black));
        	rlLeftProfile.setBackgroundColor(getResources().getColor(R.color.black));
        	
        	intent = new Intent( this.getActivity(), ProjectAddActivity.class);
        	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	intent.putExtra("user_id", globalInfo.getUserId());
        	intent.putExtra("country_id", globalInfo.getCountryId());
        	intent.putExtra("contactPhone", globalInfo.getContactPhone() );
        	intent.putExtra("contacts", globalInfo.getContacts());
	    	startActivity(intent);
	    	break;
	    	
		case R.id.rl_left_home:
			rlLeftAdd.setBackgroundColor(getResources().getColor(R.color.black));
        	rlLeftHome.setBackgroundColor(getResources().getColor(R.color.red));
        	rlLeftSignOut.setBackgroundColor(getResources().getColor(R.color.black));
        	rlLeftProfile.setBackgroundColor(getResources().getColor(R.color.black));
        	
        	intent = new Intent( this.getActivity(), HomeActivity.class);
        	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	intent.putExtra("user_id", globalInfo.getUserId());
	    	startActivity(intent);
	        break;
	    
		case R.id.rl_left_signout:
			
			rlLeftAdd.setBackgroundColor(getResources().getColor(R.color.black));
        	rlLeftHome.setBackgroundColor(getResources().getColor(R.color.black));
        	rlLeftSignOut.setBackgroundColor(getResources().getColor(R.color.red));
        	rlLeftProfile.setBackgroundColor(getResources().getColor(R.color.black));
        	
        	globalInfo.setUserId("");
        	session.logoutUser();
        	intent = new Intent( this.getActivity(), LoginActivity.class);
        	startActivity(intent);
	        break;
	        
		case R.id.rl_left_profile:
			rlLeftAdd.setBackgroundColor(getResources().getColor(R.color.black));
        	rlLeftHome.setBackgroundColor(getResources().getColor(R.color.black));
        	rlLeftSignOut.setBackgroundColor(getResources().getColor(R.color.black));
        	rlLeftProfile.setBackgroundColor(getResources().getColor(R.color.red));
        	
        	intent = new Intent( this.getActivity(), ProfileActivity.class);
        	startActivity(intent);
	        break;   
        }
        
        
    }
	
}
