ALTER TABLE sys_role
    MODIFY COLUMN `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '角色编号' AFTER `role_name`;
alter table sys_menu
    add icon varchar(50) not null comment '图标' after menu_code;