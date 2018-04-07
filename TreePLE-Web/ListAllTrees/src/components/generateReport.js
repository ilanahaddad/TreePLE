import axios from 'axios'
import router from '../router'
var config = require('../../config')


var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
/*function ReportDto(reporterName, reportDate, lat1, long1, lat2, long2, lat3, long3, lat4, long4, biodiversityIndex, canopy, carbonSequestration){
	this.reporterName=reporterName
	this.reportDate=reportDate
	this.lat1=lat1
  this.long1=long1
  this.lat2=lat2
  this.long2=long2
  this.lat3=lat3
  this.long3=long3
  this.lat4=lat4
  this.long4=long4
  this.biodiversityIndex=biodiversityIndex
  this.canopy=canopy
  this.carbonSequestration=carbonSequestration
	
}*/

export default {
  name: 'ListTrees',
  data () {
    return {
      reporterName: '',
      reportDate:'',
      lat1:'',
      long1:'',
      lat2:'',
      long2:'',
      lat3:'',
      long3:'',
      lat4:'',
      long4:'',
      curReport:'',
      reports: []
    }
  },
  methods: {
			generateReport: function(reporterName, reporterDate, lat1, long1, lat2, long2, lat3, long3, lat4, long4) {
				AXIOS.post('/newReport/'+reporterName, {}, {params: {reportDate: reportDate, lat1: lat1, long1: long1, lat2: lat2, long2: long2, lat3: lat3, long3: long3, lat4: lat4, long4: long4}}).then(response => {
					this.reports.push(response.data)
					curReport=response.data
					this.reporterName=''
      		this.reportDate=''
      		this.lat1=''
      		this.long1=''
      		this.lat2=''
      		this.long2=''
      		this.lat3=''
      		this.long3=''
      		this.lat4=''
      		this.long4=''
				})
				.catch(e =>{
					var errorMsg= e.response.data.message
					console.log(errorMsg)
					this.errorEvent = errorMsg 
				})
				router.push({name:"Report"})
     	}
  }
}


