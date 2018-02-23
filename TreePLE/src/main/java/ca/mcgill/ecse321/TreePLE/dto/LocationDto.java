package ca.mcgill.ecse321.TreePLE.dto;

import ca.mcgill.ecse321.TreePLE.model.Tree;

public class LocationDto {
	  private double latitude;
	  private double longitude;

	  
	  public LocationDto() {
		  
	  }
	  //dunno if treeInLocation should be in here
	  public LocationDto(double latitude, double longitude) {
		  this.latitude=latitude;
		  this.longitude=longitude;

	  }
	  public double getLatitude()
	  {
	    return latitude;
	  }

	  public double getLongitude()
	  {
	    return longitude;
	  }

}	
