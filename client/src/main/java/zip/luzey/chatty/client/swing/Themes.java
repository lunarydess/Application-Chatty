package zip.luzey.chatty.client.swing;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

public enum Themes {
	// @formatter:off
	LIGHT ("Light", new FlatLightLaf ()),
	DARK  ("Dark",  new FlatDarkLaf  ()),

	ARC             ("Arc - Default",     new FlatArcIJTheme           ()),
	ARC_ORANGE      ("Arc - Orange",      new FlatArcOrangeIJTheme     ()),
	ARC_DARK        ("Arc - Dark",        new FlatArcDarkIJTheme       ()),
	ARC_DARK_ORANGE ("Arc - Dark Orange", new FlatArcDarkOrangeIJTheme ()),

	GRADIANTO_MIDNIGHT_BLUE ("Gradianto - Midnight Blue", new FlatGradiantoMidnightBlueIJTheme ()),
	GRADIANTO_DEEP_OCEAN    ("Gradianto - Deep Ocean",    new FlatGradiantoDeepOceanIJTheme    ()),
	GRADIANTO_DARK_FUCHSIA  ("Gradianto - Dark Fuchsia",  new FlatGradiantoDarkFuchsiaIJTheme  ()),
	GRADIANTO_NATURE_GREEN  ("Gradianto - Nature Green",  new FlatGradiantoNatureGreenIJTheme  ()),

	GRUVBOX_DARK_SOFT   ("Gruvbox - Dark Soft",   new FlatGruvboxDarkSoftIJTheme   ()),
	GRUVBOX_DARK_MEDIUM ("Gruvbox - Dark Medium", new FlatGruvboxDarkMediumIJTheme ()),
	GRUVBOX_DARK_HARD   ("Gruvbox - Dark Hard",   new FlatGruvboxDarkHardIJTheme   ()),

	INTELLIJ ("IntelliJ", new FlatIntelliJLaf ()),
	DARCULA  ("Darcula",  new FlatDarculaLaf  ()),

	MATERIAL_DESIGN_DARK    ("Material - Design Dark",     new FlatMaterialDesignDarkIJTheme ()),
	MATERIAL_ARC_DARK       ("Material - Arc Dark",        new FlatArcDarkIJTheme            ()),
	MATERIAL_ATOM_ONE_DARK  ("Material - Atom One Dark",   new FlatAtomOneDarkIJTheme        ()),
	MATERIAL_ATOM_ONE_LIGHT ("Material - Atom One Light",  new FlatAtomOneLightIJTheme       ()),
	MATERIAL_DRACULA        ("Material - Dracula",         new FlatDraculaIJTheme            ()),
	MATERIAL_GITHUB         ("Material - GitHub",          new FlatGitHubIJTheme             ()),
	MATERIAL_GITHUB_DARK    ("Material - GitHub Dark",     new FlatGitHubDarkIJTheme         ()),
	MATERIAL_LIGHT_OWL      ("Material - Light Owl",       new FlatLightOwlIJTheme           ()),
	MATERIAL_DARKER         ("Material - Darker",          new FlatMaterialDarkerIJTheme     ()),
	MATERIAL_DEEP_OCEAN     ("Material - Deep Ocean",      new FlatMaterialDeepOceanIJTheme  ()),
	MATERIAL_LIGHTER        ("Material - Lighter",         new FlatMaterialLighterIJTheme    ()),
	MATERIAL_OCEANIC        ("Material - Oceanic",         new FlatMaterialOceanicIJTheme    ()),
	MATERIAL_PALENIGHT      ("Material - Palenight",       new FlatMaterialPalenightIJTheme  ()),
	MATERIAL_MONOKAI_PRO    ("Material - Monokai Pro",     new FlatMonokaiProIJTheme         ()),
	MATERIAL_MOONLIGHT      ("Material - Moonlight",       new FlatMoonlightIJTheme          ()),
	MATERIAL_NIGHT_OWL      ("Material - Night Owl",       new FlatNightOwlIJTheme           ()),
	MATERIAL_SOLARIZED_DARK ("Material - Solarized Dark",  new FlatSolarizedDarkIJTheme      ()),
	MATERIAL_SOLARIZED_LIGH ("Material - Solarized Light", new FlatSolarizedLightIJTheme     ()),

	SOLARIZED_DARK  ("Solarized - Dark",  new FlatSolarizedDarkIJTheme  ()),
	SOLARIZED_LIGHT ("Solarized - Light", new FlatSolarizedLightIJTheme ()),

	CARBON        ("Carbon",        new FlatCarbonIJTheme       ()),
	COBALT2       ("Cobalt 2",      new FlatCobalt2IJTheme      ()),
	CYAN_LIGHT    ("Cyan Light",    new FlatCyanLightIJTheme    ()),
	DARK_FLAT     ("Dark Flat",     new FlatDarkFlatIJTheme     ()),
	DARK_PURPLE   ("Dark purple",   new FlatDarkPurpleIJTheme   ()),
	DRACULA       ("Dracula",       new FlatDraculaIJTheme      ()),
	GRAY          ("Gray",          new FlatGrayIJTheme         ()),
	HIBERBEE_DARK ("Hiberbee Dark", new FlatHiberbeeDarkIJTheme ()),
	HIGH_CONTRAST ("High Contrast", new FlatHighContrastIJTheme ()),
	LIGHT_FLAT    ("Light Flat",    new FlatLightFlatIJTheme    ()),
	MONOCAI       ("Monocai",       new FlatMonocaiIJTheme      ()),
	NORD          ("Nord",          new FlatNordIJTheme         ()),
	ONE_DARK      ("One Dark",      new FlatOneDarkIJTheme      ()),
	SPACEGRAY     ("Spacegray",     new FlatSpacegrayIJTheme    ()),
	VUESION       ("Vuesion",       new FlatVuesionIJTheme      ()),
	XCODE         ("XCode",         new FlatXcodeDarkIJTheme    ());
	// @formatter:on

	private static final File SCHEME_FILE = new File(System.getProperty(
		 "user.home",
		 "~/.needle"
	), ".config/needle/swing.theme.txt");
	private static Themes globalTheme = DARK;

	static {
		if (!SCHEME_FILE.exists()) {
			SCHEME_FILE.mkdirs();
			SCHEME_FILE.delete();
			try {
				if (!SCHEME_FILE.createNewFile()) throw new RuntimeException();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private final String friendlyName;
	private final FlatLaf theme;

	Themes(
		 final String friendlyName,
		 final FlatLaf theme
	) {
		this.friendlyName = friendlyName;
		this.theme = theme;
	}

	public static void select(final @NotNull Themes theme) {
		select(theme, true);
	}

	public static void select(
		 final @NotNull Themes theme,
		 final boolean update
	) {
		globalTheme = theme;
		if (update) applyGlobal();
	}

	public static void applyGlobal() {
		FlatLaf.setup(globalTheme.getTheme());
		FlatLaf.updateUI();
	}


	public static @Nullable Themes byName(final @NotNull String name) {
		return byName(name, true);
	}

	public static @Nullable Themes byName(
		 final @NotNull String name,
		 final boolean ignoreCase
	) {
		Predicate<Themes> predicate = ignoreCase ?
		                              t -> t.friendlyName.equalsIgnoreCase(name) :
		                              t -> t.friendlyName.equals(name);
		for (Themes theme : values()) {
			if (!predicate.test(theme)) continue;
			return theme;
		}
		return null;
	}

	public static Themes getGlobalTheme() {
		return globalTheme;
	}

	public FlatLaf getTheme() {
		return this.theme;
	}
}
