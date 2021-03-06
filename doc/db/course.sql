create table coures(
  id char(8) not null default '' comment 'id',
  name varchar(50) not null  comment '名称',
  summary varchar(2000) comment '概述',
  time int default 0 comment '时长|单位秒',
  price decimal(8,2) not null default 0.00 comment '价格',
  image varchar(100) comment '封面',
  level char(1) not null comment '级别',
  charge char(1) comment '类型|CHARGE("C","收费"),FREE("F","免费")',
  status char (1) comment '状态|PUBLISH("P","发布"),DRAFT("D","草稿")',
  enroll integer default 0 comment '报名数',
  sort int comment '排序',
  create_at datetime(3) comment '创建时间',
  update_at datetime(3) comment '修改时间',
  primary key (id)
)engine =innodb default charset =utf8mb4 comment '课程表'