package com.jograt.atenatics.googlesignin;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class ProfileFragment extends Fragment {

    UserBean bean;
    ImageView profile;
    TextView name;
    TextView email;
    TextView coins;
    View view;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        profile = (ImageView) view.findViewById(R.id.profile_image);
        name = (TextView) view.findViewById(R.id.profile_name);
        email = (TextView) view.findViewById(R.id.profile_email);
        coins = (TextView) view.findViewById(R.id.profile_coins);
        bean = ((NavActivity) getActivity()).getBean();
        new DownloadImageTask((ImageView) view.findViewById(R.id.profile_image))
                .execute(bean.getUrl());
        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.v("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            profile.setImageBitmap(result);
            name.setText(bean.getUser());
            email.setText(bean.getEmail());
            coins.setText("Total ReCash Coins : "+bean.getCash());
        }
    }
}
