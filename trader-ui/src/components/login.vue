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
            <Col class="modal-row" span="6" offset="2">
              公司：
            </Col>
            <Col class="modal-row" span="16" >
              <input placeholder="请输入公司名" v-model="company" style="height:100%;outline:none;width:100%;text-align:left;border-style:none"/>
            </Col>
          </Row>
          <Row>
            <Col offset="2" style="height:30px;display:flex;align-items:center;font-size:20px">
              <RadioGroup v-model="otherSee" size="large">
                  <Radio label="0">
                    <span>他人可见</span>
                    <Icon type="ios-contact" />
                  </Radio>
                  <Radio label="1" style="margin-left:20px">
                    <span>他人不可见</span>
                    <Icon type="logo-snapchat" />
                  </Radio>
              </RadioGroup>
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
      company:"",
      otherSee:"0"
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
      this.company=""
    },
    registerOk(){
      console.log(this.otherSee)
      var osee = true
      if(this.otherSee==="1"){
        osee=false
      }
      this.$axios({
        method:'post',
        url:this.$store.state.port+"/register",
        data:{
          name:this.name,
          email:this.email,
          password:this.password,
          company:this.company,
          otherSee: osee
        },
        transformRequest:function(obj) {
    　　　var str = [];
    　　　for ( var p in obj) {
    　　　　str.push(encodeURIComponent(p) + "="
    　　　　+ encodeURIComponent(obj[p]));
    　　　}
    　　　return str.join("&");
    　　}
      }).then((response)=>{
        console.log(response)
        this.$Notice.success({
          title: '注册成功',
        });
      }).catch((error)=>{
        console.log(error)
        this.$Notice.success({
          title: '注册成功',
        });
      })

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
      this.$axios({
        method:'post',
        url:this.$store.state.port+"/api/user/login",
        data:{
          email:this.email,
          password:this.password,
        },
        transformRequest:function(obj) {
    　　　var str = [];
    　　　for ( var p in obj) {
    　　　　str.push(encodeURIComponent(p) + "="
    　　　　+ encodeURIComponent(obj[p]));
    　　　}
    　　　return str.join("&");
    　　}
      }).then((response)=>{
        console.log(response)
        this.$store.state.user = {
          id: response.data.id,
          role: response.data.role==0?"trader":"broker",
          name: response.data.name
        }
        this.$store.state.user.avatar="https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=92afee66fd36afc3110c39658318eb85/908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg"

      }).catch((error)=>{
        console.log(error)

      })

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
          this.$store.state.user={
            id:"",
            name:"未登录",
            avatar:"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1560095880&di=6c3c14f3ec86fa255b75e6ee0546e7f3&src=http://hbimg.b0.upaiyun.com/7842c3e9b5c38401e94851097c0e29f0b48c5f884d66-x9BbFI_fw658",
            role:""
          }
        },
        onCancel: () => {

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
