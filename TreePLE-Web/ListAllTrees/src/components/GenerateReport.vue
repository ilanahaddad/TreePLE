<template>
  <div id="generatereport">
		<div v-if="showGenerate">
	<!--
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
	-->
    <hr>
    <p>
      <span v-if="errorGenerateReport" style="color:red">{{errorGenerateReport}} </span>
    </p>
    <h4>Perimeter Selection</h4>
    <table align="center">
    	<tr>
    		<td style="padding:0 5px 0 5px;">Reference Point #</td>
    		<td style="padding:0 5px 0 5px;">Latitude</td>
    		<td style="padding:0 5px 0 5px;">Longitude</td>
    	</tr>
    	<tr>
    		<td style="padding:0 5px 0 5px;">1</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" v-model= "lat1" placeholder="latitude">
    		</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" v-model= "long1" placeholder="longitude">
    		</td>
    	</tr>
    	<tr>
    		<td style="padding:0 5px 0 5px;">2</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" v-model= "lat2" placeholder="latitude">
    		</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" v-model= "long2" placeholder="longitude">
    		</td>
    	</tr>
    	<tr>
    		<td style="padding:0 5px 0 5px;">3</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" v-model= "lat3" placeholder="latitude">
    		</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" v-model= "long3" placeholder="longitude">
    		</td>
    	</tr>
    	<tr>
    		<td style="padding:0 5px 0 5px;">4</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" v-model= "lat4" placeholder="latitude">
    		</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" v-model= "long4" placeholder="longitude">
    		</td>
    	</tr>
    </table>
    <br>
    <table align="center">
    <tr>
    	<td style="padding:0 5px 0 5px;">Reporter Name</td>
    	<td style="padding:0 5px 0 5px;">
			<input type="text" v-model= "reporterName" placeholder="John Doe">
    	</td>
    </tr>

  	<tr>
    	<td style="padding:0 5px 0 5px;">Report Date</td>
    	<td style="padding:0 5px 0 5px;">
			<input type="date" v-model="reportDate" placeholder="YYYY-MM-DD">
    	</td>
    </tr>
    </table>
    <br>
    <br>
<!--
    <button class="submit_button" onclick="location.href = 'http://127.0.0.1:8087/#/report'">GENERATE REPORT</button>
-->
		<button class="submit_button" v-on:click="generateReport(reporterName, reportDate, lat1, long1, lat2, long2, lat3, long3, lat4, long4)" >GENERATE REPORT</button>
	</div>

<!-- GENERATED REPORT PAGE- ONLY APPEARS ONCE YOU CLICK SUBMIT BUTTON-->
	<div v-if="showReport">
  <h3>Sustainability Report</h3>
  <br>
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

<script src="./generateReport.js">
</script>

<style>

.submit_button {
    border: none;
    text-align: center;
    cursor: pointer;
    font-style: bold;
}

.submit_button:hover {
    background: green;
    color: white;
}
</style>
