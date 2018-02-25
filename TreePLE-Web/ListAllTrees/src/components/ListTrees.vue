<template>
  <div id="listtrees">
    <h2>List of Trees</h2>
    <table>
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
