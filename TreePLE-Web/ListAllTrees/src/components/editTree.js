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
      errorMunicipalities: '',
      errorEditTree: '',
      errorLandUse: ''
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
    editTree: function(treeId, height, diameter, age, ownerName, species, landuse, municipality) {
      AXIOS.post('/updateTreeData/' + treeId, {}, {
          params: {
            newHeight: height,
            newDiameter: diameter,
            newAge: age,
            newOwnerName: ownerName,
            newSpecies: species,
            newLandUse: landuse,
            newMunicipality: municipality
          }
        }).then(response => {
          this.changedTree = response.data
          this.treeId = ''
          this.ownerName = ''
          this.species = ''
          this.age = ''
          this.diameter = ''
          this.long2 = ''
          this.height = ''
          this.municipality = ''
          this.landuse = ''
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorEditTree = errorMsg
        })
    }
  }
}