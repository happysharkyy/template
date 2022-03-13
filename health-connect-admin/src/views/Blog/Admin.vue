<template>
  <div class="page-container">
	<!--工具栏-->
	<div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
		<el-form :inline="true" :model="filters" :size="size">
			<el-form-item>
				<el-input v-model="filters.title" placeholder="用户名"></el-input>
			</el-form-item>
			<el-form-item>
				<kt-button icon="fa fa-search" :label="$t('action.search')" perms="topic:view" type="primary" @click="findPage(null)"/>
			</el-form-item>
		</el-form>
	</div>
	<div class="toolbar" style="float:right;padding-top:10px;padding-right:15px;">
		<el-form :inline="true" :size="size">
			<el-form-item>
				<el-button-group>
				<el-tooltip content="刷新" placement="top">
					<el-button icon="fa fa-refresh" @click="findPage(null)"></el-button>
				</el-tooltip>
				<el-tooltip content="导出" placement="top">
					<el-button icon="fa fa-file-excel-o" @click="Export()"></el-button>
				</el-tooltip>
				</el-button-group>
			</el-form-item>
		</el-form>
		<!--表格显示列界面-->
		<table-column-filter-dialog ref="tableColumnFilterDialog" :columns="columns" 
			@handleFilterColumns="handleFilterColumns"> 
		</table-column-filter-dialog>
	</div>
	<!--表格内容栏-->
	<kt-table-02 :height="350" permsEdit="topic:edit" permsDelete="topic:delete"  permsTop="topic:top"
		:data="pageResult" :columns="filterColumns"
		@findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete" @handleTop="handleTop">
	</kt-table-02>
	<!--新增编辑界面-->
	<el-dialog :title="operation='编辑'" width="80%" :visible.sync="dialogVisible" :close-on-click-modal="false">
		<el-form :model="topic" ref="topic" class="demo-topic">
			<el-form-item prop="title">
                <el-input
                  v-model="topic.title"
                  placeholder="输入新的主题名称"
                ></el-input>
              </el-form-item>

              <!--Markdown-->
              <div id="vditor"></div>
				<div style="margin-top:10px;align:left">
				<el-tag
				:key="tag"
				v-for="tag in tags"
				closable
				:disable-transitions="false"
				@close="handleClose(tag)">
				{{tag}}
				</el-tag>

				<el-input
					class="input-new-tag"
					v-if="inputVisible"
					v-model="inputValue"
					ref="saveTagInput"
					size="small"
					@keyup.enter.native="handleInputConfirm"
					@blur="handleInputConfirm"
					>
					</el-input>
					<el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
				</div>
              <el-form-item class="mt-3" style="margin-top:10px">
                <el-button type="primary" @click="handleUpdate()"
                  >更新
                </el-button>
                <el-button @click="resetForm('topic')">重置</el-button>
              </el-form-item>
		</el-form>
		<!-- <div slot="footer" class="dialog-footer">
			<el-button :size="size" @click.native="dialogVisible = false">{{$t('action.cancel')}}</el-button>
			<el-button :size="size" type="primary" @click.native="submitForm" :loading="editLoading">{{$t('action.submit')}}</el-button>
		</div> -->
	</el-dialog>
  </div>
</template>                                                                                                                                                                 

<script>
import PopupTreeInput from "@/components/PopupTreeInput"
import KtTable02 from "@/views/Core/KtTable02"
import KtButton from "@/views/Core/KtButton"
import TableColumnFilterDialog from "@/views/Core/TableColumnFilterDialog"
import { format } from "@/utils/datetime"
import Vditor from 'vditor'
import 'vditor/dist/index.css'

