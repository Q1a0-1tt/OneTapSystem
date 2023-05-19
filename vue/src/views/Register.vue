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
            <el-input placeholder="用户名" v-model="user.username" class="input"></el-input>
          </el-form-item>
          <el-form-item prop="password" style="margin-top:-18.5px">
            <h4>密 码 :</h4>
            <el-input placeholder="密码" v-model="user.password" show-password class="input"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword" style="margin-top:-18.5px">
            <h4>确 认 密 码 :</h4>
            <el-input placeholder="确认密码" v-model="user.confirmPassword" show-password class="input"></el-input>
          </el-form-item>
          <el-form-item style="margin-top: 20px">
            <el-button size="medium" autocomplete="off" @click="register" style="min-width:320px;background-color: #880000;color: #ffffff;font-family: '等线 Light'">注册</el-button>
          </el-form-item>

          <el-form-item style="margin-top: 10px">
            <el-button size="medium" autocomplete="off" @click="$router.push('/login')" style="min-width:320px;background-color: #ffffff;color: #880000;font-family: '等线 Light'">取消</el-button>
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
        confirmPassword: [
          { required: true, message: ' ', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },

  methods:{
    register(){
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          if(this.user.password !== this.user.confirmPassword){
            this.$message.error("两次输入密码不一致")
            return false
          }
          this.request.post("/user/register", this.user).then(res => {
            if (res.code === "200") {
              this.$message.success("注册成功")
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

<style>
.logo{
  height: 60px;
  width: 200px;
  margin-top: 715px;
  margin-left: 30px;
  background-image: url("@/assets/jxxy/logo.png");
  background-repeat: no-repeat;
}
.wrapper{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}
.input {
  margin-bottom: 15px;
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

.btn-group button {
  width: 100%;
  margin-top: 20px;
}

</style>