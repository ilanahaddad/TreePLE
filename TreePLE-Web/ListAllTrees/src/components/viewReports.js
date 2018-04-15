import axios from 'axios'
//import router from '../router'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: {
    'Access-Control-Allow-Origin': frontendUrl
  }
})

function SustainabilityReportDto(reporterName, reportDate, reportPerimeter, biodiversityIndex, canopy, carbonSequestration) {
  this.reporterName = reporterName
  this.reportDate = reportDate
  this.reportPerimeter = reportPerimeter
  this.biodiversityIndex = biodiversityIndex
  this.canopy = canopy
  this.carbonSequestration = carbonSequestration
}

function LocationDto(lat, long) {
  this.latitude = lat
  this.longitude = long
}

export default {
  name: 'GenerateReport',
  data() {
    return {
      reports: [],
      version: '',
      errorVersion: '',
      errorReports: ''
    }
  },
  created : function(){
    AXIOS.get('/versionNumber')
    .then(response => {
        this.version=response.data
    })
    .catch(e=> {
        this.errorVersion=e;
    });
    AXIOS.get('/reports')
    .then(response=>{
        this.reports=response.data
    })
    .catch(e=> {
        this.errorReports=e;
    })
  }
}
