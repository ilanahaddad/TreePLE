package ca.mcgill.ecse321.TreePLE.service;

import java.util.List;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Survey;
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
			 Location location, User owner, 
			Municipality municipality, Version version, Tree.LandUse land) throws InvalidInputException{
		if(species==null || location==null || owner == null || municipality==null || version == null) {
			throw new InvalidInputException("Error: Species name, tree location, owner, municipality, or version cannot be null!");
		}
		Tree t = new Tree(species, height, diameter,location, owner, municipality, version);
		t.setLand(land);
		tm.addTree(t);
		PersistenceXStream.saveToXMLwithXStream(tm);
		return t;
	}

	public Location getLocationForTree(Tree t) {
		return t.getCoordinates();
	}
	public User getOwnerForTree(Tree t) {
		return t.getOwner();
	}
	public Municipality getMunicipalityForTree(Tree t) {
		return t.getTreeMunicipality();
	}
}
