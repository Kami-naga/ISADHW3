<template>
  <div class="layout">
    <Layout>
      <Sider class="sider"  width=150>
        <div class="product-title">
          上市品种
        </div>
        <Menu style="width:150px" :active-name="product.id">
          <MenuItem v-for="(item,index) in this.$store.state.products" :key="index" :name="item.id">
            <span>{{item.name}}</span>
          </MenuItem>
        </Menu>
      </Sider>
      <Layout :style="{marginLeft: '150px'}">
        <Header class="header">
          <Select style="width:200px" :placeholder="brokerPlace" @on-change="changeBroker" filterable>
              <Option v-for="item in this.$store.state.brokers" :value="item.id" :key="item.id">{{ item.name }}</Option>
          </Select>
          <Select  v-if="orderbooksPage==false" style="width:200px;margin-left:50px" :placeholder="this.$store.state.bookPlace" @on-change="changeBook" filterable>
              <Option v-for="item in this.$store.state.booksData" :value="item.id" :key="item.id">{{ item.bookName }}</Option>
          </Select>
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
      product:{},
      broker:{},
      book:{},
      brokerPlace:"",
    }
  },
  methods:{
    changeBroker(e){
      for (var i=0;i<this.$store.state.brokers.length;i++){
        if(this.$store.state.brokers[i].id===e){
          this.broker = this.$store.state.brokers[i]
          if(this.$route.path.slice(1).split("/")[1] !=='orderbooks'){
            
          }
          return
        }
      }
    },
    changeBook(e){
      for (var i=0;i<this.$store.state.booksData.length;i++){
        if(this.$store.state.booksData[i].id===e){
          this.book = this.$store.state.booksData[i]
          return
        }
      }
    },
  },
  computed:{
    orderbooksPage(){
      if(this.$route.path.slice(1).split("/")[1]==='orderbooks'){
        return true
      }
      return false
    }
  },
  mounted(){
    this.product = this.$store.state.products[0]
    this.broker = this.$store.state.brokers[0]
    this.brokerPlace = this.broker.name
    this.$store.state.bookPlace = this.$store.state.booksData[0].bookName
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
