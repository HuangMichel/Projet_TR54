/**
 *
 */
package utbm.tr54.robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;

/**
 * This class contains all function available to use for a robot
 *
 * @author Michel
 *
 */
public class Robot {

	/** The left motor **/
	private final RegulatedMotor leftMotor;

	/** The right motor **/
	private final RegulatedMotor rightMotor;

	/** The left motor port **/
	private Port leftPort = MotorPort.B;

	/** The right motor port **/
	private Port rightPort = MotorPort.C;

	/**
	 * Robot default constructor
	 */
	public Robot() {
		leftMotor = new EV3LargeRegulatedMotor(leftPort);
		rightMotor = new EV3LargeRegulatedMotor(rightPort);
		rightMotor.synchronizeWith(new RegulatedMotor[] { leftMotor });
	}

	/**
	 * Robot constructor initializing the motors port
	 *
	 * @param _leftPort
	 *            the left port
	 * @param _rightPort
	 *            the right port
	 */
	public Robot(final Port _leftPort, final Port _rightPort) {
		leftPort = _leftPort;
		rightPort = _rightPort;
		leftMotor = new EV3LargeRegulatedMotor(leftPort);
		rightMotor = new EV3LargeRegulatedMotor(rightPort);
		rightMotor.synchronizeWith(new RegulatedMotor[] { leftMotor });
	}

	/**
	 * Makes the robot goes backward
	 */
	public void backward() {
		rightMotor.startSynchronization();
		rightMotor.backward();
		leftMotor.backward();
		rightMotor.endSynchronization();
	}

	/**
	 * Closes the motors
	 */
	public void close() {
		rightMotor.close();
		leftMotor.close();
	}

	/**
	 * Makes the robot goes forward
	 */
	public void forward() {
		rightMotor.startSynchronization();
		rightMotor.forward();
		leftMotor.forward();
		rightMotor.endSynchronization();
	}

	/**
	 * Gets the current speed from the left motor
	 *
	 * @return the current speed from the left motor
	 */
	public float getCurrentSpeedLeft() {
		return leftMotor.getSpeed();
	}

	/**
	 * Gets the current speed from the right motor
	 *
	 * @return the current speed from the right motor
	 */
	public float getCurrentSpeedRight() {
		return rightMotor.getSpeed();
	}

	/**
	 * Gets left motor tachometer count
	 *
	 * @return tachometer count
	 */
	public int getLeftTacho() {
		return leftMotor.getTachoCount();
	}

	/**
	 * Gets the max speed from the left motor
	 *
	 * @return the max speed from the left motor
	 */
	public float getMaxSpeedLeft() {
		return leftMotor.getMaxSpeed();
	}

	/**
	 * Gets the max speed from the right motor
	 *
	 * @return the max speed from the right motor
	 */
	public float getMaxSpeedRight() {
		return rightMotor.getMaxSpeed();
	}

	/**
	 * Gets right motor tachometer count
	 *
	 * @return tachometer count
	 */
	public int getRightTacho() {
		return rightMotor.getTachoCount();
	}

	/**
	 * Resets tachometer count
	 */
	public void resetTacho() {
		rightMotor.resetTachoCount();
		leftMotor.resetTachoCount();
	}

	/**
	 * Rotate the robot on the left
	 *
	 * @param angle
	 *            the angle of rotation in rad
	 */
	public void rotateLeft(final float angle) {
		rightMotor.rotate((int) (angle * 180 / Math.PI), true);
		leftMotor.rotate((int) (-angle * 180 / Math.PI));
	}

	/**
	 * Rotate the robot on the right
	 *
	 * @param angle
	 *            the angle of rotation in rad
	 */
	public void rotateRight(final float angle) {
		leftMotor.rotate((int) (angle * 180 / Math.PI), true);
		rightMotor.rotate((int) (-angle * 180 / Math.PI));
	}

	/**
	 * Sets the rotation angle of the left motor
	 *
	 * @param angle
	 *            the angle of rotation in rad
	 */
	public void setRotateLeftMotor(final float angle) {
		leftMotor.rotate((int) ((angle * 180) / Math.PI), true);
	}

	/**
	 * Sets the rotation angle of the right motor
	 *
	 * @param angle
	 *            the angle of rotation in rad
	 */
	public void setRotateRightMotor(final float angle) {
		rightMotor.rotate((int) ((angle * 180) / Math.PI), true);
	}

	/**
	 * Sets the speed of the robot
	 *
	 * @param percent
	 *            the percent of the max speed to set
	 */
	public void setSpeed(final float percent) {
		leftMotor.setSpeed((int) (getMaxSpeedLeft() * percent));
		rightMotor.setSpeed((int) (getMaxSpeedRight() * percent));
	}

	/**
	 * Sets the speed of the robot
	 * 
	 * @param percent1
	 *            the percent of the max speed to set for the motor left
	 * @param percent2
	 *            the percent of the max speed to set for the motor right
	 */
	public void setSpeed(float percent1, float percent2) {
		leftMotor.setSpeed((int) (getMaxSpeedLeft() * percent1));
		rightMotor.setSpeed((int) (getMaxSpeedRight() * percent2));
	}

	/**
	 * Stops the robot
	 */
	public void stop() {
		rightMotor.startSynchronization();
		rightMotor.stop(true);
		leftMotor.stop();
		rightMotor.endSynchronization();
	}

}
