<template>
  <div id="edittree">
  	<table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/app'>View Trees</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/generatereport'>Generate Report</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/viewreportspreliminary'>View Reports</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/forecast'>Forecast</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/edittree'><span style="font-weight: bold; color: green">Edit Tree</span></a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/movetree'>Move Tree</a></td>
      </tr>
      <tr></tr>
  </table>
  <hr>
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

<style>
tr,td {
    padding: 15px;
}
</style>

