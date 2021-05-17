<template>
  <div>
    <div style="padding: 10px 10px 0 10px">
        <el-row style="display: inline-flex; width: 100%;">
          <div class="font" style="margin:0px 20px 10px 20px">
            部门
            <el-select
            v-model="department.accountAlias"
            multiple
            filterable
            popper-class="select-rule"
            no-match-text="暂无信息"
            no-data-text="暂无信息"
            :default-first-option=true
            :reserve-keyword=true
            @change="selectProvider"
            @remove-tag="deleteTag"
            placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.accountId"
              :label="item.accountAlias"
              :value="item.accountId">
            </el-option>
          </el-select>
          </div>
          <el-select v-model="type" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <div style="width: 40%;display: inline-flex;margin-left: 80px;">
            <span class="font" style="width: 100px;text-align: center;line-height: 40px;">按列查询</span>
            <el-input placeholder="请输入内容" v-model="input3" class="input-with-select">
              <el-select v-model="select" slot="prepend" placeholder="请选择" class="select">
                <el-option label="餐厅名" value="1"></el-option>
                <el-option label="订单号" value="2"></el-option>
                <el-option label="用户电话" value="3"></el-option>
              </el-select>
              <el-button slot="append" icon="el-icon-search" @click="selectForTable"></el-button>
            </el-input>
          </div>
        </el-row>
    </div>
    <el-divider></el-divider>
    <!--<el-row style="padding: 0px 10px 10px 10px;margin-left: 10px">
      <el-button size="mini" round>全部分机</el-button>
      <el-button size="mini" round>所管分机</el-button>
    </el-row>-->
    <el-switch
      v-model="showTable"
      active-text="全部分机"
      inactive-text="所管分机"
      style="padding: 0px 10px 10px 10px;margin-left: 10px">
    </el-switch>
    <el-table
      :data="accountAll"
      height="350"
      border
      ref="multipleTable"
      tooltip-effect="dark"
      @selection-change="handleSelectionChange"
      style="width: 100%;margin-right: 20px;">
      <!--:row-style="rowstyle"-->
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column v-if="show"  prop="accountId" label="帐号id" >
      </el-table-column>
      <el-table-column prop="accountName" label="帐号名称" >
      </el-table-column>
      <el-table-column prop="accountAlias" label="帐号别名" >
      </el-table-column>
      <el-table-column prop="accountDesp" label="所属部门" :formatter="forDepartment" >
      </el-table-column>
      <el-table-column prop="accountDesp" label="所属管理员" :formatter="forAdministrator" >
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
  export default {
    data(){
      return {
        labelPosition: 'right',
        department:[],
        show:false,
        input3: '',
        select: '',
        accountAll:[],
        showTable:'',
        options: [{
          ap: '4',
          label: '话务员'
        },
        {
            ap: '8',
          label: '分机'
          }],
        type: ''
      }
    },
    methods: {
      selectProvider(){

      },
      selectForTable(val){
        console.log(this.select,'------------------------------selectForTable------------------------',this.input3)
      },
      deleteTag(){

      },
      handleSelectionChange(){

      },
      forDepartment(){},
      forAdministrator(){},
    }
  }

</script>
<style>
 .el-divider--horizontal{
    margin: 10px 0;
  }
  .select .el-input {
    width: 130px;
  }
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
  .font {
    font-size: medium;
  }
  .el-radio-button__inner{
    line-height: 20px;
    font-size: medium;
  }
</style>
