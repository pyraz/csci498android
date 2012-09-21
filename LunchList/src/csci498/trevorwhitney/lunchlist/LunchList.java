package csci498.trevorwhitney.lunchlist;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class LunchList extends ListActivity {
	
	public final static String ID_EXTRA = 
			"csci498.trevorwhitney.lunchlist._ID";
	
	Cursor restaurants = null;
	RestaurantAdapter adapter = null;
	Restaurant current = null;
	RestaurantHelper helper = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_lunch_list);
	  helper = new RestaurantHelper(this);
	  
	  restaurants = helper.getAll();
	  startManagingCursor(restaurants);
	  adapter = new RestaurantAdapter(restaurants);
	  setListAdapter(adapter);
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
	
	@Override
	public void onListItemClick(ListView list, View view, int position,
			long id) {
		Intent i = new Intent(LunchList.this, DetailForm.class);
		
		i.putExtra(ID_EXTRA, String.valueOf(id));
		startActivity(i);
	}
	
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
