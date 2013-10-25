package ru.zebro.phrasebook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{

	private static String DB_PATH;
	
    //стандартный системный путь к базе данных приложения
//    private static String RELATIVE_DB_PATH = "data/ru.zebro.phrasebook/databases/";

    private static String DB_NAME = "phrasebook";

    private SQLiteDatabase phrasebookDataBase;

    private final Context phrasebookContext;

    /**
     * Конструктор
     * Принимает и сохраняет ссылку на переданный контекст для доступа к ресурсам приложения
     * @param context
     */
    public DataBaseHelper(Context context) {

    	super(context, DB_NAME, null, 1);
        this.phrasebookContext = context;
        DB_PATH = context.getFilesDir().getPath(); // + RELATIVE_DB_PATH;
    }

  /**
     * Создает пустую базу данных и перезаписывает ее нашей собственной базой
     * */
    public void createDataBase() throws IOException{

    	boolean dbExist = checkDataBase();

    	if(dbExist){
    		//ничего не делать - база уже есть
    	}else{

    		//вызывая этот метод создаем пустую базу, позже она будет перезаписана
        	this.getReadableDatabase();

        	try {

    			copyDataBase();

    		} catch (IOException e) {

        		throw new Error("Error copying database");

        	}
    	}

    }

    /**
     * Проверяет, существует ли уже эта база, чтобы не копировать каждый раз при запуске приложения
     * @return true если существует, false если не существует
     */
    private boolean checkDataBase(){
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    /**
     * Копирует базу из папки assets заместо созданной локальной БД
     * Выполняется путем копирования потока байтов.
     * */
    private void copyDataBase() throws IOException{

    	//Открываем локальную БД как входящий поток
    	InputStream myInput = phrasebookContext.getAssets().open(DB_NAME);

    	//Путь ко вновь созданной БД
    	String outFileName = DB_PATH + DB_NAME;

    	//Открываем пустую базу данных как исходящий поток
    	OutputStream myOutput = new FileOutputStream(outFileName);

    	//перемещаем байты из входящего файла в исходящий
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}

    	//закрываем потоки
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();

    }

    public void openDataBase() throws SQLException{

    	//открываем БД
        String myPath = DB_PATH + DB_NAME;
        phrasebookDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

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
	
	@SuppressLint("UseSparseArrays")
	public List<Map<Integer, String>> getPhraseBook() {
		List<Map<Integer, String>> phrasebookList = new ArrayList<Map<Integer, String>>();
		Map<Integer, String> rusPhrasesMap = new HashMap<Integer, String>();
		Map<Integer, String> mandarinPhrasesMap = new HashMap<Integer, String>();
		Map<Integer, String> pinyinPhrasesMap = new HashMap<Integer, String>();
		
		String[] phraseColumns = {"_id", "_phrase_string"};
		
		Cursor cursor = phrasebookDataBase.query("_phrases_rus", phraseColumns, null, null, null, null, null);
		cursor.moveToPosition(0);
		
		do {
			rusPhrasesMap.put(cursor.getInt(0), cursor.getString(1));
		} while(cursor.moveToNext());
		phrasebookList.add(0, rusPhrasesMap);
		
		cursor = phrasebookDataBase.query("_phrases_mandarin", phraseColumns, null, null, null, null, null);
		cursor.moveToPosition(0);
		
		do {
			mandarinPhrasesMap.put(cursor.getInt(0), cursor.getString(1));
		} while(cursor.moveToNext());	
		phrasebookList.add(1, mandarinPhrasesMap);
		
		cursor = phrasebookDataBase.query("_phrases_pinyin", phraseColumns, null, null, null, null, null);
		cursor.moveToPosition(0);
		
		do {
			pinyinPhrasesMap.put(cursor.getInt(0), cursor.getString(1));
		} while(cursor.moveToNext());	
		phrasebookList.add(2, pinyinPhrasesMap);		
		
		return phrasebookList;
	}
	
	

        // Здесь можно добавить вспомогательные методы для доступа и получения данных из БД
        // вы можете возвращать курсоры через "return myDataBase.query(....)", это облегчит их использование
        // в создании адаптеров для ваших view

}