package ca.mcgill.ecse321.TreePLE.dto;

public class MunicipalityDto {
	private String name;

	public MunicipalityDto() {

	}
	public MunicipalityDto(String name) {
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
}
