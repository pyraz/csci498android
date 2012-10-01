package csci498.trevorwhitney.lunchlist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class DetailForm extends Activity {
	EditText name = null;
	EditText address = null;
	EditText notes = null;
	RadioGroup types = null;
	EditText feed = null;
	RestaurantHelper helper = null;
	String restaurantId = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_form);
		helper = new RestaurantHelper(this);
		
		name = (EditText)findViewById(R.id.name);
	  address = (EditText)findViewById(R.id.address);
	  notes = (EditText)findViewById(R.id.notes);
	  types = (RadioGroup)findViewById(R.id.types);
	  feed = (EditText)findViewById(R.id.feed);
	  
	  Button save = (Button)findViewById(R.id.save_btn);
	  save.setOnClickListener(onSave);
	  
	  restaurantId = getIntent().getStringExtra(LunchList.ID_EXTRA);
	  if (restaurantId != null) {
	  	loadRestaurant();
	  }
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		helper.close();
	}
	
	@Override
	public void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
		
		state.putString("name", name.getText().toString());
		state.putString("address", address.getText().toString());
		state.putString("notes", notes.getText().toString());
		state.putInt("type", types.getCheckedRadioButtonId());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		name.setText(savedInstanceState.getString("name"));
		address.setText(savedInstanceState.getString("address"));
		notes.setText(savedInstanceState.getString("notes"));
		types.check(savedInstanceState.getInt("type"));
	}
	
	public void loadRestaurant() {
		Cursor c = helper.getById(restaurantId);
		
		c.moveToFirst();
		name.setText(helper.getName(c));
		address.setText(helper.getAddress(c));
		notes.setText(helper.getNotes(c));
		feed.setText(helper.getFeed(c));
		
		if (helper.getType(c).equals("dine_in")) {
			types.check(R.id.type_in);
		}
		else if (helper.getType(c).equals("take_out")) {
			types.check(R.id.type_out);
		}
		else {
			types.check(R.id.type_del);
		}
	}
	
	private View.OnClickListener onSave = new View.OnClickListener() {	
		public void onClick(View v) {
    	String type = null;

			switch (types.getCheckedRadioButtonId()) {
			case R.id.type_in:
				type = "dine_in";
				break;
			
			case R.id.type_out:
				type = "take_out";
				break;
				
			case R.id.type_del:
				type = "delivery";
				break;
			}
			
			if (restaurantId == null) {
				helper.insert(name.getText().toString(), 
						address.getText().toString(), type, 
						notes.getText().toString(), feed.getText().toString());
			}
			else {
				helper.update(restaurantId, name.getText().toString(), 
						address.getText().toString(), type, 
						notes.getText().toString(), feed.getText().toString());
			}
			
			finish();
		}
	};
}