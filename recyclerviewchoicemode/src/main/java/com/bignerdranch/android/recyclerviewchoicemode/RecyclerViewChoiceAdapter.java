package com.bignerdranch.android.recyclerviewchoicemode;

import java.util.List;

import android.support.v7.widget.RecyclerView;

import com.bignerdranch.android.recyclerviewchoicemode.MultiSelector.OnItemClickListener;

public abstract class RecyclerViewChoiceAdapter<T extends SwappingHolder>
		extends RecyclerView.Adapter<T> {

	protected MultiSelector selector;

	public RecyclerViewChoiceAdapter(MultiSelector selector) {
		this.selector = selector;
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		selector.setOnItemClickListener(listener);
	}

	/**
	 * Asigna el item en la posición especificada con el estado de selección
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
	 * Verifica si una posición está seleccionada
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
