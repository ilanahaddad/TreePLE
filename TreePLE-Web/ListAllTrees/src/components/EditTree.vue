<template>
  <div id="edittree">
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
  <p>
    <span v-if="errorEditTree" style="color:red">{{errorEditTree}} </span>
    <span v-if="successEditTree" style="color:green">{{successEditTree}} </span>
  </p>
  <h2>Edit Tree</h2>
    <table align="center">
      <tr>
        <td>
          Tree ID:
        </td>
        <td>
          <input type="text" v-model= "treeId" placeHolder= "Tree ID">
        </td>
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
    </table>
    <button align="center" @click= "editTree(treeId, height, diameter, age, ownerName, species, landuse, municipality)">Edit Tree Data</button>
 </div>
</template>
<script src="./editTree.js">
</script>
<style>
tr,td {
    padding: 15px;
}
</style>

