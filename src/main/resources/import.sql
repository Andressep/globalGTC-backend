-- Insert a customer
INSERT INTO customer (name, rut, email, phone, address) VALUES ('Andres', '26931652-7', 'andresseo.95@gmail.com', '(9) 3707 8878', 'La Cisterna, Region Metropolitana');

-- Insert a salesperson and a category
INSERT INTO salesperson (name) VALUES ('Andres');
INSERT INTO category (name) VALUES ('Acero');

-- Insert a product
INSERT INTO products (description, product_code, weight_per_meter, price, length, category_id) VALUES ('Perfil C 2x2x0.85 40x38x8x0.85 6000MM', '2000-06000', 0.83, 1500.0, 6, 1);
INSERT INTO products (description, product_code, weight_per_meter, price, length, category_id) VALUES ('Perfil C 2x2x0.85 40x38x8x0.85 2400MM', '2000-02400', 0.83, 1000.0, 2.4, 1);
INSERT INTO products (description, product_code, weight_per_meter, price, length, category_id) VALUES ('Perfil C 2x2x0.85 40x38x8x0.85 3000MM', '2000-03000', 0.83, 1200.0, 3, 1);

-- Insert a quotation and an item
INSERT INTO quotations (customer_id, salesperson_id, created_at, quotation_code) VALUES (1, 1, NOW(), 'A7356');
INSERT INTO quotation_details (quotation_id, product_id, quantity) VALUES (1, 1, 2);
INSERT INTO quotation_details (quotation_id, product_id, quantity) VALUES (1, 2, 1);

--INSERT INTO quotations (customer_id, salesperson_id) VALUES (1, 1);
--INSERT INTO quotation_details (quotation_id, product_id, quantity) VALUES (2, 1, 5);
