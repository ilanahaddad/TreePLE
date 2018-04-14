
<template>
    <div id= "viewreports">
  <h2>Report History for Version {{version}} </h2></br>
    <div v-for="curReport in reports">
    <h5>Basic information</h5>
 
    <div style="text-align:center">Location Perimeter:</div>
	<div v-for="location in curReport.reportPerimeter">
        ({{location.latitude}}, {{location.longitude}})
	</div>
    <table align= "center">
      <tr>
        <td style="padding:0 5px 0 5px;">Reporter:</td>
        <td style="padding:0 5px 0 5px;">{{curReport.reporterName}}</td>
      </tr
      <tr>
        <td style="padding:0 5px 0 5px;">Date:</td>
        <td style="padding:0 5px 0 5px;">{{curReport.reportDate}}</td>
      </tr>
    </table>
    <br>
    <h5>Computed values</h5>
    <table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;">Biodiversity Index:</td>
        <td style="padding:0 5px 0 5px;">{{curReport.biodiversityIndex}}</td>
      </tr>
      <tr>
        <td style="padding:0 5px 0 5px;">Canopy Size:</td>
        <td style="padding:0 5px 0 5px;">{{curReport.canopy}}</td>
      </tr>
      <tr>
        <td style="padding:0 5px 0 5px;">Carbon Sequestration Index:</td>
        <td style="padding:0 5px 0 5px;">{{curReport.carbonSequestration}}</td>
      </tr>
    </table>

  </div>
</div>
</template>
<script src="./viewReports.js">
</script>

