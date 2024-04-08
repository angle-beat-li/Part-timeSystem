CREATE TABLE `USER_JOB`  (
  `id` int NOT NULL,
  `user_id` int NULL COMMENT '用户id',
  `job_id` int NULL COMMENT '职位id',
  `apply_time` datetime NULL COMMENT '申请时间',
  PRIMARY KEY (`id`)
);

CREATE TABLE `JOB`  (
  `job_name` varchar(255) NULL COMMENT '职位名称',
  `id` int NOT NULL,
  `Released` date NULL COMMENT '发布时间',
  `address` varchar(255) NULL COMMENT '发布地点',
  `user_id` int NULL COMMENT '发布职位人的id',
  `description` varchar(255) NULL COMMENT '岗位描述',
  PRIMARY KEY (`id`)
);

CREATE TABLE `ROLE`  (
  `id` int NOT NULL COMMENT '权限',
  `descrip` varchar(255) NULL COMMENT '权限描述',
  `name` varchar(255) NULL COMMENT '名称',
  PRIMARY KEY (`id`)
);

CREATE TABLE `USER`  (
  `phone` int NOT NULL COMMENT '电话号码，用户登录账号',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `address` varchar(255) NULL COMMENT '用户地址',
  `status` int NULL COMMENT '0-已注销，1-在使用',
  `name` varchar(255) NULL COMMENT '用户名称',
  `resume` varchar(255) NULL COMMENT '简历名称',
  PRIMARY KEY (`phone`)
);

CREATE TABLE `USER_ROLE`  (
  `user_id` int NULL COMMENT '用户id',
  `id` int NOT NULL,
  `role_id` int NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
);

