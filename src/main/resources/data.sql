INSERT INTO categories (id, name) VALUES ('LH01', 'Dien thoai')
ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO categories (id, name) VALUES ('LH02', 'Laptop')
ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO categories (id, name) VALUES ('LH03', 'Phu kien')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO products (id, name, price, date, category_id)
VALUES ('SP01', 'iPhone 15', 25000000, '2026-01-10', 'LH01')
ON DUPLICATE KEY UPDATE
name = VALUES(name),
price = VALUES(price),
date = VALUES(date),
category_id = VALUES(category_id);

INSERT INTO products (id, name, price, date, category_id)
VALUES ('SP02', 'Dell XPS 13', 32000000, '2026-02-20', 'LH02')
ON DUPLICATE KEY UPDATE
name = VALUES(name),
price = VALUES(price),
date = VALUES(date),
category_id = VALUES(category_id);

INSERT INTO app_users (username, password, fullname, enabled, role)
VALUES ('admin', '123', 'Administrator', TRUE, 'ADMIN')
ON DUPLICATE KEY UPDATE
password = VALUES(password),
fullname = VALUES(fullname),
enabled = VALUES(enabled),
role = VALUES(role);

INSERT INTO app_users (username, password, fullname, enabled, role)
VALUES ('user1', '123', 'Nguyen Van A', TRUE, 'USER')
ON DUPLICATE KEY UPDATE
password = VALUES(password),
fullname = VALUES(fullname),
enabled = VALUES(enabled),
role = VALUES(role);
