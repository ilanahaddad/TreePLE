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

function LocationDto(lat, long) {
  this.latitude = lat
  this.longitude = long
}

export default {
  name: 'GenerateReport',
  data() {
    return {
      treeId: '',
			viewSurveysPre: true, 
			viewSurveysForTree: false,
			surveys: [],
			errorSurveys: ''
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
			viewSurveys: function(treeId) {
				AXIOS.get('/surveysForTree/'+treeId).then(response => {
					this.surveys.push(response.data)
				})
				.catch(e =>{
					var errorMsg= e.response.data.message
					console.log(errorMsg)
					this.errorEvent = errorMsg 
				})
				this.viewSurveysForTree=true
				this.viewSurveysPre=false
     	}

  }
}
