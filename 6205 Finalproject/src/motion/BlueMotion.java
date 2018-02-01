 /*
 * CSYE 6205
 * @author Wenbo Sun
 * NUID:001994516
 */
package motion;

import animation.DrawAnimation;
import threeBody.Body;
//pso for blue
public class BlueMotion extends Motion{
	//blue pso
	public double[][] bluepbest = new double[n][];		//best for one priticle
    public double[] bluegbest;							//best for all priticles
    public double[][] bluex = new double[n][3];
	double[] blueapproximatePosition;
	double[] bluexmax;
	double[] bluexmin;
	
	//init all particle for red
	public void initPsoblue(Body tar,Body b1,Body b2){
		blueapproximatePosition= getPosition(tar,b1,b2);
		//init bluegbest with a very bad position
		bluegbest = new double[]{-blueapproximatePosition[0],-blueapproximatePosition[2],-blueapproximatePosition[2]};
		//range of bluex
		bluexmax = new double[]{tar.position[0]+scope,tar.position[1]+scope,tar.position[2]+scope};
		bluexmin = new double[]{tar.position[0]-scope,tar.position[1]-scope,tar.position[2]-scope};
		//init the bluex y, bluepbest and bluegbest
		for(int i = 0;i<n;i++){
			bluex[i] = new double[] {scope*2*Math.random()+ bluexmin[0],
								 	 scope*2*Math.random()+ bluexmin[1],
								 	 scope*2*Math.random()+ bluexmin[2]};
				
			v[i] = new double[] {scope*2*0.01*Math.random()+vmin[0],
								 scope*2*0.01*Math.random()+vmin[1],
								 scope*2*0.01*Math.random()+vmin[2]};
			bluepbest[i]=bluex[i];
			if(getDistance(bluegbest,blueapproximatePosition)>getDistance(bluepbest[i],blueapproximatePosition)) bluegbest = bluepbest[i];
		}
	}
	
	//main pso for blue
	public void psoblue(Body tar,Body b1,Body b2,int i){
		//get v
		v[i] = new double[] {w*v[i][0]+c1*Math.random()*(bluepbest[i][0]-bluex[i][0])+c2*Math.random()*(bluegbest[0]-bluex[i][0]),
							 w*v[i][1]+c1*Math.random()*(bluepbest[i][1]-bluex[i][1])+c2*Math.random()*(bluegbest[1]-bluex[i][1]),
							 w*v[i][2]+c1*Math.random()*(bluepbest[i][2]-bluex[i][2])+c2*Math.random()*(bluegbest[2]-bluex[i][2])};
		//keep the v in range
		if(v[i][0]>vmax[0]) v[i][0]=vmax[0];
		else if(v[i][0]<vmin[0]) v[i][0]=vmin[0];
		if(v[i][1]>vmax[1]) v[i][1]=vmax[1];
		else if(v[i][1]<vmin[1]) v[i][1]=vmin[1];
		if(v[i][2]>vmax[2]) v[i][2]=vmax[2];
		else if(v[i][2]<vmin[2]) v[i][2]=vmin[2];
		//use v to get the new position
		bluex[i][0]+=v[i][0];
		bluex[i][1]+=v[i][1];
		bluex[i][2]+=v[i][2];
		//keep the bluex in range
		if(bluex[i][0]>bluexmax[0]) v[i][0]=bluexmax[0];
		else if(bluex[i][0]<bluexmin[0]) v[i][0]=bluexmin[0];
		if(bluex[i][1]>bluexmax[1]) v[i][1]=bluexmax[1];
		else if(bluex[i][1]<bluexmin[1]) v[i][1]=bluexmin[1];
		if(bluex[i][2]>bluexmax[2]) v[i][2]=bluexmax[2];
		else if(bluex[i][2]<bluexmin[2]) v[i][2]=bluexmin[2];
		//update bluepbest and bluegbest
		if(getDistance(bluepbest[i],blueapproximatePosition)>getDistance(bluex[i],blueapproximatePosition)) bluepbest[i]=bluex[i];
		if(getDistance(bluegbest,blueapproximatePosition)>getDistance(bluepbest[i],blueapproximatePosition)) bluegbest = bluepbest[i];
	}

	@Override
	public void run() {
		synchronized(DrawAnimation.rm){
			psoblue(tempblue,tempred,tempgreen,count);
		}
	}		
}
