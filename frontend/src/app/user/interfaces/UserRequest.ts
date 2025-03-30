export default interface UserRequest {
  nroDocumento: string;
  nombre: string;
  apellido: string;
  email: string;
  fechaNacimiento: string;
  fechaIngreso: string;
  fotoBase64?: string;
  password: string;
}