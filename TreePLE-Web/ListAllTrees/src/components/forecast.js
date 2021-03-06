import axios from 'axios'
var config = require('../../config')

//import router from '../router'

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: {
    'Access-Control-Allow-Origin': frontendUrl
  }
})

function TreeDto(species, height, diameter, age, coordinates, owner, treeMunicipality, land) {
  this.species = species
  this.height = height
  this.diameter = diameter
  this.coordinates = coordinates
  this.owner = owner
  this.treeMunicipality = treeMunicipality
  this.land = land
  this.age = age

}

function LocationDto(lat, long) {
  this.latitude = lat
  this.longitude = long
}
export default {
  name: 'EditTree',
  //add comma
  data() {
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
      responseForecast: '',
      newTree: '',
      newCoordinates: '',
      errorForecast: '',
      errorAddTree: ''
    }
  },
  created: function() {
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
        this.versions = response.data
      })
      .catch(e => {
        this.errorVersions = e;
      });
  },
  methods: {
    addTree: function(species, height, diameter, newLat, newLong, owner, treeMunicipality, age, land) {
      //this.newCoordinates= new LocationDto(newLat, newLong)
      //this.newTree= new TreeDto(species, height, diameter, age, this.newCoordinates, owner, treeMunicipality, land
      AXIOS.post('/newTreeDto/' + species, {}, {
          params: {
            height: height,
            diameter: diameter,
            municipality: treeMunicipality,
            latitude: newLat,
            longitude: newLong,
            owner: owner,
            age: age,
            landuse: land
          }
        }).then(response => {

          this.newTrees.push(response.data)
          this.newTree = response.data
          this.newSpecies = ''
          this.newTreeHeight = ''
          this.newTreeDiameter = ''
          this.newLat = ''
          this.newLong = ''
          this.newOwner = ''
          this.newMunicipality = ''
          this.newAge = ''
          this.newLanduse = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorAddTree = errorMsg
          this.errorForecast = ''
        })
    },
    deleteTree: function(treeId) {
      this.deleteTreeIdList.push(treeId)
      this.deleteTreeId = ''
    },
    createForecast: function(name, versionBase, timeLapse) {
      AXIOS.post('/newForecast/' + name, {}, {
          params: {
            baseVersion: versionBase,
            futureYear: timeLapse,
            treesToPlant: this.newTrees,
            treesToCutDown: this.deleteTreeIdList
          }
        }).then(response => {
          this.responseForecast = response.data
          this.name = ''
          this.versionBase = ''
          this.timeLapse = ''
          this.newTrees = ''
          this.deleteTreeIdList = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorForecast = errorMsg
          this.errorAddTree = ''
        })
    }
  }
}