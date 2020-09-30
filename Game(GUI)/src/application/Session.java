package application;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Session implements EventHandler<ActionEvent>
{
	private ArrayList<Mob> mobList = new ArrayList<Mob>();
	private static Player thePlayer = new Player();
	Text gText;
	ScrollPane gPane;
	TextField gTField;
	String stringLog = "";
	private String userIn = null;
	int phase = 0;
	boolean gOver = false;
	boolean mobsCleared = false;
	int ablSelect = 0;
	int encounters = 0;
	int zone = 0;
	boolean targetACQ = false;
	boolean targetREQ = false;
	int[][] dngn = new int[5][5];
	
	
public void startSession(Text x, ScrollPane y, TextField z)
	{
		this.gText = x;
		this.gPane = y;
		this.gTField = z;
		gTField.setOnAction(this::handle);
		thePlayer =  Player.getPlayer();	
		this.stringToLog("What is the name of your Hero?");
			
	}
public void dngnCreate()
{
   for(int x =0; x <dngn.length; x++)
   {
	   for (int y = 0; y < dngn.length; y++)
	   {
		   dngn[x][y] = -1;
	   }
   }
   int startx =  (int) (Math.random() * (dngn.length -  0) + 0);
   int starty =  (int) (Math.random() * (dngn.length -  0) + 0);
   int finishx =  (int) (Math.random() * (dngn.length -  startx +3) + (startx +3));
   int finishy =  (int) (Math.random() * (dngn.length -  starty +3) + (starty +3));
   dngn[startx][starty] = 0;
   dngn[finishx][finishy] = 0;
   int xdiff = finishx - startx;
   int ydiff = finishy - starty;
   if (xdiff < 0)
   {
	   xdiff = xdiff * -1;
   }
   if (ydiff < 0)
   {
	   ydiff = ydiff * -1;
   }
   int dngnsize = xdiff + ydiff + 10;
   while (((xdiff >0)& (ydiff > 0)) && ((startx + starty) != (finishx + finishy)))
   {
	   int decide = (int) (Math.random() * (101 - 50 + 1) + 50);
	   if (decide >= 50)
	   {
		   dngn[++startx][starty] = 0;
		   xdiff--;
	   }
	   else if (decide < 49)
	   {
		   dngn[startx][++starty] = 0;
		   ydiff--;
	   }
	   else
	   {
		   System.out.println("Decision Initilization error");
	   }
   }

}

	private String getUIN()
	{
		return this.userIn;
	}
	public void spawnMobs()
	{
		
		System.out.println();
		for(int x = (int) (Math.random() * (4 - 1 + 1) + 1); x>0; x-- )
		{
			mobList.add(new Skeleton());
			
			System.out.println("A mob has been spawned");	
		}
		this.mobsCleared = false;
	}
	
	
	public void mobFuneral()
	{
		if (mobList.size() != 0)
		{
			for(int x = 0; x< this.mobList.size(); x++)
			{
				if (this.mobList.get(x).isDead() == true)
				{
					stringToLog(this.mobList.get(x).getName() + " " + (1+x) + " has died.");
					this.mobList.remove(x);
				}
			}
		}
		if (mobList.size() == 0)
		{
			this.mobsCleared = true;
		}
	}
	public void mobStats()
	{
		for(int x = 0; x< this.mobList.size(); x++)
		{
			stringToLog(this.mobList.get(x).getName() + " number "+ (x+1) + " has " + this.mobList.get(x).getHP() + " Health left.");
		}
	}
	public void stringToLog(String x)
	{
		
		stringLog += x;
		stringLog += "\n";
		gText.setText(stringLog);
		

		gPane.setContent(gText);
		gPane.setVvalue(1.0);
		
		
	}
	
	public void pAttack()
	{
		this.mobFuneral();
		if(this.ablSelect == 1)
		{
			int damage = thePlayer.attack(mobList.get(Integer.parseInt(getUIN()) - 1));
			if(damage == 0)
			{
				stringToLog(this.mobList.get(Integer.parseInt(getUIN())).getName() + " " + Integer.parseInt(getUIN()) + " dodged your attack!");
			}
			else if(damage == 0)
			{
				stringToLog("SOMETHING IS REALLY WRONG WITH ATTACK METHODS IF YOU CAN SEE THIS");
			}
			stringToLog("You attacked " + this.mobList.get((Integer.parseInt(getUIN()))-1).getName() + " number " + Integer.parseInt(getUIN())  + 
					" and did " + damage +" points of damage!");
			this.mobFuneral();
			this.targetACQ=false;
			this.targetREQ=false;
			this.ablSelect = 0;
		}
		else if(this.ablSelect == 2)
		{
			if (mobList.size()==1)
			{
				int damage = thePlayer.attack(mobList.get(0));
				if(damage == 0)
				{
					stringToLog(this.mobList.get(0).getName() + " " + 1 + " dodged your attack!");
				}
				else if(damage < 0)
				{
					stringToLog("SOMETHING IS REALLY WRONG WITH THE ATTACK METHODS IF YOU CAN SEE THIS");
				}
				stringToLog("You attacked " + this.mobList.get(0).getName() + " number " + 1  + 
						" and did " + damage +" points of damage!");
				if(mobList.size() == 0 )
				{
					this.ablSelect = 0;
					this.mobFuneral();
					return;
				}
				this.ablSelect = 0;
				this.mobFuneral();
				return;
			}
			
			int x = (int) ((Math.random() * ((mobList.size()-1) - 0)) + 0);
			int[] damageStats = thePlayer.attack(this.mobList.get(x),this.mobList.get(x+1));
			if (damageStats[0] ==0)
				{
					stringToLog(this.mobList.get(x).getName() + " number " + (x+1) + " dodged your attack!");
				}
			stringToLog("You attacked " + this.mobList.get(x).getName() + " number " + (x+1)  +
					" and did " + damageStats[0] +" points of damage!");
			if (damageStats[1] ==0)
			{
				stringToLog(this.mobList.get(x+1).getName() + " number " + (x+2) + " dodged your attack!");
			}
			stringToLog("You attacked " + this.mobList.get(x+1).getName() + " number " + (x+2)  +
					" and did " + damageStats[1] +" points of damage!");
			this.mobFuneral();
			this.ablSelect = 0;
		}
	}
	public boolean mobAttack()
	{
		for (int x = 0; x<mobList.size(); x++)
		{	int damage = mobList.get(x).attack(thePlayer);
		if(damage == 0)
		{
			stringToLog("You dodged " + this.mobList.get(0).getName() + "number " + (x+1) + "'s " + " attack!");
		}
		else if(damage < 0)
		{
			stringToLog("SOMETHING IS REALLY WRONG WITH THE ATTACK METHODS IF YOU CAN SEE THIS");
		}
			stringToLog(mobList.get(x).getName() + " number "+ (x+1) + " atacked you for " + damage + " points of damage!");
		}
		if(thePlayer.getHP() <= 0)
		{
			return true;
		}
		else
		{
		return false;
		}
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		try {
		this.userIn= gTField.getText();
		stringToLog( "--------Player Input---------"+"\n" + this.getUIN() + "\n" +"--------Player Input---------" );
		gTField.clear();
		
		if (this.phase == 0)
		{
			thePlayer.setName(this.getUIN());
			stringToLog("You Hero's name is " + this.getUIN() + ".");
			
			thePlayer.setHP(500);
			thePlayer.setSTMN(100);
			thePlayer.setSTR(30);
			thePlayer.setAGI(150);
			this.spawnMobs();
			stringToLog("You were hired by the local village to investigate this abandoned castle. People say they've seen and heard supernatural things from this place.");
			stringToLog("You eneter the castle are immediately attacked by enemies.");
			this.stringToLog("You have been approached by " + mobList.size() +" enemies.");
			this.mobStats();
			stringToLog("What ability would you like to use?");
			stringToLog("1. Slash : Choose one enemy in particular to attack");
			stringToLog("2. Cleave : Attack two random enemies at the same time");
			this.phase = 1;
			
			
		}
		else if (phase ==-1)
		{
			stringToLog("You are dead, please start over");
		}
		else if (this.phase == 2)
		{
			
			if (thePlayer.getskillP() < 1)
			{
				thePlayer.setskillP(0);
				stringToLog("You awake from you sleep, gather your belonings and leave the room. You continue down the hall to a new chamber");
				phase = 1;
				this.spawnMobs();
			}
			if (Integer.parseInt(getUIN()) == 2)
			{
				thePlayer.setHP((thePlayer.getHP()) + 20);
				thePlayer.setskillP((thePlayer.getskillP()) - 1);
				stringToLog("You now have " + thePlayer.getHP() + " HP");
				stringToLog("Skill points remaining : " + thePlayer.getskillP());
				stringToLog("1. STRENGTH" + "\n" + "2. ENDURANCE");
				if (thePlayer.getskillP() < 1)
				{
					thePlayer.setskillP(0);
					stringToLog("You awake from you sleep, gather your belonings and leave the room. You continue down the hall to a new chamber");
					phase = 1;
					this.spawnMobs();
				}
			}
			else if (Integer.parseInt(getUIN()) == 1)
			{
				thePlayer.setSTR((thePlayer.getSTR()) + 2);
				thePlayer.setskillP((thePlayer.getskillP()) - 1);
				stringToLog("You now have " + thePlayer.getSTR() + " STR");
				stringToLog("Skill points remaining : " + thePlayer.getskillP());
				stringToLog("1. STRENGTH" + "\n" + "2. ENDURANCE");
				if (thePlayer.getskillP() < 1)
				{
					thePlayer.setskillP(0);
					stringToLog("You awake from you sleep, gather your belonings and leave the room. You continue down the hall to a new chamber");
					phase = 1;
					this.spawnMobs();
				}
			}
			
		}
		else if (this.phase == 1 && this.ablSelect ==0)
		{
			if (Integer.parseInt(getUIN()) < 3 && Integer.parseInt(getUIN()) > 0)
			{
				ablSelect = Integer.parseInt(this.getUIN());
				if(ablSelect != 2)
				{
					this.targetREQ = true;
				}
			}
			else
			{
				stringToLog("That is not a valid entry, please select an ability to use.");
				stringToLog("1. Slash : Choose one enemy in particular to attack");
				stringToLog("2. Cleave : Attack two random enemies at the same time");
			}
			
		}
		if(this.phase== 1 && (this.targetREQ == true && this.targetACQ ==false))
		{
			stringToLog("Which enemy would you like to target?");
			this.targetACQ = true;
			return;
		}
		
		if (((this.phase== 1 && this.ablSelect !=0 ) & this.targetACQ==true) | (this.phase== 1 && this.ablSelect !=0 ))
		{
			
			while (mobsCleared == false)
			{
				
				try 
				{	
					this.pAttack();
					this.mobFuneral();
					if(  this.mobAttack() == true)
					{
						stringToLog("You died!");
						phase = -1;
						break;
					}
					stringToLog("\n");
					if( mobList.size() == 0)
					{
						mobsCleared = true;
					}
					if (mobsCleared == false)
					{
						
						this.mobStats();
						stringToLog("What ability would you like to use?");
						stringToLog("1. Slash : Choose one enemy in particular to attack");
						stringToLog("2. Cleave : Attack two random enemies at the same time");
					}
					
					
					if(mobsCleared==true)
					{
						int levels = thePlayer.getskillP();	
						if (levels != 0 & encounters == 2)
						{
							stringToLog("Continuing along to the throne room, you enter a hallway. Exausted you notice a door and open it hoping to rest." 
									+ "\n" + "You find a dusty, dilapidated but safe bed room. Without thinking you stumble onto the bed and close your eyes...");
							stringToLog("You have leveled up and can increase your abilities");
							stringToLog("Which ones will you level? Skill points remaining : " + thePlayer.getskillP());
							stringToLog("1. STRENGTH" + "\n" + "2. ENDURANCE");
							
							phase = 2;
						}
						else if (levels == 0 & encounters == 2)
						{
							encounters = 0;
							zone++;
							this.spawnMobs();
							stringToLog("After you get your bearing, you leave this chamber and enter the next, getting closer to the throne room.");
						}
						else if(encounters != 2)
						{
							this.spawnMobs();
							this.stringToLog(" NEW ENCOUNTER! You have been approached by " + mobList.size() + " mobs.");
							this.mobStats();
							stringToLog("What ability would you like to use?");
							stringToLog("1. Slash : Choose one enemy in particular to attack");
							stringToLog("2. Cleave : Attack two random enemies at the same time");
							encounters++;
						}
					
					}
					break;
					 
				}
				catch(NumberFormatException e)
				{
					stringToLog("1That is not a valid entry, please try again. ");
					break;	
				}
				catch(IndexOutOfBoundsException e)
				{
					stringToLog("2That is not a valid entry, please try again.");
					break;	
				}
				
			}

		}

	}
		catch(NumberFormatException e)
		{
			stringToLog("3That is not a valid entry, please try again.333333");
			return;
		
			
		}
	}

}
