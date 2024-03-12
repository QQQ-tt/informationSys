create table collect_info
(
    id                 bigint(0)    NOT NULL AUTO_INCREMENT,
    district           varchar(255) NOT NULL COMMENT '地区名字',
    hospital_name      varchar(255) NOT NULL COMMENT '医院名字',
    collect_name       varchar(255) NOT NULL COMMENT '采集人姓名',
    collect_sex        bit(1)       NOT NULL COMMENT '采集人性别：0是女，1是男',
    collect_nation     varchar(255) NOT NULL COMMENT '采集人民族',
    collect_birth_data date         NOT NULL COMMENT '采集人出生日期',
    collect_card       varchar(255) NOT NULL COMMENT '采集人身份证号',
    package_name       INT          NOT NULL COMMENT '套餐ID',
    tube_card          varchar(255) NOT NULL COMMENT '试管条码号',
    sampling_point     varchar(255) NOT NULL COMMENT '采样点',
    create_by            varchar(50)  null comment '创建人',
    create_on            datetime     not null comment '创建时间',
    update_by            varchar(50)  null comment '更新人',
    update_on            datetime     null comment '更新时间',
    PRIMARY KEY (id)
) COMMENT = '采集信息管理表';