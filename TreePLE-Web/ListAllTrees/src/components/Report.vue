<template>
  <div id="report">
  	<table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/app'>View Trees</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/generatereport'><span style="font-weight: bold; color: green">Generate Report</span></a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/viewreportspreliminary'>View Surveys</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/forecast'>Forecast</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/edittree'>Edit Tree</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/movetree'>Move Tree</a></td>
      </tr>
      <tr></tr>
  </table>
  <hr>
  <h2>Sustainability Report</h2>
  <br>
  <h5>Basic information</h5>
  <table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;">Location Perimeter</td>
        <td style="padding:0 5px 0 5px;">({{curReport.lat1}}, {{curReport.long1}}) ({{curReport.lat2}},{{curReport.long2}}) ({{curReport.lat3}}, {{curReport.lat3}}) ({{curReport.lat4}}, {{curReport.lat4}}) </td>
      </tr>
      <tr>
        <td style="padding:0 5px 0 5px;">Reporter</td>
        <td style="padding:0 5px 0 5px;">{{curReport.reporterName}}</td>
      </tr
      <tr>
        <td style="padding:0 5px 0 5px;">Date</td>
        <td style="padding:0 5px 0 5px;">{{curReport.reportDate}}</td>
      </tr>
  </table>
  <br>
  <h5>Computed values</h5>
  <table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;">Biological Index</td>
        <td style="padding:0 5px 0 5px;">{{curReport.biodiversityIndex}}</td>
      </tr>
      <tr>
        <td style="padding:0 5px 0 5px;">Canopy Size</td>
        <td style="padding:0 5px 0 5px;">{{curReport.canopy}}</td>
      </tr>
      <tr>
        <td style="padding:0 5px 0 5px;">Carbon Sequestration Index</td>
        <td style="padding:0 5px 0 5px;">{{curReport.carbonSequestration}}</td>
      </tr>
  </table>
 </div>
</template>
<!--
<script src="./generateReport.js">
</script>
-->
