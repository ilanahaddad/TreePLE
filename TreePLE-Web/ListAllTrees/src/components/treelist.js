import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function TreeDto (species, height, diameter, coordinates, owner, treeMunicipality, versions, land, status, id) {
  this.id = id
  this.species = species
  this.status = status 
  this.height = height
  this.diameter = diameter
  this.coordinates = coordinates
  this.owner = owner
  this.treeMunicipality = treeMunicipality
  this.versions = versions
  this.land = land
}

export default {
  name: 'ListTrees',
  data () {
    return {
      trees: [],
      versions: [],
      municipalities: [],
      statuses: [],
      species: [],
      landUses: [],
      newTree: '',
      errorVersions: '',
      errorTree: '',
      errorSpecies: '',
      errorStatus: '',
      errorMunicipalities:'',
      errorLandUse: '',
      response: [],
      selectedMunicipality: '',
      selectedVersion: '',
      selectedSpecies: '',
      selectedStatus: '',
      selectedLandUse: '',
      center: {lat: 45.5048, lng: -73.5772},
      markers: []
      /*
      markers: [{
        position: {lat: 45.50, lng: -73.57}
      }, {
        position: {lat: 45.51, lng: -73.58}
      }]
      */
 
    }
  },
  created: function () {
    // Initializing trees from backend
    AXIOS.get('/trees')
    .then(response => {
      // JSON responses are automatically parsed.
      this.trees = response.data
      for(var i=0;i<this.trees.length;i++){
      this.markers.push({position: {lng: this.trees[i].coordinates.longitude, lat: this.trees[i].coordinates.latitude)}}
    }
    })
    .catch(e => {
      this.errorTree = e;
    });
     //Test data
    //const t1 = new TreeDto('Pine', '4', '0.5', 'coordinates', 'Thomas', 'Outremont', '1', 'Park', 'Planted', '100')
    //const t2 = new TreeDto('Cedar', '4', '0.5', 'coordinates', 'Thomas', 'Outremont', '1', 'Park', 'Planted', '112')
    // Sample initial content
    //this.trees = [t1, t2]

    AXIOS.get('/municipalities')
    .then(response => {
      // JSON responses are automatically parsed.
      this.municipalities = response.data
    })
    .catch(e => {
      this.errorMunicipalities = e;
    });

    AXIOS.get('/species')
    .then(response => {
      // JSON responses are automatically parsed.
      this.species = response.data
    })
    .catch(e => {
      this.errorSpecies = e;
    });

    AXIOS.get('/statuses')
    .then(response => {
      // JSON responses are automatically parsed.
      this.statuses = response.data
    })
    .catch(e => {
      this.errorSpecies = e;
    });

    AXIOS.get('/versions')
    .then(response => {
      // JSON responses are automatically parsed.
      this.versions = response.data
    })
    .catch(e => {
      this.errorVersions = e;
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
		showTreeData: function(event) {
        var panel = event.target.nextElementSibling;
        if (panel.style.display === "block") {
            panel.style.display = "none";
        } else {
            panel.style.display = "block";
        }
     },

    listAll: function(){
      AXIOS.get('/trees/')
      .then(response => {
      // JSON responses are automatically parsed.
       this.trees = response.data
       })
    .catch(e => {
      this.errorTree = e;
    });
    },

    listByMunicipality: function(selectedMunicipality){
      AXIOS.get('/treesByMunicipality/' + selectedMunicipality)
      .then(response => {
      // JSON responses are automatically parsed.
       this.trees = response.data
       //this.trees = []
       })
    .catch(e => {
      this.errorMunicipalities = e;
    });

    },

    listBySpecies: function(selectedSpecies){
      //const t1 = new TreeDto('Pine', '4', '0.5', 'coordinates', 'Thomas', 'Outremont', '1', 'Park', 'Planted', '100')
    
      AXIOS.get('/treesBySpecies/' + selectedSpecies)
      .then(response => {
      //JSON responses are automatically parsed.
       this.trees = response.data
      //this.trees = [t1]
       })
    .catch(e => {
      this.errorSpecies = e;
    });
    },

    listByStatus: function(selectedStatus){
      AXIOS.get('/treesByStatus/' + selectedStatus)
      .then(response => {
      // JSON responses are automatically parsed.
       this.trees = response.data
       })
    .catch(e => {
      this.errorStatus = e;
    });
    },

    listByLandUse: function(selectedLandUse){
      AXIOS.get('/treesByLandUse/' + selectedLandUse)
      .then(response => {
      // JSON responses are automatically parsed.
       this.trees = response.data
       })
    .catch(e => {
      this.errorLandUse = e;
    });
    },

    updateVersion: function(VersionNumber){
      AXIOS.get('/treesByLandUse/' + selectedLandUse)
      .then(response => {
      // JSON responses are automatically parsed.
       this.trees = response.data
       })
    .catch(e => {
      this.errorLandUse = e;
    });
    }

    //createTree: function (treeId, treeSpecies, treeLongitude, treeLatitude, treeStatus) {
      //AXIOS.post(`/trees/`+treeId, {}, {})
      //.then(response => {
        // JSON responses are automatically parsed.
        //this.trees.push(response.data)
        //this.newTree = ''
        //this.errorTree = ''
      //})
      //.catch(e => {
       // var errorMsg = e.message
        //console.log(errorMsg)
        //this.errorTree = errorMsg
      //});
      // Create a new participant and add it to the list of participants
      //var t = new TreeDto(treeId, treeSpecies, treeLongitude, treeLatitude, treeStatus)
      //this.trees.push(t)
      // Reset the name field for new participants
      //this.newTree = ''
    //}
  },
/*
  data () {
    return {
      center: {lat: 45.5048, lng: -73.5772},
      markers: [{
        position: {lat: 45.50, lng: -73.57}
      }, {
        position: {lat: 45.51, lng: -73.58}
      }]
    }
  }*/

  

}

import * as VueGoogleMaps from 'vue2-google-maps'
import Vue from 'vue'

Vue.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyDUSNhFJa7yvEUUKHiQCeWgqBPI4xDgXHg',
    v: '3.32'
    // libraries: 'places', //// If you need to use place input
  }
})


