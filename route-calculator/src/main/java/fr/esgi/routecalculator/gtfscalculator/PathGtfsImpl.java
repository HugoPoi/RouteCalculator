package fr.esgi.routecalculator.gtfscalculator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs.services.HibernateGtfsFactory;

import fr.esgi.routecalculator.interfaces.INode;
import fr.esgi.routecalculator.interfaces.gtfs.IPathGtfs;

public class PathGtfsImpl implements IPathGtfs {

	PathGtfsImpl last;

	static String parentStopIdArrival;
	static GregorianCalendar departureTime;
	static int departureTimeInSeconds;
	
	String parentStopIdDeparture;

	Stop currentStop;
	StopTime currentStopTime;

	// Temps total de ce chemin en secondes
	int totaltime;

	static GtfsMutableRelationalDao dao;

	// constructeur d'initialisation de calcul (appelé une seul fois)
	public PathGtfsImpl(String parentStopIdDeparture,
			String parentStopIdArrival, GregorianCalendar departureTimeIn) {
		super();
		departureTime = departureTimeIn;
		PathGtfsImpl.dao = new HibernateGtfsFactory(HibernateUtil.getSessionFactory()).getDao();
		PathGtfsImpl.parentStopIdArrival = getParentStopIdForNameStop(parentStopIdArrival);
		this.parentStopIdDeparture = getParentStopIdForNameStop(parentStopIdDeparture);
		totaltime = 0;
		
		java.util.GregorianCalendar calendar = new GregorianCalendar();
		departureTimeInSeconds = calendar.get(Calendar.HOUR_OF_DAY) * 3600
				+ calendar.get(Calendar.MINUTE) * 60
				+ calendar.get(Calendar.SECOND);
		System.out.println("Heure de départ en seconde : " + departureTimeInSeconds);
	}

	// constructeur d'incrémentation de chemin
	public PathGtfsImpl(PathGtfsImpl inLast, StopTime takenStopTime) throws Exception {
		this.last = inLast;
		this.currentStopTime = takenStopTime;
		//Take the StopTime and Forward +1
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		StopTime getNextStopTime = (StopTime) session.createQuery("from StopTime as stoptime where stoptime.trip = :trip AND stoptime.stopSequence = :position")
			.setEntity("trip", takenStopTime.getTrip())
			.setInteger("position", takenStopTime.getStopSequence()+1).uniqueResult();
		
		if(getNextStopTime == null){
			throw new Exception("Train arrivé au terminus");
		}else{
			int triptime = getNextStopTime.getArrivalTime() - takenStopTime.getDepartureTime();
			if(last.last == null){
				triptime += takenStopTime.getDepartureTime() - departureTimeInSeconds;
			}
			this.totaltime = last.totaltime + triptime;
			this.currentStop = getNextStopTime.getStop();
			this.parentStopIdDeparture = this.currentStop.getParentStation();
		}
		
	}

	static private String getParentStopIdForNameStop(String name) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List<Stop> stops = session
				.createQuery("from Stop as stop where stop.name = ?")
				.setString(0, name).list();
		session.close();
		if (stops.isEmpty()) {
			System.out
					.println("Erreur, le nom de cette station n'existe pas !");
		} else {
			System.out.println(stops.get(0).getParentStation().toString());
			return stops.get(0).getParentStation().toString();
		}
		return null;
	}

	public int compareTo(IPathGtfs o) {
		if(this.totaltime < o.getTotalTime()){
			return 1;
		}
		if(this.totaltime > o.getTotalTime()){
			return -1;
		}
		return 0;
	}

	@Override
	public IPathGtfs getParent() {
		return last;
	}

	@Override
	public int getTotalTime() {
		return totaltime;
	}

	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public List<StopTime> getPossibleConnectionsStopId() {
		List<StopTime> possibilities = new LinkedList<StopTime>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		int currentRealTimeInSeconds = departureTimeInSeconds + totaltime;

			@SuppressWarnings("rawtypes")
			List stops = session
					.createQuery(
							"from Stop as stop where stop.parentStation = ?")
					.setString(0, parentStopIdDeparture).list();

			for (Object possibleLines : stops) {
				
				List<Route> routesList= session.createQuery(
						"select stoptime.trip.route from StopTime as stoptime where stoptime.stop = :stop AND stoptime.departureTime >= :currenttime group by stoptime.trip.route")
						.setEntity("stop", possibleLines)
						.setInteger("currenttime", currentRealTimeInSeconds).list();
				
				for (Object possibleRoute : routesList) {
					possibilities.add((StopTime) session.createQuery("from StopTime as stoptime where stoptime.stop = :stop AND stoptime.departureTime >= :currenttime AND stoptime.trip.route = :route order by stoptime.departureTime")
						.setEntity("stop", possibleLines)
						.setInteger("currenttime", currentRealTimeInSeconds)
						.setEntity("route", possibleRoute).setMaxResults(1).uniqueResult());
				}
			}

			return possibilities;
	}

	@Override
	public Stop getCurrentStop() {
		return currentStop;
	}

	@Override
	public Trip getCurrentTrip() {
		return currentStopTime.getTrip();
	}

	@Override
	public Boolean isCompleted() {
		return (parentStopIdDeparture.equals(parentStopIdArrival));
	}
	
	@Override
	public String toString() {
		StringBuffer out = new StringBuffer();
		PathGtfsImpl enext = this;
		do{
			if(enext.currentStopTime != null) out.append(enext.currentStopTime.getStop().getName() + enext.currentStopTime.getTrip().getId() + "\n");
			enext = enext.last;
		}
		while(enext != null);
		
		return out.toString();
	}
	
}
