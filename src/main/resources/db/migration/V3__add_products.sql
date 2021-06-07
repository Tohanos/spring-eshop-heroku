INSERT INTO products (id, price, title)
VALUES (1, 450.0, 'Salami'),
       (2, 103.0, 'Sausages'),
       (3, 385.0, 'Cervelat'),
       (4, 485.0, 'Pepperoni'),
       (5, 510.0, 'Saucisson'),
       (6, 505.0, 'Chorizo');

ALTER SEQUENCE product_seq RESTART WITH 6;