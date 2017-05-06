INSERT INTO grupo (codigo, nome) VALUES (1, 'Administrador');
INSERT INTO grupo (codigo, nome) VALUES (2, 'Fornecedor');
INSERT INTO grupo (codigo, nome) VALUES (3, 'Potencial Comprador');

INSERT INTO usuario (codigo, ativo, email, nome, senha) 
VALUES (1, 1, 'compraki@compraki.com', 'Administrador do Sistema', '$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG');

INSERT INTO permissao VALUES (1, 'ROLE_CADASTRAR_FORNECEDOR');
INSERT INTO permissao VALUES (2, 'ROLE_CADASTRAR_USUARIO');

INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 1);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 2);

INSERT INTO usuario_grupo (codigo_usuario, codigo_grupo) VALUES (
    (SELECT codigo FROM usuario WHERE email = 'compraki@compraki.com'), 1);
    
INSERT INTO pessoa (cpf_cnpj, data_nascimento, bairro, cep, cidade, estado, rua, nome, sexo, tipo_pessoa, codigo_usuario) 
VALUES ('98765432197', '1978-03-25', 'Setor Bueno','74000000', 'Goiânia', 'GO', 'Rua da pessoa', 'Fulano da Silva','MASCULINO', 'FISICA',
(SELECT codigo FROM usuario WHERE codigo = 1));

INSERT INTO pessoa_telefone (codigo_pessoa, numero_telefone) VALUES (
    (SELECT codigo FROM pessoa WHERE codigo = 1), '6299997777');
    
INSERT INTO pessoa_telefone (codigo_pessoa, numero_telefone) VALUES (
    (SELECT codigo FROM pessoa WHERE codigo = 1), '6237377777');


INSERT INTO acessorio (descricao) VALUES ('Ar condicionado');
INSERT INTO acessorio (descricao) VALUES ('Roda de liga leve');
INSERT INTO acessorio (descricao) VALUES ('Direção Hidraulica');
INSERT INTO acessorio (descricao) VALUES ('Roda aro 16');
INSERT INTO acessorio (descricao) VALUES ('Câmbio automático');

INSERT INTO carro (cor, data_criacao, descricao, ipva_pago) VALUES ('Azul', '2017-05-06', 'Carro sem defeito de lataria',1);

INSERT INTO carro_acessorio (codigo_carro, codigo_acessorio) VALUES (
    (SELECT codigo FROM carro WHERE codigo = 1), (SELECT codigo FROM acessorio WHERE codigo = 1));

INSERT INTO carro_acessorio (codigo_carro, codigo_acessorio) VALUES (
    (SELECT codigo FROM carro WHERE codigo = 1), (SELECT codigo FROM acessorio WHERE codigo = 3));
    
INSERT INTO fabricante (nome) VALUES ('Fiat');
INSERT INTO fabricante (nome) VALUES ('Wolksvagem');
INSERT INTO fabricante (nome) VALUES ('Ford');
INSERT INTO fabricante (nome) VALUES ('Chevrolet');   

INSERT INTO modelo_carro (categoria, descricao, codigo_fabricante) VALUES (
    'SEDAN', 'GOL',(SELECT codigo FROM fabricante WHERE codigo = 2));


