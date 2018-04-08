import axios from 'axios'
var config = require('../../config')

//import router from '../router'

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'EditTree',
	//add comma
  data () {
    return {
      treeId: '',
      newLat: '',
      newLong: ''
    }
  }, 
  methods: {
  		moveTree: function(treeId, newLat, newLong){
  			AXIOS.post('/moveTree/'+treeId, {}, {params: {latitude: newLat, longitude: newLong}}).then(response => {
					this.treeId=''
      		this.newLat=''
      		this.newLong=''
				})
				.catch(e =>{
					var errorMsg= e.response.data.message
					console.log(errorMsg)
					this.errorEvent = errorMsg 
				})
  		}
  }  
}



