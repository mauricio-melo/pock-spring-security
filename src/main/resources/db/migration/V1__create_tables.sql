CREATE TABLE contract(
  idt_contract  			    BIGINT AUTO_INCREMENT NOT NULL,
  idt_company                   BIGINT NOT NULL,
  dat_start_contract 	        DATETIME NOT NULL,
  dat_end_contract 	            DATETIME NOT NULL,
  dat_creation 	                DATETIME NOT NULL,
  dat_update 	                DATETIME NULL,
  is_current                    TINYINT(1) NOT NULL,
  PRIMARY KEY contract_pk (idt_contract),
  UNIQUE (idt_company, is_current)
);

CREATE TABLE company(
  idt_company                   BIGINT AUTO_INCREMENT NOT NULL,
  name       					VARCHAR(255) NOT NULL,
  document       				VARCHAR(255) NOT NULL,
  dat_creation 	                DATETIME NOT NULL,
  dat_update 	                DATETIME NULL,
  PRIMARY KEY contract_pk (idt_company),
  UNIQUE (document)
);

CREATE TABLE profile(
  idt_profile  			        BIGINT AUTO_INCREMENT NOT NULL,
  name       					VARCHAR(255) NOT NULL,
  PRIMARY KEY profile_pk (idt_profile),
  UNIQUE (name)
);

CREATE TABLE customer(
  idt_customer			        BIGINT AUTO_INCREMENT NOT NULL,
  idt_profile	                BIGINT NULL,
  idt_company                   BIGINT NULL,
  name  	                    VARCHAR(255) NOT NULL,
  username 					    VARCHAR(255) NOT NULL,
  password  	                VARCHAR(255) NOT NULL,
  flg_enabled                   TINYINT(1) NOT NULL,
  created_by 	                VARCHAR(255) NULL,
  modified_by 	                VARCHAR(255) NULL,
  dat_creation 	                DATETIME NOT NULL,
  dat_update 	                DATETIME NULL,
  PRIMARY KEY customer_pk (idt_customer),
  CONSTRAINT fk_cu_profile      FOREIGN KEY (idt_profile) REFERENCES profile (idt_profile),
  CONSTRAINT fk_cu_company      FOREIGN KEY (idt_company) REFERENCES company (idt_company),
  UNIQUE (username)
);

CREATE TABLE role(
  idt_role  			        BIGINT AUTO_INCREMENT NOT NULL,
  name       					VARCHAR(255) NOT NULL,
  PRIMARY KEY role_pk (idt_role),
  UNIQUE (name)
);

CREATE TABLE profile_role(
  idt_profile_role			    BIGINT AUTO_INCREMENT NOT NULL,
  idt_profile	                BIGINT NOT NULL,
  idt_role  					BIGINT NOT NULL,
  PRIMARY KEY profile_role_pk (idt_profile_role),
  CONSTRAINT fk_pr_profile      FOREIGN KEY (idt_profile) REFERENCES profile (idt_profile),
  CONSTRAINT fk_pr_role         FOREIGN KEY (idt_role) REFERENCES role (idt_role),
  UNIQUE (idt_profile, idt_role)
);