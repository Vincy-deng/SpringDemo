 <template>
    <div>
    <div class="el-alert">
    <el-row>
      <el-button type="primary" size="small" @click="addAccount()">添加</el-button>
    </el-row>
    </div>
  <el-table
    :data="tableData"
    height="500"
    border
    :row-style="{height:'20px'}"
    :cell-style="{padding:'0px'}"
    style="width: 100%">
    <el-table-column v-if="show"  prop="accountId" label="帐号id" width="180">
    </el-table-column>
    <el-table-column prop="accountName" label="帐号名称" width="180">
    </el-table-column>
    <el-table-column prop="accountAlias" label="帐号别名" width="180">
    </el-table-column>
    <el-table-column prop="accountProperty" label="帐号类型" width="180">
      <template slot-scope="scope">
        <span v-if="scope.row.accountProperty == 1">管理员</span>
        <span v-if="scope.row.accountProperty == 2">班长</span>
        <span v-if="scope.row.accountProperty == 4">话务员</span>
        <span v-if="scope.row.accountProperty == 8">分机</span>
        <span v-if="scope.row.accountProperty == 16">部门</span>
      </template>
    </el-table-column>
    <el-table-column prop="accountDesp" label="所属部门" :formatter="changeManProp" width="250">
    </el-table-column>
    <el-table-column prop="accountDspts" label="所管理的部门" width="250">
    </el-table-column>
    <el-table-column label="操作" width="150">
      <template slot-scope="scope">
        <el-button type="primary" size="mini" @click="manageAccount(scope.row)"
                   v-if="scope.row.accountProperty == 1 || scope.row.accountProperty == 2 "
                   icon="el-icon-user" circle></el-button>
        <el-button type="info" size="mini" @click="updateAccount(scope.row)" icon="el-icon-edit" circle></el-button>
        <el-button type="danger" size="mini" icon="el-icon-delete" @click="delAccount(scope.row)" circle></el-button>
      </template>
    </el-table-column>
  </el-table>
  <div class="block">
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
    <el-dialog :title="title" :visible.sync="dialogFormVisible" width="65%" height="60%" @close='closeAddDialog'>
      <el-form ref="addAccountData" :model="addAccountData" :rules="rules" label-width="80px">
        <el-form-item label="帐号类型" class="input1">
        <el-radio-group v-model="radio" @change="agreeChange">
          <el-radio :label="1">管理员</el-radio>
          <el-radio :label="2">班长</el-radio>
          <el-radio :label="4">话务员</el-radio>
          <el-radio :label="8">分机</el-radio>
          <el-radio :label="16">部门</el-radio>
        </el-radio-group>
        </el-form-item>
        <!--这里是根据单选按钮类型显示的div-->
        <div id="monitor" v-show="hide" ><!--这是班长模块的div-->
          <div class="monitor_info" >
            <el-form-item label="帐号名称" class="input1">
              <el-input v-model="addAccountData.name" clearable></el-input>
            </el-form-item>
            <el-form-item label="帐号别名" class="input2">
              <el-input v-model="addAccountData.nameAlias" clearable></el-input>
            </el-form-item>
            <el-form-item label="密码" class="input1" prop="password">
              <el-input v-model="addAccountData.password" show-password ></el-input>
            </el-form-item>
            <el-form-item label="确认密码" class="input2" prop="password2">
              <el-input v-model="addAccountData.password2" show-password ></el-input>
            </el-form-item>
            <el-form-item label="部门" class="input2">
              <template>
                <el-select style="width: 100%;" v-model="value" placeholder="请选择" @change="getDepartmentName">
                  <el-option
                    v-for="item in options"
                    :key="item.accountId"
                    :label="item.accountAlias"
                    :value="item.accountId">
                  </el-option>
                </el-select>
              </template>
            </el-form-item>
          </div>
          <div class="monitor_tree">
            <el-tree
              class="treeclass"
              ref="tree"
              :data="treeData"
              :props="defaultProps"
              @node-click="nodeClick"
              @check-change="handleClick"
              node-key="id"
              show-checkbox
            >
            </el-tree>
            <span></span>
          </div>

        </div>
        <!--这里话务员和分机共用的模块-->
        <div id="operator" v-show="hide2" ><!--这是班长模块的div-->
          <div class="monitor_info" >
            <el-checkbox v-model="checked" style="margin-left: 15px;" @change="checkedState">批量添加</el-checkbox>
            <div id="operator_checkboxShow" v-show="CS1">
              <el-form-item label="帐号前缀" class="input1">
                <el-input v-model="addAccountData.prefix" clearable></el-input>
              </el-form-item>
              <el-form-item label="开始帐号" class="input2">
                <el-input v-model="addAccountData.start" clearable></el-input>
              </el-form-item>
              <el-form-item label="结束帐号" class="input1">
                <el-input v-model="addAccountData.end" clearable></el-input>
              </el-form-item>
            </div>
            <el-form-item label="帐号名称" class="input1" v-show="CS2">
              <el-input v-model="addAccountData.name" clearable></el-input>
            </el-form-item>
            <el-form-item label="帐号别名" class="input2">
              <el-input v-model="addAccountData.nameAlias" clearable></el-input>
            </el-form-item>
            <el-form-item label="部门" class="input2">
              <template>
                <el-select style="width: 100%;" v-model="value" placeholder="请选择" @change="getDepartmentName">
                  <el-option
                    v-for="item in options"
                    :key="item.accountId"
                    :label="item.accountAlias"
                    :value="item.accountId">
                  </el-option>
                </el-select>
              </template>
            </el-form-item>
          </div>
        </div>
        <!--这里是部门模块-->
        <div id="department" v-show="hide3" ><!--这是班长模块的div-->
          <div class="monitor_info" >
            <el-form-item label="帐号名称" class="input1" v-show="CS2">
              <el-input v-model="addAccountData.name" clearable></el-input>
            </el-form-item>
            <el-form-item label="帐号别名" class="input2">
              <el-input v-model="addAccountData.nameAlias" clearable></el-input>
            </el-form-item>
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addCancelBtn">取 消</el-button>
        <el-button type="primary" @click="addOkBtn('addAccountData')">确 定</el-button>
      </div>
    </el-dialog>

      <!--这里是管理界面的弹出对话框-->
      <el-dialog
        title="管理设置"
        :visible.sync="centerDialogVisible"
        width="96%"
        center>
        <el-select
          v-model="options.accountAlias"
          multiple
          filterable
          popper-class="select-rule"
          no-match-text="暂无信息"
          no-data-text="暂无信息"
          :default-first-option=true
          :reserve-keyword=true
          @change="selectProvider"
          @remove-tag="deleteTag"
          style="width:100%"
          placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.accountId"
          :label="item.accountAlias"
          :value="item.accountId">
        </el-option>
        </el-select>
      <div style="display: inline-flex;margin-top: 20px;width: 100%;">
        <el-table
          :data="accountAll"
          height="350"
          border
          ref="multipleTable"
          tooltip-effect="dark"
          @selection-change="handleSelectionChange"
          :row-style="rowstyle"
          style="width: 100%;margin-right: 20px;">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column v-if="show"  prop="accountId" label="帐号id" width="80">
          </el-table-column>
          <el-table-column prop="accountName" label="帐号名称" width="80">
          </el-table-column>
          <el-table-column prop="accountAlias" label="帐号别名" width="80">
          </el-table-column>
          <el-table-column prop="accountDesp" label="所属部门" :formatter="changeManProp" width="120">
          </el-table-column>
          <el-table-column prop="accountDesp" label="所属管理员" :formatter="changeAForA" width="120">
          </el-table-column>
        </el-table>
        <el-button-group style="display: inline-grid;">
          <el-button type="primary" icon="el-icon-d-arrow-right" @click="allMoveRight"
                     style="border-top-right-radius: 4px;border-bottom-left-radius: 0;"></el-button>
          <el-button type="primary" icon="el-icon-arrow-right" @click="moveRight"></el-button>
          <el-button type="primary" icon="el-icon-arrow-left" @click="moveLeft" ></el-button>
          <el-button type="primary" icon="el-icon-d-arrow-left" @click="allMoveLeft"
                     style="border-bottom-left-radius: 4px;border-top-right-radius: 0;"></el-button>
        </el-button-group>
        <el-table
          :data="accountByMaId"
          height="350"
          border
          ref="multipleTable2"
          tooltip-effect="dark"
          @selection-change="handleSelectionChange2"
          style="width: 70%;margin-left: 20px">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
            <el-table-column v-if="show"  prop="accountId" label="帐号id" width="50">
            </el-table-column>
            <el-table-column prop="accountName" label="帐号名称" width="120">
            </el-table-column>
            <el-table-column prop="accountAlias" label="帐号别名" width="120">
            </el-table-column>
            <el-table-column prop="accountDesp" label="所属部门" :formatter="changeDeProp" width="200">
            </el-table-column>
        </el-table>
      </div>
        <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="saveForM">保 存</el-button>
  </span>
      </el-dialog>
  </div>
