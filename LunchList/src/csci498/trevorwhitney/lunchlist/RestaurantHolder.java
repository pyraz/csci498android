package csci498.trevorwhitney.lunchlist;

import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class RestaurantHolder {
	private TextView name = null;
	private TextView address = null;
	private ImageView icon = null;
	
	RestaurantHolder(View row) {
		name = (TextView)row.findViewById(R.id.name);
		address = (TextView)row.findViewById(R.id.address);
		icon = (ImageView)row.findViewById(R.id.icon);
	}
	
	void populateList(Cursor c, RestaurantHelper helper) {
		name.setText(helper.getName(c));
		address.setText(helper.getAddress(c));
		
		if (helper.getType(c).equals("dine_in")) {
			icon.setImageResource(R.drawable.ball_red);
		}
		else if (helper.getType(c).equals("take_out")) {
			icon.setImageResource(R.drawable.ball_yellow);
		}
		else {
			icon.setImageResource(R.drawable.ball_green);
		}
	}
}