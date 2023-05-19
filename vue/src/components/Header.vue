<template>
<div style="font-size: 12px;line-height: 60px;display: flex">
  <div style="flex: 1;color: #ffffff">
    <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px;color: #ffffff" @click="collapse"></span>

    <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px;">
      <el-breadcrumb-item :to="'/Main'"><span style="color: #ffffff">首页</span></el-breadcrumb-item>
      <el-breadcrumb-item><span style="color: #ffffff">{{currentPathName}}</span></el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  <div style="margin-top: 5px;margin-right: 30px;"><img src="@/assets/jxxy/tag.jpg" style="height: 22px;width: 220px"></div>

  <el-dropdown style="width: 130px;cursor: pointer">
    <div style="display: inline-block">
      <img :src="user.avatarUrl" alt=""
            style="width: 30px;border-radius: 50%;position: relative;top:10px;right: 5px;margin-right: 5px">
      <span style="color: #ffffff">{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px;margin-right: 5px;color: #ffffff"></i>
    </div>

    <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
      <el-dropdown-item style="font-size: 14px;padding: 5px 0" >
        <router-link to="/person" style="text-decoration: none">个人信息</router-link>
      </el-dropdown-item>
      <el-dropdown-item style="font-size: 14px;padding: 5px 0">
        <span style="text-decoration: none" @click="logout">退出</span>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse:Function,
    user:Object
  },
  computed: {
    currentPathName() {
      return this.$store.state.currentPathName;
    }
  },
  data() {
    return {

    }
  },
  methods: {
    // collapse() {
    //   this.$emit("asideCollapse")
    // },
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      localStorage.removeItem("menus")
    }
  }
}
</script>

<style scoped>
el-breadcrumb-item{
  color: #ffffff;
}
</style>