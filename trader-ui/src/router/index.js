import Vue from 'vue'
import Router from 'vue-router'
import operatingBoard from '@/components/OperatingBoard'
import products from '@/components/products'
import orderbooks from '@/components/orderbooks'
import orderbook from '@/components/orderbook'
import test from '@/components/test'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect:'products'
    },
    {
      path: '/test',
      component: test,
    },
    {
      path:'/products',
      component: products,
      children:[
        {
          path: '/',
          redirect:'orderbooks'
        },
        {
          path:'orderbooks',
          name:'orderbooks',
          component: orderbooks
        },
        {
          path:'orderbook',
          name:'orderbook',
          component: orderbook
        },
      ]
    }
  ]
})
