import { defineStore } from 'pinia'
import axios from 'axios'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    token: localStorage.getItem('token') || null,
    loading: false,
    error: null
  }),
  getters: {
    isAuthenticated: (state) => !!state.token,
    getUser: (state) => state.user
  },
  actions: {
    async login(username, password) {
      this.loading = true
      this.error = null
      
      try {
        const response = await axios.post('/api/auth/login', {
          username,
          password
        })
        
        this.token = response.data.token
        this.user = response.data.user
        localStorage.setItem('token', response.data.token)
        
        this.loading = false
        return true
      } catch (error) {
        this.error = error.response?.data?.message || '登录失败'
        this.loading = false
        return false
      }
    },
    
    logout() {
      this.user = null
      this.token = null
      localStorage.removeItem('token')
    },
    
    async fetchUser() {
      if (!this.token) return
      
      try {
        const response = await axios.get('/api/auth/me', {
          headers: {
            Authorization: `Bearer ${this.token}`
          }
        })
        
        this.user = response.data
      } catch (error) {
        this.logout()
      }
    }
  }
})    