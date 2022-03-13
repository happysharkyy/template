<template>
    <div>
        <el-container>
        <el-header style="height:200px;">
            <div style="display: flex;justify-content:center">
                <div  style="background-color:#7EC0EE" class="transition-box">
                    <div>
                        <h4>新增用户</h4>
                        <h3>{{addUser}}</h3>
                    </div>
                </div>
                <div  style="background-color:#FF9F7F" class="transition-box">
                    <div>
                        <h4>今日发帖</h4>
                        <h3>{{addPost}}</h3>
                    </div>
                </div>
                <div  style="background-color:#C23531" class="transition-box">
                    <div>
                        <h4>今日总评论数</h4>
                        <h3>{{addComment}}</h3>
                    </div>
                </div>
                <div  style="background-color:#FFD700" class="transition-box">
                    <div>
                        <h4>今日总转发数</h4>
                        <h3>{{addForward}}</h3>
                    </div>
                </div>
                <div  style="background-color:#BDA29A" class="transition-box">
                    <div>
                        <h4>今日总点赞数</h4>
                        <h3>{{addStar}}</h3>
                    </div>
                </div>
            </div>
        </el-header>
                <el-container>
                    <el-aside width="100%" style="margin-top:20px">
                        <div id="main" style="width: 100%;height:400px;"></div>
                    </el-aside>
                </el-container>
                <el-container>
                    <el-main width="100%">
                        <div id="dayVisited" style="width: 100%;height:600px;"></div>
                    </el-main>
                </el-container>
                <el-container>
                    <el-aside width="50%" style="margin-top:20px">
                         <div id="rank" style="width: 100%;height:400px;"></div>
                    </el-aside>
                   
                    <el-main width="50%">
                         <h3>标签被用次数排行</h3>
                         <el-table
                            :data="tableData"
                            border
                            stripe
                            style="width: 100%">
                            <el-table-column
                                prop="id"
                                label="排名"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                prop="name"
                                label="标签"
                                width="180">
                            </el-table-column>
                            <el-table-column
                                prop="topicCount"
                                label="被用次数">
                            </el-table-column>
                            </el-table>
                    </el-main> 
                </el-container>

        </el-container>
    </div>
