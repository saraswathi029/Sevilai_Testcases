package utility;


	

	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.util.Properties;

	public class Propertiesfile {
		FileInputStream fis;

		public static Properties fetchproperty() throws Throwable {

			FileInputStream fis = new FileInputStream("src\\test\\resources\\ama.properties");

			Properties prop = new Properties();
			prop.load(fis);
			return prop;
		}

		public static String getUrl() throws Throwable {
			return fetchproperty().getProperty("url");
		}

		public static String getusername() throws Throwable {
			return fetchproperty().getProperty("username");
		}

		public static String getpassword() throws Throwable {
			return fetchproperty().getProperty("password");
		}

	}



