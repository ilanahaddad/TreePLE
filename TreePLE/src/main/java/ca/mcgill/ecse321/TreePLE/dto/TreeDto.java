package ca.mcgill.ecse321.TreePLE.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse321.TreePLE.model.Location;
import ca.mcgill.ecse321.TreePLE.model.Municipality;
import ca.mcgill.ecse321.TreePLE.model.Survey;
import ca.mcgill.ecse321.TreePLE.model.Tree;
import ca.mcgill.ecse321.TreePLE.model.User;
import ca.mcgill.ecse321.TreePLE.model.Tree.LandUse;
import ca.mcgill.ecse321.TreePLE.model.Tree.Status;

public class TreeDto {

	private String species;
	private double height;
	//private Status status;
	private double diameter;
	private LandUse land;
	private LocationDto coordinates;
	private String owner;
	private int age;
	private MunicipalityDto treeMunicipality;
	//private int id;

	public TreeDto() {
	}

/*	public TreeDto(String name) {
		this(name, Date.valueOf("1971-01-01"), Time.valueOf("00:00:00"), Time.valueOf("23:59:59"));
	}*/

	public TreeDto(String species, double height, double diameter, int age,
			LocationDto coordinates, String owner, MunicipalityDto treeMunicipality, 
			 LandUse land){
		this.species = species;
		this.height = height;
		this.diameter = diameter;
		this.land = land;
		this.coordinates = coordinates;
		this.owner = owner;
		this.treeMunicipality = treeMunicipality;
		//this.id = id;
		this.age = age;
	}

	public String getSpecies()
	{
		return species;
	}

	public double getHeight()
	{
		return height;
	}

/*	public Status getStatus()
	{
		return status;
	}*/

	public double getDiameter()
	{
		return diameter;
	}

	public LandUse getLand()
	{
		return land;
	}

	/*public int getId()
	{
		return id;
	}*/
	public LocationDto getCoordinates()
	{
		return coordinates;
	}

	public String getOwner()
	{
		return owner;
	}
	public MunicipalityDto getTreeMunicipality()
	{
		return treeMunicipality;
	}

/*	public Version getVersion(int index)
	{
		Version aVersion = versions.get(index);
		return aVersion;
	}*/
	public int getAge() {
		return age;
	}
	public void setMunicipality(MunicipalityDto mun) {
		this.treeMunicipality = mun;
		
	}

	public void setLocation(LocationDto location) {
		this.coordinates = location;
		
	}

/*	public void setUser(UserDto owner) {
		this.owner=owner;
		
	}*/
}
