<template>
  <div style="background-color:white">
    <Row>
      <Col span="11">
        <Row>
          <Col span="3" class="bookCell bookTitle">
            Level
          </Col>
          <Col span="6" class="bookCell bookTitle">
            Buy Vol
          </Col>
          <Col span="6" class="bookCell bookTitle">
            Price
          </Col>
          <Col span="6" class="bookCell bookTitle">
            Sell Vol
          </Col>
          <Col span="3" class="bookCell bookTitle">
            Level
          </Col>
        </Row>
        <Row v-for="(node,index) in sellList" :key="node.price">
          <Col span="3" class="bookCell buyCell">
          </Col>
          <Col span="6" class="bookCell buyCell">
          </Col>
          <Col span="6" class="bookCell sellPrice" v-bind:class="{ 'cursorCell': sellList.length-index===1}">
            {{node.price}}
          </Col>
          <Col span="6" class="bookCell sellCell">
            {{node.vol}}
          </Col>
          <Col span="3" class="bookCell sellCell">
            {{sellList.length-index}}
          </Col>
        </Row>
        <Row v-for="(node,index) in buyList" :key="node.price">
          <Col span="3" class="bookCell buyCell">
            {{1+index}}
          </Col>
          <Col span="6" class="bookCell buyCell">
            {{node.vol}}
          </Col>
          <Col span="6" class="bookCell buyPrice" v-bind:class="{ 'cursorCell': 1+index===1}">
            {{node.price}}
          </Col>
          <Col span="6" class="bookCell sellCell">
          </Col>
          <Col span="3" class="bookCell sellCell">
          </Col>
        </Row>
      </Col>
      <Col span="12" offset="1">
        <Tabs v-model="currentTab" @on-click="currentTabChanged" type="card">
          <TabPane label="Market Order" name="market">
            <Row>
              <Col span="22" class="orderCell onTop" >
                Market Order
              </Col>
            </Row>
            <Row>
              <Col span="11" class="orderCell">
                {{instrument_name}}
              </Col>
              <Col span="11" class="orderCell" style="padding-left:0px">
                <button @click="changeSide" style="padding-left:10px;border-left:none;border-style:none;background-color:#fff;width:100%;text-align:left;height:100%;">
                  {{side}}
                </button>
              </Col>
            </Row>
            <Row>
              <Col span="3" class="orderCell" style="border-right:0px">
                Broker
              </Col>
              <Col span="19" class="orderCell" style="border-left:0px;">
                {{broker_name}}
              </Col>
            </Row>
            <Row>
              <Col span="1" class="orderCell" style="border-right:0px">
                Qty
              </Col>
              <Col span="21" class="orderCell" style="border-left:0px;padding-left:30px">
                <input placeholder="请输入数量" v-model="qty" style="height:90%;outline:none;width:100%;text-align:left;border-style:none"/>
              </Col>
            </Row>
            <Row>
              <Col span="22" class="orderCell" style="display:flex;align-items:center;">
                <Checkbox style="width:100%;font-size:20px" size="large" v-model="confirm">&#160;I confirm the trade is correct</Checkbox>
              </Col>
            </Row>
            <Row>
              <Col span="11" class="orderCell" style="padding-left:0px">
                <button @click="ok" style="padding-left:10px;border-left:none;border-style:none;background-color:#fff;width:100%;text-align:left;height:100%;">
                  OK
                </button>
              </Col>
              <Col span="11" class="orderCell" style="padding-left:0px">
                <button @click="cancel" style="padding-left:10px;border-left:none;border-style:none;background-color:#fff;width:100%;text-align:left;height:100%;">
                  Cancel
                </button>
              </Col>
            </Row>
          </TabPane>
          <TabPane label="Limit Order" name="limit">
            <Row>
              <Col span="22" class="orderCell onTop" >
                Limit Order
              </Col>
            </Row>
            <Row>
              <Col span="11" class="orderCell">
                {{instrument_name}}
              </Col>
              <Col span="11" class="orderCell" style="padding-left:0px">
                <button @click="changeSide" style="padding-left:10px;border-left:none;border-style:none;background-color:#fff;width:100%;text-align:left;height:100%;">
                  {{side}}
                </button>
              </Col>
            </Row>
            <Row>
              <Col span="3" class="orderCell" style="border-right:0px">
                Broker
              </Col>
              <Col span="19" class="orderCell" style="border-left:0px;">
                {{broker_name}}
              </Col>
            </Row>
            <Row>
              <Col span="1" class="orderCell" style="border-right:0px">
                Qty
              </Col>
              <Col span="10" class="orderCell" style="border-left:0px;padding-left:30px">
                <input placeholder="请输入数量" v-model="qty" style="height:90%;outline:none;width:100%;text-align:left;border-style:none"/>
              </Col>
              <Col span="2" class="orderCell" style="border-right:0px">
                Price
              </Col>
              <Col span="9" class="orderCell" style="border-left:0px;padding-left:15px">
                <input placeholder="请输入价位" v-model="price" style="height:90%;outline:none;width:100%;text-align:left;border-style:none"/>
              </Col>
            </Row>
            <Row>
              <Col span="22" class="orderCell" style="display:flex;align-items:center;">
                <Checkbox style="width:100%;font-size:20px" size="large" v-model="confirm">&#160;I confirm the trade is correct</Checkbox>
              </Col>
            </Row>
            <Row>
              <Col span="11" class="orderCell" style="padding-left:0px">
                <button @click="ok" style="padding-left:10px;border-left:none;border-style:none;background-color:#fff;width:100%;text-align:left;height:100%;">
                  OK
                </button>
              </Col>
              <Col span="11" class="orderCell" style="padding-left:0px">
                <button @click="cancel" style="padding-left:10px;border-left:none;border-style:none;background-color:#fff;width:100%;text-align:left;height:100%;">
                  Cancel
                </button>
              </Col>
            </Row>
          </TabPane>
          <TabPane label="Stop Order" name="stop">
            <Row>
              <Col span="22" class="orderCell onTop" >
                Stop Order
              </Col>
            </Row>
            <Row>
              <Col span="11" class="orderCell">
                {{instrument_name}}
              </Col>
              <Col span="11" class="orderCell" style="padding-left:0px">
                <button @click="changeSide" style="padding-left:10px;border-left:none;border-style:none;background-color:#fff;width:100%;text-align:left;height:100%;">
                  {{side}}
                </button>
              </Col>
            </Row>
            <Row>
              <Col span="3" class="orderCell" style="border-right:0px">
                Broker
              </Col>
              <Col span="19" class="orderCell" style="border-left:0px;">
                {{broker_name}}
              </Col>
            </Row>
            <Row>
              <Col span="1" class="orderCell" style="border-right:0px">
                Qty
              </Col>
              <Col span="10" class="orderCell" style="border-left:0px;padding-left:30px">
                <input placeholder="请输入数量" v-model="qty" style="height:90%;outline:none;width:100%;text-align:left;border-style:none"/>
              </Col>
              <Col span="2" class="orderCell" style="border-right:0px">
                Price
              </Col>
              <Col span="9" class="orderCell" style="border-left:0px;padding-left:15px">
                <input placeholder="请输入价位" v-model="price" style="height:90%;outline:none;width:100%;text-align:left;border-style:none"/>
              </Col>
            </Row>
            <Row>
              <Col span="22" class="orderCell" style="display:flex;align-items:center;">
                <Checkbox style="width:100%;font-size:20px" size="large" v-model="confirm">&#160;I confirm the trade is correct</Checkbox>
              </Col>
            </Row>
            <Row>
              <Col span="11" class="orderCell" style="padding-left:0px">
                <button @click="ok" style="padding-left:10px;border-left:none;border-style:none;background-color:#fff;width:100%;text-align:left;height:100%;">
                  OK
                </button>
              </Col>
              <Col span="11" class="orderCell" style="padding-left:0px">
                <button @click="cancel" style="padding-left:10px;border-left:none;border-style:none;background-color:#fff;width:100%;text-align:left;height:100%;">
                  Cancel
                </button>
              </Col>
            </Row>
          </TabPane>
          <TabPane label="Cancel Order" name="cancel">
            <Row>
              <Col span="22" class="orderCell onTop" >
                Cancel Order
              </Col>
            </Row>
            <Row>
              <Col span="3" class="orderCell" style="border-right:0px">
                OrderID
              </Col>
              <Col span="19" class="orderCell" style="border-left:0px;padding-left:15px">
                <input placeholder="请输入orderID" v-model="orderId" style="height:90%;outline:none;width:100%;text-align:left;border-style:none"/>
              </Col>
            </Row>
            <Row>
              <Col span="3" class="orderCell" style="border-right:0px">
                Broker
              </Col>
              <Col span="19" class="orderCell" style="border-left:0px;">
                {{broker_name}}
              </Col>
            </Row>
            <Row>
              <Col span="11" class="orderCell" style="padding-left:0px">
                <button @click="ok" style="padding-left:10px;border-left:none;border-style:none;background-color:#fff;width:100%;text-align:left;height:100%;">
                  OK
                </button>
              </Col>
              <Col span="11" class="orderCell" style="padding-left:0px">
                <button @click="cancel" style="padding-left:10px;border-left:none;border-style:none;background-color:#fff;width:100%;text-align:left;height:100%;">
                  Cancel
                </button>
              </Col>
            </Row>
          </TabPane>
        </Tabs>
      </Col>
    </Row>
  </div>
