create table sys_user
(
    id          int auto_increment,
    name        varchar(100)     not null comment '姓名',
    phone       varchar(20)      null comment '联系方式',
    user_id     varchar(100)     not null comment '用户名',
    password    varchar(255)     null comment '密码',
    status      bit default b'1' not null comment '状态',
    delete_flag bit default b'0' not null comment '逻辑删除',
    create_by   varchar(50)      null comment '创建人',
    create_on   datetime         not null comment '创建时间',
    update_by   varchar(50)      null comment '更新人',
    update_on   datetime         null comment '更新时间',
    constraint `PRIMARY`
        primary key (id)
)
    comment '用户信息表';