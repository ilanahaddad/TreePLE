<template>
  <div id="viewreportspre">
  <!--
  	<table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/app'>View Trees</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/generatereport'>Generate Report</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/viewreportspreliminary'><span style="font-weight: bold; color: green">View Surveys</span></a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/forecast'>Forecast</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/edittree'>Edit Tree</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/movetree'>Move Tree</a></td>
      </tr>
      <tr></tr>
  </table>
  -->
  <hr>
  <p>Please enter desired tree ID to view reports:</p>
  <table align="center">
    <tr>
      <td style="padding:0 5px 0 5px;">Tree ID:</td>
      <td style="padding:0 5px 0 5px;">
        <input type="text" v-model= "treeId" placeHolder= "Tree ID">
      </td>
      <td>
        <button onclick="location.href = 'http://127.0.0.1:8087/#/viewreports'">OK</button>
      </td>
    </tr>
  </table>
 </div>
</template>

