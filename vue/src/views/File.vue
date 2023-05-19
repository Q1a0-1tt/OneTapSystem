<template>
<div>
  <div>
    <el-input style="width: 200px;" class="ml-10" placeholder="请输入搜索名称" suffix-icon="el-icon-search" v-model="name"></el-input>
    <el-button class="ml-10" type="primary" @click="load" style="width: 67px">搜索</el-button>
    <el-button type="warning" @click="reset" style="width: 67px">重置</el-button>
  </div>

  <div style="margin-top: 10px">
    <el-upload action="http://localhost:9090/file/upload"
               :show-file-list="false"
               :on-success="handleFileUploadSuccess"
               style="display: inline-block">
      <el-button type="primary" class="ml-10" >上传文件<i class="el-icon-top"></i></el-button>
    </el-upload>

    <el-popconfirm
        class="mt-5"
        confirm-button-text="确定"
        cancel-button-text="取消"
        icon="el-icon-info"
        icon-color="red"
        title="确定批量删除吗？"
        @confirm="batchDel"
    >
      <el-button type="danger" slot="reference" class="ml-5">批量删除 <i class="el-icon-remove-outline"></i></el-button>
    </el-popconfirm>
  </div>

  <el-table :data="tableData" border="true" :header-cell-style="{background:'#880000',color:'#ffffff'}" @selection-change="handleSelectionChange" style="margin-top: 10px">
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="id" label="ID" width="50"></el-table-column>
    <el-table-column prop="name" label="文件名称" width="600"></el-table-column>
    <el-table-column prop="type" label="文件类型" width="200"></el-table-column>
    <el-table-column prop="size" label="文件大小" width="100"></el-table-column>

    <el-table-column label="启用" width="100">
      <template slot-scope="scope">
        <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
      </template>
    </el-table-column>

    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
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
</div>
</template>

<script>
export default {
  name: "File",
  data(){
    return{
      tableData:[],
      name:"",
      total:0,
      pageNum:1,
      pageSize:30,
      multipleSelection:[]
    }
  },
  created() {
   this.load()
  },
  methods:{
    load(){
      this.request.get("/file/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name: this.name,
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
    deleteUser(id){
      this.request.delete("/file/"+id).then(res=>{
        if(res.code === "200"){
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
      this.request.post("/file/del/batch/",ids).then(res=>{
        if(res.code === "200"){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    handleFileUploadSuccess(res){
      this.load()
    },
    download(url){
      window.open(url)
    },
    changeEnable(row){
      this.request.post("/file/update",row).then(res=>{
        if(res.code === "200"){
          this.$message.success("操作成功")
        }else{
          this.$message.error("操作失败")
        }
      })
    }
  }
}
</script>

<style scoped>

</style>