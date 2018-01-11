/**
 *
 */
package utbm.tr54.robot;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import utbm.tr54.network.CommunicationRobot;

/**
 * The class AIBot
 *
 * @author Michel
 *
 */
public class AIBot extends Robot implements Runnable {

	/** Address IP of the server **/
	private static final String SERVER_ADDRESS = "192.168.137.212";

	/** RECEPTION PORT **/
	private static final int RECEPTION_PORT = 8888;

	/** SENDING PORT **/
	private static final int SENDING_PORT = 7777;

	/** Initialize mode **/
	public final static int INITIALIZE = 0;

	/** Start mode **/
	public final static int RUN = 1;

	/** The order to pass the intersection **/
	private static int order = 0;

	/** The object Sensors **/
	private final Sensors sensors;

	/** The communication WIFI **/
	private CommunicationRobot wifi;

	/** The address IP **/
	private InetAddress ip;

	/** The message to send **/
	private byte[] msgSend;

	/** The message **/
	private String msg;

	/** The message received **/
	private String[] msgReceived;

	/** The id of the robot **/
	private int id;

	/** The normal speed of the robot **/
	private final float normalSpeed = 0.4f;

	/** The position of the robot **/
	private int position;

	/** The boolean to inform if the robot saw the color orange **/
	private boolean sawOrange = false;

	/** The boolean to inform if the robot saw the color blue **/
	private boolean sawBlue = false;

	/** The boolean to inform if the robot saw the color black **/
	private boolean sawBlack = false;

	/** The boolean to inform if the robot saw the color black **/
	private boolean sawWhite = false;

	/** The mode **/
	private int mode = 1;

	/** The current color saw **/
	private ColorRGB currentColor = new ColorRGB();

	/** The constant period in ms **/
	private static final int PERIOD = 50;

	/** The name of the color saw **/
	private String currentColorName;

