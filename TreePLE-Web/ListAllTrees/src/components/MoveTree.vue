<template>
  <div id="movetree">
<!--
  	<table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/app'>View Trees</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/generatereport'>Generate Report</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/viewreportspreliminary'>View Surveys</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/forecast'>Forecast</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/edittree'>Edit Tree</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/movetree'><span style="font-weight: bold; color: green">Move Tree</span></a></td>
      </tr>
      <tr></tr>
  </table>
-->
  <hr>
  <p>
    <span v-if="errorMoveTree" style="color:red">{{errorMoveTree}} </span>
  </p>
  <h2>Move Tree</h2>
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
    <button align="center" @click="moveTree(treeId, newLat, newLong)">Move Tree</button>
 </div>
</template>
<script src="./moveTree.js">
</script>
<style>
tr,td {
    padding: 15px;
}
