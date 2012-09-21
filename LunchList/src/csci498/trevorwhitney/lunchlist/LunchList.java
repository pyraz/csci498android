package csci498.trevorwhitney.lunchlist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class LunchList extends TabActivity {
	
	Cursor restaurants = null;
	RestaurantAdapter adapter = null;
	Restaurant current = null;
	RestaurantHelper helper = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_lunch_list);
	  helper = new RestaurantHelper(this);
	  
	  ListView list = (ListView)findViewById(R.id.restaurants_list);
	  restaurants = helper.getAll();
	  startManagingCursor(restaurants);
	  adapter = new RestaurantAdapter(restaurants);
	  list.setAdapter(adapter);
	  list.setOnItemClickListener(onListClick);
	  
	  TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
	  spec.setContent(R.id.restaurants_list);
	  spec.setIndicator("List", getResources().getDrawable(
	  		R.drawable.list));
	  getTabHost().addTab(spec);
	  
	  spec = getTabHost().newTabSpec("tag2");
	  spec.setContent(R.id.details_form);
	  spec.setIndicator("Details", getResources().getDrawable(
	  		R.drawable.restaurant));
	  getTabHost().addTab(spec);
	  
	  getTabHost().setCurrentTab(0);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		helper.close();
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}
	
	
	private AdapterView.OnItemClickListener onListClick = new
			AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent,
						View view, int position, long id) {
					Intent i = new Intent(LunchList.this, DetailForm.class);
					
					startActivity(i);
				}
			};
	
	class RestaurantAdapter extends CursorAdapter {
		
		RestaurantAdapter(Cursor c) {
			super(LunchList.this, c);
		}
		
		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			RestaurantHolder holder = (RestaurantHolder)row.getTag();
			holder.populateList(c, helper);
		}
		
		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.restuarant_row,
					parent, false);
			RestaurantHolder holder = new RestaurantHolder(row);
			
			row.setTag(holder);
			
			return row;
		}
		
	}

}
