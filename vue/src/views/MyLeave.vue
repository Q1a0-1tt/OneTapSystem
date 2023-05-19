<template>
  <div>
    <el-table :data="tableData" border :header-cell-style="{background:'#880000',color:'#ffffff'}" stripe style="width: 100%">
      <el-table-column prop="name" label="姓名" width="180"></el-table-column>
      <el-table-column prop="starttime" label="请假时间" width="300">
        <template slot-scope="scope">
          {{ scope.row.starttime| formatDate }}
        </template>
      </el-table-column>
      <el-table-column prop="finaltime" label="返回时间" width="300">
        <template slot-scope="scope">
          {{ scope.row.finaltime| formatDate }}
        </template>
      </el-table-column>
      <el-table-column prop="destination" label="目的地" width="200"></el-table-column>
      <el-table-column prop="reason" label="理由" width="200"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template slot-scope="scope">
          {{ scope.row.status | getStatus }}
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
      this.request.get("/leave/pageByUser/"+user.username,{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
        }
      }).then(res =>{
        this.tableData = res.object.records
        this.total = res.object.total
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
      const hours = date.getHours().toString().padStart(2, "0");
      const minutes = date.getMinutes().toString().padStart(2, "0");
      const seconds = date.getSeconds().toString().padStart(2, "0");
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    getStatus(status){
      if(status == 0){
        return "待审批"
      }else if(status == 1){
        return "通过"
      }else{
        return "拒绝"
      }
    }
  }
}
</script>

<style scoped>

</style>