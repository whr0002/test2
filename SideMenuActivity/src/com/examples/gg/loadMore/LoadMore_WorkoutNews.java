package com.examples.gg.loadMore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.ActionBar;
import com.examples.gg.adapters.NewsArrayAdapter_Official;
import com.examples.gg.data.Video;
import com.rs.app.R;

public class LoadMore_WorkoutNews extends LoadMore_Base {
	// private ArrayList<News> mNews = new ArrayList<News>();
	private NewsArrayAdapter_Official mArrayAdatper;
	private getMatchInfo mgetMatchInfo;
	// private int pageNum;
	private final String baseUri = "";
	private String mAPI;

	public LoadMore_WorkoutNews(String api, int curPosition) {
		this.mAPI = api;
		this.currentPosition = curPosition;
	}

	@Override
	public void Initializing() {
		// Inflating view

		// Give a title for the action bar
		abTitle = "Lastest News";

		// Give API URLs
		API.add(mAPI);

		// pageNum = 0;

		// Show menu
		setHasOptionsMenu(true);
		setOptionMenu(true, true);

//		currentPosition = 0;

	}

	@Override
	public void setDropdown() {
		if (hasDropDown) {

			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

			final String[] catagory = { "Health Headlines", "Fittness",
					"Body and Mind", "Lifestyle", "Diet and Nutrition" };

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					mActionBar.getThemedContext(),
					R.layout.sherlock_spinner_item, android.R.id.text1,
					catagory);

			adapter.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

			mActionBar.setListNavigationCallbacks(adapter, this);

			mActionBar.setSelectedNavigationItem(currentPosition);

		} else {
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		}
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {

		if (firstTime) {
			firstTime = false;
			return true;
		}

		FragmentTransaction ft = getFragmentManager().beginTransaction();

		switch (itemPosition) {

		case 0:
			// Menu option 1
			ft.replace(R.id.content_frame, new LoadMore_WorkoutNews(
					"http://www.ctvnews.ca/health/health-headlines",0));
			break;

		case 1:
			// Menu option 2
			ft.replace(R.id.content_frame, new LoadMore_WorkoutNews(
					"http://www.ctvnews.ca/health/fitness",1));
			break;

		case 2:
			// Menu option 3
			ft.replace(R.id.content_frame, new LoadMore_WorkoutNews(
					"http://www.ctvnews.ca/health/body-and-mind",2));
			break;

		case 3:
			// Menu option 4
			ft.replace(R.id.content_frame, new LoadMore_WorkoutNews(
					"http://www.ctvnews.ca/health/lifestyle",3));
			break;

		case 4:
			// Menu option 5

			ft.replace(R.id.content_frame, new LoadMore_WorkoutNews(
					"http://www.ctvnews.ca/health/diet-and-nutrition",4));
			break;
		}

		ft.commit();

		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void refreshFragment() {
		String firstApi = API.get(0);
		API.clear();
		API.add(firstApi);
		titles.clear();
		videolist.clear();
		setListView();
	}

	@Override
	protected void setGridViewItemClickListener() {
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = videolist.get(position).getVideoId();
				Intent i = new Intent(sfa,NewsViewerActivity.class);
				i.putExtra("uri", url);
				startActivity(i);
			}
		});
	}

	@SuppressLint("NewApi")
	@Override
	protected void doRequest() {
		// TODO Auto-generated method stub

		// System.out.println("DO!!!!!");
		for (String s : API) {
			mgetMatchInfo = new getMatchInfo(getMatchInfo.INITTASK, gv,
					fullscreenLoadingView, mRetryView);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				mgetMatchInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
						s);
			} else {
				mgetMatchInfo.execute(s);
			}
		}
	}

	private class getMatchInfo extends LoadMoreTask {

		public getMatchInfo(int type, View contentView, View loadingView,
				View retryView) {
			super(type, contentView, loadingView, retryView);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void setRetryListener(final int type) {
			mRetryButton = (Button) retryView.findViewById(R.id.mRetryButton);

			mRetryButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					mgetMatchInfo = (getMatchInfo) new getMatchInfo(type,
							contentView, loadingView, retryView);
					mgetMatchInfo.DisplayView(loadingView, contentView,
							retryView);
					mgetMatchInfo.execute(API.get(API.size() - 1));

				}
			});

		}

		@Override
		public String doInBackground(String... uri) {

			super.doInBackground(uri[0]);

			if (!taskCancel && responseString != null) {
				try {
					pull(responseString);
				} catch (Exception e) {

				}
			}
			// pullNews();
			return responseString;
		}

		private void pull(String responseString) {
			Document doc = Jsoup.parse(responseString);
			// get all links
			Elements links = new Elements();
			links = doc.select("div.content-primary li.dc");

			if (!links.isEmpty()) {
				Elements imageElements = new Elements();
				Elements newsUriElements = new Elements();
				Elements newsTitleElements = new Elements();
				Elements newsSubtitleElements = new Elements();
				// Elements dateElements = new Elements();

				String imageUri = "";
				String newsUri = "";
				String newsTitle = "";
				String newsSubtitle = "";
				// String date = "";
				// System.out.println("Number of News: " + links.size());

				for (Element link : links) {
					imageElements = link.select("img");
					if(imageElements.size()>0){
						imageUri = imageElements.first().attr("src");
//						System.out.println(c+"Image uri: " +imageUri);
					}
					
					newsUriElements = link.select("h2.teaserTitle");
					if(newsUriElements.size()>0){
						newsUri = newsUriElements.first()
								.select("a").first().attr("href");
//						System.out.println("article uri: " +newsUri);
						if(newsUri != null){
							newsUri = newsUri.replaceAll("www.ctvnews.ca", "www.ctvnews.ca/mobile");
						}
					}

					
					newsTitleElements = link.select("h2.teaserTitle");		
					if(newsTitleElements.size()>0){
						newsTitle = newsTitleElements.first()
								.select("a").first().text();
//						System.out.println("article title: " +newsTitle);
					}
					
					newsSubtitleElements = link.select("p");		
//					System.out.println("size of subtitle: " +newsSubtitleElements.size());
					if(newsSubtitleElements.size() != 0){
						newsSubtitle = newsSubtitleElements.first().text();
//						System.out.println("article subtitle: " +newsSubtitle);
					}

//					dateElements = link.select("span.maketip");		
//					if(dateElements.size()>0){
//						date = dateElements.first().text();
//					}

					Video v = new Video();
					v.setThumbnailUrl(imageUri);
					// v.setRecentVideoUrl(newsUri);
					v.setTitle(newsTitle);
					v.setAuthor(newsSubtitle);
					v.setVideoId(newsUri);
					v.setAsNews();

					titles.add(newsTitle);
					videolist.add(v);

				}
			}else{
				Toast.makeText(
						sfa,
						"Sorry, fail to get data.",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPostExecute(String result) {

			// Log.d("AsyncDebug", "Into onPostExecute!");

			if (!taskCancel && result != null) {

				vaa.notifyDataSetChanged();

				// loading done
				DisplayView(contentView, retryView, loadingView);

				isMoreVideos = false;
				if (!isMoreVideos) {

					gv.setOnScrollListener(null);
				}

			} else {

				handleCancelView();
			}

		}
	}

	@Override
	public void onDestroy() {

		super.onDestroy();

		if (mgetMatchInfo != null
				&& mgetMatchInfo.getStatus() == Status.RUNNING)
			mgetMatchInfo.cancel(true);

	}

}
