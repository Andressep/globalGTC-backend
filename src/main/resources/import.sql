-- Insert a customer
INSERT INTO customer (name, rut, email) VALUES ('Andres', '26931652-7', 'andresseo.95@gmail.com');

-- Insert a salesperson and a category
INSERT INTO salesperson (name) VALUES ('Andres');
INSERT INTO category (name) VALUES ('Acero');

-- Insert a product
INSERT INTO products (name, code_id, weight, price, size, category_id) VALUES ('Perfil C 2x2x0.85 40x38x8x0.85 6000MM', '2000-06000', 4.98, 1500, '6000MM', 1);

-- Insert a quotation and an item
INSERT INTO quotations (customer_id, salesperson_id) VALUES (1, 1);
INSERT INTO items (quotation_id, product_id, quantity) VALUES (1, 1, 2);

INSERT INTO quotations (customer_id, salesperson_id) VALUES (1, 1);
INSERT INTO items (quotation_id, product_id, quantity) VALUES (2, 1, 5);
