# 教学管理系统

## 项目简介
本教学管理系统是一个基于 Spring Boot 和 Vue.js 构建的 Web 应用程序，用于实现教学过程中的成绩管理、课程管理、教师管理、学生管理以及请假管理等功能。系统采用前后端分离的架构，后端使用 Spring Boot 作为核心框架，结合 Spring Data JPA 进行数据库操作；前端使用 Vue.js 框架，搭配 ElementPlus 组件库，提供了良好的用户界面和交互体验。

## 技术栈

**后端**
- [x] Spring Boot 3.2.0：作为后端开发的核心框架，简化了项目的配置和开发流程。
- [x] Spring Data JPA：用于与数据库进行交互，实现数据的持久化操作。
- [x] MySQL：作为系统的数据库，存储教学管理相关的数据。
- [x] Lombok：简化 Java 代码，减少样板代码的编写。
- [x] Swagger：用于生成 API 文档，方便接口的测试和开发。
- [x] JWT：用于实现用户认证和授权。

**前端**
- [x] Vue.js：渐进式 JavaScript 框架，用于构建用户界面。
- [x] ElementPlus：基于 Vue 3 的桌面端组件库，提供了丰富的 UI 组件。
- [x] Vite：构建工具，提供快速的开发体验。

## 项目结构
```shell
teaching-management-system/
├── backend/                   # 后端代码
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/teachingmanagement/
│   │   │   │       ├── controller/    # 控制器层
│   │   │   │       ├── entity/        # 实体类
│   │   │   │       ├── repository/    # 数据访问层
│   │   │   │       └── service/       # 服务层
│   │   │   └── resources/
│   │   │       ├── application.yml    # 配置文件
│   │   │       └── init.sql           # 初始化 SQL 脚本
│   │   └── test/
│   └── pom.xml                      # Maven 配置文件
├── frontend/                  # 前端代码
│   ├── src/
│   │   ├── App.vue            # 根组件
│   │   ├── main.js            # 入口文件
│   │   ├── router/            # 路由配置
│   │   └── stores/            # 状态管理
│   ├── package.json           # npm 配置文件
│   └── vite.config.js         # Vite 配置文件
└── .idea/                     # IDE 配置文件
```

## 环境准备
**后端**
- [x] Java 21：确保已安装 Java 21 及以上版本。
- [x] Maven：用于管理项目依赖。
- [x] MySQL：安装并启动 MySQL 数据库，创建名为 teaching_management 的数据库。

**前端**
- [x] Node.js：确保已安装 Node.js 18 及以上版本。
- [x] npm：Node.js 包管理器，用于安装前端依赖。

## 快速开始
**后端**

1. 打开 backend/src/main/resources/application.yml 文件，配置数据库连接信息：
  ```shell
  spring:
  datasource:
    url: jdbc:mysql://localhost:3306/teaching_management?useSSL=false&serverTimezone=UTC
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
  ```

2.在项目根目录下，使用 Maven 命令构建并运行后端项目：
  ```shell
  cd backend
  mvn spring-boot:run
  ```
  后端服务将启动在 http://localhost:8080

**前端**

1.在项目根目录下，使用 npm 安装前端依赖：
  ```shell
  cd frontend
  npm install
  ```

2.启动前端开发服务器：
  ```shell
  npm run dev
  ```
  前端应用将启动在 http://localhost:5173。

## API文档
系统使用 Swagger 生成 API 文档，访问 http://localhost:8080/swagger-ui.html 可以查看和测试后端接口。

## 功能模块
**成绩管理**
- [x] 获取所有成绩：GET /api/grades
- [x] 根据学生 ID 获取成绩：GET /api/grades/student/{studentId}
- [x] 根据课程 ID 获取成绩：GET /api/grades/course/{courseId}
- [x] 根据作业 ID 获取成绩：GET /api/grades/assignment/{assignmentId}
- [x] 根据 ID 获取单个成绩：GET /api/grades/{id}
- [x] 创建成绩：POST /api/grades
- [x] 更新成绩：PUT /api/grades/{id}
- [x] 删除成绩：DELETE /api/grades/{id}

**请假管理**
- [x] 获取所有请假记录：GET /api/leaves
- [x] 根据学生 ID 获取请假记录：GET /api/leaves/student/{studentId}
- [x] 根据状态获取请假记录：GET /api/leaves/status/{status}
- [x] 根据 ID 获取单个请假记录：GET /api/leaves/{id}
- [x] 创建请假记录：POST /api/leaves
- [x] 批准请假记录：PUT /api/leaves/approve/{id}
- [x] 拒绝请假记录：PUT /api/leaves/reject/{id}
- [x] 删除请假记录：DELETE /api/leaves/{id}

## 注意事项
请确保数据库服务正常运行，并且数据库连接信息配置正确。
前端开发服务器会自动代理 /api 开头的请求到后端服务，确保后端服务正常启动。

## 贡献
如果你对本项目有任何建议或改进意见，欢迎提交 Issues 或 Pull Requests。

## 许可证
本项目采用 ISC 许可证。
      
