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

function ReportDto(reporterName, reportDate, reportPerimeter, bioIndex, canopy, carbonSeq) {
  this.reporterName = reporterName
  this.reportDate = reportDate
  this.reportPerimeter = reportPerimeter
  this.biodiversityIndex = bioIndex
  this.canopy = canopy
  this.carbonSequestration = carbonSeq
}

function LocationDto(lat, long) {
  this.latitude = lat
  this.longitude = long
}

export default {
  name: 'GenerateReport',
  data() {
    return {
      reporterName: '',
      reportDate: '',
      lat1: '',
      long1: '',
      lat2: '',
      long2: '',
      lat3: '',
      long3: '',
      lat4: '',
      long4: '',
      curReport: '',
      reports: [],
      showReport: false,
      showGenerate: true
    }
  },
  /*created : function(){
      var lat1=1
      var long1=1
      var lat2=1
      var long2=2
      var lat3=2
      var long3=2
      var lat4=2
      var long4=1
      var loc1= new LocationDto(lat1, long1)
      var loc2= new LocationDto(lat2, long2)
      var loc3= new LocationDto(lat3, long3)
      var loc4= new LocationDto(lat4, long4)
      var rPerim= [loc1, loc2, loc3,loc4]
    const r= new ReportDto('Diana', '2018-02-02', rPerim, '1.2', '1.3', '1.4')
    this.curReport= r
    
  },*/
  methods: {
    generateReport: function(reporterName, reporterDate, lat1, long1, lat2, long2, lat3, long3, lat4, long4) {
      AXIOS.post('/newReport/' + reporterName, {}, {
          params: {
            reportDate: reportDate,
            lat1: lat1,
            long1: long1,
            lat2: lat2,
            long2: long2,
            lat3: lat3,
            long3: long3,
            lat4: lat4,
            long4: long4
          }
        }).then(response => {
          this.reports.push(response.data)
          curReport = response.data
          this.reporterName = ''
          this.reportDate = ''
          this.lat1 = ''
          this.long1 = ''
          this.lat2 = ''
          this.long2 = ''
          this.lat3 = ''
          this.long3 = ''
          this.lat4 = ''
          this.long4 = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorEvent = errorMsg
        })
      this.showReport = true
      this.showGenerate = false
    }
  }
}