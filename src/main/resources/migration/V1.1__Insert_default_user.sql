INSERT INTO app.users (id, username, password, first_name, last_name, display_name, email, mobile_no, activation_serial_number, profile_image_uri, deactivated_date, user_deactivate_reason_id, created_by, created_on) VALUES (3, 'jmn@jmn.com', '$2a$10$hCOTiYULM/2n3T.xOlppW.7dWOIrT4D1HTBhYKs0hT4sZAybECZu6', 'Jeannot', 'Munganga', 'Jeannot Munganga', 'jmn@jmn.com', '0810759538', null, null, null, null, null, '2023-02-15 06:42:00.171981 +00:00');

INSERT INTO app.user_role (id, user_id, role_id, created_by, created_on) VALUES (1, 3, 'SYSTEM_ADMIN', null, '2023-02-15 06:44:19.317287 +00:00');
