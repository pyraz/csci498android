package csci498.trevorwhitney.lunchlist;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class LunchFragment extends ListFragment {
	
	public final static String ID_EXTRA = 
			"csci498.trevorwhitney.lunchlist._ID";
	Cursor restaurants = null;
	RestaurantAdapter adapter = null;
	RestaurantHelper helper = null;
	SharedPreferences prefs = null;
	OnRestaurantListener listener = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  setHasOptionsMenu(true);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		helper = new RestaurantHelper(getActivity());
	  prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
	  initList();
	  prefs.registerOnSharedPreferenceChangeListener(prefListener);
	}
	
	@Override
	public void onPause() {
		helper.close();
		
		super.onPause();
	}
	
	@Override
	public void onListItemClick(ListView list, View view, int position,
			long id) {
		if (listener != null) {
			listener.onRestaurantSelected(id);
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, 
			MenuInflater inflater) {
		inflater.inflate(R.menu.option, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add) {
			startActivity(new Intent(getActivity(), DetailForm.class));
			
			return true;
		}
		else if (item.getItemId() == R.id.prefs) {
			startActivity(new Intent(getActivity(), EditPreferences.class));
			
			return true;
		}
		else if (item.getItemId() == R.id.help){
			startActivity(new Intent(getActivity(), HelpPage.class));
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void setOnRestaurantListener(OnRestaurantListener listener) {
		this.listener = listener;
	}
	
	private void initList() {
		if (restaurants != null) {
			restaurants.close();
		}
		
		restaurants = helper.getAll(prefs.getString("sort_order", "name"));
		adapter = new RestaurantAdapter(restaurants);
		setListAdapter(adapter);
	}
	
	private SharedPreferences.OnSharedPreferenceChangeListener prefListener = 
			new SharedPreferences.OnSharedPreferenceChangeListener() {
		public void onSharedPreferenceChanged(SharedPreferences sharedPrefs,
				String key) {
			if (key.equals("sort_order")) {
				initList();
			}
		}
	};
	
	public interface OnRestaurantListener {
		void onRestaurantSelected(long id);
	}
	
	class RestaurantAdapter extends CursorAdapter {
		
		@SuppressWarnings("deprecation")
		RestaurantAdapter(Cursor c) {
			super(getActivity(), c);
		}
		
		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			RestaurantHolder holder = (RestaurantHolder)row.getTag();
			holder.populateList(c, helper);
		}
		
		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View row = inflater.inflate(R.layout.restuarant_row,
					parent, false);
			RestaurantHolder holder = new RestaurantHolder(row);
			
			row.setTag(holder);
			
			return row;
		}
		
	}

}
