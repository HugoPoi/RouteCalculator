package fr.esgi.routecalculator.gtfscalculator;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs.services.HibernateGtfsFactory;

import com.mysql.jdbc.Connection;


import fr.esgi.routecalculator.interfaces.gtfs.IPathGtfs;

public class PathGtfsImpl implements IPathGtfs{
	
	PathGtfsImpl last;
	
	static String parentStopIdArrival;
	String parentStopIdDeparture;

	
	String currentStopId;
	int currentTripId;
	
	public Stop currentStop;
	public StopTime currentStopTime;
	public boolean plusDeRoutePossible = false;
	
	//Temps total de ce chemin en secondes
	int totaltime;
	
	static GtfsMutableRelationalDao dao;
	static SessionFactory sessionFactory;
	
	//constructeur d'initialisation de calcul (appelÃ© une seul fois)
	public PathGtfsImpl(String parentStopIdDeparture,SessionFactory inSessionFactory) {
		super();
		sessionFactory = inSessionFactory;
		PathGtfsImpl.dao = new HibernateGtfsFactory(inSessionFactory).getDao();
		this.parentStopIdDeparture = getParentStopIdForNameStop(parentStopIdDeparture);
		Graph.parentIdDeparture = this.parentStopIdDeparture;
		totaltime = 0;
	}
	
	//constructeur d'incrÃ©mentation de chemin
	public PathGtfsImpl(PathGtfsImpl inLast, StopTime currentStopTime) throws SQLException{
		this.last = inLast;
		System.out.println("Construction d'une nouvelle route");
		//System.out.println("Stoptime en entre " + currentStopTime.getId());
	
		this.currentStop = getNextStopForStopTimes(currentStopTime);
		if(this.currentStop == null){
			System.out.println("On arrive à la fin, il n'y a plus de station suivante");
			plusDeRoutePossible = true;
		}else if (Graph.parentIdDeparture.equals(this.currentStop.getParentStation())){ 
			System.out.println("On retourne sur la station précédente, on ne s'occupe pas de cette route");
			plusDeRoutePossible = true;
		}else if(inLast != null && inLast.last != null) {
			if(inLast.last.getCurrentStop() != null){
				if(this.currentStop.getParentStation().equals(inLast.last.getCurrentStop().getParentStation())){
					System.out.println("On retourne sur la station précédente, cette route ne va donc pas être crée !");
					plusDeRoutePossible = true;
				}
			}
		}
		

		this.currentStopTime = currentStopTime;
		//calcul du temps.
		//System.out.println(inLast.totaltime +"   "+ currentStopTime.getArrivalTime() +" "+ Graph.departTime);
		this.totaltime = inLast.totaltime + currentStopTime.getArrivalTime() - Graph.departTime;
		System.out.println("currentStopTime : " + this.currentStopTime.getId());
		if(this.currentStop != null)
			System.out.println("currentStop : " + this.currentStop.getName() );
		System.out.println("----------------------");


	}
	
