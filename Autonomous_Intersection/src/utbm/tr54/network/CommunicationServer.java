/**
 *
 */
package utbm.tr54.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * This class manages the communication between Robot to Server
 *
 * @author Michel
 *
 */
public class CommunicationServer extends CommunicationRobot {

	/**
	 * CommunicationServer constructor initializing the reception port
	 *
	 * @param port
	 *            the reception port
	 * @throws SocketException
	 */
	public CommunicationServer(final int port) throws SocketException {
		super(port);
	}

	/**
	 * Gets the initialize information from the robot
	 *
	 * @return initialize information from the robot
	 * @throws IOException
	 */
	public String[] receiveInitialize() throws IOException {
		final byte[] msg = new byte[256];
		packetReceived = new DatagramPacket(msg, msg.length);
		receiveSocket.receive(packetReceived);
		final String[] ip = packetReceived.getAddress().toString().split("/");
		final String msgReceived = new String(packetReceived.getData(), 0, packetReceived.getLength()) + "," + ip[1];
		return msgReceived.split(",");
	}

	/**
	 * Sends to one robot by ip to set the id to the robot
	 *
	 * @param msg
	 *            the message to send
	 * @param ip
	 *            the address ip of the robot
	 * @param port
	 *            the sending port
	 * @throws IOException
	 */
	public void sendInitialize(final byte[] msg, final InetAddress ip, final int port) throws IOException {
		packetSent = new DatagramPacket(msg, msg.length, ip, port);
		packetSent.setData(msg);
		sendSocket.send(packetSent);
	}

}
