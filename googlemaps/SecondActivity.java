package com.example.googlemaps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;


public class SecondActivity extends AppCompatActivity {
    ImageView img;
    final private int REQUEST_INTERNET = 123;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private InputStream OpenHttpConnection(String urlString) throws IOException {

        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            response = httpConn.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }

        } catch (Exception ex) {

            Log.d("Networking", Objects.requireNonNull(ex.getLocalizedMessage()));
            throw new IOException("Error connecting");

        }

        return in;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET);

        } else {
            new
                DownloadImageTask().execute("https://upload.wikimedia.org/wikipedia/en/9/99/Uls ter_University_Logo.png");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Bitmap DownloadImage(String URL) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();

        } catch (IOException e1) {
            Log.d("NetworkingActivity", e1.getLocalizedMessage());
        }

        return bitmap;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
     { @RequiresApi(api = Build.VERSION_CODES.KITKAT)
     protected Bitmap doInBackground(String... urls){

        return DownloadImage(urls[0]);

    }
        protected void onPostExecute(Bitmap result){
            ImageView img = (ImageView) findViewById(R.id.imageView);
            img.setImageBitmap(result);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        switch (requestCode){
            case REQUEST_INTERNET:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    new
                        DownloadImageTask().execute("https://upload.wikimedia.org/wikipedia/en/9/99/Uls ter_University_Logo.png" +
                        "");
                }

                else{
                    Toast.makeText(SecondActivity.this, "Permission Denied",
                            Toast.LENGTH_LONG).show();
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }
}



