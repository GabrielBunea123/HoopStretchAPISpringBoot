CREATE TABLE muscle_group (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    default_priority_number INTEGER NOT NULL DEFAULT 1
);

CREATE TABLE equipment_item (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE exercise (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    is_double_sided BOOLEAN NOT NULL,
    type VARCHAR(50),
    is_in_mobility_test BOOLEAN NOT NULL DEFAULT FALSE,
    cover_url VARCHAR(255),
    video_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE exercise_muscle_group (
    exercise_id BIGINT NOT NULL,
    muscle_group_id BIGINT NOT NULL,
    PRIMARY KEY (exercise_id, muscle_group_id),
    CONSTRAINT fk_exercise_muscle FOREIGN KEY (exercise_id) REFERENCES exercise(id),
    CONSTRAINT fk_muscle_group FOREIGN KEY (muscle_group_id) REFERENCES muscle_group(id)
);

CREATE TABLE exercise_equipment_item (
    exercise_id BIGINT NOT NULL,
    equipment_item_id BIGINT NOT NULL,
    PRIMARY KEY (exercise_id, equipment_item_id),
    CONSTRAINT fk_exercise_equipment FOREIGN KEY (exercise_id) REFERENCES exercise(id),
    CONSTRAINT fk_equipment_item FOREIGN KEY (equipment_item_id) REFERENCES equipment_item(id)
);

CREATE TABLE exercise_guidelines (
    exercise_id BIGINT NOT NULL,
    guideline VARCHAR(255) NOT NULL,
    CONSTRAINT fk_exercise_guideline FOREIGN KEY (exercise_id) REFERENCES exercise(id)
);

CREATE TABLE exercise_contraindications (
    exercise_id BIGINT NOT NULL,
    contraindication VARCHAR(255),
    CONSTRAINT fk_exercise_contraindication FOREIGN KEY (exercise_id) REFERENCES exercise(id)
);

INSERT INTO muscle_group (id, name) VALUES
    (1,'Trapezius'), (2,'Deltoids'), (3,'Triceps'), (4,'Biceps'), (5,'Chest'), (6,'Abs'), (7,'Obliques'),
    (8,'Forearm'), (9,'Lower-back'), (10,'Upper-back'), (11,'Hips & adductors'), (12,'Gluteal'),
    (13,'Quadriceps'), (14,'Hamstring'), (15,'Calves'), (16,'Tibialis'), (17,'Ankles');

INSERT INTO equipment_item (id, name) VALUES
    (1,'Foam roller'), (2,'Band'), (3,'Ball');

INSERT INTO exercise (id, name, is_double_sided, type, is_in_mobility_test, cover_url, video_url, created_at, version, is_deleted)
VALUES
    (1,'Hamstring Stretch', false, 'STATIC', true, 'https://example.com/cover1.jpg', 'https://example.com/video1.mp4', CURRENT_TIMESTAMP, 0, false),
    (2,'Shoulder Roll', false, 'DYNAMIC', false, 'https://example.com/cover2.jpg', 'https://example.com/video2.mp4', CURRENT_TIMESTAMP, 0, false),
    (3,'Quad Stretch', true, 'STATIC', false, 'https://example.com/cover3.jpg', 'https://example.com/video3.mp4', CURRENT_TIMESTAMP, 0, false);

INSERT INTO exercise_muscle_group (exercise_id, muscle_group_id) VALUES
    (1, 14), (2, 2), (3, 13);

INSERT INTO exercise_equipment_item (exercise_id, equipment_item_id) VALUES
    (1, 1), (2, 2), (3, 2);

INSERT INTO exercise_guidelines (exercise_id, guideline) VALUES
     (1, 'Hold each leg for 30 seconds'),
     (2, 'Perform 10 repetitions'),
     (3, 'Hold for 45 seconds each side');

INSERT INTO exercise_contraindications (exercise_id, contraindication) VALUES
    (1, 'Knee injury'),
    (3, 'Lower back pain'),
    (2, NULL);