package ru.zebro.phrasebook;

import java.util.Map;

public class Phrasebook {

	private Map<Integer, String> sourcePhrasebookMap;
	
	private Map<Integer, String> destinationPhrasebookMap;
	
	private Map<Integer, String> spellingPhrasebookMap;

	public Phrasebook(Map<Integer, String> sourcePhrasebookMap,
			Map<Integer, String> destinationPhrasebookMap, Map<Integer, String> spellingPhrasebookMap) {
		super();
		this.sourcePhrasebookMap = sourcePhrasebookMap;
		this.destinationPhrasebookMap = destinationPhrasebookMap;
		this.spellingPhrasebookMap = spellingPhrasebookMap;
	}
	
	public String getSourcePhrase(int i) {
		return sourcePhrasebookMap.get(i);
	}
	
	public String getDestinationPhrase(int i) {
		return destinationPhrasebookMap.get(i);
	}
	
	public String getSpellingPhrase(int i) {
		return spellingPhrasebookMap.get(i);
	}	
	
}
