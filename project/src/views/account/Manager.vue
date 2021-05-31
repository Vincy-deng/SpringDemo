<template>
  <div>
    <div style="margin: 20px 0 10px 0;border: 1px solid #ccc;width: 100%;border-radius: 20px">
        <el-row style="display: inline-flex; width: 100%;padding-top: 5px;">
          <div class="font" style="margin:0px 20px 10px 20px;">
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
              v-for="item in department"
              :key="item.accountId"
              :label="item.accountAlias"
              :value="item.accountId">
            </el-option>
          </el-select>
          </div>
          <div class="font" style="margin:0px 20px 10px 20px">
            类型
            <el-select
              v-model="value1"
              placeholder="请选择"
              @change="selectChanged">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.label"
              :value="item.id">
            </el-option>
          </el-select>
          </div>
          <div style="width: 40%;display: inline-flex;height: 40px;">
            <span class="font" style="width: 100px;text-align: center;line-height: 40px;">名称</span>
            <el-input placeholder="请输入内容" v-model="input3" class="input-with-select" :disabled="selectTrue">
              <el-select v-model="select" slot="prepend" placeholder="请选择" class="select"
                         @change="selectName">
                <el-option label="帐号名称" value="1"></el-option>
                <el-option label="帐号别名" value="2"></el-option>
              </el-select>
            </el-input>
          </div>
        </el-row>
    </div>
    <div style="width: 100%;margin-top: 5px;text-align:center">
      <el-button  slot="append" size="medium" round icon="el-icon-search" @click="selectForTable"
                  style="width: 300px;align-content: center;background-color: rgb(160, 207, 255);color: #ffffff">查找</el-button>
    </div>
    <el-divider></el-divider>
    <el-row style="padding: 0px 10px 10px 10px;margin-left: 10px">
      <el-switch
        v-model="showTable"
        active-text="全部分机"
        inactive-text="所管分机"
        @change="showWho"
        style="margin-top: 3px;"
        >
      </el-switch>
      <el-button type="success" size="mini" round  style="float: right;margin-right: 45px;" @click="removeList">{{ showText }}</el-button>
    </el-row>
    <el-table
      :row-style="{height:'20px'}"
      :cell-style="{padding:'0px'}"
     v-if="isShow"
    :data="accountAll"
    height="350"
    border
    ref="multipleTable"
    tooltip-effect="dark"
    @selection-change="handleSelectionChange"
    style="width: 100%;margin-right: 20px;font-size: 10px">
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
    <el-table-column prop="accountProperty" label="帐号类型" width="180" >
      <template slot-scope="scope">
        <span v-if="scope.row.accountProperty == 1">管理员</span>
        <span v-if="scope.row.accountProperty == 2">班长</span>
        <span v-if="scope.row.accountProperty == 4">话务员</span>
        <span v-if="scope.row.accountProperty == 8">分机</span>
        <span v-if="scope.row.accountProperty == 16">部门</span>
      </template>
    </el-table-column>
    <el-table-column prop="accountDesp" label="所属部门" :formatter="forDepartment" >
    </el-table-column>
    <el-table-column prop="accountDesp" label="所属管理员" :formatter="forAdministrator" >
    </el-table-column>
  </el-table>

    <el-table
      v-if="isHide"
      :data="accountByMaId"
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
      <el-table-column prop="accountProperty" label="帐号类型" width="180" >
        <template slot-scope="scope">
          <span v-if="scope.row.accountProperty == 1">管理员</span>
          <span v-if="scope.row.accountProperty == 2">班长</span>
          <span v-if="scope.row.accountProperty == 4">话务员</span>
          <span v-if="scope.row.accountProperty == 8">分机</span>
          <span v-if="scope.row.accountProperty == 16">部门</span>
        </template>
      </el-table-column>
      <el-table-column prop="accountDesp" label="所属部门" >
      </el-table-column>
      <el-table-column prop="accountDesp" label="所属管理员" >
      </el-table-column>
    </el-table>
    <div class="block" v-if="pageone">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="dataSize"
      ></el-pagination>
    </div>
    <div class="block" v-if="pagetwo">
      <el-pagination
        @size-change="handleSizeChangeTwo"
        @current-change="handleCurrentChangeTwo"
        :current-page="pageNumTwo"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="pageSizeTwo"
        layout="total, sizes, prev, pager, next, jumper"
        :total="dataSizeTwo"
      ></el-pagination>
    </div>
  </div>
