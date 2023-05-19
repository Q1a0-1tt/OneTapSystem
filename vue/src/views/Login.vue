<template>
  <div class="wrapper">
    <div class="login-box">
      <div class="left">
        <el-form :model="user" :rules="rules" ref="userForm" class="left-form">
          <el-form-item style="text-align: center">
            <h2>嘉兴学院一站式社区网上预约系统</h2>
          </el-form-item>

          <el-form-item prop="username" style="margin-top:50px">
            <h4>账 号 :</h4>
            <el-input class="input-field" size=" small " prefix-icon="el-icon-user" placeholder="用户名" v-model="user.username" ></el-input>
          </el-form-item>

          <el-form-item prop="password" style="margin-top:-20px">
            <h4>密 码 :</h4>
            <el-input class="input-field" size=" small " prefix-icon="el-icon-lock" placeholder="密码" show-password v-model="user.password"></el-input>
          </el-form-item>

          <el-form-item style="margin-top: 20px">
            <el-button size="medium" @click="login" style="min-width:320px;background-color: #880000;color: #ffffff;font-family: '等线 Light'">登 录</el-button>
          </el-form-item>

          <el-form-item style="margin-top: 10px">
            <el-button size="medium" @click="$router.push('/register')" style="min-width:320px;background-color: #ffffff;color: #880000;font-family: '等线 Light'">注 册</el-button>
          </el-form-item>

        </el-form>
      </div>
      <div class="right">
        <div class="logo"></div>
      </div>

    </div>
  </div>
</template>

<script>
import {setRoutes} from "@/router";

export default {
  name: "Login",
  data(){
    return{
      user:{},
      rules: {
        username: [
          { required: true, message: ' ', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: ' ', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },

  methods:{
    login(){
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          this.request.post("/user/login", this.user).then(res => {
            if (res.code == "200") {
              localStorage.setItem("user", JSON.stringify(res.object))
              localStorage.setItem("menus", JSON.stringify(res.object.menus))
              //动态设置路由
              setRoutes()
              this.$router.push('/Main')
              this.$message.success("登陆成功")

            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style scoped>
.logo{
  height: 60px;
  width: 200px;
  margin-top: 715px;
  margin-left: 30px;
  background-image: url("@/assets/jxxy/logo.png");
  background-repeat: no-repeat;
}
.left{
  padding:75px ;
  width: 550px;
  height: 800px;
  float: left;
  font-family: '等线 Light'
}
.left-form {
  margin-top: 100px;
  padding: 40px;
}
.right{
  width: 650px;
  height: 800px;
  float: right;
  background: #880000;
  background-size: cover;
  background-image: url("@/assets/jxxy/back.png");
  background-repeat: no-repeat;
  box-shadow: 0 0 10px rgba(0,0,0,0.2);
  border-radius:  0 10px 10px 3px;
}
.wrapper{
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fafafa;
}

.login-box{
  display: flex;
  align-items: center;
  background-color: #fff;
  width: 1200px;
  box-shadow: 0 0 10px rgba(0,0,0,0.2);
  border-radius: 10px;
}


.input-field {
  margin-bottom: 20px;
  margin-top: 20px;
}
.btn-group button {
  width: 100%;
  margin-top: 20px;
}

</style>