</template>
<script>
const echarts =  require('echarts')
export default {
   data() {
    return {
        addUser:0,
        addPost:0,
        addForward:0,
        addStar:0,
        addComment:0,
        monthList:[],
        monthListValue:[],
        Rank:[],
        RankValue:[],
        ViewRank:[],
        ViewRankValue:[],
        tableData: []
    }
  },
    mounted() {
     this.init()
     //this.getChatNumTop() //发送消息最多用户排行榜 调用方法
    },
    methods:{
      init(){
          this.$api.view.getTodayAddUser().then(res=>{
              this.addUser = res.data
          })
          this.$api.view.getTodayAddForward().then(res=>{
              this.addForward = res.data
          })

          this.$api.view.getTodayAddStar().then(res=>{
              this.addStar = res.data
          })
          this.$api.view.getTodayAddComment().then(res=>{
              this.addComment = res.data
          })
          this.$api.view.getTodayAddPost().then(res=>{
              this.addPost = res.data
          })
          this.$api.view.getMonthAddPost().then(res=>{
              console.log(res.data)
              this.monthList = res.data.keyList
              this.monthListValue = res.data.resultList
              this.getMerchantSelling() //本月发帖数据 调用方法
          })
          this.$api.view.getRank().then(res=>{
              console.log(res.data)
              this.Rank = res.data.key
              this.RankValue = res.data.result
              this.getRank() //个人发帖数据排行榜 调用方法
          })
          this.$api.view.getViewRank().then(res=>{
              console.log(res.data)
              this.ViewRankValue = res.data
              for(let i =0 ;i<this.ViewRankValue.length;i++){
                  this.ViewRank[i] = this.ViewRankValue[i].name
              }
                this.getClickNumTop() //帖子点击量排行榜 调用方法
          })
          this.$api.view.getTagRank().then(res=>{
              this.tableData = res.data
              this.getTagNumTop() //打标签数量排行榜 调用方法
          })
   

      },
      getClickNumTop(){
      var myChart = echarts.init(document.getElementById("dayVisited"));
      let option = {
        title: {
          text: "帖子浏览量Top6",
          x: "left",
          top:40,
          left: 10,
          textStyle: {
            fontSize: 16,
            color: "rgba(51,51,51,1)"
          }
        },
        legend: {
            top: 'bottom'
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
    
        color: ["#5B9CEC", "#CF5BEC", "#FFA200", "#FF5C95", "#1BD6DA"], //自己设置扇形图颜色
        series: [
          {
            name: '面积模式',
            type: 'pie',
            radius: [50, 250],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
                borderRadius: 8
            },
            data: this.ViewRankValue,
            
          }
        ]
      };
      myChart.setOption(option);
      },
      getRank(){
          // 基于准备好的dom，获取main节点init初始化echarts实例
        var myChart = echarts.init(document.getElementById('rank'));
        // 指定图表的配置项和数据
        var option = {
          title: {
            text: '本月发帖量排行', //头部名称
            x: 'center' //居中
          },
          tooltip: {},
          legend: { //图列的显示
            data: ['营收'], //图列，就是显示在右侧的那个蓝色小方块
            top: '20',// legend 放置位置。 
            right: "100", //右边100个像素位置
            orient: 'vertical' // 纵向布局。
          },
          xAxis: { //x轴显示数据
            data: this.Rank //x轴显示数据名称
          },
          yAxis: {}, //y轴数据
          series: [{
            name: '发帖数量',
            type: 'bar', //改这个就好了，数据随意加
            data: this.RankValue,//柱条数据
            label: { //标签，柱形头上的数字
              show: true,
              position: "outside",
              color: "rgba(0, 0, 0, 1.0)"
            }
          }],

          itemStyle: {
            // 设置柱形的颜色
            color: '#5099e6',
          }
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
      },
      getMerchantSelling() {
        let _this = this
        // 基于准备好的dom，获取main节点init初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        // 指定图表的配置项和数据
        var option = {
          title: {
            text: '本月发帖数据', //头部名称
            x: 'center' //居中
          },
          tooltip: {},
          legend: { //图列的显示
            data: ['营收'], //图列，就是显示在右侧的那个蓝色小方块
            top: '20',// legend 放置位置。 
            right: "100", //右边100个像素位置
            orient: 'vertical' // 纵向布局。
          },
          xAxis: { //x轴显示数据
            data: _this.monthList //x轴显示数据名称
          },
          yAxis: {}, //y轴数据
          series: [{
            name: '发帖数量',
            type: 'line', //改这个就好了，数据随意加
            data: _this.monthListValue,//柱条数据
             //光滑
            smooth: true,
            symbol: "circle", // 拐点类型
            symbolSize: 5, // 拐点圆的大小
            animation: true, //false: hover圆点不缩放 .true:hover圆点默认缩放
            itemStyle: {
              normal: {
                color: "#5BB8F9", // 折线条的颜色
                borderColor: "#a9dbff", // 拐点边框颜色
                borderWidth: 5, //拐点边框大小
                areaStyle: {
                  type: "default",
                  //折线下面的渐变色
                  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    { offset: 0, color: "#5BB8F9" },
                    { offset: 1, color: "#FFFFFF" }
                  ])
                }
              }
            },

          }],
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
      },
 }

}
</script>
<style>
  .transition-box {
    margin-bottom: 10px;
    width: 200px;
    height: 100px;
    border-radius: 4px;
    text-align: center;
    color: #fff;
    padding: 0px 20px;
    box-sizing: border-box;
    margin-right: 20px;
  }
    .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>