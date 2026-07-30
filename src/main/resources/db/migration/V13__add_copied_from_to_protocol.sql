ALTER TABLE protocol
    RENAME COLUMN user_id TO owner_id;

ALTER TABLE protocol
    ADD COLUMN copied_from BIGINT REFERENCES protocol(id) ON DELETE SET NULL;