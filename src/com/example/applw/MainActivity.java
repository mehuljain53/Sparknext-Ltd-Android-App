package com.example.applw;

import refer.refer_activity;
import suggestion.suggesstion_list_activity;
import calendar_new.Calendar_new_list;
import food.food_list_activity;
import Calendar.calendar_list_activity;
import Culture.Culturelistactivity;
import Rules.RulesFragment;
import Rules.RulesPreFragment;
import Team.teamlistactivity;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks, Rules.Communicator {

	String featuresofspark[];

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Url_and_Strings.context = this;
		getActionBar().setIcon(android.R.color.transparent);
		// getActionBar().setBackgroundDrawable(new ColorDrawable(0x0000ff));
		featuresofspark = getResources().getStringArray(
				R.array.list_of_features);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		if (position == 0) {
			fragmentManager.beginTransaction()
					.replace(R.id.container, new Culturelistactivity())
					.commit();
			getActionBar().setTitle("Culture");
		} else if (position == 2) {
			fragmentManager.beginTransaction()
					.replace(R.id.container, new Calendar_new_list()).commit();
		} else if (position == 1) {
			Log.e("food", "entered");
			fragmentManager.beginTransaction()
					.replace(R.id.container, new food_list_activity()).commit();

		} else if (position == 5) {
			fragmentManager.beginTransaction()
					.replace(R.id.container, new teamlistactivity()).commit();
		} else if (position == 7) {
			fragmentManager.beginTransaction()
					.replace(R.id.container, new refer_activity()).commit();
		} else if (position == 6) {
			fragmentManager.beginTransaction()
					.replace(R.id.container, new suggesstion_list_activity())
					.commit();
		} else if (position == 3) {
			fragmentManager.beginTransaction()
					.replace(R.id.container, new RulesPreFragment()).commit();
		}

		else {
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							PlaceholderFragment.newInstance(position + 1))
					.commit();

		}

	}

	public void onSectionAttached(int number) {
		/*
		 * switch (number) { case 1: mTitle =
		 * getString(R.string.title_section1); break; case 2: mTitle =
		 * getString(R.string.title_section2); break; case 3: mTitle =
		 * getString(R.string.title_section3); break; }
		 */
		String title[] = (getResources()
				.getStringArray(R.array.list_of_features));
		mTitle = title[number - 1];
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

	public void exitAppMethod() {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		if (mNavigationDrawerFragment.isDrawerOpen()) {
			mNavigationDrawerFragment.mDrawerLayout.closeDrawers();
		} else if (Url_and_Strings.Login_mode == Url_and_Strings.User) {
			super.onBackPressed();
			this.overridePendingTransition(R.anim.anim_3, R.anim.anim_4);
		} else {
			super.onBackPressed();
			this.overridePendingTransition(R.anim.anim_3, R.anim.anim_4);
		}
	}

	@Override
	public void respond(int data) {

		Log.e("In respond", "before fragment manager");

		/*
		 * fragmentManager.beginTransaction() .replace(R.id.container, new
		 * suggesstion_list_activity()) .commit();
		 */
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.container, new RulesFragment(data))
				.addToBackStack("").commit();

		Log.e("in respond", "after fragment manager");

	}

}
