package com.github.jdha.codedivider.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.Converter
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.OptionTag
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

enum class TextCase {
    UPPER,
    LOWER,
    TITLE,
    NONE
}

enum class TextPosition {
    LEFT,
    CENTER,
    Right
}

enum class CommentSymbolType {
    ONE_SINGLE_LINE,
    TWO_SINGLE_LINE,
    TWO_MULTI_LINE
}

enum class BoxType {
    NORMAL,
    ROUNDED,
    HEAVY,
    NORMAL_OPEN_TOP,
    ROUNDED_OPEN_TOP,
}

data class LineSettings(
    var lineLength: Int,
    var textCase: TextCase,
    var whiteSpacePadCommentSymbol: Boolean,
    var customLineChar: String,
    var commentSymbolType: CommentSymbolType,
    var errorOnNoCommentSymbol: Boolean
)

@Service
@State(name = "CodeDividerSettings", storages = [Storage("CodeDividerSettings.xml")])
class CodeDividerSettingsState : PersistentStateComponent<CodeDividerSettingsState> {

    // LINE - NORMAL
    @OptionTag(converter = LineSettingsConverter::class)
    var normalLineSettings =
        LineSettings(
            lineLength = 88,
            textCase = TextCase.TITLE,
            whiteSpacePadCommentSymbol = true,
            customLineChar = "─",
            commentSymbolType = CommentSymbolType.ONE_SINGLE_LINE,
            errorOnNoCommentSymbol = true)

    // LINE - HEAVY
    @OptionTag(converter = LineSettingsConverter::class)
    var heavyLineSettings =
        LineSettings(
            lineLength = 88,
            textCase = TextCase.UPPER,
            whiteSpacePadCommentSymbol = true,
            customLineChar = "═",
            commentSymbolType = CommentSymbolType.ONE_SINGLE_LINE,
            errorOnNoCommentSymbol = true)

    // BOX
    var maxBoxLength: Int = 88

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

// This may cause an issue in future as there is an expected number of fields. Adding extra fields
// will cause this error: Caused by: java.lang.IndexOutOfBoundsException: Index 5 out of bounds for
// length 5. The only way to fix this is to add logic to check if this error happens and return new
// default values for the settings struct.

class LineSettingsConverter : Converter<LineSettings>() {
    override fun fromString(@NotNull value: String): LineSettings {
        val parts = value.split("\t")
        return LineSettings(
            lineLength = parts[0].toInt(),
            textCase = TextCase.valueOf(parts[1]),
            whiteSpacePadCommentSymbol = parts[2].toBoolean(),
            customLineChar = parts[3],
            commentSymbolType = CommentSymbolType.valueOf(parts[4]),
            errorOnNoCommentSymbol = parts[5].toBoolean())
    }

    override fun toString(value: LineSettings): String {
        return listOf(
                value.lineLength.toString(),
                value.textCase.name,
                value.whiteSpacePadCommentSymbol.toString(),
                value.customLineChar,
                value.commentSymbolType.name,
                value.errorOnNoCommentSymbol.toString())
            .joinToString("\t")
    }
}
