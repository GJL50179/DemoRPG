package application;
public class Player 
{
	private int HP;
	private int HPmax;
	private int STMN;
	private int STMNmax;
	private int STR;
	private int AGI;
	private int XP;
	private int LVL;
	private String name;
	private static Player pRef = null;
	private boolean inCombat = false;
	private int skillP = 0;
   
	
	
	
	public int getAGI() {
		return AGI;
	}

	public void setAGI(int AGI) {
		this.AGI = AGI;
	}

	public int getskillP() {
		return skillP;
	}

	public void setskillP(int skillP) {
		this.skillP = skillP;
	}
	
	Player()
	{
	}
	
	public static Player getPlayer()
	{
		if (pRef == null)
		{
			pRef = new Player();
		}
		return pRef;
		
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public int getHP() 
	{
		return HP;
	}
	public void setHP(int HP) 
	{
		this.HP = HP;
		this.HPmax =HP;
	}
	public int getSTMN() 
	{
		return STMN;
	}
	public void setSTMN(int STMN) 
	{
		this.STMN = STMN;
	}
	public int getSTR() 
	{
		return STR;
	}
	
	public void setSTR(int STR) 
	{
		this.STR = STR;
	}
	
	public void modXP(int XP)
	{
		this.XP += XP;
		this.levelUp();
	}
	
	public void hurt(int x)
	{
		this.HP -= x;
		if (this.HP <= 0)
		{
			System.out.println("Player has died");
		}
	}
	public int attack(Mob x)
	{
		int z = (int)(Math.random() * ((10 - 0) + 1)) + this.STR;
		int playerDodge =(int)(Math.random() * ((100 - 0) + 1)) + this.AGI;
		int mobDodge =(int)(Math.random() * ((100 - 0) + 1)) + x.getAGI();
		if (mobDodge > playerDodge)
		{
			return 0;
		}
		this.modXP(x.hurt(z)) ;
		return z;
	}
	public int[] attack(Mob x, Mob y)
	{
		int[] damageDisplay = new int[2];
		int playerDodge =(int)(Math.random() * ((100 - 0) + 1)) + this.AGI;
		int mobxDodge =(int)(Math.random() * ((100 - 0) + 1)) + x.getAGI();
		int mobyDodge =(int)(Math.random() * ((100 - 0) + 1)) + y.getAGI();
		int damage = (int)(Math.random() * ((11 - 0) + 1)) + this.STR;
		if( mobxDodge < playerDodge)
		{
		damageDisplay[0] = damage;
		this.modXP(x.hurt(damage));
		}
		else if ( mobxDodge > playerDodge)
		{
			damageDisplay[0] = 0;
		}
		damage = (int)(Math.random() * ((11 - 0) + 1)) + this.STR;
		if( mobxDodge < playerDodge)
		{
		damageDisplay[1] = damage;
		this.modXP(y.hurt(damage));
		}
		
		else if ( mobxDodge > playerDodge)
		{
			damageDisplay[1] = 0;
		}
		return damageDisplay;
	}
	public void levelUp()
	{
		
		if(this.XP >= (50 * this.LVL + (((int) this.LVL/15)*20)))
		{
			this.LVL += 1;
			System.out.println("Player has leveled up and is now level " + this.LVL + " !");
			this.skillP++;
			this.HP = this.HPmax;
			this.levelUp();
			
		}
		
	}

	public String getName() {
		return this.name;
	}
	
	 
}
