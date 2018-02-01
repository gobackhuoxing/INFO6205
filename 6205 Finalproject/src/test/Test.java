 /*
 * CSYE 6205
 * @author Wenbo Sun
 * NUID:001994516
 */
package test;

import motion.BlueMotion;
import motion.GreenMotion;
import motion.Motion;
import motion.RedMotion;
import threeBody.Body;

//test all data
public class Test {
	public static double[] redposition; 	//meter(m)
	public static double[] redvelocity; 	//meter/second(m/s)
	public static double redmass;			//kiloton(kt)
	public static double[] greenposition;
	public static double[] greenvelocity;
	public static double greenmass;
	public static double[] blueposition;
	public static double[] bluevelocity;
	public static double bluemass;
	public static RedMotion rm = new RedMotion();
	public static GreenMotion gm = new GreenMotion();
	public static BlueMotion bm = new BlueMotion();
	
	//test data
	public void testAll(){
		redposition = new double[] {2,0,0};
		redvelocity = new double[] {0,0.1,0};
		redmass = 1;
		greenposition = new double[] {0,2,0};
		greenvelocity = new double[] {0,0,0.1};
		greenmass = 1;
		blueposition = new double[] {0,0,2};
		bluevelocity = new double[] {0.1,0,0};
		bluemass = 1;
		Motion.red = new Body(redposition,redvelocity,redmass);
		Motion.green = new Body(greenposition,greenvelocity,greenmass);
		Motion.blue = new Body(blueposition,bluevelocity,bluemass);
		System.out.println(Motion.red.toString());
		System.out.println(Motion.green.toString());
		System.out.println(Motion.blue.toString());
		
		for(int i =0;i<100;i++){
			System.out.println(i);
			rm.tempred = Motion.red;
			gm.tempgreen = Motion.green;
			bm.tempblue = Motion.blue;
			rm.initPsored(rm.tempred,gm.tempgreen,bm.tempblue);
			gm.initPsogreen(gm.tempgreen,rm.tempred,bm.tempblue);
			bm.initPsoblue(bm.tempblue,rm.tempred,gm.tempgreen);
			for(int j =0;j<Motion.g;j++){
				for(int k=0;k<Motion.n;k++){
					//red
					rm.psored(rm.tempred,gm.tempgreen,bm.tempblue,k);
					//green
					gm.psogreen(gm.tempgreen,rm.tempred,bm.tempblue,k);
					//blue
					bm.psoblue(bm.tempblue,rm.tempred,gm.tempgreen,k);
					
				//	show every particle
					//show red p
					System.out.println("red particle "+k+ ": "+rm.redx[k][0]+","+rm.redx[k][1]+","+rm.redx[k][2]);
					System.out.println("pbest: "+rm.redpbest[k][0]+","+rm.redpbest[k][0]+","+rm.redpbest[k][0]);
					System.out.println("gbest: "+rm.redgbest[0]+","+rm.redgbest[0]+","+rm.redgbest[0]);
					//show green p
					System.out.println("green particle "+k+ ": "+gm.greenx[k][0]+","+gm.greenx[k][1]+","+gm.greenx[k][2]);
					System.out.println("pbest: "+gm.greenpbest[k][0]+","+gm.greenpbest[k][0]+","+gm.greenpbest[k][0]);
					System.out.println("gbest: "+gm.greengbest[0]+","+gm.greengbest[0]+","+gm.greengbest[0]);
					//show blue p
					System.out.println("blue particle "+k+ ": "+bm.bluex[k][0]+","+bm.bluex[k][1]+","+bm.bluex[k][2]);
					System.out.println("pbest: "+bm.bluepbest[k][0]+","+bm.bluepbest[k][0]+","+bm.bluepbest[k][0]);
					System.out.println("gbest: "+bm.bluegbest[0]+","+bm.bluegbest[0]+","+bm.bluegbest[0]);
				}
			}
			//updata position
			Motion.red.setPosition(rm.redgbest);
			Motion.green.setPosition(gm.greengbest);
			Motion.blue.setPosition(bm.bluegbest);
			//update velocity
			Motion.red.setVelocity(Motion.getVelocity(rm.tempred,gm.tempgreen,bm.tempblue));
			Motion.green.setVelocity(Motion.getVelocity(gm.tempgreen,rm.tempred,bm.tempblue));
			Motion.blue.setVelocity(Motion.getVelocity(bm.tempblue,gm.tempgreen,rm.tempred));
			//show all bodys
			System.out.println(Motion.red.toString());
			System.out.println(Motion.green.toString());
			System.out.println(Motion.blue.toString());
		}
	}
	
	public static void main(String[] args) {
		Test r = new Test();
		r.testAll();
	}
}
