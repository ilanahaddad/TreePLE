
<template>
  <div id="listtrees">
    <table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/app'><span style="font-weight: bold; color: green">View Trees</span></a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/generatereport'>Generate Report</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/viewreportspreliminary'>View Reports</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/forecast'>Forecast</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/editTree'>Edit Tree</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/moveTree'>Move Tree</a></td>
      </tr>
      <tr></tr>
    </table>
    <h6><font color ="#3366cc">Current Version: 1.0</font></h6> <!--CHANGE TO CURRENT VERSION -->
    <hr>
    <table align="center">
      <tr>
        <td style="padding: 0 10px 0 0"><h2>List Trees By:</h2></td>
        <td style="padding: 0 10px 0 15px;" id="All_select">All</td>
        <td style="padding:0 10px 0 10px;" id="Species_select">Species</td>
        <td style="padding:0 10px 0 10px;" id="Municipality_select">Municipality
        </td>
        <td style="padding:0 10px 0 10px;">
          <td style="padding:0 10px 0 10px;" id="Status_select">
            <select>
              <option value="Planted">Planted</option>
              <option value="ToBeCutdown">To be cutdown</option>
              <option value="Diseased">Diseased</option>
            </select>
            <button class='selection'>OK</button>
          </td>
        </td>
        <td style="padding:0 10px 0 10px;">
          <td style="padding:0 10px 0 10px;" id="LandUse_select">
            <select>
              <option value="Residential">Residential</option>
              <option value="NonResidential">Non-Residential</option>
            </select>
            <button class='selection'>OK</button>
          </td>
        </td>
      </tr>
      <tr></tr>
    </table>
    <br>
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
    <!--
    <table align="center">
      <tr>
          <td>ID</td>
          <td>Species Type</td>
          <td>Height (in metres)</td>
          <td>Diameter (in metres)</td>
          <td>Municipality</td>
          <td>Owner</td>
          <td>Longitude</td>
          <td>Latitude</td>
          <td>Status</td>
      </tr>
      <tr v-for="tree in trees" >
        <td>{{ tree.id }}</td>
        <td>{{ tree.species }}</td>
        <td>{{ tree.height }}</td>
        <td>{{ tree.diameter }}</td>
        <td>{{ tree.treeMunicipality.name }}</td>
        <td>{{ tree.owner.name }}</td>
        <td>{{ tree.coordinates.longitude }}</td>
        <td>{{ tree.coordinates.latitude }}</td>
        <td>{{ tree.status }}</td>
      </tr>
    -->
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
      <span v-if="errorTree" style="color:red">Error: {{errorTree}} </span>
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
