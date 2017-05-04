INSERT INTO grupo (codigo, nome) VALUES (1, 'Administrador');
INSERT INTO grupo (codigo, nome) VALUES (2, 'Fornecedor');
INSERT INTO grupo (codigo, nome) VALUES (3, 'Potencial Comprador');

INSERT INTO usuario (codigo, ativo, email, senha) 
VALUES (1, 1, 'compraki@compraki.com', '$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG');

INSERT INTO permissao VALUES (1, 'ROLE_CADASTRAR_FORNECEDOR');
INSERT INTO permissao VALUES (2, 'ROLE_CADASTRAR_USUARIO');

INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 1);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 2);

INSERT INTO usuario_grupo (codigo_usuario, codigo_grupo) VALUES (
    (SELECT codigo FROM usuario WHERE email = 'compraki@compraki.com'), 1);
