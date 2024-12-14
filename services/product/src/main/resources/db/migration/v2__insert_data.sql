INSERT INTO category (id, description, name) VALUES
    (NEXTVAL('category_seq'), 'Computer keyboards of various types', 'Computer Keyboards'),
    (NEXTVAL('category_seq'), 'Monitors for desktops and laptops', 'Computer Monitors'),
    (NEXTVAL('category_seq'), 'Display screens and projectors', 'Display Screens'),
    (NEXTVAL('category_seq'), 'Pointing devices like mice', 'Computer Mice'),
    (NEXTVAL('category_seq'), 'Accessories for computing needs', 'Computer Accessories');


-- Assuming you already have a sequence named 'product_seq'

-- Insert products for the 'Computer Keyboards' category
INSERT INTO product (id, available_quantity, description, name, price, category_id) VALUES
    (NEXTVAL('product_seq'), 10, 'Mechanical keyboard with RGB lighting', 'Mechanical Keyboard', 120.50, 1),
    (NEXTVAL('product_seq'), 15, 'Wireless compact keyboard', 'Wireless Keyboard', 90.99, 1),
    (NEXTVAL('product_seq'), 20, 'Backlit gaming keyboard with customizable keys', 'Gaming Keyboard', 150.75, 1),
    (NEXTVAL('product_seq'), 25, 'Ergonomic keyboard with wrist rest', 'Ergonomic Keyboard', 110.00, 1);

-- Insert products for the 'Computer Monitors' category
INSERT INTO product (id, available_quantity, description, name, price, category_id) VALUES
    (NEXTVAL('product_seq'), 12, '24-inch Full HD monitor', 'Full HD Monitor', 200.00, 2),
    (NEXTVAL('product_seq'), 8, '27-inch 4K UHD monitor', '4K Monitor', 350.99, 2),
    (NEXTVAL('product_seq'), 5, 'Curved gaming monitor', 'Gaming Monitor', 400.75, 2),
    (NEXTVAL('product_seq'), 7, 'Portable monitor for laptops', 'Portable Monitor', 180.50, 2);

-- Insert products for the 'Computer Accessories' category
INSERT INTO product (id, available_quantity, description, name, price, category_id) VALUES
    (NEXTVAL('product_seq'), 30, 'USB-C docking station with HDMI and Ethernet', 'Docking Station', 60.00, 5),
    (NEXTVAL('product_seq'), 50, 'Laptop cooling pad with fans', 'Cooling Pad', 25.99, 5),
    (NEXTVAL('product_seq'), 40, 'External hard drive enclosure', 'HDD Enclosure', 35.50, 5),
    (NEXTVAL('product_seq'), 60, 'Universal power adapter', 'Power Adapter', 19.99, 5);
