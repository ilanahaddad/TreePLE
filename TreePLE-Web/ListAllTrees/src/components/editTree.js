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
      ownerName: '',
      species: '',
      age: '',
			diameter: '',
			height: '',
			municipality: '',
			landuse: ''
    }
  }, 
  methods: {
  		editTree: function(height, diameter, age, ownerName, species, landuse, municipality){
  			AXIOS.post('/updateTreeData/'+treeId, {}, {params: {newHeight: height, newDiameter: diameter, newAge: age, newOwnerName: ownerName, newSpecies: species, newLandUse: landuse, newMunicipality: municipality}}).then(response => {
					this.treeId=''
      		this.ownerName=''
      		this.species=''
      		this.age=''
      		this.diameter=''
      		this.long2=''
      		this.height''
      		this.municipality=''
      		this.landuse=''
				})
				.catch(e =>{
					var errorMsg= e.response.data.message
					console.log(errorMsg)
					this.errorEvent = errorMsg 
				})
  		}
  }  
}



