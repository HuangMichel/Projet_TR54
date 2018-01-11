/**
 *
 */
package utbm.tr54.robot;

/**
 * This class represents the color RGB
 *
 * @author Michel
 *
 */
public class ColorRGB {

	/** The red component **/
	private float R;

	/** The green component **/
	private float G;

	/** The blue component **/
	private float B;

	/** The white values **/
	private float[] white = new float[] { 1.f, 1.f, 1.f };

	/** The blue values **/
	private float[] blue = new float[] { 0.25f, 0.086f, 0.07f };

	/** The black values **/
	private float[] black = new float[] { 0.f, 0.f, 0.f };

	/** The orange values **/
	private float[] orange = new float[] { 0.37f, 0.08f, 0.04f };

	/** The color ID **/
	private int colorID;

	/**
	 * ColorRGB default constructor
	 */
	public ColorRGB() {
		R = 0.f;
		G = 0.f;
		B = 0.f;
	}

	/**
	 * ColorRGB constructor initializing RGB components
	 * 
	 * @param r
	 *            the red component
	 * @param g
	 *            the green component
	 * @param b
	 *            the blue component
	 */
	public ColorRGB(float r, float g, float b) {
		this.R = r;
		this.G = g;
		this.B = b;
	}

	/**
	 * Gets the color name
	 *
	 * @param color
	 *            object of ColorRGB
	 * @return the color name
	 */
	public String colorName() {
		String colorName = "";
		if (this.equalsOrange()) {
			return colorName = "ORANGE";
		} else if (this.equalsBlack()) {
			return colorName = "BLACK";
		} else if (this.equalsBlue()) {
			return colorName = "BLUE";
		} else if (this.equalsWhite()) {
			return colorName = "WHITE";
		}

		return colorName;
	}

	/**
	 * Checks if the 2 colors are equals
	 * 
	 * @param color
	 *            the object ColorRGB
	 * @return true if successful
	 */
	public boolean equals(ColorRGB color) {
		return (this.equals(color));
	}

	/**
	 * Checks if the color is black
	 * 
	 * @return true if successful
	 */
	public boolean equalsBlack() {
		return this.equals(this, black, 0.1f);
	}

	/**
	 * Checks if the color is blue
	 * 
	 * @return true if successful
	 */
	public boolean equalsBlue() {
		return equals(this, blue, 0.1f);
	}

	/**
	 * Checks if the color is orange
	 * 
	 * @return true if successful
	 */
	public boolean equalsOrange() {
		return equals(this, orange, 0.1f);
	}

	/**
	 * Checks if the color is white
	 * 
	 * @return true if successful
	 */
	public boolean equalsWhite() {
		return this.equals(this, white, 0.1f);
	}

	/**
	 * Gets the blue component value
	 * 
	 * @return the blue value
	 */
	public float getBlue() {
		return B;
	}

	/**
	 * Gets the green component value
	 * 
	 * @return the green value
	 */
	public float getGreen() {
		return G;
	}

	/**
	 * Gets the red component value
	 * 
	 * @return the red value
	 */
	public float getRed() {
		return R;
	}

	/**
	 * Sets the blue component value
	 * 
	 * @param b
	 *            the blue value
	 */
	public void setBlue(float b) {
		B = b;
	}

	/**
	 * Sets the green component value
	 * 
	 * @param g
	 *            the green value
	 */
	public void setGreen(float g) {
		G = g;
	}

	/**
	 * Sets the red component value
	 * 
	 * @param r
	 *            the red value
	 */
	public void setRed(float r) {
		R = r;
	}

	/**
	 * Compares two colors RGB
	 * 
	 * @param color1
	 *            the color RGB
	 * @param color2
	 *            the color RGB
	 * @param error
	 *            a coefficient of error
	 * @return true, if is equals
	 */
	public boolean equals(ColorRGB color1, float[] color2, float error) {
		boolean bool = true;
		bool = Math.abs(color1.getRed() - color2[0]) < error;
		bool = Math.abs(color1.getGreen() - color2[1]) < error;
		bool = Math.abs(color1.getBlue() - color2[2]) < error;
		return bool;
	}

	/**
	 * Sets the color id
	 * 
	 * @param id
	 *            the id
	 */
	public void setColorID(int id) {
		this.colorID = id;
	}

	/**
	 * Gets the color id
	 * 
	 * @param colorID
	 *            the color id
	 */
	public int getColorID() {
		return this.colorID;
	}

}
