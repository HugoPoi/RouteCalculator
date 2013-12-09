package fr.esgi.routecalculator.gtfscalculator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.model.calendar.ServiceDate;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs.services.HibernateGtfsFactory;

import sun.security.jca.ServiceId;

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
	
	static HashSet<Stop> visitedStations = new HashSet<Stop>();
	static List<ServiceId> servicesAvailables;
        
        private String departStation;
        private String endStation;

	// constructeur d'initialisation de calcul (appelé une seul fois)
	@SuppressWarnings("unchecked")
	public PathGtfsImpl(String parentStopIdDeparture,
			String parentStopIdArrival, GregorianCalendar departureTimeIn) {
		super();
                this.departStation = parentStopIdDeparture;
                this.endStation = parentStopIdArrival;
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
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		servicesAvailables = session.createQuery("select service.serviceId from ServiceCalendar as service where service.startDate <= :date AND service.endDate >= :date" +
				" AND service.serviceId NOT IN (select exception.serviceId from ServiceCalendarDate AS exception WHERE exception.date = :date)")
				.setString("date", new ServiceDate(departureTime).getAsString()).list();
	}

	// constructeur d'incrémentation de chemin
	public PathGtfsImpl(PathGtfsImpl inLast, StopTime takenStopTime){
		this.last = inLast;
		this.currentStopTime = takenStopTime;
		this.currentStop = takenStopTime.getStop();
		this.parentStopIdDeparture = currentStop.getParentStation();
		PathGtfsImpl.visitedStations.add(currentStop);
                this.departStation = inLast.departStation;
                this.endStation = inLast.endStation;
		if(last.currentStopTime != null){
			this.totaltime = (currentStopTime.getArrivalTime() - last.currentStopTime.getArrivalTime()) + last.totaltime;
		}else{
			this.totaltime = (currentStopTime.getArrivalTime() - departureTimeInSeconds);
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
		if(this.totaltime > o.getTotalTime()){
			return 1;
		}
		if(this.totaltime < o.getTotalTime()){
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

	public int getDepartureTime() {
		return departureTimeInSeconds;
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
						"select stoptime.trip.route from StopTime as stoptime where stoptime.stop = :stop AND stoptime.departureTime >= :currenttime" +
						" AND stoptime.trip.serviceId IN (:servicesavailable)" +
						" group by stoptime.trip.route")
						.setEntity("stop", possibleLines)
						.setParameterList("servicesavailable", servicesAvailables)
						.setInteger("currenttime", currentRealTimeInSeconds).list();	
				
				for (Object possibleRoute : routesList) {
					StopTime startStopTime = (StopTime) session.createQuery("from StopTime as stoptime where stoptime.stop = :stop AND stoptime.departureTime >= :currenttime AND stoptime.trip.route = :route" +
							" AND stoptime.trip.serviceId IN (:servicesavailable)" +
							" order by stoptime.departureTime")
						.setEntity("stop", possibleLines)
						.setInteger("currenttime", currentRealTimeInSeconds)
						.setParameterList("servicesavailable", servicesAvailables)
						.setEntity("route", possibleRoute).setMaxResults(1).uniqueResult();
					if(last != null){
					StopTime getNextStopTime = (StopTime) session.createQuery("from StopTime as stoptime where stoptime.trip = :trip" +
							" AND stoptime.stopSequence = :position AND stoptime.stop.parentStation != :laststop" +
							" AND stoptime.stop NOT IN (:visitedstations) ")
							.setEntity("trip", startStopTime.getTrip())
							.setString("laststop", last.parentStopIdDeparture)
							.setParameterList("visitedstations", visitedStations)
							.setInteger("position", startStopTime.getStopSequence()+1).uniqueResult();
					if(getNextStopTime != null) possibilities.add(getNextStopTime);
					}else{
						StopTime getNextStopTime = (StopTime) session.createQuery("from StopTime as stoptime where stoptime.trip = :trip AND stoptime.stopSequence = :position")
								.setEntity("trip", startStopTime.getTrip())
								.setInteger("position", startStopTime.getStopSequence()+1).uniqueResult();
						if(getNextStopTime != null) possibilities.add(getNextStopTime);
					}
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
        
        public String getDepartStation () {
            return departStation;
        }
        
        public String getEndStation () {
            return endStation;
        }
        
        public List<StopTime> getTrips () {
            ArrayList<StopTime> stopTimes = new ArrayList<>();
            PathGtfsImpl enext = this;
            String previousId = "";
            do{
                if (enext.currentStopTime != null) {
                    String id = enext.currentStopTime.getTrip().getId().getId();
                    if (!id.equals(previousId)) {
                        previousId = id;
                        stopTimes.add(0,enext.currentStopTime);
                    }
                }
                enext = enext.last;
            } while(enext != null);
            return stopTimes;
        }
	
	@Override
	public String toString() {
            //this.getTransportUtilise(this.getTripsId());
		StringBuilder out = new StringBuilder();
		PathGtfsImpl enext = this;
		
		do{
			if(enext.currentStopTime != null) out.append(enext.currentStopTime.getStop().getName() + enext.currentStopTime.getTrip() + "\n");
			enext = enext.last;
		}
		while(enext != null);
		
		return out.toString();
	}
	
}
