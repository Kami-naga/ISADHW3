<template>
  <div id="app">
    <div style="height:60px;">
      <Row>
        <Col span="12">
          ISAD
        </Col>
        <Col class="check-trades" span="3">
          <Button v-if="this.$store.state.user.role=='broker'" @click="addBook" type="info" size="large">
            添加期货
          </Button>
        </Col>
        <Col class="check-trades" span="3">
          <Button @click="checkTrades" type="info" size="large">
            {{isChecked?"返回上一级":"查看所有交易"}}
          </Button>
        </Col>
        <Col span="6">
          <login></login>
        </Col>
      </Row>
    </div>
    <router-view/>
    <Modal
      v-model="modalBook"
      title="添加期货"
      @on-ok="addbookOk"
      @on-cancel="addbookCancel">
      <div style="width:300px">
        <Row>
          <Col class="modal-row" span="6" offset="2">
            类型：
          </Col>
          <Col class="modal-row" span="16" >
            <Select v-model="product" filterable>
                <Option v-for="item in this.$store.state.products" :value="item.id" :key="item.id">{{ item.ename }}</Option>
            </Select>
          </Col>
        </Row>
        <Row>
          <Col class="modal-row" span="6" offset="2">
            到期日：
          </Col>
          <Col class="modal-row" span="16" >
            <DatePicker format="yyyy-MM-dd" type="date" placeholder="选择日期" @on-change="changePeriod"></DatePicker>
          </Col>
        </Row>
      </div>
    </Modal>
  </div>
</template>

<script>
import login from '@/components/login'
export default {
  name: 'App',
  components:{
    login
  },
  data(){
    return {
      modalBook:false,
      product:"",
      period:""
    }
  },
  methods:{
    changePeriod(e){
      this.period = e
    },
    addBook(){
      this.modalBook = true
    },
    clearBook(){
      this.product = ""
      this.period = ""
    },
    addbookOk(){
      console.log(this.product,this.period)

      this.modalBook = false
      this.clearBook()
    },
    addbookCancel(){
      this.modalBook = false
      this.clearBook()
    },
    checkTrades(){
      if(this.$route.path.slice(1).split("/")[0]==="orderblotter"){
        this.$router.go(-1)
        return
      }
      this.$router.push("/orderblotter")
    }
  },
  computed:{
    isChecked(){
      if(this.$route.path.slice(1).split("/")[0]==="orderblotter"){
        return true;
      }
      return false;
    }
  },
  mounted(){
    this.$axios({
      method:'get',
      url:this.$store.state.port+"/initPage"
    }).then((response)=>{
      console.log(response)
      this.$store.state.products = response.data.products
      this.$store.state.brokers = response.data.brokers
      this.$store.state.booksData = response.data.booksData
      this.$store.state.product = this.$store.state.products[0]
      this.$store.state.broker = this.$store.state.brokers[0]
    }).catch((error)=>{
      console.log(error)
    })
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.check-trades{
  height:60px;
  display:flex;
  align-items:center;
}

.modal-row{
  height:40px;
  font-size:15px;
  line-height:40px;
}
</style>
