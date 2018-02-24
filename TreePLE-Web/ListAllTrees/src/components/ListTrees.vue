<template>
  <div id="listtrees">
    <h2>List of Trees</h2>
    <table>
      <tr>
          <td>ID</td>
          <td>Species Type</td>
	  <td>Latitude</td>
	  <td>Longitude</td>
	  <td>Status</td>
      </tr>
      <tr v-for="tree in trees" >
        <td>{{ tree.id }}</td>
        <td>{{ tree.species }}</td>
        <td>{{ tree.longitude }}</td>
        <td>{{ tree.latitude }}</td>
        <td>{{ tree.status }}</td>
      </tr>
    <!-- ... -->
      <tr>
          <td>
              <input type="text" placeholder="Species">
          </td>
	  <td>
              <input type="text" placeholder="Latitude">
          </td>
          <td>
              <input type="text" placeholder="Longitude">
          </td>
          <td>
              <input type="text" placeholder="Status">
          </td>
          <td>
              <button>Create tree</button>
          </td>
      </tr>
    </table>
    <p>
      <span style="color:red">Error: Message text comes here</span>
    </p>
  </div>
</template>

<script src="./treelist.js">
</script>