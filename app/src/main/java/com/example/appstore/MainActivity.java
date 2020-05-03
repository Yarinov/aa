package com.example.appstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.appstore.entity.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private RecyclerView appsRecyclerView;
    private AppsAdapter appsAdapter;

    private JSONObject appJsonString;
    private ArrayList<App> appsArrayList = new ArrayList<App>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup activity objects
        appsRecyclerView = findViewById(R.id.appsRecyclerView);

        appsAdapter = new AppsAdapter(this, appsArrayList);

        //Get the local json file data and insert to the apps array list
        getJsonAppsData();

        //Setup the recyclerView
        appsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        appsRecyclerView.setItemViewCacheSize(25);
        appsRecyclerView.setAdapter(appsAdapter);
        appsRecyclerView.setHasFixedSize(true);
    }

    private void getJsonAppsData() {
        GetJson t = new GetJson(appsArrayList, this);
        t.execute();

    }

    class GetJson extends AsyncTask<Void, Void, JSONObject> {

        ArrayList<App> appsArrayList;
        Context context;

        public GetJson(ArrayList<App> appsArrayList, Context context) {
            this.appsArrayList = appsArrayList;
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            String str = "http://fota.colmobil.co.il.s3-eu-west-1.amazonaws.com/FOTA/settings/update_apps_v1.4.json";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                JSONObject jsonObject = new JSONObject(stringBuffer.toString());

                return jsonObject;
            } catch (Exception ex) {
                Log.e("App", "yourDataTask", ex);
                return null;
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            if (jsonObject != null) {
                try {
                    //Get the arrayList that contains the apps data
                    JSONArray appJsonArray = jsonObject.getJSONArray("endpoint_response_items_array");

                    //For each object in the json file parse it and insert it to the apps array list
                    for (int i = 0; i < appJsonArray.length(); i++) {
                        JSONObject jo_inside = appJsonArray.getJSONObject(i);
                        String appType = jo_inside.getString("type");
                        JSONObject appPayload = jo_inside.getJSONObject("payload");

                        String appName = appPayload.getString("app_name");
                        String appDownloadUrl = appPayload.getString("download_url1");
                        String appVersion = appPayload.getString("version");
                        String appDescription = appPayload.getString("description");
                        String appIcon = appPayload.optString("icon_url");
                        String appPackageName = appPayload.getString("package_name");

                        App newApp;
                        if (appIcon != null)
                            newApp = new App(appName, appVersion, appDescription, appDownloadUrl, appIcon, appPackageName);
                        else
                            newApp = new App(appName, appVersion, appDescription, appDownloadUrl, "", appPackageName);

                        appsArrayList.add(newApp);
                    }

                    appsAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}

