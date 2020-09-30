package application;

public class Mob 
{
	private int HP = 100;
	private int STR = 5;
	private int AGI = 1;
	private int LVL = 1;
	private String name = "Mob";
	public int getSTR() {
		return STR;
	}
	public int getAGI() {
		return AGI;
	}
	public int getLVL() {
		return LVL;
	}
	public String getName() {
		return name;
	}
	private boolean dead;                     
	public int attack(Player P1)
	{
		int x = (int)(Math.random() * ((8 - 0) + 1)) + this.STR;
		int mobDodge =(int)(Math.random() * ((100 - 0) + 1)) + this.AGI;
		int playerDodge =(int)(Math.random() * ((100 - 0) + 1)) + P1.getAGI();
		if (playerDodge > mobDodge)
		{
			return 0;
		}
		P1.hurt(x);
		return x;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public void setSTR(int sTR) {
		STR = sTR;
	}
	public void setAGI(int aGI) {
		AGI = aGI;
	}
	public void setLVL(int lVL) {
		LVL = lVL;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public int hurt(int x)
	{
		this.HP -= x;
		if (this.HP <= 0)
		{
			this.dead = true;
			System.out.println("Mob has died");
			return (int) ((this.LVL) + Math.random() * ((99 - 0) + 1));
		}
		return 0;
	}
	public int getHP() 
	{
		// TODO Auto-generated method stub
		return this.HP;
	}
	public boolean isDead()
	{
		return dead;
	}
	
}
