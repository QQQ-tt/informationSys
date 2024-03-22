ALTER TABLE `information`.`sys_region`
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市名字' AFTER `parent_id`,
    ADD COLUMN `ctype` int NULL COMMENT '城市级别' AFTER `name`;