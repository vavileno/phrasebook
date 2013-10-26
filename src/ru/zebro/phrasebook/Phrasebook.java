package ru.zebro.phrasebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;

public class Phrasebook {
	
	private String STRING_PHRASE_COLUMN_NAME = "_phrase_string";
	
	private String STRING_CATEGORY_COLUMN_NAME = "_category_num";
	
	private Map<Integer, String> sourcePhrasebookMap;
	
	private Map<Integer, String> destinationPhrasebookMap;
	
	private Map<Integer, String> pronuncPhrasebookMap;
	
	private List<Integer> currentCategoryIds = new ArrayList<Integer>();
	
	private DataBaseHelper dbhelper;

	public Phrasebook(DataBaseHelper dbHelper) {
		this.dbhelper = dbHelper;
	}
	
	public void reload(int category) {
		List<Map<Integer, String>> phraseBookList = dbhelper.loadPhraseBook();
		
		sourcePhrasebookMap = phraseBookList.get(0);
		destinationPhrasebookMap = phraseBookList.get(1);
		pronuncPhrasebookMap = phraseBookList.get(2);
		
		if(category != 0) {
			currentCategoryIds = dbhelper.loadCategoryPhraseIds(category);
		}
		else {
			currentCategoryIds.clear();
			currentCategoryIds.add(0);
			currentCategoryIds.addAll(sourcePhrasebookMap.keySet());
		}
	}
	
	public Phrasebook(Map<Integer, String> sourcePhrasebookMap,
			Map<Integer, String> destinationPhrasebookMap, 
			Map<Integer, String> pronuncPhrasebookMap, 
			DataBaseHelper dbhelper) {
		super();
		this.sourcePhrasebookMap = sourcePhrasebookMap;
		this.destinationPhrasebookMap = destinationPhrasebookMap;
		this.pronuncPhrasebookMap = pronuncPhrasebookMap;
		this.dbhelper = dbhelper;
	}
	
	public String getSourcePhrase(int i) {
		return sourcePhrasebookMap.get(currentCategoryIds.get(i));
	}
	
	public String getDestinationPhrase(int i) {
		return destinationPhrasebookMap.get(currentCategoryIds.get(i));
	}
	
	public String getSpellingPhrase(int i) {
		return pronuncPhrasebookMap.get(currentCategoryIds.get(i));
	}
	
	public void savePhrase(String sourcePhrase, String destinationPhrase, String pronuncPhrase) {
		ContentValues cv = new ContentValues();
		cv.put(STRING_PHRASE_COLUMN_NAME, sourcePhrase);
		dbhelper.save(DataBaseHelper.STRING_SOURCE_TABLE_NAME, cv);
		cv.put(STRING_PHRASE_COLUMN_NAME, destinationPhrase);
		dbhelper.save(DataBaseHelper.STRING_DESTINATION_TABLE_NAME, cv);
		cv.put(STRING_PHRASE_COLUMN_NAME, pronuncPhrase);
		dbhelper.save(DataBaseHelper.STRING_PRONUNCATION_TABLE_NAME, cv);
		cv.put(STRING_CATEGORY_COLUMN_NAME, 1);
		dbhelper.save(DataBaseHelper.STRING_CATEGORY_TABLE_NAME, cv);
	}
	
	public void deletePhrase(int i) {
		dbhelper.deleteById(DataBaseHelper.STRING_SOURCE_TABLE_NAME, STRING_PHRASE_COLUMN_NAME, i);
		dbhelper.deleteById(DataBaseHelper.STRING_DESTINATION_TABLE_NAME, STRING_PHRASE_COLUMN_NAME, i);
		dbhelper.deleteById(DataBaseHelper.STRING_PRONUNCATION_TABLE_NAME, STRING_PHRASE_COLUMN_NAME, i);
	}

	public int getSize() {
		return currentCategoryIds.size() - 1;
	}
}
