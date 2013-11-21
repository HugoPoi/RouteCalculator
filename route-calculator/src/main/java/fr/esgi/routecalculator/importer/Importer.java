package fr.esgi.routecalculator.importer;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs.services.HibernateGtfsFactory;

public class Importer {

	private static final String KEY_CLASSPATH = "classpath:";

	private static final String KEY_FILE = "file:";

	public static void main(String[] args) throws IOException {

		/*if (!(args.length == 1 || args.length == 2)) {
			System.err.println("usage: gtfsPath [hibernate-config.xml]");
			System.exit(-1);
		}*/

		String resource = "file:src/test/ressources/hibernate.cfg.xml";
		if (args.length == 2)
			resource = args[1];
		
		String gtfsPathFiles = "src/test/ressources/gtfs/";
		if (args.length >= 1)
			gtfsPathFiles = args[0];

		HibernateGtfsFactory factory = createHibernateGtfsFactory(resource);

		GtfsReader reader = new GtfsReader();
		reader.setInputLocation(new File(gtfsPathFiles));

		GtfsMutableRelationalDao dao = factory.getDao();
		reader.setEntityStore(dao);
		reader.run();

		Collection<Stop> stops = dao.getAllStops();

		for (Stop stop : stops)
			System.out.println(stop.getName());

	}

	// Other methods
	private static HibernateGtfsFactory createHibernateGtfsFactory(
			String resource) {

		Configuration config = new Configuration();

		if (resource.startsWith(KEY_CLASSPATH)) {
			resource = resource.substring(KEY_CLASSPATH.length());
			config = config.configure(resource);
		} else if (resource.startsWith(KEY_FILE)) {
			resource = resource.substring(KEY_FILE.length());
			config = config.configure(new File(resource));
		} else {
			config = config.configure(new File(resource));
		}

		SessionFactory sessionFactory = config.buildSessionFactory();
		return new HibernateGtfsFactory(sessionFactory);
	}
}
