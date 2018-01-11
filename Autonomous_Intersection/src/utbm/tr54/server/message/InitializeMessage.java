package utbm.tr54.server.message;

/**
 * This class represents the initialize information receive from the robot
 *
 * @author Michel
 *
 */
public class InitializeMessage {

	/** The id of the robot **/
	private int id;

	/** The address IP of the robot **/
	private String ip;

	/**
	 * InitializeMessage default constructor
	 */
	public InitializeMessage() {
		id = 0;
		ip = "";
	}

	/**
	 * InitializeMessage constructor initializing with parameter
	 *
	 * @param id
	 *            the id of the robot
	 * @param ip
	 *            the address ip of the robot
	 */
	public InitializeMessage(final int id, final String ip) {
		this.id = id;
		this.ip = ip;
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
	 * Gets the address ip of the robot
	 *
	 * @return the address ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the id to the robot
	 *
	 * @param id
	 *            the id of the robot
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Sets the address ip of the robot
	 *
	 * @param ip
	 *            the address ip
	 */
	public void setIp(final String ip) {
		this.ip = ip;
	}
}