</template>

<script>
export default {
  name: 'result',
  data () {
    return {
      sells:[
        {
          price:1250,
          vol:50
        },
        {
          price:1260,
          vol:60
        },
        {
          price:1270,
          vol:70
        },
        {
          price:1280,
          vol:80
        },
        {
          price:1290,
          vol:90
        },
        {
          price:1300,
          vol:100
        }
      ],
      buys:[
        {
          price:1240,
          vol:140
        },
        {
          price:1230,
          vol:130
        },
        {
          price:1220,
          vol:120
        },
        {
          price:1210,
          vol:110
        }
      ],
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
      confirm:false,
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
      this.confirm=false
    },
    ok(){

    },
    cancel(){

    }
  },
  computed:{
    sellList:function(){
      var sellList = []
      for(var i=0; i<this.sells.length && i!=5;i++){
        sellList.push(this.sells[i])
      }
      return sellList.reverse()
    },
    buyList:function(){
      var buyList = []
      for(var i=0; i<this.buys.length && i!=5;i++){
        buyList.push(this.buys[i])
      }
      return buyList
    }
  },
}
</script>

<style scoped>
.bookCell{
  height:30px;
  font-size:20px;
  text-align:center;
  line-height:30px;
  border:1px solid black
}

.bookTitle{
  font-weight:600;
  background-color:white;
}

.sellPrice{
  background-color:#aaaaaa;
  color:blue
}

.buyPrice{
  background-color:#aaaaaa;
  color:red
}

.sellCell{
  background-color:#ffbb66;
}

.buyCell{
  background-color:#99bbff;
}

.cursorCell{
  background-color:#ffff33
}

.orderCell{
  border:1px solid black;
  height:30px;
  line-height:30px;
  font-size:20px;
  padding-left:10px;
}

.onTop{
  <!--margin-top:12px;-->
  background-color:#5599ff;
  font-weight:600
}
</style>
