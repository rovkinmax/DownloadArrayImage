package com.example.DownloadArrayImage;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity implements View.OnClickListener
{
    /**
     * Called when the activity is first created.
     */
    final String k = "http://4.bp.blogspot.com/_BFp02Fm5oSs/TQecAHhepFI/AAAAAAAAAEY/Yyo8h2oQB0w/s1600/Download%2BImage.PNG";
    final String urls[] = {k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k, k,};



    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.buttonGo).setOnClickListener(this);
        textView = (TextView)findViewById(R.id.text);
    }

    @Override
    public void onClick(final View v)
    {
        if(v.getId() == R.id.buttonGo)
            new DownLoadImages().execute();
    }


    private class DownLoadImages extends AsyncTask<Void, Void, List<String>>
    {
        private final int TIME_OUT_FOR_DOWNLOAD = 20000;

        @Override
        protected void onPreExecute()
        {
            textView.setText("download...");
        }

        @Override
        protected List<String> doInBackground(final Void... params)
        {
            final List<String> list = new ArrayList<String>();
            for(int i = 0; i< urls.length;i++)
                list.add(downLoadImage(urls[i],i));
            return list;
        }

        @Override
        protected void onPostExecute(final List<String> list)
        {
            textView.setText("Done!");

        }

        private String downLoadImage(final String link, final int numb)
        {
            final String fileName = String.format("img_%d.png",numb);
            try
            {
                URL updateUrl = new URL(link);
                URLConnection urlConnection = updateUrl.openConnection();
                urlConnection.setConnectTimeout(TIME_OUT_FOR_DOWNLOAD);
                urlConnection.setReadTimeout(TIME_OUT_FOR_DOWNLOAD);
                urlConnection.connect();

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                OutputStream outputStream = MyActivity.this.openFileOutput(fileName, Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = inputStream.read(buffer)) != -1 )
                    outputStream.write(buffer, 0, count);

                outputStream.flush();
                outputStream.close();
                inputStream.close();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return fileName;
        }

    }


}
