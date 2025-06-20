<template>
  <div class="course-view">
    <el-card class="filter-card">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input
            v-model="searchQuery"
            placeholder="请输入课程名称"
            @keyup.enter="fetchCourses"
          >
            <template #append>
              <el-button @click="fetchCourses">搜索</el-button>
            </template>
          </el-input>
        </el-col>
        
        <el-col :span="6">
          <el-select
            v-model="teacherId"
            placeholder="请选择教师"
            @change="fetchCourses"
          >
            <el-option
              v-for="teacher in teachers"
              :key="teacher.id"
              :label="teacher.name"
              :value="teacher.id"
            ></el-option>
          </el-select>
        </el-col>
        
        <el-col :span="6">
          <el-select
            v-model="status"
            placeholder="请选择状态"
            @change="fetchCourses"
          >
            <el-option label="全部" value=""></el-option>
            <el-option label="进行中" value="active"></el-option>
            <el-option label="已结束" value="completed"></el-option>
          </el-select>
        </el-col>
        
        <el-col :span="6" style="text-align: right">
          <el-button type="primary" @click="handleCreate">
            <el-icon><Plus /></el-icon>
            <span>创建课程</span>
          </el-button>
        </el-col>
      </el-row>
    </el-card>
    
    <el-card class="table-card">
      <el-table
        :data="courses"
        stripe
        border
        @row-click="handleRowClick"
        highlight-current-row
      >
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="课程名称"></el-table-column>
        <el-table-column prop="teacherName" label="授课教师"></el-table-column>
        <el-table-column prop="startDate" label="开始日期" width="120"></el-table-column>
        <el-table-column prop="endDate" label="结束日期" width="120"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ getStatusText(scope.row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button
              size="small"
              type="primary"
              @click="handleEdit(scope.row)"
            >
              <el-icon><Edit /></el-icon>
              <span>编辑</span>
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row)"
            >
              <el-icon><Delete /></el-icon>
              <span>删除</span>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        class="pagination"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>
    
    <!-- 创建/编辑课程对话框 -->
    <el-dialog :visible.sync="dialogVisible" title="课程管理">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="120px"
      >
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="formData.name" />
        </el-form-item>
        
        <el-form-item label="授课教师" prop="teacherId">
          <el-select v-model="formData.teacherId" placeholder="请选择教师">
            <el-option
              v-for="teacher in teachers"
              :key="teacher.id"
              :label="teacher.name"
              :value="teacher.id"
            ></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker
            v-model="formData.startDate"
            type="date"
            placeholder="选择日期"
            value-format="YYYY-MM-DD"
          ></el-date-picker>
        </el-form-item>
        
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker
            v-model="formData.endDate"
            type="date"
            placeholder="选择日期"
            value-format="YYYY-MM-DD"
          ></el-date-picker>
        </el-form-item>
        
        <el-form-item label="课程描述" prop="description">
          <el-input
            v-model="formData.description"
            type="textarea"
            :rows="4"
          ></el-input>
        </el-form-item>
        
        <el-form-item label="课程状态" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio label="active">进行中</el-radio>
            <el-radio label="completed">已结束</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveCourse">保存</el-button>
        </span>
      </template>
    </el-dialog>
    
    <!-- 删除确认对话框 -->
    <el-dialog
      title="确认删除"
      :visible.sync="deleteDialogVisible"
      width="30%"
      :before-close="handleCloseDeleteDialog"
    >
      <template #content>
        <p>确定要删除课程 "{{ deleteCourseName }}" 吗？</p>
      </template>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="deleteDialogVisible = false">取消</el-button>
          <el-button type="danger" @click="confirmDelete">确认删除</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { useCourseStore } from '../stores/courseStore'
import { useTeacherStore } from '../stores/teacherStore'
import { Plus, Edit, Delete, Check, Close } from '@element-plus/icons-vue'

const courseStore = useCourseStore()
const teacherStore = useTeacherStore()

// 筛选条件
const searchQuery = ref('')
const teacherId = ref('')
const status = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 课程列表
const courses = ref([])
const teachers = ref([])

