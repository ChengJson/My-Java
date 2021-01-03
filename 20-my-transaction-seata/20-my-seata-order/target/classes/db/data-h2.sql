DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');




DELETE FROM order_tbl;

INSERT INTO order_tbl (id,user_id ,commodity_code, count,money) VALUES
(1, 'zhangsan', '1号商品',2,100),
(2, 'lisi', '2号商品',1,200);