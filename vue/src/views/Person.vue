<template>
<div >
  <el-card style="width: 500px;">
    <el-form label-width="70px">
      <el-upload style="text-align: center;padding-bottom: 20px" class="avatar-uploader" action="http://localhost:9090/file/upload"
                 :show-file-list="false" :on-success="handleAvatarSuccess">
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>

      <el-form-item label="用户名："><el-input v-model="form.username" disabled autocomplete="off" ></el-input></el-form-item>
      <el-form-item label="昵称："><el-input v-model="form.nickname" autocomplete="off"></el-input></el-form-item>
      <el-form-item label="邮箱："><el-input v-model="form.email" autocomplete="off"></el-input></el-form-item>
      <el-form-item label="电话："><el-input v-model="form.phone" autocomplete="off"></el-input></el-form-item>
      <el-form-item label="地址："><el-input type="textarea" v-model="form.address" autocomplete="off"></el-input></el-form-item>
      <el-form-item><el-button type="primary" @click="saveUser">确 定</el-button></el-form-item>
    </el-form>
  </el-card>
</div>
</template>

<script>
export default {
  name: "Person",
  data(){
    return {
      form:{},
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
    }
  },
  created() {
    this.getUser().then(res=>{
      this.form = res
    })
  },
  methods:{
    async getUser(){
      return (await this.request.get("/user/username/"+this.user.username)).object
    },
    saveUser(){
      this.request.post("/user",this.form).then(res=>{
        if(res.code === "200"){
          this.$message.success("保存成功")
          this.$emit("refreshUser")
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    handleAvatarSuccess(res){
      console.log(res)
      this.form.avatarUrl = res
    },
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 2px dashed #d9d9d9;
  border-radius: 20px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.el-upload{
  border-radius: 30px;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>