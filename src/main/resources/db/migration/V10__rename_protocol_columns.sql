ALTER TABLE protocol
    RENAME COLUMN type TO purpose;

ALTER TABLE protocol
    RENAME COLUMN category TO target;

ALTER TABLE protocol
    ALTER COLUMN purpose DROP NOT NULL;

ALTER TABLE protocol
    ALTER COLUMN target DROP NOT NULL;

ALTER TABLE protocol
    ALTER COLUMN duration_seconds DROP NOT NULL;

UPDATE protocol
SET target = 'FULL_BODY'
WHERE target = 'SPECIFIC';

UPDATE protocol
SET visibility = 'PRIVATE'
WHERE visibility = 'USER';

UPDATE protocol
SET visibility = 'PUBLIC'
WHERE visibility = 'GENERAL';