package csci498.trevorwhitney.lunchlist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

public class LunchList extends Activity {
	
	List<Restaurant> restaurants = new ArrayList<Restaurant>();
	ArrayAdapter<Restaurant> adapter = null;
	ArrayAdapter<String> address_adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        Button save = (Button)findViewById(R.id.save_btn);
        save.setOnClickListener(onSave);
        
        ListView list = (ListView)findViewById(R.id.restaurant_list);
        adapter = new ArrayAdapter<Restaurant>(this,
        		android.R.layout.simple_list_item_1,
        		restaurants);
        list.setAdapter(adapter);
        
        AutoCompleteTextView address_view = (AutoCompleteTextView)
        		findViewById(R.id.address);
        address_adapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_dropdown_item_1line,
        		getAddresses());
        address_view.setAdapter(address_adapter);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {	
    	public void onClick(View v) {
    		Restaurant restaurant = new Restaurant();
			EditText name = (EditText)findViewById(R.id.name);
			EditText address = (EditText)findViewById(R.id.address);
			
			restaurant.setName(name.getText().toString());
			restaurant.setAddress(address.getText().toString());
			
			RadioGroup types = (RadioGroup)findViewById(R.id.types);
			
			switch (types.getCheckedRadioButtonId()) {
			case R.id.type_in:
				restaurant.setType("dine_in");
				break;
			
			case R.id.type_out:
				restaurant.setType("take_out");
				break;
				
			case R.id.type_del:
				restaurant.setType("delivery");
				break;
			}
			
			adapter.add(restaurant);
			address_adapter.add(restaurant.getAddress());
			name.setText("");
			address.setText("");
			types.clearCheck();
		}
	};
	
	private String[] getAddresses() {
		String[] addresses = new String[restaurants.size()];
		for (int i = 0; i < restaurants.size(); i++) {
			addresses[i] = restaurants.get(i).getAddress();
		}
		return addresses;
	}

}
