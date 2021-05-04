CREATE TABLE IF NOT EXISTS m_user (
    user_id VARCHAR(6) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_name VARCHAR(32) NOT NULL,
    department VARCHAR(32) NOT NULL,
    role VARCHAR(32) NOT NULL,
    PRIMARY KEY(user_id)
);

CREATE TABLE IF NOT EXISTS m_book (
    book_id INTEGER NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL ,
    author VARCHAR(32),
    publisher VARCHAR(32),
    PRIMARY KEY(book_id),
    UNIQUE(title)
);

CREATE TABLE IF NOT EXISTS m_lending (
    lending_id INTEGER NOT NULL AUTO_INCREMENT,
    book_id INTEGER NOT NULL,
    user_id VARCHAR(6) NOT NULL,
    PRIMARY KEY(lending_id),
    FOREIGN KEY(book_id) REFERENCES m_book(book_id),
    FOREIGN KEY(user_id) REFERENCES m_user(user_id)
);

