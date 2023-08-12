##Perfiles
INSERT INTO cliente (name, rut, email) VALUES ('Andres', '26931652-7', 'Andresseo.95@gmail.com');
INSERT INTO vendedor(name) VALUES ('Andres');
##Productos
INSERT INTO productos (name, weight, price, size, create_at) VALUES ('Acero', 3, 8000, '2x2x0,85', NOW());
INSERT INTO productos (name, weight, price, size, create_at) VALUES ('Mortero', 25, 2600, '', NOW());
## Cotizaciones
INSERT INTO cotizaciones (cliente_id, vendedor_id, create_at) VALUES (1, 1, now());
INSERT INTO items(cotizacion_id, producto_id, cantidad) VALUES (1, 1, 2);
INSERT INTO items(cotizacion_id, producto_id, cantidad) VALUES (1, 2, 4);

