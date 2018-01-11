/**
 *
 */
package utbm.tr54;

import utbm.tr54.server.AIServer;

/**
 * @author Michel
 *
 */
public class AIServerLauncher {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new Thread(new AIServer(1)).start();
	}

}
