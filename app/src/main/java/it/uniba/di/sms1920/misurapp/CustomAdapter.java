package it.uniba.di.sms1920.misurapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mSensorNames;
    private int[] mImages;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, String[] sensorNames, int[] images) {
        this.mContext = context;
        this.mSensorNames = sensorNames;
        this.mImages = images;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mSensorNames.length;
    }

    @Override
    public Object getItem(int position) {
        return  position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {

            convertView = inflater.inflate(R.layout.square_layout, null);
            TextView textView = (TextView) convertView.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_image);
            textView.setText(mSensorNames[position]);
            imageView.setImageResource(mImages[position]);

            return convertView;
    }
}
