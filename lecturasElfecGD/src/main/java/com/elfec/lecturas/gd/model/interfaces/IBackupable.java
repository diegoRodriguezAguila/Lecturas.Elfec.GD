package com.elfec.lecturas.gd.model.interfaces;

public interface IBackupable {
	public String getBackupFilename();

	public String toInsertSQL();

	public String toDeleteSQL();
}
