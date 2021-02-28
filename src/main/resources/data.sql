INSERT INTO m_user (user_id, password, user_name, department, role)
VALUES('123456', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '白洲次郎', '東京営業所', 'ROLE_GENERAL');
/* VALUES('123456', 'password', '白洲次郎', '東京営業所', 'ROLE_GENERAL'); */

INSERT INTO m_user (user_id, password, user_name, department, role)
VALUES('112233', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '吉田茂', '福岡営業所', 'ROLE_ADMIN');
/* VALUES('112233', 'passpass', '吉田茂', '福岡営業所', 'ROLE_ADMIN'); */

INSERT INTO m_book (book_id, title, author, publisher)
VALUES('000001', 'Effective Java', 'ジョシュア・ブロック', '丸善出版');

INSERT INTO m_book (book_id, title, author, publisher)
VALUES('000002', 'JUnit実践入門', '渡辺修司', '技術評論社');

INSERT INTO m_book (book_id, title, author, publisher)
VALUES('000003', 'エクストリームプログラミング', 'ケント・ベック', 'オーム社');