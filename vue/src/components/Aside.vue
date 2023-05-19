<template>
  <el-menu :default-openeds="opens" style="min-height: 100%;overflow-x: hidden"
           :collapse="isCollapsed"
           background-color="#240000"
           text-color="#d3d3d3"
           active-text-color="#ffffff"
           :collapse-transition="false"
           class="el-menu-vertical-demo"
           router
  >
    <div style="height: 60px;line-height: 60px;text-align: center">
      <span style="font-size: 18px;color: white"><b>{{maintitle}}</b></span>
    </div>
    <div v-for="item in menus" :key="item.id+''">
      <div v-if="!item.path">
        <el-submenu :index="item.id.toString()">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
          <div  v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path.toString()">
              <i :class="subItem.icon"></i>
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>
  </el-menu>


</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapsed:Boolean,
    maintitle:String,
    index: {
      type: String,
      required: true
    },
  },
  data(){
    return{
      menus:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")):[],
      opens:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")).map(v=>v.id+""):[],
    }
  },
  methods: {
    logout() {
      // perform logout action here
      // for example, clear the user's session or token
      console.log("Logout button clicked");
    }
  }
}
</script>