	/**
	 * IABot default constructor
	 */
	public AIBot() {
		super();
		sensors = new Sensors();
		id = 0;
		try {
			wifi = new CommunicationRobot(AIBot.RECEPTION_PORT);
			ip = InetAddress.getByName(AIBot.SERVER_ADDRESS);
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * IABot constructor initializing id of the robot
	 *
	 * @param id
	 *            the id of the robot
	 * @param mode
	 *            the mode
	 */
	public AIBot(final int id, final int mode) {
		super();
		sensors = new Sensors();
		this.id = id;
		this.mode = mode;
		try {
			wifi = new CommunicationRobot(AIBot.RECEPTION_PORT);
			ip = InetAddress.getByName(AIBot.SERVER_ADDRESS);
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * IABot constructor initializing robot components
	 *
	 * @param leftPort
	 *            the left motor port
	 * @param rightPort
	 *            the right motor port
	 * @param ultrasonicPort
	 *            the ultrasonic port
	 * @param colorPort
	 *            the color port
	 */
	public AIBot(final Port leftPort, final Port rightPort, final Port ultrasonicPort, final Port colorPort,
			final Port touchPort) {
		super(leftPort, rightPort);
		sensors = new Sensors(ultrasonicPort, colorPort, touchPort);
		id = 0;
		try {
			wifi = new CommunicationRobot(AIBot.RECEPTION_PORT);
			ip = InetAddress.getByName(AIBot.SERVER_ADDRESS);
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * Gets the order of passage to pass the intersection
	 *
	 * @param list
	 *            the list of passage
	 * @return true if is in the list
	 */
	public int getOrder(final String[] list) {
		if (inTheList(list) == true) {
			for (int i = 0; i < list.length; ++i) {
				if (new Integer(list[i]).equals(getId())) {
					return (i + 1);
				}
			}
		}
		return 0;
	}

	/**
	 * Gets the position of the robot
	 *
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Checks if the robot is in the list
	 *
	 * @param id
	 *            the id of the robot
	 * @return true if in the list
	 */
	public boolean inTheList(final String[] list) {
		for (int i = 0; i < list.length; i++) {
			if (new Integer(i).equals(getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if there is an obstacle
	 *
	 * @return true if there are one
	 */
	public boolean Obstacle() {
		if (sensors.getDistance() < 0.15) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void run() {

		while (!sensors.isPush()) {

			if (mode == INITIALIZE) {

				final String initialize = "init, " + Integer.toString(getId());

				msgSend = initialize.getBytes();

				LCD.drawString("Sending " + initialize, 0, 1);

				try {
					wifi.sendData(msgSend, ip, AIBot.SENDING_PORT);
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					wifi.close();
					e.printStackTrace();
				}

				msgReceived = null;
				LCD.drawString("Waiting to receive", 0, 2);

				try {
					msgReceived = wifi.receiveData();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					wifi.close();
					e.printStackTrace();
				}

				if (msgReceived[0].startsWith("ok")) {
					setId(new Integer(msgReceived[1]));
					LCD.drawInt(Integer.parseInt(msgReceived[1]), 0, 5);
				}

				LCD.drawString("Rcving " + msgReceived, 0, 3);

				mode = RUN;

			} else if (mode == RUN) {

				LCD.clear();

				LCD.drawString("AI ROBOT " + Integer.toString(getId()), 0, 0);

				while (!sensors.isPush()) {

					Button.LEDPattern(0);

					if (Obstacle() == false) {

						updatePosition();

						updateColor();

						this.forward();

						if (sawOrange) {

							LCD.clear(3);
							LCD.drawString("Enter access zone", 0, 3);

							resetTacho();

							setPosition(getLeftTacho());

							msg = Integer.toString(getId()) + "," + Integer.toString(getPosition()) + ",2";

							msgSend = msg.getBytes();

							try {

								wifi.sendData(msgSend, ip, AIBot.SENDING_PORT);

								msgReceived = wifi.receiveData();

							} catch (final IOException e) {
								// TODO Auto-generated catch block
								wifi.close();
								e.printStackTrace();
							}

							while (inTheList(msgReceived) == false && !sensors.isPush()) {
								stop();
								setLED(0);
								try {

									msgReceived = wifi.receiveData();

								} catch (final IOException e) {
									// TODO Auto-generated catch block
									wifi.close();
									e.printStackTrace();
								}

							}

							AIBot.order = getOrder(msgReceived);

							setLED(AIBot.order);

							setSpeed(normalSpeed);
							this.forward();

							updateColor();

						}

						if (AIBot.order != 0 && !sensors.isPush()) {

							if (AIBot.order == 1 && !sensors.isPush()) {

								while (this.position < 19 && !sensors.isPush()) {

									if (Obstacle() == true) {

										LCD.clear(6);
										stop();
										LCD.drawString("Stop obstacle", 0, 6);

									} else {

										updatePosition();

										updateColor();

										LCD.clear(4);
										LCD.drawString("Sending update", 0, 4);

										msg = Integer.toString(this.getId()) + "," + Integer.toString(getPosition())
												+ ",1";

										msgSend = msg.getBytes();

										try {
											wifi.sendData(msgSend, ip, AIBot.SENDING_PORT);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										updateSpeed(1);

									}

								}

								if (this.position > 19 && !sensors.isPush()) {

									LCD.clear(3);
									LCD.drawString("Sending out", 0, 3);
									msg = Integer.toString(this.getId()) + "," + Integer.toString(getPosition()) + ",3";
									msgSend = msg.getBytes();

									try {
										wifi.sendData(msgSend, ip, AIBot.SENDING_PORT);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									Button.LEDPattern(0);
									order = 0;
								}

								LCD.clear(3);

							} else {

								while (AIBot.order > 1 && !sensors.isPush()) {

									stop();

									try {
										msgReceived = wifi.receiveData();
									} catch (final IOException e) {
										// TODO Auto-generated catch block
										wifi.close();
										e.printStackTrace();
									}

									AIBot.order = getOrder(msgReceived);

									setLED(AIBot.order);

								}
							}

						}

						updateSpeed(0);

						try {
							Thread.sleep(PERIOD);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {

						LCD.clear(6);
						stop();
						LCD.drawString("Stop obstacle", 0, 6);

					}
				}
			}
		}

		msg = Integer.toString(getId()) + "," + Integer.toString(getPosition()) + ",3";

		msgSend = msg.getBytes();

		try {
			wifi.sendData(msgSend, ip, AIBot.SENDING_PORT);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			wifi.close();
			e.printStackTrace();
		}

		wifi.close();
		sensors.close();
		close();
	}

	/**
	 * Sets the booleans color
	 *
	 * @param colorName
	 *            the color name
	 */
	public void setBooleanColor(final String colorName) {
		switch (colorName) {
			case "BLACK":
				sawBlack = true;
				sawBlue = false;
				sawOrange = false;
				sawWhite = false;
				break;
			case "WHITE":
				sawWhite = true;
				sawBlack = false;
				sawBlue = false;
				sawOrange = false;
				break;
			case "RED":
				sawOrange = true;
				sawWhite = false;
				sawBlack = false;
				sawBlue = false;
				break;
			case "ORANGE":
				sawOrange = true;
				sawWhite = false;
				sawBlack = false;
				sawBlue = false;
				break;
			case "BLUE":
				sawBlue = true;
				sawOrange = false;
				sawWhite = false;
				sawBlack = false;
				break;
			default:
				sawBlue = false;
				sawOrange = false;
				sawWhite = false;
				sawBlack = false;
				break;
		}
	}

	/**
	 * Sets the id to the robot
	 *
	 * @param id
	 *            the id
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Sets LED in function of the order of passage
	 *
	 * @param order
	 *            the order of passage
	 */
	public void setLED(final int order) {
		switch (order) {
			case 0:
				Button.LEDPattern(2);
				LCD.clear(5);
				LCD.drawString("Waiting to enter in the list", 0, 5);
				break;
			case 1:
				Button.LEDPattern(1);
				LCD.clear(5);
				LCD.drawString("First to pass", 0, 5);
				break;
			case 2:
				Button.LEDPattern(3);
				LCD.clear(5);
				LCD.drawString("Second to pass", 0, 5);
				break;
			default:
				Button.LEDPattern(2);
				LCD.clear(5);
				LCD.drawString("Third or more to pass", 0, 5);
				break;
		}
	}

	/**
	 * Sets the position of the robot
	 *
	 * @param pos
	 *            the position
	 */
	public void setPosition(final int pos) {
		position = pos;
	}

	/**
	 * Updates the position
	 */
	public void updatePosition() {
		this.position = this.getRightTacho() / 100;
		LCD.clear(1);
		LCD.drawString("Position " + getPosition(), 0, 1);
	}

	/**
	 * Updates the speed
	 * 
	 * @param j
	 *            the id if saw orange (0: no, 1: saw)
	 */
	public void updateSpeed(int j) {

		switch (j) {

			case 0:

				if (sawBlue) {

					setSpeed(normalSpeed);

					this.forward();

				}

				if (sawWhite) {

					setSpeed(0.2f, 0.1f);

				}

				if (sawBlack) {

					setSpeed(0.1f, 0.2f);

				}

				break;

			case 1:

				if (currentColorName.startsWith("BLUE")) {

					setSpeed(normalSpeed);
					this.forward();

				} else if (currentColorName.startsWith("BLACK")) {

					setSpeed(0.1f, 0.2f);

				} else if (currentColorName.startsWith("WHITE")) {

					setSpeed(0.1f, 0.2f);

				}

				break;
		}
	}

	/**
	 * Updates the color saw by the sensor
	 */
	public void updateColor() {

		currentColor.setColorID(sensors.getColorInt());
		currentColorName = sensors.getColorName(currentColor.getColorID());

		LCD.clear(2);
		LCD.drawString("Color " + currentColorName, 0, 2);

		setBooleanColor(currentColorName);
	}
}
