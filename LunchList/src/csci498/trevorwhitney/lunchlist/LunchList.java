package csci498.trevorwhitney.lunchlist;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class LunchList extends TabActivity {
	
	static final int DIALOG_TOAST_ID = 0;
	static final int DIALOG_ERROR_ID = 1;
	
	List<Restaurant> restaurants = new ArrayList<Restaurant>();
	RestaurantAdapter adapter = null;
	Restaurant current = null;
	EditText name = null;
	EditText address = null;
	EditText notes = null;
	RadioGroup types = null;
	Exception exception = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	  try {
			super.onCreate(savedInstanceState);
		  setContentView(R.layout.activity_lunch_list);
		  
		  name = (EditText)findViewById(R.id.name);
		  address = (EditText)findViewById(R.id.address);
		  notes = (EditText)findViewById(R.id.notes);
		  types = (RadioGroup)findViewById(R.id.types);
		  
		  Button save = (Button)findViewById(R.id.save_btn);
		  save.setOnClickListener(onSave);
		  
		  ListView list = (ListView)findViewById(R.id.restaurant_list);
		  adapter = new RestaurantAdapter();
		  list.setAdapter(adapter);
		  list.setOnItemClickListener(onListClick);
		  
		  TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
		  spec.setContent(R.id.restaurant_list);
		  spec.setIndicator("List", getResources().getDrawable(
		  		R.drawable.list));
		  getTabHost().addTab(spec);
		  
		  spec = getTabHost().newTabSpec("tag2");
		  spec.setContent(R.id.details_form);
		  spec.setIndicator("Details", getResources().getDrawable(
		  		R.drawable.restaurant));
		  getTabHost().addTab(spec);
		  
		  getTabHost().setCurrentTab(0);
		  
		  //purposefully raise divide by zero exception
		  int error = 1/0;
	  }
	  catch (Exception e) {
			exception = e;
			showDialog(DIALOG_ERROR_ID);
	  }
	  
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.option, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.toast) {
			showDialog(DIALOG_TOAST_ID);
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch(id) {
		case DIALOG_TOAST_ID:
			String message = "No restuarant selected";
			
			dialog = new AlertDialog.Builder(this)
					.setMessage(message)
					.setCancelable(false)
					.setNeutralButton("Ok", 
							new DialogInterface.OnClickListener() {	
								public void onClick(DialogInterface dialog, 
										int which) {	dialog.dismiss(); }	
							})
					.create();
			break;
		case DIALOG_ERROR_ID:
			String error_message = "Information unavailable.";
			
			if (exception != null) {
				StackTraceElement[] stackTrace = exception.getStackTrace();
				error_message = "A " + exception.getMessage() + 
						" exception occured on line " + 
						stackTrace[0].getLineNumber() + ".";
			}	
			
			dialog = new AlertDialog.Builder(LunchList.this)
					.setCancelable(false)
					.setTitle("An Error Occured")
					.setNeutralButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, 
										int which) {	dialog.dismiss();	}
							})
					.setMessage(error_message)
					.create();
			break;
		default:
			dialog = null;
		}
		
		return dialog;
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		
		switch(id) {
		case DIALOG_TOAST_ID:
			if (current != null) {
				String message = current.getNotes();
				((AlertDialog)dialog).setMessage(message);
			}
			break;
		case DIALOG_ERROR_ID:
			if (exception != null) {
				StackTraceElement[] stackTrace = exception.getStackTrace();
				
				String error_message = "A " + exception.getMessage() + 
						" exception occured on line " + 
						stackTrace[0].getLineNumber() + ".";
				
				((AlertDialog)dialog).setMessage(error_message);
			}
		break;
		}
	}

	private View.OnClickListener onSave = new View.OnClickListener() {	
		public void onClick(View v) {
    	current = new Restaurant();
			current.setName(name.getText().toString());
			current.setAddress(address.getText().toString());
			current.setNotes(notes.getText().toString());

			switch (types.getCheckedRadioButtonId()) {
			case R.id.type_in:
				current.setType("dine_in");
				break;
			
			case R.id.type_out:
				current.setType("take_out");
				break;
				
			case R.id.type_del:
				current.setType("delivery");
				break;
			}
			
			adapter.add(current);
			
			//clear form for next entry
			name.setText("");
			address.setText("");
			types.check(R.id.type_out);
		}
	};
	
	private AdapterView.OnItemClickListener onListClick = new
			AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent,
						View view, int position, long id) {
					current = restaurants.get(position);
					name.setText(current.getName());
					address.setText(current.getAddress());
					notes.setText(current.getNotes());
					
					if (current.getType().equals("dine_in")) {
						types.check(R.id.type_in);
					}
					else if (current.getType().equals("take_out")) {
						types.check(R.id.type_out);
					}
					else {
						types.check(R.id.type_del);
					}
					
					getTabHost().setCurrentTab(1);
				}
			};
	
	class RestaurantAdapter extends ArrayAdapter<Restaurant> {
		
		RestaurantAdapter() {
			super(LunchList.this,
					android.R.layout.simple_list_item_1,
					restaurants);
		}
		
		public View getView(int position, View convertView,
				ViewGroup parent) {
			View row = convertView;
			RestaurantHolder holder = null;
			
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.restuarant_row, null);
				holder = new RestaurantHolder(row);
				row.setTag(holder);
			}
			else {
				holder = (RestaurantHolder)row.getTag();
			}
			
			holder.populateList(restaurants.get(position));
			
			return row;
		}
		
	}
	
	static class RestaurantHolder {
		private TextView name = null;
		private TextView address = null;
		private ImageView icon = null;
		
		RestaurantHolder(View row) {
			name = (TextView)row.findViewById(R.id.name);
			address = (TextView)row.findViewById(R.id.address);
			icon = (ImageView)row.findViewById(R.id.icon);
		}
		
		void populateList(Restaurant restaurant) {
			name.setText(restaurant.getName());
			address.setText(restaurant.getAddress());
			
			if (restaurant.getType().equals("dine_in")) {
				icon.setImageResource(R.drawable.ball_red);
			}
			else if (restaurant.getType().equals("take_out")) {
				icon.setImageResource(R.drawable.ball_yellow);
			}
			else {
				icon.setImageResource(R.drawable.ball_green);
			}
		}
	}

}
