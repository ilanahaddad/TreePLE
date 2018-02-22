import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ListTrees from '@/components/ListTrees'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/app',
      name: 'ListTrees',
      component: ListTrees
    }
  ]
})
