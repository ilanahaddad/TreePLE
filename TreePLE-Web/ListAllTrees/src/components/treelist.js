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
    // Test data
    const t1 = new TreeDto('123', 'Pine', '60908', '45452', 'Planted')
    const t2 = new TreeDto('124', 'Cedar', '60910', '45600', 'Planted')
    // Sample initial content
    this.trees = [t1, t2]
  },

  methods: {
    createTree: function (treeId, treeSpecies, treeLongitude, treeLatitude, treeStatus) {
      // Create a new participant and add it to the list of participants
      var t = new TreeDto(treeId, treeSpecies, treeLongitude, treeLatitude, treeStatus)
      this.trees.push(t)
      // Reset the name field for new participants
      this.newTree = ''
    }
  }
}
