package com.gdx.game;

import java.util.ArrayList;
import java.util.Random;

import org.usb4java.Device;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends ScreenAdapter {
	
	private WorldRenderer worldRenderer;
	private World world;
	public static float delayTime = 0;
	private ArrayList<Question> qList;
	public static int quizNumber = 10;
	public Random random;
	private McuWithPeriBoard peri;
	public static int test = 0;
	public GameScreen() {
		world = new World();
		worldRenderer = new WorldRenderer(world);
		qList = world.getQList();
		random = new Random();
		genQuestion();
		genBoard();
	}
	
	@Override
	public void render(float delta) {
		
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		worldRenderer.render();
	}
	
	public void update(float delta) {
		//test = peri.getSwitch();
		if(countdown(delta)) {
			return;
		}
		if(World.state == 0) {
			this.toQuestionState();
			//World.state = 1;
		} 
		else if (World.state == 1) {
			this.toCountDownState();
		}
		else if (World.state == 2) {
			this.toPressState();
		}
		else if (World.state == 3) {
			this.pressState();
		}
		else if (World.state == 4) {
			this.toAnswerState();
		}
		else if (World.state == 5) {
			this.answerState();
		}
		else if (World.state == 6 && qList.get(world.nowQuestion).chkAns()) {
			World.state = 4;
			this.delayTime = 3;
			world.winner = world.winner%2 + 1;
		} else if (World.state == 6) {
			if(world.winner == 1) {
				world.score1 ++;
			} else {
				world.score2 ++;
			}
			if(world.nowQuestion < this.quizNumber-1) {
				world.state = 1;
				world.nowQuestion++;
			} else {
				world.state = 7;
			}
		}
	}
	
	public boolean countdown(float delta) {
		if(delayTime > 0) {
			delayTime -= delta;
			return true;
		}
		delayTime = 0;
		return false;
	}
	
	public void toQuestionState() {
		if(peri.getSwitch() != 0){
			World.state = 1;
		}
		//this.delayTime = 3;
	}
	
	public void toCountDownState() {
		World.state = 2;
		this.delayTime = 3;
	}
	
	public void toPressState() {
		World.state = 3;
	}
	
	public void pressState() {
		world.winner = 0;
		if(peri.getSwitch() == 1) {
			world.winner = 1;
		} else if(peri.getSwitch() == 2) {
			world.winner = 2;
		}
		if (world.winner != 0) {
			World.state = 4;
			this.delayTime = 3;
		}
	}
	
	public void toAnswerState() {
		World.state = 5;
	}
	
	public void answerState() {
		qList.get(world.nowQuestion).selectedAnswer = 0;
		if(peri.getSwitch() == 3) 
		{
			qList.get(world.nowQuestion).selectedAnswer = 1;
			
		} else if(peri.getSwitch() == 4) 
		{
			qList.get(world.nowQuestion).selectedAnswer = 2;
			
		} else if(peri.getSwitch() == 5) 
		{
			qList.get(world.nowQuestion).selectedAnswer = 3;
			
		} else if(peri.getSwitch() == 6) 
		{
			qList.get(world.nowQuestion).selectedAnswer = 4;
		}
		if (qList.get(world.nowQuestion).selectedAnswer != 0) {
			World.state = 6;
			this.delayTime = 1;
		}
	}
	public void genQuestion() {
		int[] a = new int[this.quizNumber];
		for(int i=0;i<this.quizNumber;i++) {
			a[i] = i+1;
		}
		/*for(int i=0;i<100;i++) {
			int tmp = random.nextInt(this.quizNumber);
			int tmpp = random.nextInt(this.quizNumber);
			int ttmp = a[tmp];
			a[tmp] = a[tmpp];
			a[tmpp] = ttmp;
		}*/
		for(int i=0;i<this.quizNumber;i++) {
			this.qList.add(new Question(a[i]));
		}
	}
	
	public void genBoard() {
		
		McuBoard.initUsb();
		try
		{
			Device[] devices = McuBoard.findBoards();
			peri = new McuWithPeriBoard(devices[0]);
			
		} 
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
