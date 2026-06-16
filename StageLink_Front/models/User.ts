export interface User {
  idUtilisateur: number;
  nom: string;
  prenom: string;
  email: string;
  password: string; // Optional: Don't display this unless required
  dateNaissance: string; // Represented as a string in ISO 8601 format
  sexe: boolean;
  telephone: string;
  role: string;
}
