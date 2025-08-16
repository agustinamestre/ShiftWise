export default interface User {
  nroDocumento: string;
  nombre: string;
  apellido: string;
  email: string;
  fechaNacimiento: string;
  fechaIngreso?: string;
  fechaCreacion?: string;
  fotoBase64?: string;
  password?: string;
}
