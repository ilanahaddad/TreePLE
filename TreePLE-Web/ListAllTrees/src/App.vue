<!--
<template>
  <div id="app">
    <img src="./assets/logo.png">
    <router-view></router-view>
  </div>
</template>
-->

<template>
  <div id="app">
    <img src="./assets/logo.png" align="center" height="262" width="170">
		
		<table align="center">
      <tr>
        <td style="padding:0 15px 0 15px;"><router-link to="app">View Trees</router-link></td>
        <td style="padding:0 15px 0 15px;"><router-link to="generateReport">Generate Report</router-link></td>
        <td style="padding:0 15px 0 15px;"><router-link to="viewReports">View Reports</router-link></td>
        <td style="padding:0 15px 0 15px;"><router-link to="viewsurveys">View Surveys</router-link></td>
        <td style="padding:0 15px 0 15px;"><router-link to="addTree">Add Tree</router-link></td>
				<td style="padding:0 15px 0 15px;"><router-link to="editTree">Edit Trees</router-link></td>
        <td style="padding:0 15px 0 15px;"><router-link to="moveTree">Move Tree</router-link></td>
        <td style="padding:0 15px 0 15px;"><router-link to="forecast">Forecast</router-link> </td>

      </tr>
      <tr></tr>
    </table>
	<router-view></router-view>
  </div>
</template>

<script>
import GenerateReport from './components/GenerateReport'
import Report from './components/Report'
import Forecast from './components/Forecast'
import ListTrees from './components/ListTrees'
import EditTree from './components/EditTree'
import MoveTree from './components/MoveTree'
import ViewSurveys from './components/ViewSurveys'
import ViewReports from '@/components/ViewReports'
import AddTree from '@/components/AddTree'

export default {
  name: 'app',
  components: {
    ListTrees,
    GenerateReport,
    Report,
    Forecast,
    EditTree,
    MoveTree,
    ViewSurveys,
    ViewReports,
    AddTree
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
