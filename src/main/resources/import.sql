-- Insert a customer
INSERT INTO customer (name, rut, email, phone, address) VALUES ('Andres', '26931652-7', 'andresseo.95@gmail.com', '(9) 3707 8878', 'La Cisterna, Region Metropolitana');

-- Insert a salesperson and a category
INSERT INTO salesperson (name) VALUES ('Andres');
INSERT INTO category (name) VALUES ('Acero');

-- Insert a product
INSERT INTO products (description, code, weight, price, length, category_id) VALUES (' Perfil C 2x2x0.85 40x38x8x0.85 6000MM', '2000-06000', 4.98, 1500.0, '6000MM', 1);
INSERT INTO products (description, code, weight, price, length, category_id) VALUES (' Perfil C 2x2x0.85 40x38x8x0.85 2400MM', '2000-02400', 2.04, 1000.0, '2400MM', 1);
INSERT INTO products (description, code, weight, price, length, category_id) VALUES (' Perfil C 2x2x0.85 40x38x8x0.85 3000MM', '2000-03000', 3.4, 1200.0, '3000MM', 1);

-- Insert a quotation and an item
INSERT INTO quotations (customer_id, salesperson_id, created_at) VALUES (1, 1, NOW());
INSERT INTO quotation_details (quotation_id, product_id, quantity) VALUES (1, 1, 2);
INSERT INTO quotation_details (quotation_id, product_id, quantity) VALUES (1, 2, 1);

--INSERT INTO quotations (customer_id, salesperson_id) VALUES (1, 1);
--INSERT INTO quotation_details (quotation_id, product_id, quantity) VALUES (2, 1, 5);
