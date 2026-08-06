ALTER TABLE muscle_group
DROP COLUMN IF EXISTS mobility_area;

ALTER TABLE muscle_group
ADD COLUMN mobility_area VARCHAR(50);

-- shoulders
UPDATE muscle_group
SET mobility_area = 'SHOULDERS'
WHERE LOWER(name) IN (
  'deltoids',
  'trapezius',
  'chest'
);

-- hips
UPDATE muscle_group
SET mobility_area = 'HIPS'
WHERE LOWER(name) IN (
  'hips & adductors',
  'quadriceps'
);

UPDATE muscle_group
SET mobility_area = 'THORACIC_SPINE'
WHERE LOWER(name) IN (
  'upper-back'
);

-- LOWER BACK
UPDATE muscle_group
SET mobility_area = 'POSTERIOR'
WHERE LOWER(name) IN (
  'lower-back',
  'gluteal',
  'hamstring'
);

-- ANKLES
UPDATE muscle_group
SET mobility_area = 'ANKLES'
WHERE LOWER(name) IN (
  'calves',
  'tibialis',
  'ankles'
);

UPDATE muscle_group
SET mobility_area = 'ARMS'
WHERE LOWER(name) IN (
  'biceps',
  'forearm',
  'triceps'
);

UPDATE muscle_group
SET mobility_area = 'CORE'
WHERE LOWER(name) IN (
  'abs',
  'obliques'
);