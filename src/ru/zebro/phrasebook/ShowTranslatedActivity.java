package ru.zebro.phrasebook;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;


public class ShowTranslatedActivity extends Activity {
	
	public static final String TRANSLATED_TEXT = "TRANSLATED_TEXT";	

	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_show_translated_phrase);
	    
	    TextView view = (TextView) findViewById(R.id.showTranslate);
	    

	    view.setTextSize(getResources().getDimension(R.dimen.textsize));
	    view.setText(this.getIntent().getExtras().getString(TRANSLATED_TEXT));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.finish();
		return true;
	}
}
