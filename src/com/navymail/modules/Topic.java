package com.navymail.modules;

import java.util.ArrayList;
import java.util.HashMap;

public class Topic {
	public int id;
	public String title;
	public String conclusion;
	public ArrayList<String> imageUrls = new ArrayList<String>();
	public boolean eSigned = false;
	
	//public HashMap<Integer, Ta2shera> hash = new HashMap<Integer, Ta2shera>();

	public Topic() {
	}

	public Topic(int id, String title, String conclusion,
			ArrayList<String> imageUrls, boolean eSigned) {
		super();
		
		this.id = id;
		this.title = title;
		this.conclusion = conclusion;
		this.imageUrls = imageUrls;
		this.eSigned = eSigned;
		
/*		String title2 ="imageUrls";
		String Topic = "topic";*/
		
		this.conclusion = conclusion;
	}
}
