
create database dubbo_mall;

use dubbo_mall;

CREATE TABLE `dubbo_item` (
  `id` bigint(10) NOT NULL,
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `item_id` varchar(45) DEFAULT NULL COMMENT '商品id',
  `name` varchar(45) DEFAULT NULL COMMENT '名称',
  `price` decimal(6,0) DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';

CREATE TABLE `dubbo_order` (
  `id` bigint(10) NOT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `order_id` varchar(45) DEFAULT NULL COMMENT '订单id',
  `detail_id` varchar(45) DEFAULT NULL COMMENT '明细id',
  `is_paid` tinyint(1) DEFAULT NULL COMMENT '是否付款',
  `is_expired` tinyint(1) DEFAULT NULL COMMENT '是否过期',
  `order_desc` varchar(45) DEFAULT NULL COMMENT '订单描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='交易订单表';

CREATE TABLE `dubbo_order_detail` (
  `id` bigint(10) NOT NULL,
  `grmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `order_id` varchar(45) DEFAULT NULL COMMENT '订单id',
  `item_id` varchar(45) DEFAULT NULL COMMENT '商品id',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单明细表';

CREATE TABLE `dubbo_stock` (
  `id` bigint(10) NOT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `item_id` varchar(45) DEFAULT NULL COMMENT '商品id',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `quantity_lock` int(11) DEFAULT NULL COMMENT '锁定数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存表'