DROP TABLE IF EXISTS m_lending CASCADE ;
DROP TABLE IF EXISTS m_stock CASCADE ;
DROP TABLE IF EXISTS m_book CASCADE ;
DROP TABLE IF EXISTS m_user CASCADE ;

CREATE TABLE IF NOT EXISTS m_user (
    user_id VARCHAR(6) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_name VARCHAR(32) NOT NULL,
    mail_addr VARCHAR(128) NOT NULL,
    department VARCHAR(32) NOT NULL,
    role VARCHAR(32) NOT NULL,
    PRIMARY KEY(user_id)
);

CREATE TABLE IF NOT EXISTS m_book (
    book_id SERIAL NOT NULL,
    title VARCHAR(255) NOT NULL ,
    author VARCHAR(32),
    publisher VARCHAR(32),
    PRIMARY KEY(book_id),
    UNIQUE(title)
);

CREATE TABLE IF NOT EXISTS m_stock (
    stock_id SERIAL NOT NULL,
    book_id INTEGER NOT NULL,
    state VARCHAR(16) NOT NULL,
    PRIMARY KEY(stock_id),
    FOREIGN KEY(book_id) REFERENCES m_book(book_id)
);

CREATE TABLE IF NOT EXISTS m_lending (
    lending_id SERIAL NOT NULL,
    stock_id INTEGER NOT NULL,
    user_id VARCHAR(6) NOT NULL,
    lending_date DATE,
    PRIMARY KEY(lending_id),
    FOREIGN KEY(stock_id) REFERENCES m_stock(stock_id),
    FOREIGN KEY(user_id) REFERENCES m_user(user_id)
);
