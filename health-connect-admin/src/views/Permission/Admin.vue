<template>
  <div class="role-container">
    <el-card>
      <el-row :gutter="20">
        <el-col :span="10">
          <el-card
            shadow="never"
            style="border: none;"
          >

            <div
              slot="header"
              class="title"
              :style="{'borderLeftWidth':'3px','borderLeftStyle':'solid','borderLeftColor':borderTheme}"
            >
              <span style="padding-left:10px">角色列表</span>
            </div>

            <el-table
              ref="table"
              :data="roleData"
              border
              size="mini"
              highlight-current-row
              @current-change="handleSelectionChange"
            >
              <el-table-column
                prop="name"
                label="角色名称"
              >
              </el-table-column>
              <el-table-column
                prop="isDel"
                label="状态"
                width="80"
              >
                <template slot-scope="scope">
                  {{ scope.row.isDel==0?'启用':'禁用' }}
                </template>
              </el-table-column>
              <el-table-column
                prop="remark"
                label="角色介绍"
              >
              </el-table-column>
              <el-table-column
                label="操作"
                width="150"
              >
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    style="color:#ffa97d"
                    type="text"
                    @click="openDetail(scope.$index,scope.row)"
                  >
                    查看
                  </el-button>
                  <el-button
                    size="mini"
                    type="text"
                    :loading="loading.form"
                    @click="openEdit(scope.$index, scope.row)"
                    v-if="scope.row.isDefault!=1"
                  >
                    修改
                  </el-button>
                  <el-button
                    style="color:#ed3838"
                    size="mini"
                    type="text"
                    :loading="loading.delete"
                    @click="openDel(scope.$index, scope.row)"
                    v-if="scope.row.isDefault!=1"
                  >
                    删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              :total="total"
              v-if="roleData.length>0"
              class="text-right"
              size="mini"
              style="margin: 15px 0 10px;"
              @current-change="handleCurrentChange"
              @size-change="sizeChange"
              :current-page.sync="pageNum"
              :page-size="pageSize"
              :page-sizes="[5,10, 30,50,100,200]"
              layout="sizes,total, prev, pager, next,jumper"
              :pager-count="5"
              :page-count="pageCount"
            >
            </el-pagination>
          </el-card>
        </el-col>
        <el-col :span="14">
          <div
            style="overflow:hidden;"
            ref="rightCard"
          >

            <el-card shadow="never">
                          <div
              slot="header"
              class="title"
              :style="{'borderLeftWidth':'3px','borderLeftStyle':'solid','borderLeftColor':borderTheme}"
            >
              <span style="padding-left:10px">权限列表</span>
            </div>
            <div style="height: 400px;overflow-y: scroll;font-size:14px">
                <el-tree :data="menuData" size="mini" show-checkbox node-key="id" :props="defaultProps"
                    style="width: 100%;pading-top:20px;" ref="menuTree" 
                    v-loading="menuLoading" element-loading-text="拼命加载中"  :check-strictly="sysStrictly"
                    @check-change="handleMenuCheckChange">
                </el-tree>
            </div>
            <div style="float:left;padding-left:24px;padding-top:12px;padding-bottom:4px;">
                <el-checkbox v-model="checkAll" @change="handleCheckAll"><b>全选</b></el-checkbox>
            </div>
            <el-button
            type="primary"
            size="medium"
            @click="saveTree()"
        >保存</el-button>
            </el-card>
          </div>
        </el-col>
      </el-row>
    </el-card>

   
  </div>
</template>

<script>



