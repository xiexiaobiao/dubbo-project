create database dubbo_db;

use dubbo_db;

drop table if exists 'dubbo_delivery';
CREATE TABLE `dubbo_delivery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `delivery_id` varchar(45) DEFAULT NULL COMMENT '快递单号',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `order_id` varchar(45) DEFAULT NULL COMMENT '订单号',
  `user_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='快递物流表'

drop table if exists 'dubbo_finance';
CREATE TABLE `dubbo_finance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `finance_id` varchar(45) DEFAULT NULL COMMENT '表ID',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `user_id` varchar(45) DEFAULT NULL,
  `balance` decimal(10,2) DEFAULT NULL COMMENT '余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='银行账户表'

drop table if exists 'dubbo_item';
CREATE TABLE `dubbo_item` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `item_id` varchar(45) DEFAULT NULL COMMENT '商品id',
  `name` varchar(45) DEFAULT NULL COMMENT '名称',
  `price` decimal(6,2) DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表'

drop table if exists 'dubbo_order';
CREATE TABLE `dubbo_order` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `order_id` varchar(45) DEFAULT NULL COMMENT '订单id',
  `detail_id` varchar(45) DEFAULT NULL COMMENT '明细id',
  `is_paid` tinyint(1) DEFAULT NULL COMMENT '是否付款',
  `is_expired` tinyint(1) DEFAULT NULL COMMENT '是否过期',
  `user_id` varchar(45) DEFAULT NULL,
  `order_desc` varchar(45) DEFAULT NULL COMMENT '订单描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20204 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='交易订单表'

drop table if exists 'dubbo_order_detail';
CREATE TABLE `dubbo_order_detail` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `order_detail_id` varchar(45) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `order_id` varchar(45) DEFAULT NULL COMMENT '订单id',
  `item_id` varchar(45) DEFAULT NULL COMMENT '商品id',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3525 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单明细表'

drop table if exists 'dubbo_stock';
CREATE TABLE `dubbo_stock` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `item_id` varchar(45) DEFAULT NULL COMMENT '商品id',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `quantity_lock` int(11) DEFAULT NULL COMMENT '锁定数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存表'
