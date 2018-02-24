import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function TreeDto (id, species, longitude, latitude, status) {
  this.id = id
  this.species = species
  this.longitude = longitude
  this.latitude = latitude
  this.status = status
}

export default {
  name: 'ListTrees',
  data () {
    return {
      trees: [],
      newTree: '',
      errorTree: '',
      response: []
    }
  },
  created: function () {
    // Initializing trees from backend
    AXIOS.get(`/trees`)
    .then(response => {
      // JSON responses are automatically parsed.
      this.trees = response.data
    })
    .catch(e => {
      this.errorTree = e;
    });
    // Test data
    //const t1 = new TreeDto('123', 'Pine', '60908', '45452', 'Planted')
    //const t2 = new TreeDto('124', 'Cedar', '60910', '45600', 'Planted')
    // Sample initial content
    //this.trees = [t1, t2]
  },

  methods: {
    createTree: function (treeId, treeSpecies, treeLongitude, treeLatitude, treeStatus) {
      AXIOS.post(`/trees/`+treeId, {}, {})
      .then(response => {
        // JSON responses are automatically parsed.
        this.trees.push(response.data)
        this.newTree = ''
        this.errorTree = ''
      })
      .catch(e => {
        var errorMsg = e.message
        console.log(errorMsg)
        this.errorTree = errorMsg
      });
      // Create a new participant and add it to the list of participants
      //var t = new TreeDto(treeId, treeSpecies, treeLongitude, treeLatitude, treeStatus)
      //this.trees.push(t)
      // Reset the name field for new participants
      //this.newTree = ''
    }
  }
}
