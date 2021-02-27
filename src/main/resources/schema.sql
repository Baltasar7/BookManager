/* ユーザーマスタ */
CREATE TABLE IF NOT EXISTS m_user (
    user_id VARCHAR(6) PRIMARY KEY,
    password VARCHAR(255),
    user_name VARCHAR(32),
    department VARCHAR(32),
    role VARCHAR(32)
);