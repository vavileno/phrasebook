package ru.zebro.phrasebook;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	/**
	 * 	Current category, selected by user.
	 * 	By default common category is selected and all source phrases from database are displayed.
	 * 	User can select another category from menu and appropriate values will be displayed in the list.
	 * */
	private int currentCategory; 
	
	private AlertDialog.Builder deletePhraseAlert;	
	
	private AutoCompleteTextView searchAutocomplete;
	
	public int getCurrentCategory() {
		return currentCategory;
	}
	
	public void setCurrentCategory(int currentCategory) {
		this.currentCategory = currentCategory;
	}

	public AlertDialog.Builder getDeletePhraseAlert() {
		return deletePhraseAlert;
	}

	public void setDeletePhraseAlert(AlertDialog.Builder deletePhraseAlert) {
		this.deletePhraseAlert = deletePhraseAlert;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Util.setMainActivity(this);

		Util.initDb(getApplicationContext());
		
		deletePhraseAlert = createDeletePhraseAlert();

		// default source phrase list
		final PhraseAdapter phraseAdapter = new PhraseAdapter(this);
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(phraseAdapter);
	    
	    final Context context = this;

	    // source phrase clicked
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	Intent intent = new Intent(context, ShowTranslatedActivity.class);
	        	intent.putExtra(ShowTranslatedActivity.TRANSLATED_TEXT, 
	        			Util.getPhrasebook().getDestinationPhrase(position + 1));
	        	intent.putExtra(ShowTranslatedActivity.TRANSLATED_TEXT_SPELLED, 
	        			Util.getPhrasebook().getSpellingPhrase(position + 1));	
		    	intent.putExtra(ShowTranslatedActivity.SOURCE_TEXT, 
		    			Util.getPhrasebook().getSourcePhrase(position + 1));	        	
	        	startActivity(intent);
	        }
	    });
	    
	    // long click on source phrase
	    gridview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long rowId) {

				final TextView textView = (TextView)view;
            	deletePhraseAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    	Util.getPhrasebook().deletePhrase(textView.getText());
                    	refreshGridview(0);
                  } });
            	
    			deletePhraseAlert.show();
                return true;
			}
	    	
		});

	    // Autocomplete for search source phrase for translating
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getSourceItems());
        searchAutocomplete = (AutoCompleteTextView)findViewById(R.id.autoCompleteSearch);
        searchAutocomplete.setAdapter(adapter);
        searchAutocomplete.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String selected = (String) arg0.getAdapter().getItem(arg2);
								
		    	Intent intent = new Intent(context, ShowTranslatedActivity.class);
		    	intent.putExtra(ShowTranslatedActivity.TRANSLATED_TEXT, 
		    			Util.getPhrasebook().getDestinationPhrase(selected));
		    	intent.putExtra(ShowTranslatedActivity.TRANSLATED_TEXT_SPELLED, 
		    			Util.getPhrasebook().getSpellingPhrase(selected));
		    	intent.putExtra(ShowTranslatedActivity.SOURCE_TEXT, 
		    			selected);			    	
		    	startActivity(intent);
		    	searchAutocomplete.setText("");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * 	Category selected
	 * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.action_common_category:
		    	refreshGridview(1);
		    	setCurrentCategory(1);
		    	return true;    			
    		case R.id.action_time_category:
		    	refreshGridview(2);
		    	setCurrentCategory(2);
		    	return true;    			
    		case R.id.action_places_category:
		    	refreshGridview(3);
		    	setCurrentCategory(3);
		    	return true;
    		case R.id.action_hotel_category:
		    	refreshGridview(4);
		    	setCurrentCategory(4);
		    	return true;
    		case R.id.action_transport_category:
		    	refreshGridview(5);
		    	setCurrentCategory(5);
		    	return true;
    		case R.id.action_money_category:
		    	refreshGridview(6);
		    	setCurrentCategory(6);
		    	return true;
    		case R.id.action_food_category:
		    	refreshGridview(7);
		    	setCurrentCategory(7);
		    	return true;
    		case R.id.action_health_category:
		    	refreshGridview(8);
		    	setCurrentCategory(8);
		    	return true;
		    case R.id.action_exit:
		    	this.finish(); 
		        return true;
		    case R.id.action_add_phrase:
		    	Intent intent = new Intent(this, AddPhraseActivity.class);
		    	intent.putExtra("category", getCurrentCategory());
		    	startActivity(intent);
		    	return true;
		    case R.id.action_refresh_gridview:
		    	refreshGridview(0);
		    	setCurrentCategory(0);
		    	return true;
		    default:
		        return super.onOptionsItemSelected(item);
    	}    	
    }
    
    // Refresh source phrases list
    public void refreshGridview(int category) {
    	GridView gridview = (GridView) findViewById(R.id.gridview);
    	Util.getPhrasebook().reload(category);
    	((PhraseAdapter)gridview.getAdapter()).notifyDataSetChanged();    	
    }
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
    private List<String> getSourceItems() {
		return Util.getPhrasebook().getSourcePhrases();
	}

    // DeletePhrase dialog
	private AlertDialog.Builder createDeletePhraseAlert() {
    	AlertDialog.Builder deletePhraseAlert = new AlertDialog.Builder(this);
    	deletePhraseAlert.setTitle(R.string.action_delete_phrase_header);
    	deletePhraseAlert.setMessage(R.string.action_delete_phrase_message);

    	deletePhraseAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                
          } });
    	return deletePhraseAlert;
    }	
}
