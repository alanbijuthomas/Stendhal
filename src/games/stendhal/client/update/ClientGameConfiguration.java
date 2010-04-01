package games.stendhal.client.update;

import java.io.InputStream;
import java.util.Properties;

/**
 * read the configuration file for the client.
 * 
 * @author hendrik
 */
public class ClientGameConfiguration {

	private static ClientGameConfiguration instance;

	private Properties gameConfig = getGameProperties();

	private ClientGameConfiguration() {
		instance = this;
	}

	private Properties getGameProperties() {
		try {
			InputStream is =  ClientGameConfiguration.class
					.getResourceAsStream("game.properties");
			if (is != null) {
				gameConfig.load(is);
				is.close();
			} else {
				gameConfig = new DefaultGameProperties();
			}
			return gameConfig;
		} catch (Exception e) {
			e.printStackTrace();
			return new DefaultGameProperties();
		}
	}

	private static void init() {
		if (instance == null) {
			instance = new ClientGameConfiguration();
		}
	}

	/**
	 * gets a configuration value, in case it is undefined, the default of
	 * game-default.properties is returned. If this is undefined, too, the
	 * return value is null
	 * 
	 * @param key
	 *            key
	 * @return configured value
	 */
	public static String get(final String key) {
		init();
		return instance.gameConfig.getProperty(key);
	}
}
