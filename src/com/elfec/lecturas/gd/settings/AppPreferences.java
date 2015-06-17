package com.elfec.lecturas.gd.settings;



import com.elfec.lecturas.gd.model.exceptions.InitializationException;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Maneja las sharedpreferences de toda la aplicaci�n
 * @author Diego
 *
 */
public class AppPreferences {

	private final String LOGGED_USERNAME = "loggedUsername";
	private final String LOGGED_CASHDESK_NUMBER = "loggedCashdeskNumber";
	private final String LOGGED_CASHDESK_DESC = "loggedCashdeskDescription";
	//ONCE IMPORT DATA
	private final String ALL_ONCE_REQUIRED_DATA_IMPORTED = "allOnceReqDataImported";
	private final String SUPPLY_CATEGORY_TYPES_IMPORTED = "supplyCategoryTypesImported";
	private final String CPT_CALCULATION_BASES_IMPORTED = "conceptCalculationBasesImported";
	private final String PRNT_CALCULATION_BASES_IMPORTED = "printCalculationBasesImported";
	private final String CATEGORIES_IMPORTED = "categoriesImported";
	private final String CONCEPTS_IMPORTED = "conceptsImported";
	private final String BANK_ACCOUNTS_IMPORTED = "bankAccountsImported";
	private final String PERIOD_BANK_ACCOUNTS_IMPORTED = "periodBankAccountsImported";
	private final String ANNULMENT_REASONS_IMPORTED = "annulmentReasonsImported";
	
	private SharedPreferences preferences;
	
	private AppPreferences(Context context)
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
		
	private static Context context;
	private static AppPreferences appPreferences;
	/**
	 * Este m�todo se debe llamar al inicializar la aplicaci�n
	 * @param context
	 */
	public static void initialize(Context context)
	{
		AppPreferences.context = context;
	}
	/**
	 * Obtiene el contexto de la aplicaci�n
	 * @return el contexto de la aplicaci�n
	 */
	public static Context getApplicationContext()
	{
		return AppPreferences.context;
	}
	
	public static AppPreferences instance()
	{
		if(appPreferences==null)
		{
			if(context==null)
				throw new InitializationException();
			appPreferences = new AppPreferences(context);
		}
		return appPreferences;
	}
	
	/**
	 * Obtiene el usuario logeado actual
	 * @return null si es que ninguno se ha logeado
	 */
	public String getLoggedUsername()
	{
		return preferences.getString(LOGGED_USERNAME, null);
	}
	
	/**
	 * Asigna el usuario logeado actual, sobreescribe cualquier usuario que haya sido logeado antes
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setLoggedUsername(String loggedUsername)
	{
		preferences.edit().putString(LOGGED_USERNAME, loggedUsername).commit();
		return this;
	}	
	
	/**
	 * Obtiene el numero de caja del usuario logeado actual
	 * @return -1 si es que ninguno se ha logeado, o si no se asign� el valor
	 */
	public int getLoggedCashdeskNumber()
	{
		return preferences.getInt(LOGGED_CASHDESK_NUMBER, -1);
	}
	
	/**
	 * Asigna el numero de caja del usuario logeado actual
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setLoggedCashdeskNumber(int loggedCashdeskNumber)
	{
		preferences.edit().putInt(LOGGED_CASHDESK_NUMBER, loggedCashdeskNumber).commit();
		return this;
	}	

	/**
	 * Obtiene la descripci�n de la caja del usuario logeado actual
	 * @return -1 si es que ninguno se ha logeado, o si no se asign� el valor
	 */
	public String getLoggedCashdeskDesc()
	{
		return preferences.getString(LOGGED_CASHDESK_DESC, null);
	}
	
