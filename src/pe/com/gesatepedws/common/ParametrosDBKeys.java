package pe.com.gesatepedws.common;

public enum ParametrosDBKeys {

	HOR_EJEC_PROC("1"),
	HOR_SAL_UNID_BOD("2"),
	TIEMP_PROM_DESP("3"),
	FACT_PES_CARG("4"),
	FACT_VOL_CARG("5"),
	RUT_GEN_HOJ_RUT("6"),
	MIN_ADVERT_VENT_HOR("7"),
	KEY_API_DIRECTIONS("8"),
	KEY_FIREBASE("9"),
	AUTH_DOMAIN("10"),
	DATABASE_URL("11"),
	PROJECT_ID("12"),
	STORAGE_BUCKET("13"),
	MESSAGING_SENDER_ID("14"),
	SMTP_SERVER("15"),
	FROM_EMAIL("16"),
	PASSWORD_EMAIL("17"),
	SMTP_PORT("18"),
	SMS_API_KEY("19"),
	SMS_SENDER("20"),
	SMS_COD_PAIS_DEST("21"),
	SMS_TEST_MODE("22"),
	SMS_WS_PROVIDER_URL("23")
	;
	
	private String dbKey;
	
	private ParametrosDBKeys(String dbKey) {
		this.dbKey = dbKey;
	}
	
	public String getKey() {
		return this.dbKey;
	}
	
}
