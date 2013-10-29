package ru.zebro.phrasebook;

import java.io.IOException;

import android.content.Context;


public class Util {
    
    private static Phrasebook phrasebook;
    
	private static DataBaseHelper dbHelper;    
	
	private static MainActivity mainActivity;
	
	public static void initDb(Context context) {
		dbHelper = new DataBaseHelper(context);
		
        try {
//        	if(!dbHelper.initDbFromCsvFile()) {
        		dbHelper.createDataBase();
//        	}
			dbHelper.openDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public static Phrasebook getPhrasebook() {
		if(phrasebook == null) {
			phrasebook = new Phrasebook(dbHelper);
			phrasebook.reload(0);
		}
		return phrasebook;
	}

	public static MainActivity getMainActivity() {
		return mainActivity;
	}	
	
	public static void setMainActivity(MainActivity mainActivity) {
		if(Util.mainActivity == null) {
			Util.mainActivity = mainActivity;			
		}
	}
	
	public static void closeDb() {
		if(dbHelper != null) {
			dbHelper.close();			
		}		
	}

}
