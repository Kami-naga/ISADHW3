<template>
  <div>
    <Table
     :columns="booksForm"
     :data="this.$store.state.booksData"
    ></Table>
  </div>
</template>

<script>
export default {
  name: 'result',
  methods: {
    initWebSocket() {
      const wsuri = "ws://localhost:8080/websocket/"+"B"+this.$store.state.book.brokerId+"I"+this.$store.state.book.id;
      this.$store.state.webSock = new WebSocket(wsuri);
      this.$store.state.webSock.onopen = this.webSocketOnOpen;
      this.$store.state.webSock.onerror = this.webSocketOnError;
      this.$store.state.webSock.onmessage = this.webSocketOnMessage;
      this.$store.state.webSock.onclose = this.webSocketOnClose;
    },

    webSocketOnOpen() {
      console.log("连接成功");
    },

    webSocketOnError() {
      console.log("连接发生错误");
    },

    webSocketOnMessage(e) {
      console.log(e.data)
      const temp = JSON.parse(""+e.data)
      this.orderBook = temp
      this.buys = temp.buysFive
      this.sells = temp.sellsFive
      this.orderBookId = temp.orderbookId
      this.$store.state.buys = temp.buysFive
      this.$store.state.sells = temp.sellsFive
    },

    webSocketSend(agentData) {
      this.$store.state.webSock.send(agentData);
    },

    webSocketOnClose() {
      console.log("连接关闭");
    }
  },
  data () {
    return {
      booksForm:[
        {
          title:'期货名称',
          key:'bookName',
          align:'center',
          render:(h,params)=>{
            return h("div", [
              h(
                "div",
                {
                  style: {
                    color: "#2d8cf0",
                    fontSize:"15px"
                  },
                },
                params.row.bookName
              )
            ])
          }
        },
        /*{
          title:'卖出价',
          key:'sellPrice',
          align:'center'
        },
        {
          title:'买入价',
          key:'buyPrice',
          align:'center',
        },*/
        {
          title:'操作',
          align: 'center',
          render:(h,params)=>{
            return h('div',[
              h('Button',{
                attrs:{
                  style:"width:80px;font-size:13px"
                },
                props:{
                  type:"success",
                  icon:'ios-send-outline',
                },
                on:{
                  click:()=>{
                    var bookId = params.row.id
                    for (var i=0;i<this.$store.state.booksData.length;i++){
                      if(this.$store.state.booksData[i].id===bookId){
                        this.$store.state.book = this.$store.state.booksData[i]
                        this.$axios({
                          method:'post',
                          url:this.$store.state.port+"/showDetail",
                          data:{
                            brokerId: this.$store.state.book.brokerId,
                            instrumentId : bookId,
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
                          this.$store.state.sells = response.data.sellsFive
                          this.$store.state.buys = response.data.buysFive
                          this.webSocketOnClose();
                          this.initWebSocket();
                          this.$router.push({ path: '/products/orderbook'})

                        }).catch((error)=>{
                          console.log(error)
                          this.$router.push({ path: '/products/orderbook'})
                        })
                        return
                      }
                    }
                  }
                }
              },'查看')
            ])
          }
        }
      ],

    }
  }
}
</script>

<style>
.ivu-table-overflowX{
  overflow-x:hidden
}

.ivu-tabs-bar{
  margin-bottom:0px;
}

.ivu-table-tip{
  overflow-x:hidden
}

.ivu-table-cell{
  padding-left:0px;
  padding-right:0px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  word-break: break-all;
  box-sizing: border-box;
  font-size:15px
}
</style>
