<template>
  <div>
    <el-table
    :data="tableData"
    style="width: 100%;font-size: 10px"
    :row-style="{height:'15px'}"
    border
    :cell-style="{padding:'2px'}">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column prop="extension" label="分机" width="90">
      </el-table-column>
      <el-table-column prop="ctiAppIpAddr" label="CTP程序IP" width="100">
      </el-table-column>
      <el-table-column prop="computer" label="录音客户端IP" width="100">
      </el-table-column>
      <el-table-column prop="channelType" label="通道类型" width="120">
      </el-table-column>
      <el-table-column prop="enable" label="是否启用" :formatter="enable" width="80">
      </el-table-column>
      <el-table-column prop="cardType" label="录音方案" width="120">
      </el-table-column>
      <el-table-column prop="chNoInCard" label="通道号" width="80">
      </el-table-column>
      <el-table-column prop="channelIpAddr" label="分机IP" width="120">
      </el-table-column>
      <el-table-column prop="channelVirtualExt" label="虚拟分机" width="120">
      </el-table-column>
    <el-table-column label="操作" width="120">
      <template slot-scope="scope">
        <el-button
          size="mini"
          icon="el-icon-edit" circle
          @click="handleEdit(scope.$index, scope.row)"></el-button>
        <el-button
          size="mini"
          type="danger"
          icon="el-icon-delete" circle
          @click="handleDelete(scope.$index, scope.row)"></el-button>
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

    <el-dialog
      title="提示"
      :visible.sync="centerDialogVisible"
      width="75%"
      center>
      <el-form ref="form" :model="form" label-width="160px">
        <div style="display: inline-flex" class="showformInput">
        <el-form-item label="开始分机">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="结束分机">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
      </div>
        <div style="display: inline-flex" class="showformInput">
          <el-form-item label="录音客户端IP">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="分机IP">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
        </div>
        <div style="display: inline-flex" class="showformInput">
          <el-form-item label="分机MAC地址">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="CIT程序IP">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
        </div>
        <div style="display: inline-flex" class="showformInput">
          <el-form-item label="录音方案">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="通道类型">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
        </div>
        <div style="display: inline-flex" class="showformInput">
          <el-form-item label="虚拟分机">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="包大小">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
        </div>
        <div style="display: inline-flex" class="showformInput">
          <el-form-item label="SBC服务IP">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="部门">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
        </div>
        <el-form-item label="是否启动">
          <el-switch v-model="form.delivery"></el-switch>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
  import { getChannelsList} from '../../api/channelsData'; export default {
    data() {
      return {
        pageSize: 10,
        pageNum: 1,
        dataSize: 0,
        tableData: [{}],
        centerDialogVisible: false,
        form:{

        },
      }
    },
    created () {
      this.getData();
    },
    mounted() {
      this.getData();
      },
    watch:{
      pageSize: function(){
        this.getData();
      },
      pageNum: function(){
        this.getData();
      }
    },
    methods: {
      getData () {
        const data = {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        };
        getChannelsList(data).then(Response =>{
          console.log(Response,'------------------------------------------')
          this.tableData = Response.data.List;
          this.dataSize = Response.data.total;
          //this.listDesp=Response.data.listDesp;
        });
      },
      enable(row){
        console.log(row.enable,'-------------------------------enable-------------------------')
        return (row.enable===1)?'启用':'暂停';
      },
      handleSizeChange(val) {
        this.pageSize = val;
      },
      handleCurrentChange(val) {
        this.pageNum = val;
      },
      handleEdit(index, row) {
        this.centerDialogVisible= true;
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      }
    }
  }
</script>
<style>
 .showformInput .el-form-item  {
   margin-bottom: 0px;
 }
 .showformInput .el-input {
   width: 130px;
 }
</style>
