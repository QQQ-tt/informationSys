create table sys_user_role
(
    id          int auto_increment,
    user_id     int              not null comment '用户id',
    role_id     int              not null comment '角色id',
    delete_flag bit default b'0' not null comment '逻辑删除',
    create_by   varchar(50)      null comment '创建人',
    create_on   datetime         not null comment '创建时间',
    update_by   varchar(50)      null comment '更新人',
    update_on   datetime         null comment '更新时间',
    constraint `PRIMARY`
        primary key (id)
)
    comment '用户角色关系表';