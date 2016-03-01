package ro.pub.cs.systems.pdsd.lab02.graphicuserinterface;

import ro.pub.cs.systems.pdsd.lab02.R;
import ro.pub.cs.systems.pdsd.lab02.general.Constants;
import ro.pub.cs.systems.pdsd.lab02.general.Utilities;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;

public class LifecycleMonitorActivity extends Activity {
	
	private ButtonClickListener buttonClickListener = new ButtonClickListener();
	
	private class ButtonClickListener implements Button.OnClickListener {

		@Override
		@SuppressWarnings("all")
		public void onClick(View view) {
			EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
			EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
			if (getResources().getString(R.string.ok_button_content).equals(((Button)view).getText().toString())) {

				LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  		
				
				if (Utilities.allowAccess(getApplicationContext(), usernameEditText.getText().toString(), passwordEditText.getText().toString())) {
				    View popupContent = layoutInflater.inflate(R.layout.popup_window_authentication_success, null);  
				    final PopupWindow popupWindow = new PopupWindow(popupContent, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
				             
				    Button dismissButton = (Button)popupContent.findViewById(R.id.dismiss_button);
				    dismissButton.setOnClickListener(new Button.OnClickListener(){	
				    	@Override
				    	public void onClick(View view) {
				    		popupWindow.dismiss();
				    	}
				    });
					popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);	
				} else {
				    View popupContent = layoutInflater.inflate(R.layout.popup_window_authentication_fail, null);  
				    final PopupWindow popupWindow = new PopupWindow(popupContent, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
				             
				    Button dismissButton = (Button)popupContent.findViewById(R.id.dismiss_button);
				    dismissButton.setOnClickListener(new Button.OnClickListener(){	
				    	@Override
				    	public void onClick(View view) {
				    		popupWindow.dismiss();
				    	}
				    });
					popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);					
				}
			}
			if (getResources().getString(R.string.cancel_button_content).equals(((Button)view).getText().toString())) {
				usernameEditText.setText(getResources().getString(R.string.empty));
				passwordEditText.setText(getResources().getString(R.string.empty));
			}
		}
		
	}
	
	boolean started = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_monitor);
        
        Button okButton = (Button)findViewById(R.id.ok_button);
        okButton.setOnClickListener(buttonClickListener);
        Button cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);
        if (!started)
        	Log.d(Constants.TAG, "onCreate() first time running");
        else
        	Log.d(Constants.TAG, "onCreate() activity was started earlier");
    
		EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
		EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
		
		if (savedInstanceState.getBoolean(Constants.CREDENTIAL_TAG)) {
			usernameEditText.setText(savedInstanceState.getCharArray(Constants.USERNAME_TAG).toString());
			passwordEditText.setText(savedInstanceState.getCharArray(Constants.PASSWORD_TAG).toString());
		}
    }    

    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
        Log.d(Constants.TAG, "onDestroy() method was invoked");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
        Log.d(Constants.TAG, "onPause() method was invoked");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
		EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
		
		if (savedInstanceState.getBoolean(Constants.CREDENTIAL_TAG)) {
			usernameEditText.setText(savedInstanceState.getCharArray(Constants.USERNAME_TAG).toString());
			passwordEditText.setText(savedInstanceState.getCharArray(Constants.PASSWORD_TAG).toString());
			
			System.out.println(savedInstanceState.getCharArray(Constants.USERNAME_TAG).toString());
			System.out.println(savedInstanceState.getCharArray(Constants.PASSWORD_TAG).toString());
			
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		CheckBox checkbox = (CheckBox)findViewById(R.id.remember_me_checkbox);
		if (checkbox.isChecked()) {
			EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
			EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
			
			outState.putString(Constants.USERNAME_TAG, usernameEditText.getText().toString());
			outState.putString(Constants.PASSWORD_TAG, passwordEditText.getText().toString());
			outState.putBoolean(Constants.CREDENTIAL_TAG, true);
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		started = false;
        Log.d(Constants.TAG, "onRestart() method was invoked");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		started = true;
        Log.d(Constants.TAG, "onResume() method was invoked");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
        Log.d(Constants.TAG, "onStart() method was invoked");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
        Log.d(Constants.TAG, "onStop() method was invoked");
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
