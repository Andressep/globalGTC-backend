##Perfiles
INSERT INTO cliente (name, rut, email) VALUES ('Andres', '26931652-7', 'Andresseo.95@gmail.com');
INSERT INTO vendedor(name) VALUES ('Andres');
##Productos
    ## Acero
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x2x0,85 40x38x8x0,85 6000MM', '2000-06000', 4.98, 0, '6000MM', NOW());
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x3x0,85 60x38x8x0,85 2400MM', '2000-02400', 2.3, 0, '2400MM', NOW());
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x3x0,85 60x38x8x0,85 3000MM', '2000-03000', 2.88, 0, '3000MM', NOW());
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x3x0,85 60x38x8x0,85 6000MM', '2000-06000', 5.76, 0, '6000MM', NOW());
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x4x0,85 90x38x12x0,85 2400MM', '2020-06000', 2.95, 0, '2400MM', NOW());
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x4x0,85 90x38x12x0,85 3000MM', '2020-06000', 3.69, 0, '3000MM', NOW());
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x4x0,85 90x38x12x0,85 6000MM', '2020-06000', 7.38, 0, '6000MM', NOW());
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x4x1,0 90x38x12x1,0 6000MM', '2025-06000', 8.64, 0, '6000MM', NOW());
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x5x0,85 100x40x12x0,85 6000MM', '2040-06000', 7.92, 0, '6000MM', NOW());
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x5x1,0 100x40x12x1,0 6000MM', '2041-06000', 9.3, 0, '6000MM', NOW());
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x6x0,85 150x40x12x0,85 6000MM', '2050-06000', 9.84, 0, '6000MM', NOW());
INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Perfil C 2x6x1,0 150x40x12x1,0 6000MM', '2055-06000', 11.64, 0, '6000MM', NOW());


    ## Morteros
##INSERT INTO productos (name, code_id, weight, price, size, create_at) VALUES ('Mortero', 'ndunudnw', 25, 2600, '', NOW());
## Cotizaciones
INSERT INTO cotizaciones (cliente_id, vendedor_id, create_at) VALUES (1, 1, now());
INSERT INTO items(cotizacion_id, producto_id, cantidad) VALUES (1, 1, 2);
##INSERT INTO items(cotizacion_id, producto_id, cantidad) VALUES (1, 2, 4);

