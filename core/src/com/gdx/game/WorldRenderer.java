package com.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class WorldRenderer {
	
	private BitmapFont font;
	public SpriteBatch batch;
	public World world;
	private Array<Texture> ansCard;
	private Array<Texture> number;
	private Sound correct;
	private Sound incorrect;
	private boolean isPlay;
	private Texture winer[];
	
	public WorldRenderer(World world) {
		this.world = world;
		font = new BitmapFont();
		batch = Quize.batch;
		ansCard = new Array<Texture>();
		for(int i = 1;i < 4;i++) {
			ansCard.add(new Texture("card"+i+".png"));
		}
		number = new Array<Texture>();
		for(int i=1;i<6;i++) {
			number.add(new Texture(i+".png"));
		}
		correct = Gdx.audio.newSound(Gdx.files.internal("sound/correct.wav"));
		incorrect = Gdx.audio.newSound(Gdx.files.internal("sound/wrong.wav"));
		winer = new Texture[2];
		winer[0] = new Texture("p1.png");
		winer[1] = new Texture("p2.png");
	}
	
	public void render() {
		batch.begin();
		if(World.state == 0) {
			batch.draw(new Texture("start.png"),0,0);
		}
		else if(World.state == 3) {
			batch.draw(world.getQList().get(world.nowQuestion).q, 0,0);
		} 
		else if (World.state == 2) {
			drawCountDown();
		}
		else if (World.state == 4) {
			drawRoundWinner();
		}
		else if (World.state == 5) {
			drawAnsCard();
			drawAnsChoice();
			isPlay = false;
		}
		else if (World.state == 6) {
			drawAnsCard();
			drawRealAnsCard();
			drawAnsChoice();
		}
		else if (World.state == 7) {
			Texture tmp;
			if(World.score1 > World.score2) {
				tmp = new Texture("p1w.png");
			}
			else if(World.score1 == World.score2) {
				tmp = new Texture("draw.png");
			}
			else {
				tmp = new Texture("p2w.png");
			}
			batch.draw(tmp,0,0);
		}
		batch.end();
	}
	
	public void drawAnsCard() {
		batch.draw(ansCard.get(0), 0, 0);
		batch.draw(ansCard.get(0), Quize.WIDTH/2, 0);
		batch.draw(ansCard.get(0), 0, Quize.HEIGHT/2);
		batch.draw(ansCard.get(0), Quize.WIDTH/2, Quize.HEIGHT/2);
	}
	
	public void drawRealAnsCard() {
		int tmp = world.getQList().get(world.nowQuestion).selectedAnswer;
		boolean tmp2 = world.getQList().get(world.nowQuestion).chkAns();
		if(tmp == 3) {
			if(tmp2) {
				batch.draw(ansCard.get(1), 0, 0);
			}
			else {
				batch.draw(ansCard.get(2), 0, 0);
			}
		}
		if(tmp == 4) {
			if(tmp2) {
				batch.draw(ansCard.get(1), Quize.WIDTH/2, 0);
			}
			else {
				batch.draw(ansCard.get(2), Quize.WIDTH/2, 0);
			}
		}
		if(tmp == 1) {
			if(tmp2) {
				batch.draw(ansCard.get(1), 0, Quize.HEIGHT/2);
			}
			else {
				batch.draw(ansCard.get(2), 0, Quize.HEIGHT/2);
			}
		}
		if(tmp == 2) {
			if(tmp2) {
				batch.draw(ansCard.get(1), Quize.WIDTH/2, Quize.HEIGHT/2);
			}
			else {
				batch.draw(ansCard.get(2), Quize.WIDTH/2, Quize.HEIGHT/2);
			}
		}
		if(tmp2 && !isPlay) {
			incorrect.play();
			isPlay = true;
		} else if(!isPlay) {
			correct.play();
			isPlay = true;
		}
		//font.draw(batch,"test = "+GameScreen.test,30,30);
	}
	
	public void drawAnsChoice() {
		batch.draw(world.getQList().get(world.nowQuestion).a.get(world.getQList().get(world.nowQuestion).arrOrder[2]), 0, 0);
		batch.draw(world.getQList().get(world.nowQuestion).a.get(world.getQList().get(world.nowQuestion).arrOrder[3]), Quize.WIDTH/2, 0);
		batch.draw(world.getQList().get(world.nowQuestion).a.get(world.getQList().get(world.nowQuestion).arrOrder[0]), 0, Quize.HEIGHT/2);
		batch.draw(world.getQList().get(world.nowQuestion).a.get(world.getQList().get(world.nowQuestion).arrOrder[1]), Quize.WIDTH/2, Quize.HEIGHT/2);
	}
	
	public void drawCountDown() {
		if((int)GameScreen.delayTime < 3) {
			batch.draw(number.get((int)GameScreen.delayTime),0,0);
		}
	}
	
	public void drawRoundWinner() {
		batch.draw(winer[world.winner - 1],0,0);
	}

}
