INSERT INTO roles (name)
VALUES ('ADMIN')
ON CONFLICT (name) DO NOTHING;

INSERT INTO _user (
    first_name,
    last_name,
    date_of_birth,
    email,
    password,
    account_locked,
    enabled
)
VALUES (
    'Admin',
    'System',
    '2000-01-01',
    'admin@admin.com',
    '$2a$10$wR2fHJPqJ3VvU70z2eP7xOkR4wNG0Ax7Anxr1j7rwhhtjfiPgk41C',
    FALSE,
    TRUE
)
ON CONFLICT (email) DO NOTHING;

INSERT INTO _user_roles (user_id, roles_id)
SELECT u.id, r.id
FROM _user u, roles r
WHERE u.email = 'admin@admin.com'
AND r.name = 'ADMIN'
ON CONFLICT DO NOTHING;
