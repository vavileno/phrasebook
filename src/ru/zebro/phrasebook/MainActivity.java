package ru.zebro.phrasebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity {
	
//	private static final String TAG = "MainActivity";
	
	private DataBaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new DataBaseHelper(getApplicationContext());
		final PhraseAdapter phraseAdapter = new PhraseAdapter(this, dbHelper);
		
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(phraseAdapter);
	    
	    final Context context = this;

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	Intent intent = new Intent(context, ShowTranslatedActivity.class);
		    	intent.putExtra(ShowTranslatedActivity.TRANSLATED_TEXT, 
		    			phraseAdapter.getPhrasebook().getDestinationPhrase(position + 1));
		    	startActivity(intent);
	        }
	    });		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		    case R.id.action_settings:
		    	this.finish(); 
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
    	}    	
    }	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(dbHelper != null) {
			dbHelper.close();			
		}
	}

}
