package csci498.trevorwhitney.lunchlist;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LunchList extends Activity {
	
	Restaurant restaurant = new Restaurant();
	EditText name, address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        Typeface quicksand = Typeface.createFromAsset(getAssets(), 
        		"fonts/quicksand.otf");
        Typeface florante = Typeface.createFromAsset(getAssets(),
        		"fonts/florante.ttf");
        
        name = (EditText)findViewById(R.id.name);
        address = (EditText)findViewById(R.id.address);
        name.setTypeface(quicksand);
        address.setTypeface(florante);
        
        Button save = (Button)findViewById(R.id.save_btn);
        save.setOnClickListener(onSave);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {	
		public void onClick(View v) {
			restaurant.setName(name.getText().toString());
			restaurant.setAddress(address.getText().toString());
		}
	};

}
