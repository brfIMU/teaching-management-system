<template>
  <div id="app">
    <el-container>
      <el-header v-if="isAuthenticated">
        <div class="logo">教学管理系统</div>
        <div class="user-info">
          <span>{{ user?.username || '未知用户' }}</span>
          <el-button size="small" @click="logout">退出登录</el-button>
        </div>
      </el-header>
      
      <el-container v-if="isAuthenticated">
        <el-aside width="200px">
          <el-menu
            :default-active="activeMenu"
            class="el-menu-vertical"
            @open="handleOpen"
            @close="handleClose"
            router
          >
            <el-menu-item index="/">
              <template #title>
                <el-icon><HomeFilled /></el-icon>
                <span>首页</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/courses">
              <template #title>
                <el-icon><Notebook /></el-icon>
                <span>课程管理</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/teachers">
              <template #title>
                <el-icon><UserFilled /></el-icon>
                <span>教师管理</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/students">
              <template #title>
                <el-icon><Users /></el-icon>
                <span>学生管理</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/scores">
              <template #title>
                <el-icon><Document /></el-icon>
                <span>成绩管理</span>
              </template>
            </el-menu-item>
          </el-menu>
        </el-aside>
        
        <el-main>
          <router-view />
        </el-main>
      </el-container>
      
      <router-view v-else />
    </el-container>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from './stores/authStore'
import { HomeFilled, Notebook, UserFilled, Users, Document } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

const isAuthenticated = computed(() => authStore.isAuthenticated)
const user = computed(() => authStore.user)
const activeMenu = computed(() => route.path)

const logout = () => {
  authStore.logout()
  router.push({ name: 'login' })
}

const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}

const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}

onMounted(() => {
  if (isAuthenticated.value) {
    authStore.fetchUser()
  }
})
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100vh;
}

.el-header {
  background-color: #333;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.logo {
  font-size: 24px;
  font-weight: bold;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 20px;
}

.el-aside {
  background-color: #404040;
  color: white;
}

.el-menu-vertical {
  border-right: none;
}
</style>    