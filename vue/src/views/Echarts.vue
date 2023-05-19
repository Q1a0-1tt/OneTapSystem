<template>
<div>
  <el-row :gutter="9" style="margin-bottom: 30px">
    <el-col :span="8"><el-card shadow="hover">
        <div>用户总数：</div>
        <div style="padding: 10px 0;text-align: center;font-weight: bold;">{{peopleCount}}</div>
      </el-card></el-col>
    <el-col :span="8"><el-card shadow="hover">
        <div>活动数量：</div>
        <div style="padding: 10px 0;text-align: center;font-weight: bold;">{{actionCount}}</div>
      </el-card></el-col>
    <el-col :span="8"><el-card shadow="hover">
        <div>请假待审批：</div>
        <div style="padding: 10px 0;text-align: center;font-weight: bold;">{{leaveCount}}</div>
      </el-card ></el-col>
  </el-row>
<!--  图表-->
  <el-row>
    <el-col :span="12"><div id="main" style="width: 800px;height: 700px"></div></el-col>
    <el-col :span="12"><div id="pie" style="width: 800px;height: 700px"></div></el-col>
  </el-row>
</div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  name: "echarts",
  data(){
    return{
      peopleCount:0,
      actionCount:0,
      leaveCount:0
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("user/count").then(res=>{
        this.peopleCount = res.object
      })
      this.request.get("action/count").then(res=>{
        this.actionCount =res.object
      })
      this.request.get("leave/count").then(res=>{
        this.leaveCount = res.object
      })
    }
  },
  //页面渲染再触发
  mounted() {
    //折线图
    var option = {
      title:{text:'各季度创建人数', sub:'学生', left:'center'},
      tooltip: {trigger: "item"},
      xAxis: {type: 'category', data: ["第一季度","第二季度","第三季度","第四季度"]},
      yAxis: {type: 'value'},
      series: [{data: [], type: 'line'},]
    };
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    this.request.get("/echarts/members").then(res=>{
      option.series[0].data= res.object
      myChart.setOption(option);
    })

    //饼图
    var pieOption = {
      tooltip: {trigger: 'item'},
      legend: {top: '5%', left: 'center'},
      series: [
        {name: '请假统计', type: 'pie', radius: ['40%', '70%'], avoidLabelOverlap: false,
          itemStyle: {borderRadius: 10, borderColor: '#fff', borderWidth: 2},
          label: {show: false, position: 'center'},
          emphasis: {label: {show: true, fontSize: 40, fontWeight: 'bold'}},
          labelLine: {show: false},
          data: []
        }
      ]
    };
    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);
    this.request.get("/echarts/leaveCount").then(res=>{
       pieOption.series[0].data = res.object
        pieChart.setOption(pieOption);
    })
  }
}
</script>

<style scoped>

</style>