<template>
  <div></div>
</template>

<script>
export default {
  data() {
    return {
      webSock: null,
    };
  },
  created() {
    this.initWebSocket()
  },
  destroyed: function() {
    this.webSocketClose()
  },
  methods: {
    initWebSocket() {
      const wsuri = "ws://localhost:8080/websocket/B1I1";
      this.webSock = new WebSocket(wsuri);
      this.webSock.onopen = this.webSocketOnOpen;
      this.webSock.onerror = this.webSocketOnError;
      this.webSock.onmessage = this.webSocketOnMessage;
      this.webSock.onclose = this.webSocketOnClose;
    },

    webSocketOnOpen() {
      console.log("连接成功");
    },

    webSocketOnError() {
      console.log("连接发生错误");
    },

    webSocketOnMessage(e) {
      console.log(e);
      // const data = JSON.parse(e.data);
      console.log(e.data);
    },

    webSocketSend(agentData) {
      this.webSock.send(agentData);
    },

    webSocketOnClose(e) {
      console.log("连接关闭 ("+e.code+")");
    }
  }
};
</script>
