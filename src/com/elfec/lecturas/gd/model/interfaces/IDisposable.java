package com.elfec.lecturas.gd.model.interfaces;

/**
 * Interfaz para objetos que deben liberar recursos al finalizar su uso
 * 
 * @author drodriguez
 *
 */
public interface IDisposable {
	/**
	 * Este método es llamado cuando el objeto que lo implementa ya no es
	 * necesario. Debería liberar cualquier recurso allocado en memoria por el
	 * objeto. Solo debe ser llamado despues de que todo lo que depende de este
	 * objeto ha sido liberado (disposed).
	 */
	public void dispose();
}
