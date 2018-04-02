<template>
  <div id="generatereport">
  	<table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/app'>View Trees</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/generatereport'><span style="font-weight: bold; color: green">Generate Report</span></a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/viewreportspreliminary'>View Reports</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/forecast'>Forecast</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/edittree'>Edit Tree</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/movetree'>Move Tree</a></td>
      </tr>
      <tr></tr>
    </table>
    <hr>
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
    			<input type="text" placeholder="latitude">
    		</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" placeholder="longitude">
    		</td>
    	</tr>
    	<tr>
    		<td style="padding:0 5px 0 5px;">2</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" placeholder="latitude">
    		</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" placeholder="longitude">
    		</td>
    	</tr>
    	<tr>
    		<td style="padding:0 5px 0 5px;">3</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" placeholder="latitude">
    		</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" placeholder="longitude">
    		</td>
    	</tr>
    	<tr>
    		<td style="padding:0 5px 0 5px;">4</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" placeholder="latitude">
    		</td>
    		<td style="padding:0 5px 0 5px;">
    			<input type="text" placeholder="longitude">
    		</td>
    	</tr>
    </table>
    <br>
    <table align="center">
    <tr>
    	<td style="padding:0 5px 0 5px;">Reporter Name</td>
    	<td style="padding:0 5px 0 5px;">
			<input type="text" placeholder="John Doe">
    	</td>
    </tr>
    <tr>
    	<td style="padding:0 5px 0 5px;">Version #</td>
    	<td style="padding:0 5px 0 5px;">
			<input type="text" placeholder="1.0">
    	</td>
    </tr>
    </table>
    <br>
    <br>
    <button class="submit_button" onclick="location.href = 'http://127.0.0.1:8087/#/report'">GENERATE REPORT</button>
    </div>
</template>

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
