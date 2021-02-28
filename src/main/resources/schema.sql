/* ユーザーマスタ */
CREATE TABLE IF NOT EXISTS m_user (
    user_id VARCHAR(6) PRIMARY KEY,
    password VARCHAR(255),
    user_name VARCHAR(32),
    department VARCHAR(32),
    role VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS m_book (
    book_id VARCHAR(8) PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(32),
    publisher VARCHAR(32)
);