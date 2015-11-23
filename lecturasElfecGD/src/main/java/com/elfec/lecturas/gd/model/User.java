package com.elfec.lecturas.gd.model;

import java.io.UnsupportedEncodingException;

import org.joda.time.DateTime;
import org.joda.time.Days;

import android.util.Base64;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.elfec.lecturas.gd.model.enums.UserStatus;
import com.elfec.lecturas.gd.model.security.AES;

/**
 * Se guardan los usuarios localmente
 * 
 * @author drodriguez
 *
 */
@Table(name = "Users")
public class User extends Model {
	@Column(name = "Username", notNull = true)
	private String username;

	@Column(name = "EncryptedPassword", notNull = true)
	private String encryptedPassword;

	@Column(name = "SyncDate")
	private DateTime syncDate;

	@Column(name = "Profile", notNull = true)
	public String profile;

	@Column(name = "RangeOfDays", notNull = true)
	public int rangeOfDays;

	@Column(name = "Status", notNull = true)
	private short status;

	public User() {
		super();
	}

	public User(String username, String profile,
			int rangeOfDays, UserStatus status) {
		super();
		this.username = username;
		this.profile = profile;
		this.rangeOfDays = rangeOfDays;
		this.status = status.toShort();
	}

	/**
	 * Accede a la base de datos y obtiene el usuario que corresponde al nombre
	 * de usuario provisto
	 * 
	 * @param username
	 *            el nombre de usuario para buscar
	 * @return El usuario con el nombre de usuario correspondiente
	 * **/
	public static User findByUserName(String username) {
		return new Select().from(User.class).where("Username=?", username)
				.executeSingle();
	}

	/**
	 * Sincroniza a un usuario, encripta su password, lo guarda localmente y le
	 * asigna una fecha de sincronización
	 * 
	 * @param password
	 *            (el password sin encriptar)
	 * @return el usuario sincronizado
	 */
	public User synchronizeUser(String password) {
		syncDate = DateTime.now();
		encryptedPassword = new AES().encrypt(generateUserKey(), password);
		save();
		return this;
	}

	/**
	 * Verifica si es que el password proporcionado coincide con el del usuario
	 * 
	 * @param testPassword
	 * @return true si es que los passwords encriptados coinciden
	 */
	public boolean passwordMatch(String testPassword) {
		return encryptedPassword.equals(new AES().encrypt(generateUserKey(),
				testPassword));
	}
	
	/**
	 * Verifica que la fecha actual del sistema este dentro el rango
	 * de la fecha de sincronización y el rango de días asignado al usuario
	 * @return true si es que la fecha actual está en rango
	 */
	public boolean isSyncDateInRange() {
		int elapsedDaysFromSync = Days.daysBetween(syncDate, DateTime.now()).getDays();
		return elapsedDaysFromSync<=rangeOfDays && elapsedDaysFromSync>=0;
	}

	/**
	 * Genera la clave correspondiente a este usuario, utiliza la fecha de
	 * sincronización y el nombre de usuario para crearla, así que esta ya
	 * deberían estar asignados
	 * 
	 * @return Key para encriptación correspondiente a este usuario
	 */
	public final String generateUserKey() {
		StringBuilder str = new StringBuilder();
		str.append(syncDate.getMillis()).insert(5,
				((char) ((int) str.charAt(5)) * 14));
		str.append(syncDate.getMillis()).insert(6,
				((char) ((int) str.charAt(1)) * 9));
		str.append(syncDate.getMillis()).insert(3, username.charAt(0));
		try {
			return Base64.encodeToString(str.toString().getBytes("UTF-8"),
					Base64.DEFAULT);
		} catch (UnsupportedEncodingException e) {
		}
		return str.toString();
	}

	// #region Getters y Setters

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public DateTime getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(DateTime syncDate) {
		this.syncDate = syncDate;
	}

	public UserStatus getStatus() {
		return UserStatus.get(status);
	}

	public void setStatus(UserStatus status) {
		this.status = status.toShort();
	}

	// #endregion

}
