USE tasks_db;

CREATE TABLE IF NOT EXISTS tasks (
    id CHAR(36) NOT NULL DEFAULT (UUID()),
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    progress INT NOT NULL DEFAULT 0,
    status ENUM('PROGRESS', 'COMPLETE') NOT NULL DEFAULT 'PROGRESS',
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
