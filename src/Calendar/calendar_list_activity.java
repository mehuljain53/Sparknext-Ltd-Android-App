package Calendar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.applw.MainActivity;
import com.example.applw.R;
import com.example.applw.Url_and_Strings;

public class calendar_list_activity extends Fragment {

	private List<calendar_class> List_of_events;
	private LoadAllProducts lap;
	ListView MylistView;
	Calendar_adapter cap;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.calendar_list_activity,
				container, false);

		lap = new LoadAllProducts();
		lap.execute();

		Log.e("ImageLoadTask", "Calendar fragment Loaded ");

		List_of_events = new ArrayList<calendar_class>();

		// SET AS CURRENT LIST
		MylistView = (ListView) (rootView
				.findViewById(R.id.Calendarfragment_List_view));

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(3);
	}

	class LoadAllProducts extends AsyncTask<String, String, String> {

		protected String doInBackground(String... args) {
			// Building Parameters

			InputStream is = null;
			String result = null;
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			String user, pass;
			user = "hey";
			pass = "hello";
			Log.e("log_tag", user + pass);

			nameValuePairs.add(new BasicNameValuePair("Username", user));
			nameValuePairs.add(new BasicNameValuePair("Password", pass));

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(Url_and_Strings.ip
						+ "myproject/calendar_access.php");
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();

				Log.e("log_tag", "connection success ");

			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection " + e.toString());
				return "no connection";
			}
			// convert response to string
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");

				}
				is.close();

				result = sb.toString();
				Log.e("result is", result);
			} catch (Exception e) {
				Log.e("log_tag", "Error converting result " + e.toString());
				return "false";

			}

			try {

				JSONObject object = new JSONObject(result);
				String ch = object.getString("success");
				if (ch.equals("1")) {

					Log.e("log_tag", "connection success ");

					JSONArray productObj = object.getJSONArray("products");

					// get first product object from JSON Array
					for (int i = 0; i < productObj.length(); i++) {

						JSONObject c = productObj.getJSONObject(i);
						Log.e("log_tag", "inside..... ");
						// Storing each json item in variable
						String id = c.getString("id");
						String date = c.getString("date");
						String event = c.getString("event_name");
						String desc = c.getString("description");
						String othfd = c.getString("other_field");
						
						/*String DATE_FORMAT_NOW = date;
						 Date date123 = new Date();
						 SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
						 date = sdf.format(date123 );
						    */
					  /*  DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					     Date date123 = format1.parse(date);
					     DateFormat format2 = new SimpleDateFormat("MMMMM dd, yyyy");
					      date = format2.format(date123);*/
					     
						calendar_class calenevent = new calendar_class(date,
								event, desc);
						List_of_events.add(calenevent);

						Log.e("calendar", "" + List_of_events.size());

					}
					Log.e("calendar", "return true");
					return "true";

				}

				else {

					return "false";

				}

			} catch (JSONException e) {
				Log.e("log_tag", "Error parsing data " + e.toString());
				// Toast.makeText(getApplicationContext(), "JsonArray fail",
				// Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Log.e("log_tag", "other exception " + e.toString());
			}
			return "false";
		}

		protected void onPostExecute(String file_url) {
			if (file_url.equals("true")) {
				Log.e("calendar", "Inside post");
				cap = new Calendar_adapter(Url_and_Strings.context,
						List_of_events);
				MylistView.setAdapter(cap);

				Log.e("Culture", "main assync task finished");
			}
			else{
				Toast.makeText(Url_and_Strings.context, "Connection Problem..", Toast.LENGTH_LONG).show();
			}
		}

	}

}