// 对话框状态
const dialogVisible = ref(false)
const formRef = ref(null)
const formData = reactive({
  id: null,
  name: '',
  teacherId: '',
  startDate: '',
  endDate: '',
  description: '',
  status: 'active'
})

const formRules = reactive({
  name: [
    { required: true, message: '请输入课程名称', trigger: 'blur' },
    { min: 2, max: 50, message: '课程名称长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  teacherId: [
    { required: true, message: '请选择授课教师', trigger: 'change' }
  ],
  startDate: [
    { required: true, message: '请选择开始日期', trigger: 'change' }
  ],
  endDate: [
    { required: true, message: '请选择结束日期', trigger: 'change' },
    { validator: validateEndDate, trigger: 'change' }
  ]
})

// 删除确认对话框
const deleteDialogVisible = ref(false)
const deleteCourseId = ref(null)
const deleteCourseName = ref('')

// 状态转换
const getStatusText = (status) => {
  return status === 'active' ? '进行中' : '已结束'
}

const getStatusType = (status) => {
  return status === 'active' ? 'success' : 'info'
}

// 日期验证
const validateEndDate = (rule, value, callback) => {
  if (value && formData.startDate && new Date(value) < new Date(formData.startDate)) {
    callback(new Error('结束日期不能早于开始日期'))
  } else {
    callback()
  }
}

// 获取课程列表
const fetchCourses = async () => {
  await courseStore.fetchCourses({
    search: searchQuery.value,
    teacherId: teacherId.value,
    status: status.value,
    page: currentPage.value,
    size: pageSize.value
  })
  
  courses.value = courseStore.courses
  total.value = courseStore.total
}

// 获取教师列表
const fetchTeachers = async () => {
  await teacherStore.fetchTeachers()
  teachers.value = teacherStore.teachers
}

// 分页处理
const handleSizeChange = (newSize) => {
  pageSize.value = newSize
  fetchCourses()
}

const handleCurrentChange = (newPage) => {
  currentPage.value = newPage
  fetchCourses()
}

// 新增课程
const handleCreate = () => {
  formData.id = null
  formData.name = ''
  formData.teacherId = ''
  formData.startDate = ''
  formData.endDate = ''
  formData.description = ''
  formData.status = 'active'
  
  dialogVisible.value = true
  nextTick(() => {
    formRef.value.resetFields()
  })
}

// 编辑课程
const handleEdit = (row) => {
  formData.id = row.id
  formData.name = row.name
  formData.teacherId = row.teacherId
  formData.startDate = row.startDate
  formData.endDate = row.endDate
  formData.description = row.description
  formData.status = row.status
  
  dialogVisible.value = true
}

// 保存课程
const saveCourse = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      let success
      
      if (formData.id) {
        // 更新课程
        success = await courseStore.updateCourse(formData.id, formData)
      } else {
        // 创建课程
        success = await courseStore.createCourse(formData)
      }
      
      if (success) {
        fetchCourses()
        dialogVisible.value = false
        ElMessage({
          message: formData.id ? '课程更新成功' : '课程创建成功',
          type: 'success'
        })
      }
    } else {
      console.log('验证失败')
      return false
    }
  })
}

// 删除课程
const handleDelete = (row) => {
  deleteCourseId.value = row.id
  deleteCourseName.value = row.name
  deleteDialogVisible.value = true
}

const confirmDelete = async () => {
  const success = await courseStore.deleteCourse(deleteCourseId.value)
  
  if (success) {
    fetchCourses()
    deleteDialogVisible.value = false
    ElMessage({
      message: '课程删除成功',
      type: 'success'
    })
  }
}

const handleCloseDeleteDialog = (done) => {
  deleteDialogVisible.value = false
  done()
}

// 表格行点击
const handleRowClick = (row) => {
  console.log('点击行:', row)
}

onMounted(() => {
  fetchTeachers()
  fetchCourses()
})
</script>

<style scoped>
.course-view {
  padding: 20px;
}

.filter-card {
  margin-bottom: 20px;
}

.table-card {
  min-height: 400px;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}
</style>    