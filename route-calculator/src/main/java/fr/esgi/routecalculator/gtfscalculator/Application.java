package fr.esgi.routecalculator.gtfscalculator;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;

import fr.esgi.routecalculator.interfaces.gtfs.IPathGtfs;


public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IPathGtfs Test = new PathGtfsImpl("2009", 1999, HibernateUtil.getSessionFactory());
		
		//Premier passage
		/*for (StopTime toto : Test.getPossibleConnectionsStopId()) {
			System.out.println(toto.toString());
		}*/
		
		//IdStop 1629 : Alexandre Dumas 
		StopTime testStoptimes = new StopTime();
		testStoptimes.setArrivalTime(67208);
		Stop testStop = new Stop();
		testStop.setParentStation("1978");
		Test.setCurrentStopTime(testStoptimes);
		Test.setCurrentStop(testStop);
		//For the test
		
		//Second et autres passages
		for (StopTime toto : Test.getPossibleConnectionsStopId()) {
			System.out.println(toto.toString());
		}
	}
}
