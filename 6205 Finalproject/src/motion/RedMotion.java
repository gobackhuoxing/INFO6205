 /*
 * CSYE 6205
 * @author Wenbo Sun
 * NUID:001994516
 */
package motion;

import animation.DrawAnimation;
import threeBody.Body;
//pso for red
public class RedMotion extends Motion{
	//red pso
	public double[][] redpbest = new double[n][];		//best for one priticle
    public double[] redgbest;							//best for all priticles
    public double[][] redx = new double[n][3];
	double[] redapproximatePosition;
	double[] redxmax;
	double[] redxmin;

	//init all particle for red
	public void initPsored(Body tar,Body b1,Body b2){
		redapproximatePosition= getPosition(tar,b1,b2);
		//init redgbest with a very bad position
		redgbest = new double[]{-redapproximatePosition[0],-redapproximatePosition[2],-redapproximatePosition[2]};
		//range of redx
		redxmax = new double[]{tar.position[0]+scope,tar.position[1]+scope,tar.position[2]+scope};
		redxmin = new double[]{tar.position[0]-scope,tar.position[1]-scope,tar.position[2]-scope};
		//init the redx y, redpbest and redgbest
		for(int i = 0;i<n;i++){
			redx[i] = new double[] {scope*2*Math.random()+ redxmin[0],
								 	scope*2*Math.random()+ redxmin[1],
								 	scope*2*Math.random()+ redxmin[2]};
				
			v[i] = new double[] {scope*2*0.01*Math.random()+vmin[0],
								 scope*2*0.01*Math.random()+vmin[1],
								 scope*2*0.01*Math.random()+vmin[2]};
			redpbest[i]=redx[i];
			if(getDistance(redgbest,redapproximatePosition)>getDistance(redpbest[i],redapproximatePosition)) redgbest = redpbest[i];
		}
	}
	
	//main pso for red
	public void psored(Body tar,Body b1,Body b2,int i){
		//get v
		v[i] = new double[] {w*v[i][0]+c1*Math.random()*(redpbest[i][0]-redx[i][0])+c2*Math.random()*(redgbest[0]-redx[i][0]),
							 w*v[i][1]+c1*Math.random()*(redpbest[i][1]-redx[i][1])+c2*Math.random()*(redgbest[1]-redx[i][1]),
							 w*v[i][2]+c1*Math.random()*(redpbest[i][2]-redx[i][2])+c2*Math.random()*(redgbest[2]-redx[i][2])};
		//keep the v in range
		if(v[i][0]>vmax[0]) v[i][0]=vmax[0];
		else if(v[i][0]<vmin[0]) v[i][0]=vmin[0];
		if(v[i][1]>vmax[1]) v[i][1]=vmax[1];
		else if(v[i][1]<vmin[1]) v[i][1]=vmin[1];
		if(v[i][2]>vmax[2]) v[i][2]=vmax[2];
		else if(v[i][2]<vmin[2]) v[i][2]=vmin[2];
		//use v to get the new position
		redx[i][0]+=v[i][0];
		redx[i][1]+=v[i][1];
		redx[i][2]+=v[i][2];
		//keep the redx in range
		if(redx[i][0]>redxmax[0]) v[i][0]=redxmax[0];
		else if(redx[i][0]<redxmin[0]) v[i][0]=redxmin[0];
		if(redx[i][1]>redxmax[1]) v[i][1]=redxmax[1];
		else if(redx[i][1]<redxmin[1]) v[i][1]=redxmin[1];
		if(redx[i][2]>redxmax[2]) v[i][2]=redxmax[2];
		else if(redx[i][2]<redxmin[2]) v[i][2]=redxmin[2];
		//update redpbest and redgbest
		if(getDistance(redpbest[i],redapproximatePosition)>getDistance(redx[i],redapproximatePosition)) redpbest[i]=redx[i];
		if(getDistance(redgbest,redapproximatePosition)>getDistance(redpbest[i],redapproximatePosition)) redgbest = redpbest[i];
	}

	@Override
	public void run() {
		synchronized(DrawAnimation.rm){
			psored(tempred,tempgreen,tempblue,count);
		}
	}	
}
