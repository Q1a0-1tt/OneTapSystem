<template>
  <div>
    <div style="margin-bottom: 20px;">
      <el-radio-group v-model="radio" @change="pageChange">
        <el-radio-button label="待审核" value="0"></el-radio-button>
        <el-radio-button label="审核通过" value="1" ></el-radio-button>
        <el-radio-button label="审核失败" value="2"></el-radio-button>
      </el-radio-group>
    </div>

    <el-table :data="tableData" border stripe :header-cell-style="{background:'#880000',color:'#ffffff'}" style="width: 100%">
      <el-table-column prop="name" label="姓名" width="180"></el-table-column>
      <el-table-column prop="starttime" label="请假时间" width="200">
        <template slot-scope="scope">
          {{ scope.row.starttime | formatDate }}
        </template>
      </el-table-column>
      <el-table-column prop="finaltime" label="返回时间" width="200">
        <template slot-scope="scope">
          {{ scope.row.finaltime | formatDate }}
        </template>
      </el-table-column>
      <el-table-column prop="destination" label="目的地" width="200"></el-table-column>
      <el-table-column prop="reason" label="理由" width="300"></el-table-column>
      <el-table-column prop="status" label="状态" width="120">
        <template slot-scope="scope">
          {{ scope.row.status | getStatus }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" @click="handlePass(scope.row)">通过 <i class="el-icon-check"></i></el-button>
          <el-button type="danger" @click="handleReject(scope.row)">拒绝 <i class="el-icon-close"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "Leave",
  data() {
    return {
      radio: "",
      tableData: [],
      Status:0,
      total: 0,
      pageNum: 1,
      pageSize: 30,
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.request.get("leave/page", {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize,
            },
          })
          .then((res) => {
            this.tableData = res.object.records;
            this.total = res.object.total;
          });
    },
    handlePass(row){
      row.status = 1
      this.request.post("/leave",row).then(res=>{
          this.$message.success("通过完成")
          this.load()
      })
    },
    handleReject(row){
      row.status = 2
      this.request.post("/leave",row).then(res=>{
        this.$message.success("拒绝成功")
        this.load()
      })
    },
    pageChange(){
      var temp = null;
      if(this.radio=="待审核"){temp = 0}
      else if(this.radio == '审核通过'){temp = 1}
      else{temp = 2}
      this.request.get("leave/pageByStatus/"+temp, {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        },
      })
          .then((res) => {
            this.tableData = res.object.records;
            this.total = res.object.total;
          });
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
    getStatus(status){
      if(status == 0){return "待审批"}
      else if(status == 1){return "通过"}
      else {return "拒绝"}
    }
  },
};
</script>

<style scoped></style>
