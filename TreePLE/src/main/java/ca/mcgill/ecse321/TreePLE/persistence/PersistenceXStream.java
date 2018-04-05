package ca.mcgill.ecse321.TreePLE.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.thoughtworks.xstream.XStream;

import ca.mcgill.ecse321.TreePLE.model.Forecast;
import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.SustainabilityReport;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.VersionManager;

// The first type parameter is the domain type for which we are creating the repository.
// The second is the key type that is used to look it up. This example will not use it.
@Repository
public class PersistenceXStream {
//TODO: ADD SURVEY 
	private static XStream xstream = new XStream();
	//private static String filename = "/webservice/data.xml";
	private static String filename = "data.xml";
	public static VersionManager initializeModelManager(String fileName) {
		// Initialization for persistence
		VersionManager vm;
		setFilename(fileName);
		setAlias("versionManager", VersionManager.class);
		setAlias("treeManager", TreeManager.class);
		setAlias("versionManager", VersionManager.class);
		setAlias("tree", Tree.class);
		setAlias("municipality", Municipality.class);
		setAlias("survey", Survey.class);
		setAlias("location", Location.class);
		setAlias("sustainabilityReport", SustainabilityReport.class);
		setAlias("user", User.class);
		setAlias("forecast", Forecast.class);

		// load model if exists, create otherwise
		File file = new File(fileName);
		if (file.exists()) {
			vm = (VersionManager) loadFromXMLwithXStream();
		} else {
			try {
				System.out.println(file.getAbsolutePath()); //added by marton for debugging
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			vm = new VersionManager();
			User defaultUser = new User();
			TreeManager tm = new TreeManager(true, "1.0", 2018, defaultUser);
			vm.addTreeManager(tm);
			saveToXMLwithXStream(vm);
		}
		return vm;
	}

	public static boolean saveToXMLwithXStream(Object obj) {
		xstream.setMode(XStream.ID_REFERENCES);
		String xml = xstream.toXML(obj); // save our xml file

		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(xml);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Object loadFromXMLwithXStream() {
		xstream.setMode(XStream.ID_REFERENCES);
		try {
			FileReader fileReader = new FileReader(filename); // load our xml file
			return xstream.fromXML(fileReader);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setAlias(String xmlTagName, Class<?> className) {
		xstream.alias(xmlTagName, className);
	}

	public static void setFilename(String fn) {
		filename = fn;
	}

	public static String getFilename() {
		return filename;
	}

	public static void clearData() {
		File myFoo = new File(filename);
		FileWriter fooWriter;
		try {
			fooWriter = new FileWriter(myFoo, false);
			fooWriter.write("");
			fooWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
