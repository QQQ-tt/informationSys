create table sys_menu
(
    id          int auto_increment,
    menu_name   varchar(50)      not null comment '菜单名称',
    menu_code   varchar(50)      null comment '菜单编号',
    path        varchar(255)     null comment '菜单地址',
    parent_id   int default 0    not null comment '父级id',
    delete_flag bit default b'0' not null comment '逻辑删除',
    create_by   varchar(50)      null comment '创建人',
    create_on   datetime         not null comment '创建时间',
    update_by   varchar(50)      null comment '更新人',
    update_on   datetime         null comment '更新时间',
    constraint `PRIMARY`
        primary key (id)
)
    comment '菜单信息表';