<template>
  <div>
    <el-button  @click="handleAdd" style="width: 100px;height:35px;background-color: #880000;color: #ffffff">新增 <i class="el-icon-circle-plus" ></i></el-button>

    <div>
      <el-table :data="tableData" border :header-cell-style="{background:'#880000',color:'#ffffff'}" stripe style="width: 100%;margin-top: 10px">
        <el-table-column prop="date" label="日期" width="100">
          <template slot-scope="scope">
            {{ scope.row.date| formatDate }}
          </template>
        </el-table-column>
        <el-table-column prop="name" label="活动名称" width="380"></el-table-column>
        <el-table-column prop="place" label="活动地点" width="270"></el-table-column>
        <el-table-column prop="joinpeople" label="参与人数" width="80" ></el-table-column>
        <el-table-column prop="people" label="需求人数" width="80"></el-table-column>
        <el-table-column prop="introduce" label="活动介绍" width="300"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-s-flag"></i></el-button>
            <el-button type="success" @click="exp(scope.row.id)">导出 <i class="el-icon-files"></i></el-button>
            <el-popover
                placement="top"
                width="280"
                icon="el-icon-info"
                icon-color="red"
                class="ml-10">
              <p>该操作不可撤销，确定删除？</p>
              <div style="text-align: right; margin: 0">
                <el-button type="primary" size="mini" @click="deleteAction(scope.row.id)">确定</el-button>
              </div>
              <el-button type="danger" slot="reference">删除<i class="el-icon-document-delete"></i></el-button>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
      <div style="padding: 10px 0">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>
    </div>

    <div>
      <el-dialog title="活动信息" :visible.sync="dialogFormVisible" width="50%">
        <el-form label-width="70px">
          <div class="form-group">
            <label for="name">活动名称</label>
            <input type="text" id="name" v-model="action.name" required class="form-control">
          </div>
          <div class="form-group">
            <label for="leave-date">活动日期：</label>
            <input type="datetime-local" id="leave-date" v-model="action.date" required class="form-control">
          </div>
          <div class="form-group">
            <label for="leave-date">活动人数：</label>
            <input type="text" id="people" v-model="action.people" required class="form-control">
          </div>
          <div class="form-group">
            <label for="leave-date">活动地点：</label>
            <input type="text" id="place" v-model="action.place" required class="form-control">
          </div>
          <div class="form-group">
            <label for="destination">活动介绍：</label>
            <textarea type="text" id="introduce" v-model="action.introduce" required class="form-control"/>
          </div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>


</template>

<script>
export default {
  name: "LeaveApplication",
  data() {
    return {
      action: {
        name: '',
        date:'',
        introduce: '',
        place: '',
        people:'',
      },
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:5,
      multipleSelection:[],
      dialogFormVisible:false,
      showNameError: false,
      showStartDateError: false,
      showEndDateError: false,
      showDestinationError: false,
      showReasonError: false,
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
    submitForm() {
      console.log(this.action);
      this.request.post("/action",this.action).then(res=>{
        if(res.code=="200"){
          this.$message.success("提交成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("提交失败")
          this.dialogFormVisible = false
          this.load()
        }
      })
    },
    handleAdd(){
      this.dialogFormVisible = true;
    },
    handleEdit(row){
      this.action = Object.assign({},row)
      this.dialogFormVisible = true;
    },
    deleteAction(id){
      this.request.delete("/action/"+id).then(res=>{
        if(res.code == 200){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    exp(id){
      window.open("http://localhost:9090/action/export/"+id)
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum;
      this.load();
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
  },
}
</script>

<style scoped>

.form-group {
  margin-bottom: 20px;
}

label {
  font-size: 18px;
  font-weight: bold;
  display: block;
  margin-bottom: 10px;
}

input[type="text"],
textarea {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}

input[type="datetime-local"] {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}

textarea {
  height: 150px;
}

.btn {
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 16px;
}

.ml-auto {
  margin-right: auto;
}

.error {
  color: red;
}
</style>
