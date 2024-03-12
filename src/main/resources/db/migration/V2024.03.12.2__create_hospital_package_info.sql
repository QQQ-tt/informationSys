create table hospital_package_info
(
    `id`               bigint(0)   NOT NULL AUTO_INCREMENT,
    `hospital_info_id` bigint(0)   NOT NULL COMMENT '医院信息id',
    `info_package_id`  bigint(0)   NOT NULL COMMENT '套餐信息id',
    `delete_flag`      bit(1)      NOT NULL DEFAULT b'0' COMMENT '逻辑删除',
    create_by          varchar(50) null comment '创建人',
    create_on          datetime    not null comment '创建时间',
    update_by          varchar(50) null comment '更新人',
    update_on          datetime    null comment '更新时间',
    PRIMARY KEY (`id`)
) COMMENT = '医院信息与套餐信息中间表';