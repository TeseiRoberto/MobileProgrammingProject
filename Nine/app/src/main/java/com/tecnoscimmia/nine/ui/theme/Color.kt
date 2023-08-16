package com.tecnoscimmia.nine.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/*
 * This file contains definitions for all the colors used in the application and all it's widgets
 */


class NineColors
{
	companion object
	{
		val gold			= Color(0xFFEDDF41)
		val silver			= Color(0xFFBFBFBF)
		val bronze 			= Color(0xFFD16D33)

		val settingsGrey 	= Color(0xFF808080)

		val md_theme_light_primary = Color(0xFF0061A3)
		val md_theme_light_onPrimary = Color(0xFFFFFFFF)
		val md_theme_light_primaryContainer = Color(0xFFD1E4FF)
		val md_theme_light_onPrimaryContainer = Color(0xFF001D36)
		val md_theme_light_secondary = Color(0xFF2E5DA8)
		val md_theme_light_onSecondary = Color(0xFFFFFFFF)
		val md_theme_light_secondaryContainer = Color(0xFFD7E2FF)
		val md_theme_light_onSecondaryContainer = Color(0xFF001A40)
		val md_theme_light_tertiary = Color(0xFF0561A4)
		val md_theme_light_onTertiary = Color(0xFFFFFFFF)
		val md_theme_light_tertiaryContainer = Color(0xFFD2E4FF)
		val md_theme_light_onTertiaryContainer = Color(0xFF001D36)
		val md_theme_light_error = Color(0xFFBA1A1A)
		val md_theme_light_errorContainer = Color(0xFFFFDAD6)
		val md_theme_light_onError = Color(0xFFFFFFFF)
		val md_theme_light_onErrorContainer = Color(0xFF410002)
		val md_theme_light_background = Color(0xFFFDFCFF)
		val md_theme_light_onBackground = Color(0xFF1A1C1E)
		val md_theme_light_surface = Color(0xFFFDFCFF)
		val md_theme_light_onSurface = Color(0xFF1A1C1E)
		val md_theme_light_surfaceVariant = Color(0xFFDFE2EB)
		val md_theme_light_onSurfaceVariant = Color(0xFF42474E)
		val md_theme_light_outline = Color(0xFF73777F)
		val md_theme_light_inverseOnSurface = Color(0xFFF1F0F4)
		val md_theme_light_inverseSurface = Color(0xFF2F3033)
		val md_theme_light_inversePrimary = Color(0xFF9ECAFF)
		val md_theme_light_shadow = Color(0xFF000000)
		val md_theme_light_surfaceTint = Color(0xFF0061A3)
		val md_theme_light_outlineVariant = Color(0xFFC3C7CF)
		val md_theme_light_scrim = Color(0xFF000000)

		val md_theme_dark_primary = Color(0xFF9ECAFF)
		val md_theme_dark_onPrimary = Color(0xFF003258)
		val md_theme_dark_primaryContainer = Color(0xFF00497C)
		val md_theme_dark_onPrimaryContainer = Color(0xFFD1E4FF)
		val md_theme_dark_secondary = Color(0xFFACC7FF)
		val md_theme_dark_onSecondary = Color(0xFF002F67)
		val md_theme_dark_secondaryContainer = Color(0xFF09458E)
		val md_theme_dark_onSecondaryContainer = Color(0xFFD7E2FF)
		val md_theme_dark_tertiary = Color(0xFF9FCAFF)
		val md_theme_dark_onTertiary = Color(0xFF003259)
		val md_theme_dark_tertiaryContainer = Color(0xFF00497E)
		val md_theme_dark_onTertiaryContainer = Color(0xFFD2E4FF)
		val md_theme_dark_error = Color(0xFFFFB4AB)
		val md_theme_dark_errorContainer = Color(0xFF93000A)
		val md_theme_dark_onError = Color(0xFF690005)
		val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
		val md_theme_dark_background = Color(0xFF1A1C1E)
		val md_theme_dark_onBackground = Color(0xFFE2E2E6)
		val md_theme_dark_surface = Color(0xFF1A1C1E)
		val md_theme_dark_onSurface = Color(0xFFE2E2E6)
		val md_theme_dark_surfaceVariant = Color(0xFF42474E)
		val md_theme_dark_onSurfaceVariant = Color(0xFFC3C7CF)
		val md_theme_dark_outline = Color(0xFF8D9199)
		val md_theme_dark_inverseOnSurface = Color(0xFF1A1C1E)
		val md_theme_dark_inverseSurface = Color(0xFFE2E2E6)
		val md_theme_dark_inversePrimary = Color(0xFF0061A3)
		val md_theme_dark_shadow = Color(0xFF000000)
		val md_theme_dark_surfaceTint = Color(0xFF9ECAFF)
		val md_theme_dark_outlineVariant = Color(0xFF42474E)
		val md_theme_dark_scrim = Color(0xFF000000)


		val seed = Color(0xFF0D1B2A)

		val lightTheme = lightColorScheme(
			primary = md_theme_light_primary,
			onPrimary = md_theme_light_onPrimary,
			primaryContainer = md_theme_light_primaryContainer,
			onPrimaryContainer = md_theme_light_onPrimaryContainer,
			secondary = md_theme_light_secondary,
			onSecondary = md_theme_light_onSecondary,
			secondaryContainer = md_theme_light_secondaryContainer,
			onSecondaryContainer = md_theme_light_onSecondaryContainer,
			tertiary = md_theme_light_tertiary,
			onTertiary = md_theme_light_onTertiary,
			tertiaryContainer = md_theme_light_tertiaryContainer,
			onTertiaryContainer = md_theme_light_onTertiaryContainer,
			error = md_theme_light_error,
			errorContainer = md_theme_light_errorContainer,
			onError = md_theme_light_onError,
			onErrorContainer = md_theme_light_onErrorContainer,
			background = md_theme_light_background,
			onBackground = md_theme_light_onBackground,
			surface = md_theme_light_surface,
			onSurface = md_theme_light_onSurface,
			surfaceVariant = md_theme_light_surfaceVariant,
			onSurfaceVariant = md_theme_light_onSurfaceVariant,
			outline = md_theme_light_outline,
			inverseOnSurface = md_theme_light_inverseOnSurface,
			inverseSurface = md_theme_light_inverseSurface,
			inversePrimary = md_theme_light_inversePrimary,
			surfaceTint = md_theme_light_surfaceTint,
			outlineVariant = md_theme_light_outlineVariant,
			scrim = md_theme_light_scrim,
		)


		val darkTheme = darkColorScheme(
			primary = md_theme_dark_primary,
			onPrimary = md_theme_dark_onPrimary,
			primaryContainer = md_theme_dark_primaryContainer,
			onPrimaryContainer = md_theme_dark_onPrimaryContainer,
			secondary = md_theme_dark_secondary,
			onSecondary = md_theme_dark_onSecondary,
			secondaryContainer = md_theme_dark_secondaryContainer,
			onSecondaryContainer = md_theme_dark_onSecondaryContainer,
			tertiary = md_theme_dark_tertiary,
			onTertiary = md_theme_dark_onTertiary,
			tertiaryContainer = md_theme_dark_tertiaryContainer,
			onTertiaryContainer = md_theme_dark_onTertiaryContainer,
			error = md_theme_dark_error,
			errorContainer = md_theme_dark_errorContainer,
			onError = md_theme_dark_onError,
			onErrorContainer = md_theme_dark_onErrorContainer,
			background = md_theme_dark_background,
			onBackground = md_theme_dark_onBackground,
			surface = md_theme_dark_surface,
			onSurface = md_theme_dark_onSurface,
			surfaceVariant = md_theme_dark_surfaceVariant,
			onSurfaceVariant = md_theme_dark_onSurfaceVariant,
			outline = md_theme_dark_outline,
			inverseOnSurface = md_theme_dark_inverseOnSurface,
			inverseSurface = md_theme_dark_inverseSurface,
			inversePrimary = md_theme_dark_inversePrimary,
			surfaceTint = md_theme_dark_surfaceTint,
			outlineVariant = md_theme_dark_outlineVariant,
			scrim = md_theme_dark_scrim,
		)

	}

}