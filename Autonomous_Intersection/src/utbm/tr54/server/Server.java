/**
 *
 */
package utbm.tr54.server;

import java.util.ArrayList;

import utbm.tr54.server.message.InitializeMessage;
import utbm.tr54.server.message.Message;

/**
 * This class contains all function available to use for a server
 *
 * @author Michel
 *
 */
public class Server {

	/** List of robots to pass the intersection **/
	private static ArrayList<RobotData> listRobots;

	/** List of robot to be initialize **/
	private static ArrayList<InitializeMessage> listInitialize;

	/**
	 * Gets the list of robot to initialize
	 *
	 * @return the list of robot to initialize
	 */
	public static ArrayList<InitializeMessage> getListInitialize() {
		return Server.listInitialize;
	}

	/**
	 * Gets the list of robots, the list of passage
	 *
	 * @return the list of robots
	 */
	public static String getListRobots() {
		String msg = "";
		for (int i = 0; i < listRobots.size(); i++) {
			msg += Integer.toString(listRobots.get(i).getId()) + ", ";
		}
		return msg;
	}

	/**
	 * Gets the the number of robot in the list of passage
	 *
	 * @return the number of robot in the list of passage
	 */
	public static int getSize() {
		return Server.listRobots.size();
	}

	/**
	 * Checks is the list of passage
	 *
	 * @return true is empty
	 */
	public static boolean isEmpty() {
		return Server.listRobots.isEmpty();
	}

	/** Count the number of robots to be initialize **/
	private int countInitialize = 0;

	/**
	 * Server default constructor
	 */
	public Server() {
		Server.listRobots = new ArrayList<RobotData>();
		Server.listInitialize = new ArrayList<InitializeMessage>();
	}

	/**
	 * Add a new robot in the list to initialize
	 *
	 * @param ip
	 *            the address ip of the robot
	 */
	public void addInitialize(final String ip) {
		count();
		final InitializeMessage init = new InitializeMessage(countInitialize, ip);
		Server.listInitialize.add(init);
	}

	/**
	 * Add a new robot in the list
	 *
	 * @param robot
	 *            object of RobotData
	 */
	public void addRobot(final RobotData robot) {
		Server.listRobots.add(robot);
	}

	/**
	 * Count the number of robot to initialize
	 */
	public void count() {
		countInitialize++;
	}

	/**
	 * Gets the number of robots to initialize
	 *
	 * @return
	 */
	public int getCount() {
		return countInitialize;
	}

	/**
	 * Remove the robot by id of the list
	 *
	 * @param id
	 *            the id of the robot
	 */
	public void removeRobot(final int id) {
		for (int i = 0; i < listRobots.size(); i++) {
			if (listRobots.get(i).getId() == id) {
				removeRobot(listRobots.get(i));
			}
		}
	}

	/**
	 * Remove the robot of the list
	 *
	 * @param robot
	 *            object of RobotData
	 */
	public void removeRobot(final RobotData robot) {
		Server.listRobots.remove(robot);
	}

	/**
	 * Update the data about the robot in the list
	 *
	 * @param msg
	 */
	public void updateData(final Message msg) {
		if (Server.isEmpty()) {
			final RobotData robot = new RobotData(msg.getId(), msg.getPosition());
			addRobot(robot);
		} else {
			for (int i = 0; i < listRobots.size(); i++) {
				if (listRobots.get(i).getId() == msg.getId()) {
					listRobots.get(i).setPosition(msg.getPosition());
				}
			}
		}
	}
}
