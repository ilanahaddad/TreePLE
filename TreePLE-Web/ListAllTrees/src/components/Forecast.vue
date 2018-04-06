<template>
  <div id="generatereport">
  	<table align="center">
      <tr>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/app'>View Trees</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/generatereport'>Generate Report</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/viewreportspreliminary'>View Surveys</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/forecast'><span style="font-weight: bold; color: green">Forecast</span></a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/edittree'>Edit Tree</a> - </td>
        <td style="padding:0 5px 0 5px;"><a href='http://127.0.0.1:8087/#/movetree'>Move Tree</a></td>
      </tr>
      <tr></tr>
    </table>
    <hr>
    <h2>New Forecast</h2>
    <br>
    <h5>Basic information</h5>
    <table align="center">
        <tr>
          <td style="padding:0 5px 0 5px;">Based on Version #</td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" placeholder="1.0">
          </td>
        </tr>
        <tr>
          <td style="padding:0 5px 0 5px;">Time lapse (in years)</td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" placeholder="5">
          </td>
        </tr>
        <tr>
          <td style="padding:0 5px 0 5px;">New Version #</td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" placeholder="1.1">
          </td>
        </tr>
    </table>
    <br>
    <br>
    <h5>Trees to be deleted</h5>
    <table align="center">
        <tr>
          <td style="padding:0 5px 0 5px;">Tree ID#</td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" placeholder="34">
          </td>
          <td><button class='forecastbutton'>OK</button></td>
        </tr>
    </table>
    <br>
    <p>The current trees to be deleted are given as follows:</p>
    <p>DYNAMIC LIST</p>
    <br>
    <br>
    <h5>Trees to be added</h5>
    <table align="center">
        <tr>
          <td style="padding:0 5px 0 5px;">Species</td>
          <td style="padding:0 5px 0 5px;">Height(m)</td>
          <td style="padding:0 5px 0 5px;">Diameter(m)</td>
          <td style="padding:0 5px 0 5px;">Municipality</td>
          <td style="padding:0 5px 0 5px;">Latitude</td>
          <td style="padding:0 5px 0 5px;">Longitude</td>
        </tr>
        <tr>
          <td style="padding:0 5px 0 5px;">
            <input type="text" placeholder="White Ash">
          </td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" placeholder="4.0">
          </td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" placeholder="1.0">
          </td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" placeholder="Outremont">
          </td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" placeholder="34.555">
          </td>
          <td style="padding:0 5px 0 5px;">
            <input type="text" placeholder="98.777">
          </td>
          <td><button class='forecastbutton'>OK</button></td>
        </tr>
    </table>
    <br>
    <p>The current trees to be added are given as follows:</p>
    <p>DYNAMIC LIST</p>
    <br>
    <button class='submitbutton'>Submit new forecast</button>
  </div>
</template>

<style>
.forecastbutton {
    border: none;
    border-width:thick;
    border-radius:2px;
    text-align: center;
    cursor: pointer;
}

.forecastbutton:hover {
    background: green;
    color: white;
}

.submitbutton {
    border: #193366;
    border-style: solid;
    border-width: thin;
    border-radius:4px;
    color:#193366;
    font-size: 30px;
    text-align: center;
    cursor: pointer;
}

.submitbutton:hover {
    background:#193366;
    color: white;
    border:#193366;
}
</style>


