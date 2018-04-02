<style>
tr,td {
    padding: 15px;
}
</style>
<template>
  <div id="moveTree">
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
					New Latitude:
				</td>
				<td>
					<input type="text" v-model= "newLat" placeHolder= "Latitude">
				</td>
				<td>
					New Longitude:
				</td>
				<td>
					<input type="text" v-model= "newLong" placeHolder= "Longitude">
				</td>
			</tr>
    </table>
 		<button align="center">Move Tree</button>
  </div>
</template>

<script src="./moveTree.js">
</script>

