package zip.luzey.chatty.client.utility;


import org.jetbrains.annotations.NotNull;

public enum OperatingSystem {
	WINDOWS, LINUX, MAC, SOLARIS, UNKNOWN;

	private static final @NotNull String PROP_OS = System.getProperty("os.name").toLowerCase();

	// @formatter:off
	private static final @NotNull OperatingSystem OS =
		 (PROP_OS.contains("win"))                                                       ? WINDOWS :
		 (PROP_OS.contains("mac") || PROP_OS.contains("osx"))                            ? MAC :
		 (PROP_OS.contains("nix") || PROP_OS.contains("nux") || PROP_OS.contains("aix")) ? LINUX :
		 (PROP_OS.contains("solaris"))                                                   ? SOLARIS :
		                                                                                   UNKNOWN;
	// @formatter:on

	private static final @NotNull String libraryExtension = OS == WINDOWS ? "dll" : OS == MAC ? "dylib" : "SO";

	/**
	 * @return the operating-system the jvm reported
	 */
	public static @NotNull OperatingSystem get() {
		return OS;
	}

	/**
	 * @return the operating-system-string the jvm reported
	 */
	public static @NotNull String getPropString() {
		return PROP_OS;
	}

	/**
	 * @return the native file-extension ending
	 */
	public static @NotNull String getLibExt() {
		return libraryExtension;
	}

	public static final class Architecture { // @formatter:off
		public static final @NotNull String PROP_ARCH = System.getProperty("os.arch").toLowerCase();
		public static final boolean
			 AMD_64 = PROP_ARCH.contains("amd64"  ),
			 ARM_64 = PROP_ARCH.contains("aarch64"),
			 ARM    = PROP_ARCH.contains("armv7"  ),
			 X_86   = PROP_ARCH.contains("x86_64" ) ||
			          PROP_ARCH.contains("x86-64" ),

			 IA_64   = PROP_ARCH.contains("ia64"   ),
			 MIPS_64 = PROP_ARCH.contains("mips64" ),
			 MIPS    = PROP_ARCH.contains("mips"   ),
			 PPC_64  = PROP_ARCH.contains("ppc64"  ),
			 SPARC   = PROP_ARCH.contains("sparcv9");



		/** @return if it's amd64 */ public static boolean isAmd64 () { return AMD_64; }
		/** @return if it's arm64 */ public static boolean isArm64 () { return ARM_64; }
		/** @return if it's arm   */ public static boolean isArm   () { return ARM  ; }
		/** @return if it's x86   */ public static boolean isX86   () { return X_86  ; }

		/** @return if it's ia64   */ public static boolean isIa64   () { return IA_64  ; }
		/** @return if it's mips64 */ public static boolean isMips64 () { return MIPS_64; }
		/** @return if it's mips   */ public static boolean isMips   () { return MIPS  ; }
		/** @return if it's ppc64  */ public static boolean isPpc64  () { return PPC_64 ; }
		/** @return if it's sparc  */ public static boolean isSparc  () { return SPARC ; }

		/**
		 * @return if it's 64-bit
		 */
		public static boolean is64Bit() {
			return isArm64 () ||
			       isAmd64 () ||
			       isMips64() ||
			       isPpc64 () ||
			       isSparc () ||
			       isIa64  ();
		}

		public static @NotNull String getArchString() { return PROP_ARCH; }
	} // @formatter:on
}
