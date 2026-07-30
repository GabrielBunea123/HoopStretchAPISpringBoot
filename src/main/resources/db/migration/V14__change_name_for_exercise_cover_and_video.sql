ALTER TABLE exercise
    RENAME COLUMN cover_url TO cover_s3_key;

ALTER TABLE exercise
    RENAME COLUMN video_url TO video_s3_key;