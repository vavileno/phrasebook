package ru.zebro.phrasebook;

import java.io.IOException;
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
    
    private List<Map<Long, String>> phrasebookList;
    
    private DataBaseHelper dbHelper;

    public PhraseAdapter(Context c, DataBaseHelper dbHelper) {
        mContext = c;
        this.dbHelper = dbHelper;
        
        try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        phrasebookList =  dbHelper.getPhraseBook();
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
        	textView.setLayoutParams(new GridView.LayoutParams(85, 85));
        	textView.setPadding(8, 8, 8, 8);
        } else {
        	textView = (TextView) convertView;
        }

        textView.setText("Тест");
        return textView;
    }
}