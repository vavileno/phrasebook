package ru.zebro.phrasebook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("UseSparseArrays")
public class DataBaseHelper extends SQLiteOpenHelper {
	
	public static final String STRING_ID_COLUMN_NAME = "_id";
	
	public static final String STRING_SOURCE_TABLE_NAME = "_phrases_rus";
	
	public static final String STRING_DESTINATION_TABLE_NAME = "_phrases_mandarin";
	
	public static final String STRING_PRONUNCATION_TABLE_NAME = "_phrases_pinyin";	
	
	public static final String STRING_CATEGORY_TABLE_NAME = "_phrases_category";
	
	private static String DB_PATH;
	
    private static String DB_NAME = "phrasebook";

    private SQLiteDatabase phrasebookDataBase;

    private final Context phrasebookContext;

    /**
     * @param context
     */
    public DataBaseHelper(Context context) {

    	super(context, DB_NAME, null, 1);
        this.phrasebookContext = context;
        DB_PATH = context.getFilesDir().getPath(); // + RELATIVE_DB_PATH;
    }

    /**
     *	Creates empty database and fills with values from resource file 
     * */
    public void createDataBase() throws IOException{

    	boolean dbExist = checkDataBase();

    	if(dbExist){
    		// nothing
    	}else{

        	this.getReadableDatabase();

        	try {

    			copyDataBase();

    		} catch (IOException e) {

        		throw new Error("Error copying database");

        	}
    	}
    }

    private boolean checkDataBase(){
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    /**
     * Copies database from assets folder overwriting earlier created local db
     * */
    private void copyDataBase() throws IOException{

    	InputStream myInput = phrasebookContext.getAssets().open(DB_NAME);

    	String outFileName = DB_PATH + DB_NAME;

    	OutputStream myOutput = new FileOutputStream(outFileName);

    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}

    	myOutput.flush();
    	myOutput.close();
    	myInput.close();

    }

    public void openDataBase() throws SQLException{

        String myPath = DB_PATH + DB_NAME;
        phrasebookDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
	public synchronized void close() {
    	    if(phrasebookDataBase != null)
    	    	phrasebookDataBase.close();
    	    super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	// Loads phrase IDs for given category
	public List<Integer> loadCategoryPhraseIds(int category) {
		List<Integer> result = new ArrayList<Integer>();
		
		String[] phraseColumns = {"_id"};
		Cursor cursor = phrasebookDataBase.query("_phrases_category", 
													phraseColumns, 
													"_category_num = " + category, 
													null, null, null, null);
		if(cursor.getCount() == 0) {
			result.add(0);
			return result;
		} 
		cursor.moveToPosition(0);
		do {
			result.add(cursor.getInt(0));
		} while(cursor.moveToNext());	
		return result;		
	}	
	
	// Loads source, pronuncation and translated phrases in memory
	@SuppressLint("UseSparseArrays")
	public List<Map<Integer, String>> loadPhraseBook() {
		List<Map<Integer, String>> phrasebookList = new ArrayList<Map<Integer, String>>();
		String[] phraseColumns = {"_id", "_phrase_string"};
		
		Cursor cursor = phrasebookDataBase.query("_phrases_rus", phraseColumns, null, null, null, null, null);
		phrasebookList.add(0, loadPhrasesMap(cursor));
		
		cursor = phrasebookDataBase.query("_phrases_mandarin", phraseColumns, null, null, null, null, null);
		phrasebookList.add(1, loadPhrasesMap(cursor));
		
		cursor = phrasebookDataBase.query("_phrases_pinyin", phraseColumns, null, null, null, null, null);
		phrasebookList.add(2, loadPhrasesMap(cursor));		
		
		return phrasebookList;
	}
	
	private Map<Integer, String> loadPhrasesMap(Cursor cursor) {
		if(cursor.getCount() == 0) {
			return Collections.emptyMap();
		} 
		cursor.moveToPosition(0);
		
		Map<Integer, String> phrasesMap = new HashMap<Integer, String>();
		do {
			phrasesMap.put(cursor.getInt(0), cursor.getString(1));
		} while(cursor.moveToNext());	
		return phrasesMap;		
	}

	public void save(String table, ContentValues values) {
		phrasebookDataBase.insert(table, null, values);
	}

	public void deleteById(String tableName, Integer id) {
		phrasebookDataBase.delete(tableName, STRING_ID_COLUMN_NAME + " = " + id, null);
	}


        // Здесь можно добавить вспомогательные методы для доступа и получения данных из БД
        // вы можете возвращать курсоры через "return myDataBase.query(....)", это облегчит их использование
        // в создании адаптеров для ваших view

}