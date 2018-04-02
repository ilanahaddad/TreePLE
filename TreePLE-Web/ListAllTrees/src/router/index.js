import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ListTrees from '@/components/ListTrees'
import GenerateReport from '@/components/GenerateReport'
import Report from '@/components/Report'
import Forecast from '@/components/Forecast'
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
    },
    {
      path: '/generatereport',
      name: 'GenerateReport',
      component: GenerateReport
    },
    {
      path: '/report',
      name: 'Report',
      component: Report
    },
    {
      path: '/forecast',
      name: 'Forecast',
      component: Forecast
    }
  ]
})
