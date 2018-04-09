package ca.mcgill.ecse321.TreePLE.dto;

public class ForecastDto {
	private String name;
	private String versionCreated;
	private int year;
	
	public ForecastDto() {
		
	}
	public ForecastDto(String name, String versionCreated, int year) {
		this.name = name;
		this.versionCreated = versionCreated;
		this.year = year;
	}
	
	public String getName() {
		return this.name;
	}
	public String getVersionCreated() {
		return this.versionCreated;
	}
	public int getYear() {
		return this.year;
	}

}
