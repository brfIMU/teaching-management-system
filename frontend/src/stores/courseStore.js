import { defineStore } from 'pinia'
import axios from 'axios'

export const useCourseStore = defineStore('course', {
  state: () => ({
    courses: [],
    course: null,
    loading: false,
    error: null
  }),
  actions: {
    async fetchCourses() {
      this.loading = true
      this.error = null
      
      try {
        const response = await axios.get('/api/courses')
        this.courses = response.data
        this.loading = false
      } catch (error) {
        this.error = error.response?.data?.message || '获取课程列表失败'
        this.loading = false
      }
    },
    
    async fetchCourse(id) {
      this.loading = true
      this.error = null
      
      try {
        const response = await axios.get(`/api/courses/${id}`)
        this.course = response.data
        this.loading = false
      } catch (error) {
        this.error = error.response?.data?.message || '获取课程详情失败'
        this.loading = false
      }
    },
    
    async createCourse(courseData) {
      this.loading = true
      this.error = null
      
      try {
        const response = await axios.post('/api/courses', courseData)
        this.courses.push(response.data)
        this.loading = false
        return true
      } catch (error) {
        this.error = error.response?.data?.message || '创建课程失败'
        this.loading = false
        return false
      }
    },
    
    async updateCourse(id, courseData) {
      this.loading = true
      this.error = null
      
      try {
        const response = await axios.put(`/api/courses/${id}`, courseData)
        const index = this.courses.findIndex(c => c.id === id)
        if (index !== -1) {
          this.courses[index] = response.data
        }
        this.loading = false
        return true
      } catch (error) {
        this.error = error.response?.data?.message || '更新课程失败'
        this.loading = false
        return false
      }
    },
    
    async deleteCourse(id) {
      this.loading = true
      this.error = null
      
      try {
        await axios.delete(`/api/courses/${id}`)
        this.courses = this.courses.filter(c => c.id !== id)
        this.loading = false
        return true
      } catch (error) {
        this.error = error.response?.data?.message || '删除课程失败'
        this.loading = false
        return false
      }
    }
  }
})    