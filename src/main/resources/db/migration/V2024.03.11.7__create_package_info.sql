create table package_info
(
    id           int auto_increment,
    package_name varchar(255)     null comment '套餐名称',
    status       int default 0    not null comment '引用次数',
    delete_flag  bit default b'0' not null comment '逻辑删除',
    create_by    varchar(50)      null comment '创建人',
    create_on    datetime         not null comment '创建时间',
    update_by    varchar(50)      null comment '更新人',
    update_on    datetime         null comment '更新时间',
    constraint `PRIMARY`
        primary key (id)
)
    comment '检查套餐表';