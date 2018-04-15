import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: {
    'Access-Control-Allow-Origin': frontendUrl
  }
})

export default {
  name: 'EditTree',
  //add comma
  data() {
    return {
      treeId: '',
      ownerName: '',
      species: '',
      age: '',
      diameter: '',
      height: '',
      municipality: '',
      landuse: '',
      municipalities: [],
      landUses: [],
      changedTree: '',
      newLat: '', 
      newLong: '',
      errormsg: ''
    }
  },
  created: function() {

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
  },
  methods: {
    createTree: function(species, height, diameter, municipality, newLat, newLong, ownerName, age, landuse) {
      AXIOS.post('/newTree/' + species, {}, {
          params: {
            species: species,
            height: height,
            diameter: diameter,
            municipality: municipality,
            latitude: newLat,
            longitude: newLong,
            owner: ownerName,
            age: age,
            landuse: landuse
          }
        }).then(response => {
          this.ownerName = ''
          this.species = ''
          this.age = ''
          this.diameter = ''
          this.newLong = ''
          this.newLat= ''
          this.height = ''
          this.municipality = ''
          this.landuse = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorEvent = errorMsg
        })
    }
  }
}