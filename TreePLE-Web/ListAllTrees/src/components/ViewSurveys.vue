
<template>
  <div id="viewsurveys">
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
	<div v-if="viewSurveysPre">
  <p>Please enter desired tree ID to view reports:</p>
  <table align="center">
    <tr>
      <td style="padding:0 5px 0 5px;">Tree ID:</td>
      <td style="padding:0 5px 0 5px;">
        <input type="text" v-model= "treeId" placeHolder= "ex. 3">
      </td>
      <td>
        <button @click="viewSurveys(treeId)">View Surveys</button>
      </td>
    </tr>
  </table>
	</div>
	<div v-if="viewSurveysForTree">

  <h2>Report History for tree ID: {{treeId}} </h2></br>
  <hr>
 	 	<div v-for="survey in surveys">
				<h6> Date : {{survey.reportDate}} </h6>
				<h6> Surveyor: {{survey.surveyor}} </h6>
				<h6>Status: {{survey.tree.status}} </h6>

				<hr>
		</div>
	</div>
 </div>
</template>
<script src="./viewSurveys.js">
</script>

