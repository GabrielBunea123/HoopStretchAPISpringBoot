CREATE TABLE users
(
    id                  SERIAL PRIMARY KEY,
    username            VARCHAR(150) NOT NULL UNIQUE,
    email               VARCHAR(255) NOT NULL UNIQUE,
    display_name        VARCHAR(150),
    provider            VARCHAR(50)  NOT NULL DEFAULT 'EMAIL',
    password            VARCHAR(255),
    date_of_birth       VARCHAR(255),
    profile_picture_url TEXT,
    created_at          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP,
    version             BIGINT       NOT NULL DEFAULT 0,
    is_deleted          BOOLEAN      NOT NULL DEFAULT FALSE,

    CONSTRAINT chk_provider CHECK (provider IN ('GOOGLE', 'SPOTIFY', 'EMAIL'))
);

CREATE TABLE refresh_token
(
    id         SERIAL PRIMARY KEY,
    token      VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP    NOT NULL,
    user_id    BIGINT       NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    version    BIGINT       NOT NULL DEFAULT 0,
    is_deleted BOOLEAN      NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE TABLE access_token
(
    id               SERIAL PRIMARY KEY,
    token            VARCHAR(255) NOT NULL,
    expires_at       TIMESTAMP    NOT NULL,
    token_type       VARCHAR(50)  NOT NULL,
    user_id          BIGINT       NOT NULL,
    refresh_token_id BIGINT       NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP,
    version          BIGINT       NOT NULL DEFAULT 0,
    is_deleted       BOOLEAN      NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_access_token_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_access_token_refresh
        FOREIGN KEY (refresh_token_id)
            REFERENCES refresh_token (id)
            ON DELETE CASCADE
);
