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
	
	private int currentCategory; 
	
	private AlertDialog.Builder deletePhraseAlert;	
	
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
		final PhraseAdapter phraseAdapter = new PhraseAdapter(this);
		
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(phraseAdapter);
	    
	    final Context context = this;

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	Intent intent = new Intent(context, ShowTranslatedActivity.class);
		    	intent.putExtra(ShowTranslatedActivity.TRANSLATED_TEXT, 
		    			Util.getPhrasebook().getDestinationPhrase(position + 1));
		    	intent.putExtra(ShowTranslatedActivity.TRANSLATED_TEXT_SPELLED, 
		    			Util.getPhrasebook().getSpellingPhrase(position + 1));		    	
		    	startActivity(intent);
	        }
	    });
	    
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
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.activity_list_item, getItems());
        AutoCompleteTextView searchAutocomplete = (AutoCompleteTextView)findViewById(R.id.autoCompleteSearch);
        searchAutocomplete.setThreshold(3);
        searchAutocomplete.setDropDownHeight(10);
        searchAutocomplete.setAdapter(adapter);
	}
	
    private List<String> getItems() {
		return Util.getPhrasebook().getSourcePhrases();
	}

	private AlertDialog.Builder createDeletePhraseAlert() {
    	AlertDialog.Builder deletePhraseAlert = new AlertDialog.Builder(this);
    	deletePhraseAlert.setTitle(R.string.action_delete_phrase_header);
    	deletePhraseAlert.setMessage(R.string.action_delete_phrase_message);

    	deletePhraseAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                
          } });
    	return deletePhraseAlert;
    }	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
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
    
    public void refreshGridview(int category) {
    	GridView gridview = (GridView) findViewById(R.id.gridview);
    	Util.getPhrasebook().reload(category);
    	((PhraseAdapter)gridview.getAdapter()).notifyDataSetChanged();    	
    }
    
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
	
	
//	private void load() {
//		try{
//			InputStream myInput  = getApplicationContext().getAssets().open("input.txt");
//			
//			int ch;
//			StringBuilder source = new StringBuilder();
//			StringBuilder dest = new StringBuilder();
//			StringBuilder pronunc = new StringBuilder();
//			
//			StringBuilder current = source;
//			while((ch = myInput.read()) > 0) {
//				if((char)ch != '\t') {
//					current.append((char) ch);
//				}
//				else {
//
//				}
//			}
//			
//			  FileInputStream fstream = new FileInputStream("textfile.txt");
//			  // Get the object of DataInputStream
//			  DataInputStream in = new DataInputStream(fstream);
//			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
//			  String strLine;
//			  //Read File Line By Line
//			  while ((strLine = br.readLine()) != null)   {
//			  // Print the content on the console
//			  System.out.println (strLine);
//			  }
//			  //Close the input stream
//			  in.close();
//		}catch (Exception e)	{
//			  System.err.println("Error: " + e.getMessage());
//		}
//		
////		Util.getPhrasebook().savePhrase(sourcePhrase, destinationPhrase, pronuncPhrase)
//	}

}
