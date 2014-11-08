package com.rs.app;


import java.util.ArrayList;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.examples.gg.adapters.EntryAdapter;
import com.examples.gg.data.EntryItem;
import com.examples.gg.data.Item;
import com.examples.gg.data.SectionItem;
import com.examples.gg.loadMore.FavoritesFragment;
import com.examples.gg.loadMore.HistoryFragment;
import com.examples.gg.loadMore.LoadMore_H_Subscription;
import com.examples.gg.loadMore.LoadMore_News;
import com.examples.gg.loadMore.LoadMore_WorkoutNews;
import com.examples.gg.loadMore.SearchResultFragment;
import com.examples.gg.loadMore.SubscriptionFragment;
import com.examples.gg.loadMore.TipsFragment;
import com.examples.gg.settings.SettingsActivity;
import com.rs.app.R;


public class SideMenuActivity extends SherlockFragmentActivity {

	// Declare Variable
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	ArrayList<Item> items = new ArrayList<Item>();
	ActionBar mActionBar;
	EntryAdapter eAdapter;

	private FragmentManager fm;
	private final String firstTimePrefs = "firsttime";
//	private InterstitialAd interstitial;

	// private boolean doubleBackToExitPressedOnce = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_main);

		// Create the interstitial
//		interstitial = new InterstitialAd(this,
//				"ca-app-pub-6718707824713684/7369125856");

		// Create ad request
//		AdRequest adRequest = new AdRequest();
//		adRequest.addTestDevice("5E4CA696BEB736E734DD974DD296F11A");
		// Begin loading your interstitial
//		interstitial.loadAd(adRequest);

		// Set Ad Listener to use the callbacks below
//		interstitial.setAdListener(this);

		
		// Initial fragment manager
		fm = this.getSupportFragmentManager();

		// Locate DrawerLayout in drawer_main.xml
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Locate ListView in drawer_main.xml
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Do not allow list view to scroll over
		// mDrawerList.setOverScrollMode(ListView.OVER_SCROLL_NEVER);

		// Set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Pass results to MenuListAdapter Class
		// mMenuAdapter = new MenuListAdapter(this, title, subtitle, icon);
		items.add(new SectionItem("Everyday's Feed"));
		items.add(new EntryItem("What's New", "Fresh every day",
				R.drawable.fresh_meat));

		items.add(new EntryItem("Latest News", "From CTV News",
				R.drawable.ic_action_news));

		items.add(new SectionItem("Let's do it"));
		items.add(new EntryItem("7-minute workout", "Train efficiently",
				R.drawable.minutes));
		
		items.add(new SectionItem("Workout Videos"));
		items.add(new EntryItem("General", null,
				R.drawable.highlights));
		items.add(new EntryItem("Abs", null,
				R.drawable.highlights));
		items.add(new EntryItem("Arms", null,
				R.drawable.highlights));
		items.add(new EntryItem("Back", null,
				R.drawable.highlights));
		items.add(new EntryItem("Butt", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Cardio", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Fat Burning", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Full Body", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Jump Rope", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Legs", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Strength", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Stretches", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Thigh", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Upper Body", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Walking", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Weight Loss", null,
				R.drawable.highlights));
		
		items.add(new EntryItem("Yoga", null,
				R.drawable.highlights));
		
//		items.add(new EntryItem("Matches", "You don't wanna miss it",
//				R.drawable.swords));

		items.add(new SectionItem("Healthy Life"));
//		items.add(new EntryItem("Twitch Streams", "Battle begins!",
//				R.drawable.live));
		items.add(new EntryItem("Favorites", "Great videos",
				R.drawable.medal));
		items.add(new EntryItem("Subscriptions", "Local subscriptions",
				R.drawable.upcoming));
		items.add(new EntryItem("History", "Watched videos", R.drawable.minutes));
		items.add(new EntryItem("Healthy Tips", "Make your life better",
				R.drawable.live));

//		items.add(new SectionItem("Match Table"));
//		items.add(new EntryItem("Upcomings", "Matches coming soon!",
//				R.drawable.upcoming));
//		items.add(new EntryItem("Recent Results", "It's in the bag!",
//				R.drawable.list_result));

		// "About" section
		items.add(new SectionItem("About App"));
		items.add(new EntryItem("Feedback", "Help us make it better",
				R.drawable.feedback));

		items.add(new EntryItem("Share", "Share our app",
				R.drawable.ic_action_social_share));
		items.add(new EntryItem("Rate", "Like it?",
				R.drawable.ic_action_rating_good));

		eAdapter = new EntryAdapter(this, items);

		// setListAdapter(adapter);

		// Set the MenuListAdapter to the ListView
		mDrawerList.setAdapter(eAdapter);

