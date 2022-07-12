INSERT INTO role(name)
VALUES ('ROLE_APPROVE'),
('ROLE_REVIEW'),
('ROLE_CREATE_CUSTOMER'),
('ROLE_ADM');

INSERT INTO customer(username, name, password, flg_enabled, dat_creation, dat_update)
VALUES ('financial', 'Financial', '$2a$10$1rgjjkRzbO/c6lIKjYv1UudoYBGQk3wmUMnscPK6W8yP3iUcyC3Gu', 1, SYSDATE(), SYSDATE());

INSERT INTO customer_role(idt_customer, idt_role, flg_enabled, dat_creation, dat_update)
VALUES ((SELECT c.idt_customer FROM customer c WHERE c.username = 'totonero'),
(SELECT r.idt_role FROM role r WHERE r.name = 'ROLE_ADM'), 1, SYSDATE(), SYSDATE());