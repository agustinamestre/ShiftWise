INSERT INTO conceptos (id, hs_minimo, hs_maximo, laborable, nombre)
VALUES (1, 6, 8, true, 'Turno Normal');
INSERT INTO conceptos (id, hs_minimo, hs_maximo, laborable, nombre)
VALUES (2, 2, 6, true, 'Turno Extra');
INSERT INTO conceptos (id, hs_minimo, hs_maximo, laborable, nombre)
VALUES (3, null, null, false, 'Dia Libre');

-- Permisos
INSERT INTO permisos (id_permiso, codigo, nombre) VALUES (1, 'JORNADAS ESCRITURA', 'Crear jornadas');
INSERT INTO permisos (id_permiso, codigo, nombre) VALUES (2, 'JORNADAS LECTURA', 'Ver jornadas');
INSERT INTO permisos (id_permiso, codigo, nombre) VALUES (3, 'USUARIOS ESCRITURA', 'Crear usuarios');
INSERT INTO permisos (id_permiso, codigo, nombre) VALUES (4, 'USUARIOS LECTURA', 'Ver usuarios');
INSERT INTO permisos (id_permiso, codigo, nombre) VALUES (5, 'USUARIOS_ELIMINAR', 'Eliminar usuarios');

-- Perfiles
INSERT INTO perfiles (id_perfil, nombre) VALUES (1, 'ADMIN');
INSERT INTO perfiles (id_perfil, nombre) VALUES (2, 'EMPLEADO');

-- ADMIN: todos los permisos
INSERT INTO perfiles_permisos (id_perfil, id_permiso) VALUES (1, 1);
INSERT INTO perfiles_permisos (id_perfil, id_permiso) VALUES (1, 2);
INSERT INTO perfiles_permisos (id_perfil, id_permiso) VALUES (1, 3);
INSERT INTO perfiles_permisos (id_perfil, id_permiso) VALUES (1, 4);

-- EMPLEADO: solo los necesarios para autogestión
INSERT INTO perfiles_permisos (id_perfil, id_permiso) VALUES (2, 1); -- crear jornadas
INSERT INTO perfiles_permisos (id_perfil, id_permiso) VALUES (2, 2); -- ver jornadas
INSERT INTO perfiles_permisos (id_perfil, id_permiso) VALUES (2, 3); -- escritura
INSERT INTO perfiles_permisos (id_perfil, id_permiso) VALUES (2, 4); -- lectura
