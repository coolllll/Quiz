package com.gdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Question {
	
	public Texture q;
	public Array<Texture> a;
	public int[] arrOrder; 
	public int selectedAnswer;
	public int rightAnswer = 0;
	public Random aaa;
	
	public Question(int number) {
		q = new Texture("q"+number+".png");
		a = new Array<Texture>();
		for(int i = 1;i < 5;i++) {
			a.add(new Texture("a"+number+""+i+".png"));
		}
		arrOrder = new int[4];
		this.selectedAnswer = 0;
		aaa = new Random();
		genOrder();
	}
	
	public boolean chkAns() {
		return arrOrder[this.selectedAnswer-1] != this.rightAnswer;
	}
	
	public void genOrder() {
		for(int i=0;i<4;i++) {
			arrOrder[i] = i;
		}
		for(int i=0;i<100;i++) {
			int tmp = aaa.nextInt(4);
			int tmpp = aaa.nextInt(4);
			int ttmp = arrOrder[tmp];
			arrOrder[tmp] = arrOrder[tmpp];
			arrOrder[tmpp] = ttmp;
		}
	}

}
