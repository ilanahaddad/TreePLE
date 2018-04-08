
<template>
  <div id="app">
    <img src="./assets/logo.png">
    <router-view></router-view>
  </div>
</template>

<!--
<template>
  <div id="app">
    <img src="./assets/logo.png">
    <router-view></router-view>
		<table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;"><router-link to="ListTrees">View Trees</router-link></td>
        <td style="padding:0 5px 0 5px;"><router-link to="GenerateReport">GenerateReport</router-link></td>
        <td style="padding:0 5px 0 5px;"><router-link to="GenerateReport">ViewSurveys</router-link></td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/forecast'><span style="font-weight: bold; color: green">Forecast</span></a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/edittree'>Edit Tree</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/movetree'>Move Tree</a></td>
      </tr>
      <tr></tr>
    </table>
  </div>
</template>
-->
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
