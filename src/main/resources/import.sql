INSERT INTO roles(role_id,role) VALUES (1,'ADMIN')
INSERT INTO roles(role_id,role) VALUES (2,'REQUISITANTE')
INSERT INTO roles(role_id,role) VALUES (3,'OPERADOR')
INSERT INTO roles(role_id,role) VALUES (4,'RESPONSAVELSETOR')

INSERT INTO setores(setor_id,nome) VALUES (1,'INFORMÁTICA')
INSERT INTO users (id,ativo,email,nome,confirmacao_senha,senha,user_name,role_id,id_setor) VALUES (1,true, 'admin@admin.com','admin','$2a$12$vQbLWK4m23PvkJ1wOdtauep3smkIzD6N/Wx5hCi4XgSawRkLnO61i','$2a$12$vQbLWK4m23PvkJ1wOdtauep3smkIzD6N/Wx5hCi4XgSawRkLnO61i','admin',1,1)

INSERT INTO user_role(user_id,role_id) VALUES (1,1)