/**
 *
 */
package utbm.tr54;

import utbm.tr54.robot.AIBot;

/**
 * @author Michel
 *
 */
public class AIBotLauncher {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new Thread(new AIBot(1, 1)).start();
	}

}
