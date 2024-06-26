create table collect_info
(
    id int NOT NULL AUTO_INCREMENT,
    hospital_id        INT              NOT NULL COMMENT '医院id',
    collect_name       varchar(255)     NOT NULL COMMENT '采集人姓名',
    collect_sex        bit              NOT NULL COMMENT '采集人性别：0是女，1是男',
    collect_nation     varchar(255)     NOT NULL COMMENT '采集人民族',
    collect_birth_data date             NOT NULL COMMENT '采集人出生日期',
    collect_card       varchar(255)     NOT NULL COMMENT '采集人身份证号',
    package_id         INT              NOT NULL COMMENT '套餐ID',
    tube_card          varchar(255)     NOT NULL COMMENT '试管条码号',
    sampling_point     varchar(255)     NOT NULL COMMENT '采样点',
    status             bit default b'0' not null comment '作废状态：0是未作废，1是已作废',
    delete_flag        bit default b'0' not null comment '逻辑删除',
    create_by          varchar(50)      null comment '创建人',
    create_on          datetime         not null comment '创建时间',
    update_by          varchar(50)      null comment '更新人',
    update_on          datetime         null comment '更新时间',
    PRIMARY KEY (id)
) COMMENT = '采集信息管理表';