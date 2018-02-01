 /*
 * CSYE 6205
 * @author Wenbo Sun
 * NUID:001994516
 */
package motion;

import animation.DrawAnimation;
import threeBody.Body;
//pso for green
public class GreenMotion extends Motion{
	//green pso
	public double[][] greenpbest = new double[n][];		//best for one priticle
    public double[] greengbest;							//best for all priticles
    public double[][] greenx = new double[n][3];
	double[] greenapproximatePosition;
	double[] greenxmax;
	double[] greenxmin;
	
	//init all particle for red
	public void initPsogreen(Body tar,Body b1,Body b2){
		greenapproximatePosition= getPosition(tar,b1,b2);
		//init greengbest with a very bad position
		greengbest = new double[]{-greenapproximatePosition[0],-greenapproximatePosition[2],-greenapproximatePosition[2]};
		//range of greenx
		greenxmax = new double[]{tar.position[0]+scope,tar.position[1]+scope,tar.position[2]+scope};
		greenxmin = new double[]{tar.position[0]-scope,tar.position[1]-scope,tar.position[2]-scope};
		//init the greenx y, greenpbest and greengbest
		for(int i = 0;i<n;i++){
			greenx[i] = new double[] {scope*2*Math.random()+ greenxmin[0],
									  scope*2*Math.random()+ greenxmin[1],
									  scope*2*Math.random()+ greenxmin[2]};
				
			v[i] = new double[] {scope*2*0.01*Math.random()+vmin[0],
								 scope*2*0.01*Math.random()+vmin[1],
								 scope*2*0.01*Math.random()+vmin[2]};
			greenpbest[i]=greenx[i];
			if(getDistance(greengbest,greenapproximatePosition)>getDistance(greenpbest[i],greenapproximatePosition)) greengbest = greenpbest[i];
		}
	}
	
	//main pso for green
	public void psogreen(Body tar,Body b1,Body b2,int i){
		//get v
		v[i] = new double[] {w*v[i][0]+c1*Math.random()*(greenpbest[i][0]-greenx[i][0])+c2*Math.random()*(greengbest[0]-greenx[i][0]),
							 w*v[i][1]+c1*Math.random()*(greenpbest[i][1]-greenx[i][1])+c2*Math.random()*(greengbest[1]-greenx[i][1]),
							 w*v[i][2]+c1*Math.random()*(greenpbest[i][2]-greenx[i][2])+c2*Math.random()*(greengbest[2]-greenx[i][2])};
		//keep the v in range
		if(v[i][0]>vmax[0]) v[i][0]=vmax[0];
		else if(v[i][0]<vmin[0]) v[i][0]=vmin[0];
		if(v[i][1]>vmax[1]) v[i][1]=vmax[1];
		else if(v[i][1]<vmin[1]) v[i][1]=vmin[1];
		if(v[i][2]>vmax[2]) v[i][2]=vmax[2];
		else if(v[i][2]<vmin[2]) v[i][2]=vmin[2];
		//use v to get the new position
		greenx[i][0]+=v[i][0];
		greenx[i][1]+=v[i][1];
		greenx[i][2]+=v[i][2];
		//keep the greenx in range
		if(greenx[i][0]>greenxmax[0]) v[i][0]=greenxmax[0];
		else if(greenx[i][0]<greenxmin[0]) v[i][0]=greenxmin[0];
		if(greenx[i][1]>greenxmax[1]) v[i][1]=greenxmax[1];
		else if(greenx[i][1]<greenxmin[1]) v[i][1]=greenxmin[1];
		if(greenx[i][2]>greenxmax[2]) v[i][2]=greenxmax[2];
		else if(greenx[i][2]<greenxmin[2]) v[i][2]=greenxmin[2];
		//update greenpbest and greengbest
		if(getDistance(greenpbest[i],greenapproximatePosition)>getDistance(greenx[i],greenapproximatePosition)) greenpbest[i]=greenx[i];
		if(getDistance(greengbest,greenapproximatePosition)>getDistance(greenpbest[i],greenapproximatePosition)) greengbest = greenpbest[i];		
	}

	@Override
	public void run() {
		synchronized(DrawAnimation.rm){
			psogreen(tempgreen,tempred,tempblue,count);
		}
	}	
}
