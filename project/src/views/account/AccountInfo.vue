<!--suppress ALL -->
<template>
  <div id="accountInfo">
<el-row>
  <el-row>
    <el-col><span>可选择属性:</span></el-col>
    <el-col>
      <el-checkbox-group v-model="checkArr" size="medium">
        <el-col :span=12 v-for="item in optionalColumnList" :key="item.prop">
          <el-row :gutter=20>
            <el-col>
              <el-col :span=2><el-checkbox :label="item.propName" @change="menuChange(item)" ></el-checkbox></el-col>
            </el-col>
          </el-row>
        </el-col>
      </el-checkbox-group>
    </el-col>
  </el-row>
</el-row>
<el-table class="fixedtableHeight" v-loading="listLoading" ref="multipleTable" :data="showList" :header-cell-style="{background:'#96CDCD'}" stripe tooltip-effect="dark" style="width: 100%;margin-top:1%;">
  <el-table-column :label="item" :property="item.prop" v-for="item in tableColumnList" :key="item.prop" align="center">
    <template slot-scope="scope">
      <span>{{scope.row[scope.column.property]}}</span>
    </template>
  </el-table-column>
</el-table>
  </div>
</template>

<script>
  import { getAccountList } from "../../api/accountData";
  export default {
  data () {

    return {
      listLoading: false,
      showList: [{}],
      tableColumnList: [{}],
      // 这里为了简便我就没有调用后台接口获取数据，直接写的假数据  你要用的话可以调用后台接口获取tableColumnList，注意数据格式
      /*tableColumnList: [{ prop: 'id', propName: '编号' },
        { prop: 'name', propName: '名字' },
        { prop: 'age', propName: '保质期' },
        { prop: 'remark', propName: '特点' }],*/
      // 这里为了简便我就没有调用后台接口获取数据，直接写的假数据
      /*dataList: [{ id: '100001', name: '小红萝卜', age: '2年', remark: '适合油炸', country: '中国', address: '广东省深圳市' },
      ],*/
      optionalColumnList: [{ prop: 'country', propName: '出口国家' },
        { prop: 'address', propName: '零售点' }],
      checkArr: []
    }

  },
  created () {
    this.queryFn()
  },
  methods: {
    queryFn () {
      // 调用后台接口获取tableColumnList和dataList的方法写在这里

        getAccountList().then(response =>{
        this.tableColumnList = response.data.tablelist
        this.dataList = response.data.accountList
          console.log("这里是getAccountList",response )
          this.showList = this.dataList
          console.log("这里是showList",this.showList)
     })

    },
    clearQuery () {
      this.query.name = ''
      this.queryFn()
    },
    menuChange (item) {
      console.log("进menuChange方法了")
      // 注意  我这里都用的假数据，要从后台获取tableColumnList和dataList的时候
      // ，每一次调用menuChange都要重新获取tableColumnList和dataList，保证属性和数据是对应的
      let flag = true
      for (var i = 0; i < this.checkArr.length; i++) {
        console.log("this.checkArr[i] ",this.checkArr[i] )
        if (this.checkArr[i] === item.propName) {
          flag = false
          break
        }
      }
      if (!flag) {
        this.tableColumnList.push(item)
        console.log("this.tableColumnList.push(item)",this.tableColumnList.push(item))
      }
      if (flag) {
        Array.prototype.contains = function (obj) {
          var j = this.length
          while (j--) {
            if (this[j] === obj) {
              return j // 返回的这个 i 就是元素的索引下标，
            }
          }
          return false
        }
        this.tableColumnList.splice(this.tableColumnList.contains(item), 1)
      }
    }
  }
}
</script>
