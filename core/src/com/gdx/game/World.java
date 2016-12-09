package com.gdx.game;

import java.util.ArrayList;

public class World {
	
	private ArrayList<Question> qList;
	public int nowQuestion = 0;
	public static int state;
	public int winner = 0;
	public static int score1 = 0;
	public static int score2 = 0;
	
	public World() {
		qList = new ArrayList();
		state = 0;
	}
	
	public ArrayList<Question> getQList() {
		return qList;
	}

}
