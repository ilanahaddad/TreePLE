package ca.mcgill.ecse321.TreePLE.service;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.TreeManager;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.Version;
import ca.mcgill.ecse321.TreePLE.persistence.PersistenceXStream;


public class TreeManagerService {
	private TreeManager tm;
	
	public TreeManagerService(TreeManager tm) {
		this.tm=tm;
	}
	public Tree createTree(String species,  double height, double diameter, 
			int id, Location location, User owner, 
			Municipality municipality, Version version) throws InvalidInputException{
		if(species==null || location==null || owner == null || municipality==null || version == null) {
			throw new InvalidInputException("Error: Species name, tree location, owner, municipality, or version cannot be null!");
		}
		Tree t = new Tree(species, height, diameter, id, location, owner, municipality, version);
		tm.addTree(t);
		PersistenceXStream.saveToXMLwithXStream(tm);
		return t;
	}
}
