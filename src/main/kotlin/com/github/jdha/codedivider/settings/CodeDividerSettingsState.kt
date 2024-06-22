package com.github.jdha.codedivider.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.Converter
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.OptionTag
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@Serializable
enum class TextCase {
    UPPER,
    LOWER,
    TITLE,
    NONE
}

@Serializable
enum class TextPosition {
    LEFT,
    CENTER,
    Right
}

@Serializable
enum class CommentSymbolType {
    ONE_SINGLE_LINE,
    TWO_SINGLE_LINE,
    TWO_MULTI_LINE
}

enum class BoxType {
    NORMAL,
    ROUNDED,
    HEAVY,
    DOUBLE,
    NORMAL_OPEN_TOP,
    ROUNDED_OPEN_TOP,
}

@Serializable
enum class LineType {
    NORMAL_LINE,
    HEAVY_LINE,
}

@Serializable
data class LineSettings(
    var lineType: LineType,
    var lineLength: Int,
    var textCase: TextCase,
    var whiteSpacePadCommentSymbol: Boolean,
    var customLineChar: String,
    var commentSymbolType: CommentSymbolType,
    var errorOnNoCommentSymbol: Boolean
)

@Serializable
data class BoxSettings(
    var maxLength: Int,
    var targetLength: Int,
    var textCase: TextCase,
    var commentSymbolType: CommentSymbolType,
    var errorOnNoCommentSymbol: Boolean
)

@Service
@State(name = "CodeDividerSettings", storages = [Storage("CodeDividerSettings.xml")])
class CodeDividerSettingsState : PersistentStateComponent<CodeDividerSettingsState> {

    // LINE - NORMAL
    @OptionTag(converter = LineSettingsConverter::class)
    var normalLineSettings = normalSettingDefaults()

    // LINE - HEAVY
    @OptionTag(converter = LineSettingsConverter::class)
    var heavyLineSettings = heavySettingDefaults()

    // BOX
    @OptionTag(converter = BoxSettingsConverter::class) var boxSettings = boxSettingDefaults()

    companion object {
        val instance: CodeDividerSettingsState
            get() =
                ApplicationManager.getApplication().getService(CodeDividerSettingsState::class.java)
    }

    @Nullable override fun getState() = this

    override fun loadState(state: CodeDividerSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }
}

// ══ Helpers ══════════════════════════════════════════════════════════════════════════════════════

fun heavySettingDefaults() =
    LineSettings(
        lineType = LineType.HEAVY_LINE,
        lineLength = 88,
        textCase = TextCase.UPPER,
        whiteSpacePadCommentSymbol = true,
        customLineChar = "═",
        commentSymbolType = CommentSymbolType.ONE_SINGLE_LINE,
        errorOnNoCommentSymbol = true,
    )

fun normalSettingDefaults() =
    LineSettings(
        lineType = LineType.NORMAL_LINE,
        lineLength = 88,
        textCase = TextCase.TITLE,
        whiteSpacePadCommentSymbol = true,
        customLineChar = "─",
        commentSymbolType = CommentSymbolType.ONE_SINGLE_LINE,
        errorOnNoCommentSymbol = true,
    )

fun boxSettingDefaults() =
    BoxSettings(
        maxLength = 88,
        targetLength = 60,
        textCase = TextCase.TITLE,
        commentSymbolType = CommentSymbolType.ONE_SINGLE_LINE,
        errorOnNoCommentSymbol = true,
    )

class LineSettingsConverter : Converter<LineSettings>() {
    override fun fromString(@NotNull value: String) =
        try {
            Json.decodeFromString<LineSettings>(value)
        } catch (e: Exception) {
            println("Error encountered while deserializing JSON to LineSettings. Error: $e")
            when {
                value.contains(LineType.NORMAL_LINE.name) -> {
                    println("Setting default values for LineType.NORMAL_LINE")
                    normalSettingDefaults()
                }
                value.contains(LineType.HEAVY_LINE.name) -> {
                    println("Setting default values for LineType.HEAVY_LINE")
                    heavySettingDefaults()
                }
                else -> {
                    println("Could not find LineType reference. Setting value to normal default.")
                    normalSettingDefaults()
                }
            }
        }

    override fun toString(value: LineSettings) = Json.encodeToString(value)
}

class BoxSettingsConverter : Converter<BoxSettings>() {
    override fun fromString(@NotNull value: String) =
        try {
            Json.decodeFromString<BoxSettings>(value)
        } catch (e: Exception) {
            println("Error encountered while deserializing JSON to BoxSettings. Error: $e")
            println("Setting default values for Box Settings")
            boxSettingDefaults()
        }

    override fun toString(value: BoxSettings) = Json.encodeToString(value)
}
