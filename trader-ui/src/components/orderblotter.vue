<template>
  <div class="blotter">
    <Row>
      <Col span="2" class="titleCell" style="border-left:2px solid black">
        TradeID
      </Col>
      <Col span="2" class="titleCell">
        Broker
      </Col>
      <Col span="3" class="titleCell">
        Product
      </Col>
      <Col span="2" class="titleCell">
        Period
      </Col>
      <Col span="2" class="titleCell">
        Price
      </Col>
      <Col span="1" class="titleCell">
        Qty
      </Col>
      <Col span="6">
        <Row>
          <Col span="24" class="titleSubCell" style="border-top:2px solid black">
            Initiator
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <Row>
              <Col span="10" class="titleSubCell">
                Trader
              </Col>
              <Col span="10" class="titleSubCell">
                Company
              </Col>
              <Col span="4" class="titleSubCell">
                Side
              </Col>
            </Row>
          </Col>
        </Row>
      </Col>
      <Col span="6"  style="border-right:2px solid black">
        <Row>
          <Col span="24" class="titleSubCell" style="border-top:2px solid black">
            Completion
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <Row>
              <Col span="10" class="titleSubCell">
                Trader
              </Col>
              <Col span="10" class="titleSubCell">
                Company
              </Col>
              <Col span="4" class="titleSubCell">
                Side
              </Col>
            </Row>
          </Col>
        </Row>
      </Col>
    </Row>

    <Row v-for="(trade,index) in this.$store.state.trades" :key="trade.trade_id">
      <Col span="2" class="contentCell" style="border-left:2px solid black" v-bind:class="{ 'contentBottom': index===trades.length-1}">
        {{trade.tradeId}}
      </Col>
      <Col span="2" class="contentCell" v-bind:class="{ 'contentBottom': index===trades.length-1}">
        {{trade.broker}}
      </Col>
      <Col span="3" class="contentCell" v-bind:class="{ 'contentBottom': index===trades.length-1}">
        {{trade.product}}
      </Col>
      <Col span="2" class="contentCell" v-bind:class="{ 'contentBottom': index===trades.length-1}">
        {{trade.periodT}}
      </Col>
      <Col span="2" class="contentCell" v-bind:class="{ 'contentBottom': index===trades.length-1}">
        {{trade.price}}
      </Col>
      <Col span="1" class="contentCell" v-bind:class="{ 'contentBottom': index===trades.length-1}">
        {{trade.qty}}
      </Col>
      <Col span="6">
        <Row>
          <Col span="24">
            <Row>
              <Col span="10" class="contentSubCell" v-bind:class="{ 'contentBottom': index===trades.length-1}">
                {{trade.initiator.otherSee==false&&trade.initiator.id!=userId?"":trade.initiator.name}}
              </Col>
              <Col span="10" class="contentSubCell" v-bind:class="{ 'contentBottom': index===trades.length-1}">
                {{trade.initiator.otherSee==false&&trade.initiator.id!=userId?"":trade.initiator.company}}
              </Col>
              <Col span="4" class="contentSubCell" v-bind:class="{ 'contentBottom': index===trades.length-1}">
                {{trade.initiator.orderSide}}
              </Col>
            </Row>
          </Col>
        </Row>
      </Col>
      <Col span="6"  style="border-right:2px solid black">
        <Row>
          <Col span="24">
            <Row>
              <Col span="10" class="contentSubCell" v-bind:class="{ 'contentBottom': index===trades.length-1}">
                {{trade.completion.otherSee==false&&trade.completion.id!=userId?"":trade.completion.name}}
              </Col>
              <Col span="10" class="contentSubCell" v-bind:class="{ 'contentBottom': index===trades.length-1}">
                {{trade.completion.otherSee==false&&trade.completion.id!=userId?"":trade.completion.company}}
              </Col>
              <Col span="4" class="contentSubCell" v-bind:class="{ 'contentBottom': index===trades.length-1}">
                {{trade.completion.orderSide}}
              </Col>
            </Row>
          </Col>
        </Row>
      </Col>
    </Row>
  </div>
</template>

<script>
export default {
  name: 'orderblotter',
  data(){
    return{

    }
  },
  mounted() {
    this.$axios({
      method: "get",
      url: this.$store.state.port2 + "/api/trade/all",
    })
      .then(response => {
        this.$store.state.trades = response.data.reverse()
        console.log(response.data)
      })
      .catch(error => {
        console.log(error);
      });
  },
  computed:{
    trades(){
      return this.$store.state.trades
    },
    userId(){
      return this.$store.state.user.id
    }
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
  font-size:15px;
  padding:5px
}

.titleSubCell{
  background-color:#5599ff;
  border:1px solid black;
  height:30px;
  font-size:15px;
  padding:5px
}

.contentCell{
  background-color:white;
  border:1px solid black;
  height:30px;
  font-size:15px;
  padding:5px
}

.contentSubCell{
  background-color:white;
  border:1px solid black;
  height:30px;
  font-size:15px;
  padding:5px
}

.contentBottom{
  border-bottom:2px solid black;
}

</style>
