CREATE TABLE protocol (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150),
    category VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    visibility VARCHAR(255) NOT NULL,
    duration_seconds INT NOT NULL,
    user_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_protocol_owner FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE protocol_exercise (
    id SERIAL PRIMARY KEY,
    protocol_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    duration INT,
    order_index INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_protocol_ex FOREIGN KEY (protocol_id) REFERENCES protocol(id) ON DELETE CASCADE,
    CONSTRAINT fk_exercise FOREIGN KEY (exercise_id) REFERENCES exercise(id),

    CONSTRAINT uq_protocol_exercise UNIQUE (protocol_id, exercise_id)
);

INSERT INTO protocol (name, category, type, visibility, duration_seconds, user_id, updated_at)
VALUES
    ('Morning Stretch', 'DAILY', 'STRETCHING', 'PUBLIC', 900, 1, NOW()),
    ('Post-Workout Recovery', 'POSTGAME', 'RECOVERY', 'PRIVATE', 1200, 1, NOW()),
    ('Pre-Game Warmup', 'PREGAME', 'DYNAMIC', 'PUBLIC', 600, 2, NOW());