import Vue from 'vue'
import Router from 'vue-router'
import operatingBoard from '@/components/OperatingBoard'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'OperatingBoard',
      component: operatingBoard
    }
  ]
})
