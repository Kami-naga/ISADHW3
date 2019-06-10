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
