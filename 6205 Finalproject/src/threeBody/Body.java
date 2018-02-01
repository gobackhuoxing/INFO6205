 /*
 * CSYE 6205
 * @author Wenbo Sun
 * NUID:001994516
 */
package threeBody;
import java.util.Arrays;

//the entity for celestial body
public class Body {
	public double[] position;
	public double[] velocity;
	public double mass;
	
	public Body(double[] position, double[] velocity, double mass) {
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
	}

	public Body(double[] position, double[] velocity) {
		this.position = position;
		this.velocity = velocity;
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(double[] velocity) {
		this.velocity = velocity;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	@Override
	public String toString() {
		return "Body [position=" + Arrays.toString(position) + ", velocity=" + Arrays.toString(velocity);
	}
	
	
}
