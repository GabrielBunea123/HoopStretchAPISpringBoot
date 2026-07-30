CREATE TABLE roles (
    id          BIGSERIAL    PRIMARY KEY,
    name        TEXT         NOT NULL UNIQUE,
    description TEXT,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    version     BIGINT       NOT NULL DEFAULT 0,
    is_deleted  BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE user_roles (
    user_id    BIGINT    NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id    BIGINT    NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    granted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    granted_by BIGINT    REFERENCES users(id) ON DELETE SET NULL,
    expires_at TIMESTAMP,

    PRIMARY KEY (user_id, role_id)
);

INSERT INTO roles (name, description) VALUES
    ('SUPER_ADMIN', 'Full system access'),
    ('ADMIN',       'Administrative access, excluding destructive system operations'),
    ('MODERATOR',   'Content moderation access'),
    ('PREMIUM',     'Access to premium features'),
    ('USER',        'Baseline access for all authenticated users');

CREATE INDEX idx_user_roles_user_id    ON user_roles(user_id);
CREATE INDEX idx_user_roles_expires_at ON user_roles(expires_at) WHERE expires_at IS NOT NULL;

--
--
-- CREATE TABLE permissions (
--                              id          BIGSERIAL    PRIMARY KEY,
--                              name        TEXT         NOT NULL UNIQUE,
--                              description TEXT,
--                              created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
--                              updated_at  TIMESTAMP,
--                              version     BIGINT       NOT NULL DEFAULT 0,
--                              is_deleted  BOOLEAN      NOT NULL DEFAULT FALSE
-- );
--
-- CREATE TABLE role_permissions (
--                                   role_id       BIGINT    NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
--                                   permission_id BIGINT    NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
--                                   granted_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--                                   granted_by    BIGINT    REFERENCES users(id) ON DELETE SET NULL,
--
--                                   PRIMARY KEY (role_id, permission_id)
-- );
--
-- -- Roles
-- -- Permissions
-- INSERT INTO permissions (name, description) VALUES
--     ('users:read',                'View user profiles'),
--     ('users:write',               'Edit user profiles'),
--     ('users:ban',                 'Ban users'),
--     ('users:delete',              'Delete user accounts'),
--     ('equipment_item:read',       'View equipment items'),
--     ('equipment_item:write',      'Edit equipment items'),
--     ('equipment_item:delete',     'Delete equipment items'),
--     ('exercise:read',             'View exercises'),
--     ('exercise:write',            'Edit exercises'),
--     ('exercise:delete',           'Delete exercises'),
--     ('mobility_assessment:read',  'View mobility assessments'),
--     ('mobility_assessment:write', 'Edit mobility assessments'),
--     ('mobility_assessment:delete','Delete mobility assessments'),
--     ('muscle_group:read',         'View muscle groups'),
--     ('muscle_group:write',        'Edit muscle groups'),
--     ('muscle_group:delete',       'Delete muscle groups'),
--     ('protocol:read',             'View protocols'),
--     ('protocol:write',            'Edit protocols'),
--     ('protocol:delete',           'Delete protocols'),
--     ('mobilityTest:read',         'View mobility test'),
--     ('mobilityTest:write',        'Edit mobility test'),
--     ('mobilityTest:delete',       'Delete mobility test');
--
-- -- TODO: finish here
-- -- USERS
-- -- super_admin gets everything
-- INSERT INTO role_permissions (role_id, permission_id)
-- SELECT
--     (SELECT id FROM roles WHERE name = 'super_admin'),
--     id
-- FROM permissions;
--
-- -- admin gets everything except users:delete
-- INSERT INTO role_permissions (role_id, permission_id)
-- SELECT
--     (SELECT id FROM roles WHERE name = 'admin'),
--     id
-- FROM permissions
-- WHERE name NOT IN (
--         'users:delete',
--         'equipment_item:delete',
--         'exercise:delete',
--         'mobility_assessment:delete',
--         'muscle_group:delete',
--         'protocol:delete',
--         'mobilityTest:delete'
--     );
--
-- -- moderator
-- INSERT INTO role_permissions (role_id, permission_id)
-- SELECT
--     (SELECT id FROM roles WHERE name = 'moderator'),
--     id
-- FROM permissions
-- WHERE name IN (
--         'users:read',
--         'users:ban',
--         'equipment_item:read'
--     );
--
-- -- user (baseline)
-- INSERT INTO role_permissions (role_id, permission_id)
-- SELECT
--     (SELECT id FROM roles WHERE name = 'user'),
--     id
-- FROM permissions
-- WHERE name IN ('users:read');