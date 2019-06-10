<template>
  <div class="layout">
    <Layout>
      <Sider class="sider" width="150">
        <div class="product-title">上市品种</div>
        <Menu
          style="width:150px"
          :active-name="this.$store.state.product.id"
          @on-select="productSelect"
        >
          <MenuItem v-for="(item,index) in this.$store.state.products" :key="index" :name="item.id">
            <span>{{item.name}}</span>
          </MenuItem>
        </Menu>
      </Sider>
      <Layout :style="{marginLeft: '150px'}">
        <Header class="header">
          <Select
            style="width:200px"
            :placeholder="this.$store.state.broker.name"
            @on-change="changeBroker"
            filterable
          >
            <Option
              v-for="item in this.$store.state.brokers"
              :value="item.id"
              :key="item.id"
            >{{ item.name }}</Option>
          </Select>
          <Select
            v-if="orderbooksPage==false"
            style="width:200px;margin-left:50px"
            :placeholder="this.$store.state.book.bookName"
            @on-change="changeBook"
            filterable
          >
            <Option
              v-for="item in this.$store.state.booksData"
              :value="item.id"
              :key="item.id"
            >{{ item.bookName }}</Option>
          </Select>
        </Header>
        <Content>
          <router-view/>
        </Content>
      </Layout>
    </Layout>
  </div>
</template>

<script>
export default {
  name: "products",
  data() {
    return {};
  },
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
    },
    changeBroker(e) {
      for (var i = 0; i < this.$store.state.brokers.length; i++) {
        if (this.$store.state.brokers[i].id === e) {
          this.$store.state.broker = this.$store.state.brokers[i];
          this.$axios({
            method: "post",
            url: this.$store.state.port + "/api/instrument/byProductAndBroker",
            data: {
              productId: this.$store.state.product.id,
              brokerId: e
            },
            transformRequest: function(obj) {
              var str = [];
              for (var p in obj) {
                str.push(
                  encodeURIComponent(p) + "=" + encodeURIComponent(obj[p])
                );
              }
              return str.join("&");
            }
          })
            .then(response => {
              let temp = [];
              response.data.forEach(value => {
                let cell = {
                  id: value.instrumentId,
                  bookName: value.product.name + value.periodT,
                  brokerId: value.broker.brokerId
                };
                temp.push(cell);
              });
              this.$store.state.booksData = temp;
              console.log(temp);
              if (this.$route.path.slice(1).split("/")[1] === "orderbook") {
                this.$router.push("/products/orderbooks")
              }
            })
            .catch(error => {
              console.log(error);
            });
          return;
        }
      }
    },
    changeBook(e) {
      for (var i = 0; i < this.$store.state.booksData.length; i++) {
        if (this.$store.state.booksData[i].id === e) {
          this.$store.state.book = this.$store.state.booksData[i];
          this.$axios({
            method: "post",
            url: this.$store.state.port + "/showDetail",
            data: {
              instrumentId: e,
              brokerId: this.$store.state.broker.id
            },
            transformRequest: function(obj) {
              var str = [];
              for (var p in obj) {
                str.push(
                  encodeURIComponent(p) + "=" + encodeURIComponent(obj[p])
                );
              }
              return str.join("&");
            }
          })
            .then(response => {
              console.log(response);
              this.$store.state.sells = response.data.sellsFive;
              this.$store.state.buys = response.data.buysFive;
              this.webSocketOnClose();
              this.initWebSocket();
            })
            .catch(error => {
              console.log(error);
            });
          return;
        }
      }
    },
    productSelect(productId) {
      for (var i = 0; i < this.$store.state.products.length; i++) {
        if (this.$store.state.products[i].id === productId) {
          this.$store.state.product = this.$store.state.products[i];
          this.$axios({
            method: "post",
            url: this.$store.state.port + "/api/broker/byProduct",
            data: {
              productId: productId
            },
            transformRequest: function(obj) {
              var str = [];
              for (var p in obj) {
                str.push(
                  encodeURIComponent(p) + "=" + encodeURIComponent(obj[p])
                );
              }
              return str.join("&");
            }
          })
            .then(response => {
              let temp = [];
              response.data.forEach(value => {
                let cell = { id: value.brokerId, name: value.name };
                temp.push(cell);
              });
              this.$store.state.brokers = temp;
              this.$store.state.broker = temp[0];
              this.$axios({
                method: "post",
                url:
                  this.$store.state.port + "/api/instrument/byProductAndBroker",
                data: {
                  productId: productId,
                  brokerId: this.$store.state.broker.id
                },
                transformRequest: function(obj) {
                  var str = [];
                  for (var p in obj) {
                    str.push(
                      encodeURIComponent(p) + "=" + encodeURIComponent(obj[p])
                    );
                  }
                  return str.join("&");
                }
              })
                .then(response => {
                  let temp = [];
                  response.data.forEach(value => {
                    let cell = {
                      id: value.instrumentId,
                      bookName: value.product.name + value.periodT,
                      brokerId: value.broker.brokerId
                    };
                    temp.push(cell);
                  });
                  this.$store.state.booksData = temp;
                  console.log(temp);
                  if (this.$route.path.slice(1).split("/")[1] === "orderbook") {
                    this.$router.push("/products/orderbooks")
                  }
                })
                .catch(error => {
                  console.log(error);
                });
            })
            .catch(error => {
              console.log(error);
            });
          return;
        }
      }
    }
  },
  computed: {
    orderbooksPage() {
      if (this.$route.path.slice(1).split("/")[1] === "orderbooks") {
        return true;
      }
      return false;
    }
  },
  mounted() {}
};
</script>

<style scoped>
.layout {
  border-top: 2px solid #d0d0d0;
}

.product-title {
  height: 50px;
  font-size: 20px;
  text-align: center;
  line-height: 50px;
  color: #fff;
  background-color: #2d8cf0;
}

.ivu-menu-vertical .ivu-menu-item {
  padding-left: 15px;
  padding-right: 15px;
  padding-top: 14px;
  padding-bottom: 8px;
  text-align: center;
  font-size: 20px;
}
::-webkit-scrollbar {
  display: none;
}
.sider {
  height: calc(100vh - 70px);
  position: fixed;
  left: 0;
  overflow: auto;
}
.header {
  height: 50px;
  background-color: #f0faff;
}
.ivu-layout-header {
  line-height: 50px;
  font-size: 20px;
}
</style>
