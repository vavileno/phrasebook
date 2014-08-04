package ru.zebro.phrasebook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;

public class Phrasebook implements ArrayContainer<String> {
	
	private String STRING_PHRASE_COLUMN_NAME = "_phrase_string";
	
	private String STRING_CATEGORY_COLUMN_NAME = "_category_num";
	
	private String STRING_CATEGORY_ID_COLUMN = "_id";
	
	private LinkedHashMap<Integer, String> sourcePhrasebookMap;
	
	private LinkedHashMap<Integer, String> destinationPhrasebookMap;
	
	private LinkedHashMap<Integer, String> pronuncPhrasebookMap;
	
	/**
	 *	This list contents phrase ID list for current selected category
	 * */ 
	private List<Integer> currentCategoryIds = new ArrayList<>();

    private int currentCategory;
	
	private DataBaseHelper dbhelper;

	public Phrasebook(DataBaseHelper dbHelper) {
		this.dbhelper = dbHelper;
	}
	
	public void reload(int category) {
		List<LinkedHashMap<Integer, String>> phraseBookList = dbhelper.loadPhraseBook();
		
		sourcePhrasebookMap = phraseBookList.get(0);
		destinationPhrasebookMap = phraseBookList.get(1);
		pronuncPhrasebookMap = phraseBookList.get(2);
		
		if(category != 0) {
			currentCategoryIds = dbhelper.loadCategoryPhraseIds(category);
            currentCategory = category;
		}
		else {
            currentCategory = category;
			currentCategoryIds.clear();
			currentCategoryIds.add(0);
			currentCategoryIds.addAll(sourcePhrasebookMap.keySet());
		}
	}
	
	public Phrasebook(LinkedHashMap<Integer, String> sourcePhrasebookMap,
                      LinkedHashMap<Integer, String> destinationPhrasebookMap,
                      LinkedHashMap<Integer, String> pronuncPhrasebookMap,
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
	
	public String getDestinationPhrase(String sourcePhrase) {
		int i = getIdBySourcePhrase(sourcePhrase);
		return destinationPhrasebookMap.get(i);
	}
	
	public String getSpellingPhrase(String sourcePhrase) {
		int i = getIdBySourcePhrase(sourcePhrase);
		return pronuncPhrasebookMap.get(i);
	}	
	
	public List<String> getSourcePhrases() {
		List<String> result = new ArrayList<String>(sourcePhrasebookMap.values());
		return result;
	}	
	
	public void savePhrase(String sourcePhrase, String destinationPhrase, String pronuncPhrase, int category) {
		ContentValues cv = new ContentValues();
		cv.put(STRING_PHRASE_COLUMN_NAME, sourcePhrase);
		dbhelper.save(DataBaseHelper.STRING_SOURCE_TABLE_NAME, cv);
		cv.put(STRING_PHRASE_COLUMN_NAME, destinationPhrase);
		dbhelper.save(DataBaseHelper.STRING_DESTINATION_TABLE_NAME, cv);
		cv.put(STRING_PHRASE_COLUMN_NAME, pronuncPhrase);
		dbhelper.save(DataBaseHelper.STRING_PRONUNCATION_TABLE_NAME, cv);
		
		Util.getPhrasebook().reload(category);
		
		cv.clear();
		cv.put(STRING_CATEGORY_ID_COLUMN, getIdBySourcePhrase(sourcePhrase));
		cv.put(STRING_CATEGORY_COLUMN_NAME, category > 0 ? category : 1);
		dbhelper.save(DataBaseHelper.STRING_CATEGORY_TABLE_NAME, cv);
	}
	
	public void deletePhrase(CharSequence sourcePhrase) {
		int id = getIdBySourcePhrase(sourcePhrase.toString());
		dbhelper.deleteById(DataBaseHelper.STRING_SOURCE_TABLE_NAME, id);
		dbhelper.deleteById(DataBaseHelper.STRING_DESTINATION_TABLE_NAME, id);
		dbhelper.deleteById(DataBaseHelper.STRING_PRONUNCATION_TABLE_NAME, id);
		dbhelper.deleteById(DataBaseHelper.STRING_CATEGORY_TABLE_NAME, id);
	}

	public int getSize() {
		return currentCategoryIds.size() - 1;
	}
	
	private int getIdBySourcePhrase(String phrase) {
		if(phrase == null || phrase.isEmpty()) {
			return 0;
		}
		for(Map.Entry<Integer, String> entry : sourcePhrasebookMap.entrySet()) {
			if(phrase.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return 0;
	}

    @Override
    public List<String> getArray() {
        List<String> result = new ArrayList<>();
        for(int i=1; i < currentCategoryIds.size(); i++) {
            result.add(sourcePhrasebookMap.get(currentCategoryIds.get(i)));
        }
        return result;
    }
}
