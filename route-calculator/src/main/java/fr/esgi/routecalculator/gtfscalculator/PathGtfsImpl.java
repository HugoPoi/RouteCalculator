package fr.esgi.routecalculator.gtfscalculator;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs.services.HibernateGtfsFactory;

import fr.esgi.routecalculator.interfaces.gtfs.IPathGtfs;

public class PathGtfsImpl implements IPathGtfs {
	
	PathGtfsImpl last;
	
	static int parentStopIdArrival;
	Integer parentStopIdDeparture;
	
	Integer currentStopId;
	Integer currentTripId;
	StopTime currentStopTime;
	
	//Temps total de ce chemin en secondes
	int totaltime;
	
	static GtfsMutableRelationalDao dao;
	static SessionFactory sessionFactory;
	
	//constructeur d'initialisation de calcul (appelé une seul fois)
	public PathGtfsImpl(int parentStopIdDeparture,int parentStopIdArrival, SessionFactory inSessionFactory) {
		super();
		sessionFactory = inSessionFactory;
		PathGtfsImpl.dao = new HibernateGtfsFactory(inSessionFactory).getDao();
		PathGtfsImpl.parentStopIdArrival = parentStopIdArrival;
		this.parentStopIdDeparture = parentStopIdDeparture;
		totaltime = 0;
	}
	
	//constructeur d'incrémentation de chemin
	public PathGtfsImpl(PathGtfsImpl inLast, int stopId, int tripId){
		this.last = inLast;
		currentStopId = stopId;
		currentTripId = tripId;
		
		int triptime = 0;//a implémenter
		//calcul du temps.
		this.totaltime = last.totaltime + triptime;
	}

	@Override
	public int compareTo(IPathGtfs o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IPathGtfs getParent() {
		return last;
	}

	@Override
	public Stop getCurrentStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trip getCurrentTrip() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCurrentStopId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentTripId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Stop> getPossibleConnections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StopTime> getPossibleConnectionsStopId() {
		//List possibilities = new LinkedList<StopTime>();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		if(this.currentStopTime == null){
			//Quand on commence appelé une seul fois
			//récupération des stops;
			@SuppressWarnings("rawtypes")
			List stops = session.createQuery("from Stop as stop where stop.parentStation = ?").setString(0, parentStopIdDeparture.toString()).list();
			session.close();
			for (Object possibleLines : stops) {
				return dao.getStopTimesForStop((Stop) possibleLines);
			}
		}else{
			//Sinon process normal
		}
		return null;
	}

}