export default {
	components:{
		PopupTreeInput,
		KtTable02,
		KtButton,
		TableColumnFilterDialog
	},
	data() {
		return {
			size: 'small',
			filters: {
				title: ''
			},
			columns: [], inputVisible: false,
        	inputValue: '',
			filterColumns: [],
			pageRequest: { pageNum: 1, pageSize: 10 },
			pageResult: {},

			operation: false, // true:新增, false:编辑
			dialogVisible: false, // 新增编辑界面是否显示
			editLoading: false,
	
			// 新增编辑界面数据
			topic: {
				title: '',
			},
			title: '', // 标题
      		tags: [],
			content: [],
            roles:[]
		}
	},
	// created() {
    // 	this.fetchTopic();
  	// },
	methods: {
		Export(){
			window.open(baseUrl+"/post/Export");
		},
		
		handleClose(tag) {
		var that=this;
        that.tags.splice(that.tags.indexOf(tag), 1);
      },

		showInput() {
			this.inputVisible = true;
			this.$nextTick(_ => {
			this.$refs.saveTagInput.$refs.input.focus();
			});
		},
		handleTop(params){
			this.$api.blog.batchTop(params.row.id).then();
			alert("置顶成功！")
		},
		handleInputConfirm() {
			let inputValue = this.inputValue;
			if (inputValue) {
				this.tags.push(inputValue);
				console.log(this.tags)
			}
			this.inputVisible = false;
			this.inputValue = '';
		}
		,
		 renderMarkdown(md) {
			this.contentEditor = new Vditor("vditor", {
				height: 460,
				placeholder: "输入要更新的内容",
				preview: {
				hljs: { style: "monokai" },
				},
				mode: "sv",
				after: () => {
				this.contentEditor.setValue(md);
				},
			});
			},
		fetchTopic(id) {

			this.$api.blog.getTopic(id).then((value) => {
				this.topic = value.data.topic;
				console.log(this.topic)
				this.tags = value.data.tags.map(tag => tag.name);
				this.renderMarkdown(this.topic.content);
			});
			},
			handleUpdate: function () {
							
			console.log(this.tags)
			this.$api.tag.save(this.tags,this.topic.id).then((res) => {
				console.log(res)
			});
			
			this.topic.content = this.contentEditor.getValue();
			this.$api.blog.save(this.topic).then((res) => {
				this.editLoading = false
				if(res.code == 200) {
					this.$message({ message: '操作成功', type: 'success' })
					this.dialogVisible = false
					this.$refs['topic'].resetFields()
				} else {
					this.$message({message: '操作失败, ' + res.msg, type: 'error'})
				}
				this.findPage(null)
			});
			},

			resetForm(formName) {
			this.$refs[formName].resetFields();
			this.contentEditor.setValue("");
			this.tags = "";
			},
		
		// 获取分页数据
		findPage: function (data) {
			if(data !== null) {
				this.pageRequest = data.pageRequest
			}
			this.pageRequest.columnFilters = {name: {name:'title', value:this.filters.title}}
			console.log(this.pageRequest.columnFilters)
			this.$api.blog.findPage(this.pageRequest).then((res) => {
				this.pageResult = res.data
                this.content = res.data.content
                console.log(res)
                console.log(this.content)

                for(let i =0;i<this.content.length;i++){
                    let params={
                        topicid:this.pageResult.content[i].id,
                        type:'blog'
                    }
                    this.$api.star.fetchStarByTopicId(params).then((res) => {
                        this.$set(this.pageResult.content[i],"star", res.data.length)
                        //this.pageResult.content[i].push(star)
                    })

                    let params1={
                        topicid:this.pageResult.content[i].id,
                        type:'blog'
                    }
                    this.$api.forward.fetchForwardByTopicId(params1).then((res) => {
         
                        this.$set(this.pageResult.content[i],"forward", res.data.length)
                        //this.pageResult.content[i].push(star)
                    })
                    
                }
                
				//this.findUserRoles()
			}).then(data!=null?data.callback:'')
		},

		// 批量删除
		handleDelete: function (data) {
			this.$api.blog.batchDelete(data.params).then(data!=null?data.callback:'')
		},
		// 显示编辑界面
		handleEdit: function (params) {
			this.dialogVisible = true
			this.operation = false
			this.fetchTopic(params.row.id);
		},
		// 编辑
		submitForm: function () {
			this.$refs.topic.validate((valid) => {
				if (valid) {
					this.$confirm('确认提交吗？', '提示', {}).then(() => {
						this.editLoading = true
						let params = Object.assign({}, this.topic)
						console.log(params)
						this.$api.blog.save(params).then((res) => {
							this.editLoading = false
							if(res.code == 200) {
								this.$message({ message: '操作成功', type: 'success' })
								this.dialogVisible = false
								this.$refs['topic'].resetFields()
							} else {
								this.$message({message: '操作失败, ' + res.msg, type: 'error'})
							}
							this.findPage(null)
						})
					})
				}
			})
		},

		// 时间格式化
      	dateFormat: function (row, column, cellValue, index){
          	return format(row[column.property])
      	},
		// 处理表格列过滤显示
      	displayFilterColumnsDialog: function () {
			this.$refs.tableColumnFilterDialog.setDialogVisible(true)
      	},
		// 处理表格列过滤显示
      	handleFilterColumns: function (data) {
			this.filterColumns = data.filterColumns
			this.$refs.tableColumnFilterDialog.setDialogVisible(false)
      	},
		// 处理表格列过滤显示
      	initColumns: function () {
			this.columns = [
				{prop:"id", label:"帖子ID", minWidth:120},
				{prop:"title", label:"标题", minWidth:120},
				{prop:"userId", label:"用户ID", minWidth:120},
				{prop:"createTime", label:"创建时间", minWidth:150},
				{prop:"star", label:"点赞数", minWidth:50},
				{prop:"forward", label:"转发量", minWidth:50},
				// {prop:"createBy", label:"创建人", minWidth:120},
				// {prop:"createTime", label:"创建时间", minWidth:120, formatter:this.dateFormat}
				// {prop:"lastUpdateBy", label:"更新人", minWidth:100},
				// {prop:"lastUpdateTime", label:"更新时间", minWidth:120, formatter:this.dateFormat}
			]
			this.filterColumns = JSON.parse(JSON.stringify(this.columns));
      	}
	},
	mounted() {
		this.initColumns()
	}
}
</script>

<style scoped>
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>