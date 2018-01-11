package utbm.tr54.robot;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

/**
 * This class constains all function available about sensors (ultrasonic sensor,
 * color sensor and touch sensor)
 *
 * @author Michel
 *
 */
public class Sensors {

	/** Ultrasonic sensor **/
	private final EV3UltrasonicSensor ultrasonicSensor;

	/** The distance provider **/
	private SampleProvider distanceProvider;

	/** The boolean of ultrasonic sensor **/
	private boolean ultrasonicActivated = false;

	/** Ultrasonic port **/
	private Port ultrasonicPort = SensorPort.S2;

	/** Color sensor **/
	private EV3ColorSensor colorSensor;

	/** The color provider **/
	private SampleProvider colorProvider;

	/** The boolean of color sensor **/
	private boolean colorActivated = false;

	/** Color sensor port **/
	private Port colorPort = SensorPort.S3;

	/** Touch sensor **/
	private final EV3TouchSensor touchSensor;

	/** The touch provider **/
	private SampleProvider touchProvider;

	/** The boolean of touch sensor **/
	private boolean touchActivated = false;

	/** Touch port **/
	private Port touchPort = SensorPort.S1;

	/**
	 * Sensors default constructor
	 */
	public Sensors() {
		ultrasonicSensor = new EV3UltrasonicSensor(ultrasonicPort);
		ultrasonicSensor.setCurrentMode("Distance");
		distanceProvider = ultrasonicSensor.getDistanceMode();
		ultrasonicSensor.enable();
		ultrasonicActivated = ultrasonicSensor.isEnabled();

		colorSensor = new EV3ColorSensor(colorPort);
		colorSensor.setCurrentMode("RGB");
		colorProvider = colorSensor.getRGBMode();
		colorActivated = true;

		touchSensor = new EV3TouchSensor(touchPort);
		touchSensor.setCurrentMode("Touch");
		touchProvider = touchSensor.getTouchMode();
		touchActivated = true;
	}

	/**
	 * Sensors constructor initializing the sensors port
	 *
	 * @param _ultrasonicPort
	 *            the ultrasonic port
	 * @param _colorPort
	 *            the color port
	 * @param _touchPort
	 *            the touch port
	 */
	public Sensors(final Port _ultrasonicPort, final Port _colorPort, final Port _touchPort) {
		ultrasonicPort = _ultrasonicPort;
		ultrasonicSensor = new EV3UltrasonicSensor(ultrasonicPort);
		ultrasonicSensor.setCurrentMode("Distance");
		distanceProvider = ultrasonicSensor.getDistanceMode();
		ultrasonicSensor.enable();
		ultrasonicActivated = ultrasonicSensor.isEnabled();

		colorPort = _colorPort;
		colorSensor = new EV3ColorSensor(colorPort);
		colorSensor.setCurrentMode("RGB");
		colorProvider = colorSensor.getRGBMode();
		colorActivated = true;

		touchPort = _touchPort;
		touchSensor = new EV3TouchSensor(touchPort);
		touchSensor.setCurrentMode("Touch");
		touchProvider = touchSensor.getTouchMode();
		touchActivated = true;
	}

	/**
	 * Sets the color sensor in RGB mode
	 */
	public void setColorSensorRGB() {
		colorSensor.setCurrentMode("RGB");
		colorProvider = colorSensor.getRGBMode();
	}

	/**
	 * Sets the color sensor in ID mode
	 */
	public void setColorSensorIDmode() {
		colorSensor.setCurrentMode("ColorID");
		colorProvider = colorSensor.getColorIDMode();
	}

	/**
	 * Closes the sensors
	 */
	public void close() {
		colorSensor.close();
		ultrasonicSensor.close();
		touchSensor.close();
	}

	/**
	 * Closes the color sensor
	 */
	public void closeColor() {
		colorSensor.close();
	}

	/**
	 * Closes the touch sensor
	 */
	public void closeTouch() {
		touchSensor.close();
	}

	/**
	 * Closes the ultrasonic sensor
	 */
	public void closeUltrasonic() {
		ultrasonicSensor.close();
	}

	/**
	 * Gets the color name
	 *
	 * @param color
	 *            RGB colors
	 * @return the color name
	 */
	public String colorName(final float[] color) {
		String colorName = "";
		if (color[0] <= 0f && color[1] <= 0f && color[2] <= 0f) {
			colorName = "BLACK";
		} else if (color[0] >= 1f && color[1] < 0.627f && color[2] < 0.478f) {
			colorName = "ORANGE";
		} else if (color[0] <= 0.878f && color[1] >= 0f && color[2] >= 0.439f) {
			colorName = "BLUE";
		} else if (color[0] >= 1.000f && color[1] >= 1.000f && color[2] >= 1.000f) {
			colorName = "WHITE";
		}

		return colorName;
	}

	/**
	 * Disable ultrasonic sensor
	 */
	public void disableUltrasonic() {
		ultrasonicSensor.disable();
		ultrasonicActivated = false;
	}

	/**
	 * Gets the average of RGB color
	 * 
	 * @param color
	 *            the RGB color
	 * @return average
	 */
	public float getAverage(final float[] color) {
		if (color.length == 3) {
			float average = 0;
			for (final float c : color) {
				average += c;
			}
			return (average / color.length);
		} else {
			return 0;
		}

	}

