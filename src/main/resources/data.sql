INSERT INTO m_user (user_id, password, user_name, department, role)
VALUES('123456', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '白洲次郎', '東京営業所', 'ROLE_GENERAL');
/* VALUES('123456', 'password', '白洲次郎', '東京営業所', 'ROLE_GENERAL'); */

INSERT INTO m_user (user_id, password, user_name, department, role)
VALUES('112233', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '吉田茂', '福岡営業所', 'ROLE_ADMIN');
/* VALUES('112233', 'passpass', '吉田茂', '福岡営業所', 'ROLE_ADMIN'); */


INSERT INTO m_book (title, author, publisher)
VALUES('Effective Java', 'ジョシュア・ブロック', '丸善出版');
INSERT INTO m_book (title, author, publisher)
VALUES('JUnit実践入門', '渡辺修司', '技術評論社');
INSERT INTO m_book (title, author, publisher)
VALUES('エクストリームプログラミング', 'ケント・ベック', 'オーム社');
INSERT INTO m_book (title, author, publisher)
VALUES('Java1', 'aaa', 'bbb');
INSERT INTO m_book (title, author, publisher)
VALUES('Java2', 'aaa', 'bbb');
INSERT INTO m_book (title, author, publisher)
VALUES('Java3', 'aaa', 'bbb');


INSERT INTO m_stock (book_id, state)
VALUES('1', 'lending');
INSERT INTO m_stock (book_id, state)
VALUES('1', 'stock');
INSERT INTO m_stock (book_id, state)
VALUES('2', 'lending');
INSERT INTO m_stock (book_id, state)
VALUES('2', 'stock');
INSERT INTO m_stock (book_id, state)
VALUES('3', 'stock');
INSERT INTO m_stock (book_id, state)
VALUES('3', 'stock');
INSERT INTO m_stock (book_id, state)
VALUES('4', 'lending');
INSERT INTO m_stock (book_id, state)
VALUES('5', 'stock');
INSERT INTO m_stock (book_id, state)
VALUES('6', 'reserve');


INSERT INTO m_lending (stock_id, user_id, lending_date)
VALUES('1', '112233', '2021-06-01');
INSERT INTO m_lending (stock_id, user_id, lending_date)
VALUES('3', '123456', '2021-06-02');
INSERT INTO m_lending (stock_id, user_id, lending_date)
VALUES('7', '123456', '2021-06-03');
