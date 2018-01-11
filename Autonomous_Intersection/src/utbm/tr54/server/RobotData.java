/**
 *
 */
package utbm.tr54.server;

/**
 * This class represents the data receive by the robot and it's used for the
 * server
 *
 * @author Michel
 *
 */
public class RobotData {

	/** The id of the robot **/
	private int id;

	/** The position of the robot **/
	private int position;

	/**
	 * RobotData default constructor
	 */
	public RobotData() {
		id = 0;
		position = 0;
	}

	/**
	 * RobotData constructor initializing the information receive from the robot
	 *
	 * @param id
	 *            the id of the robot
	 * @param pos
	 *            the position
	 */
	public RobotData(final int id, final int pos) {
		this.id = id;
		position = pos;
	}

	/**
	 * Gets the id of the robot
	 *
	 * @return the robot of the robot
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the position of the robot
	 *
	 * @return
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Sets the id of the robot
	 *
	 * @param i
	 *            the id
	 */
	public void setId(final int i) {
		id = i;
	}

	/**
	 * Sets the position of the robot
	 *
	 * @param p
	 *            the position
	 */
	public void setPosition(final int p) {
		position = p;
	}

}