export default {

  beforeMount() {},
  created() {
    this.getRolePageList();
  },

  updated() {
    this.$nextTick(() => {
      this.compHeight = this.$refs.rightCard.offsetHeight + "px";
    });
  },

  data() {
    return {
      columns: [
        // {title:'角色编号',key:'rolecode'},
        { title: "角色名称", key: "name" },
        { title: "状态", slot: "isDel" }
      ],
      total: "",
       props: {
          label: 'name',
          children: 'zones',
          isLeaf: 'leaf'
        },
      roleData: [],
      sysStrictly:true,
      roleForm: {
        roleName: ""
      },
        defaultProps: {
        children: 'children',
        label: 'remark'
    },
      options: [
        {
          value: "1",
          label: "可选"
        },
        {
          value: "0",
          label: "不可选"
        }
      ],
      dialogTitle: "",
      isCollapse: true,
      pageSize: 5,
      pageNum: 1,
      pageCount: 1,
      addForm: {},
      addDialogStatus: false,
      loading: {
        form: false,
        reset: false
      },
      status: {
        dialogStatus: false,
        showDetail: false,
        editStatus: true
      },
      memberInfo: {
        roleName: "",
        status: "",
        remark: "",
        deptName: ""
      },
      			selectRole: {},
			menuData: [],
			menuSelections: [],
			menuLoading: false,
      nameExit: false,
      selectDepart: false,
      departCompont: "",
      deptId: "",
      checkAll: false,
      compHeight: "",
      warehouseRolelist:[],
      currentRoleMenus: []
    };
  },
  computed: {
    borderTheme() {
      return "#1890ff";
    },
    userInfo() {
      return this.$store.state.userInfo;
    }
  },
  /** 过滤属性 */
  filters: {
    
  },

  methods: {
    handleSelectionChange(val) {
      if (!val) {
        return;
      }
      this.tableId = val.id;
      this.name = val.name;
      localStorage.setItem("roleId", this.tableId);
      localStorage.setItem("name", this.name);
        this.$api.role.findPermissionByRoleId(this.tableId).then(res => {
            this.currentRoleMenus = res.data
            this.$refs.menuTree.setCheckedNodes(res.data)
        })
    },
    statusFormatter(row) {
      if (row.status) return "有效";
      return "无效";
    },
    getRolePageList() {
      this.menuLoading = true
      this.$api.role.getSelectPermission().then(res => {
            this.menuData = res.data
            this.menuLoading = false
      });
      this.tableId = 0;
      const params = {};
      params.columnFilters =   {name: {name:'userName', value:''}};
      params.pageNum = this.pageNum;
      params.pageSize = this.pageSize;
      this.$api.role.findPage(params).then(res => {
        if (res.code == "200") {
          this.roleData = res.data.content; 
          this.pageCount = res.data.pages
            ? res.data.pages
            : res.data.total / this.pageSize;
          this.total = res.data.total;
          if (this.roleData.length > 0)
            this.$refs.table.setCurrentRow(this.roleData[0]);
        }
      });
      this.handleSelectionChange(0);
    },
    handleMenuCheckChange(data, check, subCheck) {
        if(check) {
            // 节点选中时同步选中父节点
            let pid = data.pid
            this.$refs.menuTree.setChecked(pid, true, false)
        } else {
            // 节点取消选中时同步取消选中子节点
            if(data.children != null) {
                data.children.forEach(element => {
                    this.$refs.menuTree.setChecked(element.id, false, false)
                });
            }
        }
	},
    handleCurrentChange(pageNum) {
      // this.currentPage = pageNum;
      this.pageNum = pageNum;
      this.getRolePageList();
    },
    sizeChange(pageSize) {
      this.pageSize = pageSize;
      this.getRolePageList();
    },

    search() {
      this.pageNum = 1;
      this.getRolePageList();
    },
    reset() {
      this.roleForm = {
        roleName: ""
      };
      this.getRolePageList();
    },
    saveTree(){
        let roleId = localStorage.getItem("roleId")
        // if('superadmin' ==localStorage.getItem("name")) {
        //     this.$message({message: '超级管理员拥有所有权限，不允许修改！', type: 'error'})
        //     return
        // }
        this.authLoading = true
        let checkedNodes = this.$refs.menuTree.getCheckedNodes(false, false)
        let roleMenus = []
        for(let i=0, len=checkedNodes.length; i<len; i++) {
            let roleMenu = { roleId:roleId, permissionId:checkedNodes[i].id }
            roleMenus.push(roleMenu)
        }
        this.$api.role.saveRolePermission(roleMenus).then((res) => {
            if(res.code == 200) {
                this.$message({ message: '操作成功', type: 'success' })
            } else {
                this.$message({message: '操作失败, ' + res.msg, type: 'error'})
            }
            this.authLoading = false
        })
    },
    submitForm(type) {
      let query = {};
      if (type == 0) {
        query = this.addForm;
      } else {
        query = this.memberInfo;
      }
      addRole(query).then(res => {
        if (res.code == "001") {
          this.addDialogStatus = false;
          this.status.dialogStatus = false;
          this.$message.success("保存成功");
          this.getRolePageList();
        } else {
          this.$message.error(res.info);
        }
      });
    },
    cancelForm() {
      this.addDialogStatus = false;
    },
    openDetail(index, row) {
      this.memberInfo = Object.assign({}, row);
      this.dialogTitle = "查看详情";
      this.status = {
        dialogStatus: true,
        showDetail: true,
        editStatus: false
      };
    },
    openEdit(index, row) {
      if (row.defaultFlag == 1) {
        this.$message.info("默认角色不允许修改");
        return;
      }
      // this.$refs["infoForm"].resetFields();
      this.memberInfo = row;
      this.memberInfo.isClose = String(this.memberInfo.isClose);
      this.dialogTitle = "编辑详情";
      this.status = {
        dialogStatus: true,
        showDetail: false,
        editStatus: true
      };
    },
    openDel(index, row) {
      if (row.defaultFlag == 1) {
        this.$message.info("默认角色不允许删除");
        return;
      }
      this.$confirm("确定要删除？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        if (this.pageNum > 1 && this.total % this.pageSize == 1 && index == 0) {
          this.pageNum = this.pageNum - 1;
        }
        delRole(row.id).then(res => {
          if (res.code == "001") {
            this.$message.success("已删除");
            this.getRolePageList();
          } else {
            this.$message.error(res.info);
          }
        });
      });
    },
    editInfo() {
      this.submitForm(1);
    },
    departDialog() {
      this.selectDepart = true;
    },
    // 全选操作
    handleCheckAll() {
        if(this.checkAll) {
            let allMenus = []
            this.checkAllMenu(this.menuData, allMenus)
            this.$refs.menuTree.setCheckedNodes(allMenus)
        } else {
            this.$refs.menuTree.setCheckedNodes([])
        }
    },
    // 递归全选
    checkAllMenu(menuData, allMenus) {
        menuData.forEach(menu => {
            allMenus.push(menu)
            if(menu.children) {
                this.checkAllMenu(menu.children, allMenus)
            }
        });
    },
    submitTree(payload) {
      this.roleForm.deptName = payload.name;
      this.roleForm.deptId = payload.deptId;
      this.selectDepart = false;
    },
    cancelTree() {
      this.selectDepart = false;
    },
    handleClose() {
      this.addDialogStatus = false;
    }
  }
};
</script>

<style scoped>
.tree-box {
  height: 100%;
  border-right: 1px solid #ddd;
}

.tree {
  margin: 15px 0;
  overflow: auto;
  height: 400px;
  width: 250px;
  /* border: 1px solid #ccc */
}
.tree-tool {
  padding: 0 5px 10px;
}

.drop-menu {
  float: right;
  line-height: 28px;
}

.formText {
  display: inline-block;
  word-break: break-word;
}
</style>
<style type="text/css" lang="scss">
.role-container {
  padding: 10px;
}
.role-container .el-collapse {
  border: none;
  .el-collapse-item__wrap {
    border-bottom: none;
  }
}
// .el-collapse-item__arrow {
//    margin-top: 15px;
// }

.role-container .el-card__header {
  background: #fefefe;
  border-bottom: none;
}

.iconRight {
  float: right;
  padding-top: 5px;
}
.el-tree-node > .el-tree-node__children {
  overflow: visible;
}
.el-tree > .el-tree-node {
  display: inline-block !important;
  min-width: 100%;
  width: auto;
}
.text-right {
  text-align: right;
}
</style>
