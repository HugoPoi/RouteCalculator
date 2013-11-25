package fr.esgi.routecalculator.gtfscalculator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
	
	//constructeur d'initialisation de calcul (appel√© une seul fois)
	public PathGtfsImpl(int parentStopIdDeparture,int parentStopIdArrival, SessionFactory inSessionFactory) {
		super();
		sessionFactory = inSessionFactory;
		PathGtfsImpl.dao = new HibernateGtfsFactory(inSessionFactory).getDao();
		PathGtfsImpl.parentStopIdArrival = parentStopIdArrival;
		this.parentStopIdDeparture = parentStopIdDeparture;
		totaltime = 0;
	}
	
	//constructeur d'incr√©mentation de chemin
	public PathGtfsImpl(PathGtfsImpl inLast, int stopId, int tripId){
		this.last = inLast;
		currentStopId = stopId;
		currentTripId = tripId;
		
		int triptime = 0;//a impl√©menter
		//calcul du temps.
		this.totaltime = last.totaltime + triptime;
	}

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

	@SuppressWarnings("null")
	@Override
	public List<StopTime> getPossibleConnectionsStopId() {
		//List possibilities = new LinkedList<StopTime>();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		if(this.currentStopTime == null){
			
			//CrÈation de la date de dÈpart
			int departTime = 0;
			java.util.GregorianCalendar calendar = new GregorianCalendar();
			java.util.Date time  = calendar.getTime();
			departTime = calendar.get(Calendar.HOUR_OF_DAY)*3600 + calendar.get(Calendar.MINUTE)*60 + calendar.get(Calendar.SECOND);
			System.out.println("Heure de dÈpart en seconde : " + departTime);
			
			
			//Quand on commence appel√© une seul fois
			//r√©cup√©ration des stops;
			@SuppressWarnings("rawtypes")
			List stops = session.createQuery("from Stop as stop where stop.parentStation = ?").setString(0, parentStopIdDeparture.toString()).list();
			
			//CrÈation d'une liste de type StopsTimes
			//CrÈation d'une nouvelle session.createQuery, qui prend en filtre l'heure de dÈpart et les diffÈrents stop 
			//Il ne faut rÈcupÈrer que le premier stopTime pour une station celui le plus proche de l'heure de dÈpart
			/*
			 * SELECT * FROM `gtfs_stop_times` as B WHERE `stop_id` like "1674"
			and `arrivalTime` > 1020
			and `arrivalTime` = (select max(`arrivalTime`)
			                                from `gtfs_stop_times` as A where A.gid = B.gid)
			 */
			session.close();
			//Cette boucle doit Ítre supprimer au profit de celle au dessus
			List<StopTime> Liststoptime = new ArrayList<StopTime>();
			
			//Le plus petit stopTime pour un stop en fonction de la date de dÈpart 
			StopTime minTimeStopTime;

			for (Object possibleLines : stops) {
				minTimeStopTime = new StopTime();
				minTimeStopTime.setDepartureTime(9999999);
				for (StopTime stoptime : dao.getStopTimesForStop((Stop) possibleLines) ){	
						if(stoptime.getDepartureTime() < minTimeStopTime.getDepartureTime()){
							if(stoptime.getDepartureTime() >= departTime){
							minTimeStopTime = stoptime;
						}
						}
				}
				Liststoptime.add(minTimeStopTime);
			}
			return Liststoptime;
		}else{
			@SuppressWarnings("rawtypes")
			List stops = session.createQuery("from Stop as stop where stop.id = ?").setString(0, currentStopId.toString()).list();
			
			
		}
		return null;
	}

}
