<template>
<div>

  <div>
    <el-input style="width: 200px;"  placeholder="请输入搜索名称" suffix-icon="el-icon-search" v-model="name"></el-input>
    <el-button class="ml-10" type="primary" @click="load" style="width: 67px">搜索</el-button>
    <el-button type="warning" @click="reset" style="width: 67px">重置</el-button>
  </div>

  <div style="margin-top: 10px">
    <el-button type="primary" @click="handleAdd()">新增 <i class="el-icon-circle-plus" ></i></el-button>

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
  </div>

  <el-table :data="tableData"
            @selection-change="handleSelectionChange"
            row-key="id"
            :header-cell-style="{background:'#880000',color:'#ffffff'}"
            border
            stripe
            default-expand-all
            style="margin-top: 10px">
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="id" label="ID" width="80"></el-table-column>
    <el-table-column prop="name" label="名称"></el-table-column>
    <el-table-column prop="path" label="路径"></el-table-column>
    <el-table-column prop="pagePath" label="页面路径"></el-table-column>
    <el-table-column label="图标"  align="center" width="50px">
      <template slot-scope="scope">
        <i :class="scope.row.icon"/>
      </template>
    </el-table-column>
    <el-table-column prop="description" label="描述"></el-table-column>


    <el-table-column label="操作" width="300">
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
        <el-button
            type="info"
            @click="handleAdd(scope.row.id)"
            v-if="!scope.row.pid && !scope.row.path"
            class="ml-10">新增子菜单 <i class="el-icon-plus"></i></el-button>

      </template>
    </el-table-column>
  </el-table>


  <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="70px">
      <el-form-item label="名称">
        <el-input v-model="form.name" autocomplete="off" ></el-input>
      </el-form-item>
      <el-form-item label="路径">
        <el-input v-model="form.path" autocomplete="off" ></el-input>
      </el-form-item>
      <el-form-item label="页面路径">
        <el-input v-model="form.pagePath" autocomplete="off" ></el-input>
      </el-form-item>
      <el-form-item label="图标">
          <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
              <i :class="item.value"/> {{item.name}}
            </el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.description" autocomplete="off"></el-input>
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
  name: "menu",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:30,
      name:"",
      dialogFormVisible:false,
      form:{},
      multipleSelection:[],
      options:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/menu",{
        params:{
          name:this.name
        }
      }).then(res =>{
        this.tableData = res.object
      })
    },
    reset(){
      this.name=""
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
    handleAdd(id){
      this.dialogFormVisible = true
      this.form = {}
      if(id){
        this.form.pid = id
      }
    },
    saveUser(){
      this.request.post("/menu",this.form).then(res=>{
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
      //this.form = Object.assign({},row)
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
      //请求图标数据
      this.request.get("/menu/icon").then(res =>{
        this.options = res.object
      })
    },
    deleteUser(id){
      this.request.delete("/menu/"+id).then(res=>{
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
      this.request.post("/menu/del/batch/",ids).then(res=>{
        if(res){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    exp(){
      window.open("http://localhost:9090/menu/export")
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