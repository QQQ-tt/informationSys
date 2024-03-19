ALTER TABLE `information`.`sys_role`
    MODIFY COLUMN `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '角色编号' AFTER `role_name`;