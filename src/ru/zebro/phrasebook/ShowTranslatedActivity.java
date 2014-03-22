package ru.zebro.phrasebook;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 *	This activity displays translated value 
 * */
public class ShowTranslatedActivity extends Activity {
	
	public static final String TRANSLATED_TEXT = "TRANSLATED_TEXT";
	
	public static final String TRANSLATED_TEXT_SPELLED = "TRANSLATED_TEXT_SPELLED";	
	
	public static final String SOURCE_TEXT = "SOURCE_TEXT";

	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_show_translated_phrase);
	    
	    TextView view = (TextView) findViewById(R.id.showTranslate);
	    TextView spellView = (TextView) findViewById(R.id.showTranslateSpell);
	    TextView sourceView = (TextView) findViewById(R.id.showSource);	    

	    view.setTextSize(getResources().getDimension(R.dimen.textsize));
	    view.setText(this.getIntent().getExtras().getString(TRANSLATED_TEXT));
	    
	    spellView.setTextSize(getResources().getDimension(R.dimen.textsizeSpelled));
	    spellView.setText(this.getIntent().getExtras().getString(TRANSLATED_TEXT_SPELLED));
	    
	    sourceView.setTextSize(getResources().getDimension(R.dimen.textsizeSpelled));
	    sourceView.setText(this.getIntent().getExtras().getString(SOURCE_TEXT));	    
	}
}
