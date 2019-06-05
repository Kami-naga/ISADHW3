<template>
  <div>
    <Table
     :columns="booksForm"
     :data="booksData"
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
          width:'300px',
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
        {
          title:'卖出价',
          key:'sellPrice',
          align:'center'
        },
        {
          title:'买入价',
          key:'buyPrice',
          align:'center',
        },
        {
          title:'操作',
          align: 'center',
          width: '200px',
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
                    this.$router.push({ path: '/products/orderbook'})
                  }
                }
              },'查看')
            ])
          }
        }
      ],
      booksData:[
        {
          bookName:"Cu1109",
          buyPrice:109,
          sellPrice:120
        }
      ]
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
