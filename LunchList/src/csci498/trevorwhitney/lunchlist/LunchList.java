package csci498.trevorwhitney.lunchlist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LunchList extends Activity {
	
	List<Restaurant> restaurants = new ArrayList<Restaurant>();
	RestaurantAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        Button save = (Button)findViewById(R.id.save_btn);
        save.setOnClickListener(onSave);
        
        ListView list = (ListView)findViewById(R.id.restaurant_list);
        adapter = new RestaurantAdapter();
        list.setAdapter(adapter);
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
			name.setText("");
			address.setText("");
			types.clearCheck();
		}
	};
	
	//Inner class for custom Restaurant adapter
	class RestaurantAdapter extends ArrayAdapter<Restaurant> {
		
		RestaurantAdapter() {
			super(LunchList.this,
					android.R.layout.simple_list_item_1,
					restaurants);
		}
		
		public View getView(int position, View convertView,
				ViewGroup parent) {
			View row = convertView;
			
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.restuarant_row, null);
			}
			
			Restaurant restaurant = restaurants.get(position);
			((TextView)row.findViewById(R.id.name)).setText(
					restaurant.getName());
			((TextView)row.findViewById(R.id.address)).setText(
					restaurant.getAddress());
			
			ImageView icon = (ImageView)row.findViewById(R.id.icon);
			if (restaurant.getType().equals("dine_in")) {
				icon.setImageResource(R.drawable.ball_red);
			} 
			else if (restaurant.getType().equals("take_out")) {
				icon.setImageResource(R.drawable.ball_yellow);
			}
			else {
				icon.setImageResource(R.drawable.ball_green);
			}
			
			return row;
		}
		
	}

}
