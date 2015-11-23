package com.elfec.lecturas.gd.model.exceptions;


public class ReadingNotFoundException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 6392400195140092569L;

	private String accountNumber;
	private String meter;
	private int nus;

	public ReadingNotFoundException(String accountNumber, String meter, int nus) {
		super();
		this.accountNumber = accountNumber;
		this.meter = meter;
		this.nus = nus;
	}

	@Override
	public String getMessage() {
		StringBuilder str = new StringBuilder(
				"No se encontró ninguna lectura con");
		if (accountNumber != null && !accountNumber.isEmpty())
			str.append(" cuenta: <b>").append(accountNumber).append("</b>,");
		if (meter != null && !meter.isEmpty())
			str.append(" número de serie de medidor: <b>").append(meter)
					.append("</b>,");
		if (nus != -1)
			str.append(" número de suministro: <b>").append(nus)
					.append("</b>,");
		str = new StringBuilder(str.substring(0, str.length() - 1));
		int lastIndex = str.lastIndexOf(",");
		if (lastIndex != -1)
			str.replace(lastIndex, lastIndex + 1, " y");
		return str.toString();
	}
}
