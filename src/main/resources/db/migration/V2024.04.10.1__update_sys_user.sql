alter table sys_user
    add hospital_status bit null comment '医院自动添加状态' after status;