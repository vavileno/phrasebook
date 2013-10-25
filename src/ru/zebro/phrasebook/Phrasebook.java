package ru.zebro.phrasebook;

import java.util.Map;

public class Phrasebook {

	private Map<Integer, String> sourcePhrasebookMap;
	
	private Map<Integer, String> destinationPhrasebookMap;

	public Phrasebook(Map<Integer, String> sourcePhrasebookMap,
			Map<Integer, String> destinationPhrasebookMap) {
		super();
		this.sourcePhrasebookMap = sourcePhrasebookMap;
		this.destinationPhrasebookMap = destinationPhrasebookMap;
	}
	
	public String getSourcePhrase(int i) {
		return sourcePhrasebookMap.get(i);
	}
	
	public String getDestinationPhrase(int i) {
		return destinationPhrasebookMap.get(i);
	}	
	
}
