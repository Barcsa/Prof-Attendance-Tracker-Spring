-- Default user
INSERT INTO user(id, version, email, name, password)
SELECT 1, 1, 'barcsa@emanuel.ro', 'Barcsa', '$2a$12$MILLEQ6aGaukVQafYns90.LdSsFZYIp7xH1qrkziBJL7brTYnrO26'
WHERE NOT EXISTS (SELECT * FROM user WHERE id = 1);

-- Roles for default user
INSERT INTO user_role(id, role, user_id)
SELECT 1, 'ADMIN', 1
WHERE NOT EXISTS (SELECT * FROM user_role WHERE id = 1);

INSERT INTO user_role(id, role, user_id)
SELECT 2, 'PROFESOR', 1
WHERE NOT EXISTS (SELECT * FROM user_role WHERE id = 2);