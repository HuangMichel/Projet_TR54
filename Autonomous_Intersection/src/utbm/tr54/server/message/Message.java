package utbm.tr54.server.message;

/**
 * This class represents the information received from the robot
 *
 * @author Michel
 *
 */
public class Message {

	/** The id of the robot **/
	private int id;

	/** The position of the robot **/
	private int position;

	/** The type of information **/
	private int type;

	/**
	 * Message default constructor
	 *
	 * @param data
	 *            the data receive by the robot, structure of the data receive "id,
	 *            position, type"
	 */
	public Message(final String[] data) {
		id = new Integer(data[0]);
		position = new Integer(data[1]);
		type = new Integer(data[2]);
	}

	/**
	 * Gets the id of the robot
	 *
	 * @return the id of the robot
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the position of the robot
	 *
	 * @return the position of the robot
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Gets the type of information
	 *
	 * @return the type of information
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the id of the robot
	 *
	 * @param id
	 *            the id of the robot
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Sets the position of the robot
	 *
	 * @param pos
	 *            the position of the robot
	 */
	public void setPosition(final int pos) {
		position = pos;
	}

	/**
	 * Sets type of information
	 *
	 * @param i
	 *            the type of information
	 */
	public void setType(final int i) {
		type = i;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", position=" + position + "]";
	}

}
