
<template>
  <div id="listtrees">
    <table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;">Generate Report - </td>
        <td style="padding:0 5px 0 5px;">View Reports - </td>
        <td style="padding:0 5px 0 5px;">New Forecast - </td>
        <td style="padding:0 5px 0 5px;">Edit Tree - </td>
        <td style="padding:0 5px 0 5px;">Move Tree</td>
      </tr>
      <tr></tr>
    </table>
    <h6><font color = "blue">Current Version: 1.0</font></h6> <!--CHANGE TO CURRENT VERSION -->
    <hr>
    <table align="center">
      <tr>
        <td><h2>List Trees By:</h2></td>
        <td style="padding:0 15px 0 15px;"><button id="All_Button" class="button">All</button></td>
        <td style="padding:0 15px 0 15px;">
          <button id="Species_Button" class="button">Species</button></td>
        </td>
        <td style="padding:0 15px 0 15px;">
          <button id="Municipality_Button" class="button">Municipality</button></td>
        </td>
        <td style="padding:0 15px 0 15px;">
          <button id="Status_Button" class="button">Status</button></td>
        </td>
        <td style="padding:0 15px 0 15px;">
          <button id="LandUse_Button" class="button">Land Use</button></td>
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

<script src="./treelist.js">
</script>

<style>
.button {
    border: none;
    text-align: center;
    cursor: pointer;
}

.button:hover {
    background: green;
    color: white;
}
</style>
