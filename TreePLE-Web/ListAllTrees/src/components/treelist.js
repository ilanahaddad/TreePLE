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

var icons = {
  planted: {
    icon: 'https://imageshack.com/a/img922/879/deEdYF.png'
  }
};

function TreeDto(species, height, diameter, age, coordinates, owner, treeMunicipality, land) {
  this.id = id
  this.species = species
  this.status = status
  this.height = height
  this.diameter = diameter
  this.coordinates = coordinates
  this.ownerName = owner
  this.treeMunicipality = treeMunicipality
  //this.versions = versions
  this.land = land
}

export default {
  name: 'ListTrees',
  data() {
    return {
      trees: [],
      versions: [],
      municipalities: [],
      newMun: '',
      statuses: [],
      species: [],
      landUses: [],
      newTree: '',
      errorVersions: '',
      errorTree: '',
      errorSpecies: '',
      errorStatus: '',
      errorMunicipalities: '',
      errorLandUse: '',
      errorCreateMun: '',
      successCreateMun: '',
      response: [],
      selectedMunicipality: '',
      selectedVersion: '',
      selectedSpecies: '',
      selectedStatus: '',
      selectedLandUse: '',
      surveyDate: '',
      surveyTree: '',
      surveyor: '',
      surveyStatus: '',
      center: {
        lat: 45.5048,
        lng: -73.5772
      },
      markers: [],
      /*
      markers: [{
        position: {lat: 45.50, lng: -73.57}
      }, {
        position: {lat: 45.51, lng: -73.58}
      }]
      */
			curVersion: '',
			vYear: ''
    }
  },
  created: function() {
    // Initializing trees from backend
    AXIOS.get('/trees')
      .then(response => {
        // JSON responses are automatically parsed.
        this.trees = response.data
        for (var i = 0; i < this.trees.length; i++) {
          this.markers.push({
            position: {
              lng: this.trees[i].coordinates.longitude,
              lat: this.trees[i].coordinates.latitude
            },
            icon: 'http://maps.google.com/mapfiles/kml/shapes/star.png'
          })
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
		AXIOS.get('/versionNumber')
			.then(response => {
				this.curVersion=response.data
			})
			.catch(e=> {
				this.errorVersions=e;
			});
		AXIOS.get('/versionYear')
			.then(response => {
				this.vYear=response.data
			})
			.catch(e =>{
				this.errorVersions=e;
			});

  },

  methods: {

  	addMunicipality: function (newMun) {
  		AXIOS.post('/newMunicipality/'+newMun, {}, {}).then(response => {
					this.municipalities.push(response.data)
          this.successCreateMun= "Awesome! You have successfully created " + newMun + " as a municipality.";
					this.newMun='';
				})
				.catch(e =>{
					var errorMsg= e.response.data.message
					console.log(errorMsg)
					this.errorCreateMun = errorMsg;
          this.errorStatus = '';
          this.errorTree = '';
          this.errorLandUse = '';
          this.errorSpecies = '';
          this.errorMunicipalities = '';
          this.errorVersions = ''; 
				})
  	},
		showTreeData: function(event) {
        var panel = event.target.nextElementSibling;
        if (panel.style.display === "block") {
            panel.style.display = "none";
        } else {
            panel.style.display = "block";
        }
     },

    listAll: function() {
      AXIOS.get('/trees/')
        .then(response => {
          // JSON responses are automatically parsed.
          this.trees = response.data
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorTree = errorMsg;
          this.errorStatus = '';
          this.errorLandUse = '';
          this.errorSpecies = '';
          this.errorMunicipalities = '';
          this.errorVersions = '';
          this.errorCreateMun = '';
        });
    },

    listByMunicipality: function(selectedMunicipality) {
      AXIOS.get('/treesByMunicipality/' + selectedMunicipality)
        .then(response => {
          // JSON responses are automatically parsed.
          this.trees = response.data
          //this.trees = []
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorStatus = '';
          this.errorTree = '';
          this.errorLandUse = '';
          this.errorSpecies = '';
          this.errorMunicipalities = errorMsg;
          this.errorVersions = '';
          this.errorCreateMun = '';
        });

    },

    listBySpecies: function(selectedSpecies) {
      //const t1 = new TreeDto('Pine', '4', '0.5', 'coordinates', 'Thomas', 'Outremont', '1', 'Park', 'Planted', '100')

      AXIOS.get('/treesBySpecies/' + selectedSpecies)
        .then(response => {
          //JSON responses are automatically parsed.
          this.trees = response.data
          //this.trees = [t1]
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorStatus = '';
          this.errorLandUse = '';
          this.errorTree = '';
          this.errorMunicipalities = '';
          this.errorSpecies = errorMsg;
          this.errorVersions = '';
          this.errorCreateMun = '';
        });
    },

    listByStatus: function(selectedStatus) {
      AXIOS.get('/treesByStatus/' + selectedStatus)
        .then(response => {
          // JSON responses are automatically parsed.
          this.trees = response.data
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorStatus = errorMsg;
          this.errorLandUse = '';
          this.errorTree = '';
          this.errorSpecies = '';
          this.errorMunicipalities = '';
          this.errorVersions = '';
          this.errorCreateMun = '';
        });
    },

    listByLandUse: function(selectedLandUse) {
      AXIOS.get('/treesByLandUse/' + selectedLandUse)
        .then(response => {
          // JSON responses are automatically parsed.
          this.trees = response.data
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          this.errorLandUse = errorMsg;
          this.errorStatus = '';
          this.errorTree = '';
          this.errorSpecies = '';
          this.errorMunicipalities = '';
          this.errorVersions = '';
          this.errorCreateMun = '';
        });
    },

    updateVersion: function(versionNumber) {
      AXIOS.post('/updateVersion/', {}, {
        params: {versionNum: versionNumber}
      })
        .then(response => {
          // JSON responses are automatically parsed.
         	this.selectedVersion=''
          window.location.reload();
        })
        .catch(e => {
          this.errorLandUse = e;
        });
			AXIOS.get('/versionNumber')
			.then(response => {
				this.curVersion=response.data.toString()
			})
			.catch(e=> {
				this.errorVersions=e;
			});
			AXIOS.get('/versionYear')
			.then(response => {
				this.vYear=response.data
			})
			.catch(e =>{
        var errorMsg = e.response.data.message
        console.log(errorMsg)
				this.errorVersions= errorMsg;
        this.errorLandUse = '';
        this.errorStatus = '';
        this.errorTree = '';
        this.errorSpecies = '';
        this.errorMunicipalities = '';
        this.errorCreateMun = '';
			});

			
    }, 
    createSurvey: function(surveyDate, surveyTree, surveyor, surveyStatus){
      AXIOS.post('/newSurvey/', {}, {params: {
        reportDate: surveyDate, 
        tree: surveyTree, 
        surveyor: surveyor, 
        newTreeStatus: surveyStatus}})
      .then(response =>{
        this.surveyDate= '';
        this.surveyTree= '';
        this.surveyor= '';
        this.surveyStatus= '';
      })
      .catch(e =>{
        var errorMsg= e.response.data.message
        console.log(errorMsg)
        
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
