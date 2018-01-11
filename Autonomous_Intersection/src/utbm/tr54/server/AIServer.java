/**
 *
 */
package utbm.tr54.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import lejos.hardware.lcd.LCD;
import utbm.tr54.network.CommunicationServer;
import utbm.tr54.server.message.Message;

/**
 * The class AIServer
 *
 * @author Michel
 *
 */
public class AIServer extends Server implements Runnable {

	/** Broadcast address **/
	private static final String BROADCAST_ADDRESS = "192.168.137.255";

	/** RECEPTION PORT **/
	private static final int RECEPTION_PORT = 7777;

	/** SENDING PORT **/
	private static final int SENDING_PORT = 8888;

	/** Initialize mode **/
	public final static int INITIALIZE = 0;

	/** Start mode **/
	public final static int RUN = 1;

	/** Number of robots **/
	private static int NB_ROBOTS = 1;

	/** The type no update **/
	public final int TYPE_NOUPDATE = 0;

	/** The type to update **/
	public final int TYPE_UPDATE = 1;

	/** The type to inform in (saw orange) **/
	public final int TYPE_NEW = 2;

	/** The type to inform out (finished intersection) **/
	public final int TYPE_OUT = 3;

	/** The communication WIFI **/
	private CommunicationServer wifi;

	/** The address IP **/
	private InetAddress ip;

	/** The message received **/
	private String[] msgReceived;

	/** The message to send **/
	private byte[] msgSend;

	/** The object Message **/
	private Message msg;

	/** The mode **/
	private int mode = 1;

	/** Count the number of robots **/
	private int count = 0;

	/**
	 * Default constructor
	 */
	public AIServer() {
		super();
		LCD.drawString("Initializing...", 0, 0);
		try {
			wifi = new CommunicationServer(AIServer.RECEPTION_PORT);
		} catch (final SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Default constructor initializing with parameters
	 * 
	 * @param mode
	 *            the mode
	 */
	public AIServer(int mode) {
		super();
		LCD.drawString("Initializing...", 0, 0);
		try {
			wifi = new CommunicationServer(AIServer.RECEPTION_PORT);
		} catch (final SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mode = mode;
	}

	/**
	 * Checks if the robot is in the list
	 *
	 * @param id
	 *            the id of the robot
	 * @return true if in the list
	 */
	public boolean inTheList(final int id) {
		String[] msg = getListRobots().split(", ");

		for (int i = 0; i < getSize(); i++) {
			if (msg[i].startsWith(Integer.toString(id))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		LCD.clear();
		LCD.drawString("Starting Server ...", 0, 0);

		if (mode == INITIALIZE) {

			LCD.drawString("Mode Initialize", 0, 1);

			/** Wait to receive all robot to initialize **/
			while (count != AIServer.NB_ROBOTS) {
				LCD.clear(2);
				LCD.drawString("Waiting " + AIServer.NB_ROBOTS + " robots", 0, 2);
				try {

					msgReceived = wifi.receiveInitialize();

				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (msgReceived[0].startsWith("init") && msgReceived[1].startsWith("0")) {
					addInitialize(msgReceived[2]);
					count++;
				}

				LCD.clear(3);
				LCD.drawString(count + " Robots in", 0, 3);
			}

			/** Sending to all robots to initialize **/
			if (count == AIServer.NB_ROBOTS) {
				for (int i = 0; i < getListInitialize().size(); i++) {
					try {
						ip = InetAddress.getByName(getListInitialize().get(i).getIp());
						String initialize = "ok, " + Integer.toString(getListInitialize().get(i).getId());
						msgSend = initialize.getBytes();
						wifi.sendInitialize(msgSend, ip, AIServer.SENDING_PORT);

					} catch (final UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (final IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			LCD.drawString("Finished to attribute if of each robots", 0, 4);

			mode = RUN;

		} else if (mode == RUN) {

			LCD.clear();

			LCD.drawString("Mode Run", 0, 0);

			try {
				ip = InetAddress.getByName(AIServer.BROADCAST_ADDRESS);
			} catch (final UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			while (true) {

				/** Receive information from robot **/
				try {
					msgReceived = wifi.receiveData();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				msg = new Message(msgReceived);

				LCD.clear(1);
				LCD.drawString(Server.getSize() + " Robots in the list", 0, 1);

				switch (msg.getType()) {

					case TYPE_UPDATE:

						if (inTheList(msg.getId()) == false) {
							LCD.clear(4);
							LCD.drawString("NEW", 0, 4);
							msg.setType(TYPE_NOUPDATE);
							final RobotData robot = new RobotData(msg.getId(), msg.getPosition());
							addRobot(robot);

						} else if (inTheList(msg.getId()) == true) {
							LCD.clear(4);
							LCD.drawString("UPDATES", 0, 4);
							msg.setType(TYPE_NOUPDATE);
							updateData(msg);
						}

						break;

					case TYPE_NEW:

						if (!inTheList(msg.getId()) == false) {
							LCD.clear(4);
							LCD.drawString("NEW", 0, 4);
							msg.setType(TYPE_NOUPDATE);
							final RobotData robot = new RobotData(msg.getId(), msg.getPosition());
							addRobot(robot);

						}

						break;

					case TYPE_OUT:

						if (inTheList(msg.getId()) == true) {
							LCD.clear(4);
							LCD.drawString("OUT", 0, 4);
							msg.setType(TYPE_NOUPDATE);
							removeRobot(msg.getId());

						}

						break;
				}

				/** Sending information to all robots **/
				LCD.clear(3);
				LCD.drawString("List of passage ", 0, 2);
				LCD.drawString(Server.getListRobots(), 0, 3);

				msgSend = Server.getListRobots().getBytes();

				try {
					wifi.sendDataBroadcast(msgSend, ip, AIServer.SENDING_PORT);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
