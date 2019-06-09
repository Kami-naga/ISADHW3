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
    <Modal
      title="用户注册"
      v-model="modalReg"
      @on-ok="registerOk"
      @on-cancel="registerCancel"
      >
        <div style="width:300px">
          <Row>
            <Col class="modal-row" span="6" offset="2">
              用户名：
            </Col>
            <Col class="modal-row" span="16" >
              <input placeholder="请输入用户名" v-model="name" style="height:100%;outline:none;width:100%;text-align:left;border-style:none"/>
            </Col>
          </Row>
          <Row>
            <Col class="modal-row" span="6" offset="2">
              邮箱：
            </Col>
            <Col class="modal-row" span="16" >
              <input placeholder="请输入邮箱" v-model="email" style="height:100%;outline:none;width:100%;text-align:left;border-style:none"/>
            </Col>
          </Row>
          <Row>
            <Col class="modal-row" span="6" offset="2">
              密码：
            </Col>
            <Col class="modal-row" span="16" >
              <input placeholder="请输入密码" v-model="password" style="height:100%;outline:none;width:100%;text-align:left;border-style:none"/>
            </Col>
          </Row>
          <Row>
            <Col offset="2" style="height:30px;display:flex;align-items:center;font-size:20px">
              <RadioGroup v-model="role" size="large">
                  <Radio label="trader">
                    <span>trader</span>
                    <Icon type="ios-contact" />
                  </Radio>
                  <Radio label="broker" style="margin-left:20px">
                    <span>broker</span>
                    <Icon type="logo-snapchat" />
                  </Radio>
              </RadioGroup>
            </Col>
          </Row>
          <Row v-if="role==='trader'">
            <Col class="modal-row" span="6" offset="2">
              公司：
            </Col>
            <Col class="modal-row" span="16" >
              <input placeholder="请输入公司名" v-model="company" style="height:100%;outline:none;width:100%;text-align:left;border-style:none"/>
            </Col>
          </Row>
        </div>
    </Modal>
    <Modal
      v-model="modalLogin"
      title="用户登陆"
      @on-ok="loginOk"
      @on-cancel="loginCancel">
      <div style="width:300px">
        <Row>
          <Col class="modal-row" span="6" offset="2">
            邮箱：
          </Col>
          <Col class="modal-row" span="16" >
            <input placeholder="请输入邮箱" v-model="email" style="height:100%;outline:none;width:100%;text-align:left;border-style:none"/>
          </Col>
        </Row>
        <Row>
          <Col class="modal-row" span="6" offset="2">
            密码：
          </Col>
          <Col class="modal-row" span="16" >
            <input placeholder="请输入密码" v-model="password" style="height:100%;outline:none;width:100%;text-align:left;border-style:none"/>
          </Col>
        </Row>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  name: 'login',
  data(){
    return{
      modalReg:false,
      modalLogin:false,
      name:"",
      email:"",
      password:"",
      role:"trader",
      company:""
    }
  },
  methods:{
    register(){
      this.modalReg = true
    },
    clear(){
      this.name="",
      this.email="",
      this.password="",
      this.role="trader",
      this.company=""
    },
    registerOk(){
      console.log(this.name,this.email,this.password,this.company)
      this.modalReg = false
      this.clear()
    },
    registerCancel(){
      this.modalReg = false
      this.clear()
    },
    login(){
      this.modalLogin = true
    },
    loginOk(){
      console.log(this.email,this.password)
      this.modalLogin = false
      this.clear()
    },
    loginCancel(){
      this.modalLogin = false
      this.clear()
    },
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

.modal-row{
  height:30px;
  font-size:15px;
  line-height:30px;
}
</style>
