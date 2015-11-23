package com.elfec.widgets.recyclerviewchoicemode;

import android.support.v7.widget.RecyclerView;

import java.util.List;


public abstract class RecyclerViewChoiceAdapter<T extends SwappingHolder>
		extends RecyclerView.Adapter<T> {

	protected MultiSelector selector;

	public RecyclerViewChoiceAdapter(MultiSelector selector) {
		this.selector = selector;
	}

	public void setOnItemClickListener(MultiSelector.OnItemClickListener listener) {
		selector.setOnItemClickListener(listener);
	}

	/**
	 * Asigna el item en la posici�n especificada con el estado de selecci�n
	 * provisto
	 * 
	 * @param pos
	 * @param selected
	 */
	public void setSelected(int pos, boolean selected) {
		selector.setSelected(pos, getItemId(pos), selected);
	}

	/**
	 * Remueve todos los items seleccionados
	 */
	public void clearSelections() {
		selector.clearSelections();
	}

	/**
	 * Verifica si una posici�n est� seleccionada
	 * 
	 * @param pos
	 */
	public boolean isSelected(int pos) {
		return selector.isSelected(pos, getItemId(pos));
	}

	/**
	 * Obtiene las posiciones seleccionadas
	 * 
	 * @return lista de posiciones seleccionadas
	 */
	public List<Integer> getSelectedPositions() {
		return selector.getSelectedPositions();
	}
}
