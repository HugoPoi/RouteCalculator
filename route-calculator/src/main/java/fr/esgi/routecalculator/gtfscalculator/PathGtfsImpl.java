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
	
	static String parentStopIdArrival;
	String parentStopIdDeparture;

	
	String currentStopId;
	int currentTripId;
	
	Stop currentStop;
	StopTime currentStopTime;
	
	//Temps total de ce chemin en secondes
	int totaltime;
	
	static GtfsMutableRelationalDao dao;
	static SessionFactory sessionFactory;
	
	//constructeur d'initialisation de calcul (appel√© une seul fois)
	public PathGtfsImpl(String parentStopIdDeparture,String parentStopIdArrival, SessionFactory inSessionFactory) {
		super();
		sessionFactory = inSessionFactory;
		PathGtfsImpl.dao = new HibernateGtfsFactory(inSessionFactory).getDao();
		PathGtfsImpl.parentStopIdArrival = getParentStopIdForNameStop(parentStopIdArrival);
		this.parentStopIdDeparture = getParentStopIdForNameStop(parentStopIdDeparture);
		totaltime = 0;
	}
	
	//constructeur d'incr√©mentation de chemin
	public PathGtfsImpl(PathGtfsImpl inLast, String stopId, int tripId){
		this.last = inLast;
		currentStopId = stopId;
		currentTripId = tripId;
		
		int triptime = 0;//a impl√©menter
		//calcul du temps.
		this.totaltime = last.totaltime + triptime;
	}
	
	public void setCurrentStop (Stop stop){
		this.currentStop =stop;
	}

	
	public void setCurrentStopTime (StopTime stopTime){
		this.currentStopTime = stopTime;
	}
	
	public void setCurrentStopId (String id){
		this.currentStopId = id;
	}

	public void setParentStopIdDeparture (String id){
		this.parentStopIdDeparture = id;
	}

	static private String getParentStopIdForNameStop(String name){
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List<Stop> stops = session.createQuery("from Stop as stop where stop.name = ?").setString(0, name).list();
		session.close();
		if(stops.isEmpty()){
			System.out.println("Erreur, le nom de cette station n'existe pas !");
		}else{
				System.out.println(stops.get(0).getParentStation().toString());
				return stops.get(0).getParentStation().toString();
		}
		return null;
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
			List stops = session.createQuery("from Stop as stop where stop.parentStation = ?").setString(0, parentStopIdDeparture).list();
			
			session.close();
			//Cette boucle doit Ítre supprimer au profit de celle au dessus
			List<StopTime> Liststoptime = new ArrayList<StopTime>();
			
			//Le plus petit stopTime pour un stop en fonction de la date de dÈpart 
			StopTime minTimeStopTime;

			for (Object possibleLines : stops) {
				boolean firstPassage = true;
				minTimeStopTime = new StopTime();
				minTimeStopTime.setDepartureTime(9999999);
				for (StopTime stoptime : dao.getStopTimesForStop((Stop) possibleLines) ){
						if(stoptime.getDepartureTime() < minTimeStopTime.getDepartureTime()){
							if(stoptime.getDepartureTime() >= departTime){
								minTimeStopTime = stoptime;
							}
						}
				}
				if(minTimeStopTime.getDepartureTime() != 9999999){
					Liststoptime.add(minTimeStopTime);
				}else{
					System.out.println("Le Stop n'a plus de StopTime ‡ cette heure");
				}
			}
			return Liststoptime;
		}else{
			@SuppressWarnings("rawtypes")
			List<Stop> stops = session.createQuery("from Stop as stop where stop.parentStation = ?").setString(0, currentStop.getParentStation().toString()).list();
			session.close();
			
			StopTime minTimeStopTime;

			List<StopTime> Liststoptime = new ArrayList<StopTime>();
			for (Stop stopRead : stops){
				minTimeStopTime = new StopTime();
				minTimeStopTime.setDepartureTime(9999999);
				for (StopTime stoptime : dao.getStopTimesForStop((Stop) stopRead) ){	
					if(stoptime.getDepartureTime() > currentStopTime.getArrivalTime()){
						if(stoptime.getDepartureTime() <= minTimeStopTime.getDepartureTime()){
							//On ne rÈcupÈre pas le stopTime qui a ÈtÈ parcouru just avant 
							if(last != null && !last.currentStopTime.equals(stoptime)){
								minTimeStopTime = stoptime;
							}
					}
				}
			}
				if(minTimeStopTime.getDepartureTime() != 9999999){
					Liststoptime.add(minTimeStopTime);
				}else{
					System.out.println("Le Stop n'a plus de StopTime ‡ cette heure");
				}
			}
			
			return Liststoptime;	
		}
	}

}
