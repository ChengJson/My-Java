DROP TABLE IF EXISTS user;

CREATE TABLE user
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS order_tbl;
CREATE TABLE order_tbl (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id varchar(255) DEFAULT NULL,
  commodity_code varchar(255) DEFAULT NULL,
  count int(11) DEFAULT 0,
  money int(11) DEFAULT 0,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS undo_log;
CREATE TABLE undo_log (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  branch_id bigint(20) NOT NULL,
  xid varchar(100) NOT NULL,
  context varchar(128) NOT NULL,
  rollback_info longblob NOT NULL,
  log_status int(11) NOT NULL,
  log_created datetime NOT NULL,
  log_modified datetime NOT NULL,
  ext varchar(100) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ux_undo_log (xid,branch_id)
);