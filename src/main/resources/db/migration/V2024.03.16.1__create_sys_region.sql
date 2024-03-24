create table sys_region
(
    `id` int NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市名字',
    `parent_id` int(11) NULL DEFAULT NULL COMMENT '父节点id',
    PRIMARY KEY (`id`)
) COMMENT = '地区表';