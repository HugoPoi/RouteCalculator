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
		Graph premierGraph = new Graph();
		premierGraph.findRoute("Alexandre-Dumas", "Belleville");
	}
}
