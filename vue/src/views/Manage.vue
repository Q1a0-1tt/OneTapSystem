<template>
    <el-container>
      <el-aside :width="sidewidth+'px'" style="background-color: rgb(36 0 0);min-height: 100vh;box-shadow: 2px 0 6px rgb(0 21 41/35%) ">
        <Aside :is-collapsed="isCollapsed" :maintitle="maintitle" :index="'1'"/>
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #ccc;">
          <Header :collapse-btn-class="collapseBtnClass" :collapse="collapse" :user="user"/>
        </el-header>

        <el-main>
          <router-view @refreshUser="getUser"/>
        </el-main>
      </el-container>
    </el-container>
</template>

<style>
.el-header {
  background-color: rgb(136 6 6/100%);
  color: #333;
}

.el-aside {
  color: #333;
}

</style>

<script>
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";
import User from "@/views/User.vue";
export default {
  name: 'Home',
  data(){
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapsed:false,
      sidewidth:200,
      maintitle:'嘉兴学院',
      user:{},

    }
  },
  created() {
    this.getUser()
  },
  components:{
    Header,
    Aside,
    User,
  },
  methods:{
    collapse(){
      this.isCollapsed = ! this.isCollapsed
      if(this.isCollapsed){
        this.sidewidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.maintitle="嘉"
      }else{
        this.sidewidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.maintitle="嘉兴学院"
      }
    },
    getUser(){
      let username = localStorage.getItem("user")?
          JSON.parse(localStorage.getItem("user")).username:{}
      this.request.get("/user/username/"+username).then(res=>{
        this.user = res.object
      })
    }
  }
}
</script>
