package com.agustinamestre.ShiftWiseBackend.config;

import com.agustinamestre.ShiftWiseBackend.domain.NombrePerfil;
import com.agustinamestre.ShiftWiseBackend.domain.Perfil;
import com.agustinamestre.ShiftWiseBackend.domain.Permiso;
import com.agustinamestre.ShiftWiseBackend.domain.User;
import com.agustinamestre.ShiftWiseBackend.repositories.PerfilRepository;
import com.agustinamestre.ShiftWiseBackend.repositories.PermisoRepository;
import com.agustinamestre.ShiftWiseBackend.repositories.UserRepository;
import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class AdminUserInitializer {

    private final UserRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PermisoRepository permisoRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.nroDocumento}")
    private String adminNroDocumento;

    @Value("${admin.password}")
    private String adminPassword;

    @Bean
    public ApplicationRunner initAdminUser(){
        return args -> {

            usuarioRepository.findByNroDocumento(adminNroDocumento).ifPresentOrElse(
                    user -> log.info("Usuario administrador ya existe: {}", adminNroDocumento),
                    () -> {
                        Set<Permiso> permisos = new HashSet<>(permisoRepository.findAll());

                        Perfil perfilAdmin = perfilRepository.findByNombre(NombrePerfil.ADMIN)
                                .orElseGet(() -> {
                                    Perfil nuevoPerfil = new Perfil();
                                    nuevoPerfil.setNombre(NombrePerfil.ADMIN);
                                    return nuevoPerfil;
                                });

                        perfilAdmin.setPermisos(permisos);
                        perfilRepository.save(perfilAdmin);

                        User admin = new User();
                        admin.setId("admin-" + Ksuid.newKsuid().toString());
                        admin.setNombre("Administrador");
                        admin.setApellido("Administrador");
                        admin.setFechaIngreso(LocalDate.now());
                        admin.setNroDocumento(adminNroDocumento);
                        admin.setFechaNacimiento(LocalDate.of(1980, 1, 1));
                        admin.setEmail("agustinamestre@gmail.com");
                        admin.setPassword(passwordEncoder.encode(adminPassword));
                        admin.setPerfil(perfilAdmin);
                        usuarioRepository.save(admin);

                        log.info("Usuario administrador creado exitosamente");
                    }
            );
        };
    }

}
