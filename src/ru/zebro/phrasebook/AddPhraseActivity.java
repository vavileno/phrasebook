package ru.zebro.phrasebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPhraseActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_add_phrase);
	    
	    final Activity addPhrase = this;
	    
        final Button saveButton = (Button) findViewById(R.id.add_phrase_button_save);
        
        final EditText sourcePhraseEditText = (EditText) findViewById(R.id.sourcePhraseTextEdit);
        final EditText destinationPhraseEditText = (EditText) findViewById(R.id.destinationPhraseTextEdit);
        final EditText spellPhraseEditText = (EditText) findViewById(R.id.spellPhraseTextEdit);
        
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Util.getPhrasebook().savePhrase(
            			sourcePhraseEditText.getText().toString(),
            			destinationPhraseEditText.getText().toString(),
            			spellPhraseEditText.getText().toString(),
            			getIntent().getExtras().getInt("category"));
            	addPhrase.finish();
            	Util.getMainActivity().refreshGridview(0);
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
