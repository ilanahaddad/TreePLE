<style>
tr,td {
    padding: 15px;
}
</style>
<template>
  <div id="editTree">
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
				<td>
					<input type="text" v-model="municipality" placeHolder="Municipality">
				</td>
        <td>
					Land Use:
				</td>
				<td>
					<input type="text" v-model="landuse" placeHolder="Land Use">
				</td>
			</tr>
    </table>
 		<button align="center">Edit Tree Data</button>
  </div>
</template>

<script src="./editTree.js">
</script>

