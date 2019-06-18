<template>
  <div class="board" >
    <div style="width:50%;display:flex;flex-direction:row">
      <Tree :data="tree"></Tree>
    </div>
    <div style="width:650px;position:fixed;top:0;right:0">
      <Tabs v-model="currentTab" @on-click="currentTabChanged">
          <TabPane label="Market Order" name="market">
            <div class="card-container">
              <Card style="width:600px;">
                <p slot="title">Market Order</p>
                <div style="width:100%;height:100%">
                  <div class="card-item">
                    <p style="width:50%;text-align:left;margin-left:10px">Instrument:{{instrument_name}}</p>
                    <button @click="changeSide" style="border-style:none;background-color:#fff;width:50%;padding-left:10px;text-align:left;height:100%;border-left:1px solid #a8a8a8">
                      {{side}}
                    </button>
                  </div>
                  <div class="card-item">
                    <p style="text-align:left;margin-left:10px">
                      Broker:{{broker_name}}
                    </p>
                  </div>
                  <div class="card-item">
                    <div style="text-align:left;margin-left:10px;display:flex;flex-direction:row;align-items:center">
                      <p>qty:</p>
                      <input v-model="qty" style="outline:none;margin:5px;width:200px;text-align:center;border-style:none"/>
                    </div>
                  </div>
                  <div class="card-item">
                    <button @click="Ok" style="outline:none;border-style:none;background-color:#fff;width:50%;padding-left:10px;text-align:left;height:100%;border-left:1px solid #a8a8a8">
                      Ok
                    </button>
                    <button @click="Cancel" style="outline:none;margin-left:10px;border-style:none;background-color:#fff;width:50%;padding-left:10px;text-align:left;height:100%;border-left:1px solid #a8a8a8">
                      Cancel
                    </button>
                  </div>
                </div>
              </Card>
            </div>
          </TabPane>
          <TabPane label="Limit Order" name="limit">
            <div class="card-container">
              <Card style="width:600px;">
                <p slot="title">Limit Order</p>
                <div style="width:100%;height:100%">
                  <div class="card-item">
                    <p style="width:50%;text-align:left;margin-left:10px">Instrument:{{instrument_name}}</p>
                    <button @click="changeSide" style="outline:none;border-style:none;background-color:#fff;width:50%;padding-left:10px;text-align:left;height:100%;border-left:1px solid #a8a8a8">
                      {{side}}
                    </button>
                  </div>
                  <div class="card-item">
                    <p style="text-align:left;margin-left:10px">
                      Broker:{{broker_name}}
                    </p>
                  </div>
                  <div class="card-item">
                    <div style="width:300px;text-align:left;padding-left:10px;display:flex;flex-direction:row;align-items:center">
                      <p>price:</p>
                      <input v-model="price" style="outline:none;margin:5px;width:200px;text-align:center;border-style:none"/>
                    </div>
                    <div style="border-left:1px solid #a8a8a8;width:300px;text-align:left;padding-left:10px;display:flex;flex-direction:row;align-items:center">
                      <p>qty:</p>
                      <input v-model="qty" style="outline:none;margin:5px;width:200px;text-align:center;border-style:none"/>
                    </div>
                  </div>
                  <div class="card-item">
                    <button @click="Ok" style="outline:none;border-style:none;background-color:#fff;width:300px;padding-left:10px;text-align:left;height:100%;border-left:1px solid #a8a8a8">
                      Ok
                    </button>
                    <button @click="Cancel" style="outline:none;border-style:none;background-color:#fff;width:300px;padding-left:10px;text-align:left;height:100%;border-left:1px solid #a8a8a8">
                      Cancel
                    </button>
                  </div>
                </div>
              </Card>
            </div>
          </TabPane>
          <TabPane label="Stop Order" name="stop">
            <div class="card-container">
              <Card style="width:600px;">
                <p slot="title">Stop Order</p>
                <div style="width:100%;height:100%">
                  <div class="card-item">
                    <p style="width:50%;text-align:left;margin-left:10px">Instrument:{{instrument_name}}</p>
                    <button @click="changeSide" style="outline:none;border-style:none;background-color:#fff;width:50%;padding-left:10px;text-align:left;height:100%;border-left:1px solid #a8a8a8">
                      {{side}}
                    </button>
                  </div>
                  <div class="card-item">
                    <p style="text-align:left;margin-left:10px">
                      Broker:{{broker_name}}
                    </p>
                  </div>
                  <div class="card-item">
                    <div style="width:300px;text-align:left;padding-left:10px;display:flex;flex-direction:row;align-items:center">
                      <p>price:</p>
                      <input v-model="price" style="outline:none;margin:5px;width:200px;text-align:center;border-style:none"/>
                    </div>
                    <div style="border-left:1px solid #a8a8a8;width:300px;text-align:left;padding-left:10px;display:flex;flex-direction:row;align-items:center">
                      <p>qty:</p>
                      <input v-model="qty" style="outline:none;margin:5px;width:200px;text-align:center;border-style:none"/>
                    </div>
                  </div>
                  <div class="card-item">
                    <button @click="Ok" style="outline:none;border-style:none;background-color:#fff;width:300px;padding-left:10px;text-align:left;height:100%;border-left:1px solid #a8a8a8">
                      Ok
                    </button>
                    <button @click="Cancel" style="outline:none;border-style:none;background-color:#fff;width:300px;padding-left:10px;text-align:left;height:100%;border-left:1px solid #a8a8a8">
                      Cancel
                    </button>
                  </div>
                </div>
              </Card>
            </div>
          </TabPane>
          <TabPane label="Cancel Order" name="cancel">
            <div class="card-container">
              <Card style="width:600px;">
                <p slot="title">Cancel Order</p>
                <div style="width:100%;height:100%">
                  <div class="card-item">
                    <div style="text-align:left;margin-left:10px;display:flex;flex-direction:row;align-items:center">
                      <p>OrderID:</p>
                      <input v-model="orderId" style="outline:none;margin:5px;width:200px;text-align:center;border-style:none"/>
                    </div>
                  </div>
                  <div class="card-item">
                    <p style="text-align:left;margin-left:10px">
                      Broker:{{broker_name}}
                    </p>
                  </div>
                  <div class="card-item">
                    <button @click="Ok" style="outline:none;border-style:none;background-color:#fff;width:50%;padding-left:10px;text-align:left;height:100%;border-left:1px solid #a8a8a8">
                      Ok
                    </button>
                    <button @click="Cancel" style="outline:none;margin-left:10px;border-style:none;background-color:#fff;width:50%;padding-left:10px;text-align:left;height:100%;border-left:1px solid #a8a8a8">
                      Cancel
                    </button>
                  </div>
                </div>
              </Card>
            </div>
          </TabPane>
      </Tabs>
    </div>
  </div>
