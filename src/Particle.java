import java.awt.Color;
import java.lang.reflect.GenericArrayType;



public class Particle implements Comparable<Particle>{
	
	private static int counter = 1;
	private int ID;
	private Position pos; 
	private Velocity v;	
	private double radius;
	private double mass;
	private Color color;
	private int count = 0;
	
	public Particle(double radius, Color color, double mass, Position pos, Velocity v) {
		this.pos = pos;
		this.v = v;
		this.radius = radius;
		this.color = color;
		this.mass = mass;
		this.ID = counter++;
	}
	
	public Velocity getV() {
		return v;
	}
	
	public double getMass() {
		return mass;
	}
	
	public Position getPosition(){
		return pos;
	}
	
	
	public double getradius() {
		return radius;
	}
	

	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Particle)){
			return false;
		}else{
			Particle particleObj = (Particle) obj;
			return this.ID == particleObj.ID;
		}
	}
	
	public String toString(){
		return "" + ID + " " + radius;
	}

	@Override
	public int compareTo(Particle arg0) {
		return this.ID-arg0.getID();
	}
	
	public void updatePos(double time){
		double x = pos.getX()+v.getXVelocity()*time;
		double y = pos.getY()+v.getYVelocity()*time;
		this.pos = new Position(x,y);
	}
	
	public void setPosition(double x, double y){
		this.pos = new Position(x,y);
	}
	
	public double getDistance(Particle other){
		return Math.sqrt(Math.pow(pos.getX()-other.getPosition().getX(), 2) + Math.pow(pos.getY()-other.getPosition().getY(), 2))-radius-other.getradius();
	}
	public int getCount() {
		return count;
	}
	public void inCount(){
		count++;
	}
	
	public void collide(Particle p2) {
        double dx  = p2.getPosition().getX() - this.getPosition().getX();
        double dy  = p2.getPosition().getY() - this.getPosition().getY();
        double dvx = p2.getV().getXVelocity()- this.getV().getXVelocity();
        double dvy = p2.getV().getYVelocity() - this.getV().getYVelocity();
        double dvdr = dx*dvx + dy*dvy;            
        double dist = this.getradius() + p2.getradius(); 
        double F = 2 * this.getMass() * p2.getMass() * dvdr / ((this.getMass() + p2.getMass()) * dist);
        double fx = F * dx / dist;
        double fy = F * dy / dist;
        this.getV().setXVel(this.getV().getXVelocity()+ fx / this.getMass());
        this.getV().setYVel(this.getV().getYVelocity()+ fy / this.getMass());
        p2.getV().setXVel(p2.getV().getXVelocity()+fx / p2.getMass()) ;
        p2.getV().setYVel(p2.getV().getYVelocity() +fy / p2.getMass());
        this.count++;
        p2.count++;
    }
	
	public void bounceOffVerticalWall() {
        this.getV().setXVel( -this.getV().getXVelocity());
        count++;
    }

    public void bounceOffHorizontalWall() {
    	this.getV().setYVel( -this.getV().getYVelocity());
        count++;
    }
}
