 /*
 * CSYE 6205
 * @author Wenbo Sun
 * NUID:001994516
 */
package motion;
import java.util.TimerTask;

import threeBody.Body;
//class to caculate the motion
public abstract class Motion extends TimerTask{
	public static int n=15; 				//15 agents for each body, totaly 45 agents;
	public static int g =100;				//PSO loop times
	int scope = 1;          //PSO scope
	double w = 0.729; 		//inertia weight
    double c1 = 1.49445; 	//cognitive/local weight
    double c2 = 1.49445; 	//social/global weight
    double[][] v = new double[n][3];		//Velocity
    //range for particle Velocity
    double[] vmax = new double[]{scope*0.01,scope*0.01,scope*0.01};
	double[] vmin = new double[]{-scope*0.01,-scope*0.01,-scope*0.01};

	static double t = 0.001;		//second(s) 	every 0.001 second as a view point
	public static int count=0; 			//count the pso loop
	
	//init information for three body
	public static double[] redposition; 	//meter(m)
	public static double[] redvelocity; 	//meter/second(m/s)
	public static double redmass;			//kiloton(kt)
	public static double[] greenposition;
	public static double[] greenvelocity;
	public static double greenmass;
	public static double[] blueposition;
	public static double[] bluevelocity;
	public static double bluemass;
	public static Body red;
	public static Body green;
	public static Body blue;
	public  Body tempred;
	public  Body tempgreen;
	public  Body tempblue;
	//init body
	public static void init(){
		red = new Body(redposition,redvelocity,redmass);
		green = new Body(greenposition,greenvelocity,greenmass);
		blue = new Body(blueposition,bluevelocity,bluemass); 
	}
	
	//get the distance between two point
	//d=¡Ì[(x1-x2)^2+(y1-y2)^2+(z1-z2)^2]
	public static double getDistance(double[] p1,double[] p2){
		double distance = Math.pow(Math.pow(p1[0]-p2[0], 2)
								  +Math.pow(p1[1]-p2[1], 2)
								  +Math.pow(p1[2]-p1[2], 2),1.0/2);
		return distance;
	}
	
	//get the acceleration for body tar
	//a=Gm/r^2
	public static double[] getAcceleration(Body tar,Body b){
		// adjust the G to make the motivition more Observable
		double G = 6.67*Math.pow(10,-2);
		double distance = getDistance(tar.position,b.position);
		double acceleration = G*b.mass*Math.pow(10, 6)/Math.pow(distance, 2);
		double accelerations[] = new double[]{acceleration*(b.position[0]-tar.position[0])/distance,
											  acceleration*(b.position[1]-tar.position[1])/distance,
											  acceleration*(b.position[2]-tar.position[2])/distance};
		return accelerations;
	}
	
	//get the approximate position for next viewpoint (next 0.1 ms)
	//x=v0*t+at^2/2
	public double[] getPosition(Body tar,Body b1, Body b2){
		double acc[] = new double[]{getAcceleration(tar,b1)[0]+getAcceleration(tar,b2)[0],
									getAcceleration(tar,b1)[1]+getAcceleration(tar,b2)[1],
									getAcceleration(tar,b1)[2]+getAcceleration(tar,b2)[2]};
		
		double position[]= new double[]{tar.position[0]+tar.velocity[0]*t+acc[0]*Math.pow(t, 2)/2,
									    tar.position[1]+tar.velocity[1]*t+acc[1]*Math.pow(t, 2)/2,
									    tar.position[2]+tar.velocity[2]*t+acc[2]*Math.pow(t, 2)/2};
		return position;
	}
	
	//get velocity for next viewpoint(next 0.1 ms) by position
	//x=v0*t+at^2/2  so a=2(x-v0t)/t^2
	//v=¡Ì(v0^2+2ax)
	//so v=¡Ì(v0^2+2x*2(x-v0t)/t^2)=¡Ì(v0^2+4x(x-v0t)/t^2)
	//get velocity for next viewpoint(next 0.1 ms)
	public static double[] getVelocity(Body tar,Body b1, Body b2){
		double acc[] = new double[]{getAcceleration(tar,b1)[0]+getAcceleration(tar,b2)[0],
				getAcceleration(tar,b1)[1]+getAcceleration(tar,b2)[1],
				getAcceleration(tar,b1)[2]+getAcceleration(tar,b2)[2]};
		
		double velocity[] = new double[]{ tar.velocity[0]+acc[0]*t,
				tar.velocity[1]+acc[1]*t,
				tar.velocity[2]+acc[2]*t};
		return velocity;
	}	
}
