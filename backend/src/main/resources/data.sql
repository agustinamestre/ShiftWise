-- Conceptos
INSERT INTO conceptos (id, hs_minimo, hs_maximo, laborable, nombre)
SELECT 1, 6, 8, true, 'Turno Normal'
WHERE NOT EXISTS (
    SELECT 1 FROM conceptos WHERE id = 1
);

INSERT INTO conceptos (id, hs_minimo, hs_maximo, laborable, nombre)
SELECT 2, 2, 6, true, 'Turno Extra'
WHERE NOT EXISTS (
    SELECT 1 FROM conceptos WHERE id = 2
);

INSERT INTO conceptos (id, hs_minimo, hs_maximo, laborable, nombre)
SELECT 3, null, null, false, 'Dia Libre'
WHERE NOT EXISTS (
    SELECT 1 FROM conceptos WHERE id = 3
);

-- Permisos
INSERT INTO permisos (id_permiso, codigo, nombre)
SELECT 1, 'JORNADAS ESCRITURA', 'Crear jornadas'
WHERE NOT EXISTS (
    SELECT 1 FROM permisos WHERE id_permiso = 1
);

INSERT INTO permisos (id_permiso, codigo, nombre)
SELECT 2, 'JORNADAS LECTURA', 'Ver jornadas'
WHERE NOT EXISTS (
    SELECT 1 FROM permisos WHERE id_permiso = 2
);

INSERT INTO permisos (id_permiso, codigo, nombre)
SELECT 3, 'USUARIOS ESCRITURA', 'Crear usuarios'
WHERE NOT EXISTS (
    SELECT 1 FROM permisos WHERE id_permiso = 3
);

INSERT INTO permisos (id_permiso, codigo, nombre)
SELECT 4, 'USUARIOS LECTURA', 'Ver usuarios'
WHERE NOT EXISTS (
    SELECT 1 FROM permisos WHERE id_permiso = 4
);

INSERT INTO permisos (id_permiso, codigo, nombre)
SELECT 5, 'USUARIOS_ELIMINAR', 'Eliminar usuarios'
WHERE NOT EXISTS (
    SELECT 1 FROM permisos WHERE id_permiso = 5
);

INSERT INTO permisos (id_permiso, codigo, nombre)
SELECT 6, 'U02', 'USUARIO LECTURA'
WHERE NOT EXISTS (
    SELECT 1 FROM permisos WHERE codigo = 'U02'
);

-- Perfiles
INSERT INTO perfiles (id_perfil, nombre)
SELECT 1, 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM perfiles WHERE id_perfil = 1
);

INSERT INTO perfiles (id_perfil, nombre)
SELECT 2, 'EMPLEADO'
WHERE NOT EXISTS (
    SELECT 1 FROM perfiles WHERE id_perfil = 2
);

-- Perfiles-Permisos (ADMIN: todos los permisos)
INSERT INTO perfiles_permisos (id_perfil, id_permiso)
SELECT 1, 1
WHERE NOT EXISTS (
    SELECT 1 FROM perfiles_permisos WHERE id_perfil = 1 AND id_permiso = 1
);

INSERT INTO perfiles_permisos (id_perfil, id_permiso)
SELECT 1, 2
WHERE NOT EXISTS (
    SELECT 1 FROM perfiles_permisos WHERE id_perfil = 1 AND id_permiso = 2
);

INSERT INTO perfiles_permisos (id_perfil, id_permiso)
SELECT 1, 3
WHERE NOT EXISTS (
    SELECT 1 FROM perfiles_permisos WHERE id_perfil = 1 AND id_permiso = 3
);

INSERT INTO perfiles_permisos (id_perfil, id_permiso)
SELECT 1, 4
WHERE NOT EXISTS (
    SELECT 1 FROM perfiles_permisos WHERE id_perfil = 1 AND id_permiso = 4
);

-- Perfiles-Permisos (EMPLEADO: solo los necesarios para autogestión)
INSERT INTO perfiles_permisos (id_perfil, id_permiso)
SELECT 2, 1
WHERE NOT EXISTS (
    SELECT 1 FROM perfiles_permisos WHERE id_perfil = 2 AND id_permiso = 1
);

INSERT INTO perfiles_permisos (id_perfil, id_permiso)
SELECT 2, 2
WHERE NOT EXISTS (
    SELECT 1 FROM perfiles_permisos WHERE id_perfil = 2 AND id_permiso = 2
);

INSERT INTO perfiles_permisos (id_perfil, id_permiso)
SELECT 2, 3
WHERE NOT EXISTS (
    SELECT 1 FROM perfiles_permisos WHERE id_perfil = 2 AND id_permiso = 3
);

INSERT INTO perfiles_permisos (id_perfil, id_permiso)
SELECT 2, 4
WHERE NOT EXISTS (
    SELECT 1 FROM perfiles_permisos WHERE id_perfil = 2 AND id_permiso = 4
);