</template>



<script>
  import { getAccountList,delAccount,updateAccount,addAccount,getRightTree,
    getDepartmentList,findAgentById,findAllAccountByDid,saveManage} from '../../api/accountData';
  let selectProviderList = [];
  let mAid='';
  export default {
    data() {
      var validatePass1 = (rule,value,callback) => {
          if (this.radio===2){
            if (value === '') {
              callback(new Error('请输入密码'));
            }else {
              callback();
            }
          }else {
            callback();
          }
      };
      var validatePass2 = (rule, value, callback) => {
        if (this.radio===2){
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.addAccountData.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
          }
        }else {
          callback();
        }
      };
      return {
        title:'',
        show: false,
        radio: 16,
        dialogFormVisible:false,
        centerDialogVisible:false,
        tableData: [{}],
        pageSize: 20,
        pageNum: 1,
        dataSize: 0,
        hide:false,
        hide2:false,
        hide3:true,
        CS1:false,
        CS2:true,
        treeId:'',
        treeData: [{}],
        listDesp:{},
        listDesp1:{},
        listDesp2:[{}],
        accountMangen:[{}],
        despManList:[{}],
        accountAll:[{}],
        accountByMaId:[{}],
        //初始化复选框是否被选中
        checked:false,
        //添加帐号所需要初始化的数据
        addAccountData:{
          id: '',
          name: '',
          nameAlias: '',
          password: '',
          password2:'',
          prefix:'',
          start:'',
          end:''
        },
        updateAccountData:{},
        //保存选中的部门id
        departmentData:{},
        rules:{
          password: [
            { required: true, validator: validatePass1, trigger: 'blur' }
            //{ pattern: /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{8,30}$/, message: '密码为数字，小写字母，大写字母，特殊符号 至少包含三种，长度为 8 - 30位，密码不能包含 用户名，公司名称(lidian), 公司域名(rekoon) （判断的时候不区分大小写)' }
          ],
          password2: [
            { required: true, validator: validatePass2, trigger: 'blur' }
          ]
        },
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        options: [{
          accountId: '',
          accountAlias: ''
        }],
        value: '',
        multipleSelection: [{}],
        multipleSelection2: [{}]
      };
    },
    created () {
      this.queryFn();
      this.getTreeData();
      this.getDepartmentData();
    },
    mounted() {
      this.queryFn();
      this.getTreeData();
      this.getDepartmentData();
    },
    watch: {
      pageSize: function(){
        this.queryFn();
      },
      pageNum: function(){
        this.queryFn();
      }
    },
    methods: {
      //管理弹框中移动人员信息
      allMoveRight(row){
        this.multipleSelection=this.accountAll;
        this.accountAll=[];
        for (let i = 0; i <this.multipleSelection.length ; i++) {
          this.accountByMaId.push(this.multipleSelection[i]);
        }
      },
      moveRight(){
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
      },

      allMoveLeft(val){
        this.multipleSelection2=this.accountByMaId;
        this.accountByMaId=[];
        for (let i = 0; i <this.multipleSelection2.length ; i++) {
          this.accountAll.push(this.multipleSelection2[i]);
        }
      },
      moveLeft(val){
        let multData = this.multipleSelection2;
        let tableData =this.accountByMaId;
        let multDataLen = multData.length;
        let tableDataLen = tableData.length;
        for(let i = 0; i < multDataLen ;i++){
          for(let y=0;y < tableDataLen;y++){
            if(JSON.stringify(tableData[y]) == JSON.stringify(multData[i])){ //判断是否相等，相等就删除
              this.accountByMaId.splice(y,1);
            }
          }
          this.accountAll.push(this.multipleSelection2[i]);
        }
      },
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleSelectionChange2(val) {
        this.multipleSelection2 = val;
      },
      selectProvider(value){
        selectProviderList = [];
        value.forEach(function(data, index) {
          selectProviderList.push(data);
        });
        const data={
          did:selectProviderList
        };
        findAllAccountByDid(data.did).then(res=>{
          this.accountAll=[];
          for (let i = 0; i <res.data.accountList.length ; i++) {
            for (let j = 0; j <res.data.accountList[i].length ; j++) {
              this.accountAll.push(res.data.accountList[i][j]);
            }
          }
        });
      },
      deleteTag(value) {
        selectProviderList.forEach(function(data, index) {
          if (data.accountId === value) {
            selectProviderList.splice(index, value);
          }
        });
        if (selectProviderList.length===0 || selectProviderList==='' || selectProviderList===null){
          findAllAccount().then(res=>{
            this.accountAll=res.data.account;
            this.listDesp=res.data.despList;
            this.despManList=res.data.despManList;
            this.accountMangen=res.data.accountMangen;
          });
        } ;
      },
      //改变显示的属性
      changeManProp(row){
        return this.listDesp[row.accountId];
        //return this.showDesp;
      },
      changeDeProp(row){
        return (this.listDesp1[row.accountId]==null ||this.listDesp1[row.accountId]=='')?this.listDesp[row.accountId]:this.listDesp1[row.accountId];
        //return this.showDesp;
      },
      changeAForA(row){
        if (this.accountMangen[row.accountId]!==null && this.accountMangen[row.accountId]!==''){
          return this.accountMangen[row.accountId].accountAlias;
        }else {
          return '暂无管理员';
        }
      },
      rowstyle({ row, rowIndex }) {
        let stylejson = {};
        if (this.accountMangen[row.accountId]!==null && this.accountMangen[row.accountId]!==''){
          //stylejson.background = 'rgb(198, 226, 255)';
          // 也可以修改文字颜色
          stylejson.color='orange';
          return stylejson;
        } ;
        /*let stylejson = {};
        if (row.spu_id == "349") {
          stylejson.background = "rgb(198, 226, 255)";// 背景颜色
          // 也可以修改文字颜色
          stylejson.color='green' ;
          return stylejson;
        } else {
          return "";
        }*/
      },
      getDepartmentName(val){
        console.log('这里是getDepartmentName',val);
        var obj = this.options;
        var hostValue = {};
        obj.forEach((item, index) => {
          const value = item.value;
          if (!hostValue[value]) {
            hostValue[value] = {
              item
            };
          }
        });
        this.options1 = hostValue[val].item;
      },
      checkedState(val){
        this.CS1=(val==true)?true:false;
        this.CS2=(val==false)?true:false;
        this.addAccountData = {
          nameAlias:this.addAccountData.nameAlias
        };
      },
      agreeChange(val){
        console.log('这里是判断this.agreeChange',val);
        this.hide=(val=='2')?true:false;
        this.hide2=(val=='4' || val=='8')?true:false;
        this.hide3=(val=='16')?true:false;
        if (this.updateAccountData.accountId!=null && this.updateAccountData.accountId!=''){
          this.addAccountData.id=this.updateAccountData.accountId;
          this.addAccountData.name=this.updateAccountData.accountName;
          this.addAccountData.nameAlias=this.updateAccountData.accountAlias;
        } else {
          this.addAccountData = {
          };
          this.updateAccountData={};
        }
        this.$refs.tree.setCheckedKeys([]);
      },
      getDepartmentData(){
        getDepartmentList().then(result =>{
          console.log('getDepartmentData',result);
          this.options=result.data.departmentList;
        });
      },
      nodeClick(val){
      },
      handleClick(val){
      },
      getTreeData(){
        getRightTree().then(res => {
          if (res.data.status == 200){
            this.treeData = res.data.data[0];
            this.$message({
              type: 'success',
              message: '数据加载成功！'
            });
          }
        })
          .catch(err => {
            this.loading = false;
            this.$message.error('数据获取失败，请稍后再试！');
          });
      },
      queryFn () {
        const data = {
            pageNum: this.pageNum,
            pageSize: this.pageSize
        };
        getAccountList(data).then(Response =>{
          this.tableData = Response.data.accountList;
          this.dataSize = Response.data.total;
          this.listDesp=Response.data.listDesp;
        });
      },
      handleSizeChange(val) {
        this.pageSize = val;
      },
      handleCurrentChange(val) {
        this.pageNum = val;
      },
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      },
      saveForM(val){
        console.log(mAid,'------------------------保存------------------------');
        let mid='';
        for (let i = 0; i <this.accountByMaId.length; i++) {
          mid+=this.accountByMaId[i].accountId+',';
        }
        const data={
          aid:mAid,
          mid:mid
        };
        saveManage(data).then(res=>{
          console.log(res,'------------------------保存------------------------');
          this.$message({
            showClose: true,
            message: '保存成功',
            type: 'success'
          });
          this.centerDialogVisible = false;
          this.queryFn();
        }).catch(error =>{
          this.$message({
            showClose: true,
            message: '保存失败',
            type: 'error'
          });
        });

        //centerDialogVisible = false
      },
      closeAddDialog(){
        this.addAccountData = {
        };
        this.updateAccountData={};
        this.radio=16;
        this.hide=false;
        this.hide2=false;
        this.hide3=true;
      },
      addAccount(){
        console.log('addAccount()',this);
        this.dialogFormVisible = true;
        this.title='添加帐号';
        this.addAccountData = {};
        this.radio=16;
        this.hide=false;
        this.hide2=false;
        this.hide3=true;
      },
      addCancelBtn() {
        this.dialogFormVisible = false,
          this.addAccountData = {};
        this.updateAccountData={};
        this.radio=16;
        this.hide=false;
        this.hide2=false;
        this.hide3=true;
      },
      addOkBtn(formName) {
        this.$refs[formName].validate(valid =>{
          for (let i = 0; i <this.$refs.tree.getCheckedNodes().length; i++) {
            this.treeId+=this.$refs.tree.getCheckedNodes()[i].id+',';
            console.log('this.treeId=',this.treeId);
          }
          if(valid){
            const data = {
              accountId:(this.addAccountData.id=='' || this.addAccountData.id==null )?'':this.addAccountData.id,
              accountName: (this.addAccountData.name=='' || this.addAccountData.name==null )?(this.addAccountData.prefix+','+
              this.addAccountData.start+','+this.addAccountData.end):this.addAccountData.name,
              accountAlias: this.addAccountData.nameAlias,
              accountProperty:this.radio,
              accountPassword:(this.addAccountData.password=='' || this.addAccountData.password==null )?'':this.addAccountData.password,
              accountDesp:(this.departmentData.accountId=='' || this.departmentData.accountId==null )?0:this.departmentData.accountId,
              accountRights:this.treeId
            };
            console.log('进了if的判断');
            if (this.addAccountData.id=='' || this.addAccountData.id==null){
              addAccount(data).then(Response =>{
                this.$message({
                  showClose: true,
                  message: '添加成功',
                  type: 'success'
                });
                this.dialogFormVisible = false;
                this.queryFn();
              }).catch(error =>{
                this.$message({
                  showClose: true,
                  message: '添加失败帐号已存在!',
                  type: 'error'
                });
              });
            }else {
              updateAccount(data).then(Response =>{
                this.$message({
                  showClose: true,
                  message: '修改成功',
                  type: 'success'
                });
                this.dialogFormVisible = false;
                this.queryFn();
              }).catch(error =>{
                this.$message({
                  showClose: true,
                  message: '修改失败',
                  type: 'error'
                });
              });
            }

          }
        });
      },
      updateAccount(val){
        this.dialogFormVisible = true;
        this.radio=val.accountProperty;
        var radio=val.accountProperty;
        this.hide=(radio=='2')?true:false;
        this.hide2=(radio=='4' || radio=='8')?true:false;
        this.hide3=(radio=='16')?true:false;
        this.title='修改帐号';
        this.addAccountData.id=val.accountId;
        this.options=val.accountDesp;
        this.addAccountData.name=val.accountName;
        this.addAccountData.nameAlias=val.accountAlias;
        this.updateAccountData=val;
        console.log('这里是updateAccount',this.updateAccountData);
      },
      manageAccount(row){
        console.log(row.accountId,'----------------manageAccount--------------');
        this.$router.push({path:'/account/Manager',query:{aid:row.accountId}});
        /*mAid=row.accountId;
        const data={
          aid:row.accountId
        };
        findAgentById(data).then(res=>{
          console.log(res,'----------------res--------------');
          this.accountByMaId=res.data.account;
          this.listDesp1=res.data.listDesp;
        });
        findAllAccount().then(res=>{
          this.accountAll=res.data.account;
          this.listDesp=res.data.despList;
          this.despManList=res.data.despManList;
          this.accountMangen=res.data.accountMangen;
          console.log(res,'----------------res--------------');
        });
        this.centerDialogVisible=true;*/
      },
      delAccount(row){
        const data = {
          aid: row.accountId
        };
        this.$confirm('此操作将永久删除，请问是否继续？', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          if(this.tableData.length != 1 || this.pageNum != 0) {
            console.log('gggg');
            delAccount(data).then(Response => {
              console.log('delAccount------------------------',Response);
              this.$message({
                showClose: true,
                message: '删除成功',
                type: 'success'
              });
              this.queryFn();
            }).catch(error => {
              this.$message({
                showClose: true,
                message: '删除失败',
                type: 'error'
              });
            });
          }else {
            this.$message({
              showClose: true,
              message: '至少保留一条数据',
              type: 'error'
            });
          }
        });
      }
    }
  };
</script>

<style>
  .input1{
    margin-top: 30px;
    margin-right: 20px;
  }
  .input2{
    margin-right: 20px;
  }
  #monitor{
    width: 100%;
    display: inline-flex;
  }
  .monitor_info{
    margin-left: 10px;
    width: 70%;
  }
  .monitor_tree{
    margin-left: 3.5%;
    margin-top: -8.5%;
  }
  .el-dialog__header {
    align-items: center;
    justify-content: center;
    display: flex;
    background-color: #5d9fe394;
  }
  .el-dialog__body {
    padding: 0px 30px;
  }
</style>