		// Capture button clicks on side menu
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// Enable ActionBar app icon to behave as action to toggle nav drawer
		mActionBar = getSupportActionBar();

		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		// mActionBar.setTitle("Main Menu");

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(1);
					
		}
		isFirstTimeUser();
		this.validatingTips(this);

	}
	private void isFirstTimeUser(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String result = prefs.getString(firstTimePrefs, "");
		if(result.equals("")){
			// This is first time to start the activity
			// Open drawer for the user
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {
					mDrawerLayout.openDrawer(mDrawerList);
				}
			}, 0);
			
			// Save the state
			prefs.edit().putString(firstTimePrefs, "No").commit();
		}
		
	}

	private void validatingTips(SherlockFragmentActivity sfa) {
		TipsValidator tv = new TipsValidator(sfa);
		tv.Validation();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			Handler handler = new Handler();

			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				handler.postDelayed(new Runnable() {
					public void run() {
						mDrawerLayout.closeDrawer(mDrawerList);
					}
				}, 0);
			} else {
				handler.postDelayed(new Runnable() {
					public void run() {
						mDrawerLayout.openDrawer(mDrawerList);
					}
				}, 0);
			}
		}

		if (item.getItemId() == R.id.menu_settings) {

			// Start setting activity
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);

		}

		return super.onOptionsItemSelected(item);
	}

	// The click listener for ListView in the navigation drawer
	public class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// if (row != null) {
			// row.setBackgroundColor(Color.WHITE);
			// }
			// row = view;
			// view.setBackgroundColor(Color.YELLOW);
			//
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		// Set the indicator in drawer to correct position
//		if (position <= 10)
			setDrawerIndicator(position);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		// Clear the fragment stack first
		clearFragmentStack();

		mDrawerList.setItemChecked(position, true);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		}, 0);

		switch (position) {
		// Section divider case 0------------------------
		case 1:
			// News
			ft.replace(R.id.content_frame, new LoadMore_News());
			break;

		case 2:
			// Gosu news
			ft.replace(R.id.content_frame, new LoadMore_WorkoutNews("http://www.ctvnews.ca/health/health-headlines",0));
			break;

		// Section divider case 3------------------------
			
		case 4:
			// 7 minute workout
//			ft.replace(R.id.content_frame, new PlaylistFragment());
			ft.replace(R.id.content_frame, new SearchResultFragment("7 minutes workout","7-minute workout"));
			break;

		// Section divider case 5------------------------
			
		case 6:
			// General section
			ft.replace(R.id.content_frame, new LoadMore_H_Subscription());
			break;
			
		case 7:
			ft.replace(R.id.content_frame, new SearchResultFragment("abs workout","Abs"));
			break;
			
		case 8:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout arms","Arms"));
			break;
			
		case 9:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout back","Back"));
			break;

		case 10:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout butt","Butt"));
			break;
			
		case 11:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout cardio","Cardio"));
			break;
			
		case 12:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout fat burning","Fat Burning"));
			break;
			
		case 13:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout full body","Full Body"));
			break;
			
		case 14:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout jump rope","Jump Rope"));
			break;
			
		case 15:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout legs","Legs"));
			break;

		case 16:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout strength","Strength"));
			break;
			
		case 17:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout stretches","Stretches"));
			break;
			
		case 18:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout thigh","Thigh"));
			break;
			
		case 19:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout upper body","Upper Body"));
			break;
			
		case 20:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout walking","Walking"));
			break;
			
		case 21:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout weight loss","Weight Loss"));
			break;
			
		case 22:
			ft.replace(R.id.content_frame, new SearchResultFragment("workout yoga","Yoga"));
			break;
			
		// Section divider case 23------------------------
		case 24:
			ft.replace(R.id.content_frame, new FavoritesFragment());
			break;
		case 25:
			ft.replace(R.id.content_frame, new SubscriptionFragment());
			break;
		case 26:
			ft.replace(R.id.content_frame, new HistoryFragment());
			break;
		case 27:
			ft.replace(R.id.content_frame, new TipsFragment());
			break;
			
		// Section divider case 25------------------------
		case 29:
			// Feedback

			Intent email = new Intent(Intent.ACTION_VIEW);
			email.setData(Uri
					.parse("mailto:workoutmaster2014@gmail.com?subject=Workout Day Feedback"));
			startActivity(Intent.createChooser(email, "Send feedback via.."));
			// startActivity(email);
			break;

		case 30:
			// Share Dota2TV
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent
					.putExtra(Intent.EXTRA_TEXT,
							"https://play.google.com/store/apps/details?id=com.rs.app");
			sendIntent.setType("text/plain");
			startActivity(Intent
					.createChooser(sendIntent, "Share Workout Day to.."));
			// startActivity(sendIntent);
			break;

		case 31:
			// Rate Dota2TV
			Intent rateIntent = new Intent(Intent.ACTION_VIEW);
			// Try Google play
			rateIntent
					.setData(Uri.parse("market://details?id=com.rs.app"));
			if (tryStartActivity(rateIntent) == false) {
				// Market (Google play) app seems not installed, let's try to
				// open a webbrowser
				rateIntent
						.setData(Uri
								.parse("https://play.google.com/store/apps/details?id=com.rs.app"));
				if (tryStartActivity(rateIntent) == false) {
					// Well if this also fails, we have run out of options,
					// inform the user.
					Toast.makeText(
							this,
							"Could not open Google Play, please install Google Play.",
							Toast.LENGTH_SHORT).show();
				}
			}

			break;
		}

		ft.commit();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// Clear fragment back stack
	public void clearFragmentStack() {
		fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	// Handles exit
	// @Override
	// public void onBackPressed() {
	// if (fm.getBackStackEntryCount() == 0) {
	//
	// // No fragment in back stack
	//
	// if (doubleBackToExitPressedOnce) {
	// super.onBackPressed();
	// return;
	// }
	// this.doubleBackToExitPressedOnce = true;
	// Toast.makeText(this, "Please click BACK again to exit",
	// Toast.LENGTH_SHORT).show();
	//
	// // reset doubleBackToExitPressedOnce to false after 2 seconds
	// new Handler().postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// doubleBackToExitPressedOnce = false;
	//
	// }
	// }, 2000);
	// } else {
	//
	// // Fragment back stack is empty
	//
	// super.onBackPressed();
	// }
	// }

	public void setDrawerIndicator(int position) {
		for (Item i : items)
			i.setUnchecked();
		items.get(position).setChecked();
		eAdapter.notifyDataSetChanged();
	}

	private boolean tryStartActivity(Intent aIntent) {
		try {
			startActivity(aIntent);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}
	

}
