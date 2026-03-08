CREATE TABLE mobility_assessment (
     id BIGSERIAL PRIMARY KEY,
     assessment_id BIGINT NOT NULL,
     user_id BIGINT NOT NULL,
     mobility_area VARCHAR(50) NOT NULL,
     score INT NOT NULL CHECK (score >= 1 AND score <= 10),
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP,
     version BIGINT NOT NULL DEFAULT 0,
     is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

     CONSTRAINT fk_mobility_assessment_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);