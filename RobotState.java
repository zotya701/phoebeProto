package phoebeProto;

/**
 * A robot lehetségeit definiáló enum.
 */
public enum RobotState {
	
	/**
	 * Normál állapot.
	 */
	Normal,
	
	/**
	 * A robot nem tudja módosítani a sebességvektorát ebben az állapotban.
	 */
	Unturnable,
	
	/**
	 * Kiesett robot állapota.
	 */
	Eliminated
	
}
