export class Concept {
  id: number | undefined;
  nombre: string;
  laborable: boolean;
  hsMinimo: number;
  hsMaximo: number;

  constructor() {
    this.id = undefined;
    this.nombre = '';
    this.laborable = true;
    this.hsMinimo = 0;
    this.hsMaximo = 0;
  }
}
