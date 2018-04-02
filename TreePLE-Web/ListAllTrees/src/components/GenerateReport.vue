<template>
  <div id="generatereport">
    <h6><font color ="#3366cc">Current Version: 1.0</font></h6> <!--CHANGE TO CURRENT VERSION -->
    <hr>
    <table align="center">
      <tr>
        <td style="padding: 0 10px 0 0"><h2>Generate Report:</h2></td>
        <td style="padding: 0 10px 0 15px;" id="All_select" class="selection">All</td>
        <td style="padding:0 10px 0 10px;" id="Species_select" class="selection">Species</td>
        <td style="padding:0 10px 0 10px;" id="Municipality_select" class="selection">Municipality
        </td>
        <td style="padding:0 10px 0 10px;">
          <td style="padding:0 10px 0 10px;" id="Status_select" class="selection">
            <select>
              <option value="Planted">Planted</option>
              <option value="ToBeCutdown">To be cutdown</option>
              <option value="Diseased">Diseased</option>
            </select>
          </td>
        </td>
        <td style="padding:0 10px 0 10px;">
          <td style="padding:0 10px 0 10px;" id="LandUse_select" class="selection">
            <select>
              <option value="Residential">Residential</option>
              <option value="NonResidential">Non-Residential</option>
            </select>
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

