<template>
  <div>
    <Row>
      <Col span="4" class="container">
        <Avatar :src="this.$store.state.user.avatar" size="large"/>
      </Col>
      <Col span="6" class="container name">
        <p>{{this.$store.state.user.name}}</p>
      </Col>
      <Col span="12" class="container name" v-if="this.$store.state.user.name==='未登录'">
        <div class="btn" @click="login">账号登陆</div>
        <div class="btn" @click="register" style="margin-left:30px">注册</div>
      </Col>
      <Col span="12" class="container name" v-else>
        <div class="btn" @click="logout">退出登录</div>
      </Col>
    </Row>
  </div>
</template>

<script>
export default {
  name: 'login',
  data(){
    return{

    }
  },
  methods:{
    logout(){
      this.$Modal.confirm({
        title: '您将退出登陆',
        content: '',
        onOk: () => {
          this.$Message.info('Clicked ok');
        },
        onCancel: () => {
          this.$Message.info('Clicked cancel');
        }
      });
    }
  },
  computed:{

  }
}
</script>

<style scoped>
.container{
  height:60px;
  display:flex;
  flex-direction:row;
  align-items:center;
}

.name{
  font-weight:600;
  font-size:15px;
  overflow:hidden;
  white-space:nowrap;
  text-overflow:ellipsis;
}

.btn{
  cursor:pointer
}

</style>
