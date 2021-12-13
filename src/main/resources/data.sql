-- Creamos 3 depositos, 2 en AR y 1 een BR

INSERT IGNORE INTO warehouse (id,iso, number) VALUES (1,'AR',1);
INSERT IGNORE INTO warehouse (id,iso, number) VALUES (2,'AR',2);
INSERT IGNORE INTO warehouse (id,iso, number) VALUES (3,'BR',1);

-- Asignamos 3 ubicaciones de limpieza en AR01
INSERT IGNORE INTO location (id,area,hall,location_row,side,warehouse_id)
VALUES (1,'LM',00,00,'IZ',1);
INSERT IGNORE INTO location (id,area,hall,location_row,side,warehouse_id)
VALUES (2,'LM',00,00,'DE',1);
INSERT IGNORE INTO location (id,area,hall,location_row,side,warehouse_id)
VALUES (3,'LM',00,01,'IZ',1);

-- Asignamos 1 ubicacion de almacen en AR02
INSERT IGNORE INTO location (id,area,hall,location_row,side,warehouse_id)
VALUES (4,'AL',00,00,'IZ',2);

-- Asignamos 1 ubicacion de almacen en BR01
INSERT IGNORE INTO location (id,area,hall,location_row,side,warehouse_id)
VALUES (5,'AL',00,00,'IZ',3);

-- Agregamos un producto al stock
INSERT IGNORE INTO stock (id,product,quantity,location_id)
VALUES (1,'MLA813727183',10,1);


