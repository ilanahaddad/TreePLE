
<template>
  <div id="listtrees">
    
    <!--NAVIGATION TABLE -->
	<!--
    <table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/app'><span style="font-weight: bold; color: green">View Trees</span></a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/generatereport'>Generate Report</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/viewreportspreliminary'>View Surveys</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href= 'http://127.0.0.1:8087/#/forecast'>Forecast</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/editTree'>Edit Tree</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/moveTree'>Move Tree</a></td>
      </tr>
      <tr></tr>
    </table>
-->
    <hr>
    <h5><font color =green>Current Version #: {{this.curVersion}}</font></h5>
		<h6>Version Year: {{this.vYear}}</h6>

    <table align="center">
      <tr>
        <td>Change version:</td>
        <td>
          <select v-model="selectedVersion">
              <option disabled value="">Select Version</option>
              <option v-for="version in versions" >
                  <td>{{ version }}</td>
              </option>         
            </select>
        </td>
        <td>
           <button id="updateVersion" @click="updateVersion(selectedVersion)">Update</button>
        </td>
      </tr>
    </table>
<!-- CREATE MUNICIPALITY -->
    <hr>
		<h5><font color =green> Add Municipality </font></h5>
		<table align= "center">
			<td><input type ="text" v-model="newMun" placeHolder= "ex. Outremont"></td>
			<td><button align="center" @click="addMunicipality(newMun)"> Add Municipality </button> </td>
		</table>
    <p>
      <span v-if="errorCreateMun" style="color:red">{{errorCreateMun}} </span>
      <span v-if="errorMunicipalities" style="color:red">{{errorMunicipalities}} </span>
      <span v-if="successCreateMun" style="color:green">{{successCreateMun}} </span>
    </p>
		<hr>
    <!--LIST TREES BY -->

    <table align="center">
      <tr>
        <td style="padding: 0 10px 0 0"><h4><font color= green>List Trees By:</font></h4></td>

        <!-- ALL -->
        <td style="padding: 0 10px 0 15px;" id="All_select">
          All
        </td>
        <td>
          <button id="all_list" @click="listAll()">OK</button>
        </td>


        <!--SPECIES -->
        <td style="padding:0 10px 0 10px;" id="Species_select">
        <select v-model="selectedSpecies">
              <option disabled value="">Select Species</option>
              <option v-for="s in species" >
                  <td>{{ s }}</td>
              </option>         
            </select>
            <button id="species_list" @click="listBySpecies(selectedSpecies)">OK</button>
        </td>


        <!--MUNICIPALITY -->
        <td style="padding:0 10px 0 10px;" id="Municipality_select">
          <select v-model="selectedMunicipality">
              <option disabled value="">Select Municipality</option>
              <option v-for="municipality in municipalities" >
                  <td>{{ municipality.name }}</td>
              </option>         
            </select>
            <button id="municipality_list" @click="listByMunicipality(selectedMunicipality)">OK</button>
        </td>


        <!--STATUS -->
        <td style="padding:0 10px 0 10px;" id="Status_select">
          <select v-model="selectedStatus">
              <option disabled value="">Select Status</option>
              <option v-for="status in statuses" >
                  <td>{{ status }}</td>
              </option>         
            </select>
            <button id="status_list" @click="listByStatus(selectedStatus)">OK</button>
        </td>
        
        <!--LAND USE-->
        <td style="padding:0 10px 0 10px;" id="LandUse_select">
          <select v-model="selectedLandUse">
              <option disabled value="">Select Land Use</option>
              <option v-for="l in landUses" >
                  <td>{{ l }}</td>
              </option>         
            </select>
            <button id="status_list" @click="listByLandUse(selectedLandUse)">OK</button>
        </td>
        </td>
      </tr>
    </table>
    <br>

    <!--MAP STUFF -->
    <gmap-map
      :center="center"
      :zoom="13"
      style="width: 700px; height: 400px; margin-left:auto; margin-right:auto"
    >
      <gmap-marker
        :key="index"
        v-for="(m, index) in markers"
        :position="m.position"
        :clickable="true"
        :draggable="true"
        @click="center=m.position"
      ></gmap-marker>
    </gmap-map>
    
    <!-- TREE LIST (Accordion Version) -->
    <!--
    <div style="padding: 20px 0 0 0;">
    <div v-for="tree in trees" style="padding: 5px 0 0 0;">
    	<button class="accordion" v-on:click="showTreeData">Tree ID: {{ tree.id }} Lat: {{ tree.coordinates.latitude }} Long: {{ tree.coordinates.longitude }} </button>
			<div class="panel">
  			<panel>
  				Owner: {{ tree.owner.name }} </br>
  				Species : {{ tree.species }} </br>
  				Height : {{ tree.height }} </br>
  				Diameter : {{ tree.diameter }} </br>
  				Municipality : {{ tree.treeMunicipality.name }} </br>
  				Status : {{tree.status}} </br>
  			</panel>
			</div>
		</div>
		</div>
  -->
  
  <!-- TREE LIST (Normal version) -->
  
    <table align="center">
      <tr>
          <td style="padding:0 5px 0 5px;"><span style="font-weight:bold">ID</span></td>
          <td style="padding:0 5px 0 5px;"><span style="font-weight:bold">Species Type</span></td>
          <td style="padding:0 5px 0 5px;"><span style="font-weight:bold">Age</span></td>
          <td style="padding:0 5px 0 5px;"><span style="font-weight:bold">Height (in metres)</span></td>
          <td style="padding:0 5px 0 5px;"><span style="font-weight:bold">Diameter (in metres)</span></td>
          <td style="padding:0 5px 0 5px;"><span style="font-weight:bold">Municipality</span></td>
          <td style="padding:0 5px 0 5px;"><span style="font-weight:bold">Owner</span></td>
          <td style="padding:0 5px 0 5px;"><span style="font-weight:bold">Longitude</span></td>
          <td style="padding:0 5px 0 5px;"><span style="font-weight:bold">Latitude</span></td>
          <td style="padding:0 5px 0 5px;"><span style="font-weight:bold">Status</span></td>
          <td style="padding:0 5px 0 5px;"><span style="font-weight:bold">Land Use</span></td>
      </tr>
      <tr v-for="tree in trees" >
        <td style="padding:0 5px 0 5px;">{{ tree.id }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.species }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.age }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.height }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.diameter }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.treeMunicipality.name }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.ownerName }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.coordinates.longitude }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.coordinates.latitude }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.status }}</td>
        <td style="padding:0 5px 0 5px;">{{ tree.land}}</td>
      </tr>
    



    <!-- ... -->
      <!--
          //<td>
              //<input type="text" placeholder="Species">
          //</td>
    //<td>
              //<input type="text" placeholder="Latitude">
          //</td>
          //<td>
              //<input type="text" placeholder="Longitude">
          //</td>
          //<td>
              //<input type="text" placeholder="Status">
          //</td>
          //<td>
              //<button>Create tree</button>
          //</td>
      //</tr>
-->
    </table>
    <p>
      <span v-if="errorTree" style="color:red">{{errorTree}} </span>
      <span v-if="errorStatus" style="color:red">{{errorStatus}} </span>
      <span v-if="errorLandUse" style="color:red">{{errorLandUse}} </span>
      <span v-if="errorVersions" style="color:red">{{errorVersions}} </span>
      <span v-if="errorSpecies" style="color:red">{{errorSpecies}} </span>
    </p>
    </div>
</template>

<script src="./treelist.js">
</script>

<style>
.selection {
    border: none;
    text-align: center;
    cursor: pointer;
}

.selection:hover {
    background: green;
    color: white;
}
.accordion {
    background-color: #eee;
    color: #444;
    cursor: pointer;
    padding: 18px;
    width: 50%;
    border: none;
    text-align: left;
    outline: none;
    font-size: 15px;
    transition: 0.4s;
}

.active, .accordion:hover {
    background: green; 
    color:white;
}

.panel {
    padding: 0 18px;
    display: none;
    background-color: white;
    overflow: hidden;
}
</style>
