INSERT INTO cliente (name, rut, email) VALUES ('Andres', '26931652-7', 'Andresseo.95@gmail.com');

INSERT INTO vendedor(name) VALUES ('Andres');
INSERT INTO categoria(name) VALUES ('Acero');

INSERT INTO productos(name, code_id, weight, price, size, create_at, categoria_id) VALUES ('Perfil C 2x2x0,85 40x38x8x0,85 6000MM', '2000-06000', 4.98, 0, '6000MM', NOW(), 1);

INSERT INTO cotizaciones (cliente_id, vendedor_id, create_at) VALUES (1, 1, now());
INSERT INTO items(cotizacion_id, producto_id, cantidad) VALUES (1, 1, 2);

