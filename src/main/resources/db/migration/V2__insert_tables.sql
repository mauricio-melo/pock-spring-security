INSERT INTO role(name)
VALUES ('ROLE_APPROVE'),
('ROLE_REVIEW'),
('ROLE_ADM'),
('ROLE_MASTER');

INSERT INTO profile(name)
VALUES ('PREPARER'),
('REVIEWER'),
('APPROVER'),
('ADMINISTRATOR'),
('MASTER');

INSERT INTO profile_role(idt_profile, idt_role)
VALUES ((SELECT c.idt_profile FROM profile p WHERE p.name = 'PREPARER'),
(SELECT r.idt_role FROM role r WHERE r.name = 'ROLE_MASTER'), 1, SYSDATE(), SYSDATE());

INSERT INTO customer(username, name, password, flg_enabled, dat_creation, dat_update)
VALUES ('financial', 'Financial', '$2a$10$1rgjjkRzbO/c6lIKjYv1UudoYBGQk3wmUMnscPK6W8yP3iUcyC3Gu', 1, SYSDATE(), SYSDATE());

INSERT INTO customer_role(idt_customer, idt_role, flg_enabled, dat_creation, dat_update)
VALUES ((SELECT c.idt_customer FROM customer c WHERE c.username = 'financial'),
(SELECT r.idt_role FROM role r WHERE r.name = 'ROLE_MASTER'), 1, SYSDATE(), SYSDATE());