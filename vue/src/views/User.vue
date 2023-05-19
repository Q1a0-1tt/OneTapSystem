<template>
<div>

  <div>
    <el-input style="width: 200px;"  placeholder="请输入搜索名称" suffix-icon="el-icon-search" v-model="username"></el-input>
    <el-input style="width: 200px"  placeholder="请输入搜索邮箱" suffix-icon="el-icon-message" class="ml-5" v-model="email"></el-input>
    <el-input style="width: 200px"  placeholder="请输入搜索地址" suffix-icon="el-icon-position" class="ml-5" v-model="address"></el-input>
    <el-button class="ml-10" type="primary" @click="load" style="width: 67px">搜索</el-button>
    <el-button type="warning" @click="reset" style="width: 67px">重置</el-button>
  </div>

  <div style="margin-top: 10px">
    <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus" ></i></el-button>

    <el-popover
        placement="top"
        width="280"
        icon="el-icon-info"
        class="ml-10">
      <p>该操作不可撤销，确定删除？</p>
      <div style="text-align: right; margin: 0">
        <el-button type="primary" size="mini" @click="batchDel">确定</el-button>
      </div>
      <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove"></i></el-button>
    </el-popover>
    <el-upload action="http://localhost:9090/user/import"
               style="display: inline-block"
               accept=".xlsx"
               :show-file-list="false"
               :on-success="handleImportSuccess">
      <el-button type="primary" class="ml-10" >导入 <i class="el-icon-bottom"></i></el-button>
    </el-upload>
    <el-button type="primary"class="ml-10" @click="exp">导出 <i class="el-icon-top"></i></el-button>
  </div>

  <el-table :data="tableData" border stripe :header-cell-style="{background:'#880000',color:'#ffffff'}" @selection-change="handleSelectionChange" style="margin-top: 10px">
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="id" label="ID" width="50"></el-table-column>
    <el-table-column prop="username" label="用户名" width="140"></el-table-column>
    <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
    <el-table-column prop="email" label="邮箱"></el-table-column>
    <el-table-column prop="phone" label="电话"></el-table-column>
    <el-table-column prop="address" label="地址"></el-table-column>
    <el-table-column prop="role" label="角色" width="140"></el-table-column>

    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
        <el-popover
            placement="top"
            width="280"
            icon="el-icon-info"
            icon-color="red"
            class="ml-10">
          <p>该操作不可撤销，确定删除？</p>
          <div style="text-align: right; margin: 0">
            <el-button type="primary" size="mini" @click="deleteUser(scope.row.id)">确定</el-button>
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
        :page-sizes="[30, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>

  <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="70px">
      <el-form-item label="用户名：">
        <el-input v-model="form.username" autocomplete="off" ></el-input>
      </el-form-item>
      <el-form-item label="角色：">
        <el-select clearable v-model="form.role" placeholder="请选择角色" style="width: 100%">
          <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.flag">
            <i :class="item.value"/> {{item.name}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="昵称：">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱：">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话：">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址：">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveUser">确 定</el-button>
    </div>
  </el-dialog>

</div>
</template>

<script>
export default {
  name: "User",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:30,
      username:"",
      email:"",
      address:"",
      dialogFormVisible:false,
      form:{},
      multipleSelection:[],
      roles:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("user/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username: this.username,
          email: this.email,
          address:this.address,
        }
      }).then(res =>{
        this.tableData = res.object.records
        this.total = res.object.total
      })

      this.request.get("/role").then(res=>{
        this.roles = res.object
      })
    },
    reset(){
      this.username=""
      this.email=""
      this.address=""
      this.load()
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum;
      this.load();
    },
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    saveUser(){
      this.request.post("/user",this.form).then(res=>{
        if(res.code == 200){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    handleEdit(row){
      //数据回显
      this.form = Object.assign({},row)
      this.dialogFormVisible = true
    },
    deleteUser(id){
      this.request.delete("/user/"+id).then(res=>{
        if(res.code == 200){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
    batchDel(){
      let ids = this.multipleSelection.map(o =>o.id)
      this.request.post("/user/del/batch/",ids).then(res=>{
        if(res){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    exp(){
      window.open("http://localhost:9090/user/export")
    },
    handleImportSuccess(){
      this.$message.success("文件导入成功")
      this.load()
    }
  }
}
</script>

<style scoped>

</style>