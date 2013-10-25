package ru.zebro.phrasebook;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;


public class ShowTranslatedActivity extends Activity {
	
	public static final String TRANSLATED_TEXT = "TRANSLATED_TEXT";
	
	public static final String TRANSLATED_TEXT_SPELLED = "TRANSLATED_TEXT_SPELLED";	

	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_show_translated_phrase);
	    
	    TextView view = (TextView) findViewById(R.id.showTranslate);
	    TextView spellView = (TextView) findViewById(R.id.showTranslateSpell);

	    view.setTextSize(getResources().getDimension(R.dimen.textsize));
	    view.setText(this.getIntent().getExtras().getString(TRANSLATED_TEXT));
	    
	    spellView.setTextSize(getResources().getDimension(R.dimen.textsizeSpelled));
	    spellView.setText(this.getIntent().getExtras().getString(TRANSLATED_TEXT_SPELLED));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.finish();
		return true;
	}
}
