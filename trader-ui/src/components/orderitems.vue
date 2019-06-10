<template>
  <div class="blotter">
    <Row>
      <Col  style="height:30px;line-height:30px;font-size:25px;font-weight:600">
        Limit Order
      </Col>
    </Row>
    <Row>
      <Col span="4" class="titleCell" style="border-left:2px solid black">
        OrderID
      </Col>
      <Col span="4" class="titleCell">
        Broker
      </Col>
      <Col span="3" class="titleCell">
        Product
      </Col>
      <Col span="4" class="titleCell">
        Period
      </Col>
      <Col span="4" class="titleCell">
        Price
      </Col>
      <Col span="2" class="titleCell">
        Qty
      </Col>
      <Col span="3" class="titleCell">
        Operation
      </Col>
    </Row>

    <Row v-for="(orderitem,index) in this.limitOrders" :key="orderitem.orderId[0]">
      <Col span="4" class="contentCell" style="border-left:2px solid black">
        {{orderitem.orderId[0]}}
      </Col>
      <Col span="4" class="contentCell">
        {{orderitem.broker}}
      </Col>
      <Col span="3" class="contentCell">
        {{orderitem.product}}
      </Col>
      <Col span="4" class="contentCell">
        {{orderitem.periodT}}
      </Col>
      <Col span="4" class="contentCell">
        {{orderitem.price}}
      </Col>
      <Col span="2" class="contentCell">
        {{orderitem.qty}}
      </Col>
      <Col span="3" class="contentCell" style="padding:0px;line-height:40px;text-align:center">
        <div @click="cancelOrder(orderitem.orderId,orderitem.broker,orderitem.qty)" style="width:100%;height:100%;cursor:pointer">
          取消订单
        </div>
      </Col>
    </Row>
    <Row style="margin-top:40px">
      <Col  style="height:30px;line-height:30px;font-size:25px;font-weight:600">
        Stop Order
      </Col>
    </Row>
    <Row>
      <Col span="4" class="titleCell" style="border-left:2px solid black">
        OrderID
      </Col>
      <Col span="4" class="titleCell">
        Broker
      </Col>
      <Col span="3" class="titleCell">
        Product
      </Col>
      <Col span="4" class="titleCell">
        Period
      </Col>
      <Col span="4" class="titleCell">
        Price
      </Col>
      <Col span="2" class="titleCell">
        Qty
      </Col>
      <Col span="3" class="titleCell">
        Operation
      </Col>
    </Row>

    <Row v-for="(orderitem,index) in this.stopOrders" :key="orderitem.orderId[0]">
      <Col span="4" class="contentCell" style="border-left:2px solid black">
        {{orderitem.orderId[0]}}
      </Col>
      <Col span="4" class="contentCell">
        {{orderitem.broker}}
      </Col>
      <Col span="3" class="contentCell">
        {{orderitem.product}}
      </Col>
      <Col span="4" class="contentCell">
        {{orderitem.periodT}}
      </Col>
      <Col span="4" class="contentCell">
        {{orderitem.price}}
      </Col>
      <Col span="2" class="contentCell">
        {{orderitem.qty}}
      </Col>
      <Col span="3" class="contentCell" style="padding:0px;line-height:40px;text-align:center">
        <div @click="cancelOrder(orderitem.orderId,orderitem.broker,orderitem.qty)" style="width:100%;height:100%;cursor:pointer">
          取消订单
        </div>
      </Col>
    </Row>
    <Modal v-model="toCancel" title="取消订单" @on-ok="cancelOk" @on-cancel="cancelCancel">
      <div style="width:500px">
        <Row>
          <Col span="3" class="orderCell" style="border-right:0px">
            OrderID
          </Col>
          <Col span="19" class="orderCell" style="border-left:0px;padding-left:15px">
            {{orderId[0]}}
          </Col>
        </Row>
        <Row>
          <Col span="3" class="orderCell" style="border-right:0px">
            Broker
          </Col>
          <Col span="19" class="orderCell" style="border-left:0px;">
            {{broker}}
          </Col>
        </Row>
        <Row>
          <Col span="3" class="orderCell" style="border-right:0px">
            Qty
          </Col>
          <Col span="19" class="orderCell" style="border-left:0px;">
            {{qty}}
          </Col>
        </Row>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  name: 'orderitems',
  data(){
    return{
      limitOrders:[],
      stopOrders:[],
      toCancel:false,
      orderId:[],
      broker:"",
      qty:0
    }
  },
  methods:{
    cancelOrder(orderId,broker,qty){
      this.orderId = orderId
      this.broker = broker
      this.qty = qty
      this.toCancel = true
    },
    cancelOk(){
      //发送请求
      this.$Notice.success({
        title: "取消订单成功，剩余" + parseInt(this.qty*(1- 0.5*Math.random())) +"单位未交易"
      });
      this.cancelCancel()
    },
    cancelCancel(){
      this.orderId = []
      this.broker = ""
      this.toCancel = false,
      this.qty = 0
    }
  },
  mounted() {
    var limitOrders = []
    var stopOrders = []
    for(var i=0;i<this.$store.state.orderitems.length;i++){
      var item = this.$store.state.orderitems[i]
      var nodeId = item.node_id
      if(nodeId.charAt(nodeId.length-1)==='S'){
        //stop
        var sign = nodeId+item.timeSign
        var flag = true
        for(var j=0;j<stopOrders.length;j++){
          if(sign===stopOrders[j].sign){
            var tmp = stopOrders[j]
            tmp.qty = tmp.qty + item.vol
            if(item.orderId<tmp.orderId[0]){
              tmp.orderId.unshift(item.orderId)
            }
            else{
              tmp.orderId.push(item.orderId)
            }
            stopOrders[j] = tmp
            flag = false
            break
          }
        }
        if(flag){
          var tmp = nodeId.split('P')
          var str = tmp[1]
          str = str.substr(0,str.length-1)
          stopOrders.push({
            orderId:[item.orderId],
            broker:item.broker,
            product:item.product,
            qty:item.vol,
            periodT:item.periodT,
            price:str,
            sign:sign
          })
        }
      }
      else{
        //limit
        var sign = nodeId+item.timeSign
        var flag = true
        for(var j=0;j<limitOrders.length;j++){
          if(sign===limitOrders[j].sign){
            var tmp = limitOrders[j]
            tmp.qty = tmp.qty + item.vol
            if(item.orderId<tmp.orderId[0]){
              tmp.orderId.unshift(item.orderId)
            }
            else{
              tmp.orderId.push(item.orderId)
            }
            limitOrders[j] = tmp
            flag=false
            break
          }
        }
        if(flag){
          var tmp = nodeId.split('P')
          var str = tmp[1]
          limitOrders.push({
            orderId:[item.orderId],
            broker:item.broker,
            product:item.product,
            qty:item.vol,
            periodT:item.periodT,
            price:str,
            sign:sign
          })
        }
      }
    }
    this.limitOrders = limitOrders
    this.stopOrders = stopOrders

    /*this.$axios({
      method: "get",
      url: this.$store.state.port2 + "/api/orderitems",
      data:{
        traderId:this.$store.state.user.id
      },
      transformRequest:function(obj) {
  　　　var str = [];
  　　　for ( var p in obj) {
  　　　　str.push(encodeURIComponent(p) + "="
  　　　　+ encodeURIComponent(obj[p]));
  　　　}
  　　　return str.join("&");
  　　}
    })
      .then(response => {
        this.$store.state.orderitems = response.data
        console.log(response.data)
      })
      .catch(error => {
        console.log(error);
      });*/
  },
  computed:{
  }
}
</script>

<style scoped>
.blotter{
  width:100%;
  padding-left:50px;
  padding-right:50px;
}

.titleCell{
  background-color:#5599ff;
  border:1px solid black;
  border-top:2px solid black;
  height:60px;
  font-size:20px;
  padding:10px
}

.contentCell{
  background-color:white;
  border:1px solid black;
  height:40px;
  font-size:20px;
  padding:10px
}

.contentBottom{
  border-bottom:2px solid black;
}

.orderCell{
  height:25px;
  line-height:25px;
  font-size:15px
}

</style>
