package csci498.trevorwhitney.lunchlist;

import android.app.Activity;
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
	  
	  Button save = (Button)findViewById(R.id.save_btn);
	  save.setOnClickListener(onSave);
	  
	  restaurantId = getIntent().getStringExtra(LunchList.ID_EXTRA);
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
			
			//clear form for next entry
			name.setText("");
			address.setText("");
			types.check(R.id.type_out);
		}
	};
}