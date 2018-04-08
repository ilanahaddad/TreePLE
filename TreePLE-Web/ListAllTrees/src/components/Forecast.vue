<template>
  <div id="generatereport">
  	<table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/app'>View Trees</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/generatereport'>Generate Report</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/viewreportspreliminary'>View Surveys</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/forecast'><span style="font-weight: bold; color: green">Forecast</span></a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/edittree'>Edit Tree</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/movetree'>Move Tree</a></td>
      </tr>
      <tr></tr>
    </table>
    <hr>
    <h2>New Forecast</h2>
    <br>
    <h5>Basic information</h5>
    <table align="center">
        <tr>
        <!--MAKE A DROP DOWN-->
          <td style="padding:0 5px 0 5px;">Based on Version #</td>
          <!--
          <td style="padding:0 5px 0 5px;">
            <input type="text" v-model="versionBase" placeholder="ex. 1.0">
          </td>
          -->
          <td style="padding:0 10px 0 10px;" id="Version_select">
          <select v-model="versionBase">
              <option disabled value="">Select Base Version</option>
              <option v-for="v in versions" >
                  <td>{{ v }}</td>
              </option>         
           </select>
       		</td>
        </tr>
        <tr>
          <td style="padding:0 5px 0 5px;">Time lapse (in years)</td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" v-model= "timeLapse" placeholder="ex. 5">
          </td>
        </tr>
        <tr>
          <td style="padding:0 5px 0 5px;">Your Name</td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" v-model= "forecaster" placeholder="ex. Diana">
          </td>
        </tr>
    </table>
    <br>
    <br>
    <h5>Trees to be deleted</h5>
    <table align="center">
        <tr>
          <td style="padding:0 5px 0 5px;">Tree ID#</td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" v-model= "deleteTreeId" placeholder="ex. 34">
          </td>
          <td><button class='forecastbutton' @click= "deleteTree(deleteTreeId)" >Delete Tree</button></td>
        </tr>
    </table>
    <br>
    <p>The current trees to be deleted are given as follows:</p>
    <div v-for="id in deleteTreeIdList">
    	{{ id }}
    </div>
    <br>
    <br>
    <h5>Trees to be added</h5>
    <table align="center">
        <tr>
          <td style="padding:0 5px 0 5px;">Species</td>
          <td style="padding:0 5px 0 5px;">Height(m)</td>
          <td style="padding:0 5px 0 5px;">Diameter(m)</td>
          <td style="padding:0 5px 0 5px;">Municipality</td>
          <td style="padding:0 5px 0 5px;">Latitude</td>
          <td style="padding:0 5px 0 5px;">Longitude</td>
          <td style="padding:0 5px 0 5px;">Owner Name</td>
          <td style="padding:0 5px 0 5px;">Age</td>
          <td style="padding:0 5px 0 5px;">Land Use</td>
        </tr>
        <tr>
        
          <td style="padding:0 5px 0 5px;">
            <input type="text" v-model= "newSpecies"placeholder="ex. White Ash">
          </td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" v-model="newTreeHeight" placeholder="ex. 4.0">
          </td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" v-model="newTreeDiameter" placeholder="ex. 1.0">
          </td>
          <td style="padding:0 10px 0 10px;" id="Municipality_select">
          <select v-model="newMunicipality">
              <option disabled value="">Select Municipality</option>
              <option v-for="municipality in municipalities" >
                  <td>{{ municipality.name }}</td>
              </option>         
            </select>
       		</td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" v-model="newLat" placeholder="ex. 34.555">
          </td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" v-model="newLong" placeholder="ex. 98.777">
          </td>
           <td style="padding:0 5px 0 5px;">
            <input type="text" v-model="newOwner" placeholder="ex. Ilana">
          </td>
           <td style="padding:0 5px 0 5px;">
            <input type="text" v-model="newAge" placeholder="ex. 1">
          </td>
             <td style="padding:0 10px 0 10px;" id="LandUse_select">
          <select v-model="newLanduse">
              <option disabled value="">Select Land Use</option>
              <option v-for="l in landUses" >
                  <td>{{ l }}</td>
              </option>         
            </select>
        	</td>
          <td><button class='forecastbutton' @click= "addTree(newSpecies, newTreeHeight, newTreeDiameter, newLat, newLong, newOwner, newMunicipality, newAge, newLanduse)">Add Tree</button></td>
        </tr>
    </table>
    <br>
    <p>The current trees to be added are given as follows:</p>
    <p>
    <table align="center">
      <tr>
          <td style="padding:0 5px 0 5px;">ID</td>
          <td style="padding:0 5px 0 5px;">Species Type</td>
          <td style="padding:0 5px 0 5px;">Age</td>
          <td style="padding:0 5px 0 5px;">Height (in metres)</td>
          <td style="padding:0 5px 0 5px;">Diameter (in metres)</td>
          <td style="padding:0 5px 0 5px;">Municipality</td>
          <td style="padding:0 5px 0 5px;">Owner</td>
          <td style="padding:0 5px 0 5px;">Longitude</td>
          <td style="padding:0 5px 0 5px;">Latitude</td>
          <td style="padding:0 5px 0 5px;">Status</td>
          <td style="padding:0 5px 0 5px;">Land Use</td>
      </tr>
      <tr v-for="tree in newTrees" >
        <td style="padding:0 5px 0 5px;">{{ tree.id }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.species }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.age }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.height }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.diameter }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.treeMunicipality.name }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.owner.name }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.coordinates.longitude }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.coordinates.latitude }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.status }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.landUse}}</td>
      </tr>
    </table>
    </p>
    <br>
    <button class='submitbutton' @click= "createForecast(forecaster, versionBase, timeLapse)" >Submit new forecast</button>
  </div>
</template>
<script src="./forecast.js">
</script>

<style>
.forecastbutton {
    border: none;
    border-width:thick;
    border-radius:2px;
    text-align: center;
    cursor: pointer;
}

.forecastbutton:hover {
    background: green;
    color: white;
}

.submitbutton {
    border: #193366;
    border-style: solid;
    border-width: thin;
    border-radius:4px;
    color:#193366;
    font-size: 30px;
    text-align: center;
    cursor: pointer;
}

.submitbutton:hover {
    background:#193366;
    color: white;
    border:#193366;
}
</style>


