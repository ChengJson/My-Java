DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');




DELETE FROM account_tbl;

INSERT INTO account_tbl (id, user_id, money) VALUES
(1, 'zhangsan', 10000),
(2, 'lisi', 20000);