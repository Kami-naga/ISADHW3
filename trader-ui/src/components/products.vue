<template>
  <div class="layout">
    <Layout>
      <Sider class="sider"  width=150>
        <div class="product-title">
          上市品种
        </div>
        <Menu style="width:150px" :active-name="products[0].id">
          <MenuItem v-for="(product,index) in products" :key="index" :name="product.id">
            <span>{{product.name}}</span>
          </MenuItem>
        </Menu>
      </Sider>
      <Layout :style="{marginLeft: '150px'}">
        <Header class="header">
          <Dropdown  placement="bottom-start">
            <a href="javascript:void(0)">
              broker
              <Icon type="ios-arrow-down"></Icon>
            </a>
            <DropdownMenu slot="list">
              <DropdownItem  v-for="(broker,index) in brokers" :key="index">
                {{broker.name}}
              </DropdownItem>
            </DropdownMenu>
          </Dropdown>
        </Header>
        <Content>
          <router-view/>
        </Content>
      </Layout>
    </Layout>
  </div>
</template>

<script>
export default {
  name: 'products',
  data () {
    return {
      products:[
        {
          id:0,
          name:"铜"
        },
        {
          id:1,
          name:"铝"
        },
        {
          id:2,
          name:"锌"
        },
        {
          id:3,
          name:"铅"
        },
        {
          id:4,
          name:"镍"
        },
        {
          id:5,
          name:"铜"
        },
        {
          id:6,
          name:"铝"
        },
        {
          id:7,
          name:"锌"
        },
        {
          id:8,
          name:"铅"
        },
        {
          id:9,
          name:"镍"
        },
        {
          id:10,
          name:"铜"
        },
        {
          id:11,
          name:"铝"
        },
        {
          id:12,
          name:"锌"
        },
        {
          id:13,
          name:"铅"
        },
        {
          id:14,
          name:"镍"
        }
      ],
      brokers:[
        {
          id:0,
          name:"Broker M"
        },
        {
          id:1,
          name:"Broker W"
        },
        {
          id:2,
          name:"Broker S"
        },
        {
          id:3,
          name:"Broker G"
        },
      ]
    }
  },
  methods:{

  },
  mounted(){
  }
}
</script>

<style scoped>
.layout{
  border-top:2px solid #d0d0d0
}

.product-title{
  height:50px;
  font-size:20px;
  text-align:center;
  line-height:50px;
  color:#fff;
  background-color:#2d8cf0;
}

.ivu-menu-vertical .ivu-menu-item{
  padding-left: 15px;
  padding-right: 15px;
  padding-top: 14px;
  padding-bottom:8px;
  text-align:center;
  font-size:20px;
}
::-webkit-scrollbar {
  display:none
}
.sider{
  height: calc(100vh - 70px);
  position:fixed;
  left:0;
  overflow:auto;
}
.header{
  height:50px;
  background-color:#f0faff

}
.ivu-layout-header{
  line-height:50px;
  font-size:20px;
}
</style>
