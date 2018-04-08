import axios from 'axios'
var config = require('../../config')

//import router from '../router'

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
function TreeDto (species, height, diameter, coordinates, owner, treeMunicipality, age, land) {
  this.species = species
  this.status = status 
  this.height = height
  this.diameter = diameter
  this.coordinates = coordinates
  this.owner = owner
  this.treeMunicipality = treeMunicipality
  this.versions = versions
  this.land = land
	this.age=age

}
function LocationDto(lat, long){
	this.latitude=lat
	this.longitude=long
}
export default {
  name: 'EditTree',
	//add comma
  data () {
    return {
      versionBase: '',
      versions: [],
      timeLapse: '',
      forecaster: '',
      deleteTreeId: '',
      deleteTreeIdList: [],
			newSpecies: '',
			species: [],
			newTreeHeight: '',
			newTreeDiameter: '', 
			newMunicipality: '',
			municipalities: [],
			newLat: '',
			newLong: '',
			newLanduse: '',
     	landUses: [],
			newOwner: '', 
			newAge: '',
			newTrees: [],
			errorLandUse: '', 
			errorMunicipalities: '',
			errorSpecies: '',
			errorVersions: '',
			responseForecast: ''
    }
  }, 
 	created : function(){
		 AXIOS.get('/species')
    .then(response => {
      // JSON responses are automatically parsed.
      this.species = response.data
    })
    .catch(e => {
      this.errorSpecies = e;
    });
  	AXIOS.get('/municipalities')
    .then(response => {
      // JSON responses are automatically parsed.
      this.municipalities = response.data
    })
    .catch(e => {
      this.errorMunicipalities = e;
    });
    AXIOS.get('/landUseTypes')
    .then(response => {
      // JSON responses are automatically parsed.
      this.landUses = response.data
    })
    .catch(e => {
      this.errorLandUse = e;
    });
    AXIOS.get('/versions')
    .then(response => {
    	this.versions =  response.data
    })
    .catch(e => {
      this.errorVersions = e;
    });
  },
  methods: {
  		addTree: function(species, height, diameter, newLat, newLong, owner,treeMunicipality, age, land){
  				var coordinates= new LocationDto(newLat, newLong)
  				var newTree= new TreeDto(species, height, diameter, coordinates, owner, treeMunicipality, age, land)
  				this.newTrees.push(newtree)
  				this.newSpecies=''
  				this.newTreeHeight=''
  				this.newTreeDiameter=''
  				this.newLat=''
  				this.newLong=''
  				this.newOwner= ''
  				this.newMunicipality=''
  				this.newage=''
  				this.newLanduse=''
  		},
  		deleteTree: function(treeId){
  				this.deleteTreeIdList.push(treeId)
  				this.deleteTreeId=''
  		},
  		createForecast: function(name, versionBase, timeLapse){
  			AXIOS.post('/newForecast/'+name, {}, {params: {baseVersion: versionBase, futureYear: timeLapse, treesToPlant: this.newTrees, treesToCutDown: this.deleteTreeIdList}}).then(response => {
					this.responseForecast=response.data
					this.name=''
      		this.versionBase=''
      		this.timeLapse=''
      		this.newTrees=''
      		this.deleteTreeIdList=''
				})
				.catch(e =>{
					var errorMsg= e.response.data.message
					console.log(errorMsg)
					this.errorEvent = errorMsg 
				})
  		}
  }  
}