</template>
<script>
  import {multipleChoice,getDepartmentList,findAgentById} from '../../api/accountData';
  let selectProviderList = [];
  let aid='';
  export default {
    data(){
      return {
        show:false,
        isShow:true,
        isHide:false,
        selectTrue:true,
        pageone:true,
        pagetwo:false,
        tableData: [{}],
        pageSize: 20,
        pageNum: 1,
        dataSize: 0,
        pageSizeTwo: 20,
        pageNumTwo: 1,
        dataSizeTwo: 0,
        showText:'移入管理',
        multipleSelection:[],
        labelPosition: 'right',
        department:[{
          accountId: '',
          accountAlias: ''
        }],
        value: '',
        show:false,
        input3: '',
        select: '',
        accountAll:[{}],
        accountByMaId:[{}],
        showTable:'',
        options: [{
          id: '4',
          label: '话务员'
        },
        {
          id: '8',
          label: '分机'
          }],
        value1:'',
        type: '',
        select1: '',
        property:0,
        desp:'',
        alias:'',
        name:'',
      };
    },
    created () {
      this.getData();
    },
    mounted() {
      this.getData();
    },
    watch: {
      pageSize: function(){
        this.getData();
      },
      pageNum: function(){
        this.getData();
      }
    },
    methods: {
      getData(){
        const dataSize = {
          name:this.name,
          alias:this.alias,
          desp:this.desp,
          property:(this.value1===null || this.value1===''|| isNaN(this.value1))?this.property:parseInt(this.value1),
          pageSize: this.pageSize,
          pageNum: this.pageNum,
        };
        multipleChoice(dataSize).then(res=>{
          console.log(res,'---------------findAllAccount-----------------');
          this.accountAll=res.data.account.content;
          this.listDesp=res.data.despList;
          this.dataSize = res.data.account.totalElements;
          this.despManList=res.data.despManList;
          this.accountMangen=res.data.accountMangen;
        });
        aid=this.$route.query.aid;            //用这个获取url上的id
        const data={
          aid:aid
        };
        findAgentById(data).then(res=>{
          console.log(res,'----------------res--------------');
          this.accountByMaId=res.data.account;
          this.listDesp1=res.data.listDesp;
        });
        getDepartmentList().then(result =>{
          console.log('getDepartmentData',result);
          this.department=result.data.departmentList;
        });
      },
      selectName(val){
        console.log(val,'---------------selectName--------------');
        this.selectTrue=false;
      },
      selectChanged(val){
        console.log(val.id,'---------------selectChanged--------------',this.select1);
      },
      selectProvider(value){
        selectProviderList = [];
        value.forEach(function(data, index) {
          selectProviderList.push(data);
        });
        console.log(value,'----------------selectProvider--------------');
      },
      selectForTable(val){
        this.desp='';
        console.log(this.select,'------------------------------selectForTable------------------------',this.input3);
        if (selectProviderList!=null && selectProviderList.length!==0){
          for (let i = 0; i <selectProviderList.length ; i++) {
            this.desp+=selectProviderList[i]+',';
          }
        } ;
        if (this.input3!==null && this.input3!=='' ){
          this.name=(this.select==1)?this.input3:'';
          this.alias=(this.select==2)?this.input3:'';
        }
        this.accountAll=[];
        this.getData();
      },
      deleteTag(){
        selectProviderList.forEach(function(data, index) {
          if (data.accountId === value) {
            selectProviderList.splice(index, value);
          }
        });
      },
      handleSizeChange(val) {
        this.pageSize = val;
      },
      handleCurrentChange(val) {
        this.pageNum = val;
      },
      handleSizeChangeTwo(val) {
        this.pageSizeTwo = val;
      },
      handleCurrentChangeTwo(val) {
        this.pageNumTwo = val;
      },
      handleSelectionChange(val){
        this.multipleSelection = val;
      },
      showWho(val){
        if(val){
          this.pageone=true;
          this.pagetwo=false;
          this.isShow=true;
          this.isHide=false;
          this.showText='移入管理';
        }else {
          this.pageone=false;
          this.pagetwo=true;
          this.isShow=false;
          this.isHide=true;
          this.showText='移出管理';
        }
        console.log(val,'------------showWho------------');
      },
      removeList(val){
        console.log(val,'-----------removeList-------------',this.isShow);
        if (this.isShow){
          let multData = this.multipleSelection;
          let tableData =this.accountAll;
          let multDataLen = multData.length;
          let tableDataLen = tableData.length;
          for(let i = 0; i < multDataLen ;i++){
            for(let y=0;y < tableDataLen;y++){
              if(JSON.stringify(tableData[y]) == JSON.stringify(multData[i])){ //判断是否相等，相等就删除
                this.accountAll.splice(y,1);
                console.log('aa');
              }
            }
            this.accountByMaId.push(this.multipleSelection[i]);
          }
        }else {
          let multData = this.multipleSelection;
          let tableData =this.accountByMaId;
          let multDataLen = multData.length;
          let tableDataLen = tableData.length;
          for(let i = 0; i < multDataLen ;i++){
            for(let y=0;y < tableDataLen;y++){
              if(JSON.stringify(tableData[y]) == JSON.stringify(multData[i])){ //判断是否相等，相等就删除
                this.accountByMaId.splice(y,1);
              }
            }
            this.accountAll.push(this.multipleSelection[i]);
          }
        }
      },
      forDepartment(row){
        return this.listDesp[row.accountId];
      },
      forAdministrator(row){
        if (this.accountMangen[row.accountId]!==null && this.accountMangen[row.accountId]!==''){
          return this.accountMangen[row.accountId].accountAlias;
        }else {
          return '暂无管理员';
        }
      },
      getDepartmentName(val){
        console.log('这里是getDepartmentName',val);
        /*var obj = this.department;
        var hostValue = {};
        obj.forEach((item, index) => {
          const value = item.value;
          if (!hostValue[value]) {
            hostValue[value] = {
              item
            };
          }
        });*/
        this.department1 = hostValue[val].item;
      }
    }
  };

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