	/**
	 * Gets the average of RGB color
	 * 
	 * @param color
	 *            object of ColorRGB
	 * @return the average
	 */
	public float getAverageRGB(final ColorRGB color) {
		return ((color.getRed() + color.getGreen() + color.getBlue()) / 3);
	}

	/**
	 * Gets the RGB colors from the colorProvider
	 *
	 * @return RGB colors
	 * @return null if the color sensor is not activated or the color provider is
	 *         not in RGB mode
	 */
	public float[] getColor() {
		if (isColorActivated() == true && getColorProvider().sampleSize() == 3) {
			final float[] sample = new float[getColorProvider().sampleSize()];
			getColorProvider().fetchSample(sample, 0);
			return sample;
		} else {
			return null;
		}
	}

	/**
	 * Gets the color provider
	 *
	 * @return the color provider
	 */
	public SampleProvider getColorProvider() {
		return colorProvider;
	}

	/**
	 * Gets the RGB colors from the colorProvider
	 * 
	 * @return ColorRGB
	 */
	public ColorRGB getColorRGB() {
		if (isColorActivated() == true && getColorProvider().sampleSize() == 1) {
			setColorSensorRGB();
		}
		final float[] sample = new float[getColorProvider().sampleSize()];
		getColorProvider().fetchSample(sample, 0);
		final ColorRGB color = new ColorRGB(sample[0], sample[1], sample[2]);
		return color;
	}

	/**
	 * Gets the color ID
	 */
	public int getColorInt() {
		if (isColorActivated() == true && getColorProvider().sampleSize() == 3) {
			setColorSensorIDmode();
		}
		/*
		 * float[] sample = new float[getColorProvider().sampleSize()];
		 * getColorProvider().fetchSample(sample, 0); return (int) sample[0];
		 */
		return colorSensor.getColorID();
	}

	/**
	 * Gets the name of the color
	 * 
	 * @param color
	 *            the color
	 */
	public String getColorName(int color) {
		String colorName = "";
		switch (color) {
			case lejos.robotics.Color.NONE:
				colorName = "NONE";
				break;

			case lejos.robotics.Color.BLACK:
				colorName = "BLACK";
				break;

			case lejos.robotics.Color.BLUE:
				colorName = "BLUE";
				break;

			case lejos.robotics.Color.BROWN:
				colorName = "BROWN";
				break;

			case lejos.robotics.Color.GREEN:
				colorName = "GREEN";
				break;

			case lejos.robotics.Color.RED:
				colorName = "RED";
				break;

			case lejos.robotics.Color.YELLOW:
				colorName = "YELLOW";
				break;

			case lejos.robotics.Color.WHITE:
				colorName = "WHITE";
				break;
		}

		return colorName;
	}

	/**
	 * Gets the distance from the distanceProvider
	 *
	 * @return the distance in meter
	 * @return -1 if the ultrasonic sensor is not activated
	 */
	public float getDistance() {
		if (isUltrasonicActivated() == true && getDistanceProvider().sampleSize() == 1) {
			final float[] sample = new float[getDistanceProvider().sampleSize()];
			getDistanceProvider().fetchSample(sample, 0);
			return sample[0];
		} else {
			return -1;
		}
	}

	/**
	 * Gets the distance provider
	 *
	 * @return the distance provider
	 */
	public SampleProvider getDistanceProvider() {
		return distanceProvider;
	}

	/**
	 * Gets the touch provider
	 *
	 * @return the touch provider
	 */
	public SampleProvider getTouchProvider() {
		return touchProvider;
	}

	/**
	 * Checks if color sensor is activated
	 *
	 * @return true if successful
	 */
	public boolean isColorActivated() {
		return colorActivated;
	}

	/**
	 * Checks if the touch sensor is pressed
	 * 
	 * @return true if successful
	 */
	public boolean isPush() {
		if (isTouchActivated() == true && getTouchProvider().sampleSize() == 1) {
			final float[] sample = new float[getTouchProvider().sampleSize()];
			getTouchProvider().fetchSample(sample, 0);
			if (sample[0] == 1) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Checks if touch sensor is activated
	 *
	 * @return true if successful
	 */
	public boolean isTouchActivated() {
		return touchActivated;
	}

	/**
	 * Checks if ultrasonic sensor is activated
	 *
	 * @return true if successful
	 */
	public boolean isUltrasonicActivated() {
		return ultrasonicActivated;
	}

	/**
	 * Sets the color provider
	 *
	 * @param colorProvider
	 *            the color provider (must be in RGB mode)
	 */
	public void setColorProvider(final SampleProvider colorProvider) {
		this.colorProvider = colorProvider;
	}

	/**
	 * Sets the distance provider
	 *
	 * @param distanceProvider
	 *            the distance provider (must be in Distance mode)
	 */
	public void setDistanceProvider(final SampleProvider distanceProvider) {
		this.distanceProvider = distanceProvider;
	}

	/**
	 * Sets the touch provider
	 *
	 * @param touchProvider
	 *            the touch provider (must be in Touch mode)
	 */
	public void setTouchProvider(final SampleProvider touchProvider) {
		this.touchProvider = touchProvider;
	}

}
