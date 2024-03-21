ALTER TABLE `information`.`hospital_info`
    ADD COLUMN `region_id` varchar(25) NULL COMMENT '地区id' AFTER `hospital_name`;