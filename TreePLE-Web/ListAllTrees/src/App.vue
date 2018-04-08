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
    <img src="./assets/logo.png">
		
		<table align="center">
      <tr>
        <nav><router-link to="app">View Trees</router-link></nav>
        <td style="padding:0 5px 0 5px;"><router-link to="generateReport">Generate Report</router-link></td>
        <td style="padding:0 5px 0 5px;"><router-link to="viewReportspreliminary">View Surveys</router-link></td>
        <td style="padding:0 5px 0 5px;"><router-link to="Forecast">Forecast</router-link> </td>
        <td style="padding:0 5px 0 5px;"><router-link to="EditTree">Edit Trees</router-link></td>
        <td style="padding:0 5px 0 5px;"><router-link to="MoveTree">Move Tree</router-link></td>
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
import ViewReports from './components/ViewReports'
import ViewReportsPre from './components/ViewReportsPre'

export default {
  name: 'app',
  components: {
    ListTrees,
    GenerateReport,
    Report,
    Forecast,
    EditTree,
    MoveTree,
    ViewReports,
    ViewReportsPre
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