	/**
	 * Asigna la descripci�n de la caja  del usuario logeado actual
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setLoggedCashdeskDesc(String loggedCashdeskNumber)
	{
		preferences.edit().putString(LOGGED_CASHDESK_DESC, loggedCashdeskNumber).commit();
		return this;
	}
	
	
	
	/**
	 * Indica si toda la informaci�n que solo debe ser importada una vez la ha sido
	 * @return true si es que ya se import� toda
	 */
	public boolean isAllOnceReqDataImported()
	{
		return preferences.getBoolean(ALL_ONCE_REQUIRED_DATA_IMPORTED, false);
	}	
	/**
	 * Asigna si toda la informaci�n que solo debe ser importada una vez la ha sido
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setAllOnceReqDataImported(boolean isImported)
	{
		preferences.edit().putBoolean(ALL_ONCE_REQUIRED_DATA_IMPORTED, isImported).commit();
		return this;
	}
	
	/**
	 * Indica si los TIPOS_CATEG_SUM han sido importados
	 * @return true si es que ya se import�
	 */
	public boolean isSupplyCategoryTypesImported()
	{
		return preferences.getBoolean(SUPPLY_CATEGORY_TYPES_IMPORTED, false);
	}	
	/**
	 * Asigna si los TIPOS_CATEG_SUM han sido importados
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setSupplyCategoryTypesImported(boolean isImported)
	{
		preferences.edit().putBoolean(SUPPLY_CATEGORY_TYPES_IMPORTED, isImported).commit();
		return this;
	}
	
	/**
	 * Indica si los GBASES_CALC_CPTOS han sido importados
	 * @return true si es que ya se import�
	 */
	public boolean isConceptCalculationBasesImported()
	{
		return preferences.getBoolean(CPT_CALCULATION_BASES_IMPORTED, false);
	}	
	/**
	 * Asigna si los GBASES_CALC_CPTOS han sido importados
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setConceptCalculationBasesImported(boolean isImported)
	{
		preferences.edit().putBoolean(CPT_CALCULATION_BASES_IMPORTED, isImported).commit();
		return this;
	}
	
	/**
	 * Indica si los GBASES_CALC_IMP han sido importados
	 * @return true si es que ya se import�
	 */
	public boolean isPrintCalculationBasesImported()
	{
		return preferences.getBoolean(PRNT_CALCULATION_BASES_IMPORTED, false);
	}	
	/**
	 * Asigna si los GBASES_CALC_IMP han sido importados
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setPrintCalculationBasesImported(boolean isImported)
	{
		preferences.edit().putBoolean(PRNT_CALCULATION_BASES_IMPORTED, isImported).commit();
		return this;
	}
	
	/**
	 * Indica si las CATEGORIAS han sido importadas
	 * @return true si es que ya se import�
	 */
	public boolean isCategoriesImported()
	{
		return preferences.getBoolean(CATEGORIES_IMPORTED, false);
	}	
	/**
	 * Asigna si las CATEGORIAS han sido importadas
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setCategoriesImported(boolean isImported)
	{
		preferences.edit().putBoolean(CATEGORIES_IMPORTED, isImported).commit();
		return this;
	}
	
	/**
	 * Indica si los CONCEPTOS han sido importados
	 * @return true si es que ya se import�
	 */
	public boolean isConceptsImported()
	{
		return preferences.getBoolean(CONCEPTS_IMPORTED, false);
	}	
	/**
	 * Asigna si los CONCEPTOS han sido importados
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setConceptsImported(boolean isImported)
	{
		preferences.edit().putBoolean(CONCEPTS_IMPORTED, isImported).commit();
		return this;
	}
	
	/**
	 * Indica si las BAN_CTAS han sido importadas
	 * @return true si es que ya se import�
	 */
	public boolean isBankAccountsImported()
	{
		return preferences.getBoolean(BANK_ACCOUNTS_IMPORTED, false);
	}	
	/**
	 * Asigna si las BAN_CTAS han sido importadas
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setBankAccountsImported(boolean isImported)
	{
		preferences.edit().putBoolean(BANK_ACCOUNTS_IMPORTED, isImported).commit();
		return this;
	}
	
	/**
	 * Indica si los BAN_CTAS_PER han sido importados
	 * @return true si es que ya se import�
	 */
	public boolean isPeriodBankAccountsImported()
	{
		return preferences.getBoolean(PERIOD_BANK_ACCOUNTS_IMPORTED, false);
	}	
	/**
	 * Asigna si los BAN_CTAS_PER han sido importados
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setPeriodBankAccountsImported(boolean isImported)
	{
		preferences.edit().putBoolean(PERIOD_BANK_ACCOUNTS_IMPORTED, isImported).commit();
		return this;
	}
	
	/**
	 * Indica si los MOTIVOS_ANULACION han sido importados
	 * @return true si es que ya se import�
	 */
	public boolean isAnnulmentReasonsImported()
	{
		return preferences.getBoolean(ANNULMENT_REASONS_IMPORTED, false);
	}	
	/**
	 * Asigna si los MOTIVOS_ANULACION han sido importados
	 * @return la instancia actual de PreferencesManager
	 */
	public AppPreferences setAnnulmentReasonsImported(boolean isImported)
	{
		preferences.edit().putBoolean(ANNULMENT_REASONS_IMPORTED, isImported).commit();
		return this;
	}
	
	/**
	 * Borra las preferencias guardadas de la informaci�n que se debe importar
	 * solo una vez 
	 */
	public void wipeOnceRequiredDataPreferences()
	{
		preferences.edit().remove(ALL_ONCE_REQUIRED_DATA_IMPORTED)
						  .remove(SUPPLY_CATEGORY_TYPES_IMPORTED)
						  .remove(CPT_CALCULATION_BASES_IMPORTED)
						  .remove(PRNT_CALCULATION_BASES_IMPORTED)
						  .remove(CATEGORIES_IMPORTED)
						  .remove(CONCEPTS_IMPORTED)
						  .remove(BANK_ACCOUNTS_IMPORTED)
						  .remove(PERIOD_BANK_ACCOUNTS_IMPORTED)
						  .remove(ANNULMENT_REASONS_IMPORTED).commit();
	}
}
