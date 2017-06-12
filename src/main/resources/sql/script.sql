INSERT INTO grupo (codigo, nome) VALUES (1, 'Administrador');
INSERT INTO grupo (codigo, nome) VALUES (2, 'Fornecedor');
INSERT INTO grupo (codigo, nome) VALUES (3, 'Potencial Comprador');
INSERT INTO grupo (codigo, nome) VALUES (4, 'Novo Usuario');

INSERT INTO usuario (codigo, ativo, email, senha) 
VALUES (1, 1, 'compraki@compraki.com', '$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG');

INSERT INTO permissao VALUES (1, 'ROLE_CADASTRAR_VEICULO');
INSERT INTO permissao VALUES (2, 'ROLE_CADASTRAR_USUARIO');
INSERT INTO permissao VALUES (3, 'ROLE_ACESSAR_SISTEMA');

INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 1);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 2);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (2, 1);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (4, 3);

INSERT INTO usuario_grupo (codigo_usuario, codigo_grupo) VALUES (
    (SELECT codigo FROM usuario WHERE email = 'compraki@compraki.com'), 1);
    
INSERT INTO acessorio (descricao, posicao, tipoVeiculo) VALUES ('Ar condicionado', 1, 'CARRO');
INSERT INTO acessorio (descricao, posicao, tipoVeiculo) VALUES ('Roda de liga leve', 2, 'CARRO');
INSERT INTO acessorio (descricao, posicao, tipoVeiculo) VALUES ('Direção Hidraulica', 3, 'CARRO');
INSERT INTO acessorio (descricao, posicao, tipoVeiculo) VALUES ('Roda aro 16', 4, 'CARRO');
INSERT INTO acessorio (descricao, posicao, tipoVeiculo) VALUES ('Câmbio automático', 5, 'CARRO');
INSERT INTO acessorio (descricao, posicao, tipoVeiculo) VALUES ('Para choque cor do Carro', 6, 'CARRO');
INSERT INTO acessorio (descricao, posicao, tipoVeiculo) VALUES ('Roda palito', 1, 'MOTO');

INSERT INTO fabricante (nome, tipoVeiculo) VALUES ('Fiat', 'CARRO');
INSERT INTO fabricante (nome, tipoVeiculo) VALUES ('Volkswagen', 'CARRO');
INSERT INTO fabricante (nome, tipoVeiculo) VALUES ('Ford', 'CARRO');
INSERT INTO fabricante (nome, tipoVeiculo) VALUES ('Chevrolet', 'CARRO');
INSERT INTO fabricante (nome, tipoVeiculo) VALUES ('Honda', 'MOTO');


INSERT INTO modelo_veiculo (categoria, descricao, codigo_fabricante) VALUES (
    'HATCH', 'Gol',(SELECT codigo FROM fabricante WHERE nome = 'Volkswagen'));
INSERT INTO modelo_veiculo (categoria, descricao, codigo_fabricante) VALUES (
    'HATCH', 'Fox',(SELECT codigo FROM fabricante WHERE nome = 'Volkswagen'));
INSERT INTO modelo_veiculo (categoria, descricao, codigo_fabricante) VALUES (
    'SEDAN', 'Fiesta',(SELECT codigo FROM fabricante WHERE nome = 'Ford'));
INSERT INTO modelo_veiculo (categoria, descricao, codigo_fabricante) VALUES (
    'SEDAN', 'Focus',(SELECT codigo FROM fabricante WHERE nome = 'Ford'));        
INSERT INTO modelo_veiculo (categoria, descricao, codigo_fabricante) VALUES (
'HATCH', 'Uno',(SELECT codigo FROM fabricante WHERE nome = 'Fiat'));
INSERT INTO modelo_veiculo (categoria, descricao, codigo_fabricante) VALUES (
'SEDAN', 'Prisma',(SELECT codigo FROM fabricante WHERE nome = 'Chevrolet'));
INSERT INTO modelo_veiculo (categoria, descricao, codigo_fabricante) VALUES (
'HATCH', 'Palio',(SELECT codigo FROM fabricante WHERE nome = 'Fiat'));
INSERT INTO modelo_veiculo (categoria, descricao, codigo_fabricante) VALUES (
'PICKUP', 'S-10',(SELECT codigo FROM fabricante WHERE nome = 'Chevrolet'));
INSERT INTO modelo_veiculo (categoria, descricao, codigo_fabricante) VALUES (
'Esportiva', 'Cg Titan 150',(SELECT codigo FROM fabricante WHERE nome = 'Honda'));
    

INSERT INTO `cores` (`descricao`) VALUES
('Azul'), ('Amarelo'), ('Branco'),('Bege'),('Cinza'), ('Rosa'), ('Prata'), ('Preto'), ('Verde'), ('Vermelho');  

INSERT INTO carro (contentType, data_criacao, data_modificacao, descricao, foto,  ipva_pago, tipoVeiculo, codigo_cor, codigo_modelo, codigo_usuario) VALUES (null, '2017-05-06', null, 'Carro sem defeito de lataria', null, 1, 'CARRO',
1, (SELECT codigo FROM modelo_veiculo WHERE descricao = 'Gol'), 1);

INSERT INTO carro (contentType, data_criacao, data_modificacao, descricao, foto,  ipva_pago, tipOVeiculo, codigo_cor, codigo_modelo, codigo_usuario) VALUES (null, '2017-05-06', null, 'Carro sem defeito de lataria', null, 1, 'CARRO',
2, (SELECT codigo FROM modelo_veiculo WHERE descricao = 'Fox'), 1);

INSERT INTO carro (contentType, data_criacao, data_modificacao, descricao, foto,  ipva_pago, tipoVeiculo, codigo_cor, codigo_modelo, codigo_usuario) VALUES (null, '2017-05-06', null, 'Carro sem defeito de lataria', null, 1, 'CARRO',
3, (SELECT codigo FROM modelo_veiculo WHERE descricao = 'Palio'), 1);

INSERT INTO carro (contentType, data_criacao, data_modificacao, descricao, foto,  ipva_pago, tipoVeiculo, codigo_cor, codigo_modelo, codigo_usuario) VALUES (null, '2017-05-06', null, 'Carro sem defeito de lataria', null, 1, 'CARRO',
4, (SELECT codigo FROM modelo_veiculo WHERE descricao = 'Fiesta'), 1);

INSERT INTO carro (contentType, data_criacao, data_modificacao, descricao, foto,  ipva_pago, tipoVeiculo, codigo_cor, codigo_modelo, codigo_usuario) VALUES (null, '2017-05-06', null, 'Carro sem defeito de lataria', null, 1, 'CARRO',
5, (SELECT codigo FROM modelo_veiculo WHERE descricao = 'Uno'), 1);

INSERT INTO carro (contentType, data_criacao, data_modificacao, descricao, foto,  ipva_pago, tipoVeiculo, codigo_cor, codigo_modelo, codigo_usuario) VALUES (null, '2017-05-06', null, 'Carro sem defeito de lataria', null, 1, 'CARRO',
6, (SELECT codigo FROM modelo_veiculo WHERE descricao = 'Prisma'), 1);

