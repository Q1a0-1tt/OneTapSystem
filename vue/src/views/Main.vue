<template>
  <div>

    <div class="block">
      <el-carousel height="440px">
        <el-carousel-item v-for="item in imgList" :key="item.id">
          <img :src="item.idView" class="image">
        </el-carousel-item>
      </el-carousel>
    </div>

    <el-divider content-position="left">更多活动</el-divider>
    git push -f origin master
    <el-table :data="tableData" :header-cell-style="{background:'#880000',color:'#ffffff'}" border stripe style="width: 100%">
      <el-table-column prop="date" label="日期" width="180">
        <template slot-scope="scope">
          {{ scope.row.date| formatDate }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="活动名称" width="400"></el-table-column>
      <el-table-column prop="place" label="活动地点" width="300"></el-table-column>
      <el-table-column prop="joinpeople" label="参与人数" width="80" ></el-table-column>
      <el-table-column prop="people" label="需求人数" width="80"></el-table-column>
      <el-table-column prop="introduce" label="活动介绍" width="300"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" @click="JoinAction(scope.row.id)">参加 <i class="el-icon-s-flag"></i></el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[6,12,18]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>


</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:6,
      multipleSelection:[],
      imgList: [
        {id:0,idView:require("@/assets/jxxy/jxxy01.jpg")},
        {id:1,idView:require("@/assets/jxxy/jxxy02.jpg")},
        {id:2,idView:require("@/assets/jxxy/jxxy03.jpg")},
        {id:3,idView:require("@/assets/jxxy/jxxy04.jpg")},
      ]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("action/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
        }
      }).then(res =>{
        this.tableData = res.object.records
        this.total = res.object.total
      })
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum;
      this.load();
    },
    JoinAction(actionId){
      const user =JSON.parse(localStorage.getItem("user"))
      this.request.post("/action/join/username="+user.username+'/actionId='+actionId).then(res=>{
        if(res.code == 200){
          this.$message.success("报名成功")
          this.load()
        }else{
          this.$message.error("您已报名，无需再次报名")
          this.load();
        }
      })
    }
  },
  filters: {
    formatDate(timestamp) {
      const date = new Date(timestamp);
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, "0");
      const day = date.getDate().toString().padStart(2, "0");
      return `${year}-${month}-${day}`;
    },
  },
}
</script>

<style scoped>
img {
  position: absolute;
  width: 100%;
  height: 100%;
}
</style>