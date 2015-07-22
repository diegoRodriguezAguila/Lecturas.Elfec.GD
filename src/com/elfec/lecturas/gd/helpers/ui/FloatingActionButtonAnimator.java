package com.elfec.lecturas.gd.helpers.ui;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.elfec.lecturas.gd.R;

/**
 * Helper que sirve para animar los botones flotantes con sus metodos de salida
 * y entrada
 * 
 * @author drodriguez
 *
 */
public class FloatingActionButtonAnimator {

	/**
	 * Muestra el FloatingActionButton con la animaci�n por defecto
	 * 
	 * @param fab
	 */
	public static void show(FloatingActionButton fab) {
		show(fab, null, null);
	}

	/**
	 * Muestra el FloatingActionButton con la animaci�n pasada en los
	 * par�metros. Este m�todo no llama a los callbacks de animaci�n de la
	 * animaci�n para ello utilize el m�todo
	 * {@link #hide(FloatingActionButton, Animation, AnimationListener)}
	 * 
	 * @param fab
	 * @param anim
	 */
	public static void show(final FloatingActionButton fab, Animation anim) {
		show(fab, anim, null);
	}

	/**
	 * Muestra el FloatingActionButton con la animaci�n pasada en los par�metros
	 * con el callback de animaciones proporcionado
	 * 
	 * @param fab
	 * @param anim
	 * @param listener
	 */
	public static void show(final FloatingActionButton fab, Animation anim,
			final AnimationListener listener) {
		if (anim == null) {
			anim = AnimationUtils
					.loadAnimation(fab.getContext(), R.anim.fab_in);
			anim.setDuration(250);
		}
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				fab.setVisibility(View.VISIBLE);
				if (listener != null)
					listener.onAnimationStart(animation);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				if (listener != null)
					listener.onAnimationRepeat(animation);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (listener != null)
					listener.onAnimationEnd(animation);
			}
		});
		fab.startAnimation(anim);
	}

	/**
	 * Esconde el FloatingActionButton con la animaci�n por defecto
	 * 
	 * @param fab
	 */
	public static void hide(FloatingActionButton fab) {
		hide(fab, null, null);
	}

	/**
	 * Esconde el FloatingActionButton con la animaci�n pasada en los
	 * par�metros. Este m�todo no llama a los callbacks de animaci�n de la
	 * animaci�n para ello utilize el m�todo
	 * {@link #hide(FloatingActionButton, Animation, AnimationListener)}
	 * 
	 * @param fab
	 * @param anim
	 */
	public static void hide(final FloatingActionButton fab, Animation anim) {
		hide(fab, anim, null);
	}

	/**
	 * Esconde el FloatingActionButton con la animaci�n pasada en los par�metros
	 * se llama a los callbacks de animaci�n proporcionados
	 * 
	 * @param fab
	 * @param anim
	 * @param listener
	 */
	public static void hide(final FloatingActionButton fab, Animation anim,
			final AnimationListener listener) {
		if (anim == null) {
			anim = AnimationUtils.loadAnimation(fab.getContext(),
					R.anim.fab_out);
			anim.setDuration(250);
		}
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				if (listener != null)
					listener.onAnimationStart(animation);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				if (listener != null)
					listener.onAnimationRepeat(animation);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				fab.setVisibility(View.GONE);
				if (listener != null)
					listener.onAnimationEnd(animation);
			}
		});
		fab.startAnimation(anim);
	}

	/**
	 * Esconde un FloatingActionButton y al finalizar la animaci�n inicia la
	 * animaci�n de mostrar el otro bot�n, con las animaciones por defecto
	 * 
	 * @param fabToHide
	 * @param fabToShow
	 */
	public static void hideAndShow(FloatingActionButton fabToHide,
			FloatingActionButton fabToShow) {
		hideAndShow(fabToHide, fabToShow, null, null);
	}

	/**
	 * Esconde un FloatingActionButton y al finalizar la animaci�n inicia la
	 * animaci�n de mostrar el otro bot�n, con las animaciones proporcionadas
	 * 
	 * @param fabToHide
	 * @param fabToShow
	 * @param hideAnim
	 * @param showAnim
	 */
	public static void hideAndShow(FloatingActionButton fabToHide,
			final FloatingActionButton fabToShow, Animation hideAnim,
			final Animation showAnim) {

		hide(fabToHide, hideAnim, new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				show(fabToShow, showAnim, null);
			}
		});
	}

}
