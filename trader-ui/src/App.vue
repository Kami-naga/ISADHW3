<template>
  <div id="app">
    <div style="height:60px;">
      <Row>
        <Col span="12" style="height:60px;padding-left:60px;line-height:60px;font-size:40px;font-weight:700">I&#160;S&#160;A&#160;D</Col>
        <Col class="check-trades" span="3">
          <Button
            v-if="this.$store.state.user.role=='broker'"
            @click="addBook"
            type="info"
            size="large"
          >添加期货</Button>
          <Button
            v-if="this.$store.state.user.role=='trader'"
            @click="checkOrderitems"
            type="info"
            size="large"
          >{{isOrderitems?"返回上一级":"查看订单"}}</Button>
        </Col>
        <Col class="check-trades" span="3">
          <Button @click="checkTrades" type="info" size="large">{{isChecked?"返回上一级":"查看所有交易"}}</Button>
        </Col>
        <Col span="6">
          <login></login>
        </Col>
      </Row>
    </div>
    <router-view/>
    <Modal v-model="modalBook" title="添加期货" @on-ok="addbookOk" @on-cancel="addbookCancel">
      <div style="width:300px">
        <Row>
          <Col class="modal-row" span="6" offset="2">类型：</Col>
          <Col class="modal-row" span="16">
            <Select v-model="productId" filterable>
              <Option
                v-for="item in this.$store.state.products"
                :value="item.id"
                :key="item.id"
              >{{ item.ename }}</Option>
            </Select>
          </Col>
        </Row>
        <Row>
          <Col class="modal-row" span="6" offset="2">到期日：</Col>
          <Col class="modal-row" span="16">
            <DatePicker
              format="yyyy-MM-dd"
              type="date"
              placeholder="选择日期"
              @on-change="changePeriod"
            ></DatePicker>
          </Col>
        </Row>
      </div>
    </Modal>
  </div>
</template>

<script>
import login from "@/components/login";
export default {
  name: "App",
  components: {
    login
  },
  data() {
    return {
      modalBook: false,
      productId: "",
      period: ""
    };
  },
  methods: {
    changePeriod(e) {
      this.period = e;
    },
    addBook() {
      this.modalBook = true;
    },
    clearBook() {
      this.productId = "";
      this.period = "";
    },
    addbookOk() {
      console.log(this.productId, this.period);
      var temp = this.period.split('-')
      console.log(temp[1]+temp[2])

      this.$axios({
        method: "post",
        url: this.$store.state.port2 + "/api/instrument/addInstrument",
        data: {
          brokerId: this.$store.state.user.id,
          productId: this.productId,
          periodT: temp[1]+temp[2]
        },
        transformRequest: function(obj) {
          var str = [];
          for (var p in obj) {
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
          }
          return str.join("&");
        }
      })
        .then(response => {
          this.$Notice.success({
            title: "创建成功"
          });
        })
        .catch(error => {
          console.log(error);
        });

      this.modalBook = false;
      this.clearBook();
    },
    addbookCancel() {
      this.modalBook = false;
      this.clearBook();
    },
    checkTrades() {
      if (this.$route.path.slice(1).split("/")[0] === "orderblotter") {
        this.$router.go(-1);
        return;
      }
      if (this.$route.path.slice(1).split("/")[0] === "orderitems") {
        this.$router.replace("/orderblotter");
        return;
      }
      this.$router.push("/orderblotter");
    },
    checkOrderitems(){
      if (this.$route.path.slice(1).split("/")[0] === "orderitems") {
        this.$router.go(-1);
        return;
      }
      if (this.$route.path.slice(1).split("/")[0] === "orderblotter") {
        this.$router.replace("/orderitems");
        return;
      }
      this.$router.push("/orderitems");
    }
  },
  computed: {
    isChecked() {
      if (this.$route.path.slice(1).split("/")[0] === "orderblotter") {
        return true;
      }
      return false;
    },
    isOrderitems(){
      if (this.$route.path.slice(1).split("/")[0] === "orderitems") {
        return true;
      }
      return false;
    }
  },
  mounted() {
    this.$axios({
      method: "get",
      url: this.$store.state.port + "/api/product/all"
    })
      .then(response => {
        console.log(response);
        let temp = [];
        response.data.forEach(value => {
          let cell = {
            id: value.productId,
            name: value.name,
            ename: value.name
          };
          temp.push(cell);
        });
        this.$store.state.products = temp;
        this.$store.state.product = temp[0];
        this.$axios({
          method: "post",
          url: this.$store.state.port + "/api/broker/byProduct",
          data: {
            productId: 1
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
                productId: 1,
                brokerId: 1
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
              })
              .catch(error => {
                console.log(error);
              });
          })
          .catch(error => {
            console.log(error);
          });
      })
      .catch(error => {
        console.log(error);
      });
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.check-trades {
  height: 60px;
  display: flex;
  align-items: center;
  padding:15px
}

.modal-row {
  height: 40px;
  font-size: 15px;
  line-height: 40px;
}
</style>