	static public Stop getNextStopForStopTimes(StopTime stoptime) throws SQLException{
		
		Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/gtfsratp","root","");
		ResultSet resultats = null;
		

		String str[]=stoptime.getTrip().getId().toString().split("_");
		String requete = "SELECT gid FROM gtfs_stop_times where trip_id like \"" + str[1] + "\" AND stopSequence = " + (stoptime.getStopSequence() + 1);
		int stopId = 0;
		try {
		   Statement stmt = con.createStatement();
		   resultats = stmt.executeQuery(requete);
		   while(resultats.next()){
				stopId = resultats.getInt("gid");
		   }
		} catch (SQLException e) {
		   System.out.println("Erreur requête SQL");
		 }
		
		if(stopId != 0){
			return dao.getStopForId(dao.getStopTimeForId(stopId).getStop().getId());
		}else 
		{
			return null;
		}
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

	static public String getParentStopIdForNameStop(String name){
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List<Stop> stops = session.createQuery("from Stop as stop where stop.name = ?").setString(0, name).list();
		session.close();
		if(stops.isEmpty()){
			System.out.println("Erreur, le nom de cette station n'existe pas !");
		}else{
				return stops.get(0).getParentStation().toString();
		}
		return null;
	}
	

	 
	public PathGtfsImpl getParent() {
		return last;
	}

	 
	public Stop getCurrentStop() {
		return this.currentStop;
	}

	 
	public Trip getCurrentTrip() {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public int getCurrentStopId() {
		// TODO Auto-generated method stub
		return 0;
	}

	 
	public int getCurrentTripId() {
		// TODO Auto-generated method stub
		return 0;
	}

	 
	public int getTotalTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	 

	
	public int compareTo(PathGtfsImpl o) {
		if(this.totaltime > o.totaltime){
			return 1;
		}
		if(this.totaltime < o.totaltime){
			return -1;
		}
		return 0;
	}
	
	@SuppressWarnings("null")
	 
	public List<StopTime> getPossibleConnectionsStopId() {
		//List possibilities = new LinkedList<StopTime>();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		if(this.currentStopTime == null){
			

			//Création de la date de départ
			java.util.GregorianCalendar calendar = new GregorianCalendar();
			java.util.Date time  = calendar.getTime();
			Graph.departTime = calendar.get(Calendar.HOUR_OF_DAY)*3600 + calendar.get(Calendar.MINUTE)*60 + calendar.get(Calendar.SECOND);
			//System.out.println("Heure de départ en seconde : " + Graph.departTime);
			
			
			//Quand on commence appelÃ© une seul fois
			//rÃ©cupÃ©ration des stops;
			@SuppressWarnings("rawtypes")
			List stops = session.createQuery("from Stop as stop where stop.parentStation = ?").setString(0, parentStopIdDeparture).list();
			
			session.close();
			//Cette boucle doit être supprimer au profit de celle au dessus
			List<StopTime> Liststoptime = new ArrayList<StopTime>();
			
			//Le plus petit stopTime pour un stop en fonction de la date de départ 
			StopTime minTimeStopTime;

			for (Object possibleLines : stops) {
				boolean firstPassage = true;
				minTimeStopTime = new StopTime();
				minTimeStopTime.setDepartureTime(9999999);
				for (StopTime stoptime : dao.getStopTimesForStop((Stop) possibleLines) ){
						if(stoptime.getDepartureTime() < minTimeStopTime.getDepartureTime()){
							if(stoptime.getDepartureTime() >= Graph.departTime){
								minTimeStopTime = stoptime;
							}
						}
				}
				if(minTimeStopTime.getDepartureTime() != 9999999){
					Liststoptime.add(minTimeStopTime);
				}else{
					System.out.println("Le Stop n'a plus de StopTime à cette heure");
				}
			}
			return Liststoptime;
		}else{
			if(!this.plusDeRoutePossible){
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
							//On ne récupére pas le stopTime qui a été parcouru just avant 
							if(!plusDeRoutePossible){
								//System.out.println(" next stop parent id : "+stoptime.getStop().getParentStation());
								//System.out.println(" courante stop parent id : " + this.currentStop.getParentStation());
								//if(!stoptime.getStop().getParentStation().equals(this.currentStop.getParentStation()))
										minTimeStopTime = stoptime;
							}
					}
				}
			}
				if(minTimeStopTime.getDepartureTime() != 9999999){
					Liststoptime.add(minTimeStopTime);
				}else{
					System.out.println("Le Stop n'a plus de StopTime à cette heure");
				}
			}
			
			return Liststoptime;	
		}else{
			return null;
		}
		}
	}

	public boolean isCompleted(String arrival) {
		if(!this.plusDeRoutePossible){
			return (currentStop.getParentStation().toString().equals(arrival));
		}else{
			return false;
		}
	}

	@Override
	public int compareTo(IPathGtfs o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
