INSERT INTO users (username,
                   email,
                   display_name,
                   provider,
                   date_of_birth,
                   profile_picture_url,
                   created_at,
                   updated_at,
                   version,
                   is_deleted)
VALUES ('alice_smith',
        'alice.smith@example.com',
        'Alice Smith',
        'GOOGLE',
        '1990-03-15',
        'https://example.com/profiles/alice.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0,
        false),
       ('bob_jones',
        'bob.jones@example.com',
        'Bob Jones',
        'SPOTIFY',
        '1988-07-22',
        'https://example.com/profiles/bob.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0,
        false),
       ('carol_davis',
        'carol.davis@example.com',
        'Carol Davis',
        'EMAIL',
        '1995-11-30',
        'https://example.com/profiles/carol.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0,
        false),
       ('david_wilson',
        'david.wilson@example.com',
        'David Wilson',
        'GOOGLE',
        '1992-05-18',
        'https://example.com/profiles/david.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0,
        false),
       ('emma_brown',
        'emma.brown@example.com',
        'Emma Brown',
        'SPOTIFY',
        '1993-09-25',
        'https://example.com/profiles/emma.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        0,
        false);