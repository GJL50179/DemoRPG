package application;

import java.io.File;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;


public class Skeleton extends Mob 
{
	File deathRattle = new File("soundfx\\\\skeletondeath.wav");
	File hurtRattle = new File("soundfx\\\\skeletonhurt.wav");
	Clip dthClip;
	Clip hrtClip;
	Skeleton()
	{
		try {
		this.setName("Skeleton");
		dthClip = AudioSystem.getClip();
		dthClip.open(AudioSystem.getAudioInputStream(deathRattle));
		hrtClip = AudioSystem.getClip();
		hrtClip.open(AudioSystem.getAudioInputStream(hurtRattle));
		}
		catch(Exception e)
		{}
	}
	public int hurt(int x)
	{	
		
		this.setHP(getHP() - x);
		hrtClip.start();
		try {
			Thread.sleep(250);
			hrtClip.stop();
			hrtClip.setFramePosition(0);
		
		
		if (this.getHP() <= 0)
		{
			this.setDead(true);
			dthClip.start();
			Thread.sleep(250);
			dthClip.stop();
			dthClip.setFramePosition(0);
			System.out.println("Mob has died");
			return (int) ((this.getLVL()) + Math.random() * ((99 - 0) + 1));
		}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	
	}
}
