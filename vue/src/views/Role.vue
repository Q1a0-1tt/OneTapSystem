<template>
<div>

  <div>
    <el-input style="width: 200px;"  placeholder="请输入搜索名称" suffix-icon="el-icon-search" v-model="name"></el-input>
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
  </div>

  <el-table :data="tableData" border stripe :header-cell-style="{background:'#880000',color:'#ffffff'}" @selection-change="handleSelectionChange" style="margin-top: 10px">
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="id" label="ID" width="80"></el-table-column>
    <el-table-column prop="name" label="角色"></el-table-column>
    <el-table-column prop="description" label="描述"></el-table-column>
    <el-table-column prop="flag" label="标识"></el-table-column>

    <el-table-column label="操作" width="300">
      <template slot-scope="scope">
        <el-button type="info" slot="reference" @click="selectMenu(scope.row.id)">分配菜单 <i class="el-icon-menu"></i></el-button>
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

  <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="70px">
      <el-form-item label="名称">
        <el-input v-model="form.name" autocomplete="off" ></el-input>
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="form.description" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="标识">
        <el-input v-model="form.flag" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveUser">确 定</el-button>
    </div>
  </el-dialog>

  <el-dialog title="菜单分配" :visible.sync="menuDialogVisible" width="30%" :check-strictlt="true">
    <el-tree
        :props="props"
        :data="menuData"
        show-checkbox
        node-key="id"
        ref="tree"
        :default-expanded-keys="expends"
        :default-checked-keys="checks">
      <span class="custom-tree-node" slot-scope="{node,data}">
        <sapn><i :class="data.icon"/> {{data.name}}</sapn>
      </span>
    </el-tree>
    <div slot="footer" class="dialog-footer">
      <el-button @click="menuDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
    </div>
  </el-dialog>

</div>
</template>

<script>
export default {
  name: "role",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:30,
      name:"",
      dialogFormVisible:false,
      menuDialogVisible:false,
      form:{},
      multipleSelection:[],
      menuData:[],
      props:{
        label:'name'
      },
      expends:[],
      checks:[],
      roleId:0,
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/role/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name
        }
      }).then(res =>{
        console.log(res)
        this.tableData = res.object.records
        this.total = res.object.total
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
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    saveUser(){
      this.request.post("/role",this.form).then(res=>{
        if(res.code == 200){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    saveRoleMenu(){
      this.request.post("/role/roleMenu/"+this.roleId,this.$refs.tree.getCheckedKeys()).then(res=>{
        if(res.code === "200") {
          this.$message.success("绑定成功")
          this.menuDialogVisible = false;
        }else{
          this.$message.error("绑定失败")
          this.menuDialogVisible = false;
        }
      })
    },
    selectMenu(roleId){
      this.menuDialogVisible=true;
      this.roleId = roleId;
      this.request.get("/menu",).then(res =>{
        this.menuData = res.object
        this.expends=this.menuData.map(v=>v.id)
      })

      this.request.get("/role/roleMenu/"+roleId).then(res =>{
        console.log(res.object)
        this.checks = res.object
      })

    },
    handleEdit(row){
      //数据回显
      this.form = Object.assign({},row)
      this.dialogFormVisible = true
    },
    deleteUser(id){
      this.request.delete("/role/"+id).then(res=>{
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
      this.request.post("/role/del/batch/",ids).then(res=>{
        if(res){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },

  }
}
</script>

<style scoped>

</style>