CREATE TABLE user_muscle (
     id SERIAL PRIMARY KEY,
     user_id BIGINT NOT NULL,
     muscle_group_id BIGINT NOT NULL,
     priority INT NOT NULL,

     CONSTRAINT fk_user_muscle_user
         FOREIGN KEY (user_id) REFERENCES users(id)
             ON DELETE CASCADE,

     CONSTRAINT fk_user_muscle_muscle_group
         FOREIGN KEY (muscle_group_id) REFERENCES muscle_group(id)
             ON DELETE CASCADE,

     CONSTRAINT uq_user_muscle_user_muscle_group
         UNIQUE (user_id, muscle_group_id)
);