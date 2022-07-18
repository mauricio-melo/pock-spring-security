INSERT INTO company(name, document)
VALUES ('TECHNOLOGY LTDA', '25345646875');

INSERT INTO role(name)
VALUES ('ROLE_APPROVE'),
('ROLE_REVIEW');

INSERT INTO profile(name)
VALUES ('ADMIN');

INSERT INTO profile_role(idt_profile, idt_role)
VALUES ((SELECT p.idt_profile FROM profile p WHERE p.name = 'ADMIN'),
(SELECT r.idt_role FROM role r WHERE r.name = 'ROLE_APPROVE'));

INSERT INTO profile_role(idt_profile, idt_role)
VALUES ((SELECT p.idt_profile FROM profile p WHERE p.name = 'ADMIN'),
(SELECT r.idt_role FROM role r WHERE r.name = 'ROLE_REVIEW'));

INSERT INTO customer(username, name, password, idt_profile, flg_enabled, dat_creation, dat_update)
VALUES ('financial', 'Financial', '$2a$10$KyWbpMYp6hQXdYUdRAsqwuwMIwhrbuaaInrwBofUNqndQi9.z2Uvu',
(SELECT p.idt_profile FROM profile p WHERE p.name = 'ADMIN'), 1, SYSDATE(), SYSDATE());