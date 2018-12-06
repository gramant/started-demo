--liquibase user-contact sql

--changeset user-contact
CREATE TABLE user_contact (
  user_id VARCHAR(100) CONSTRAINT fk_user_contact_user_id REFERENCES users(id),
  email VARCHAR(100) NOT NULL
);