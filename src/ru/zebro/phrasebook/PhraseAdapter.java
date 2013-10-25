package ru.zebro.phrasebook;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class PhraseAdapter extends BaseAdapter {
	
	private int itemcount = 10;
	
    private Context mContext;
    
    private List<Map<Integer, String>> phrasebookList;
    
    private Phrasebook phrasebook;

    public PhraseAdapter(Context c, DataBaseHelper dbHelper) {
        mContext = c;
        
        try {
			dbHelper.createDataBase();
			dbHelper.openDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        phrasebookList =  dbHelper.getPhraseBook();

        phrasebook = phrasebookList.get(0) != null 
        		? new Phrasebook(phrasebookList.get(0), phrasebookList.get(1)) 
        		: new Phrasebook(Collections.<Integer, String> emptyMap(), Collections.<Integer, String> emptyMap());
    }

    public int getCount() {
        return itemcount;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	textView = new TextView(mContext);
        	textView.setLayoutParams(new GridView.LayoutParams(100, 100));
        	textView.setPadding(8, 8, 8, 8);
        } else {
        	textView = (TextView) convertView;
        }

        textView.setText(phrasebook.getSourcePhrase(position + 1));

        return textView;
    }

	public Phrasebook getPhrasebook() {
		return phrasebook;
	}
   
}