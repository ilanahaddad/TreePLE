<!--
String species, Double height, Doube diameter, Drop down municipaity,
Double latitude, Double longitude , String owner, int age, drop down landuse
-->
<template>
  <div id="addtree">
	<!--
  	<table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/app'>View Trees</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/generatereport'>Generate Report</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/viewreportspreliminary'>View Surveys</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/forecast'>Forecast</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/edittree'><span style="font-weight: bold; color: green">Edit Tree</span></a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/movetree'>Move Tree</a></td>
      </tr>
      <tr></tr>
  </table>
-->
  <hr>
  <h2>Add Tree</h2>
    <table align="center">
      <tr align="center">
        <td>
          Owner:
        </td>
        <td>
          <input type="text" v-model="ownerName" placeHolder="Name">
        </td>
      </tr>
      <tr>
        <td>
          Species Name:
        </td>
        <td>
          <input type="text" v-model="species" placeHolder="Species">
        </td>
        <td>
          Age: 
        </td>
        <td>
          <input type="text" v-model="age" placeHolder="Age (yrs)">
        </td>
      </tr>
      <tr>
        <td>
          Diameter:
        </td>
        <td>
          <input type="text" v-model="diameter" placeHolder="Diameter (m)">
        </td>
        <td>
          Height:
        </td>
        <td>
          <input type="text" v-model="height" placeHolder="Height (m)">
        </td> 
      </tr>
      <tr>
        <td>
          Municipality:
        </td>
        <td style="padding:0 10px 0 10px;" id="Municipality_select">
          <select v-model="municipality">
              <option disabled value="">Select Municipality</option>
              <option v-for="municipality in municipalities" >
                  <td>{{ municipality.name }}</td>
              </option>         
            </select>
        </td>
        <td>
          Land Use:
        </td>
        <td style="padding:0 10px 0 10px;" id="LandUse_select">
          <select v-model="landuse">
              <option disabled value="">Select Land Use</option>
              <option v-for="l in landUses" >
                  <td>{{ l }}</td>
              </option>         
            </select>
        </td>
        </td>
      </tr>
      <tr>
        <td>
          Latitude:
        </td>
        <td>
          <input type="text" v-model= "newLat" placeHolder= "Latitude">
        </td>
        <td>
          Longitude:
        </td>
        <td>
          <input type="text" v-model= "newLong" placeHolder= "Longitude">
        </td>
      </tr>
    </table>
    <button align="center" @click= "createTree(species, height, diameter, municipality, newLat, newLong, ownerName, age, landuse)"> Add Tree</button>
 </div>
</template>
<script src="./addTree.js">
</script>
<style>
tr,td {
    padding: 15px;
}
</style>

