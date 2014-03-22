package ru.zebro.phrasebook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PhraseAdapter extends BaseAdapter {
	
    private Context mContext;
    
    public PhraseAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return Util.getPhrasebook().getSize();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
        
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) { 
        	textView = new TextView(mContext);
        	textView.setPadding(8, 8, 8, 8);
        } else {
        	textView = (TextView) convertView;
        }

        textView.setText(Util.getPhrasebook().getSourcePhrase(position + 1));
        
        return textView;
    } 
}