</template>

<script>
export default {
  name: 'operatingBoard',
  data () {
    return {
      trader_id:1,
      instrument_id:1,
      broker_id:1,
      side:"buy",
      qty:0,
      price:0,
      orderId:"",
      currentTab:"market",
      instrument_name:"Sep15 Gold",
      broker_name:"M",
      tree:[]
    }
  },
  methods:{
    changeSide(){
      if(this.side=="buy"){
        this.side = "sell"
      }
      else{
        this.side = "buy"
      }
    },
    currentTabChanged(name){
      this.currentTab = name
      this.side="buy"
      this.qty=0
      this.price=0
      this.orderId=""
    },
    Ok(){
      if(this.currentTab=="market"){
        this.$axios({
          method:'post',
          url:'http://localhost:8083/market',
          data:{
            trader_id:this.trader_id,
            qty:this.qty,
            side:this.side,
            broker_id:this.broker_id,
            instrument_id:this.instrument_id
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

          this.$axios({
            method:'post',
            url:'http://localhost:8083/showDetail',
            data:{
              broker_id:this.broker_id,
              instrument_id:this.instrument_id
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
            var tmp = response.data
            this.construct(tmp)


          }).catch((error)=>{
            console.log(error)
          })


        }).catch((error)=>{
          console.log(error)
        })
      }
      else if(this.currentTab=="limit"){
        this.$axios({
          method:'post',
          url:'http://localhost:8083/limit',
          data:{
            trader_id:this.trader_id,
            price:this.price,
            qty:this.qty,
            side:this.side,
            broker_id:this.broker_id,
            instrument_id:this.instrument_id
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

          this.$axios({
            method:'post',
            url:'http://localhost:8083/showDetail',
            data:{
              broker_id:this.broker_id,
              instrument_id:this.instrument_id
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

            var tmp = response.data
            this.construct(tmp)


          }).catch((error)=>{
            console.log(error)
          })


        }).catch((error)=>{
          console.log(error)
        })
      }
      else if(this.currentTab=="stop"){
        this.$axios({
          method:'post',
          url:'http://localhost:8083/stop',
          data:{
            trader_id:this.trader_id,
            stopPrice:this.price,
            qty:this.qty,
            side:this.side,
            broker_id:this.broker_id,
            instrument_id:this.instrument_id
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

          this.$axios({
            method:'post',
            url:'http://localhost:8083/showDetail',
            data:{
              broker_id:this.broker_id,
              instrument_id:this.instrument_id
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

            var tmp = response.data
            this.construct(tmp)


          }).catch((error)=>{
            console.log(error)
          })


        }).catch((error)=>{
          console.log(error)
        })
      }
      else{
        this.$axios({
          method:'post',
          url:'http://localhost:8083/cancel',
          data:{
            orderId:this.orderId,
            trader_id:this.trader_id
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

          this.$axios({
            method:'post',
            url:'http://localhost:8083/showDetail',
            data:{
              broker_id:this.broker_id,
              instrument_id:this.instrument_id
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

            var tmp = response.data
            this.construct(tmp)


          }).catch((error)=>{
            console.log(error)
          })


        }).catch((error)=>{
          console.log(error)
        })
      }
    },
    Cancel(){

    },
    construct(tmp){
      this.tree = []
      var children = []
      var node = {}
      var buys = {title:"buys",expand:true,
        render:(h,{root,node,data})=>{
          return h('div',{
            style:{
              display:'inline-block',
              width:'100%',
            }
          },[
          h('p',{
            style:{
              backgroundColor:'green',
              color:'white',
              fontWeight:'700',
              fontSize:'20px',
              height:'30px',
              lineHeight:'30px',
              width:'500px'
            }
          },data.title),
          ])
        }
      }

      var sells = {title:"sells",expand:true,
        render:(h,{root,node,data})=>{
          return h('div',{
            style:{
              display:'inline-block',
              width:'100%',
            }
          },[
          h('p',{
            style:{
              backgroundColor:'red',
              color:'white',
              fontWeight:'700',
              fontSize:'20px',
              height:'30px',
              lineHeight:'30px',
              width:'500px'
            }
          },data.title),
          ])
        }
      }

      var stopBuys = {title:"stopBuys",expand:true,
        render:(h,{root,node,data})=>{
          return h('div',{
            style:{
              display:'inline-block',
              width:'100%',
            }
          },[
          h('p',{
            style:{
              backgroundColor:'green',
              color:'white',
              fontWeight:'700',
              fontSize:'20px',
              height:'30px',
              lineHeight:'30px',
              width:'500px'
            }
          },data.title),
          ])
        }
      }

      var stopSells = {title:"stopSells",expand:true,
        render:(h,{root,node,data})=>{
          return h('div',{
            style:{
              display:'inline-block',
              width:'100%',
              marginTop:"40px"
            }
          },[
          h('p',{
            style:{
              backgroundColor:'red',
              color:'white',
              fontWeight:'700',
              fontSize:'20px',
              height:'30px',
              lineHeight:'30px',
              width:'500px'
            }
          },data.title),
          ])
        }
      }

      for(var i=0;i<tmp.sells.length;i++){
        node = tmp.sells[tmp.sells.length-i-1]
        var child = {price:node.price,vol:node.vol,expand:false,
          render:(h,{root,node,data})=>{
            return h('div',{
              style:{
                display:'inline-block',
                width:'100%',
              }
            },[
            h('p',{
              style:{
                backgroundColor:'pink',
                color:'black',
                fontWeight:'500',
                fontSize:'15px',
                height:'25px',
                lineHeight:'25px',
                width:'200px',
                float:'left'
              }
            },data.price),
            h('p',{
              style:{
                backgroundColor:'pink',
                color:'black',
                fontWeight:'500',
                fontSize:'15px',
                height:'25px',
                lineHeight:'25px',
                width:'200px',
                float:'left'
              }
            },data.vol)
            ])
          }
        }
        var subChildren = []
        var subNode = {}
        for(var j=0;j<node.orderitemList.length;j++){
          subNode = node.orderitemList[j]
          subChildren.push({traderId:subNode.traderId,vol:subNode.vol,render:(h,{root,node,data})=>{
            return h('div',{
              style:{
                display:'inline-block',
                width:'100%',
              }
            },[
            h('p',{
              style:{
                backgroundColor:'#a8a8a8',
                color:'black',
                fontWeight:'400',
                fontSize:'12px',
                height:'20px',
                lineHeight:'20px',
                width:'150px',
                float:'left'
              }
            },data.traderId),
            h('p',{
              style:{
                backgroundColor:'#a8a8a8',
                color:'black',
                fontWeight:'400',
                fontSize:'12px',
                height:'20px',
                lineHeight:'20px',
                width:'150px',
                float:'left'
              }
            },data.vol)
            ])
          }})
        }
        child.children = subChildren
        children.push(child)
      }
      sells.children = children
      this.tree.push(sells)

      children = []
      for(var i=0;i<tmp.buys.length;i++){
        node = tmp.buys[i]
        var child = {price:node.price,vol:node.vol,expand:false,
          render:(h,{root,node,data})=>{
            return h('div',{
              style:{
                display:'inline-block',
                width:'100%',
              }
            },[
            h('p',{
              style:{
                backgroundColor:'pink',
                color:'black',
                fontWeight:'500',
                fontSize:'15px',
                height:'25px',
                lineHeight:'25px',
                width:'200px',
                float:'left'
              }
            },data.price),
            h('p',{
              style:{
                backgroundColor:'pink',
                color:'black',
                fontWeight:'500',
                fontSize:'15px',
                height:'25px',
                lineHeight:'25px',
                width:'200px',
                float:'left'
              }
            },data.vol)
            ])
          }
        }
        var subChildren = []
        var subNode = {}
        for(var j=0;j<node.orderitemList.length;j++){
          subNode = node.orderitemList[j]
          subChildren.push({traderId:subNode.traderId,vol:subNode.vol,render:(h,{root,node,data})=>{
            return h('div',{
              style:{
                display:'inline-block',
                width:'100%',
              }
            },[
            h('p',{
              style:{
                backgroundColor:'#a8a8a8',
                color:'black',
                fontWeight:'400',
                fontSize:'12px',
                height:'20px',
                lineHeight:'20px',
                width:'150px',
                float:'left'
              }
            },data.traderId),
            h('p',{
              style:{
                backgroundColor:'#a8a8a8',
                color:'black',
                fontWeight:'400',
                fontSize:'12px',
                height:'20px',
                lineHeight:'20px',
                width:'150px',
                float:'left'
              }
            },data.vol)
            ])
          }})
        }
        child.children = subChildren
        children.push(child)
      }
      buys.children = children
      this.tree.push(buys)

      children = []
      for(var i=0;i<tmp.stopSells.length;i++){
        node = tmp.stopSells[tmp.stopSells.length-i-1]
        var child = {price:node.price,vol:node.vol,expand:false,
          render:(h,{root,node,data})=>{
            return h('div',{
              style:{
                display:'inline-block',
                width:'100%',
              }
            },[
            h('p',{
              style:{
                backgroundColor:'pink',
                color:'black',
                fontWeight:'500',
                fontSize:'15px',
                height:'25px',
                lineHeight:'25px',
                width:'200px',
                float:'left'
              }
            },data.price),
            h('p',{
              style:{
                backgroundColor:'pink',
                color:'black',
                fontWeight:'500',
                fontSize:'15px',
                height:'25px',
                lineHeight:'25px',
                width:'200px',
                float:'left'
              }
            },data.vol)
            ])
          }
        }
        var subChildren = []
        var subNode = {}
        for(var j=0;j<node.orderitemList.length;j++){
          subNode = node.orderitemList[j]
          subChildren.push({traderId:subNode.traderId,vol:subNode.vol,render:(h,{root,node,data})=>{
            return h('div',{
              style:{
                display:'inline-block',
                width:'100%',
              }
            },[
            h('p',{
              style:{
                backgroundColor:'#a8a8a8',
                color:'black',
                fontWeight:'400',
                fontSize:'12px',
                height:'20px',
                lineHeight:'20px',
                width:'150px',
                float:'left'
              }
            },data.traderId),
            h('p',{
              style:{
                backgroundColor:'#a8a8a8',
                color:'black',
                fontWeight:'400',
                fontSize:'12px',
                height:'20px',
                lineHeight:'20px',
                width:'150px',
                float:'left'
              }
            },data.vol)
            ])
          }})
        }
        child.children = subChildren
        children.push(child)
      }
      stopSells.children = children
      this.tree.push(stopSells)

      children = []
      for(var i=0;i<tmp.stopBuys.length;i++){
        node = tmp.stopBuys[i]
        var child = {price:node.price,vol:node.vol,expand:false,
          render:(h,{root,node,data})=>{
            return h('div',{
              style:{
                display:'inline-block',
                width:'100%',
              }
            },[
            h('p',{
              style:{
                backgroundColor:'pink',
                color:'black',
                fontWeight:'500',
                fontSize:'15px',
                height:'25px',
                lineHeight:'25px',
                width:'200px',
                float:'left'
              }
            },data.price),
            h('p',{
              style:{
                backgroundColor:'pink',
                color:'black',
                fontWeight:'500',
                fontSize:'15px',
                height:'25px',
                lineHeight:'25px',
                width:'200px',
                float:'left'
              }
            },data.vol)
            ])
          }
        }
        var subChildren = []
        var subNode = {}
        for(var j=0;j<node.orderitemList.length;j++){
          subNode = node.orderitemList[j]
          subChildren.push({traderId:subNode.traderId,vol:subNode.vol,render:(h,{root,node,data})=>{
            return h('div',{
              style:{
                display:'inline-block',
                width:'100%',
              }
            },[
            h('p',{
              style:{
                backgroundColor:'#a8a8a8',
                color:'black',
                fontWeight:'400',
                fontSize:'12px',
                height:'20px',
                lineHeight:'20px',
                width:'150px',
                float:'left'
              }
            },data.traderId),
            h('p',{
              style:{
                backgroundColor:'#a8a8a8',
                color:'black',
                fontWeight:'400',
                fontSize:'12px',
                height:'20px',
                lineHeight:'20px',
                width:'150px',
                float:'left'
              }
            },data.vol)
            ])
          }})
        }
        child.children = subChildren
        children.push(child)
      }
      stopBuys.children = children
      this.tree.push(stopBuys)


    }
  },
  mounted(){
    this.$axios({
      method:'post',
      url:'http://localhost:8083/showDetail',
      data:{
        broker_id:this.broker_id,
        instrument_id:this.instrument_id
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
      var tmp = response.data
      this.construct(tmp)


    }).catch((error)=>{
      console.log(error)
    })
  }
}
</script>

<style>
.board{
  width:100%;
  height:100%;
}

.card-container{
  display:flex;
  align-items:center;
  justify-content:center
}

.card-item{
  font-size:15px;
  align-items:center;
  border-bottom:1px solid #a8a8a8;
  display:flex;
  flex-direction:row;
  height:30px;
}


.ivu-card-head{
  background-color:aquamarine
}

.ivu-card-body{
  padding:0px
}


</style>
