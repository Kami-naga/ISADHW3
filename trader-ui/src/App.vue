<template>
  <div id="app">
    <div style="height:60px;">
      <Row>
        <Col span="15">
          ISAD
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
  </div>
</template>

<script>
import login from '@/components/login'
export default {
  name: 'App',
  components:{
    login
  },
  methods:{
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
</style>
