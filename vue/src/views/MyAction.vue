<template>
<div>
  <el-table :data="tableData" border :header-cell-style="{background:'#880000',color:'#ffffff'}" stripe style="width: 100%">
    <el-table-column prop="date" label="日期" width="180">
      <template slot-scope="scope">
        {{ scope.row.date| formatDate }}
      </template>
    </el-table-column>
    <el-table-column prop="name" label="活动名称" width="400"></el-table-column>
    <el-table-column prop="place" label="活动地点" width="300"></el-table-column>
    <el-table-column prop="introduce" label="活动介绍" width="200"></el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button type="danger" @click="QuitAction(scope.row.id)">退出活动 <i class="el-icon-error"></i></el-button>
      </template>
    </el-table-column>
  </el-table>
</div>
</template>

<script>
export default {
  name: "MyAction",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:30,
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      const user = JSON.parse(localStorage.getItem("user"))
      this.request.get("action/pageByUser/"+user.username,{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
        }
      }).then(res =>{
        this.tableData = res.object.records
        this.total = res.object.total
      })
    },
    QuitAction(actionId){
      const user =JSON.parse(localStorage.getItem("user"))
      this.request.post("/action/quit/username="+user.username+'/actionId='+actionId).then(res=>{
            if(res.code == 200){
              this.$message.success("退出成功")
              this.load()
            }else{
              this.$message.error("您已报名，无需再次报名")
              this.load();
            }
      })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
  },
  filters: {
    formatDate(timestamp) {
      const date = new Date(timestamp);
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, "0");
      const day = date.getDate().toString().padStart(2, "0");
      return `${year}-${month}-${day}`;
    },
  }
}
</script>

<style scoped>

</style>