<template>
  <div class="main">
    <div class="left"></div>
    <div class="right">

      <div class="message">
        <div class="history">
          <div class="msg-item" v-for="msg in msgList" :key="msg.id">
            <img class="header-img" :src="msg.img" alt="">
            <div class="msg-content">
              <h2>{{msg.author}}<small>{{msg.time | formatDate}}</small></h2>
              <p class="cont">{{msg.content}}</p>
            </div>
          </div>
        </div>
      </div>

      <div class="input-msg">
        <el-input type="textarea" v-model="message.content"  placeholder="请输入留言内容"></el-input>
        <el-button size="small" class="send-msg" @click="sendMsg" >发 送 留 言</el-button>
      </div>
    </div>
  </div>

</template>
<script>
export default {
  created() {
    this.load()
  },
  data() {
    return {
      message: {
        id: "",
        author: '',
        time: "",
        img: '',
        content: "",
      },
      msgList: [],
    }
  },
  methods: {
    load() {
      this.request.get("message/load").then(res => {
        this.msgList = res.object;
      })
    },
    sendMsg() {
      const user = JSON.parse(localStorage.getItem("user"))
      this.message.author = user.nickname;
      this.message.img = user.avatarUrl;
      this.request.post("message", this.message).then(res => {
        if (res.code == 200) {
          this.$message.success("留言成功")
          this.load();
        } else {
          this.$message.error("留言失败")
          this.load();
        }
      })
    },
  },
  filters: {
    formatDate(time) {
      const date = new Date(time);
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, "0");
      const day = date.getDate().toString().padStart(2, "0");
      return `${year}-${month}-${day}`;
    },
  }
}

</script>
<style scoped>
.main{
  width: 1402px;
  height: 800px;
  margin: 0 auto;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0,0,0,0.2);
}
.left{
  width: 500px;
  height: 800px;
  float: left;
  border-radius: 10px 0 0 10px;
  background-image: url("@/assets/jxxy/back.png");
  background-repeat: no-repeat;
  background-size: cover;
}
.right{
  width: 900px;
  height: 800px;
  float: right;
}

.input-msg {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-bottom: 20px;
  margin-left: 50px;
  margin-top: 25px;
  height: 150px;
  width: 800px;
}

.message {
  margin-top: 20px;
  margin-left: 50px;
  max-width: 800px;
  height: 625px ;
  padding: 20px;
  border: 1px rgb(0 0 0/10%) solid;
  border-radius: 3px;
  overflow: auto;
}


.send-msg {
  width: 200px;
  height: 40px;
  color: #ffffff;
  background-color: #880000;
  margin-top: 10px;
  font-family: "等线 Light";
}

.history {
  background-color: #fff;
  overflow: auto;
}
.msg-item{
  display: flex;
  padding: 10px;
  border-bottom: 1px dashed #888;
}
.msg-item img{
  width: 100px;
  height: 100px;
  border-radius: 10px;
}
.msg-item div{
  margin-left: 10px;
  width: 100%;
}
.msg-item div h2{
  font-size: 22px;
}
.msg-item div h2 small{
  font-size: 16px;
  color: #888;
  font-weight: 600;
  margin-left: 20px;
}
.msg-item div p.cont{
  font-size: 16px;
  color: #444;
  margin: 10px 0;
  word-wrap: break-word;
  word-wrap: break-word;
  white-space: pre-wrap;
  min-height: 50px;
}
</style>
