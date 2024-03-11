create table sys_role_menu
(
    id          int auto_increment,
    role_id     int              not null comment '角色id',
    menu_id     int              not null comment '菜单id',
    delete_flag bit default b'0' not null comment '逻辑删除',
    create_by   varchar(50)      null comment '创建人',
    create_on   datetime         not null comment '创建时间',
    update_by   varchar(50)      null comment '更新人',
    update_on   datetime         null comment '更新时间',
    constraint `PRIMARY`
        primary key (id)
)
    comment '角色菜单关系表';