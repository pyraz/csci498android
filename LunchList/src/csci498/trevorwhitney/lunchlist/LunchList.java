package csci498.trevorwhitney.lunchlist;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LunchList extends Activity {
	
	Restaurant restaurant = new Restaurant();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        Button save = (Button)findViewById(R.id.save_btn);
        save.setOnClickListener(onSave);
        
        //Create Radio Buttons in Java Code
        //pass in this as context to constructor
        RadioButton dine_in = new RadioButton(this);
        dine_in.setText("Dine-In");
        dine_in.setId(1);
        RadioButton take_out = new RadioButton(this);
        take_out.setText("Take-Out");
        take_out.setId(2);
        RadioButton delivery = new RadioButton(this);
        delivery.setText("Delivery");
        delivery.setId(3);
        
        RadioGroup types = (RadioGroup)findViewById(R.id.types);
        types.addView(take_out);
        types.addView(dine_in);
        types.addView(delivery);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {	
    	public void onClick(View v) {
			EditText name = (EditText)findViewById(R.id.name);
			EditText address = (EditText)findViewById(R.id.address);
			
			restaurant.setName(name.getText().toString());
			restaurant.setAddress(address.getText().toString());
			
			RadioGroup types = (RadioGroup)findViewById(R.id.types);
			
			switch (types.getCheckedRadioButtonId()) {
			case 1:
				restaurant.setType("dine_in");
				break;
			
			case 2:
				restaurant.setType("take_out");
				break;
				
			case 3:
				restaurant.setType("delivery");
				break;
			}
		}
	};

}
