package ru.zebro.phrasebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddPhraseActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_add_phrase);
	    
	    final Activity addPhrase = this;
	    
        final Button saveButton = (Button) findViewById(R.id.add_phrase_button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	addPhrase.finish();
            }
        });	 
        
        final Button cancelButton = (Button) findViewById(R.id.add_phrase_button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	addPhrase.finish();
            }
        });	        
	}	

	
}
