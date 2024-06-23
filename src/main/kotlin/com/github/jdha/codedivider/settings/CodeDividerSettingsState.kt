package com.github.jdha.codedivider.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
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
    DOUBLE,
    NORMAL_OPEN_TOP,
    ROUNDED_OPEN_TOP,
    HEAVY_OPEN_TOP,
    DOUBLE_OPEN_TOP,
    NORMAL_OPEN_SIDE,
    ROUNDED_OPEN_SIDE,
    HEAVY_OPEN_SIDE,
    DOUBLE_OPEN_SIDE,
}

@Service
@State(name = "CodeDividerSettings", storages = [Storage("CodeDividerSettings.xml")])
class CodeDividerSettingsState : PersistentStateComponent<CodeDividerSettingsState> {

    // GENERAL
    var errorOnNoCommentSymbol = true

    // LINE - GENERAL
    var lineLengthLine = 88
    var whiteSpacePadCommentSymbolLine = true
    var commentSymbolTypeLine = CommentSymbolType.ONE_SINGLE_LINE

    // LINE - STANDARD
    var lineCharStandard = "─"
    var textCaseStandard = TextCase.TITLE

    // LINE - HEAVY
    var lineCharHeavy = "━"
    var textCaseHeavy = TextCase.UPPER

    // LINE - DOUBLE
    var lineCharDouble = "═"
    var textCaseDouble = TextCase.TITLE

    // BOX
    var targetLengthBox = 60
    var paddingSpaceAfterCommentSymbolBox = 5
    var paddingInsideBox = 2
    var textCaseBox = TextCase.NONE

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
