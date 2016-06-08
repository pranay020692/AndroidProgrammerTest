package com.apppartner.androidprogrammertest.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.widget.*;

import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.models.ChatData;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created on 12/23/14.
 *
 * @author Thomas Colligan
 */
public class ChatsArrayAdapter extends ArrayAdapter<ChatData>
{
    public ChatsArrayAdapter(Context context, List<ChatData> objects)
    {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ChatCell chatCell = new ChatCell();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.cell_chat, parent, false);

        chatCell.avatarView = (ImageView)convertView.findViewById(R.id.avatarView);
        chatCell.username = (TextView) convertView.findViewById(R.id.usernameTextView);
        chatCell.message = (TextView) convertView.findViewById(R.id.messageTextView);

        Typeface nameFace= Typeface.createFromAsset(getContext().getAssets(), "fonts/Jelloween - Machinato.ttf");
        chatCell.username.setTypeface(nameFace);

        Typeface messageFace= Typeface.createFromAsset(getContext().getAssets(), "fonts/Jelloween - Machinato Light.ttf");
        chatCell.username.setTypeface(messageFace);

        ChatData chatData = getItem(position);

        //chatCell.avatarView.setImageBitmap(getBitmapFromURL(chatData.avatarURL));

        new DownloadImageTask(chatCell.avatarView).execute(chatData.avatarURL);
        chatCell.username.setText(chatData.username);
        chatCell.message.setText(chatData.message);

        return convertView;
    }

    private static class ChatCell
    {
        ImageView avatarView;
        TextView username;
        TextView message;
    }

    public static Bitmap getBitmapFromURL(String src) { //EDIT
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
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
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            RoundImage roundedImage = new RoundImage(result);
            bmImage.setImageDrawable(roundedImage);
        }
    }


}
