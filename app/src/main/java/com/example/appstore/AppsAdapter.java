package com.example.appstore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstore.entity.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {

    private ArrayList<App> appsList;
    private Context context;

    public AppsAdapter(Context context,
                       ArrayList<App> appsList) {
        this.context = context;
        this.appsList = appsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_in_list_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final App currentApp = appsList.get(position);

        holder.appNameLabel.setText(currentApp.getAppDescription());
        holder.appVersionLabel.setText(currentApp.getAppVersion());

        //Download app icon
        if (!currentApp.getAppIconUrl().isEmpty()) {
            new DownloadImageTask(holder, currentApp).execute(currentApp.getAppIconUrl());
        }

        //On app click -> download apk and install
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAndInstallApp(currentApp);
            }
        });
    }

    private void downloadAndInstallApp(App currentApp) {

        new AppDownloadTask(context, currentApp).execute();

    }


    @Override
    public int getItemCount() {
        return appsList.size();
    }

    private void loadAppIcon(final ImageView appIconImageView, final String appIconUrl) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;

                try {
                    URL url = new URL(appIconUrl);
                    URLConnection urlConn = url.openConnection();
                    HttpURLConnection httpConn = (HttpURLConnection) urlConn;
                    httpConn.connect();

                    in = httpConn.getInputStream();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bmpimg = BitmapFactory.decodeStream(in);
                appIconImageView.setImageBitmap(bmpimg);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView appNameLabel, appVersionLabel;
        ImageView appIcon;

        public ViewHolder(View v) {
            super(v);

            appNameLabel = v.findViewById(R.id.appNameLabel);
            appVersionLabel = v.findViewById(R.id.appVersionLabel);
            appIcon = v.findViewById(R.id.appIcon);

        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ViewHolder holder;
        private App app;

        public DownloadImageTask(ViewHolder holder, App app) {
            this.holder = holder;
            this.app = app;
        }

        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        protected void onPostExecute(Bitmap result) {
            holder.appIcon.setImageBitmap(result);
        }
    }

    private Bitmap loadImageFromNetwork(String url){
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}