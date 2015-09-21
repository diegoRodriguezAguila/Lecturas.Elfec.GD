package com.elfec.lecturas.gd.remote_data_access;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.elfec.lecturas.gd.model.ReadingMeter;
import com.elfec.lecturas.gd.remote_data_access.connection.OracleDatabaseConnector;

/**
 * Provee una capa de acceso remoto a los datos de medidor de las lecturas de
 * gran demanda de la tabla <b>MOVILES.LECTURASGD_MED</b>
 * 
 * @author drodriguez
 *
 */
public class ReadingMeterRDA {
	/**
	 * Obtiene la información de los medidores de las lecturas, correspondiente
	 * a los ids de lecturas proporcionados
	 * 
	 * @param username
	 * @param password
	 * @param readingRemoteIds
	 *            la lista de ids de las lecturas de gran demanda en formato
	 *            (12334,1234)
	 * @return {@link List}<{@link ReadingMeter}> Lista de medidores de lecturas
	 * @throws ConnectException
	 * @throws SQLException
	 */
	public List<ReadingMeter> requestReadingMeters(String username,
			String password, String readingRemoteIds) throws ConnectException,
			SQLException {
		{
			List<ReadingMeter> readingMeters = new ArrayList<ReadingMeter>();
			ResultSet rs = OracleDatabaseConnector
					.instance(username, password)
					.executeSelect(
							String.format(
									"SELECT * FROM MOVILES.LECTURASGD_MED WHERE (IDLECTURAGD IN %s )",
									readingRemoteIds));
			while (rs.next()) {
				readingMeters.add(new ReadingMeter(rs.getLong("IDLECTURAGD"),
						rs.getInt("IDMEDIDOR"), rs.getString("NROSERIE"), rs
								.getShort("IDTIPO_MEDIDOR"), rs
								.getShort("DIGITOS"),
						rs.getInt("IDSUMINISTRO"), new DateTime(rs
								.getTimestamp("FECHA_ANTERIOR")), new DateTime(
								rs.getTimestamp("FECHA_ACTUAL")), rs
								.getBigDecimal("TAG_ACTIVO_DISTRIBUIR"), rs
								.getBigDecimal("TAG_ACTIVO_PICO"), rs
								.getBigDecimal("TAG_ACTIVO_RESTO"), rs
								.getBigDecimal("TAG_ACTIVO_VALLE"), rs
								.getBigDecimal("TAG_REACTIVO_DISTRIBUIR"), rs
								.getBigDecimal("TAG_REACTIVO_PICO"), rs
								.getBigDecimal("TAG_REACTIVO_RESTO"), rs
								.getBigDecimal("TAG_REACTIVO_VALLE"), rs
								.getBigDecimal("TAG_POT_PUNTA"), rs
								.getBigDecimal("TAG_POT_FPUNTA"), rs
								.getBigDecimal("LEC_ANT_ACTIVO_DISTRIBUIR"), rs
								.getBigDecimal("LEC_ANT_ACTIVO_PICO"), rs
								.getBigDecimal("LEC_ANT_ACTIVO_RESTO"), rs
								.getBigDecimal("LEC_ANT_ACTIVO_VALLE"), rs
								.getBigDecimal("LEC_ANT_REACTIVO_DISTRIBUIR"),
						rs.getBigDecimal("LEC_ANT_REACTIVO_PICO"), rs
								.getBigDecimal("LEC_ANT_REACTIVO_RESTO"), rs
								.getBigDecimal("LEC_ANT_REACTIVO_VALLE"), rs
								.getInt("CONSUMO_PROM_ACT"), rs
								.getInt("CONSUMO_PROM_REACT"), rs
								.getBigDecimal("POT_PUNTA_ARRASTRE"), rs
								.getBigDecimal("POT_FPUNTA_ARRASTRE")));
			}
			return readingMeters;
		}
	}
}
