/**
 *
 */
package utbm.tr54.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * This class manages the communication between Robot to Server
 *
 * @author Michel
 *
 */
public class CommunicationRobot {

	/** Send socket **/
	protected DatagramSocket sendSocket;

	/** Packet sent **/
	protected DatagramPacket packetSent;

	/** Receive socket **/
	protected DatagramSocket receiveSocket;

	/** Packet received **/
	protected DatagramPacket packetReceived;

	/**
	 * CommunicationRobot constructor initializing the reception port
	 *
	 * @param port
	 *            the reception port
	 * @throws SocketException
	 */
	public CommunicationRobot(final int port) throws SocketException {
		sendSocket = new DatagramSocket();
		receiveSocket = new DatagramSocket(port);
	}

	/**
	 * Closes the sockets
	 */
	public void close() {
		closeSender();
		closeReceiver();
	}

	/**
	 * Closes the socket receiver
	 */
	public void closeReceiver() {
		receiveSocket.close();
	}

	/**
	 * Closes the socket sender
	 */
	public void closeSender() {
		sendSocket.close();
	}

	/**
	 * Gets the information received from the server
	 *
	 * @return the information received from the server
	 * @throws IOException
	 */
	public String[] receiveData() throws IOException {
		final byte[] msg = new byte[256];
		packetReceived = new DatagramPacket(msg, msg.length);
		receiveSocket.receive(packetReceived);
		return new String(packetReceived.getData()).trim().split(",");
	}

	/**
	 * Sends information to the server
	 *
	 * @param msg
	 *            the message to send
	 * @param ip
	 *            the address ip
	 * @param port
	 *            the sending port
	 * @throws IOException
	 */
	public void sendData(final byte[] msg, final InetAddress ip, final int port) throws IOException {
		packetSent = new DatagramPacket(msg, msg.length, ip, port);
		packetSent.setData(msg);
		sendSocket.send(packetSent);
	}

	/**
	 * Sends broadcast information to all robots This function is used for the
	 * server and not for the robot
	 *
	 * @param msg
	 *            the message to send
	 * @param ip
	 *            the address ip
	 * @param port
	 *            the sending port
	 * @throws IOException
	 */
	public void sendDataBroadcast(final byte[] msg, final InetAddress ip, final int port) throws IOException {
		packetSent = new DatagramPacket(msg, msg.length, ip, port);
		packetSent.setData(msg);
		sendSocket.setBroadcast(true);
		sendSocket.send(packetSent);
	}
}
